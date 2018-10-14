package com.pure.service.web.rest;

import com.pure.service.SimpleServiceApp;

import com.pure.service.domain.CustomerCardUpgradeLog;
import com.pure.service.repository.CustomerCardUpgradeLogRepository;
import com.pure.service.service.CustomerCardUpgradeLogService;
import com.pure.service.web.rest.errors.ExceptionTranslator;
import com.pure.service.service.dto.CustomerCardUpgradeLogCriteria;
import com.pure.service.service.CustomerCardUpgradeLogQueryService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CustomerCardUpgradeLogResource REST controller.
 *
 * @see CustomerCardUpgradeLogResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SimpleServiceApp.class)
public class CustomerCardUpgradeLogResourceIntTest {

    private static final String DEFAULT_CUSTOMER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMER_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_CUSTOMER_ID = 1L;
    private static final Long UPDATED_CUSTOMER_ID = 2L;

    private static final String DEFAULT_SERIAL_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_SERIAL_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private CustomerCardUpgradeLogRepository customerCardUpgradeLogRepository;

    @Autowired
    private CustomerCardUpgradeLogService customerCardUpgradeLogService;

    @Autowired
    private CustomerCardUpgradeLogQueryService customerCardUpgradeLogQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCustomerCardUpgradeLogMockMvc;

    private CustomerCardUpgradeLog customerCardUpgradeLog;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CustomerCardUpgradeLogResource customerCardUpgradeLogResource = new CustomerCardUpgradeLogResource(customerCardUpgradeLogService, customerCardUpgradeLogQueryService);
        this.restCustomerCardUpgradeLogMockMvc = MockMvcBuilders.standaloneSetup(customerCardUpgradeLogResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustomerCardUpgradeLog createEntity(EntityManager em) {
        CustomerCardUpgradeLog customerCardUpgradeLog = new CustomerCardUpgradeLog()
            .customerName(DEFAULT_CUSTOMER_NAME)
            .customerId(DEFAULT_CUSTOMER_ID)
            .serialNumber(DEFAULT_SERIAL_NUMBER)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return customerCardUpgradeLog;
    }

    @Before
    public void initTest() {
        customerCardUpgradeLog = createEntity(em);
    }

    @Test
    @Transactional
    public void createCustomerCardUpgradeLog() throws Exception {
        int databaseSizeBeforeCreate = customerCardUpgradeLogRepository.findAll().size();

        // Create the CustomerCardUpgradeLog
        restCustomerCardUpgradeLogMockMvc.perform(post("/api/customer-card-upgrade-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerCardUpgradeLog)))
            .andExpect(status().isCreated());

        // Validate the CustomerCardUpgradeLog in the database
        List<CustomerCardUpgradeLog> customerCardUpgradeLogList = customerCardUpgradeLogRepository.findAll();
        assertThat(customerCardUpgradeLogList).hasSize(databaseSizeBeforeCreate + 1);
        CustomerCardUpgradeLog testCustomerCardUpgradeLog = customerCardUpgradeLogList.get(customerCardUpgradeLogList.size() - 1);
        assertThat(testCustomerCardUpgradeLog.getCustomerName()).isEqualTo(DEFAULT_CUSTOMER_NAME);
        assertThat(testCustomerCardUpgradeLog.getCustomerId()).isEqualTo(DEFAULT_CUSTOMER_ID);
        assertThat(testCustomerCardUpgradeLog.getSerialNumber()).isEqualTo(DEFAULT_SERIAL_NUMBER);
        assertThat(testCustomerCardUpgradeLog.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testCustomerCardUpgradeLog.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testCustomerCardUpgradeLog.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testCustomerCardUpgradeLog.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createCustomerCardUpgradeLogWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = customerCardUpgradeLogRepository.findAll().size();

        // Create the CustomerCardUpgradeLog with an existing ID
        customerCardUpgradeLog.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustomerCardUpgradeLogMockMvc.perform(post("/api/customer-card-upgrade-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerCardUpgradeLog)))
            .andExpect(status().isBadRequest());

        // Validate the CustomerCardUpgradeLog in the database
        List<CustomerCardUpgradeLog> customerCardUpgradeLogList = customerCardUpgradeLogRepository.findAll();
        assertThat(customerCardUpgradeLogList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCustomerCardUpgradeLogs() throws Exception {
        // Initialize the database
        customerCardUpgradeLogRepository.saveAndFlush(customerCardUpgradeLog);

        // Get all the customerCardUpgradeLogList
        restCustomerCardUpgradeLogMockMvc.perform(get("/api/customer-card-upgrade-logs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customerCardUpgradeLog.getId().intValue())))
            .andExpect(jsonPath("$.[*].customerName").value(hasItem(DEFAULT_CUSTOMER_NAME)))
            .andExpect(jsonPath("$.[*].customerId").value(hasItem(DEFAULT_CUSTOMER_ID.intValue())))
            .andExpect(jsonPath("$.[*].serialNumber").value(hasItem(DEFAULT_SERIAL_NUMBER)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    public void getCustomerCardUpgradeLog() throws Exception {
        // Initialize the database
        customerCardUpgradeLogRepository.saveAndFlush(customerCardUpgradeLog);

        // Get the customerCardUpgradeLog
        restCustomerCardUpgradeLogMockMvc.perform(get("/api/customer-card-upgrade-logs/{id}", customerCardUpgradeLog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(customerCardUpgradeLog.getId().intValue()))
            .andExpect(jsonPath("$.customerName").value(DEFAULT_CUSTOMER_NAME))
            .andExpect(jsonPath("$.customerId").value(DEFAULT_CUSTOMER_ID.intValue()))
            .andExpect(jsonPath("$.serialNumber").value(DEFAULT_SERIAL_NUMBER))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    public void getAllCustomerCardUpgradeLogsByCustomerNameIsEqualToSomething() throws Exception {
        // Initialize the database
        customerCardUpgradeLogRepository.saveAndFlush(customerCardUpgradeLog);

        // Get all the customerCardUpgradeLogList where customerName equals to DEFAULT_CUSTOMER_NAME
        defaultCustomerCardUpgradeLogShouldBeFound("customerName.equals=" + DEFAULT_CUSTOMER_NAME);

        // Get all the customerCardUpgradeLogList where customerName equals to UPDATED_CUSTOMER_NAME
        defaultCustomerCardUpgradeLogShouldNotBeFound("customerName.equals=" + UPDATED_CUSTOMER_NAME);
    }

    @Test
    @Transactional
    public void getAllCustomerCardUpgradeLogsByCustomerNameIsInShouldWork() throws Exception {
        // Initialize the database
        customerCardUpgradeLogRepository.saveAndFlush(customerCardUpgradeLog);

        // Get all the customerCardUpgradeLogList where customerName in DEFAULT_CUSTOMER_NAME or UPDATED_CUSTOMER_NAME
        defaultCustomerCardUpgradeLogShouldBeFound("customerName.in=" + DEFAULT_CUSTOMER_NAME + "," + UPDATED_CUSTOMER_NAME);

        // Get all the customerCardUpgradeLogList where customerName equals to UPDATED_CUSTOMER_NAME
        defaultCustomerCardUpgradeLogShouldNotBeFound("customerName.in=" + UPDATED_CUSTOMER_NAME);
    }

    @Test
    @Transactional
    public void getAllCustomerCardUpgradeLogsByCustomerNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerCardUpgradeLogRepository.saveAndFlush(customerCardUpgradeLog);

        // Get all the customerCardUpgradeLogList where customerName is not null
        defaultCustomerCardUpgradeLogShouldBeFound("customerName.specified=true");

        // Get all the customerCardUpgradeLogList where customerName is null
        defaultCustomerCardUpgradeLogShouldNotBeFound("customerName.specified=false");
    }

    @Test
    @Transactional
    public void getAllCustomerCardUpgradeLogsByCustomerIdIsEqualToSomething() throws Exception {
        // Initialize the database
        customerCardUpgradeLogRepository.saveAndFlush(customerCardUpgradeLog);

        // Get all the customerCardUpgradeLogList where customerId equals to DEFAULT_CUSTOMER_ID
        defaultCustomerCardUpgradeLogShouldBeFound("customerId.equals=" + DEFAULT_CUSTOMER_ID);

        // Get all the customerCardUpgradeLogList where customerId equals to UPDATED_CUSTOMER_ID
        defaultCustomerCardUpgradeLogShouldNotBeFound("customerId.equals=" + UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    public void getAllCustomerCardUpgradeLogsByCustomerIdIsInShouldWork() throws Exception {
        // Initialize the database
        customerCardUpgradeLogRepository.saveAndFlush(customerCardUpgradeLog);

        // Get all the customerCardUpgradeLogList where customerId in DEFAULT_CUSTOMER_ID or UPDATED_CUSTOMER_ID
        defaultCustomerCardUpgradeLogShouldBeFound("customerId.in=" + DEFAULT_CUSTOMER_ID + "," + UPDATED_CUSTOMER_ID);

        // Get all the customerCardUpgradeLogList where customerId equals to UPDATED_CUSTOMER_ID
        defaultCustomerCardUpgradeLogShouldNotBeFound("customerId.in=" + UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    public void getAllCustomerCardUpgradeLogsByCustomerIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerCardUpgradeLogRepository.saveAndFlush(customerCardUpgradeLog);

        // Get all the customerCardUpgradeLogList where customerId is not null
        defaultCustomerCardUpgradeLogShouldBeFound("customerId.specified=true");

        // Get all the customerCardUpgradeLogList where customerId is null
        defaultCustomerCardUpgradeLogShouldNotBeFound("customerId.specified=false");
    }

    @Test
    @Transactional
    public void getAllCustomerCardUpgradeLogsByCustomerIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        customerCardUpgradeLogRepository.saveAndFlush(customerCardUpgradeLog);

        // Get all the customerCardUpgradeLogList where customerId greater than or equals to DEFAULT_CUSTOMER_ID
        defaultCustomerCardUpgradeLogShouldBeFound("customerId.greaterOrEqualThan=" + DEFAULT_CUSTOMER_ID);

        // Get all the customerCardUpgradeLogList where customerId greater than or equals to UPDATED_CUSTOMER_ID
        defaultCustomerCardUpgradeLogShouldNotBeFound("customerId.greaterOrEqualThan=" + UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    public void getAllCustomerCardUpgradeLogsByCustomerIdIsLessThanSomething() throws Exception {
        // Initialize the database
        customerCardUpgradeLogRepository.saveAndFlush(customerCardUpgradeLog);

        // Get all the customerCardUpgradeLogList where customerId less than or equals to DEFAULT_CUSTOMER_ID
        defaultCustomerCardUpgradeLogShouldNotBeFound("customerId.lessThan=" + DEFAULT_CUSTOMER_ID);

        // Get all the customerCardUpgradeLogList where customerId less than or equals to UPDATED_CUSTOMER_ID
        defaultCustomerCardUpgradeLogShouldBeFound("customerId.lessThan=" + UPDATED_CUSTOMER_ID);
    }


    @Test
    @Transactional
    public void getAllCustomerCardUpgradeLogsBySerialNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        customerCardUpgradeLogRepository.saveAndFlush(customerCardUpgradeLog);

        // Get all the customerCardUpgradeLogList where serialNumber equals to DEFAULT_SERIAL_NUMBER
        defaultCustomerCardUpgradeLogShouldBeFound("serialNumber.equals=" + DEFAULT_SERIAL_NUMBER);

        // Get all the customerCardUpgradeLogList where serialNumber equals to UPDATED_SERIAL_NUMBER
        defaultCustomerCardUpgradeLogShouldNotBeFound("serialNumber.equals=" + UPDATED_SERIAL_NUMBER);
    }

    @Test
    @Transactional
    public void getAllCustomerCardUpgradeLogsBySerialNumberIsInShouldWork() throws Exception {
        // Initialize the database
        customerCardUpgradeLogRepository.saveAndFlush(customerCardUpgradeLog);

        // Get all the customerCardUpgradeLogList where serialNumber in DEFAULT_SERIAL_NUMBER or UPDATED_SERIAL_NUMBER
        defaultCustomerCardUpgradeLogShouldBeFound("serialNumber.in=" + DEFAULT_SERIAL_NUMBER + "," + UPDATED_SERIAL_NUMBER);

        // Get all the customerCardUpgradeLogList where serialNumber equals to UPDATED_SERIAL_NUMBER
        defaultCustomerCardUpgradeLogShouldNotBeFound("serialNumber.in=" + UPDATED_SERIAL_NUMBER);
    }

    @Test
    @Transactional
    public void getAllCustomerCardUpgradeLogsBySerialNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerCardUpgradeLogRepository.saveAndFlush(customerCardUpgradeLog);

        // Get all the customerCardUpgradeLogList where serialNumber is not null
        defaultCustomerCardUpgradeLogShouldBeFound("serialNumber.specified=true");

        // Get all the customerCardUpgradeLogList where serialNumber is null
        defaultCustomerCardUpgradeLogShouldNotBeFound("serialNumber.specified=false");
    }

    @Test
    @Transactional
    public void getAllCustomerCardUpgradeLogsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        customerCardUpgradeLogRepository.saveAndFlush(customerCardUpgradeLog);

        // Get all the customerCardUpgradeLogList where createdBy equals to DEFAULT_CREATED_BY
        defaultCustomerCardUpgradeLogShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the customerCardUpgradeLogList where createdBy equals to UPDATED_CREATED_BY
        defaultCustomerCardUpgradeLogShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllCustomerCardUpgradeLogsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        customerCardUpgradeLogRepository.saveAndFlush(customerCardUpgradeLog);

        // Get all the customerCardUpgradeLogList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultCustomerCardUpgradeLogShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the customerCardUpgradeLogList where createdBy equals to UPDATED_CREATED_BY
        defaultCustomerCardUpgradeLogShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllCustomerCardUpgradeLogsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerCardUpgradeLogRepository.saveAndFlush(customerCardUpgradeLog);

        // Get all the customerCardUpgradeLogList where createdBy is not null
        defaultCustomerCardUpgradeLogShouldBeFound("createdBy.specified=true");

        // Get all the customerCardUpgradeLogList where createdBy is null
        defaultCustomerCardUpgradeLogShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllCustomerCardUpgradeLogsByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        customerCardUpgradeLogRepository.saveAndFlush(customerCardUpgradeLog);

        // Get all the customerCardUpgradeLogList where createdDate equals to DEFAULT_CREATED_DATE
        defaultCustomerCardUpgradeLogShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the customerCardUpgradeLogList where createdDate equals to UPDATED_CREATED_DATE
        defaultCustomerCardUpgradeLogShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllCustomerCardUpgradeLogsByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        customerCardUpgradeLogRepository.saveAndFlush(customerCardUpgradeLog);

        // Get all the customerCardUpgradeLogList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultCustomerCardUpgradeLogShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the customerCardUpgradeLogList where createdDate equals to UPDATED_CREATED_DATE
        defaultCustomerCardUpgradeLogShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllCustomerCardUpgradeLogsByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerCardUpgradeLogRepository.saveAndFlush(customerCardUpgradeLog);

        // Get all the customerCardUpgradeLogList where createdDate is not null
        defaultCustomerCardUpgradeLogShouldBeFound("createdDate.specified=true");

        // Get all the customerCardUpgradeLogList where createdDate is null
        defaultCustomerCardUpgradeLogShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllCustomerCardUpgradeLogsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        customerCardUpgradeLogRepository.saveAndFlush(customerCardUpgradeLog);

        // Get all the customerCardUpgradeLogList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultCustomerCardUpgradeLogShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the customerCardUpgradeLogList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultCustomerCardUpgradeLogShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllCustomerCardUpgradeLogsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        customerCardUpgradeLogRepository.saveAndFlush(customerCardUpgradeLog);

        // Get all the customerCardUpgradeLogList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultCustomerCardUpgradeLogShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the customerCardUpgradeLogList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultCustomerCardUpgradeLogShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllCustomerCardUpgradeLogsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerCardUpgradeLogRepository.saveAndFlush(customerCardUpgradeLog);

        // Get all the customerCardUpgradeLogList where lastModifiedBy is not null
        defaultCustomerCardUpgradeLogShouldBeFound("lastModifiedBy.specified=true");

        // Get all the customerCardUpgradeLogList where lastModifiedBy is null
        defaultCustomerCardUpgradeLogShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllCustomerCardUpgradeLogsByLastModifiedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        customerCardUpgradeLogRepository.saveAndFlush(customerCardUpgradeLog);

        // Get all the customerCardUpgradeLogList where lastModifiedDate equals to DEFAULT_LAST_MODIFIED_DATE
        defaultCustomerCardUpgradeLogShouldBeFound("lastModifiedDate.equals=" + DEFAULT_LAST_MODIFIED_DATE);

        // Get all the customerCardUpgradeLogList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultCustomerCardUpgradeLogShouldNotBeFound("lastModifiedDate.equals=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllCustomerCardUpgradeLogsByLastModifiedDateIsInShouldWork() throws Exception {
        // Initialize the database
        customerCardUpgradeLogRepository.saveAndFlush(customerCardUpgradeLog);

        // Get all the customerCardUpgradeLogList where lastModifiedDate in DEFAULT_LAST_MODIFIED_DATE or UPDATED_LAST_MODIFIED_DATE
        defaultCustomerCardUpgradeLogShouldBeFound("lastModifiedDate.in=" + DEFAULT_LAST_MODIFIED_DATE + "," + UPDATED_LAST_MODIFIED_DATE);

        // Get all the customerCardUpgradeLogList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultCustomerCardUpgradeLogShouldNotBeFound("lastModifiedDate.in=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllCustomerCardUpgradeLogsByLastModifiedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerCardUpgradeLogRepository.saveAndFlush(customerCardUpgradeLog);

        // Get all the customerCardUpgradeLogList where lastModifiedDate is not null
        defaultCustomerCardUpgradeLogShouldBeFound("lastModifiedDate.specified=true");

        // Get all the customerCardUpgradeLogList where lastModifiedDate is null
        defaultCustomerCardUpgradeLogShouldNotBeFound("lastModifiedDate.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultCustomerCardUpgradeLogShouldBeFound(String filter) throws Exception {
        restCustomerCardUpgradeLogMockMvc.perform(get("/api/customer-card-upgrade-logs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customerCardUpgradeLog.getId().intValue())))
            .andExpect(jsonPath("$.[*].customerName").value(hasItem(DEFAULT_CUSTOMER_NAME)))
            .andExpect(jsonPath("$.[*].customerId").value(hasItem(DEFAULT_CUSTOMER_ID.intValue())))
            .andExpect(jsonPath("$.[*].serialNumber").value(hasItem(DEFAULT_SERIAL_NUMBER)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultCustomerCardUpgradeLogShouldNotBeFound(String filter) throws Exception {
        restCustomerCardUpgradeLogMockMvc.perform(get("/api/customer-card-upgrade-logs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingCustomerCardUpgradeLog() throws Exception {
        // Get the customerCardUpgradeLog
        restCustomerCardUpgradeLogMockMvc.perform(get("/api/customer-card-upgrade-logs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCustomerCardUpgradeLog() throws Exception {
        // Initialize the database
        customerCardUpgradeLogService.save(customerCardUpgradeLog);

        int databaseSizeBeforeUpdate = customerCardUpgradeLogRepository.findAll().size();

        // Update the customerCardUpgradeLog
        CustomerCardUpgradeLog updatedCustomerCardUpgradeLog = customerCardUpgradeLogRepository.findOne(customerCardUpgradeLog.getId());
        updatedCustomerCardUpgradeLog
            .customerName(UPDATED_CUSTOMER_NAME)
            .customerId(UPDATED_CUSTOMER_ID)
            .serialNumber(UPDATED_SERIAL_NUMBER)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restCustomerCardUpgradeLogMockMvc.perform(put("/api/customer-card-upgrade-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCustomerCardUpgradeLog)))
            .andExpect(status().isOk());

        // Validate the CustomerCardUpgradeLog in the database
        List<CustomerCardUpgradeLog> customerCardUpgradeLogList = customerCardUpgradeLogRepository.findAll();
        assertThat(customerCardUpgradeLogList).hasSize(databaseSizeBeforeUpdate);
        CustomerCardUpgradeLog testCustomerCardUpgradeLog = customerCardUpgradeLogList.get(customerCardUpgradeLogList.size() - 1);
        assertThat(testCustomerCardUpgradeLog.getCustomerName()).isEqualTo(UPDATED_CUSTOMER_NAME);
        assertThat(testCustomerCardUpgradeLog.getCustomerId()).isEqualTo(UPDATED_CUSTOMER_ID);
        assertThat(testCustomerCardUpgradeLog.getSerialNumber()).isEqualTo(UPDATED_SERIAL_NUMBER);
        assertThat(testCustomerCardUpgradeLog.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testCustomerCardUpgradeLog.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testCustomerCardUpgradeLog.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testCustomerCardUpgradeLog.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingCustomerCardUpgradeLog() throws Exception {
        int databaseSizeBeforeUpdate = customerCardUpgradeLogRepository.findAll().size();

        // Create the CustomerCardUpgradeLog

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCustomerCardUpgradeLogMockMvc.perform(put("/api/customer-card-upgrade-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerCardUpgradeLog)))
            .andExpect(status().isCreated());

        // Validate the CustomerCardUpgradeLog in the database
        List<CustomerCardUpgradeLog> customerCardUpgradeLogList = customerCardUpgradeLogRepository.findAll();
        assertThat(customerCardUpgradeLogList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCustomerCardUpgradeLog() throws Exception {
        // Initialize the database
        customerCardUpgradeLogService.save(customerCardUpgradeLog);

        int databaseSizeBeforeDelete = customerCardUpgradeLogRepository.findAll().size();

        // Get the customerCardUpgradeLog
        restCustomerCardUpgradeLogMockMvc.perform(delete("/api/customer-card-upgrade-logs/{id}", customerCardUpgradeLog.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CustomerCardUpgradeLog> customerCardUpgradeLogList = customerCardUpgradeLogRepository.findAll();
        assertThat(customerCardUpgradeLogList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomerCardUpgradeLog.class);
        CustomerCardUpgradeLog customerCardUpgradeLog1 = new CustomerCardUpgradeLog();
        customerCardUpgradeLog1.setId(1L);
        CustomerCardUpgradeLog customerCardUpgradeLog2 = new CustomerCardUpgradeLog();
        customerCardUpgradeLog2.setId(customerCardUpgradeLog1.getId());
        assertThat(customerCardUpgradeLog1).isEqualTo(customerCardUpgradeLog2);
        customerCardUpgradeLog2.setId(2L);
        assertThat(customerCardUpgradeLog1).isNotEqualTo(customerCardUpgradeLog2);
        customerCardUpgradeLog1.setId(null);
        assertThat(customerCardUpgradeLog1).isNotEqualTo(customerCardUpgradeLog2);
    }
}
