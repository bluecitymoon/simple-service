package com.pure.service.service.impl;

import com.pure.service.service.CustomerCardUpgradeLogService;
import com.pure.service.domain.CustomerCardUpgradeLog;
import com.pure.service.repository.CustomerCardUpgradeLogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing CustomerCardUpgradeLog.
 */
@Service
@Transactional
public class CustomerCardUpgradeLogServiceImpl implements CustomerCardUpgradeLogService{

    private final Logger log = LoggerFactory.getLogger(CustomerCardUpgradeLogServiceImpl.class);

    private final CustomerCardUpgradeLogRepository customerCardUpgradeLogRepository;

    public CustomerCardUpgradeLogServiceImpl(CustomerCardUpgradeLogRepository customerCardUpgradeLogRepository) {
        this.customerCardUpgradeLogRepository = customerCardUpgradeLogRepository;
    }

    /**
     * Save a customerCardUpgradeLog.
     *
     * @param customerCardUpgradeLog the entity to save
     * @return the persisted entity
     */
    @Override
    public CustomerCardUpgradeLog save(CustomerCardUpgradeLog customerCardUpgradeLog) {
        log.debug("Request to save CustomerCardUpgradeLog : {}", customerCardUpgradeLog);
        return customerCardUpgradeLogRepository.save(customerCardUpgradeLog);
    }

    /**
     *  Get all the customerCardUpgradeLogs.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CustomerCardUpgradeLog> findAll(Pageable pageable) {
        log.debug("Request to get all CustomerCardUpgradeLogs");
        return customerCardUpgradeLogRepository.findAll(pageable);
    }

    /**
     *  Get one customerCardUpgradeLog by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public CustomerCardUpgradeLog findOne(Long id) {
        log.debug("Request to get CustomerCardUpgradeLog : {}", id);
        return customerCardUpgradeLogRepository.findOne(id);
    }

    /**
     *  Delete the  customerCardUpgradeLog by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CustomerCardUpgradeLog : {}", id);
        customerCardUpgradeLogRepository.delete(id);
    }
}
