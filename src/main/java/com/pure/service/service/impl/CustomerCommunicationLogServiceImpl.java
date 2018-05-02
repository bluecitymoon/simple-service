package com.pure.service.service.impl;

import com.pure.service.service.CustomerCommunicationLogService;
import com.pure.service.domain.CustomerCommunicationLog;
import com.pure.service.repository.CustomerCommunicationLogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing CustomerCommunicationLog.
 */
@Service
@Transactional
public class CustomerCommunicationLogServiceImpl implements CustomerCommunicationLogService{

    private final Logger log = LoggerFactory.getLogger(CustomerCommunicationLogServiceImpl.class);

    private final CustomerCommunicationLogRepository customerCommunicationLogRepository;

    public CustomerCommunicationLogServiceImpl(CustomerCommunicationLogRepository customerCommunicationLogRepository) {
        this.customerCommunicationLogRepository = customerCommunicationLogRepository;
    }

    /**
     * Save a customerCommunicationLog.
     *
     * @param customerCommunicationLog the entity to save
     * @return the persisted entity
     */
    @Override
    public CustomerCommunicationLog save(CustomerCommunicationLog customerCommunicationLog) {
        log.debug("Request to save CustomerCommunicationLog : {}", customerCommunicationLog);
        return customerCommunicationLogRepository.save(customerCommunicationLog);
    }

    /**
     *  Get all the customerCommunicationLogs.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CustomerCommunicationLog> findAll(Pageable pageable) {
        log.debug("Request to get all CustomerCommunicationLogs");
        return customerCommunicationLogRepository.findAll(pageable);
    }

    /**
     *  Get one customerCommunicationLog by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public CustomerCommunicationLog findOne(Long id) {
        log.debug("Request to get CustomerCommunicationLog : {}", id);
        return customerCommunicationLogRepository.findOne(id);
    }

    /**
     *  Delete the  customerCommunicationLog by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CustomerCommunicationLog : {}", id);
        customerCommunicationLogRepository.delete(id);
    }
}
