package com.pure.service.service.impl;

import com.pure.service.service.CustomerCollectionLogService;
import com.pure.service.domain.CustomerCollectionLog;
import com.pure.service.repository.CustomerCollectionLogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * Service Implementation for managing CustomerCollectionLog.
 */
@Service
@Transactional
public class CustomerCollectionLogServiceImpl implements CustomerCollectionLogService{

    private final Logger log = LoggerFactory.getLogger(CustomerCollectionLogServiceImpl.class);

    private final CustomerCollectionLogRepository customerCollectionLogRepository;

    public CustomerCollectionLogServiceImpl(CustomerCollectionLogRepository customerCollectionLogRepository) {
        this.customerCollectionLogRepository = customerCollectionLogRepository;
    }

    /**
     * Save a customerCollectionLog.
     *
     * @param customerCollectionLog the entity to save
     * @return the persisted entity
     */
    @Override
    public CustomerCollectionLog save(CustomerCollectionLog customerCollectionLog) {
        log.debug("Request to save CustomerCollectionLog : {}", customerCollectionLog);
        return customerCollectionLogRepository.save(customerCollectionLog);
    }

    /**
     *  Get all the customerCollectionLogs.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CustomerCollectionLog> findAll(Pageable pageable) {
        log.debug("Request to get all CustomerCollectionLogs");
        return customerCollectionLogRepository.findAll(pageable);
    }

    /**
     *  Get one customerCollectionLog by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public CustomerCollectionLog findOne(Long id) {
        log.debug("Request to get CustomerCollectionLog : {}", id);
        return customerCollectionLogRepository.findOne(id);
    }

    /**
     *  Delete the  customerCollectionLog by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CustomerCollectionLog : {}", id);
        customerCollectionLogRepository.delete(id);
    }

    @Override
    public void fixRegionId() {

        List<CustomerCollectionLog> allLogs = customerCollectionLogRepository.findAll();

        for (CustomerCollectionLog customerCollectionLog : allLogs) {
            customerCollectionLog.setRegionId(customerCollectionLog.getCustomer().getRegionId());
            customerCollectionLog.setMoneyCollected(customerCollectionLog.getMoneyShouldCollected());
        }

        customerCollectionLogRepository.save(allLogs);
    }
}
