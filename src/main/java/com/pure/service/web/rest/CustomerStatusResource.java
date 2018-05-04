package com.pure.service.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pure.service.domain.CustomerStatus;
import com.pure.service.service.CustomerStatusService;
import com.pure.service.web.rest.util.HeaderUtil;
import com.pure.service.web.rest.util.PaginationUtil;
import com.pure.service.service.dto.CustomerStatusCriteria;
import com.pure.service.service.CustomerStatusQueryService;
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
 * REST controller for managing CustomerStatus.
 */
@RestController
@RequestMapping("/api")
public class CustomerStatusResource {

    private final Logger log = LoggerFactory.getLogger(CustomerStatusResource.class);

    private static final String ENTITY_NAME = "customerStatus";

    private final CustomerStatusService customerStatusService;

    private final CustomerStatusQueryService customerStatusQueryService;

    public CustomerStatusResource(CustomerStatusService customerStatusService, CustomerStatusQueryService customerStatusQueryService) {
        this.customerStatusService = customerStatusService;
        this.customerStatusQueryService = customerStatusQueryService;
    }

    /**
     * POST  /customer-statuses : Create a new customerStatus.
     *
     * @param customerStatus the customerStatus to create
     * @return the ResponseEntity with status 201 (Created) and with body the new customerStatus, or with status 400 (Bad Request) if the customerStatus has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/customer-statuses")
    @Timed
    public ResponseEntity<CustomerStatus> createCustomerStatus(@RequestBody CustomerStatus customerStatus) throws URISyntaxException {
        log.debug("REST request to save CustomerStatus : {}", customerStatus);
        if (customerStatus.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new customerStatus cannot already have an ID")).body(null);
        }
        CustomerStatus result = customerStatusService.save(customerStatus);
        return ResponseEntity.created(new URI("/api/customer-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /customer-statuses : Updates an existing customerStatus.
     *
     * @param customerStatus the customerStatus to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated customerStatus,
     * or with status 400 (Bad Request) if the customerStatus is not valid,
     * or with status 500 (Internal Server Error) if the customerStatus couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/customer-statuses")
    @Timed
    public ResponseEntity<CustomerStatus> updateCustomerStatus(@RequestBody CustomerStatus customerStatus) throws URISyntaxException {
        log.debug("REST request to update CustomerStatus : {}", customerStatus);
        if (customerStatus.getId() == null) {
            return createCustomerStatus(customerStatus);
        }
        CustomerStatus result = customerStatusService.save(customerStatus);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, customerStatus.getId().toString()))
            .body(result);
    }

    /**
     * GET  /customer-statuses : get all the customerStatuses.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of customerStatuses in body
     */
    @GetMapping("/customer-statuses")
    @Timed
    public ResponseEntity<List<CustomerStatus>> getAllCustomerStatuses(CustomerStatusCriteria criteria,@ApiParam Pageable pageable) {
        log.debug("REST request to get CustomerStatuses by criteria: {}", criteria);
        Page<CustomerStatus> page = customerStatusQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/customer-statuses");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /customer-statuses/:id : get the "id" customerStatus.
     *
     * @param id the id of the customerStatus to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the customerStatus, or with status 404 (Not Found)
     */
    @GetMapping("/customer-statuses/{id}")
    @Timed
    public ResponseEntity<CustomerStatus> getCustomerStatus(@PathVariable Long id) {
        log.debug("REST request to get CustomerStatus : {}", id);
        CustomerStatus customerStatus = customerStatusService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(customerStatus));
    }

    /**
     * DELETE  /customer-statuses/:id : delete the "id" customerStatus.
     *
     * @param id the id of the customerStatus to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/customer-statuses/{id}")
    @Timed
    public ResponseEntity<Void> deleteCustomerStatus(@PathVariable Long id) {
        log.debug("REST request to delete CustomerStatus : {}", id);
        customerStatusService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
