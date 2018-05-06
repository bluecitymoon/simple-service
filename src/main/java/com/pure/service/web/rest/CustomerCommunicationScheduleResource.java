package com.pure.service.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pure.service.domain.CustomerCommunicationSchedule;
import com.pure.service.service.CustomerCommunicationScheduleService;
import com.pure.service.web.rest.util.HeaderUtil;
import com.pure.service.web.rest.util.PaginationUtil;
import com.pure.service.service.dto.CustomerCommunicationScheduleCriteria;
import com.pure.service.service.CustomerCommunicationScheduleQueryService;
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
 * REST controller for managing CustomerCommunicationSchedule.
 */
@RestController
@RequestMapping("/api")
public class CustomerCommunicationScheduleResource {

    private final Logger log = LoggerFactory.getLogger(CustomerCommunicationScheduleResource.class);

    private static final String ENTITY_NAME = "customerCommunicationSchedule";

    private final CustomerCommunicationScheduleService customerCommunicationScheduleService;

    private final CustomerCommunicationScheduleQueryService customerCommunicationScheduleQueryService;

    public CustomerCommunicationScheduleResource(CustomerCommunicationScheduleService customerCommunicationScheduleService, CustomerCommunicationScheduleQueryService customerCommunicationScheduleQueryService) {
        this.customerCommunicationScheduleService = customerCommunicationScheduleService;
        this.customerCommunicationScheduleQueryService = customerCommunicationScheduleQueryService;
    }

    /**
     * POST  /customer-communication-schedules : Create a new customerCommunicationSchedule.
     *
     * @param customerCommunicationSchedule the customerCommunicationSchedule to create
     * @return the ResponseEntity with status 201 (Created) and with body the new customerCommunicationSchedule, or with status 400 (Bad Request) if the customerCommunicationSchedule has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/customer-communication-schedules")
    @Timed
    public ResponseEntity<CustomerCommunicationSchedule> createCustomerCommunicationSchedule(@RequestBody CustomerCommunicationSchedule customerCommunicationSchedule) throws URISyntaxException {
        log.debug("REST request to save CustomerCommunicationSchedule : {}", customerCommunicationSchedule);
        if (customerCommunicationSchedule.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new customerCommunicationSchedule cannot already have an ID")).body(null);
        }
        CustomerCommunicationSchedule result = customerCommunicationScheduleService.save(customerCommunicationSchedule);
        return ResponseEntity.created(new URI("/api/customer-communication-schedules/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PostMapping("/customer-communication-schedules/signin/{id}")
    @Timed
    public ResponseEntity<CustomerCommunicationSchedule> signin(@PathVariable Long id) throws URISyntaxException {
        log.debug("REST request to save sigin in : {}", id);
        if (id == null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idrequired", "Need an ID")).body(null);
        }
        CustomerCommunicationSchedule result = customerCommunicationScheduleService.signin(id);

        return ResponseEntity.created(new URI("/api/customer-communication-schedules/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /customer-communication-schedules : Updates an existing customerCommunicationSchedule.
     *
     * @param customerCommunicationSchedule the customerCommunicationSchedule to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated customerCommunicationSchedule,
     * or with status 400 (Bad Request) if the customerCommunicationSchedule is not valid,
     * or with status 500 (Internal Server Error) if the customerCommunicationSchedule couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/customer-communication-schedules")
    @Timed
    public ResponseEntity<CustomerCommunicationSchedule> updateCustomerCommunicationSchedule(@RequestBody CustomerCommunicationSchedule customerCommunicationSchedule) throws URISyntaxException {
        log.debug("REST request to update CustomerCommunicationSchedule : {}", customerCommunicationSchedule);
        if (customerCommunicationSchedule.getId() == null) {
            return createCustomerCommunicationSchedule(customerCommunicationSchedule);
        }
        CustomerCommunicationSchedule result = customerCommunicationScheduleService.save(customerCommunicationSchedule);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, customerCommunicationSchedule.getId().toString()))
            .body(result);
    }

    /**
     * GET  /customer-communication-schedules : get all the customerCommunicationSchedules.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of customerCommunicationSchedules in body
     */
    @GetMapping("/customer-communication-schedules")
    @Timed
    public ResponseEntity<List<CustomerCommunicationSchedule>> getAllCustomerCommunicationSchedules(CustomerCommunicationScheduleCriteria criteria,@ApiParam Pageable pageable) {
        log.debug("REST request to get CustomerCommunicationSchedules by criteria: {}", criteria);
        Page<CustomerCommunicationSchedule> page = customerCommunicationScheduleQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/customer-communication-schedules");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /customer-communication-schedules/:id : get the "id" customerCommunicationSchedule.
     *
     * @param id the id of the customerCommunicationSchedule to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the customerCommunicationSchedule, or with status 404 (Not Found)
     */
    @GetMapping("/customer-communication-schedules/{id}")
    @Timed
    public ResponseEntity<CustomerCommunicationSchedule> getCustomerCommunicationSchedule(@PathVariable Long id) {
        log.debug("REST request to get CustomerCommunicationSchedule : {}", id);
        CustomerCommunicationSchedule customerCommunicationSchedule = customerCommunicationScheduleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(customerCommunicationSchedule));
    }

    /**
     * DELETE  /customer-communication-schedules/:id : delete the "id" customerCommunicationSchedule.
     *
     * @param id the id of the customerCommunicationSchedule to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/customer-communication-schedules/{id}")
    @Timed
    public ResponseEntity<Void> deleteCustomerCommunicationSchedule(@PathVariable Long id) {
        log.debug("REST request to delete CustomerCommunicationSchedule : {}", id);
        customerCommunicationScheduleService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
