package com.pure.service.service;

import com.pure.service.domain.CustomerScheduleFeedback;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing CustomerScheduleFeedback.
 */
public interface CustomerScheduleFeedbackService {

    /**
     * Save a customerScheduleFeedback.
     *
     * @param customerScheduleFeedback the entity to save
     * @return the persisted entity
     */
    CustomerScheduleFeedback save(CustomerScheduleFeedback customerScheduleFeedback);

    /**
     *  Get all the customerScheduleFeedbacks.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<CustomerScheduleFeedback> findAll(Pageable pageable);

    /**
     *  Get the "id" customerScheduleFeedback.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    CustomerScheduleFeedback findOne(Long id);

    /**
     *  Delete the "id" customerScheduleFeedback.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
