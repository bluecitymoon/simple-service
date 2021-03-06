package com.pure.service.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pure.service.domain.CustomerCommunicationSchedule;
import com.pure.service.domain.User;
import com.pure.service.region.RegionBasedInsert;
import com.pure.service.region.RegionBasedQuery;
import com.pure.service.region.RegionIdStorage;
import com.pure.service.security.SecurityUtils;
import com.pure.service.service.CustomerCommunicationScheduleQueryService;
import com.pure.service.service.CustomerCommunicationScheduleService;
import com.pure.service.service.UserService;
import com.pure.service.service.dto.CustomerCommunicationScheduleCriteria;
import com.pure.service.service.util.DateUtil;
import com.pure.service.web.rest.util.HeaderUtil;
import com.pure.service.web.rest.util.PaginationUtil;
import io.github.jhipster.service.filter.InstantFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @Autowired
    private UserService userService;

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
    @RegionBasedInsert
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

    @PostMapping("/customer-communication-schedules/customersignin/{id}")
    @Timed
    public ResponseEntity<CustomerCommunicationSchedule> customerSignin(@PathVariable Long id) {
        log.debug("REST request to save sigin in : {}", id);
        if (id == null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idrequired", "Need an ID")).body(null);
        }
        CustomerCommunicationSchedule result = customerCommunicationScheduleService.customerSignin(id);

        return ResponseEntity.ok().build();
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

    @PutMapping("/customer-communication-schedules/batchUpdate")
    @Timed
    public ResponseEntity<List<CustomerCommunicationSchedule>> batchUpdateCustomerCommunicationSchedule(@RequestBody List<CustomerCommunicationSchedule> customerCommunicationSchedules) {

        List<CustomerCommunicationSchedule> result = customerCommunicationScheduleService.batchUpdate(customerCommunicationSchedules);
        return ResponseEntity.ok()
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
    @RegionBasedQuery
    public ResponseEntity<List<CustomerCommunicationSchedule>> getAllCustomerCommunicationSchedules(CustomerCommunicationScheduleCriteria criteria,@ApiParam Pageable pageable) {
        log.debug("REST request to get CustomerCommunicationSchedules by criteria: {}", criteria);

        CustomerCommunicationScheduleCriteria customerCommunicationScheduleCriteria = new CustomerCommunicationScheduleCriteria();
        if (criteria.isNull() && !SecurityUtils.isCurrentUserHeadmasterOrAdmin()) {

            customerCommunicationScheduleCriteria.setCustomerCreatedBy(SecurityUtils.getCurrentUserLogin());

        } else {
            customerCommunicationScheduleCriteria = criteria;
        }

        Page<CustomerCommunicationSchedule> page = customerCommunicationScheduleQueryService.findByCriteria(customerCommunicationScheduleCriteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/customer-communication-schedules");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/customer-communication-schedules/today")
    @Timed
    public ResponseEntity<List<CustomerCommunicationSchedule>> getAllTodayCustomerCommunicationSchedules() {
        log.debug("REST request to get CustomerCommunicationSchedules in today");

        CustomerCommunicationScheduleCriteria customerCommunicationScheduleCriteria = new CustomerCommunicationScheduleCriteria();

        User currentUser = userService.getUserWithAuthorities();

        LongFilter longFilter = new LongFilter();
        longFilter.setEquals(currentUser.getId());

        customerCommunicationScheduleCriteria.setFollowerId(longFilter);

        InstantFilter instantFilter = new InstantFilter();
        instantFilter.setGreaterThan(DateUtil.getSimpleTodayInstantBegin());
        instantFilter.setLessThan(DateUtil.getSimpleTodayInstantEnd());

        customerCommunicationScheduleCriteria.setSceduleDate(instantFilter);

        Long regionId = Long.valueOf(RegionIdStorage.getRegionIdContext());
        LongFilter regionIdFilter = new LongFilter();
        regionIdFilter.setEquals(regionId);

        customerCommunicationScheduleCriteria.setRegionId(regionIdFilter);

        List<CustomerCommunicationSchedule> page = customerCommunicationScheduleQueryService.findByCriteria(customerCommunicationScheduleCriteria);

        return new ResponseEntity<>(page, null, HttpStatus.OK);
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
