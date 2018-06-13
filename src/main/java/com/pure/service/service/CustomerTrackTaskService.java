package com.pure.service.service;

import com.pure.service.domain.CustomerTrackTask;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing CustomerTrackTask.
 */
public interface CustomerTrackTaskService {

    /**
     * Save a customerTrackTask.
     *
     * @param customerTrackTask the entity to save
     * @return the persisted entity
     */
    CustomerTrackTask save(CustomerTrackTask customerTrackTask);

    /**
     *  Get all the customerTrackTasks.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<CustomerTrackTask> findAll(Pageable pageable);

    /**
     *  Get the "id" customerTrackTask.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    CustomerTrackTask findOne(Long id);

    /**
     *  Delete the "id" customerTrackTask.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
