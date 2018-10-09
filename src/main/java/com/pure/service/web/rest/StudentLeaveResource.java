package com.pure.service.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pure.service.domain.StudentLeave;
import com.pure.service.service.StudentLeaveService;
import com.pure.service.service.dto.dto.StudentLeaveRequest;
import com.pure.service.web.rest.util.HeaderUtil;
import com.pure.service.web.rest.util.PaginationUtil;
import com.pure.service.service.dto.StudentLeaveCriteria;
import com.pure.service.service.StudentLeaveQueryService;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing StudentLeave.
 */
@RestController
@RequestMapping("/api")
public class StudentLeaveResource {

    private final Logger log = LoggerFactory.getLogger(StudentLeaveResource.class);

    private static final String ENTITY_NAME = "studentLeave";

    private final StudentLeaveService studentLeaveService;

    private final StudentLeaveQueryService studentLeaveQueryService;

    public StudentLeaveResource(StudentLeaveService studentLeaveService, StudentLeaveQueryService studentLeaveQueryService) {
        this.studentLeaveService = studentLeaveService;
        this.studentLeaveQueryService = studentLeaveQueryService;
    }

    /**
     * POST  /student-leaves : Create a new studentLeave.
     *
     * @param studentLeave the studentLeave to create
     * @return the ResponseEntity with status 201 (Created) and with body the new studentLeave, or with status 400 (Bad Request) if the studentLeave has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/student-leaves")
    @Timed
    public ResponseEntity<StudentLeave> createStudentLeave(@RequestBody StudentLeave studentLeave) throws URISyntaxException {
        log.debug("REST request to save StudentLeave : {}", studentLeave);
        if (studentLeave.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new studentLeave cannot already have an ID")).body(null);
        }
        StudentLeave result = studentLeaveService.save(studentLeave);
        return ResponseEntity.created(new URI("/api/student-leaves/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PostMapping("/student-leaves/batch-leave")
    @Timed
    public ResponseEntity<List<StudentLeave>> createBatchStudentLeave(@RequestBody StudentLeaveRequest studentLeaveRequest) throws URISyntaxException {

        if (CollectionUtils.isEmpty(studentLeaveRequest.getArrangements())) {
            throw new RuntimeException("未选择请假日期");
        }

        if (CollectionUtils.isEmpty(studentLeaveRequest.getStudents())) {
            throw new RuntimeException("未选择学员");
        }

        List<StudentLeave> result = studentLeaveService.createBatchStudentLeave(studentLeaveRequest);
        return ResponseEntity.created(new URI("/api/student-leaves/"))
            .body(result);
    }
    /**
     * PUT  /student-leaves : Updates an existing studentLeave.
     *
     * @param studentLeave the studentLeave to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated studentLeave,
     * or with status 400 (Bad Request) if the studentLeave is not valid,
     * or with status 500 (Internal Server Error) if the studentLeave couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/student-leaves")
    @Timed
    public ResponseEntity<StudentLeave> updateStudentLeave(@RequestBody StudentLeave studentLeave) throws URISyntaxException {
        log.debug("REST request to update StudentLeave : {}", studentLeave);
        if (studentLeave.getId() == null) {
            return createStudentLeave(studentLeave);
        }
        StudentLeave result = studentLeaveService.save(studentLeave);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, studentLeave.getId().toString()))
            .body(result);
    }

    /**
     * GET  /student-leaves : get all the studentLeaves.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of studentLeaves in body
     */
    @GetMapping("/student-leaves")
    @Timed
    public ResponseEntity<List<StudentLeave>> getAllStudentLeaves(StudentLeaveCriteria criteria,@ApiParam Pageable pageable) {
        log.debug("REST request to get StudentLeaves by criteria: {}", criteria);
        Page<StudentLeave> page = studentLeaveQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/student-leaves");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /student-leaves/:id : get the "id" studentLeave.
     *
     * @param id the id of the studentLeave to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the studentLeave, or with status 404 (Not Found)
     */
    @GetMapping("/student-leaves/{id}")
    @Timed
    public ResponseEntity<StudentLeave> getStudentLeave(@PathVariable Long id) {
        log.debug("REST request to get StudentLeave : {}", id);
        StudentLeave studentLeave = studentLeaveService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(studentLeave));
    }

    /**
     * DELETE  /student-leaves/:id : delete the "id" studentLeave.
     *
     * @param id the id of the studentLeave to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/student-leaves/{id}")
    @Timed
    public ResponseEntity<Void> deleteStudentLeave(@PathVariable Long id) {
        log.debug("REST request to delete StudentLeave : {}", id);
        studentLeaveService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
