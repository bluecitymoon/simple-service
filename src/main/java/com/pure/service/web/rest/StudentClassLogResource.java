package com.pure.service.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pure.service.domain.StudentClassLog;
import com.pure.service.service.StudentClassLogService;
import com.pure.service.service.dto.request.BatchSigninStudent;
import com.pure.service.web.rest.util.HeaderUtil;
import com.pure.service.web.rest.util.PaginationUtil;
import com.pure.service.service.dto.StudentClassLogCriteria;
import com.pure.service.service.StudentClassLogQueryService;
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
 * REST controller for managing StudentClassLog.
 */
@RestController
@RequestMapping("/api")
public class StudentClassLogResource {

    private final Logger log = LoggerFactory.getLogger(StudentClassLogResource.class);

    private static final String ENTITY_NAME = "studentClassLog";

    private final StudentClassLogService studentClassLogService;

    private final StudentClassLogQueryService studentClassLogQueryService;

    public StudentClassLogResource(StudentClassLogService studentClassLogService, StudentClassLogQueryService studentClassLogQueryService) {
        this.studentClassLogService = studentClassLogService;
        this.studentClassLogQueryService = studentClassLogQueryService;
    }

    /**
     * POST  /student-class-logs : Create a new studentClassLog.
     *
     * @param studentClassLog the studentClassLog to create
     * @return the ResponseEntity with status 201 (Created) and with body the new studentClassLog, or with status 400 (Bad Request) if the studentClassLog has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/student-class-logs")
    @Timed
    public ResponseEntity<StudentClassLog> createStudentClassLog(@RequestBody StudentClassLog studentClassLog) throws URISyntaxException {
        log.debug("REST request to save StudentClassLog : {}", studentClassLog);
        if (studentClassLog.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new studentClassLog cannot already have an ID")).body(null);
        }
        StudentClassLog result = studentClassLogService.save(studentClassLog);
        return ResponseEntity.created(new URI("/api/student-class-logs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PostMapping("/student-class-logs/batch-sign-in")
    @Timed
    public ResponseEntity<StudentClassLog> batchSignin(@RequestBody BatchSigninStudent students) {

        studentClassLogService.batchSignIn(students);

        return ResponseEntity.ok().build();
    }

    /**
     * PUT  /student-class-logs : Updates an existing studentClassLog.
     *
     * @param studentClassLog the studentClassLog to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated studentClassLog,
     * or with status 400 (Bad Request) if the studentClassLog is not valid,
     * or with status 500 (Internal Server Error) if the studentClassLog couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/student-class-logs")
    @Timed
    public ResponseEntity<StudentClassLog> updateStudentClassLog(@RequestBody StudentClassLog studentClassLog) throws URISyntaxException {
        log.debug("REST request to update StudentClassLog : {}", studentClassLog);
        if (studentClassLog.getId() == null) {
            return createStudentClassLog(studentClassLog);
        }
        StudentClassLog result = studentClassLogService.save(studentClassLog);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, studentClassLog.getId().toString()))
            .body(result);
    }

    /**
     * GET  /student-class-logs : get all the studentClassLogs.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of studentClassLogs in body
     */
    @GetMapping("/student-class-logs")
    @Timed
    public ResponseEntity<List<StudentClassLog>> getAllStudentClassLogs(StudentClassLogCriteria criteria,@ApiParam Pageable pageable) {
        log.debug("REST request to get StudentClassLogs by criteria: {}", criteria);
        Page<StudentClassLog> page = studentClassLogQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/student-class-logs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /student-class-logs/:id : get the "id" studentClassLog.
     *
     * @param id the id of the studentClassLog to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the studentClassLog, or with status 404 (Not Found)
     */
    @GetMapping("/student-class-logs/{id}")
    @Timed
    public ResponseEntity<StudentClassLog> getStudentClassLog(@PathVariable Long id) {
        log.debug("REST request to get StudentClassLog : {}", id);
        StudentClassLog studentClassLog = studentClassLogService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(studentClassLog));
    }

    /**
     * DELETE  /student-class-logs/:id : delete the "id" studentClassLog.
     *
     * @param id the id of the studentClassLog to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/student-class-logs/{id}")
    @Timed
    public ResponseEntity<Void> deleteStudentClassLog(@PathVariable Long id) {
        log.debug("REST request to delete StudentClassLog : {}", id);
        studentClassLogService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
