package com.pure.service.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pure.service.domain.StudentFrozen;
import com.pure.service.region.RegionBasedInsert;
import com.pure.service.region.RegionBasedQuery;
import com.pure.service.service.StudentFrozenService;
import com.pure.service.service.dto.request.StudentFrozenRequest;
import com.pure.service.web.rest.util.HeaderUtil;
import com.pure.service.web.rest.util.PaginationUtil;
import com.pure.service.service.dto.StudentFrozenCriteria;
import com.pure.service.service.StudentFrozenQueryService;
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
 * REST controller for managing StudentFrozen.
 */
@RestController
@RequestMapping("/api")
public class StudentFrozenResource {

    private final Logger log = LoggerFactory.getLogger(StudentFrozenResource.class);

    private static final String ENTITY_NAME = "studentFrozen";

    private final StudentFrozenService studentFrozenService;

    private final StudentFrozenQueryService studentFrozenQueryService;

    public StudentFrozenResource(StudentFrozenService studentFrozenService, StudentFrozenQueryService studentFrozenQueryService) {
        this.studentFrozenService = studentFrozenService;
        this.studentFrozenQueryService = studentFrozenQueryService;
    }

    /**
     * POST  /student-frozens : Create a new studentFrozen.
     *
     * @param studentFrozen the studentFrozen to create
     * @return the ResponseEntity with status 201 (Created) and with body the new studentFrozen, or with status 400 (Bad Request) if the studentFrozen has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/student-frozens")
    @Timed
    @RegionBasedInsert
    public ResponseEntity<StudentFrozen> createStudentFrozen(@RequestBody StudentFrozen studentFrozen) throws URISyntaxException {
        log.debug("REST request to save StudentFrozen : {}", studentFrozen);
        if (studentFrozen.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new studentFrozen cannot already have an ID")).body(null);
        }
        StudentFrozen result = studentFrozenService.save(studentFrozen);
        return ResponseEntity.created(new URI("/api/student-frozens/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PostMapping("/student-frozens/generate")
    @Timed
    @RegionBasedInsert
    public ResponseEntity<StudentFrozen> generateStudentFrozen(@RequestBody StudentFrozenRequest studentFrozen) throws URISyntaxException {

        StudentFrozen result = studentFrozenService.generateStudentFrozen(studentFrozen);

        return ResponseEntity.created(new URI("/api/student-frozens/" + result.getId()))
//            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /student-frozens : Updates an existing studentFrozen.
     *
     * @param studentFrozen the studentFrozen to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated studentFrozen,
     * or with status 400 (Bad Request) if the studentFrozen is not valid,
     * or with status 500 (Internal Server Error) if the studentFrozen couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/student-frozens")
    @Timed
    public ResponseEntity<StudentFrozen> updateStudentFrozen(@RequestBody StudentFrozen studentFrozen) throws URISyntaxException {
        log.debug("REST request to update StudentFrozen : {}", studentFrozen);
        if (studentFrozen.getId() == null) {
            return createStudentFrozen(studentFrozen);
        }
        StudentFrozen result = studentFrozenService.save(studentFrozen);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, studentFrozen.getId().toString()))
            .body(result);
    }

    /**
     * GET  /student-frozens : get all the studentFrozens.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of studentFrozens in body
     */
    @GetMapping("/student-frozens")
    @Timed
    @RegionBasedQuery
    public ResponseEntity<List<StudentFrozen>> getAllStudentFrozens(StudentFrozenCriteria criteria,@ApiParam Pageable pageable) {
        log.debug("REST request to get StudentFrozens by criteria: {}", criteria);
        Page<StudentFrozen> page = studentFrozenQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/student-frozens");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /student-frozens/:id : get the "id" studentFrozen.
     *
     * @param id the id of the studentFrozen to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the studentFrozen, or with status 404 (Not Found)
     */
    @GetMapping("/student-frozens/{id}")
    @Timed
    public ResponseEntity<StudentFrozen> getStudentFrozen(@PathVariable Long id) {
        log.debug("REST request to get StudentFrozen : {}", id);
        StudentFrozen studentFrozen = studentFrozenService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(studentFrozen));
    }

    /**
     * DELETE  /student-frozens/:id : delete the "id" studentFrozen.
     *
     * @param id the id of the studentFrozen to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/student-frozens/{id}")
    @Timed
    public ResponseEntity<Void> deleteStudentFrozen(@PathVariable Long id) {
        log.debug("REST request to delete StudentFrozen : {}", id);
        studentFrozenService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
