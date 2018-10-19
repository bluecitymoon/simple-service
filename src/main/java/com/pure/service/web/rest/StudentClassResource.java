package com.pure.service.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pure.service.domain.Student;
import com.pure.service.domain.StudentClass;
import com.pure.service.region.RegionBasedInsert;
import com.pure.service.region.RegionBasedQuery;
import com.pure.service.service.StudentClassService;
import com.pure.service.service.dto.dto.CommonResponse;
import com.pure.service.service.dto.request.SingleStudentClassRequest;
import com.pure.service.service.dto.request.StudentsClassRequest;
import com.pure.service.web.rest.util.HeaderUtil;
import com.pure.service.web.rest.util.PaginationUtil;
import com.pure.service.service.dto.StudentClassCriteria;
import com.pure.service.service.StudentClassQueryService;
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
 * REST controller for managing StudentClass.
 */
@RestController
@RequestMapping("/api")
public class StudentClassResource {

    private final Logger log = LoggerFactory.getLogger(StudentClassResource.class);

    private static final String ENTITY_NAME = "studentClass";

    private final StudentClassService studentClassService;

    private final StudentClassQueryService studentClassQueryService;

    public StudentClassResource(StudentClassService studentClassService, StudentClassQueryService studentClassQueryService) {
        this.studentClassService = studentClassService;
        this.studentClassQueryService = studentClassQueryService;
    }

    /**
     * POST  /student-classes : Create a new studentClass.
     *
     * @param studentClass the studentClass to create
     * @return the ResponseEntity with status 201 (Created) and with body the new studentClass, or with status 400 (Bad Request) if the studentClass has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/student-classes")
    @Timed
    @RegionBasedInsert
    public ResponseEntity<StudentClass> createStudentClass(@RequestBody StudentClass studentClass) throws URISyntaxException {
        log.debug("REST request to save StudentClass : {}", studentClass);
        if (studentClass.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new studentClass cannot already have an ID")).body(null);
        }
        StudentClass result = studentClassService.save(studentClass);
        return ResponseEntity.created(new URI("/api/student-classes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PostMapping("/student-classes/batch-assign")
    @Timed
    public ResponseEntity<CommonResponse> createStudentClass(@RequestBody StudentsClassRequest studentsClassRequest) {
        log.debug("REST request to batch assign students into class : {}", studentsClassRequest);

        CommonResponse response = studentClassService.batchAssign(studentsClassRequest);

        return ResponseEntity.ok(response);
    }
    @PostMapping("/student-classes/single-assign")
    @Timed
    public ResponseEntity<StudentClass> createSingleStudentClass(@RequestBody SingleStudentClassRequest studentsClassRequest) {
        log.debug("REST request to batch assign students into class : {}", studentsClassRequest);

        StudentClass response = studentClassService.singleAssign(studentsClassRequest);

        return ResponseEntity.ok(response);
    }


    /**
     * PUT  /student-classes : Updates an existing studentClass.
     *
     * @param studentClass the studentClass to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated studentClass,
     * or with status 400 (Bad Request) if the studentClass is not valid,
     * or with status 500 (Internal Server Error) if the studentClass couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/student-classes")
    @Timed
    public ResponseEntity<StudentClass> updateStudentClass(@RequestBody StudentClass studentClass) throws URISyntaxException {
        log.debug("REST request to update StudentClass : {}", studentClass);
        if (studentClass.getId() == null) {
            return createStudentClass(studentClass);
        }
        StudentClass result = studentClassService.save(studentClass);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, studentClass.getId().toString()))
            .body(result);
    }

    /**
     * GET  /student-classes : get all the studentClasses.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of studentClasses in body
     */
    @GetMapping("/student-classes")
    @Timed
    @RegionBasedQuery
    public ResponseEntity<List<StudentClass>> getAllStudentClasses(StudentClassCriteria criteria,@ApiParam Pageable pageable) {
        log.debug("REST request to get StudentClasses by criteria: {}", criteria);
        Page<StudentClass> page = studentClassQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/student-classes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/student-classes/students/{classId}")
    @Timed
    public ResponseEntity<List<Student>> getAllStudentInClass(@PathVariable Long classId) {

        List<Student> students = studentClassService.findStudentsInClass(classId);

        return new ResponseEntity<>(students, null, HttpStatus.OK);
    }

    /**
     * GET  /student-classes/:id : get the "id" studentClass.
     *
     * @param id the id of the studentClass to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the studentClass, or with status 404 (Not Found)
     */
    @GetMapping("/student-classes/{id}")
    @Timed
    public ResponseEntity<StudentClass> getStudentClass(@PathVariable Long id) {
        log.debug("REST request to get StudentClass : {}", id);
        StudentClass studentClass = studentClassService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(studentClass));
    }

    /**
     * DELETE  /student-classes/:id : delete the "id" studentClass.
     *
     * @param id the id of the studentClass to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/student-classes/{id}")
    @Timed
    public ResponseEntity<Void> deleteStudentClass(@PathVariable Long id) {
        log.debug("REST request to delete StudentClass : {}", id);
        studentClassService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
