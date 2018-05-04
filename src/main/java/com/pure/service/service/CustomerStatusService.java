package com.pure.service.service;

import com.pure.service.domain.CustomerStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing CustomerStatus.
 */
public interface CustomerStatusService {

    /**
     * Save a customerStatus.
     *
     * @param customerStatus the entity to save
     * @return the persisted entity
     */
    CustomerStatus save(CustomerStatus customerStatus);

    /**
     *  Get all the customerStatuses.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<CustomerStatus> findAll(Pageable pageable);

    /**
     *  Get the "id" customerStatus.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    CustomerStatus findOne(Long id);

    /**
     *  Delete the "id" customerStatus.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
