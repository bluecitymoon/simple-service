package com.pure.service.service;

import com.pure.service.domain.CustomerScheduleStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing CustomerScheduleStatus.
 */
public interface CustomerScheduleStatusService {

    /**
     * Save a customerScheduleStatus.
     *
     * @param customerScheduleStatus the entity to save
     * @return the persisted entity
     */
    CustomerScheduleStatus save(CustomerScheduleStatus customerScheduleStatus);

    /**
     *  Get all the customerScheduleStatuses.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<CustomerScheduleStatus> findAll(Pageable pageable);

    /**
     *  Get the "id" customerScheduleStatus.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    CustomerScheduleStatus findOne(Long id);

    /**
     *  Delete the "id" customerScheduleStatus.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
