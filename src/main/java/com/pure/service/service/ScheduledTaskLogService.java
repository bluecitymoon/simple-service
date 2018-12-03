package com.pure.service.service;

import com.pure.service.domain.ScheduledTaskLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing ScheduledTaskLog.
 */
public interface ScheduledTaskLogService {

    /**
     * Save a scheduledTaskLog.
     *
     * @param scheduledTaskLog the entity to save
     * @return the persisted entity
     */
    ScheduledTaskLog save(ScheduledTaskLog scheduledTaskLog);

    /**
     *  Get all the scheduledTaskLogs.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ScheduledTaskLog> findAll(Pageable pageable);

    /**
     *  Get the "id" scheduledTaskLog.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ScheduledTaskLog findOne(Long id);

    /**
     *  Delete the "id" scheduledTaskLog.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
