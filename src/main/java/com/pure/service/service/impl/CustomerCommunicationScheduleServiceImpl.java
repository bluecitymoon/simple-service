package com.pure.service.service.impl;

import com.pure.service.domain.CustomerCommunicationLog;
import com.pure.service.domain.CustomerCommunicationLogType;
import com.pure.service.domain.CustomerCommunicationSchedule;
import com.pure.service.repository.CustomerCommunicationLogRepository;
import com.pure.service.repository.CustomerCommunicationLogTypeRepository;
import com.pure.service.repository.CustomerCommunicationScheduleRepository;
import com.pure.service.service.CustomerCommunicationScheduleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;


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

    public CustomerCommunicationScheduleServiceImpl(CustomerCommunicationScheduleRepository customerCommunicationScheduleRepository,
                                                    CustomerCommunicationLogRepository customerCommunicationLogRepository,
                                                    CustomerCommunicationLogTypeRepository customerCommunicationLogTypeRepository) {
        this.customerCommunicationScheduleRepository = customerCommunicationScheduleRepository;
        this.customerCommunicationLogRepository = customerCommunicationLogRepository;
        this.customerCommunicationLogTypeRepository = customerCommunicationLogTypeRepository;
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
        CustomerCommunicationSchedule savedSchedule = customerCommunicationScheduleRepository.saveAndFlush(customerCommunicationSchedule);

        CustomerCommunicationLogType newCreateOrderType = customerCommunicationLogTypeRepository.findByCode(CustomerCommunicationLogTypeEnum.schedule.name());

        if (newCreateOrderType == null) {
            log.error(CustomerCommunicationLogTypeEnum.schedule.name() + " not exists in the log type configuration.");
        }

        CustomerCommunicationLog customerCommunicationLog = new CustomerCommunicationLog();
        if (savedSchedule.getSceduleDate() != null) {
            customerCommunicationLog.comments("预约了客户在 <b>" + savedSchedule.getSceduleDate().toString() + "</b> 沟通。");

        }

        customerCommunicationLog.setLogType(newCreateOrderType);
        customerCommunicationLog.customer(savedSchedule.getCustomer());

        CustomerCommunicationLog savedLog = customerCommunicationLogRepository.save(customerCommunicationLog);

        log.debug("Saved customer log for the new order created {}", savedLog);


        return savedSchedule;
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
        schedule = schedule.actuallMeetDate(Instant.now());

        CustomerCommunicationSchedule customerCommunicationSchedule = save(schedule);

        CustomerCommunicationLogType newCreateOrderType = customerCommunicationLogTypeRepository.findByCode(CustomerCommunicationLogTypeEnum.signin.name());

        if (newCreateOrderType == null) {
            log.error(CustomerCommunicationLogTypeEnum.signin.name() + " not exists in the log type configuration.");
        }

        CustomerCommunicationLog customerCommunicationLog = new CustomerCommunicationLog();
        customerCommunicationLog.comments("签到成功");

        customerCommunicationLog.setLogType(newCreateOrderType);
        customerCommunicationLog.customer(schedule.getCustomer());

        customerCommunicationLogRepository.save(customerCommunicationLog);

        return customerCommunicationSchedule;
    }
}
