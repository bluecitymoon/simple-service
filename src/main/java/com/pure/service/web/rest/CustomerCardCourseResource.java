package com.pure.service.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pure.service.domain.CustomerCardCourse;
import com.pure.service.service.CustomerCardCourseService;
import com.pure.service.web.rest.util.HeaderUtil;
import com.pure.service.web.rest.util.PaginationUtil;
import com.pure.service.service.dto.CustomerCardCourseCriteria;
import com.pure.service.service.CustomerCardCourseQueryService;
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
 * REST controller for managing CustomerCardCourse.
 */
@RestController
@RequestMapping("/api")
public class CustomerCardCourseResource {

    private final Logger log = LoggerFactory.getLogger(CustomerCardCourseResource.class);

    private static final String ENTITY_NAME = "customerCardCourse";

    private final CustomerCardCourseService customerCardCourseService;

    private final CustomerCardCourseQueryService customerCardCourseQueryService;

    public CustomerCardCourseResource(CustomerCardCourseService customerCardCourseService, CustomerCardCourseQueryService customerCardCourseQueryService) {
        this.customerCardCourseService = customerCardCourseService;
        this.customerCardCourseQueryService = customerCardCourseQueryService;
    }

    /**
     * POST  /customer-card-courses : Create a new customerCardCourse.
     *
     * @param customerCardCourse the customerCardCourse to create
     * @return the ResponseEntity with status 201 (Created) and with body the new customerCardCourse, or with status 400 (Bad Request) if the customerCardCourse has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/customer-card-courses")
    @Timed
    public ResponseEntity<CustomerCardCourse> createCustomerCardCourse(@RequestBody CustomerCardCourse customerCardCourse) throws URISyntaxException {
        log.debug("REST request to save CustomerCardCourse : {}", customerCardCourse);
        if (customerCardCourse.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new customerCardCourse cannot already have an ID")).body(null);
        }
        CustomerCardCourse result = customerCardCourseService.save(customerCardCourse);
        return ResponseEntity.created(new URI("/api/customer-card-courses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /customer-card-courses : Updates an existing customerCardCourse.
     *
     * @param customerCardCourse the customerCardCourse to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated customerCardCourse,
     * or with status 400 (Bad Request) if the customerCardCourse is not valid,
     * or with status 500 (Internal Server Error) if the customerCardCourse couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/customer-card-courses")
    @Timed
    public ResponseEntity<CustomerCardCourse> updateCustomerCardCourse(@RequestBody CustomerCardCourse customerCardCourse) throws URISyntaxException {
        log.debug("REST request to update CustomerCardCourse : {}", customerCardCourse);
        if (customerCardCourse.getId() == null) {
            return createCustomerCardCourse(customerCardCourse);
        }
        CustomerCardCourse result = customerCardCourseService.save(customerCardCourse);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, customerCardCourse.getId().toString()))
            .body(result);
    }

    /**
     * GET  /customer-card-courses : get all the customerCardCourses.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of customerCardCourses in body
     */
    @GetMapping("/customer-card-courses")
    @Timed
    public ResponseEntity<List<CustomerCardCourse>> getAllCustomerCardCourses(CustomerCardCourseCriteria criteria,@ApiParam Pageable pageable) {
        log.debug("REST request to get CustomerCardCourses by criteria: {}", criteria);
        Page<CustomerCardCourse> page = customerCardCourseQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/customer-card-courses");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /customer-card-courses/:id : get the "id" customerCardCourse.
     *
     * @param id the id of the customerCardCourse to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the customerCardCourse, or with status 404 (Not Found)
     */
    @GetMapping("/customer-card-courses/{id}")
    @Timed
    public ResponseEntity<CustomerCardCourse> getCustomerCardCourse(@PathVariable Long id) {
        log.debug("REST request to get CustomerCardCourse : {}", id);
        CustomerCardCourse customerCardCourse = customerCardCourseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(customerCardCourse));
    }

    /**
     * DELETE  /customer-card-courses/:id : delete the "id" customerCardCourse.
     *
     * @param id the id of the customerCardCourse to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/customer-card-courses/{id}")
    @Timed
    public ResponseEntity<Void> deleteCustomerCardCourse(@PathVariable Long id) {
        log.debug("REST request to delete CustomerCardCourse : {}", id);
        customerCardCourseService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
