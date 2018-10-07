package com.pure.service.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pure.service.domain.CustomerConsumerType;
import com.pure.service.service.CustomerConsumerTypeService;
import com.pure.service.web.rest.util.HeaderUtil;
import com.pure.service.web.rest.util.PaginationUtil;
import com.pure.service.service.dto.CustomerConsumerTypeCriteria;
import com.pure.service.service.CustomerConsumerTypeQueryService;
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
 * REST controller for managing CustomerConsumerType.
 */
@RestController
@RequestMapping("/api")
public class CustomerConsumerTypeResource {

    private final Logger log = LoggerFactory.getLogger(CustomerConsumerTypeResource.class);

    private static final String ENTITY_NAME = "customerConsumerType";

    private final CustomerConsumerTypeService customerConsumerTypeService;

    private final CustomerConsumerTypeQueryService customerConsumerTypeQueryService;

    public CustomerConsumerTypeResource(CustomerConsumerTypeService customerConsumerTypeService, CustomerConsumerTypeQueryService customerConsumerTypeQueryService) {
        this.customerConsumerTypeService = customerConsumerTypeService;
        this.customerConsumerTypeQueryService = customerConsumerTypeQueryService;
    }

    /**
     * POST  /customer-consumer-types : Create a new customerConsumerType.
     *
     * @param customerConsumerType the customerConsumerType to create
     * @return the ResponseEntity with status 201 (Created) and with body the new customerConsumerType, or with status 400 (Bad Request) if the customerConsumerType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/customer-consumer-types")
    @Timed
    public ResponseEntity<CustomerConsumerType> createCustomerConsumerType(@RequestBody CustomerConsumerType customerConsumerType) throws URISyntaxException {
        log.debug("REST request to save CustomerConsumerType : {}", customerConsumerType);
        if (customerConsumerType.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new customerConsumerType cannot already have an ID")).body(null);
        }
        CustomerConsumerType result = customerConsumerTypeService.save(customerConsumerType);
        return ResponseEntity.created(new URI("/api/customer-consumer-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /customer-consumer-types : Updates an existing customerConsumerType.
     *
     * @param customerConsumerType the customerConsumerType to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated customerConsumerType,
     * or with status 400 (Bad Request) if the customerConsumerType is not valid,
     * or with status 500 (Internal Server Error) if the customerConsumerType couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/customer-consumer-types")
    @Timed
    public ResponseEntity<CustomerConsumerType> updateCustomerConsumerType(@RequestBody CustomerConsumerType customerConsumerType) throws URISyntaxException {
        log.debug("REST request to update CustomerConsumerType : {}", customerConsumerType);
        if (customerConsumerType.getId() == null) {
            return createCustomerConsumerType(customerConsumerType);
        }
        CustomerConsumerType result = customerConsumerTypeService.save(customerConsumerType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, customerConsumerType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /customer-consumer-types : get all the customerConsumerTypes.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of customerConsumerTypes in body
     */
    @GetMapping("/customer-consumer-types")
    @Timed
    public ResponseEntity<List<CustomerConsumerType>> getAllCustomerConsumerTypes(CustomerConsumerTypeCriteria criteria,@ApiParam Pageable pageable) {
        log.debug("REST request to get CustomerConsumerTypes by criteria: {}", criteria);
        Page<CustomerConsumerType> page = customerConsumerTypeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/customer-consumer-types");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /customer-consumer-types/:id : get the "id" customerConsumerType.
     *
     * @param id the id of the customerConsumerType to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the customerConsumerType, or with status 404 (Not Found)
     */
    @GetMapping("/customer-consumer-types/{id}")
    @Timed
    public ResponseEntity<CustomerConsumerType> getCustomerConsumerType(@PathVariable Long id) {
        log.debug("REST request to get CustomerConsumerType : {}", id);
        CustomerConsumerType customerConsumerType = customerConsumerTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(customerConsumerType));
    }

    /**
     * DELETE  /customer-consumer-types/:id : delete the "id" customerConsumerType.
     *
     * @param id the id of the customerConsumerType to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/customer-consumer-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteCustomerConsumerType(@PathVariable Long id) {
        log.debug("REST request to delete CustomerConsumerType : {}", id);
        customerConsumerTypeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
