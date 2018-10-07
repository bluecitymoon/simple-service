package com.pure.service.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pure.service.domain.CustomerConsumerLog;
import com.pure.service.service.CustomerConsumerLogService;
import com.pure.service.web.rest.util.HeaderUtil;
import com.pure.service.web.rest.util.PaginationUtil;
import com.pure.service.service.dto.CustomerConsumerLogCriteria;
import com.pure.service.service.CustomerConsumerLogQueryService;
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
 * REST controller for managing CustomerConsumerLog.
 */
@RestController
@RequestMapping("/api")
public class CustomerConsumerLogResource {

    private final Logger log = LoggerFactory.getLogger(CustomerConsumerLogResource.class);

    private static final String ENTITY_NAME = "customerConsumerLog";

    private final CustomerConsumerLogService customerConsumerLogService;

    private final CustomerConsumerLogQueryService customerConsumerLogQueryService;

    public CustomerConsumerLogResource(CustomerConsumerLogService customerConsumerLogService, CustomerConsumerLogQueryService customerConsumerLogQueryService) {
        this.customerConsumerLogService = customerConsumerLogService;
        this.customerConsumerLogQueryService = customerConsumerLogQueryService;
    }

    /**
     * POST  /customer-consumer-logs : Create a new customerConsumerLog.
     *
     * @param customerConsumerLog the customerConsumerLog to create
     * @return the ResponseEntity with status 201 (Created) and with body the new customerConsumerLog, or with status 400 (Bad Request) if the customerConsumerLog has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/customer-consumer-logs")
    @Timed
    public ResponseEntity<CustomerConsumerLog> createCustomerConsumerLog(@RequestBody CustomerConsumerLog customerConsumerLog) throws URISyntaxException {
        log.debug("REST request to save CustomerConsumerLog : {}", customerConsumerLog);
        if (customerConsumerLog.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new customerConsumerLog cannot already have an ID")).body(null);
        }
        CustomerConsumerLog result = customerConsumerLogService.save(customerConsumerLog);
        return ResponseEntity.created(new URI("/api/customer-consumer-logs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /customer-consumer-logs : Updates an existing customerConsumerLog.
     *
     * @param customerConsumerLog the customerConsumerLog to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated customerConsumerLog,
     * or with status 400 (Bad Request) if the customerConsumerLog is not valid,
     * or with status 500 (Internal Server Error) if the customerConsumerLog couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/customer-consumer-logs")
    @Timed
    public ResponseEntity<CustomerConsumerLog> updateCustomerConsumerLog(@RequestBody CustomerConsumerLog customerConsumerLog) throws URISyntaxException {
        log.debug("REST request to update CustomerConsumerLog : {}", customerConsumerLog);
        if (customerConsumerLog.getId() == null) {
            return createCustomerConsumerLog(customerConsumerLog);
        }
        CustomerConsumerLog result = customerConsumerLogService.save(customerConsumerLog);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, customerConsumerLog.getId().toString()))
            .body(result);
    }

    /**
     * GET  /customer-consumer-logs : get all the customerConsumerLogs.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of customerConsumerLogs in body
     */
    @GetMapping("/customer-consumer-logs")
    @Timed
    public ResponseEntity<List<CustomerConsumerLog>> getAllCustomerConsumerLogs(CustomerConsumerLogCriteria criteria,@ApiParam Pageable pageable) {
        log.debug("REST request to get CustomerConsumerLogs by criteria: {}", criteria);
        Page<CustomerConsumerLog> page = customerConsumerLogQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/customer-consumer-logs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /customer-consumer-logs/:id : get the "id" customerConsumerLog.
     *
     * @param id the id of the customerConsumerLog to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the customerConsumerLog, or with status 404 (Not Found)
     */
    @GetMapping("/customer-consumer-logs/{id}")
    @Timed
    public ResponseEntity<CustomerConsumerLog> getCustomerConsumerLog(@PathVariable Long id) {
        log.debug("REST request to get CustomerConsumerLog : {}", id);
        CustomerConsumerLog customerConsumerLog = customerConsumerLogService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(customerConsumerLog));
    }

    /**
     * DELETE  /customer-consumer-logs/:id : delete the "id" customerConsumerLog.
     *
     * @param id the id of the customerConsumerLog to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/customer-consumer-logs/{id}")
    @Timed
    public ResponseEntity<Void> deleteCustomerConsumerLog(@PathVariable Long id) {
        log.debug("REST request to delete CustomerConsumerLog : {}", id);
        customerConsumerLogService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
