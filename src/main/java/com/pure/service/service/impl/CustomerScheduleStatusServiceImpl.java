package com.pure.service.service.impl;

import com.pure.service.service.CustomerScheduleStatusService;
import com.pure.service.domain.CustomerScheduleStatus;
import com.pure.service.repository.CustomerScheduleStatusRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing CustomerScheduleStatus.
 */
@Service
@Transactional
public class CustomerScheduleStatusServiceImpl implements CustomerScheduleStatusService{

    private final Logger log = LoggerFactory.getLogger(CustomerScheduleStatusServiceImpl.class);

    private final CustomerScheduleStatusRepository customerScheduleStatusRepository;

    public CustomerScheduleStatusServiceImpl(CustomerScheduleStatusRepository customerScheduleStatusRepository) {
        this.customerScheduleStatusRepository = customerScheduleStatusRepository;
    }

    /**
     * Save a customerScheduleStatus.
     *
     * @param customerScheduleStatus the entity to save
     * @return the persisted entity
     */
    @Override
    public CustomerScheduleStatus save(CustomerScheduleStatus customerScheduleStatus) {
        log.debug("Request to save CustomerScheduleStatus : {}", customerScheduleStatus);
        return customerScheduleStatusRepository.save(customerScheduleStatus);
    }

    /**
     *  Get all the customerScheduleStatuses.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CustomerScheduleStatus> findAll(Pageable pageable) {
        log.debug("Request to get all CustomerScheduleStatuses");
        return customerScheduleStatusRepository.findAll(pageable);
    }

    /**
     *  Get one customerScheduleStatus by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public CustomerScheduleStatus findOne(Long id) {
        log.debug("Request to get CustomerScheduleStatus : {}", id);
        return customerScheduleStatusRepository.findOne(id);
    }

    /**
     *  Delete the  customerScheduleStatus by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CustomerScheduleStatus : {}", id);
        customerScheduleStatusRepository.delete(id);
    }
}
