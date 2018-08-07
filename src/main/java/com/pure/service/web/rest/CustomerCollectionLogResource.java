package com.pure.service.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pure.service.domain.CustomerCollectionLog;
import com.pure.service.service.CustomerCollectionLogService;
import com.pure.service.web.rest.util.HeaderUtil;
import com.pure.service.web.rest.util.PaginationUtil;
import com.pure.service.service.dto.CustomerCollectionLogCriteria;
import com.pure.service.service.CustomerCollectionLogQueryService;
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
 * REST controller for managing CustomerCollectionLog.
 */
@RestController
@RequestMapping("/api")
public class CustomerCollectionLogResource {

    private final Logger log = LoggerFactory.getLogger(CustomerCollectionLogResource.class);

    private static final String ENTITY_NAME = "customerCollectionLog";

    private final CustomerCollectionLogService customerCollectionLogService;

    private final CustomerCollectionLogQueryService customerCollectionLogQueryService;

    public CustomerCollectionLogResource(CustomerCollectionLogService customerCollectionLogService, CustomerCollectionLogQueryService customerCollectionLogQueryService) {
        this.customerCollectionLogService = customerCollectionLogService;
        this.customerCollectionLogQueryService = customerCollectionLogQueryService;
    }

    /**
     * POST  /customer-collection-logs : Create a new customerCollectionLog.
     *
     * @param customerCollectionLog the customerCollectionLog to create
     * @return the ResponseEntity with status 201 (Created) and with body the new customerCollectionLog, or with status 400 (Bad Request) if the customerCollectionLog has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/customer-collection-logs")
    @Timed
    public ResponseEntity<CustomerCollectionLog> createCustomerCollectionLog(@RequestBody CustomerCollectionLog customerCollectionLog) throws URISyntaxException {
        log.debug("REST request to save CustomerCollectionLog : {}", customerCollectionLog);
        if (customerCollectionLog.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new customerCollectionLog cannot already have an ID")).body(null);
        }
        CustomerCollectionLog result = customerCollectionLogService.save(customerCollectionLog);
        return ResponseEntity.created(new URI("/api/customer-collection-logs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /customer-collection-logs : Updates an existing customerCollectionLog.
     *
     * @param customerCollectionLog the customerCollectionLog to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated customerCollectionLog,
     * or with status 400 (Bad Request) if the customerCollectionLog is not valid,
     * or with status 500 (Internal Server Error) if the customerCollectionLog couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/customer-collection-logs")
    @Timed
    public ResponseEntity<CustomerCollectionLog> updateCustomerCollectionLog(@RequestBody CustomerCollectionLog customerCollectionLog) throws URISyntaxException {
        log.debug("REST request to update CustomerCollectionLog : {}", customerCollectionLog);
        if (customerCollectionLog.getId() == null) {
            return createCustomerCollectionLog(customerCollectionLog);
        }
        CustomerCollectionLog result = customerCollectionLogService.save(customerCollectionLog);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, customerCollectionLog.getId().toString()))
            .body(result);
    }

    /**
     * GET  /customer-collection-logs : get all the customerCollectionLogs.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of customerCollectionLogs in body
     */
    @GetMapping("/customer-collection-logs")
    @Timed
    public ResponseEntity<List<CustomerCollectionLog>> getAllCustomerCollectionLogs(CustomerCollectionLogCriteria criteria,@ApiParam Pageable pageable) {
        log.debug("REST request to get CustomerCollectionLogs by criteria: {}", criteria);
        Page<CustomerCollectionLog> page = customerCollectionLogQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/customer-collection-logs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /customer-collection-logs/:id : get the "id" customerCollectionLog.
     *
     * @param id the id of the customerCollectionLog to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the customerCollectionLog, or with status 404 (Not Found)
     */
    @GetMapping("/customer-collection-logs/{id}")
    @Timed
    public ResponseEntity<CustomerCollectionLog> getCustomerCollectionLog(@PathVariable Long id) {
        log.debug("REST request to get CustomerCollectionLog : {}", id);
        CustomerCollectionLog customerCollectionLog = customerCollectionLogService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(customerCollectionLog));
    }

    /**
     * DELETE  /customer-collection-logs/:id : delete the "id" customerCollectionLog.
     *
     * @param id the id of the customerCollectionLog to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/customer-collection-logs/{id}")
    @Timed
    public ResponseEntity<Void> deleteCustomerCollectionLog(@PathVariable Long id) {
        log.debug("REST request to delete CustomerCollectionLog : {}", id);
        customerCollectionLogService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
