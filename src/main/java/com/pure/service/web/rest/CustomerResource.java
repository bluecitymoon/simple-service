package com.pure.service.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pure.service.domain.Customer;
import com.pure.service.domain.CustomerCommunicationLog;
import com.pure.service.domain.User;
import com.pure.service.region.RegionBasedInsert;
import com.pure.service.region.RegionBasedQuery;
import com.pure.service.security.SecurityUtils;
import com.pure.service.service.CustomerCommunicationLogQueryService;
import com.pure.service.service.CustomerQueryService;
import com.pure.service.service.CustomerService;
import com.pure.service.service.UserService;
import com.pure.service.service.dto.CustomerCommunicationLogCriteria;
import com.pure.service.service.dto.CustomerCriteria;
import com.pure.service.service.dto.CustomerFollowLog;
import com.pure.service.service.dto.dto.ChannelReportElement;
import com.pure.service.service.dto.dto.CombinedReport;
import com.pure.service.service.dto.dto.Overview;
import com.pure.service.service.dto.request.BatchAssignRequest;
import com.pure.service.service.dto.request.CustomerStatusRequest;
import com.pure.service.service.dto.request.MergeCustomer;
import com.pure.service.service.dto.request.StatusReportElement;
import com.pure.service.web.rest.util.HeaderUtil;
import com.pure.service.web.rest.util.PaginationUtil;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
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
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Customer.
 */
@RestController
@RequestMapping("/api")
public class CustomerResource {

    private final Logger log = LoggerFactory.getLogger(CustomerResource.class);

    private static final String ENTITY_NAME = "customer";

    private final CustomerService customerService;

    private final CustomerQueryService customerQueryService;
    private final CustomerCommunicationLogQueryService logQueryService;

    private final UserService userService;

    public CustomerResource(CustomerService customerService, CustomerQueryService customerQueryService, UserService userService, CustomerCommunicationLogQueryService logQueryService) {
        this.customerService = customerService;
        this.customerQueryService = customerQueryService;
        this.userService = userService;
        this.logQueryService = logQueryService;
    }

    /**
     * POST  /customers : Create a new customer.
     *
     * @param customer the customer to create
     * @return the ResponseEntity with status 201 (Created) and with body the new customer, or with status 400 (Bad Request) if the customer has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/customers")
    @Timed
    @RegionBasedInsert
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) throws URISyntaxException {
        log.debug("REST request to save Customer : {}", customer);
        if (customer.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new customer cannot already have an ID")).body(null);
        }
        Customer result = customerService.save(customer);
        return ResponseEntity.created(new URI("/api/customers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PostMapping("/customers/merge")
    @Timed
    public ResponseEntity<Customer> mergeCustomer( @RequestBody MergeCustomer customer) throws URISyntaxException {
        log.debug("REST request to merge Customer : {}", customer);
//        if (customer.getId() != null) {
//            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new customer cannot already have an ID")).body(null);
//        }
        Customer result = customerService.mergeCustomer(customer.getOriginalId(), customer.getTargetId(), customer.getCustomer());

        return ResponseEntity.created(new URI("/api/customers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }
    /**
     * PUT  /customers : Updates an existing customer.
     *
     * @param customer the customer to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated customer,
     * or with status 400 (Bad Request) if the customer is not valid,
     * or with status 500 (Internal Server Error) if the customer couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/customers")
    @Timed
    public ResponseEntity<Customer> updateCustomer(@RequestBody Customer customer) throws URISyntaxException {
        log.debug("REST request to update Customer : {}", customer);
        if (customer.getId() == null) {
            return createCustomer(customer);
        }
        Customer result = customerService.save(customer);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, customer.getId().toString()))
            .body(result);
    }

    /**
     * GET  /customers : get all the customers.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of customers in body
     */
    @GetMapping("/customers")
    @Timed
    @RegionBasedQuery
    public ResponseEntity<List<Customer>> getAllCustomers(CustomerCriteria criteria, @ApiParam Pageable pageable) {
        log.debug("REST request to get Customers by criteria: {}", criteria);

        String department = criteria.getDepartment();

        if (StringUtils.isEmpty(department)) {
            return ResponseEntity.badRequest().build();
        }

        User currentUser = userService.getUserWithAuthorities();
        //Only Admin and Headmaster can have all the new orders
        if (!SecurityUtils.isCurrentUserHeadmasterOrAdminOrSalesManager()) {

            LongFilter userIdFilter = new LongFilter();
            userIdFilter.setEquals(currentUser.getId());

            switch (department) {
                case "operation":
                    criteria.setCourseConsultantId(userIdFilter);
                    break;
                case "market":
                    criteria.setSalesFollowerId(userIdFilter);
                    break;
                default:
                    break;
            }
        }

        Page<Customer> page = customerQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/customers");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/customers/overview")
    @Timed
    public Overview getUntrackedCustomerInCurrentMonth() {

        return customerService.getCurrentUserOverview();
    }

    @GetMapping("/customers/fixupCreatedDateIssue")
    @Timed
    public ResponseEntity fixupCreatedDateIssue() {

        customerService.fixupCreatedDateIssue();

        return ResponseEntity.ok().build();
    }

    private void preProccessStatusRequest(CustomerStatusRequest customerStatusRequest) {

        Integer month = customerStatusRequest.getMonth();
        Integer year = customerStatusRequest.getYear();

        Integer nextMonth = month + 1;
        Integer nextYear = year;
        if (nextMonth == 13) {
            nextMonth = 1;

            nextYear = year + 1;
        }

        String monthString = "" + month;
        String nextMonthString = "" + nextMonth;
        if (month < 10) {
            monthString = "0" + monthString;
        }

        if (nextMonth < 10) {
            nextMonthString = "0" + nextMonthString;
        }

        String fullDateStart = "" + year+ "-" + monthString + "-01T00:00:01.00Z";
        String fullDateEnd = "" + nextYear + "-" + nextMonthString + "-01T00:00:01.00Z";

        customerStatusRequest.setStartDate(Instant.parse(fullDateStart));
        customerStatusRequest.setEndDate(Instant.parse(fullDateEnd));
    }

    @PostMapping("/customers/status/report")
    @Timed
    public ResponseEntity<CombinedReport> getCustomerStatusReport(@RequestBody CustomerStatusRequest customerStatusRequest) {
//2014-12-03T10:15:30.00Z
        switch (customerStatusRequest.getQueryType()) {
            case "monthly":

                if (StringUtils.isEmpty(customerStatusRequest.getYear()) || StringUtils.isEmpty(customerStatusRequest.getMonth())) {
                    return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "conditionneeded", "请输入搜索条件")).body(null);
                }

                preProccessStatusRequest(customerStatusRequest);

                break;
            case "dateRange":

                if (StringUtils.isEmpty(customerStatusRequest.getStartDate()) || StringUtils.isEmpty(customerStatusRequest.getEndDate())) {
                    return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "conditionneeded", "请输入搜索条件")).body(null);
                }
                break;
            default:
                break;
        }
        CombinedReport report = customerService.getStatusReport(customerStatusRequest);

        return new ResponseEntity<>(report, HttpStatus.OK);
    }

    @PostMapping("/customers/visited/status/report")
    @Timed
    public ResponseEntity<List<ChannelReportElement>> getVistedCustomerStatusReport(@RequestBody CustomerStatusRequest customerStatusRequest) {
//2014-12-03T10:15:30.00Z
        switch (customerStatusRequest.getQueryType()) {
            case "monthly":

                if (StringUtils.isEmpty(customerStatusRequest.getYear()) || StringUtils.isEmpty(customerStatusRequest.getMonth())) {
                    return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "conditionneeded", "请输入搜索条件")).body(null);
                }

                preProccessStatusRequest(customerStatusRequest);

                break;
            case "dateRange":

                if (StringUtils.isEmpty(customerStatusRequest.getStartDate()) || StringUtils.isEmpty(customerStatusRequest.getEndDate())) {
                    return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "conditionneeded", "请输入搜索条件")).body(null);
                }
                break;
            default:
                break;
        }
        List<ChannelReportElement> report = customerService.getVistedCustomerStatusReport(customerStatusRequest);

        return new ResponseEntity<>(report, HttpStatus.OK);
    }

    @PostMapping("/customers/status/report/location")
    @Timed
    public ResponseEntity<List<StatusReportElement>> getLocationCustomerStatusReport(@RequestBody CustomerStatusRequest customerStatusRequest) {
//2014-12-03T10:15:30.00Z
        switch (customerStatusRequest.getQueryType()) {
            case "monthly":

                if (StringUtils.isEmpty(customerStatusRequest.getYear()) || StringUtils.isEmpty(customerStatusRequest.getMonth())) {
                    return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "conditionneeded", "请输入搜索条件")).body(null);
                }

                preProccessStatusRequest(customerStatusRequest);

                break;
            case "dateRange":

                if (StringUtils.isEmpty(customerStatusRequest.getStartDate()) || StringUtils.isEmpty(customerStatusRequest.getEndDate())) {
                    return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "conditionneeded", "请输入搜索条件")).body(null);
                }
                break;
            default:
                break;
        }
        List<StatusReportElement> report = customerService.getLocationStatusReport(customerStatusRequest);

        return new ResponseEntity<>(report, HttpStatus.OK);
    }


    @GetMapping("/customers/search/{keyword}")
    @Timed
    @RegionBasedQuery
    public ResponseEntity<List<Customer>> searchAllCustomers(CustomerCriteria criteria, @ApiParam Pageable pageable, @PathVariable("keyword") String keyword) {
        log.debug("REST request to get Customers by criteria: {} and keyword {}", criteria, keyword);

        if (StringUtils.isEmpty(keyword)) {
            return new ResponseEntity<>(new ArrayList<>(), null, HttpStatus.OK);
        }

        String department = criteria.getDepartment();

        if (StringUtils.isEmpty(department)) {
            return ResponseEntity.badRequest().build();
        }

        User currentUser = userService.getUserWithAuthorities();
        //Only Admin and Headmaster can have all the new orders
        if (!SecurityUtils.isCurrentUserHeadmasterOrAdminOrSalesManager()) {

            LongFilter userIdFilter = new LongFilter();
            userIdFilter.setEquals(currentUser.getId());

            switch (department) {
                case "operation":
                    criteria.setCourseConsultantId(userIdFilter);
                    break;
                case "market":
                    criteria.setSalesFollowerId(userIdFilter);
                    break;
//                case "recipient":
//                    criteria.set
                default:
                    break;
            }
        }

        if (NumberUtils.isCreatable(keyword)) {

            StringFilter phoneNumberFilter = new StringFilter();
            phoneNumberFilter.setContains(keyword);

            criteria.setContactPhoneNumber(phoneNumberFilter);

        } else {

            StringFilter customerNameFilter = new StringFilter();
            customerNameFilter.setContains(keyword);

            criteria.setName(customerNameFilter);
        }

        Page<Customer> page = customerQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/customers");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/customers/report/backup")
    @Timed
    public ResponseEntity backupAndReassign() {

        customerService.backupReport();

        return ResponseEntity.ok().build();
    }

    /**
     * GET  /customers : get all the customers.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of customers in body
     */
    @GetMapping("/customers/withlog")
    @Timed
    @RegionBasedQuery
    public ResponseEntity<List<CustomerFollowLog>> getAllCustomersWithFollowLog(CustomerCriteria criteria, @ApiParam Pageable pageable) {
        log.debug("REST request to get Customers with log by criteria: {}", criteria);

        User currentUser = userService.getUserWithAuthorities();
        //Only Admin and Headmaster can have all the new orders
        if (!SecurityUtils.isCurrentUserHeadmasterOrAdminOrSalesManager()) {

            LongFilter userIdFilter = new LongFilter();
            userIdFilter.setEquals(currentUser.getId());

            String department = criteria.getDepartment();
            if (!StringUtils.isEmpty(department)) {

                if (department.equals("market")) {

                    log.debug("REST request to get Customers for sales ", currentUser.getFirstName());

                    criteria.setSalesFollowerId(userIdFilter);

                } else if (department.equals("operation")) {

                    log.debug("REST request to get Customers for course consultant ", currentUser.getFirstName());

                    criteria.setCourseConsultantId(userIdFilter);
                }
            }
        }

        List<CustomerFollowLog> customerFollowLogs = new ArrayList<>();

        Page<Customer> customers = customerQueryService.findByCriteria(criteria, pageable);
        for (Customer customer : customers) {

            CustomerFollowLog customerFollowLog = new CustomerFollowLog();

            BeanUtils.copyProperties(customer, customerFollowLog);

            CustomerCommunicationLogCriteria logCriteria = new CustomerCommunicationLogCriteria();

            LongFilter customerIdFilter = new LongFilter();
            customerIdFilter.setEquals(customer.getId());

            logCriteria.setCustomerId(customerIdFilter);
            List<CustomerCommunicationLog> logs = logQueryService.findByCriteria(logCriteria);

            if (CollectionUtils.isEmpty(logs)) {
                customerFollowLog.setFollowCount(0);
            } else {

                CustomerCommunicationLog lastLog = logs.get(logs.size() - 1);
                customerFollowLog.setLastFollowComments(lastLog.getComments());
                customerFollowLog.setFollowCount(logs.size());
                customerFollowLog.setLastFollowTime(lastLog.getCreatedDate());
                customerFollowLog.setLogType(lastLog.getLogType());
            }
            customerFollowLogs.add(customerFollowLog);
        }

        Page<CustomerFollowLog> logPage = new PageImpl<>(customerFollowLogs, pageable, customers.getTotalElements());

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(logPage, "/api/customers");
        return new ResponseEntity<>(customerFollowLogs, headers, HttpStatus.OK);
    }

    @PutMapping("/customers/batchupdate")
    @Timed
    public ResponseEntity<List<Customer>> batchUpdateCustomers(@RequestBody List<Customer> customers) {
        log.debug("REST request to update customers : {}", customers);
        if (CollectionUtils.isEmpty(customers)) {
            return ResponseEntity.badRequest().build();
        }
        List<Customer> result = customerService.batchSave(customers);

        return ResponseEntity.ok().body(result);
    }

    @PutMapping("/customers/batch-assign-course-consultant")
    @Timed
    public ResponseEntity<List<Customer>> batchAssignCustomers(@RequestBody BatchAssignRequest request) {
//        log.debug("REST request to update customers : {}", customers);
        if (CollectionUtils.isEmpty(request.getCustomers()) || request.getUserId() == null) {
            return ResponseEntity.badRequest().build();
        }

        List<Customer> result = customerService.batchAssignCustomer(request.getCustomers(), request.getUserId());

        return ResponseEntity.ok().body(result);
    }

    /**
     * GET  /customers/:id : get the "id" customer.
     *
     * @param id the id of the customer to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the customer, or with status 404 (Not Found)
     */
    @GetMapping("/customers/{id}")
    @Timed
    public ResponseEntity<Customer> getCustomer(@PathVariable Long id) {
        log.debug("REST request to get Customer : {}", id);
        Customer customer = customerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(customer));
    }

    @GetMapping("/customers/premerge/{oid}/{tid}")
    @Timed
    public ResponseEntity<Customer> preloadMergedCustomer(@PathVariable Long oid, @PathVariable Long tid) {

        Customer customer = customerService.preloadMergedCustomer(oid, tid);

        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(customer));
    }

    /**
     * DELETE  /customers/:id : delete the "id" customer.
     *
     * @param id the id of the customer to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/customers/{id}")
    @Timed
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        log.debug("REST request to delete Customer : {}", id);
        customerService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * @param id new order id
     * @return
     */
    @GetMapping("/customers/connect/{id}")
    @Timed
    public ResponseEntity<Customer> connectCustomer(@PathVariable Long id) {

        log.debug("REST request to connect customer with new order id: {}", id);
        if (id == null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "badrequest", "No new order id provided")).body(null);
        }

        Customer customer = customerService.importCustomerFromNewOrder(id);

        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(customer));
    }
}
