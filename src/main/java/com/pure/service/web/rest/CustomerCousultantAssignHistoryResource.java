package com.pure.service.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pure.service.domain.CustomerCousultantAssignHistory;
import com.pure.service.service.CustomerCousultantAssignHistoryService;
import com.pure.service.web.rest.util.HeaderUtil;
import com.pure.service.web.rest.util.PaginationUtil;
import com.pure.service.service.dto.CustomerCousultantAssignHistoryCriteria;
import com.pure.service.service.CustomerCousultantAssignHistoryQueryService;
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
 * REST controller for managing CustomerCousultantAssignHistory.
 */
@RestController
@RequestMapping("/api")
public class CustomerCousultantAssignHistoryResource {

    private final Logger log = LoggerFactory.getLogger(CustomerCousultantAssignHistoryResource.class);

    private static final String ENTITY_NAME = "customerCousultantAssignHistory";

    private final CustomerCousultantAssignHistoryService customerCousultantAssignHistoryService;

    private final CustomerCousultantAssignHistoryQueryService customerCousultantAssignHistoryQueryService;

    public CustomerCousultantAssignHistoryResource(CustomerCousultantAssignHistoryService customerCousultantAssignHistoryService, CustomerCousultantAssignHistoryQueryService customerCousultantAssignHistoryQueryService) {
        this.customerCousultantAssignHistoryService = customerCousultantAssignHistoryService;
        this.customerCousultantAssignHistoryQueryService = customerCousultantAssignHistoryQueryService;
    }

    /**
     * POST  /customer-cousultant-assign-histories : Create a new customerCousultantAssignHistory.
     *
     * @param customerCousultantAssignHistory the customerCousultantAssignHistory to create
     * @return the ResponseEntity with status 201 (Created) and with body the new customerCousultantAssignHistory, or with status 400 (Bad Request) if the customerCousultantAssignHistory has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/customer-cousultant-assign-histories")
    @Timed
    public ResponseEntity<CustomerCousultantAssignHistory> createCustomerCousultantAssignHistory(@RequestBody CustomerCousultantAssignHistory customerCousultantAssignHistory) throws URISyntaxException {
        log.debug("REST request to save CustomerCousultantAssignHistory : {}", customerCousultantAssignHistory);
        if (customerCousultantAssignHistory.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new customerCousultantAssignHistory cannot already have an ID")).body(null);
        }
        CustomerCousultantAssignHistory result = customerCousultantAssignHistoryService.save(customerCousultantAssignHistory);
        return ResponseEntity.created(new URI("/api/customer-cousultant-assign-histories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /customer-cousultant-assign-histories : Updates an existing customerCousultantAssignHistory.
     *
     * @param customerCousultantAssignHistory the customerCousultantAssignHistory to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated customerCousultantAssignHistory,
     * or with status 400 (Bad Request) if the customerCousultantAssignHistory is not valid,
     * or with status 500 (Internal Server Error) if the customerCousultantAssignHistory couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/customer-cousultant-assign-histories")
    @Timed
    public ResponseEntity<CustomerCousultantAssignHistory> updateCustomerCousultantAssignHistory(@RequestBody CustomerCousultantAssignHistory customerCousultantAssignHistory) throws URISyntaxException {
        log.debug("REST request to update CustomerCousultantAssignHistory : {}", customerCousultantAssignHistory);
        if (customerCousultantAssignHistory.getId() == null) {
            return createCustomerCousultantAssignHistory(customerCousultantAssignHistory);
        }
        CustomerCousultantAssignHistory result = customerCousultantAssignHistoryService.save(customerCousultantAssignHistory);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, customerCousultantAssignHistory.getId().toString()))
            .body(result);
    }

    /**
     * GET  /customer-cousultant-assign-histories : get all the customerCousultantAssignHistories.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of customerCousultantAssignHistories in body
     */
    @GetMapping("/customer-cousultant-assign-histories")
    @Timed
    public ResponseEntity<List<CustomerCousultantAssignHistory>> getAllCustomerCousultantAssignHistories(CustomerCousultantAssignHistoryCriteria criteria,@ApiParam Pageable pageable) {
        log.debug("REST request to get CustomerCousultantAssignHistories by criteria: {}", criteria);
        Page<CustomerCousultantAssignHistory> page = customerCousultantAssignHistoryQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/customer-cousultant-assign-histories");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /customer-cousultant-assign-histories/:id : get the "id" customerCousultantAssignHistory.
     *
     * @param id the id of the customerCousultantAssignHistory to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the customerCousultantAssignHistory, or with status 404 (Not Found)
     */
    @GetMapping("/customer-cousultant-assign-histories/{id}")
    @Timed
    public ResponseEntity<CustomerCousultantAssignHistory> getCustomerCousultantAssignHistory(@PathVariable Long id) {
        log.debug("REST request to get CustomerCousultantAssignHistory : {}", id);
        CustomerCousultantAssignHistory customerCousultantAssignHistory = customerCousultantAssignHistoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(customerCousultantAssignHistory));
    }

    /**
     * DELETE  /customer-cousultant-assign-histories/:id : delete the "id" customerCousultantAssignHistory.
     *
     * @param id the id of the customerCousultantAssignHistory to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/customer-cousultant-assign-histories/{id}")
    @Timed
    public ResponseEntity<Void> deleteCustomerCousultantAssignHistory(@PathVariable Long id) {
        log.debug("REST request to delete CustomerCousultantAssignHistory : {}", id);
        customerCousultantAssignHistoryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
