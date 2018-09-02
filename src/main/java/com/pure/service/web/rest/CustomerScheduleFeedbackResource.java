package com.pure.service.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pure.service.domain.CustomerScheduleFeedback;
import com.pure.service.service.CustomerScheduleFeedbackService;
import com.pure.service.web.rest.util.HeaderUtil;
import com.pure.service.web.rest.util.PaginationUtil;
import com.pure.service.service.dto.CustomerScheduleFeedbackCriteria;
import com.pure.service.service.CustomerScheduleFeedbackQueryService;
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
 * REST controller for managing CustomerScheduleFeedback.
 */
@RestController
@RequestMapping("/api")
public class CustomerScheduleFeedbackResource {

    private final Logger log = LoggerFactory.getLogger(CustomerScheduleFeedbackResource.class);

    private static final String ENTITY_NAME = "customerScheduleFeedback";

    private final CustomerScheduleFeedbackService customerScheduleFeedbackService;

    private final CustomerScheduleFeedbackQueryService customerScheduleFeedbackQueryService;

    public CustomerScheduleFeedbackResource(CustomerScheduleFeedbackService customerScheduleFeedbackService, CustomerScheduleFeedbackQueryService customerScheduleFeedbackQueryService) {
        this.customerScheduleFeedbackService = customerScheduleFeedbackService;
        this.customerScheduleFeedbackQueryService = customerScheduleFeedbackQueryService;
    }

    /**
     * POST  /customer-schedule-feedbacks : Create a new customerScheduleFeedback.
     *
     * @param customerScheduleFeedback the customerScheduleFeedback to create
     * @return the ResponseEntity with status 201 (Created) and with body the new customerScheduleFeedback, or with status 400 (Bad Request) if the customerScheduleFeedback has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/customer-schedule-feedbacks")
    @Timed
    public ResponseEntity<CustomerScheduleFeedback> createCustomerScheduleFeedback(@RequestBody CustomerScheduleFeedback customerScheduleFeedback) throws URISyntaxException {
        log.debug("REST request to save CustomerScheduleFeedback : {}", customerScheduleFeedback);
        if (customerScheduleFeedback.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new customerScheduleFeedback cannot already have an ID")).body(null);
        }
        CustomerScheduleFeedback result = customerScheduleFeedbackService.save(customerScheduleFeedback);
        return ResponseEntity.created(new URI("/api/customer-schedule-feedbacks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /customer-schedule-feedbacks : Updates an existing customerScheduleFeedback.
     *
     * @param customerScheduleFeedback the customerScheduleFeedback to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated customerScheduleFeedback,
     * or with status 400 (Bad Request) if the customerScheduleFeedback is not valid,
     * or with status 500 (Internal Server Error) if the customerScheduleFeedback couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/customer-schedule-feedbacks")
    @Timed
    public ResponseEntity<CustomerScheduleFeedback> updateCustomerScheduleFeedback(@RequestBody CustomerScheduleFeedback customerScheduleFeedback) throws URISyntaxException {
        log.debug("REST request to update CustomerScheduleFeedback : {}", customerScheduleFeedback);
        if (customerScheduleFeedback.getId() == null) {
            return createCustomerScheduleFeedback(customerScheduleFeedback);
        }
        CustomerScheduleFeedback result = customerScheduleFeedbackService.save(customerScheduleFeedback);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, customerScheduleFeedback.getId().toString()))
            .body(result);
    }

    /**
     * GET  /customer-schedule-feedbacks : get all the customerScheduleFeedbacks.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of customerScheduleFeedbacks in body
     */
    @GetMapping("/customer-schedule-feedbacks")
    @Timed
    public ResponseEntity<List<CustomerScheduleFeedback>> getAllCustomerScheduleFeedbacks(CustomerScheduleFeedbackCriteria criteria,@ApiParam Pageable pageable) {
        log.debug("REST request to get CustomerScheduleFeedbacks by criteria: {}", criteria);
        Page<CustomerScheduleFeedback> page = customerScheduleFeedbackQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/customer-schedule-feedbacks");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /customer-schedule-feedbacks/:id : get the "id" customerScheduleFeedback.
     *
     * @param id the id of the customerScheduleFeedback to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the customerScheduleFeedback, or with status 404 (Not Found)
     */
    @GetMapping("/customer-schedule-feedbacks/{id}")
    @Timed
    public ResponseEntity<CustomerScheduleFeedback> getCustomerScheduleFeedback(@PathVariable Long id) {
        log.debug("REST request to get CustomerScheduleFeedback : {}", id);
        CustomerScheduleFeedback customerScheduleFeedback = customerScheduleFeedbackService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(customerScheduleFeedback));
    }

    /**
     * DELETE  /customer-schedule-feedbacks/:id : delete the "id" customerScheduleFeedback.
     *
     * @param id the id of the customerScheduleFeedback to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/customer-schedule-feedbacks/{id}")
    @Timed
    public ResponseEntity<Void> deleteCustomerScheduleFeedback(@PathVariable Long id) {
        log.debug("REST request to delete CustomerScheduleFeedback : {}", id);
        customerScheduleFeedbackService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
