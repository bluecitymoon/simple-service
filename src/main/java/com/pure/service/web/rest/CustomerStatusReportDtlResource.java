package com.pure.service.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pure.service.domain.CustomerStatusReportDtl;
import com.pure.service.region.RegionBasedInsert;
import com.pure.service.region.RegionBasedQuery;
import com.pure.service.service.CustomerStatusReportDtlService;
import com.pure.service.web.rest.util.HeaderUtil;
import com.pure.service.web.rest.util.PaginationUtil;
import com.pure.service.service.dto.CustomerStatusReportDtlCriteria;
import com.pure.service.service.CustomerStatusReportDtlQueryService;
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
 * REST controller for managing CustomerStatusReportDtl.
 */
@RestController
@RequestMapping("/api")
public class CustomerStatusReportDtlResource {

    private final Logger log = LoggerFactory.getLogger(CustomerStatusReportDtlResource.class);

    private static final String ENTITY_NAME = "customerStatusReportDtl";

    private final CustomerStatusReportDtlService customerStatusReportDtlService;

    private final CustomerStatusReportDtlQueryService customerStatusReportDtlQueryService;

    public CustomerStatusReportDtlResource(CustomerStatusReportDtlService customerStatusReportDtlService, CustomerStatusReportDtlQueryService customerStatusReportDtlQueryService) {
        this.customerStatusReportDtlService = customerStatusReportDtlService;
        this.customerStatusReportDtlQueryService = customerStatusReportDtlQueryService;
    }


    /**
     * POST  /customer-status-report-dtls : Create a new customerStatusReportDtl.
     *
     * @param customerStatusReportDtl the customerStatusReportDtl to create
     * @return the ResponseEntity with status 201 (Created) and with body the new customerStatusReportDtl, or with status 400 (Bad Request) if the customerStatusReportDtl has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/customer-status-report-dtls")
    @Timed
    @RegionBasedInsert
    public ResponseEntity<CustomerStatusReportDtl> createCustomerStatusReportDtl(@RequestBody CustomerStatusReportDtl customerStatusReportDtl) throws URISyntaxException {
        log.debug("REST request to save CustomerStatusReportDtl : {}", customerStatusReportDtl);
        if (customerStatusReportDtl.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new customerStatusReportDtl cannot already have an ID")).body(null);
        }
        CustomerStatusReportDtl result = customerStatusReportDtlService.save(customerStatusReportDtl);
        return ResponseEntity.created(new URI("/api/customer-status-report-dtls/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /customer-status-report-dtls : Updates an existing customerStatusReportDtl.
     *
     * @param customerStatusReportDtl the customerStatusReportDtl to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated customerStatusReportDtl,
     * or with status 400 (Bad Request) if the customerStatusReportDtl is not valid,
     * or with status 500 (Internal Server Error) if the customerStatusReportDtl couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/customer-status-report-dtls")
    @Timed
    public ResponseEntity<CustomerStatusReportDtl> updateCustomerStatusReportDtl(@RequestBody CustomerStatusReportDtl customerStatusReportDtl) throws URISyntaxException {
        log.debug("REST request to update CustomerStatusReportDtl : {}", customerStatusReportDtl);
        if (customerStatusReportDtl.getId() == null) {
            return createCustomerStatusReportDtl(customerStatusReportDtl);
        }
        CustomerStatusReportDtl result = customerStatusReportDtlService.save(customerStatusReportDtl);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, customerStatusReportDtl.getId().toString()))
            .body(result);
    }

    /**
     * GET  /customer-status-report-dtls : get all the customerStatusReportDtls.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of customerStatusReportDtls in body
     */
    @GetMapping("/customer-status-report-dtls")
    @Timed
    @RegionBasedQuery
    public ResponseEntity<List<CustomerStatusReportDtl>> getAllCustomerStatusReportDtls(CustomerStatusReportDtlCriteria criteria,@ApiParam Pageable pageable) {
        log.debug("REST request to get CustomerStatusReportDtls by criteria: {}", criteria);
        Page<CustomerStatusReportDtl> page = customerStatusReportDtlQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/customer-status-report-dtls");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /customer-status-report-dtls/:id : get the "id" customerStatusReportDtl.
     *
     * @param id the id of the customerStatusReportDtl to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the customerStatusReportDtl, or with status 404 (Not Found)
     */
    @GetMapping("/customer-status-report-dtls/{id}")
    @Timed
    public ResponseEntity<CustomerStatusReportDtl> getCustomerStatusReportDtl(@PathVariable Long id) {
        log.debug("REST request to get CustomerStatusReportDtl : {}", id);
        CustomerStatusReportDtl customerStatusReportDtl = customerStatusReportDtlService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(customerStatusReportDtl));
    }

    /**
     * DELETE  /customer-status-report-dtls/:id : delete the "id" customerStatusReportDtl.
     *
     * @param id the id of the customerStatusReportDtl to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/customer-status-report-dtls/{id}")
    @Timed
    public ResponseEntity<Void> deleteCustomerStatusReportDtl(@PathVariable Long id) {
        log.debug("REST request to delete CustomerStatusReportDtl : {}", id);
        customerStatusReportDtlService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
