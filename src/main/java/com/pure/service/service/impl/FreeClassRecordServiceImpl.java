package com.pure.service.service.impl;

import com.pure.service.domain.Customer;
import com.pure.service.domain.CustomerCommunicationLog;
import com.pure.service.domain.CustomerCommunicationLogType;
import com.pure.service.domain.FreeClassRecord;
import com.pure.service.domain.NewOrderAssignHistory;
import com.pure.service.domain.User;
import com.pure.service.repository.CustomerCommunicationLogRepository;
import com.pure.service.repository.CustomerCommunicationLogTypeRepository;
import com.pure.service.repository.FreeClassRecordRepository;
import com.pure.service.repository.NewOrderAssignHistoryRepository;
import com.pure.service.repository.UserRepository;
import com.pure.service.service.CustomerCommunicationScheduleService;
import com.pure.service.service.CustomerQueryService;
import com.pure.service.service.CustomerService;
import com.pure.service.service.FreeClassRecordService;
import com.pure.service.service.dto.BatchCustomers;
import com.pure.service.service.dto.BatchCustomersResponse;
import com.pure.service.service.dto.CustomerCriteria;
import com.pure.service.service.util.BatchCustomerUtil;
import io.github.jhipster.service.filter.LongFilter;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


/**
 * Service Implementation for managing FreeClassRecord.
 */
@Service
@Transactional
public class FreeClassRecordServiceImpl implements FreeClassRecordService{

    private final Logger log = LoggerFactory.getLogger(FreeClassRecordServiceImpl.class);

    private final FreeClassRecordRepository freeClassRecordRepository;
    private final NewOrderAssignHistoryRepository newOrderAssignHistoryRepository;
    private final CustomerCommunicationLogRepository customerCommunicationLogRepository;
    private final CustomerCommunicationLogTypeRepository customerCommunicationLogTypeRepository;
    private final UserRepository userRepository;
    private final CustomerCommunicationScheduleService scheduleService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerQueryService customerQueryService;

    @Autowired
    private BatchCustomerUtil batchCustomerUtil;

    public FreeClassRecordServiceImpl(FreeClassRecordRepository freeClassRecordRepository,
                                      NewOrderAssignHistoryRepository newOrderAssignHistoryRepository,
                                      CustomerCommunicationLogRepository customerCommunicationLogRepository,
                                      CustomerCommunicationLogTypeRepository customerCommunicationLogTypeRepository,
                                      UserRepository userRepository,
                                      CustomerCommunicationScheduleService scheduleService) {
        this.freeClassRecordRepository = freeClassRecordRepository;
        this.newOrderAssignHistoryRepository = newOrderAssignHistoryRepository;
        this.customerCommunicationLogRepository = customerCommunicationLogRepository;
        this.customerCommunicationLogTypeRepository = customerCommunicationLogTypeRepository;
        this.userRepository = userRepository;
        this.scheduleService = scheduleService;
    }

    /**
     * Save a freeClassRecord.
     *
     * @param freeClassRecord the entity to save
     * @return the persisted entity
     */
    @Override
    public FreeClassRecord save(FreeClassRecord freeClassRecord) {
        log.debug("Request to save FreeClassRecord : {}", freeClassRecord);

        if (freeClassRecord.getId() == null){
            freeClassRecord.setStatus("新单");
        }

        Long agentId = freeClassRecord.getAgentId();
        if (agentId != null) {
            User referer = userRepository.findOne(agentId);
            freeClassRecord.setReferer(referer);
        }

        boolean newOrder = false;
        if (freeClassRecord.getId() != null) {

            FreeClassRecord oldFreeClassRecord = freeClassRecordRepository.findOne(freeClassRecord.getId());

            String olderFollowerLogin = oldFreeClassRecord.getSalesFollower() == null? "" : oldFreeClassRecord.getSalesFollower().getLogin();
            String olderFollowerName = oldFreeClassRecord.getSalesFollower() == null? "" : oldFreeClassRecord.getSalesFollower().getFirstName();
            String newFollowerLogin = freeClassRecord.getSalesFollower() == null? "" : freeClassRecord.getSalesFollower().getLogin();
            String newFollowerName = freeClassRecord.getSalesFollower() == null? "": freeClassRecord.getSalesFollower().getFirstName();

            if (!olderFollowerLogin.equals(newFollowerLogin)) {

                NewOrderAssignHistory newOrderAssignHistory = new NewOrderAssignHistory();

                newOrderAssignHistory = newOrderAssignHistory.newFollowerName(newFollowerName)
                    .newFollowerLogin(newFollowerLogin)
                    .newFollowerName(newFollowerName)
                    .olderFollowerLogin(olderFollowerLogin)
                    .olderFollowerName(olderFollowerName)
                    .newOrder(freeClassRecord);

                newOrderAssignHistoryRepository.save(newOrderAssignHistory);
            }

        } else {

            newOrder = true;
        }

        log.debug("Operation on new order ? " + newOrder);

        FreeClassRecord saved = freeClassRecordRepository.saveAndFlush(freeClassRecord);

        log.debug("Saved new order {} " + saved );
        //Save log for new order created.
        if (newOrder) {

            CustomerCommunicationLogType newCreateOrderType = customerCommunicationLogTypeRepository.findByCode(CustomerCommunicationLogTypeEnum.new_order_created.name());
            CustomerCommunicationLog customerCommunicationLog = new CustomerCommunicationLog();
            customerCommunicationLog.comments("回单记录创建成功");

            customerCommunicationLog.setLogType(newCreateOrderType);
            customerCommunicationLog.freeClassRecord(saved);

            CustomerCommunicationLog savedLog = customerCommunicationLogRepository.save(customerCommunicationLog);

            log.debug("Saved customer log for the new order created {}", savedLog);

            //save the default customer for new order.
            Customer newCustomer = customerService.importCustomerFromNewOrder(saved.getId());

            log.debug("Default customer saved for the new order, customer is {} ", newCustomer);
            //create default schedule for the new customer so that the recipient could check.
//            CustomerCommunicationSchedule schedule = new CustomerCommunicationSchedule();
//
//            schedule.setCustomer(newCustomer);
//            schedule.setFollower(newCustomer.getSalesFollower());
//
//            log.debug("Default schedule saved for the new order, schedule is {} ", schedule);
//
//            scheduleService.save(schedule);

        }

        return saved;
    }

    /**
     *  Get all the freeClassRecords.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<FreeClassRecord> findAll(Pageable pageable) {
        log.debug("Request to get all FreeClassRecords");
        return freeClassRecordRepository.findAll(pageable);
    }

    /**
     *  Get one freeClassRecord by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public FreeClassRecord findOne(Long id) {
        log.debug("Request to get FreeClassRecord : {}", id);
        return freeClassRecordRepository.findOneWithEagerRelationships(id);
    }

    /**
     *  Delete the  freeClassRecord by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete FreeClassRecord : {}", id);
        freeClassRecordRepository.delete(id);
    }

    @Override
    public BatchCustomersResponse upload(BatchCustomers customers) {
        String content = customers.getContent();

        List<FreeClassRecord> freeClassRecords = batchCustomerUtil.batchAnalysisCustomer(content);

        StringBuilder logBuilder = new StringBuilder();
        int importCount = 0, existedCount = 0;
        for (FreeClassRecord freeClassRecord : freeClassRecords) {

            FreeClassRecord existedNewOrder = freeClassRecordRepository.findByPersonNameAndContactPhoneNumber(freeClassRecord.getPersonName(), freeClassRecord.getContactPhoneNumber());
            if (existedNewOrder == null) {
                save(freeClassRecord);

                importCount ++;
            } else {
                existedCount ++;
            }
        }

        logBuilder.append("成功导入数据" + importCount + "条，" + " 因为已存在而跳过数据" + existedCount + "条");

        BatchCustomersResponse response = new BatchCustomersResponse();
        response.setSuccess(true);
        response.setMessage(logBuilder.toString());

        return response;
    }

    @Override
    public BatchCustomersResponse upload(InputStream inputStream, String fileName) {
        Workbook workbook = null;

        try {
            if (fileName.endsWith(".xls")) {
                workbook = new XSSFWorkbook(inputStream);
            } else if (fileName.endsWith(".xlsx")) {
//                workbook = new SXSSFWorkbook(inputStream);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<FreeClassRecord> batchSave(List<FreeClassRecord> freeClassRecords) {

        List<FreeClassRecord> records = new ArrayList<>();

        freeClassRecords.forEach(newOrder -> {
            FreeClassRecord freeClassRecord = save(newOrder);

            records.add(freeClassRecord);

            CustomerCriteria customerCriteria = new CustomerCriteria();
            LongFilter longFilter = new LongFilter();
            longFilter.setEquals(freeClassRecord.getId());
            customerCriteria.setNewOrderId(longFilter);

            List<Customer> existCustomers = customerQueryService.findByCriteria(customerCriteria);

            if (!CollectionUtils.isEmpty(existCustomers)) {

                for (Customer existCustomer : existCustomers) {
                    existCustomer.setSalesFollower(freeClassRecord.getSalesFollower());
                    customerService.save(existCustomer);
                }
            }
        });

        return freeClassRecords;
    }
}
