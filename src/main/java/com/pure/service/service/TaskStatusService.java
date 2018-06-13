package com.pure.service.service;

import com.pure.service.domain.TaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing TaskStatus.
 */
public interface TaskStatusService {

    /**
     * Save a taskStatus.
     *
     * @param taskStatus the entity to save
     * @return the persisted entity
     */
    TaskStatus save(TaskStatus taskStatus);

    /**
     *  Get all the taskStatuses.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<TaskStatus> findAll(Pageable pageable);

    /**
     *  Get the "id" taskStatus.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    TaskStatus findOne(Long id);

    /**
     *  Delete the "id" taskStatus.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
