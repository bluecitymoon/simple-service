package com.pure.service.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pure.service.domain.CustomerTrackTask;
import com.pure.service.service.CustomerService;
import com.pure.service.service.CustomerTrackTaskService;
import com.pure.service.web.rest.util.HeaderUtil;
import com.pure.service.web.rest.util.PaginationUtil;
import com.pure.service.service.dto.CustomerTrackTaskCriteria;
import com.pure.service.service.CustomerTrackTaskQueryService;
import io.github.jhipster.service.filter.LongFilter;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
 * REST controller for managing CustomerTrackTask.
 */
@RestController
@RequestMapping("/api")
public class CustomerTrackTaskResource {

    private final Logger log = LoggerFactory.getLogger(CustomerTrackTaskResource.class);

    private static final String ENTITY_NAME = "customerTrackTask";

    private final CustomerTrackTaskService customerTrackTaskService;

    private final CustomerTrackTaskQueryService customerTrackTaskQueryService;

    @Autowired
    private CustomerService customerService;

    public CustomerTrackTaskResource(CustomerTrackTaskService customerTrackTaskService, CustomerTrackTaskQueryService customerTrackTaskQueryService) {
        this.customerTrackTaskService = customerTrackTaskService;
        this.customerTrackTaskQueryService = customerTrackTaskQueryService;
    }

    /**
     * POST  /customer-track-tasks : Create a new customerTrackTask.
     *
     * @param customerTrackTask the customerTrackTask to create
     * @return the ResponseEntity with status 201 (Created) and with body the new customerTrackTask, or with status 400 (Bad Request) if the customerTrackTask has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/customer-track-tasks")
    @Timed
    public ResponseEntity<CustomerTrackTask> createCustomerTrackTask(@RequestBody CustomerTrackTask customerTrackTask) throws URISyntaxException {
        log.debug("REST request to save CustomerTrackTask : {}", customerTrackTask);
        if (customerTrackTask.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new customerTrackTask cannot already have an ID")).body(null);
        }
        CustomerTrackTask result = customerTrackTaskService.save(customerTrackTask);
        return ResponseEntity.created(new URI("/api/customer-track-tasks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /customer-track-tasks : Updates an existing customerTrackTask.
     *
     * @param customerTrackTask the customerTrackTask to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated customerTrackTask,
     * or with status 400 (Bad Request) if the customerTrackTask is not valid,
     * or with status 500 (Internal Server Error) if the customerTrackTask couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/customer-track-tasks")
    @Timed
    public ResponseEntity<CustomerTrackTask> updateCustomerTrackTask(@RequestBody CustomerTrackTask customerTrackTask) throws URISyntaxException {
        log.debug("REST request to update CustomerTrackTask : {}", customerTrackTask);
        if (customerTrackTask.getId() == null) {
            return createCustomerTrackTask(customerTrackTask);
        }
        CustomerTrackTask result = customerTrackTaskService.save(customerTrackTask);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, customerTrackTask.getId().toString()))
            .body(result);
    }

    /**
     * GET  /customer-track-tasks : get all the customerTrackTasks.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of customerTrackTasks in body
     */
    @GetMapping("/customer-track-tasks")
    @Timed
    public ResponseEntity<List<CustomerTrackTask>> getAllCustomerTrackTasks(CustomerTrackTaskCriteria criteria,@ApiParam Pageable pageable) {
        log.debug("REST request to get CustomerTrackTasks by criteria: {}", criteria);
        Page<CustomerTrackTask> page = customerTrackTaskQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/customer-track-tasks");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     *
     * @return the ResponseEntity with status 200 (OK) and the list of customerTrackTasks in body
     */
    @GetMapping("/customer-track-tasks/customer/{cid}")
    @Timed
    public List<CustomerTrackTask> getAllCustomerTrackTasks(@PathVariable Long cid) {
        CustomerTrackTaskCriteria criteria = new CustomerTrackTaskCriteria();

        LongFilter customerIdFilter = new LongFilter();
        customerIdFilter.setEquals(cid);
        criteria.setCustomerId(customerIdFilter);

        return customerTrackTaskQueryService.findByCriteria(criteria);
    }



    /**
     * GET  /customer-track-tasks/:id : get the "id" customerTrackTask.
     *
     * @param id the id of the customerTrackTask to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the customerTrackTask, or with status 404 (Not Found)
     */
    @GetMapping("/customer-track-tasks/{id}")
    @Timed
    public ResponseEntity<CustomerTrackTask> getCustomerTrackTask(@PathVariable Long id) {
        log.debug("REST request to get CustomerTrackTask : {}", id);
        CustomerTrackTask customerTrackTask = customerTrackTaskService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(customerTrackTask));
    }

    @GetMapping("/customer-track-tasks/close/{id}")
    @Timed
    public ResponseEntity<CustomerTrackTask> closeCustomerTrackTask(@PathVariable Long id) {
        log.debug("REST request to get CustomerTrackTask : {}", id);
        CustomerTrackTask customerTrackTask = customerTrackTaskService.findOne(id);

        CustomerTrackTask closedTask = customerTrackTaskService.closeTask(customerTrackTask);

        customerService.updateTrackTaskStatus(closedTask.getCustomer());

        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(closedTask));
    }

    /**
     * DELETE  /customer-track-tasks/:id : delete the "id" customerTrackTask.
     *
     * @param id the id of the customerTrackTask to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/customer-track-tasks/{id}")
    @Timed
    public ResponseEntity<Void> deleteCustomerTrackTask(@PathVariable Long id) {
        log.debug("REST request to delete CustomerTrackTask : {}", id);
        customerTrackTaskService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
