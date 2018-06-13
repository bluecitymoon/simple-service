package com.pure.service.service.impl;

import com.pure.service.service.TaskStatusService;
import com.pure.service.domain.TaskStatus;
import com.pure.service.repository.TaskStatusRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing TaskStatus.
 */
@Service
@Transactional
public class TaskStatusServiceImpl implements TaskStatusService{

    private final Logger log = LoggerFactory.getLogger(TaskStatusServiceImpl.class);

    private final TaskStatusRepository taskStatusRepository;

    public TaskStatusServiceImpl(TaskStatusRepository taskStatusRepository) {
        this.taskStatusRepository = taskStatusRepository;
    }

    /**
     * Save a taskStatus.
     *
     * @param taskStatus the entity to save
     * @return the persisted entity
     */
    @Override
    public TaskStatus save(TaskStatus taskStatus) {
        log.debug("Request to save TaskStatus : {}", taskStatus);
        return taskStatusRepository.save(taskStatus);
    }

    /**
     *  Get all the taskStatuses.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TaskStatus> findAll(Pageable pageable) {
        log.debug("Request to get all TaskStatuses");
        return taskStatusRepository.findAll(pageable);
    }

    /**
     *  Get one taskStatus by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public TaskStatus findOne(Long id) {
        log.debug("Request to get TaskStatus : {}", id);
        return taskStatusRepository.findOne(id);
    }

    /**
     *  Delete the  taskStatus by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TaskStatus : {}", id);
        taskStatusRepository.delete(id);
    }
}
