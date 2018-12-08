package com.pure.service.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pure.service.domain.StudentClassLogType;
import com.pure.service.service.StudentClassLogTypeService;
import com.pure.service.web.rest.util.HeaderUtil;
import com.pure.service.web.rest.util.PaginationUtil;
import com.pure.service.service.dto.StudentClassLogTypeCriteria;
import com.pure.service.service.StudentClassLogTypeQueryService;
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
 * REST controller for managing StudentClassLogType.
 */
@RestController
@RequestMapping("/api")
public class StudentClassLogTypeResource {

    private final Logger log = LoggerFactory.getLogger(StudentClassLogTypeResource.class);

    private static final String ENTITY_NAME = "studentClassLogType";

    private final StudentClassLogTypeService studentClassLogTypeService;

    private final StudentClassLogTypeQueryService studentClassLogTypeQueryService;

    public StudentClassLogTypeResource(StudentClassLogTypeService studentClassLogTypeService, StudentClassLogTypeQueryService studentClassLogTypeQueryService) {
        this.studentClassLogTypeService = studentClassLogTypeService;
        this.studentClassLogTypeQueryService = studentClassLogTypeQueryService;
    }

    /**
     * POST  /student-class-log-types : Create a new studentClassLogType.
     *
     * @param studentClassLogType the studentClassLogType to create
     * @return the ResponseEntity with status 201 (Created) and with body the new studentClassLogType, or with status 400 (Bad Request) if the studentClassLogType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/student-class-log-types")
    @Timed
    public ResponseEntity<StudentClassLogType> createStudentClassLogType(@RequestBody StudentClassLogType studentClassLogType) throws URISyntaxException {
        log.debug("REST request to save StudentClassLogType : {}", studentClassLogType);
        if (studentClassLogType.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new studentClassLogType cannot already have an ID")).body(null);
        }
        StudentClassLogType result = studentClassLogTypeService.save(studentClassLogType);
        return ResponseEntity.created(new URI("/api/student-class-log-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /student-class-log-types : Updates an existing studentClassLogType.
     *
     * @param studentClassLogType the studentClassLogType to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated studentClassLogType,
     * or with status 400 (Bad Request) if the studentClassLogType is not valid,
     * or with status 500 (Internal Server Error) if the studentClassLogType couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/student-class-log-types")
    @Timed
    public ResponseEntity<StudentClassLogType> updateStudentClassLogType(@RequestBody StudentClassLogType studentClassLogType) throws URISyntaxException {
        log.debug("REST request to update StudentClassLogType : {}", studentClassLogType);
        if (studentClassLogType.getId() == null) {
            return createStudentClassLogType(studentClassLogType);
        }
        StudentClassLogType result = studentClassLogTypeService.save(studentClassLogType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, studentClassLogType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /student-class-log-types : get all the studentClassLogTypes.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of studentClassLogTypes in body
     */
    @GetMapping("/student-class-log-types")
    @Timed
    public ResponseEntity<List<StudentClassLogType>> getAllStudentClassLogTypes(StudentClassLogTypeCriteria criteria,@ApiParam Pageable pageable) {
        log.debug("REST request to get StudentClassLogTypes by criteria: {}", criteria);
        Page<StudentClassLogType> page = studentClassLogTypeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/student-class-log-types");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /student-class-log-types/:id : get the "id" studentClassLogType.
     *
     * @param id the id of the studentClassLogType to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the studentClassLogType, or with status 404 (Not Found)
     */
    @GetMapping("/student-class-log-types/{id}")
    @Timed
    public ResponseEntity<StudentClassLogType> getStudentClassLogType(@PathVariable Long id) {
        log.debug("REST request to get StudentClassLogType : {}", id);
        StudentClassLogType studentClassLogType = studentClassLogTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(studentClassLogType));
    }

    /**
     * DELETE  /student-class-log-types/:id : delete the "id" studentClassLogType.
     *
     * @param id the id of the studentClassLogType to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/student-class-log-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteStudentClassLogType(@PathVariable Long id) {
        log.debug("REST request to delete StudentClassLogType : {}", id);
        studentClassLogTypeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
