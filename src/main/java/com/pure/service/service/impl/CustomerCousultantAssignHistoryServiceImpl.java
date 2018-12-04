package com.pure.service.service.impl;

import com.pure.service.service.CustomerCousultantAssignHistoryService;
import com.pure.service.domain.CustomerCousultantAssignHistory;
import com.pure.service.repository.CustomerCousultantAssignHistoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing CustomerCousultantAssignHistory.
 */
@Service
@Transactional
public class CustomerCousultantAssignHistoryServiceImpl implements CustomerCousultantAssignHistoryService{

    private final Logger log = LoggerFactory.getLogger(CustomerCousultantAssignHistoryServiceImpl.class);

    private final CustomerCousultantAssignHistoryRepository customerCousultantAssignHistoryRepository;

    public CustomerCousultantAssignHistoryServiceImpl(CustomerCousultantAssignHistoryRepository customerCousultantAssignHistoryRepository) {
        this.customerCousultantAssignHistoryRepository = customerCousultantAssignHistoryRepository;
    }

    /**
     * Save a customerCousultantAssignHistory.
     *
     * @param customerCousultantAssignHistory the entity to save
     * @return the persisted entity
     */
    @Override
    public CustomerCousultantAssignHistory save(CustomerCousultantAssignHistory customerCousultantAssignHistory) {
        log.debug("Request to save CustomerCousultantAssignHistory : {}", customerCousultantAssignHistory);
        return customerCousultantAssignHistoryRepository.save(customerCousultantAssignHistory);
    }

    /**
     *  Get all the customerCousultantAssignHistories.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CustomerCousultantAssignHistory> findAll(Pageable pageable) {
        log.debug("Request to get all CustomerCousultantAssignHistories");
        return customerCousultantAssignHistoryRepository.findAll(pageable);
    }

    /**
     *  Get one customerCousultantAssignHistory by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public CustomerCousultantAssignHistory findOne(Long id) {
        log.debug("Request to get CustomerCousultantAssignHistory : {}", id);
        return customerCousultantAssignHistoryRepository.findOne(id);
    }

    /**
     *  Delete the  customerCousultantAssignHistory by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CustomerCousultantAssignHistory : {}", id);
        customerCousultantAssignHistoryRepository.delete(id);
    }
}
