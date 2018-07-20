package com.pure.service.service.impl;

import com.pure.service.domain.Customer;
import com.pure.service.domain.CustomerCommunicationLog;
import com.pure.service.domain.CustomerCommunicationLogType;
import com.pure.service.domain.CustomerStatus;
import com.pure.service.domain.CustomerTrackTask;
import com.pure.service.domain.FreeClassRecord;
import com.pure.service.domain.Task;
import com.pure.service.domain.TaskStatus;
import com.pure.service.repository.CustomerCommunicationLogRepository;
import com.pure.service.repository.CustomerCommunicationLogTypeRepository;
import com.pure.service.repository.CustomerRepository;
import com.pure.service.repository.CustomerStatusRepository;
import com.pure.service.repository.CustomerTrackTaskRepository;
import com.pure.service.repository.TaskStatusRepository;
import com.pure.service.service.CustomerCommunicationLogQueryService;
import com.pure.service.service.CustomerQueryService;
import com.pure.service.service.CustomerService;
import com.pure.service.service.FreeClassRecordService;
import com.pure.service.service.dto.CustomerCommunicationLogCriteria;
import com.pure.service.service.dto.CustomerCriteria;
import com.pure.service.service.dto.TaskStatusEnum;
import com.pure.service.service.dto.dto.ReportEntity;
import com.pure.service.service.dto.request.CustomerStatusRequest;
import com.pure.service.service.dto.request.ReportElement;
import io.github.jhipster.service.filter.LongFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Service Implementation for managing Customer.
 */
@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private final Logger log = LoggerFactory.getLogger(CustomerServiceImpl.class);

    private final CustomerRepository customerRepository;

    @Autowired
    private FreeClassRecordService freeClassRecordService;

    @Autowired
    private CustomerTrackTaskRepository customerTrackTaskRepository;

    @Autowired
    private TaskStatusRepository taskStatusRepository;

    @Autowired
    private CustomerStatusRepository customerStatusRepository;

    private final CustomerQueryService customerQueryService;
    private final CustomerCommunicationLogTypeRepository customerCommunicationLogTypeRepository;
    private final CustomerCommunicationLogRepository customerCommunicationLogRepository;
    private final CustomerCommunicationLogQueryService customerCommunicationLogQueryService;

    public CustomerServiceImpl(CustomerRepository customerRepository,
                               CustomerQueryService customerQueryService,
                               CustomerCommunicationLogTypeRepository customerCommunicationLogTypeRepository,
                               CustomerCommunicationLogRepository customerCommunicationLogRepository,
                               CustomerCommunicationLogQueryService customerCommunicationLogQueryService) {
        this.customerRepository = customerRepository;
        this.customerQueryService = customerQueryService;
        this.customerCommunicationLogTypeRepository = customerCommunicationLogTypeRepository;
        this.customerCommunicationLogRepository = customerCommunicationLogRepository;
        this.customerCommunicationLogQueryService = customerCommunicationLogQueryService;
    }

    /**
     * Save a customer.
     *
     * @param customer the entity to save
     * @return the persisted entity
     */
    @Override
    public Customer save(Customer customer) {
        log.debug("Request to save Customer : {}", customer);

        if (customer.getStatus() == null && customer.getId() == null) {

            CustomerStatus newOrderStatus = customerStatusRepository.findByCode("new_created");
            customer.setStatus(newOrderStatus);
        }

        //TODO Synchronize more attribute
        FreeClassRecord freeClassRecord = customer.getNewOrder();
        if (freeClassRecord != null && customer.getStatus() != null) {

            String newOrderStatus = freeClassRecord.getStatus();

            if (!newOrderStatus.equals(customer.getStatus().getName())) {
                freeClassRecord.setStatus(customer.getStatus().getName());

                freeClassRecordService.save(freeClassRecord);

                log.debug("Update new order status while the customer status is changing");
            }

        }

        return customerRepository.save(customer);
    }

    /**
     * Get all the customers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Customer> findAll(Pageable pageable) {
        log.debug("Request to get all Customers");
        return customerRepository.findAll(pageable);
    }

    /**
     * Get one customer by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Customer findOne(Long id) {
        log.debug("Request to get Customer : {}", id);
        return customerRepository.findOne(id);
    }

    /**
     * Delete the  customer by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Customer : {}", id);
        customerRepository.delete(id);
    }

    @Override
    public Customer importCustomerFromNewOrder(Long newOrderId) {

        CustomerCriteria customerCriteria = new CustomerCriteria();
        LongFilter longFilter = new LongFilter();
        longFilter.setEquals(newOrderId);
        customerCriteria.setNewOrderId(longFilter);

        List<Customer> existCustomer = customerQueryService.findByCriteria(customerCriteria);

        if (CollectionUtils.isEmpty(existCustomer)) {

            FreeClassRecord newOrder = freeClassRecordService.findOne(newOrderId);

            Customer customer = new Customer();
            customer.setName(newOrder.getPersonName());
            customer.setContactPhoneNumber(newOrder.getContactPhoneNumber());
            customer.setSalesFollower(newOrder.getSalesFollower());
            customer.setChannel(newOrder.getMarketChannelCategory());
            customer.setNewOrder(newOrder);
            customer.setBirthday(newOrder.getBirthday());
            customer.setNewOrderResourceLocation(newOrder.getNewOrderResourceLocation());

            Customer savedCustomer = save(customer);

            CustomerCommunicationLogType newCreateOrderType = customerCommunicationLogTypeRepository.findByCode(CustomerCommunicationLogTypeEnum.new_customer_created.name());
            CustomerCommunicationLog customerCommunicationLog = new CustomerCommunicationLog();
            customerCommunicationLog.comments("开始跟单");

            customerCommunicationLog.setLogType(newCreateOrderType);
            customerCommunicationLog.customer(savedCustomer);

            CustomerCommunicationLog savedLog = customerCommunicationLogRepository.save(customerCommunicationLog);

            log.debug("Saved customer log for the new order created {}", savedLog);

            CustomerCommunicationLogCriteria customerCommunicationLogCriteria = new CustomerCommunicationLogCriteria();
            customerCommunicationLogCriteria.setFreeClassRecordId(longFilter);
            List<CustomerCommunicationLog> logs = customerCommunicationLogQueryService.findByCriteria(customerCommunicationLogCriteria);

            if (!CollectionUtils.isEmpty(logs)) {

                logs.forEach(log -> log.setCustomer(savedCustomer));
                customerCommunicationLogRepository.save(logs);
            }

            return savedCustomer;

        }

        return existCustomer.get(0);
    }

    @Override
    public void updateTrackTaskStatus(Customer customer, String lastComments) {

        customer.setLastTrackDate(Instant.now());
        customer.setLastTrackComments(lastComments);

        Integer trackedCount = customer.getTrackCount();
        if (trackedCount == null) {
            trackedCount = 1;
        } else {
            trackedCount += 1;
        }
        customer.setTrackCount(trackedCount);

        List<CustomerTrackTask> trackTasks = customerTrackTaskRepository.findByCustomer_Id(customer.getId());

        boolean stillHaveUnfinishedTask = false;
        for (CustomerTrackTask trackTask : trackTasks) {
            Task task = trackTask.getTask();

            if (task.getTaskStatus().getCode().equals(TaskStatusEnum.ongoing.name())) {

                TaskStatus ongoing = taskStatusRepository.findByCode(TaskStatusEnum.ongoing.name());
                customer.setTrackStatus(ongoing.getName());

                customerRepository.save(customer);

                stillHaveUnfinishedTask = true;
                break;

            }
        }

        if (!stillHaveUnfinishedTask) {

            TaskStatus ongoing = taskStatusRepository.findByCode(TaskStatusEnum.finished.name());
            customer.setTrackStatus(ongoing.getName());

            customerRepository.save(customer);
        }
    }

    @Override
    public List<Customer> batchSave(List<Customer> customers) {
        return customerRepository.save(customers);
    }

    @Override
    public List<ReportElement> getStatusReport(CustomerStatusRequest customerStatusRequest) {

        List<ReportEntity> reportEntities = customerRepository.searchCustomerStatusReport(customerStatusRequest.getStartDate(), customerStatusRequest.getEndDate());

        Map<Long, ReportElement> userBasedStatusCountMap = new HashMap<>();

        for (ReportEntity reportEntity : reportEntities) {
            ReportElement reportElement = userBasedStatusCountMap.get(reportEntity.getUserId());
            if (reportElement == null) {
                reportElement = new ReportElement();

                reportElement.setUserId(reportEntity.getUserId());
                reportElement.setUserName(reportEntity.getUserName());

                userBasedStatusCountMap.put(reportEntity.getUserId(), reportElement);
            }

            switch (reportEntity.getStatusCode()) {

                case "new_created":
                    reportElement.setNewCreatedCount(reportEntity.getCount());
                    break;
                case "Too_Young":
                    reportElement.setAgeTooSmallCount(reportEntity.getCount());
                    break;
                case "Bad_Information":
                    reportElement.setErrorInformation(reportEntity.getCount());
                    break;
                case "No_Willing":
                    reportElement.setNoWillingCount(reportEntity.getCount());
                    break;
                case "Considering":
                    reportElement.setConsideringCount(reportEntity.getCount());
                    break;
                case "meeting_schedule_made":
                    reportElement.setScheduledCount(reportEntity.getCount());
                    break;
                case "deal":
                    reportElement.setDealedCount(reportEntity.getCount());
                    break;

                default:
                    break;

            }
        }

        List<ReportElement> elements = new ArrayList<>(userBasedStatusCountMap.values());
        elements.forEach(element -> {

            Integer totalCount =  element.getConsideringCount() + element.getDealedCount() + element.getErrorInformation() + element.getAgeTooSmallCount() + element.getNewCreatedCount()
                + element.getNoWillingCount() + element.getScheduledCount();
            element.setTotalCount(totalCount);

            Double finishRate = (new Double(totalCount) - element.getNewCreatedCount()) * 100 / totalCount;

            BigDecimal finishRateDecimal = new BigDecimal(finishRate);
            BigDecimal roundedDecimal = finishRateDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
            element.setFinishRate(roundedDecimal.toString() + "%");

        });

        return elements;
    }
}
