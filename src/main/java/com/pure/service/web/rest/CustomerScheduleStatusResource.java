package com.pure.service.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pure.service.domain.CustomerScheduleStatus;
import com.pure.service.service.CustomerScheduleStatusService;
import com.pure.service.web.rest.util.HeaderUtil;
import com.pure.service.web.rest.util.PaginationUtil;
import com.pure.service.service.dto.CustomerScheduleStatusCriteria;
import com.pure.service.service.CustomerScheduleStatusQueryService;
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
 * REST controller for managing CustomerScheduleStatus.
 */
@RestController
@RequestMapping("/api")
public class CustomerScheduleStatusResource {

    private final Logger log = LoggerFactory.getLogger(CustomerScheduleStatusResource.class);

    private static final String ENTITY_NAME = "customerScheduleStatus";

    private final CustomerScheduleStatusService customerScheduleStatusService;

    private final CustomerScheduleStatusQueryService customerScheduleStatusQueryService;

    public CustomerScheduleStatusResource(CustomerScheduleStatusService customerScheduleStatusService, CustomerScheduleStatusQueryService customerScheduleStatusQueryService) {
        this.customerScheduleStatusService = customerScheduleStatusService;
        this.customerScheduleStatusQueryService = customerScheduleStatusQueryService;
    }

    /**
     * POST  /customer-schedule-statuses : Create a new customerScheduleStatus.
     *
     * @param customerScheduleStatus the customerScheduleStatus to create
     * @return the ResponseEntity with status 201 (Created) and with body the new customerScheduleStatus, or with status 400 (Bad Request) if the customerScheduleStatus has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/customer-schedule-statuses")
    @Timed
    public ResponseEntity<CustomerScheduleStatus> createCustomerScheduleStatus(@RequestBody CustomerScheduleStatus customerScheduleStatus) throws URISyntaxException {
        log.debug("REST request to save CustomerScheduleStatus : {}", customerScheduleStatus);
        if (customerScheduleStatus.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new customerScheduleStatus cannot already have an ID")).body(null);
        }
        CustomerScheduleStatus result = customerScheduleStatusService.save(customerScheduleStatus);
        return ResponseEntity.created(new URI("/api/customer-schedule-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /customer-schedule-statuses : Updates an existing customerScheduleStatus.
     *
     * @param customerScheduleStatus the customerScheduleStatus to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated customerScheduleStatus,
     * or with status 400 (Bad Request) if the customerScheduleStatus is not valid,
     * or with status 500 (Internal Server Error) if the customerScheduleStatus couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/customer-schedule-statuses")
    @Timed
    public ResponseEntity<CustomerScheduleStatus> updateCustomerScheduleStatus(@RequestBody CustomerScheduleStatus customerScheduleStatus) throws URISyntaxException {
        log.debug("REST request to update CustomerScheduleStatus : {}", customerScheduleStatus);
        if (customerScheduleStatus.getId() == null) {
            return createCustomerScheduleStatus(customerScheduleStatus);
        }
        CustomerScheduleStatus result = customerScheduleStatusService.save(customerScheduleStatus);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, customerScheduleStatus.getId().toString()))
            .body(result);
    }

    /**
     * GET  /customer-schedule-statuses : get all the customerScheduleStatuses.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of customerScheduleStatuses in body
     */
    @GetMapping("/customer-schedule-statuses")
    @Timed
    public ResponseEntity<List<CustomerScheduleStatus>> getAllCustomerScheduleStatuses(CustomerScheduleStatusCriteria criteria,@ApiParam Pageable pageable) {
        log.debug("REST request to get CustomerScheduleStatuses by criteria: {}", criteria);
        Page<CustomerScheduleStatus> page = customerScheduleStatusQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/customer-schedule-statuses");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /customer-schedule-statuses/:id : get the "id" customerScheduleStatus.
     *
     * @param id the id of the customerScheduleStatus to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the customerScheduleStatus, or with status 404 (Not Found)
     */
    @GetMapping("/customer-schedule-statuses/{id}")
    @Timed
    public ResponseEntity<CustomerScheduleStatus> getCustomerScheduleStatus(@PathVariable Long id) {
        log.debug("REST request to get CustomerScheduleStatus : {}", id);
        CustomerScheduleStatus customerScheduleStatus = customerScheduleStatusService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(customerScheduleStatus));
    }

    /**
     * DELETE  /customer-schedule-statuses/:id : delete the "id" customerScheduleStatus.
     *
     * @param id the id of the customerScheduleStatus to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/customer-schedule-statuses/{id}")
    @Timed
    public ResponseEntity<Void> deleteCustomerScheduleStatus(@PathVariable Long id) {
        log.debug("REST request to delete CustomerScheduleStatus : {}", id);
        customerScheduleStatusService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
