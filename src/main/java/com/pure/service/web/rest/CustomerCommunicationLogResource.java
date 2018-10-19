package com.pure.service.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pure.service.domain.CustomerCommunicationLog;
import com.pure.service.region.RegionBasedInsert;
import com.pure.service.region.RegionBasedQuery;
import com.pure.service.service.CustomerCommunicationLogQueryService;
import com.pure.service.service.CustomerCommunicationLogService;
import com.pure.service.service.dto.CustomerCommunicationLogCriteria;
import com.pure.service.web.rest.util.HeaderUtil;
import com.pure.service.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing CustomerCommunicationLog.
 */
@RestController
@RequestMapping("/api")
public class CustomerCommunicationLogResource {

    private final Logger log = LoggerFactory.getLogger(CustomerCommunicationLogResource.class);

    private static final String ENTITY_NAME = "customerCommunicationLog";

    private final CustomerCommunicationLogService customerCommunicationLogService;

    private final CustomerCommunicationLogQueryService customerCommunicationLogQueryService;

    public CustomerCommunicationLogResource(CustomerCommunicationLogService customerCommunicationLogService, CustomerCommunicationLogQueryService customerCommunicationLogQueryService) {
        this.customerCommunicationLogService = customerCommunicationLogService;
        this.customerCommunicationLogQueryService = customerCommunicationLogQueryService;
    }

    /**
     * POST  /customer-communication-logs : Create a new customerCommunicationLog.
     *
     * @param customerCommunicationLog the customerCommunicationLog to create
     * @return the ResponseEntity with status 201 (Created) and with body the new customerCommunicationLog, or with status 400 (Bad Request) if the customerCommunicationLog has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/customer-communication-logs")
    @Timed
    @RegionBasedInsert
    public ResponseEntity<CustomerCommunicationLog> createCustomerCommunicationLog(@RequestBody CustomerCommunicationLog customerCommunicationLog) throws URISyntaxException {
        log.debug("REST request to save CustomerCommunicationLog : {}", customerCommunicationLog);
        if (customerCommunicationLog.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new customerCommunicationLog cannot already have an ID")).body(null);
        }
        CustomerCommunicationLog result = customerCommunicationLogService.save(customerCommunicationLog);
        return ResponseEntity.created(new URI("/api/customer-communication-logs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /customer-communication-logs : Updates an existing customerCommunicationLog.
     *
     * @param customerCommunicationLog the customerCommunicationLog to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated customerCommunicationLog,
     * or with status 400 (Bad Request) if the customerCommunicationLog is not valid,
     * or with status 500 (Internal Server Error) if the customerCommunicationLog couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/customer-communication-logs")
    @Timed
    public ResponseEntity<CustomerCommunicationLog> updateCustomerCommunicationLog(@RequestBody CustomerCommunicationLog customerCommunicationLog) throws URISyntaxException {
        log.debug("REST request to update CustomerCommunicationLog : {}", customerCommunicationLog);
        if (customerCommunicationLog.getId() == null) {
            return createCustomerCommunicationLog(customerCommunicationLog);
        }
        CustomerCommunicationLog result = customerCommunicationLogService.save(customerCommunicationLog);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, customerCommunicationLog.getId().toString()))
            .body(result);
    }

    /**
     * GET  /customer-communication-logs : get all the customerCommunicationLogs.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of customerCommunicationLogs in body
     */
    @GetMapping("/customer-communication-logs")
    @Timed
    @RegionBasedQuery
    public ResponseEntity<List<CustomerCommunicationLog>> getAllCustomerCommunicationLogs(CustomerCommunicationLogCriteria criteria,@ApiParam Pageable pageable) {
        log.debug("REST request to get CustomerCommunicationLogs by criteria: {}", criteria);
        Page<CustomerCommunicationLog> page = customerCommunicationLogQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/customer-communication-logs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /customer-communication-logs/:id : get the "id" customerCommunicationLog.
     *
     * @param id the id of the customerCommunicationLog to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the customerCommunicationLog, or with status 404 (Not Found)
     */
    @GetMapping("/customer-communication-logs/{id}")
    @Timed
    public ResponseEntity<CustomerCommunicationLog> getCustomerCommunicationLog(@PathVariable Long id) {
        log.debug("REST request to get CustomerCommunicationLog : {}", id);
        CustomerCommunicationLog customerCommunicationLog = customerCommunicationLogService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(customerCommunicationLog));
    }

    /**
     * DELETE  /customer-communication-logs/:id : delete the "id" customerCommunicationLog.
     *
     * @param id the id of the customerCommunicationLog to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/customer-communication-logs/{id}")
    @Timed
    public ResponseEntity<Void> deleteCustomerCommunicationLog(@PathVariable Long id) {
        log.debug("REST request to delete CustomerCommunicationLog : {}", id);
        customerCommunicationLogService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
