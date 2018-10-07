package com.pure.service.service.impl;

import com.pure.service.service.CustomerConsumerLogService;
import com.pure.service.domain.CustomerConsumerLog;
import com.pure.service.repository.CustomerConsumerLogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing CustomerConsumerLog.
 */
@Service
@Transactional
public class CustomerConsumerLogServiceImpl implements CustomerConsumerLogService{

    private final Logger log = LoggerFactory.getLogger(CustomerConsumerLogServiceImpl.class);

    private final CustomerConsumerLogRepository customerConsumerLogRepository;

    public CustomerConsumerLogServiceImpl(CustomerConsumerLogRepository customerConsumerLogRepository) {
        this.customerConsumerLogRepository = customerConsumerLogRepository;
    }

    /**
     * Save a customerConsumerLog.
     *
     * @param customerConsumerLog the entity to save
     * @return the persisted entity
     */
    @Override
    public CustomerConsumerLog save(CustomerConsumerLog customerConsumerLog) {
        log.debug("Request to save CustomerConsumerLog : {}", customerConsumerLog);
        return customerConsumerLogRepository.save(customerConsumerLog);
    }

    /**
     *  Get all the customerConsumerLogs.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CustomerConsumerLog> findAll(Pageable pageable) {
        log.debug("Request to get all CustomerConsumerLogs");
        return customerConsumerLogRepository.findAll(pageable);
    }

    /**
     *  Get one customerConsumerLog by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public CustomerConsumerLog findOne(Long id) {
        log.debug("Request to get CustomerConsumerLog : {}", id);
        return customerConsumerLogRepository.findOne(id);
    }

    /**
     *  Delete the  customerConsumerLog by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CustomerConsumerLog : {}", id);
        customerConsumerLogRepository.delete(id);
    }
}
