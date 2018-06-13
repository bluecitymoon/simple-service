package com.pure.service.service.impl;

import com.pure.service.service.CustomerTrackTaskService;
import com.pure.service.domain.CustomerTrackTask;
import com.pure.service.repository.CustomerTrackTaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing CustomerTrackTask.
 */
@Service
@Transactional
public class CustomerTrackTaskServiceImpl implements CustomerTrackTaskService{

    private final Logger log = LoggerFactory.getLogger(CustomerTrackTaskServiceImpl.class);

    private final CustomerTrackTaskRepository customerTrackTaskRepository;

    public CustomerTrackTaskServiceImpl(CustomerTrackTaskRepository customerTrackTaskRepository) {
        this.customerTrackTaskRepository = customerTrackTaskRepository;
    }

    /**
     * Save a customerTrackTask.
     *
     * @param customerTrackTask the entity to save
     * @return the persisted entity
     */
    @Override
    public CustomerTrackTask save(CustomerTrackTask customerTrackTask) {
        log.debug("Request to save CustomerTrackTask : {}", customerTrackTask);
        return customerTrackTaskRepository.save(customerTrackTask);
    }

    /**
     *  Get all the customerTrackTasks.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CustomerTrackTask> findAll(Pageable pageable) {
        log.debug("Request to get all CustomerTrackTasks");
        return customerTrackTaskRepository.findAll(pageable);
    }

    /**
     *  Get one customerTrackTask by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public CustomerTrackTask findOne(Long id) {
        log.debug("Request to get CustomerTrackTask : {}", id);
        return customerTrackTaskRepository.findOne(id);
    }

    /**
     *  Delete the  customerTrackTask by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CustomerTrackTask : {}", id);
        customerTrackTaskRepository.delete(id);
    }
}
