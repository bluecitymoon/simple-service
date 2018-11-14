package com.pure.service.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pure.service.domain.StudentFrozenArrangement;
import com.pure.service.region.RegionBasedInsert;
import com.pure.service.region.RegionBasedQuery;
import com.pure.service.service.StudentFrozenArrangementService;
import com.pure.service.web.rest.util.HeaderUtil;
import com.pure.service.web.rest.util.PaginationUtil;
import com.pure.service.service.dto.StudentFrozenArrangementCriteria;
import com.pure.service.service.StudentFrozenArrangementQueryService;
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
 * REST controller for managing StudentFrozenArrangement.
 */
@RestController
@RequestMapping("/api")
public class StudentFrozenArrangementResource {

    private final Logger log = LoggerFactory.getLogger(StudentFrozenArrangementResource.class);

    private static final String ENTITY_NAME = "studentFrozenArrangement";

    private final StudentFrozenArrangementService studentFrozenArrangementService;

    private final StudentFrozenArrangementQueryService studentFrozenArrangementQueryService;

    public StudentFrozenArrangementResource(StudentFrozenArrangementService studentFrozenArrangementService, StudentFrozenArrangementQueryService studentFrozenArrangementQueryService) {
        this.studentFrozenArrangementService = studentFrozenArrangementService;
        this.studentFrozenArrangementQueryService = studentFrozenArrangementQueryService;
    }

    /**
     * POST  /student-frozen-arrangements : Create a new studentFrozenArrangement.
     *
     * @param studentFrozenArrangement the studentFrozenArrangement to create
     * @return the ResponseEntity with status 201 (Created) and with body the new studentFrozenArrangement, or with status 400 (Bad Request) if the studentFrozenArrangement has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/student-frozen-arrangements")
    @Timed
    @RegionBasedInsert
    public ResponseEntity<StudentFrozenArrangement> createStudentFrozenArrangement(@RequestBody StudentFrozenArrangement studentFrozenArrangement) throws URISyntaxException {
        log.debug("REST request to save StudentFrozenArrangement : {}", studentFrozenArrangement);
        if (studentFrozenArrangement.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new studentFrozenArrangement cannot already have an ID")).body(null);
        }
        StudentFrozenArrangement result = studentFrozenArrangementService.save(studentFrozenArrangement);
        return ResponseEntity.created(new URI("/api/student-frozen-arrangements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /student-frozen-arrangements : Updates an existing studentFrozenArrangement.
     *
     * @param studentFrozenArrangement the studentFrozenArrangement to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated studentFrozenArrangement,
     * or with status 400 (Bad Request) if the studentFrozenArrangement is not valid,
     * or with status 500 (Internal Server Error) if the studentFrozenArrangement couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/student-frozen-arrangements")
    @Timed
    public ResponseEntity<StudentFrozenArrangement> updateStudentFrozenArrangement(@RequestBody StudentFrozenArrangement studentFrozenArrangement) throws URISyntaxException {
        log.debug("REST request to update StudentFrozenArrangement : {}", studentFrozenArrangement);
        if (studentFrozenArrangement.getId() == null) {
            return createStudentFrozenArrangement(studentFrozenArrangement);
        }
        StudentFrozenArrangement result = studentFrozenArrangementService.save(studentFrozenArrangement);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, studentFrozenArrangement.getId().toString()))
            .body(result);
    }

    /**
     * GET  /student-frozen-arrangements : get all the studentFrozenArrangements.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of studentFrozenArrangements in body
     */
    @GetMapping("/student-frozen-arrangements")
    @Timed
    @RegionBasedQuery
    public ResponseEntity<List<StudentFrozenArrangement>> getAllStudentFrozenArrangements(StudentFrozenArrangementCriteria criteria,@ApiParam Pageable pageable) {
        log.debug("REST request to get StudentFrozenArrangements by criteria: {}", criteria);
        Page<StudentFrozenArrangement> page = studentFrozenArrangementQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/student-frozen-arrangements");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /student-frozen-arrangements/:id : get the "id" studentFrozenArrangement.
     *
     * @param id the id of the studentFrozenArrangement to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the studentFrozenArrangement, or with status 404 (Not Found)
     */
    @GetMapping("/student-frozen-arrangements/{id}")
    @Timed
    public ResponseEntity<StudentFrozenArrangement> getStudentFrozenArrangement(@PathVariable Long id) {
        log.debug("REST request to get StudentFrozenArrangement : {}", id);
        StudentFrozenArrangement studentFrozenArrangement = studentFrozenArrangementService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(studentFrozenArrangement));
    }

    /**
     * DELETE  /student-frozen-arrangements/:id : delete the "id" studentFrozenArrangement.
     *
     * @param id the id of the studentFrozenArrangement to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/student-frozen-arrangements/{id}")
    @Timed
    public ResponseEntity<Void> deleteStudentFrozenArrangement(@PathVariable Long id) {
        log.debug("REST request to delete StudentFrozenArrangement : {}", id);
        studentFrozenArrangementService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
