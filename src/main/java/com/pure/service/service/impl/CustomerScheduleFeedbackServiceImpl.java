package com.pure.service.service.impl;

import com.pure.service.service.CustomerScheduleFeedbackService;
import com.pure.service.domain.CustomerScheduleFeedback;
import com.pure.service.repository.CustomerScheduleFeedbackRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing CustomerScheduleFeedback.
 */
@Service
@Transactional
public class CustomerScheduleFeedbackServiceImpl implements CustomerScheduleFeedbackService{

    private final Logger log = LoggerFactory.getLogger(CustomerScheduleFeedbackServiceImpl.class);

    private final CustomerScheduleFeedbackRepository customerScheduleFeedbackRepository;

    public CustomerScheduleFeedbackServiceImpl(CustomerScheduleFeedbackRepository customerScheduleFeedbackRepository) {
        this.customerScheduleFeedbackRepository = customerScheduleFeedbackRepository;
    }

    /**
     * Save a customerScheduleFeedback.
     *
     * @param customerScheduleFeedback the entity to save
     * @return the persisted entity
     */
    @Override
    public CustomerScheduleFeedback save(CustomerScheduleFeedback customerScheduleFeedback) {
        log.debug("Request to save CustomerScheduleFeedback : {}", customerScheduleFeedback);
        return customerScheduleFeedbackRepository.save(customerScheduleFeedback);
    }

    /**
     *  Get all the customerScheduleFeedbacks.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CustomerScheduleFeedback> findAll(Pageable pageable) {
        log.debug("Request to get all CustomerScheduleFeedbacks");
        return customerScheduleFeedbackRepository.findAll(pageable);
    }

    /**
     *  Get one customerScheduleFeedback by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public CustomerScheduleFeedback findOne(Long id) {
        log.debug("Request to get CustomerScheduleFeedback : {}", id);
        return customerScheduleFeedbackRepository.findOne(id);
    }

    /**
     *  Delete the  customerScheduleFeedback by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CustomerScheduleFeedback : {}", id);
        customerScheduleFeedbackRepository.delete(id);
    }
}
