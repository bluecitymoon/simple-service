package com.pure.service.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pure.service.domain.CustomerCardType;
import com.pure.service.service.CustomerCardTypeService;
import com.pure.service.web.rest.util.HeaderUtil;
import com.pure.service.web.rest.util.PaginationUtil;
import com.pure.service.service.dto.CustomerCardTypeCriteria;
import com.pure.service.service.CustomerCardTypeQueryService;
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
 * REST controller for managing CustomerCardType.
 */
@RestController
@RequestMapping("/api")
public class CustomerCardTypeResource {

    private final Logger log = LoggerFactory.getLogger(CustomerCardTypeResource.class);

    private static final String ENTITY_NAME = "customerCardType";

    private final CustomerCardTypeService customerCardTypeService;

    private final CustomerCardTypeQueryService customerCardTypeQueryService;

    public CustomerCardTypeResource(CustomerCardTypeService customerCardTypeService, CustomerCardTypeQueryService customerCardTypeQueryService) {
        this.customerCardTypeService = customerCardTypeService;
        this.customerCardTypeQueryService = customerCardTypeQueryService;
    }

    /**
     * POST  /customer-card-types : Create a new customerCardType.
     *
     * @param customerCardType the customerCardType to create
     * @return the ResponseEntity with status 201 (Created) and with body the new customerCardType, or with status 400 (Bad Request) if the customerCardType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/customer-card-types")
    @Timed
    public ResponseEntity<CustomerCardType> createCustomerCardType(@RequestBody CustomerCardType customerCardType) throws URISyntaxException {
        log.debug("REST request to save CustomerCardType : {}", customerCardType);
        if (customerCardType.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new customerCardType cannot already have an ID")).body(null);
        }
        CustomerCardType result = customerCardTypeService.save(customerCardType);
        return ResponseEntity.created(new URI("/api/customer-card-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /customer-card-types : Updates an existing customerCardType.
     *
     * @param customerCardType the customerCardType to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated customerCardType,
     * or with status 400 (Bad Request) if the customerCardType is not valid,
     * or with status 500 (Internal Server Error) if the customerCardType couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/customer-card-types")
    @Timed
    public ResponseEntity<CustomerCardType> updateCustomerCardType(@RequestBody CustomerCardType customerCardType) throws URISyntaxException {
        log.debug("REST request to update CustomerCardType : {}", customerCardType);
        if (customerCardType.getId() == null) {
            return createCustomerCardType(customerCardType);
        }
        CustomerCardType result = customerCardTypeService.save(customerCardType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, customerCardType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /customer-card-types : get all the customerCardTypes.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of customerCardTypes in body
     */
    @GetMapping("/customer-card-types")
    @Timed
    public ResponseEntity<List<CustomerCardType>> getAllCustomerCardTypes(CustomerCardTypeCriteria criteria,@ApiParam Pageable pageable) {
        log.debug("REST request to get CustomerCardTypes by criteria: {}", criteria);
        Page<CustomerCardType> page = customerCardTypeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/customer-card-types");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /customer-card-types/:id : get the "id" customerCardType.
     *
     * @param id the id of the customerCardType to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the customerCardType, or with status 404 (Not Found)
     */
    @GetMapping("/customer-card-types/{id}")
    @Timed
    public ResponseEntity<CustomerCardType> getCustomerCardType(@PathVariable Long id) {
        log.debug("REST request to get CustomerCardType : {}", id);
        CustomerCardType customerCardType = customerCardTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(customerCardType));
    }

    /**
     * DELETE  /customer-card-types/:id : delete the "id" customerCardType.
     *
     * @param id the id of the customerCardType to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/customer-card-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteCustomerCardType(@PathVariable Long id) {
        log.debug("REST request to delete CustomerCardType : {}", id);
        customerCardTypeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
