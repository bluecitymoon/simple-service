package com.pure.service.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pure.service.domain.StudentClassInOutLog;
import com.pure.service.region.RegionBasedInsert;
import com.pure.service.region.RegionBasedQuery;
import com.pure.service.service.StudentClassInOutLogService;
import com.pure.service.web.rest.util.HeaderUtil;
import com.pure.service.web.rest.util.PaginationUtil;
import com.pure.service.service.dto.StudentClassInOutLogCriteria;
import com.pure.service.service.StudentClassInOutLogQueryService;
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
 * REST controller for managing StudentClassInOutLog.
 */
@RestController
@RequestMapping("/api")
public class StudentClassInOutLogResource {

    private final Logger log = LoggerFactory.getLogger(StudentClassInOutLogResource.class);

    private static final String ENTITY_NAME = "studentClassInOutLog";

    private final StudentClassInOutLogService studentClassInOutLogService;

    private final StudentClassInOutLogQueryService studentClassInOutLogQueryService;

    public StudentClassInOutLogResource(StudentClassInOutLogService studentClassInOutLogService, StudentClassInOutLogQueryService studentClassInOutLogQueryService) {
        this.studentClassInOutLogService = studentClassInOutLogService;
        this.studentClassInOutLogQueryService = studentClassInOutLogQueryService;
    }

    /**
     * POST  /student-class-in-out-logs : Create a new studentClassInOutLog.
     *
     * @param studentClassInOutLog the studentClassInOutLog to create
     * @return the ResponseEntity with status 201 (Created) and with body the new studentClassInOutLog, or with status 400 (Bad Request) if the studentClassInOutLog has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/student-class-in-out-logs")
    @Timed
    @RegionBasedInsert
    public ResponseEntity<StudentClassInOutLog> createStudentClassInOutLog(@RequestBody StudentClassInOutLog studentClassInOutLog) throws URISyntaxException {
        log.debug("REST request to save StudentClassInOutLog : {}", studentClassInOutLog);
        if (studentClassInOutLog.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new studentClassInOutLog cannot already have an ID")).body(null);
        }
        StudentClassInOutLog result = studentClassInOutLogService.save(studentClassInOutLog);
        return ResponseEntity.created(new URI("/api/student-class-in-out-logs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /student-class-in-out-logs : Updates an existing studentClassInOutLog.
     *
     * @param studentClassInOutLog the studentClassInOutLog to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated studentClassInOutLog,
     * or with status 400 (Bad Request) if the studentClassInOutLog is not valid,
     * or with status 500 (Internal Server Error) if the studentClassInOutLog couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/student-class-in-out-logs")
    @Timed
    public ResponseEntity<StudentClassInOutLog> updateStudentClassInOutLog(@RequestBody StudentClassInOutLog studentClassInOutLog) throws URISyntaxException {
        log.debug("REST request to update StudentClassInOutLog : {}", studentClassInOutLog);
        if (studentClassInOutLog.getId() == null) {
            return createStudentClassInOutLog(studentClassInOutLog);
        }
        StudentClassInOutLog result = studentClassInOutLogService.save(studentClassInOutLog);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, studentClassInOutLog.getId().toString()))
            .body(result);
    }

    /**
     * GET  /student-class-in-out-logs : get all the studentClassInOutLogs.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of studentClassInOutLogs in body
     */
    @GetMapping("/student-class-in-out-logs")
    @Timed
    @RegionBasedQuery
    public ResponseEntity<List<StudentClassInOutLog>> getAllStudentClassInOutLogs(StudentClassInOutLogCriteria criteria,@ApiParam Pageable pageable) {
        log.debug("REST request to get StudentClassInOutLogs by criteria: {}", criteria);
        Page<StudentClassInOutLog> page = studentClassInOutLogQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/student-class-in-out-logs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /student-class-in-out-logs/:id : get the "id" studentClassInOutLog.
     *
     * @param id the id of the studentClassInOutLog to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the studentClassInOutLog, or with status 404 (Not Found)
     */
    @GetMapping("/student-class-in-out-logs/{id}")
    @Timed
    public ResponseEntity<StudentClassInOutLog> getStudentClassInOutLog(@PathVariable Long id) {
        log.debug("REST request to get StudentClassInOutLog : {}", id);
        StudentClassInOutLog studentClassInOutLog = studentClassInOutLogService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(studentClassInOutLog));
    }

    /**
     * DELETE  /student-class-in-out-logs/:id : delete the "id" studentClassInOutLog.
     *
     * @param id the id of the studentClassInOutLog to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/student-class-in-out-logs/{id}")
    @Timed
    public ResponseEntity<Void> deleteStudentClassInOutLog(@PathVariable Long id) {
        log.debug("REST request to delete StudentClassInOutLog : {}", id);
        studentClassInOutLogService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
