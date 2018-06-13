package com.pure.service.service.impl;

import com.pure.service.service.TaskService;
import com.pure.service.domain.Task;
import com.pure.service.repository.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Task.
 */
@Service
@Transactional
public class TaskServiceImpl implements TaskService{

    private final Logger log = LoggerFactory.getLogger(TaskServiceImpl.class);

    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    /**
     * Save a task.
     *
     * @param task the entity to save
     * @return the persisted entity
     */
    @Override
    public Task save(Task task) {
        log.debug("Request to save Task : {}", task);
        return taskRepository.save(task);
    }

    /**
     *  Get all the tasks.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Task> findAll(Pageable pageable) {
        log.debug("Request to get all Tasks");
        return taskRepository.findAll(pageable);
    }

    /**
     *  Get one task by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Task findOne(Long id) {
        log.debug("Request to get Task : {}", id);
        return taskRepository.findOne(id);
    }

    /**
     *  Delete the  task by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Task : {}", id);
        taskRepository.delete(id);
    }
}
