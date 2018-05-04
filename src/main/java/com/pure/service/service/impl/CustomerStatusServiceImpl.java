package com.pure.service.service.impl;

import com.pure.service.service.CustomerStatusService;
import com.pure.service.domain.CustomerStatus;
import com.pure.service.repository.CustomerStatusRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing CustomerStatus.
 */
@Service
@Transactional
public class CustomerStatusServiceImpl implements CustomerStatusService{

    private final Logger log = LoggerFactory.getLogger(CustomerStatusServiceImpl.class);

    private final CustomerStatusRepository customerStatusRepository;

    public CustomerStatusServiceImpl(CustomerStatusRepository customerStatusRepository) {
        this.customerStatusRepository = customerStatusRepository;
    }

    /**
     * Save a customerStatus.
     *
     * @param customerStatus the entity to save
     * @return the persisted entity
     */
    @Override
    public CustomerStatus save(CustomerStatus customerStatus) {
        log.debug("Request to save CustomerStatus : {}", customerStatus);
        return customerStatusRepository.save(customerStatus);
    }

    /**
     *  Get all the customerStatuses.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CustomerStatus> findAll(Pageable pageable) {
        log.debug("Request to get all CustomerStatuses");
        return customerStatusRepository.findAll(pageable);
    }

    /**
     *  Get one customerStatus by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public CustomerStatus findOne(Long id) {
        log.debug("Request to get CustomerStatus : {}", id);
        return customerStatusRepository.findOne(id);
    }

    /**
     *  Delete the  customerStatus by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CustomerStatus : {}", id);
        customerStatusRepository.delete(id);
    }
}
