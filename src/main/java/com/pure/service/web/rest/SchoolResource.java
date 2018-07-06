package com.pure.service.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pure.service.domain.School;
import com.pure.service.service.SchoolService;
import com.pure.service.web.rest.util.HeaderUtil;
import com.pure.service.web.rest.util.PaginationUtil;
import com.pure.service.service.dto.SchoolCriteria;
import com.pure.service.service.SchoolQueryService;
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
 * REST controller for managing School.
 */
@RestController
@RequestMapping("/api")
public class SchoolResource {

    private final Logger log = LoggerFactory.getLogger(SchoolResource.class);

    private static final String ENTITY_NAME = "school";

    private final SchoolService schoolService;

    private final SchoolQueryService schoolQueryService;

    public SchoolResource(SchoolService schoolService, SchoolQueryService schoolQueryService) {
        this.schoolService = schoolService;
        this.schoolQueryService = schoolQueryService;
    }

    /**
     * POST  /schools : Create a new school.
     *
     * @param school the school to create
     * @return the ResponseEntity with status 201 (Created) and with body the new school, or with status 400 (Bad Request) if the school has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/schools")
    @Timed
    public ResponseEntity<School> createSchool(@RequestBody School school) throws URISyntaxException {
        log.debug("REST request to save School : {}", school);
        if (school.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new school cannot already have an ID")).body(null);
        }
        School result = schoolService.save(school);
        return ResponseEntity.created(new URI("/api/schools/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /schools : Updates an existing school.
     *
     * @param school the school to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated school,
     * or with status 400 (Bad Request) if the school is not valid,
     * or with status 500 (Internal Server Error) if the school couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/schools")
    @Timed
    public ResponseEntity<School> updateSchool(@RequestBody School school) throws URISyntaxException {
        log.debug("REST request to update School : {}", school);
        if (school.getId() == null) {
            return createSchool(school);
        }
        School result = schoolService.save(school);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, school.getId().toString()))
            .body(result);
    }

    /**
     * GET  /schools : get all the schools.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of schools in body
     */
    @GetMapping("/schools")
    @Timed
    public ResponseEntity<List<School>> getAllSchools(SchoolCriteria criteria,@ApiParam Pageable pageable) {
        log.debug("REST request to get Schools by criteria: {}", criteria);
        Page<School> page = schoolQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/schools");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /schools/:id : get the "id" school.
     *
     * @param id the id of the school to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the school, or with status 404 (Not Found)
     */
    @GetMapping("/schools/{id}")
    @Timed
    public ResponseEntity<School> getSchool(@PathVariable Long id) {
        log.debug("REST request to get School : {}", id);
        School school = schoolService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(school));
    }

    /**
     * DELETE  /schools/:id : delete the "id" school.
     *
     * @param id the id of the school to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/schools/{id}")
    @Timed
    public ResponseEntity<Void> deleteSchool(@PathVariable Long id) {
        log.debug("REST request to delete School : {}", id);
        schoolService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
