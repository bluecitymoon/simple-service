package com.pure.service.service;

import com.pure.service.domain.CustomerCousultantAssignHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing CustomerCousultantAssignHistory.
 */
public interface CustomerCousultantAssignHistoryService {

    /**
     * Save a customerCousultantAssignHistory.
     *
     * @param customerCousultantAssignHistory the entity to save
     * @return the persisted entity
     */
    CustomerCousultantAssignHistory save(CustomerCousultantAssignHistory customerCousultantAssignHistory);

    /**
     *  Get all the customerCousultantAssignHistories.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<CustomerCousultantAssignHistory> findAll(Pageable pageable);

    /**
     *  Get the "id" customerCousultantAssignHistory.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    CustomerCousultantAssignHistory findOne(Long id);

    /**
     *  Delete the "id" customerCousultantAssignHistory.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
