package com.pure.service.service;

import com.pure.service.domain.VistedCustomerStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing VistedCustomerStatus.
 */
public interface VistedCustomerStatusService {

    /**
     * Save a vistedCustomerStatus.
     *
     * @param vistedCustomerStatus the entity to save
     * @return the persisted entity
     */
    VistedCustomerStatus save(VistedCustomerStatus vistedCustomerStatus);

    /**
     *  Get all the vistedCustomerStatuses.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<VistedCustomerStatus> findAll(Pageable pageable);

    /**
     *  Get the "id" vistedCustomerStatus.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    VistedCustomerStatus findOne(Long id);

    /**
     *  Delete the "id" vistedCustomerStatus.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
