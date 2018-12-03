package com.pure.service.service.impl;

import com.pure.service.service.ScheduledTaskLogService;
import com.pure.service.domain.ScheduledTaskLog;
import com.pure.service.repository.ScheduledTaskLogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing ScheduledTaskLog.
 */
@Service
@Transactional
public class ScheduledTaskLogServiceImpl implements ScheduledTaskLogService{

    private final Logger log = LoggerFactory.getLogger(ScheduledTaskLogServiceImpl.class);

    private final ScheduledTaskLogRepository scheduledTaskLogRepository;

    public ScheduledTaskLogServiceImpl(ScheduledTaskLogRepository scheduledTaskLogRepository) {
        this.scheduledTaskLogRepository = scheduledTaskLogRepository;
    }

    /**
     * Save a scheduledTaskLog.
     *
     * @param scheduledTaskLog the entity to save
     * @return the persisted entity
     */
    @Override
    public ScheduledTaskLog save(ScheduledTaskLog scheduledTaskLog) {
        log.debug("Request to save ScheduledTaskLog : {}", scheduledTaskLog);
        return scheduledTaskLogRepository.save(scheduledTaskLog);
    }

    /**
     *  Get all the scheduledTaskLogs.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ScheduledTaskLog> findAll(Pageable pageable) {
        log.debug("Request to get all ScheduledTaskLogs");
        return scheduledTaskLogRepository.findAll(pageable);
    }

    /**
     *  Get one scheduledTaskLog by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ScheduledTaskLog findOne(Long id) {
        log.debug("Request to get ScheduledTaskLog : {}", id);
        return scheduledTaskLogRepository.findOne(id);
    }

    /**
     *  Delete the  scheduledTaskLog by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ScheduledTaskLog : {}", id);
        scheduledTaskLogRepository.delete(id);
    }
}
