package com.pure.service.service.impl;

import com.pure.service.domain.Customer;
import com.pure.service.domain.CustomerCommunicationLog;
import com.pure.service.domain.CustomerCommunicationLogType;
import com.pure.service.domain.CustomerCommunicationSchedule;
import com.pure.service.domain.CustomerScheduleStatus;
import com.pure.service.domain.CustomerStatus;
import com.pure.service.repository.CustomerCommunicationLogRepository;
import com.pure.service.repository.CustomerCommunicationLogTypeRepository;
import com.pure.service.repository.CustomerCommunicationScheduleRepository;
import com.pure.service.repository.CustomerRepository;
import com.pure.service.repository.CustomerScheduleStatusRepository;
import com.pure.service.repository.CustomerStatusRepository;
import com.pure.service.service.CustomerCommunicationScheduleQueryService;
import com.pure.service.service.CustomerCommunicationScheduleService;
import com.pure.service.service.CustomerService;
import com.pure.service.service.dto.CustomerCommunicationScheduleCriteria;
import io.github.jhipster.service.filter.LongFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.Locale;


/**
 * Service Implementation for managing CustomerCommunicationSchedule.
 */
@Service
@Transactional
public class CustomerCommunicationScheduleServiceImpl implements CustomerCommunicationScheduleService{

    private final Logger log = LoggerFactory.getLogger(CustomerCommunicationScheduleServiceImpl.class);

    private final CustomerCommunicationScheduleRepository customerCommunicationScheduleRepository;
    private final CustomerCommunicationLogRepository customerCommunicationLogRepository;
    private final CustomerCommunicationLogTypeRepository customerCommunicationLogTypeRepository;
    private final CustomerRepository customerRepository;

    @Autowired
    private CustomerCommunicationScheduleQueryService customerCommunicationScheduleQueryService;

    @Autowired
    private CustomerScheduleStatusRepository customerScheduleStatusRepository;

    @Autowired
    private CustomerStatusRepository customerStatusRepository;

    @Autowired
    private CustomerService customerService;

    public CustomerCommunicationScheduleServiceImpl(CustomerCommunicationScheduleRepository customerCommunicationScheduleRepository,
                                                    CustomerCommunicationLogRepository customerCommunicationLogRepository,
                                                    CustomerCommunicationLogTypeRepository customerCommunicationLogTypeRepository,
                                                    CustomerRepository customerRepository) {
        this.customerCommunicationScheduleRepository = customerCommunicationScheduleRepository;
        this.customerCommunicationLogRepository = customerCommunicationLogRepository;
        this.customerCommunicationLogTypeRepository = customerCommunicationLogTypeRepository;
        this.customerRepository = customerRepository;
    }

    /**
     * Save a customerCommunicationSchedule.
     *
     * @param customerCommunicationSchedule the entity to save
     * @return the persisted entity
     */
    @Override
    public CustomerCommunicationSchedule save(CustomerCommunicationSchedule customerCommunicationSchedule) {
        log.debug("Request to save CustomerCommunicationSchedule : {}", customerCommunicationSchedule);

        if (customerCommunicationSchedule.getId() == null) {
            customerService.updateCustomerStatusForNewSchedule(customerCommunicationSchedule);
        }
        CustomerCommunicationSchedule savedSchedule = customerCommunicationScheduleRepository.saveAndFlush(customerCommunicationSchedule);

        CustomerCommunicationLogType newCreateOrderType = customerCommunicationLogTypeRepository.findByCode(CustomerCommunicationLogTypeEnum.schedule.name());

        if (newCreateOrderType == null) {
            log.error(CustomerCommunicationLogTypeEnum.schedule.name() + " not exists in the log type configuration.");
        }


        CustomerCommunicationLog customerCommunicationLog = new CustomerCommunicationLog();
        if (savedSchedule.getSceduleDate() != null) {

            String scheduleFormateString = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG)
                .withLocale( Locale.CHINA )
                .withZone( ZoneId.systemDefault())
                .format(savedSchedule.getSceduleDate());
//            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//            String scheduleFormateString = simpleDateFormat.format(savedSchedule.getSceduleDate());

            customerCommunicationLog.comments("预约了客户沟通，时间: " + scheduleFormateString);
        }

        customerCommunicationLog.setLogType(newCreateOrderType);
        customerCommunicationLog.customer(savedSchedule.getCustomer());

        CustomerCommunicationLog savedLog = customerCommunicationLogRepository.save(customerCommunicationLog);

        log.debug("Saved customer log for the new order created {}", savedLog);


        return savedSchedule;
    }

    @Override
    public List<CustomerCommunicationSchedule> batchUpdate(List<CustomerCommunicationSchedule> customerCommunicationSchedules) {

        customerCommunicationSchedules.forEach(customerCommunicationSchedule -> customerRepository.save(customerCommunicationSchedule.getCustomer()));

        return customerCommunicationSchedules;
    }

    /**
     *  Get all the customerCommunicationSchedules.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CustomerCommunicationSchedule> findAll(Pageable pageable) {
        log.debug("Request to get all CustomerCommunicationSchedules");
        return customerCommunicationScheduleRepository.findAll(pageable);
    }

    /**
     *  Get one customerCommunicationSchedule by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public CustomerCommunicationSchedule findOne(Long id) {
        log.debug("Request to get CustomerCommunicationSchedule : {}", id);
        return customerCommunicationScheduleRepository.findOne(id);
    }

    /**
     *  Delete the  customerCommunicationSchedule by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CustomerCommunicationSchedule : {}", id);
        customerCommunicationScheduleRepository.delete(id);
    }

    @Override
    public CustomerCommunicationSchedule signin(Long id) {

        CustomerCommunicationSchedule schedule = findOne(id);

        return signSingleSchedule(schedule);
    }

    public CustomerCommunicationSchedule signSingleSchedule(CustomerCommunicationSchedule schedule) {

        schedule = schedule.actuallMeetDate(Instant.now());

        CustomerScheduleStatus signedSuccessStatus = customerScheduleStatusRepository.findByCode("signin_success");
        schedule.setScheduleStatus(signedSuccessStatus);

        CustomerCommunicationLogType newCreateOrderType = customerCommunicationLogTypeRepository.findByCode(CustomerCommunicationLogTypeEnum.signin.name());

        if (newCreateOrderType == null) {
            log.error(CustomerCommunicationLogTypeEnum.signin.name() + " not exists in the log type configuration.");
        }

        CustomerCommunicationLog customerCommunicationLog = new CustomerCommunicationLog();
        customerCommunicationLog.comments("客户到店签到");

        customerCommunicationLog.setLogType(newCreateOrderType);
        customerCommunicationLog.customer(schedule.getCustomer());

        customerCommunicationLogRepository.save(customerCommunicationLog);

        CustomerCommunicationSchedule saveSchedule = save(schedule);

        Customer customer = saveSchedule.getCustomer();
        CustomerStatus successCheckedStatus = customerStatusRepository.findByCode("visited");
        customer.setStatus(successCheckedStatus);

        customer.setVisitDate(Instant.now());

        customerService.save(customer);

        return saveSchedule;
    }

    @Override
    public CustomerCommunicationSchedule customerSignin(Long id) {

        CustomerCommunicationScheduleCriteria customerCommunicationScheduleCriteria = new CustomerCommunicationScheduleCriteria();

        LongFilter customerFilter = new LongFilter();
        customerFilter.setEquals(id);

        customerCommunicationScheduleCriteria.setCustomerId(customerFilter);

        boolean hasUnCheckedSchedule = false;
        List<CustomerCommunicationSchedule> customerCommunicationSchedules = customerCommunicationScheduleQueryService.findByCriteria(customerCommunicationScheduleCriteria);

        //TODO eating shit requirement
        for (CustomerCommunicationSchedule schedule : customerCommunicationSchedules) {
            if (schedule.getActuallMeetDate() != null) {
                throw new RuntimeException("该客户无需再签到");
            }
        }
        for (CustomerCommunicationSchedule customerCommunicationSchedule : customerCommunicationSchedules) {
            if (customerCommunicationSchedule.getActuallMeetDate() == null) {

                signSingleSchedule(customerCommunicationSchedule);

                hasUnCheckedSchedule = true;
            }
        }

        //没有预约数据的，新增预约记录并签到
        if (!hasUnCheckedSchedule) {

            CustomerCommunicationSchedule schedule = new CustomerCommunicationSchedule();

            Customer customer = customerRepository.findOne(id);
            schedule.setCustomer(customer);

            schedule.setComments("客户未预约，系统自动生成的预约记录");

            signSingleSchedule(schedule);

        }
        return null;
    }
}
