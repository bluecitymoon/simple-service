package com.pure.service.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pure.service.domain.StudentAbsenceLog;
import com.pure.service.region.RegionBasedInsert;
import com.pure.service.region.RegionBasedQuery;
import com.pure.service.service.StudentAbsenceLogService;
import com.pure.service.web.rest.util.HeaderUtil;
import com.pure.service.web.rest.util.PaginationUtil;
import com.pure.service.service.dto.StudentAbsenceLogCriteria;
import com.pure.service.service.StudentAbsenceLogQueryService;
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
 * REST controller for managing StudentAbsenceLog.
 */
@RestController
@RequestMapping("/api")
public class StudentAbsenceLogResource {

    private final Logger log = LoggerFactory.getLogger(StudentAbsenceLogResource.class);

    private static final String ENTITY_NAME = "studentAbsenceLog";

    private final StudentAbsenceLogService studentAbsenceLogService;

    private final StudentAbsenceLogQueryService studentAbsenceLogQueryService;

    public StudentAbsenceLogResource(StudentAbsenceLogService studentAbsenceLogService, StudentAbsenceLogQueryService studentAbsenceLogQueryService) {
        this.studentAbsenceLogService = studentAbsenceLogService;
        this.studentAbsenceLogQueryService = studentAbsenceLogQueryService;
    }

    /**
     * POST  /student-absence-logs : Create a new studentAbsenceLog.
     *
     * @param studentAbsenceLog the studentAbsenceLog to create
     * @return the ResponseEntity with status 201 (Created) and with body the new studentAbsenceLog, or with status 400 (Bad Request) if the studentAbsenceLog has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/student-absence-logs")
    @Timed
    @RegionBasedInsert
    public ResponseEntity<StudentAbsenceLog> createStudentAbsenceLog(@RequestBody StudentAbsenceLog studentAbsenceLog) throws URISyntaxException {
        log.debug("REST request to save StudentAbsenceLog : {}", studentAbsenceLog);
        if (studentAbsenceLog.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new studentAbsenceLog cannot already have an ID")).body(null);
        }
        StudentAbsenceLog result = studentAbsenceLogService.save(studentAbsenceLog);
        return ResponseEntity.created(new URI("/api/student-absence-logs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /student-absence-logs : Updates an existing studentAbsenceLog.
     *
     * @param studentAbsenceLog the studentAbsenceLog to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated studentAbsenceLog,
     * or with status 400 (Bad Request) if the studentAbsenceLog is not valid,
     * or with status 500 (Internal Server Error) if the studentAbsenceLog couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/student-absence-logs")
    @Timed
    public ResponseEntity<StudentAbsenceLog> updateStudentAbsenceLog(@RequestBody StudentAbsenceLog studentAbsenceLog) throws URISyntaxException {
        log.debug("REST request to update StudentAbsenceLog : {}", studentAbsenceLog);
        if (studentAbsenceLog.getId() == null) {
            return createStudentAbsenceLog(studentAbsenceLog);
        }
        StudentAbsenceLog result = studentAbsenceLogService.save(studentAbsenceLog);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, studentAbsenceLog.getId().toString()))
            .body(result);
    }

    /**
     * GET  /student-absence-logs : get all the studentAbsenceLogs.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of studentAbsenceLogs in body
     */
    @GetMapping("/student-absence-logs")
    @Timed
    @RegionBasedQuery
    public ResponseEntity<List<StudentAbsenceLog>> getAllStudentAbsenceLogs(StudentAbsenceLogCriteria criteria,@ApiParam Pageable pageable) {
        log.debug("REST request to get StudentAbsenceLogs by criteria: {}", criteria);
        Page<StudentAbsenceLog> page = studentAbsenceLogQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/student-absence-logs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /student-absence-logs/:id : get the "id" studentAbsenceLog.
     *
     * @param id the id of the studentAbsenceLog to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the studentAbsenceLog, or with status 404 (Not Found)
     */
    @GetMapping("/student-absence-logs/{id}")
    @Timed
    public ResponseEntity<StudentAbsenceLog> getStudentAbsenceLog(@PathVariable Long id) {
        log.debug("REST request to get StudentAbsenceLog : {}", id);
        StudentAbsenceLog studentAbsenceLog = studentAbsenceLogService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(studentAbsenceLog));
    }

    /**
     * DELETE  /student-absence-logs/:id : delete the "id" studentAbsenceLog.
     *
     * @param id the id of the studentAbsenceLog to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/student-absence-logs/{id}")
    @Timed
    public ResponseEntity<Void> deleteStudentAbsenceLog(@PathVariable Long id) {
        log.debug("REST request to delete StudentAbsenceLog : {}", id);
        studentAbsenceLogService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
