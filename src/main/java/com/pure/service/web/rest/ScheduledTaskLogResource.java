package com.pure.service.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pure.service.domain.ScheduledTaskLog;
import com.pure.service.service.ScheduledTaskLogService;
import com.pure.service.web.rest.util.HeaderUtil;
import com.pure.service.web.rest.util.PaginationUtil;
import com.pure.service.service.dto.ScheduledTaskLogCriteria;
import com.pure.service.service.ScheduledTaskLogQueryService;
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
 * REST controller for managing ScheduledTaskLog.
 */
@RestController
@RequestMapping("/api")
public class ScheduledTaskLogResource {

    private final Logger log = LoggerFactory.getLogger(ScheduledTaskLogResource.class);

    private static final String ENTITY_NAME = "scheduledTaskLog";

    private final ScheduledTaskLogService scheduledTaskLogService;

    private final ScheduledTaskLogQueryService scheduledTaskLogQueryService;

    public ScheduledTaskLogResource(ScheduledTaskLogService scheduledTaskLogService, ScheduledTaskLogQueryService scheduledTaskLogQueryService) {
        this.scheduledTaskLogService = scheduledTaskLogService;
        this.scheduledTaskLogQueryService = scheduledTaskLogQueryService;
    }

    /**
     * POST  /scheduled-task-logs : Create a new scheduledTaskLog.
     *
     * @param scheduledTaskLog the scheduledTaskLog to create
     * @return the ResponseEntity with status 201 (Created) and with body the new scheduledTaskLog, or with status 400 (Bad Request) if the scheduledTaskLog has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/scheduled-task-logs")
    @Timed
    public ResponseEntity<ScheduledTaskLog> createScheduledTaskLog(@RequestBody ScheduledTaskLog scheduledTaskLog) throws URISyntaxException {
        log.debug("REST request to save ScheduledTaskLog : {}", scheduledTaskLog);
        if (scheduledTaskLog.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new scheduledTaskLog cannot already have an ID")).body(null);
        }
        ScheduledTaskLog result = scheduledTaskLogService.save(scheduledTaskLog);
        return ResponseEntity.created(new URI("/api/scheduled-task-logs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /scheduled-task-logs : Updates an existing scheduledTaskLog.
     *
     * @param scheduledTaskLog the scheduledTaskLog to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated scheduledTaskLog,
     * or with status 400 (Bad Request) if the scheduledTaskLog is not valid,
     * or with status 500 (Internal Server Error) if the scheduledTaskLog couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/scheduled-task-logs")
    @Timed
    public ResponseEntity<ScheduledTaskLog> updateScheduledTaskLog(@RequestBody ScheduledTaskLog scheduledTaskLog) throws URISyntaxException {
        log.debug("REST request to update ScheduledTaskLog : {}", scheduledTaskLog);
        if (scheduledTaskLog.getId() == null) {
            return createScheduledTaskLog(scheduledTaskLog);
        }
        ScheduledTaskLog result = scheduledTaskLogService.save(scheduledTaskLog);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, scheduledTaskLog.getId().toString()))
            .body(result);
    }

    /**
     * GET  /scheduled-task-logs : get all the scheduledTaskLogs.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of scheduledTaskLogs in body
     */
    @GetMapping("/scheduled-task-logs")
    @Timed
    public ResponseEntity<List<ScheduledTaskLog>> getAllScheduledTaskLogs(ScheduledTaskLogCriteria criteria,@ApiParam Pageable pageable) {
        log.debug("REST request to get ScheduledTaskLogs by criteria: {}", criteria);
        Page<ScheduledTaskLog> page = scheduledTaskLogQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/scheduled-task-logs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /scheduled-task-logs/:id : get the "id" scheduledTaskLog.
     *
     * @param id the id of the scheduledTaskLog to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the scheduledTaskLog, or with status 404 (Not Found)
     */
    @GetMapping("/scheduled-task-logs/{id}")
    @Timed
    public ResponseEntity<ScheduledTaskLog> getScheduledTaskLog(@PathVariable Long id) {
        log.debug("REST request to get ScheduledTaskLog : {}", id);
        ScheduledTaskLog scheduledTaskLog = scheduledTaskLogService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(scheduledTaskLog));
    }

    /**
     * DELETE  /scheduled-task-logs/:id : delete the "id" scheduledTaskLog.
     *
     * @param id the id of the scheduledTaskLog to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/scheduled-task-logs/{id}")
    @Timed
    public ResponseEntity<Void> deleteScheduledTaskLog(@PathVariable Long id) {
        log.debug("REST request to delete ScheduledTaskLog : {}", id);
        scheduledTaskLogService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
