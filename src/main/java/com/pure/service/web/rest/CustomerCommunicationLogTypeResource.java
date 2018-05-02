package com.pure.service.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pure.service.domain.CustomerCommunicationLogType;

import com.pure.service.repository.CustomerCommunicationLogTypeRepository;
import com.pure.service.web.rest.util.HeaderUtil;
import com.pure.service.web.rest.util.PaginationUtil;
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
 * REST controller for managing CustomerCommunicationLogType.
 */
@RestController
@RequestMapping("/api")
public class CustomerCommunicationLogTypeResource {

    private final Logger log = LoggerFactory.getLogger(CustomerCommunicationLogTypeResource.class);

    private static final String ENTITY_NAME = "customerCommunicationLogType";

    private final CustomerCommunicationLogTypeRepository customerCommunicationLogTypeRepository;

    public CustomerCommunicationLogTypeResource(CustomerCommunicationLogTypeRepository customerCommunicationLogTypeRepository) {
        this.customerCommunicationLogTypeRepository = customerCommunicationLogTypeRepository;
    }

    /**
     * POST  /customer-communication-log-types : Create a new customerCommunicationLogType.
     *
     * @param customerCommunicationLogType the customerCommunicationLogType to create
     * @return the ResponseEntity with status 201 (Created) and with body the new customerCommunicationLogType, or with status 400 (Bad Request) if the customerCommunicationLogType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/customer-communication-log-types")
    @Timed
    public ResponseEntity<CustomerCommunicationLogType> createCustomerCommunicationLogType(@RequestBody CustomerCommunicationLogType customerCommunicationLogType) throws URISyntaxException {
        log.debug("REST request to save CustomerCommunicationLogType : {}", customerCommunicationLogType);
        if (customerCommunicationLogType.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new customerCommunicationLogType cannot already have an ID")).body(null);
        }
        CustomerCommunicationLogType result = customerCommunicationLogTypeRepository.save(customerCommunicationLogType);
        return ResponseEntity.created(new URI("/api/customer-communication-log-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /customer-communication-log-types : Updates an existing customerCommunicationLogType.
     *
     * @param customerCommunicationLogType the customerCommunicationLogType to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated customerCommunicationLogType,
     * or with status 400 (Bad Request) if the customerCommunicationLogType is not valid,
     * or with status 500 (Internal Server Error) if the customerCommunicationLogType couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/customer-communication-log-types")
    @Timed
    public ResponseEntity<CustomerCommunicationLogType> updateCustomerCommunicationLogType(@RequestBody CustomerCommunicationLogType customerCommunicationLogType) throws URISyntaxException {
        log.debug("REST request to update CustomerCommunicationLogType : {}", customerCommunicationLogType);
        if (customerCommunicationLogType.getId() == null) {
            return createCustomerCommunicationLogType(customerCommunicationLogType);
        }
        CustomerCommunicationLogType result = customerCommunicationLogTypeRepository.save(customerCommunicationLogType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, customerCommunicationLogType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /customer-communication-log-types : get all the customerCommunicationLogTypes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of customerCommunicationLogTypes in body
     */
    @GetMapping("/customer-communication-log-types")
    @Timed
    public ResponseEntity<List<CustomerCommunicationLogType>> getAllCustomerCommunicationLogTypes(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of CustomerCommunicationLogTypes");
        Page<CustomerCommunicationLogType> page = customerCommunicationLogTypeRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/customer-communication-log-types");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /customer-communication-log-types/:id : get the "id" customerCommunicationLogType.
     *
     * @param id the id of the customerCommunicationLogType to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the customerCommunicationLogType, or with status 404 (Not Found)
     */
    @GetMapping("/customer-communication-log-types/{id}")
    @Timed
    public ResponseEntity<CustomerCommunicationLogType> getCustomerCommunicationLogType(@PathVariable Long id) {
        log.debug("REST request to get CustomerCommunicationLogType : {}", id);
        CustomerCommunicationLogType customerCommunicationLogType = customerCommunicationLogTypeRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(customerCommunicationLogType));
    }

    /**
     * DELETE  /customer-communication-log-types/:id : delete the "id" customerCommunicationLogType.
     *
     * @param id the id of the customerCommunicationLogType to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/customer-communication-log-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteCustomerCommunicationLogType(@PathVariable Long id) {
        log.debug("REST request to delete CustomerCommunicationLogType : {}", id);
        customerCommunicationLogTypeRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
