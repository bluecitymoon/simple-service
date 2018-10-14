package com.pure.service.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pure.service.domain.CustomerCardUpgradeLog;
import com.pure.service.service.CustomerCardUpgradeLogService;
import com.pure.service.web.rest.util.HeaderUtil;
import com.pure.service.web.rest.util.PaginationUtil;
import com.pure.service.service.dto.CustomerCardUpgradeLogCriteria;
import com.pure.service.service.CustomerCardUpgradeLogQueryService;
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
 * REST controller for managing CustomerCardUpgradeLog.
 */
@RestController
@RequestMapping("/api")
public class CustomerCardUpgradeLogResource {

    private final Logger log = LoggerFactory.getLogger(CustomerCardUpgradeLogResource.class);

    private static final String ENTITY_NAME = "customerCardUpgradeLog";

    private final CustomerCardUpgradeLogService customerCardUpgradeLogService;

    private final CustomerCardUpgradeLogQueryService customerCardUpgradeLogQueryService;

    public CustomerCardUpgradeLogResource(CustomerCardUpgradeLogService customerCardUpgradeLogService, CustomerCardUpgradeLogQueryService customerCardUpgradeLogQueryService) {
        this.customerCardUpgradeLogService = customerCardUpgradeLogService;
        this.customerCardUpgradeLogQueryService = customerCardUpgradeLogQueryService;
    }

    /**
     * POST  /customer-card-upgrade-logs : Create a new customerCardUpgradeLog.
     *
     * @param customerCardUpgradeLog the customerCardUpgradeLog to create
     * @return the ResponseEntity with status 201 (Created) and with body the new customerCardUpgradeLog, or with status 400 (Bad Request) if the customerCardUpgradeLog has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/customer-card-upgrade-logs")
    @Timed
    public ResponseEntity<CustomerCardUpgradeLog> createCustomerCardUpgradeLog(@RequestBody CustomerCardUpgradeLog customerCardUpgradeLog) throws URISyntaxException {
        log.debug("REST request to save CustomerCardUpgradeLog : {}", customerCardUpgradeLog);
        if (customerCardUpgradeLog.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new customerCardUpgradeLog cannot already have an ID")).body(null);
        }
        CustomerCardUpgradeLog result = customerCardUpgradeLogService.save(customerCardUpgradeLog);
        return ResponseEntity.created(new URI("/api/customer-card-upgrade-logs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /customer-card-upgrade-logs : Updates an existing customerCardUpgradeLog.
     *
     * @param customerCardUpgradeLog the customerCardUpgradeLog to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated customerCardUpgradeLog,
     * or with status 400 (Bad Request) if the customerCardUpgradeLog is not valid,
     * or with status 500 (Internal Server Error) if the customerCardUpgradeLog couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/customer-card-upgrade-logs")
    @Timed
    public ResponseEntity<CustomerCardUpgradeLog> updateCustomerCardUpgradeLog(@RequestBody CustomerCardUpgradeLog customerCardUpgradeLog) throws URISyntaxException {
        log.debug("REST request to update CustomerCardUpgradeLog : {}", customerCardUpgradeLog);
        if (customerCardUpgradeLog.getId() == null) {
            return createCustomerCardUpgradeLog(customerCardUpgradeLog);
        }
        CustomerCardUpgradeLog result = customerCardUpgradeLogService.save(customerCardUpgradeLog);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, customerCardUpgradeLog.getId().toString()))
            .body(result);
    }

    /**
     * GET  /customer-card-upgrade-logs : get all the customerCardUpgradeLogs.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of customerCardUpgradeLogs in body
     */
    @GetMapping("/customer-card-upgrade-logs")
    @Timed
    public ResponseEntity<List<CustomerCardUpgradeLog>> getAllCustomerCardUpgradeLogs(CustomerCardUpgradeLogCriteria criteria,@ApiParam Pageable pageable) {
        log.debug("REST request to get CustomerCardUpgradeLogs by criteria: {}", criteria);
        Page<CustomerCardUpgradeLog> page = customerCardUpgradeLogQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/customer-card-upgrade-logs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /customer-card-upgrade-logs/:id : get the "id" customerCardUpgradeLog.
     *
     * @param id the id of the customerCardUpgradeLog to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the customerCardUpgradeLog, or with status 404 (Not Found)
     */
    @GetMapping("/customer-card-upgrade-logs/{id}")
    @Timed
    public ResponseEntity<CustomerCardUpgradeLog> getCustomerCardUpgradeLog(@PathVariable Long id) {
        log.debug("REST request to get CustomerCardUpgradeLog : {}", id);
        CustomerCardUpgradeLog customerCardUpgradeLog = customerCardUpgradeLogService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(customerCardUpgradeLog));
    }

    /**
     * DELETE  /customer-card-upgrade-logs/:id : delete the "id" customerCardUpgradeLog.
     *
     * @param id the id of the customerCardUpgradeLog to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/customer-card-upgrade-logs/{id}")
    @Timed
    public ResponseEntity<Void> deleteCustomerCardUpgradeLog(@PathVariable Long id) {
        log.debug("REST request to delete CustomerCardUpgradeLog : {}", id);
        customerCardUpgradeLogService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
