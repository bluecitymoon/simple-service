package com.pure.service.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pure.service.domain.TaskStatus;
import com.pure.service.service.TaskStatusService;
import com.pure.service.web.rest.util.HeaderUtil;
import com.pure.service.web.rest.util.PaginationUtil;
import com.pure.service.service.dto.TaskStatusCriteria;
import com.pure.service.service.TaskStatusQueryService;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing TaskStatus.
 */
@RestController
@RequestMapping("/api")
public class TaskStatusResource {

    private final Logger log = LoggerFactory.getLogger(TaskStatusResource.class);

    private static final String ENTITY_NAME = "taskStatus";

    private final TaskStatusService taskStatusService;

    private final TaskStatusQueryService taskStatusQueryService;

    public TaskStatusResource(TaskStatusService taskStatusService, TaskStatusQueryService taskStatusQueryService) {
        this.taskStatusService = taskStatusService;
        this.taskStatusQueryService = taskStatusQueryService;
    }

    /**
     * POST  /task-statuses : Create a new taskStatus.
     *
     * @param taskStatus the taskStatus to create
     * @return the ResponseEntity with status 201 (Created) and with body the new taskStatus, or with status 400 (Bad Request) if the taskStatus has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/task-statuses")
    @Timed
    public ResponseEntity<TaskStatus> createTaskStatus(@RequestBody TaskStatus taskStatus) throws URISyntaxException {
        log.debug("REST request to save TaskStatus : {}", taskStatus);
        if (taskStatus.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new taskStatus cannot already have an ID")).body(null);
        }
        TaskStatus result = taskStatusService.save(taskStatus);
        return ResponseEntity.created(new URI("/api/task-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /task-statuses : Updates an existing taskStatus.
     *
     * @param taskStatus the taskStatus to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated taskStatus,
     * or with status 400 (Bad Request) if the taskStatus is not valid,
     * or with status 500 (Internal Server Error) if the taskStatus couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/task-statuses")
    @Timed
    public ResponseEntity<TaskStatus> updateTaskStatus(@RequestBody TaskStatus taskStatus) throws URISyntaxException {
        log.debug("REST request to update TaskStatus : {}", taskStatus);
        if (taskStatus.getId() == null) {
            return createTaskStatus(taskStatus);
        }
        TaskStatus result = taskStatusService.save(taskStatus);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, taskStatus.getId().toString()))
            .body(result);
    }

    /**
     * GET  /task-statuses : get all the taskStatuses.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of taskStatuses in body
     */
    @GetMapping("/task-statuses")
    @Timed
    public ResponseEntity<List<TaskStatus>> getAllTaskStatuses(TaskStatusCriteria criteria,@ApiParam Pageable pageable) {
        log.debug("REST request to get TaskStatuses by criteria: {}", criteria);
        Page<TaskStatus> page = taskStatusQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/task-statuses");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /task-statuses/:id : get the "id" taskStatus.
     *
     * @param id the id of the taskStatus to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the taskStatus, or with status 404 (Not Found)
     */
    @GetMapping("/task-statuses/{id}")
    @Timed
    public ResponseEntity<TaskStatus> getTaskStatus(@PathVariable Long id) {
        log.debug("REST request to get TaskStatus : {}", id);
        TaskStatus taskStatus = taskStatusService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(taskStatus));
    }

    /**
     * DELETE  /task-statuses/:id : delete the "id" taskStatus.
     *
     * @param id the id of the taskStatus to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/task-statuses/{id}")
    @Timed
    public ResponseEntity<Void> deleteTaskStatus(@PathVariable Long id) {
        log.debug("REST request to delete TaskStatus : {}", id);
        taskStatusService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
