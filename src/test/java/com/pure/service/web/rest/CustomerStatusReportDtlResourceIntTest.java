package com.pure.service.web.rest;

import com.pure.service.SimpleServiceApp;
import com.pure.service.domain.CustomerStatusReportDtl;
import com.pure.service.repository.CustomerStatusReportDtlRepository;
import com.pure.service.service.CustomerStatusReportDtlQueryService;
import com.pure.service.service.CustomerStatusReportDtlService;
import com.pure.service.web.rest.errors.ExceptionTranslator;
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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test class for the CustomerStatusReportDtlResource REST controller.
 *
 * @see CustomerStatusReportDtlResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SimpleServiceApp.class)
public class CustomerStatusReportDtlResourceIntTest {

    private static final Long DEFAULT_USER_ID = 1L;
    private static final Long UPDATED_USER_ID = 2L;

    private static final String DEFAULT_USER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_USER_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_AGE_TOO_SMALL_COUNT = 1;
    private static final Integer UPDATED_AGE_TOO_SMALL_COUNT = 2;

    private static final Integer DEFAULT_ERROR_INFORMATION = 1;
    private static final Integer UPDATED_ERROR_INFORMATION = 2;

    private static final Integer DEFAULT_NO_WILLING_COUNT = 1;
    private static final Integer UPDATED_NO_WILLING_COUNT = 2;

    private static final Integer DEFAULT_CONSIDERING_COUNT = 1;
    private static final Integer UPDATED_CONSIDERING_COUNT = 2;

    private static final Integer DEFAULT_SCHEDULED_COUNT = 1;
    private static final Integer UPDATED_SCHEDULED_COUNT = 2;

    private static final Integer DEFAULT_DEALED_COUNT = 1;
    private static final Integer UPDATED_DEALED_COUNT = 2;

    private static final Integer DEFAULT_NEW_CREATED_COUNT = 1;
    private static final Integer UPDATED_NEW_CREATED_COUNT = 2;

    private static final Integer DEFAULT_TOTAL_COUNT = 1;
    private static final Integer UPDATED_TOTAL_COUNT = 2;

    private static final Double DEFAULT_FINISH_RATE = 1D;
    private static final Double UPDATED_FINISH_RATE = 2D;

    @Autowired
    private CustomerStatusReportDtlRepository customerStatusReportDtlRepository;

    @Autowired
    private CustomerStatusReportDtlService customerStatusReportDtlService;

    @Autowired
    private CustomerStatusReportDtlQueryService customerStatusReportDtlQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCustomerStatusReportDtlMockMvc;

    private CustomerStatusReportDtl customerStatusReportDtl;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CustomerStatusReportDtlResource customerStatusReportDtlResource = new CustomerStatusReportDtlResource(customerStatusReportDtlService, customerStatusReportDtlQueryService);
        this.restCustomerStatusReportDtlMockMvc = MockMvcBuilders.standaloneSetup(customerStatusReportDtlResource)
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
    public static CustomerStatusReportDtl createEntity(EntityManager em) {
        CustomerStatusReportDtl customerStatusReportDtl = new CustomerStatusReportDtl()
            .userId(DEFAULT_USER_ID)
            .userName(DEFAULT_USER_NAME)
            .ageTooSmallCount(DEFAULT_AGE_TOO_SMALL_COUNT)
            .errorInformation(DEFAULT_ERROR_INFORMATION)
            .noWillingCount(DEFAULT_NO_WILLING_COUNT)
            .consideringCount(DEFAULT_CONSIDERING_COUNT)
            .scheduledCount(DEFAULT_SCHEDULED_COUNT)
            .dealedCount(DEFAULT_DEALED_COUNT)
            .newCreatedCount(DEFAULT_NEW_CREATED_COUNT)
            .totalCount(DEFAULT_TOTAL_COUNT)
            .finishRate(DEFAULT_FINISH_RATE);
        return customerStatusReportDtl;
    }

    @Before
    public void initTest() {
        customerStatusReportDtl = createEntity(em);
    }

    @Test
    @Transactional
    public void createCustomerStatusReportDtl() throws Exception {
        int databaseSizeBeforeCreate = customerStatusReportDtlRepository.findAll().size();

        // Create the CustomerStatusReportDtl
        restCustomerStatusReportDtlMockMvc.perform(post("/api/customer-status-report-dtls")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerStatusReportDtl)))
            .andExpect(status().isCreated());

        // Validate the CustomerStatusReportDtl in the database
        List<CustomerStatusReportDtl> customerStatusReportDtlList = customerStatusReportDtlRepository.findAll();
        assertThat(customerStatusReportDtlList).hasSize(databaseSizeBeforeCreate + 1);
        CustomerStatusReportDtl testCustomerStatusReportDtl = customerStatusReportDtlList.get(customerStatusReportDtlList.size() - 1);
        assertThat(testCustomerStatusReportDtl.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testCustomerStatusReportDtl.getUserName()).isEqualTo(DEFAULT_USER_NAME);
        assertThat(testCustomerStatusReportDtl.getAgeTooSmallCount()).isEqualTo(DEFAULT_AGE_TOO_SMALL_COUNT);
        assertThat(testCustomerStatusReportDtl.getErrorInformation()).isEqualTo(DEFAULT_ERROR_INFORMATION);
        assertThat(testCustomerStatusReportDtl.getNoWillingCount()).isEqualTo(DEFAULT_NO_WILLING_COUNT);
        assertThat(testCustomerStatusReportDtl.getConsideringCount()).isEqualTo(DEFAULT_CONSIDERING_COUNT);
        assertThat(testCustomerStatusReportDtl.getScheduledCount()).isEqualTo(DEFAULT_SCHEDULED_COUNT);
        assertThat(testCustomerStatusReportDtl.getDealedCount()).isEqualTo(DEFAULT_DEALED_COUNT);
        assertThat(testCustomerStatusReportDtl.getNewCreatedCount()).isEqualTo(DEFAULT_NEW_CREATED_COUNT);
        assertThat(testCustomerStatusReportDtl.getTotalCount()).isEqualTo(DEFAULT_TOTAL_COUNT);
        assertThat(testCustomerStatusReportDtl.getFinishRate()).isEqualTo(DEFAULT_FINISH_RATE);
    }

    @Test
    @Transactional
    public void createCustomerStatusReportDtlWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = customerStatusReportDtlRepository.findAll().size();

        // Create the CustomerStatusReportDtl with an existing ID
        customerStatusReportDtl.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustomerStatusReportDtlMockMvc.perform(post("/api/customer-status-report-dtls")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerStatusReportDtl)))
            .andExpect(status().isBadRequest());

        // Validate the CustomerStatusReportDtl in the database
        List<CustomerStatusReportDtl> customerStatusReportDtlList = customerStatusReportDtlRepository.findAll();
        assertThat(customerStatusReportDtlList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCustomerStatusReportDtls() throws Exception {
        // Initialize the database
        customerStatusReportDtlRepository.saveAndFlush(customerStatusReportDtl);

        // Get all the customerStatusReportDtlList
        restCustomerStatusReportDtlMockMvc.perform(get("/api/customer-status-report-dtls?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customerStatusReportDtl.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].userName").value(hasItem(DEFAULT_USER_NAME)))
            .andExpect(jsonPath("$.[*].ageTooSmallCount").value(hasItem(DEFAULT_AGE_TOO_SMALL_COUNT)))
            .andExpect(jsonPath("$.[*].errorInformation").value(hasItem(DEFAULT_ERROR_INFORMATION)))
            .andExpect(jsonPath("$.[*].noWillingCount").value(hasItem(DEFAULT_NO_WILLING_COUNT)))
            .andExpect(jsonPath("$.[*].consideringCount").value(hasItem(DEFAULT_CONSIDERING_COUNT)))
            .andExpect(jsonPath("$.[*].scheduledCount").value(hasItem(DEFAULT_SCHEDULED_COUNT)))
            .andExpect(jsonPath("$.[*].dealedCount").value(hasItem(DEFAULT_DEALED_COUNT)))
            .andExpect(jsonPath("$.[*].newCreatedCount").value(hasItem(DEFAULT_NEW_CREATED_COUNT)))
            .andExpect(jsonPath("$.[*].totalCount").value(hasItem(DEFAULT_TOTAL_COUNT)))
            .andExpect(jsonPath("$.[*].finishRate").value(hasItem(DEFAULT_FINISH_RATE.doubleValue())));
    }

    @Test
    @Transactional
    public void getCustomerStatusReportDtl() throws Exception {
        // Initialize the database
        customerStatusReportDtlRepository.saveAndFlush(customerStatusReportDtl);

        // Get the customerStatusReportDtl
        restCustomerStatusReportDtlMockMvc.perform(get("/api/customer-status-report-dtls/{id}", customerStatusReportDtl.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(customerStatusReportDtl.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.intValue()))
            .andExpect(jsonPath("$.userName").value(DEFAULT_USER_NAME))
            .andExpect(jsonPath("$.ageTooSmallCount").value(DEFAULT_AGE_TOO_SMALL_COUNT))
            .andExpect(jsonPath("$.errorInformation").value(DEFAULT_ERROR_INFORMATION))
            .andExpect(jsonPath("$.noWillingCount").value(DEFAULT_NO_WILLING_COUNT))
            .andExpect(jsonPath("$.consideringCount").value(DEFAULT_CONSIDERING_COUNT))
            .andExpect(jsonPath("$.scheduledCount").value(DEFAULT_SCHEDULED_COUNT))
            .andExpect(jsonPath("$.dealedCount").value(DEFAULT_DEALED_COUNT))
            .andExpect(jsonPath("$.newCreatedCount").value(DEFAULT_NEW_CREATED_COUNT))
            .andExpect(jsonPath("$.totalCount").value(DEFAULT_TOTAL_COUNT))
            .andExpect(jsonPath("$.finishRate").value(DEFAULT_FINISH_RATE.doubleValue()));
    }

    @Test
    @Transactional
    public void getAllCustomerStatusReportDtlsByUserIdIsEqualToSomething() throws Exception {
        // Initialize the database
        customerStatusReportDtlRepository.saveAndFlush(customerStatusReportDtl);

        // Get all the customerStatusReportDtlList where userId equals to DEFAULT_USER_ID
        defaultCustomerStatusReportDtlShouldBeFound("userId.equals=" + DEFAULT_USER_ID);

        // Get all the customerStatusReportDtlList where userId equals to UPDATED_USER_ID
        defaultCustomerStatusReportDtlShouldNotBeFound("userId.equals=" + UPDATED_USER_ID);
    }

    @Test
    @Transactional
    public void getAllCustomerStatusReportDtlsByUserIdIsInShouldWork() throws Exception {
        // Initialize the database
        customerStatusReportDtlRepository.saveAndFlush(customerStatusReportDtl);

        // Get all the customerStatusReportDtlList where userId in DEFAULT_USER_ID or UPDATED_USER_ID
        defaultCustomerStatusReportDtlShouldBeFound("userId.in=" + DEFAULT_USER_ID + "," + UPDATED_USER_ID);

        // Get all the customerStatusReportDtlList where userId equals to UPDATED_USER_ID
        defaultCustomerStatusReportDtlShouldNotBeFound("userId.in=" + UPDATED_USER_ID);
    }

    @Test
    @Transactional
    public void getAllCustomerStatusReportDtlsByUserIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerStatusReportDtlRepository.saveAndFlush(customerStatusReportDtl);

        // Get all the customerStatusReportDtlList where userId is not null
        defaultCustomerStatusReportDtlShouldBeFound("userId.specified=true");

        // Get all the customerStatusReportDtlList where userId is null
        defaultCustomerStatusReportDtlShouldNotBeFound("userId.specified=false");
    }

    @Test
    @Transactional
    public void getAllCustomerStatusReportDtlsByUserIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        customerStatusReportDtlRepository.saveAndFlush(customerStatusReportDtl);

        // Get all the customerStatusReportDtlList where userId greater than or equals to DEFAULT_USER_ID
        defaultCustomerStatusReportDtlShouldBeFound("userId.greaterOrEqualThan=" + DEFAULT_USER_ID);

        // Get all the customerStatusReportDtlList where userId greater than or equals to UPDATED_USER_ID
        defaultCustomerStatusReportDtlShouldNotBeFound("userId.greaterOrEqualThan=" + UPDATED_USER_ID);
    }

    @Test
    @Transactional
    public void getAllCustomerStatusReportDtlsByUserIdIsLessThanSomething() throws Exception {
        // Initialize the database
        customerStatusReportDtlRepository.saveAndFlush(customerStatusReportDtl);

        // Get all the customerStatusReportDtlList where userId less than or equals to DEFAULT_USER_ID
        defaultCustomerStatusReportDtlShouldNotBeFound("userId.lessThan=" + DEFAULT_USER_ID);

        // Get all the customerStatusReportDtlList where userId less than or equals to UPDATED_USER_ID
        defaultCustomerStatusReportDtlShouldBeFound("userId.lessThan=" + UPDATED_USER_ID);
    }


    @Test
    @Transactional
    public void getAllCustomerStatusReportDtlsByUserNameIsEqualToSomething() throws Exception {
        // Initialize the database
        customerStatusReportDtlRepository.saveAndFlush(customerStatusReportDtl);

        // Get all the customerStatusReportDtlList where userName equals to DEFAULT_USER_NAME
        defaultCustomerStatusReportDtlShouldBeFound("userName.equals=" + DEFAULT_USER_NAME);

        // Get all the customerStatusReportDtlList where userName equals to UPDATED_USER_NAME
        defaultCustomerStatusReportDtlShouldNotBeFound("userName.equals=" + UPDATED_USER_NAME);
    }

    @Test
    @Transactional
    public void getAllCustomerStatusReportDtlsByUserNameIsInShouldWork() throws Exception {
        // Initialize the database
        customerStatusReportDtlRepository.saveAndFlush(customerStatusReportDtl);

        // Get all the customerStatusReportDtlList where userName in DEFAULT_USER_NAME or UPDATED_USER_NAME
        defaultCustomerStatusReportDtlShouldBeFound("userName.in=" + DEFAULT_USER_NAME + "," + UPDATED_USER_NAME);

        // Get all the customerStatusReportDtlList where userName equals to UPDATED_USER_NAME
        defaultCustomerStatusReportDtlShouldNotBeFound("userName.in=" + UPDATED_USER_NAME);
    }

    @Test
    @Transactional
    public void getAllCustomerStatusReportDtlsByUserNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerStatusReportDtlRepository.saveAndFlush(customerStatusReportDtl);

        // Get all the customerStatusReportDtlList where userName is not null
        defaultCustomerStatusReportDtlShouldBeFound("userName.specified=true");

        // Get all the customerStatusReportDtlList where userName is null
        defaultCustomerStatusReportDtlShouldNotBeFound("userName.specified=false");
    }

    @Test
    @Transactional
    public void getAllCustomerStatusReportDtlsByAgeTooSmallCountIsEqualToSomething() throws Exception {
        // Initialize the database
        customerStatusReportDtlRepository.saveAndFlush(customerStatusReportDtl);

        // Get all the customerStatusReportDtlList where ageTooSmallCount equals to DEFAULT_AGE_TOO_SMALL_COUNT
        defaultCustomerStatusReportDtlShouldBeFound("ageTooSmallCount.equals=" + DEFAULT_AGE_TOO_SMALL_COUNT);

        // Get all the customerStatusReportDtlList where ageTooSmallCount equals to UPDATED_AGE_TOO_SMALL_COUNT
        defaultCustomerStatusReportDtlShouldNotBeFound("ageTooSmallCount.equals=" + UPDATED_AGE_TOO_SMALL_COUNT);
    }

    @Test
    @Transactional
    public void getAllCustomerStatusReportDtlsByAgeTooSmallCountIsInShouldWork() throws Exception {
        // Initialize the database
        customerStatusReportDtlRepository.saveAndFlush(customerStatusReportDtl);

        // Get all the customerStatusReportDtlList where ageTooSmallCount in DEFAULT_AGE_TOO_SMALL_COUNT or UPDATED_AGE_TOO_SMALL_COUNT
        defaultCustomerStatusReportDtlShouldBeFound("ageTooSmallCount.in=" + DEFAULT_AGE_TOO_SMALL_COUNT + "," + UPDATED_AGE_TOO_SMALL_COUNT);

        // Get all the customerStatusReportDtlList where ageTooSmallCount equals to UPDATED_AGE_TOO_SMALL_COUNT
        defaultCustomerStatusReportDtlShouldNotBeFound("ageTooSmallCount.in=" + UPDATED_AGE_TOO_SMALL_COUNT);
    }

    @Test
    @Transactional
    public void getAllCustomerStatusReportDtlsByAgeTooSmallCountIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerStatusReportDtlRepository.saveAndFlush(customerStatusReportDtl);

        // Get all the customerStatusReportDtlList where ageTooSmallCount is not null
        defaultCustomerStatusReportDtlShouldBeFound("ageTooSmallCount.specified=true");

        // Get all the customerStatusReportDtlList where ageTooSmallCount is null
        defaultCustomerStatusReportDtlShouldNotBeFound("ageTooSmallCount.specified=false");
    }

    @Test
    @Transactional
    public void getAllCustomerStatusReportDtlsByAgeTooSmallCountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        customerStatusReportDtlRepository.saveAndFlush(customerStatusReportDtl);

        // Get all the customerStatusReportDtlList where ageTooSmallCount greater than or equals to DEFAULT_AGE_TOO_SMALL_COUNT
        defaultCustomerStatusReportDtlShouldBeFound("ageTooSmallCount.greaterOrEqualThan=" + DEFAULT_AGE_TOO_SMALL_COUNT);

        // Get all the customerStatusReportDtlList where ageTooSmallCount greater than or equals to UPDATED_AGE_TOO_SMALL_COUNT
        defaultCustomerStatusReportDtlShouldNotBeFound("ageTooSmallCount.greaterOrEqualThan=" + UPDATED_AGE_TOO_SMALL_COUNT);
    }

    @Test
    @Transactional
    public void getAllCustomerStatusReportDtlsByAgeTooSmallCountIsLessThanSomething() throws Exception {
        // Initialize the database
        customerStatusReportDtlRepository.saveAndFlush(customerStatusReportDtl);

        // Get all the customerStatusReportDtlList where ageTooSmallCount less than or equals to DEFAULT_AGE_TOO_SMALL_COUNT
        defaultCustomerStatusReportDtlShouldNotBeFound("ageTooSmallCount.lessThan=" + DEFAULT_AGE_TOO_SMALL_COUNT);

        // Get all the customerStatusReportDtlList where ageTooSmallCount less than or equals to UPDATED_AGE_TOO_SMALL_COUNT
        defaultCustomerStatusReportDtlShouldBeFound("ageTooSmallCount.lessThan=" + UPDATED_AGE_TOO_SMALL_COUNT);
    }


    @Test
    @Transactional
    public void getAllCustomerStatusReportDtlsByErrorInformationIsEqualToSomething() throws Exception {
        // Initialize the database
        customerStatusReportDtlRepository.saveAndFlush(customerStatusReportDtl);

        // Get all the customerStatusReportDtlList where errorInformation equals to DEFAULT_ERROR_INFORMATION
        defaultCustomerStatusReportDtlShouldBeFound("errorInformation.equals=" + DEFAULT_ERROR_INFORMATION);

        // Get all the customerStatusReportDtlList where errorInformation equals to UPDATED_ERROR_INFORMATION
        defaultCustomerStatusReportDtlShouldNotBeFound("errorInformation.equals=" + UPDATED_ERROR_INFORMATION);
    }

    @Test
    @Transactional
    public void getAllCustomerStatusReportDtlsByErrorInformationIsInShouldWork() throws Exception {
        // Initialize the database
        customerStatusReportDtlRepository.saveAndFlush(customerStatusReportDtl);

        // Get all the customerStatusReportDtlList where errorInformation in DEFAULT_ERROR_INFORMATION or UPDATED_ERROR_INFORMATION
        defaultCustomerStatusReportDtlShouldBeFound("errorInformation.in=" + DEFAULT_ERROR_INFORMATION + "," + UPDATED_ERROR_INFORMATION);

        // Get all the customerStatusReportDtlList where errorInformation equals to UPDATED_ERROR_INFORMATION
        defaultCustomerStatusReportDtlShouldNotBeFound("errorInformation.in=" + UPDATED_ERROR_INFORMATION);
    }

    @Test
    @Transactional
    public void getAllCustomerStatusReportDtlsByErrorInformationIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerStatusReportDtlRepository.saveAndFlush(customerStatusReportDtl);

        // Get all the customerStatusReportDtlList where errorInformation is not null
        defaultCustomerStatusReportDtlShouldBeFound("errorInformation.specified=true");

        // Get all the customerStatusReportDtlList where errorInformation is null
        defaultCustomerStatusReportDtlShouldNotBeFound("errorInformation.specified=false");
    }

    @Test
    @Transactional
    public void getAllCustomerStatusReportDtlsByErrorInformationIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        customerStatusReportDtlRepository.saveAndFlush(customerStatusReportDtl);

        // Get all the customerStatusReportDtlList where errorInformation greater than or equals to DEFAULT_ERROR_INFORMATION
        defaultCustomerStatusReportDtlShouldBeFound("errorInformation.greaterOrEqualThan=" + DEFAULT_ERROR_INFORMATION);

        // Get all the customerStatusReportDtlList where errorInformation greater than or equals to UPDATED_ERROR_INFORMATION
        defaultCustomerStatusReportDtlShouldNotBeFound("errorInformation.greaterOrEqualThan=" + UPDATED_ERROR_INFORMATION);
    }

    @Test
    @Transactional
    public void getAllCustomerStatusReportDtlsByErrorInformationIsLessThanSomething() throws Exception {
        // Initialize the database
        customerStatusReportDtlRepository.saveAndFlush(customerStatusReportDtl);

        // Get all the customerStatusReportDtlList where errorInformation less than or equals to DEFAULT_ERROR_INFORMATION
        defaultCustomerStatusReportDtlShouldNotBeFound("errorInformation.lessThan=" + DEFAULT_ERROR_INFORMATION);

        // Get all the customerStatusReportDtlList where errorInformation less than or equals to UPDATED_ERROR_INFORMATION
        defaultCustomerStatusReportDtlShouldBeFound("errorInformation.lessThan=" + UPDATED_ERROR_INFORMATION);
    }


    @Test
    @Transactional
    public void getAllCustomerStatusReportDtlsByNoWillingCountIsEqualToSomething() throws Exception {
        // Initialize the database
        customerStatusReportDtlRepository.saveAndFlush(customerStatusReportDtl);

        // Get all the customerStatusReportDtlList where noWillingCount equals to DEFAULT_NO_WILLING_COUNT
        defaultCustomerStatusReportDtlShouldBeFound("noWillingCount.equals=" + DEFAULT_NO_WILLING_COUNT);

        // Get all the customerStatusReportDtlList where noWillingCount equals to UPDATED_NO_WILLING_COUNT
        defaultCustomerStatusReportDtlShouldNotBeFound("noWillingCount.equals=" + UPDATED_NO_WILLING_COUNT);
    }

    @Test
    @Transactional
    public void getAllCustomerStatusReportDtlsByNoWillingCountIsInShouldWork() throws Exception {
        // Initialize the database
        customerStatusReportDtlRepository.saveAndFlush(customerStatusReportDtl);

        // Get all the customerStatusReportDtlList where noWillingCount in DEFAULT_NO_WILLING_COUNT or UPDATED_NO_WILLING_COUNT
        defaultCustomerStatusReportDtlShouldBeFound("noWillingCount.in=" + DEFAULT_NO_WILLING_COUNT + "," + UPDATED_NO_WILLING_COUNT);

        // Get all the customerStatusReportDtlList where noWillingCount equals to UPDATED_NO_WILLING_COUNT
        defaultCustomerStatusReportDtlShouldNotBeFound("noWillingCount.in=" + UPDATED_NO_WILLING_COUNT);
    }

    @Test
    @Transactional
    public void getAllCustomerStatusReportDtlsByNoWillingCountIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerStatusReportDtlRepository.saveAndFlush(customerStatusReportDtl);

        // Get all the customerStatusReportDtlList where noWillingCount is not null
        defaultCustomerStatusReportDtlShouldBeFound("noWillingCount.specified=true");

        // Get all the customerStatusReportDtlList where noWillingCount is null
        defaultCustomerStatusReportDtlShouldNotBeFound("noWillingCount.specified=false");
    }

    @Test
    @Transactional
    public void getAllCustomerStatusReportDtlsByNoWillingCountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        customerStatusReportDtlRepository.saveAndFlush(customerStatusReportDtl);

        // Get all the customerStatusReportDtlList where noWillingCount greater than or equals to DEFAULT_NO_WILLING_COUNT
        defaultCustomerStatusReportDtlShouldBeFound("noWillingCount.greaterOrEqualThan=" + DEFAULT_NO_WILLING_COUNT);

        // Get all the customerStatusReportDtlList where noWillingCount greater than or equals to UPDATED_NO_WILLING_COUNT
        defaultCustomerStatusReportDtlShouldNotBeFound("noWillingCount.greaterOrEqualThan=" + UPDATED_NO_WILLING_COUNT);
    }

    @Test
    @Transactional
    public void getAllCustomerStatusReportDtlsByNoWillingCountIsLessThanSomething() throws Exception {
        // Initialize the database
        customerStatusReportDtlRepository.saveAndFlush(customerStatusReportDtl);

        // Get all the customerStatusReportDtlList where noWillingCount less than or equals to DEFAULT_NO_WILLING_COUNT
        defaultCustomerStatusReportDtlShouldNotBeFound("noWillingCount.lessThan=" + DEFAULT_NO_WILLING_COUNT);

        // Get all the customerStatusReportDtlList where noWillingCount less than or equals to UPDATED_NO_WILLING_COUNT
        defaultCustomerStatusReportDtlShouldBeFound("noWillingCount.lessThan=" + UPDATED_NO_WILLING_COUNT);
    }


    @Test
    @Transactional
    public void getAllCustomerStatusReportDtlsByConsideringCountIsEqualToSomething() throws Exception {
        // Initialize the database
        customerStatusReportDtlRepository.saveAndFlush(customerStatusReportDtl);

        // Get all the customerStatusReportDtlList where consideringCount equals to DEFAULT_CONSIDERING_COUNT
        defaultCustomerStatusReportDtlShouldBeFound("consideringCount.equals=" + DEFAULT_CONSIDERING_COUNT);

        // Get all the customerStatusReportDtlList where consideringCount equals to UPDATED_CONSIDERING_COUNT
        defaultCustomerStatusReportDtlShouldNotBeFound("consideringCount.equals=" + UPDATED_CONSIDERING_COUNT);
    }

    @Test
    @Transactional
    public void getAllCustomerStatusReportDtlsByConsideringCountIsInShouldWork() throws Exception {
        // Initialize the database
        customerStatusReportDtlRepository.saveAndFlush(customerStatusReportDtl);

        // Get all the customerStatusReportDtlList where consideringCount in DEFAULT_CONSIDERING_COUNT or UPDATED_CONSIDERING_COUNT
        defaultCustomerStatusReportDtlShouldBeFound("consideringCount.in=" + DEFAULT_CONSIDERING_COUNT + "," + UPDATED_CONSIDERING_COUNT);

        // Get all the customerStatusReportDtlList where consideringCount equals to UPDATED_CONSIDERING_COUNT
        defaultCustomerStatusReportDtlShouldNotBeFound("consideringCount.in=" + UPDATED_CONSIDERING_COUNT);
    }

    @Test
    @Transactional
    public void getAllCustomerStatusReportDtlsByConsideringCountIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerStatusReportDtlRepository.saveAndFlush(customerStatusReportDtl);

        // Get all the customerStatusReportDtlList where consideringCount is not null
        defaultCustomerStatusReportDtlShouldBeFound("consideringCount.specified=true");

        // Get all the customerStatusReportDtlList where consideringCount is null
        defaultCustomerStatusReportDtlShouldNotBeFound("consideringCount.specified=false");
    }

    @Test
    @Transactional
    public void getAllCustomerStatusReportDtlsByConsideringCountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        customerStatusReportDtlRepository.saveAndFlush(customerStatusReportDtl);

        // Get all the customerStatusReportDtlList where consideringCount greater than or equals to DEFAULT_CONSIDERING_COUNT
        defaultCustomerStatusReportDtlShouldBeFound("consideringCount.greaterOrEqualThan=" + DEFAULT_CONSIDERING_COUNT);

        // Get all the customerStatusReportDtlList where consideringCount greater than or equals to UPDATED_CONSIDERING_COUNT
        defaultCustomerStatusReportDtlShouldNotBeFound("consideringCount.greaterOrEqualThan=" + UPDATED_CONSIDERING_COUNT);
    }

    @Test
    @Transactional
    public void getAllCustomerStatusReportDtlsByConsideringCountIsLessThanSomething() throws Exception {
        // Initialize the database
        customerStatusReportDtlRepository.saveAndFlush(customerStatusReportDtl);

        // Get all the customerStatusReportDtlList where consideringCount less than or equals to DEFAULT_CONSIDERING_COUNT
        defaultCustomerStatusReportDtlShouldNotBeFound("consideringCount.lessThan=" + DEFAULT_CONSIDERING_COUNT);

        // Get all the customerStatusReportDtlList where consideringCount less than or equals to UPDATED_CONSIDERING_COUNT
        defaultCustomerStatusReportDtlShouldBeFound("consideringCount.lessThan=" + UPDATED_CONSIDERING_COUNT);
    }


    @Test
    @Transactional
    public void getAllCustomerStatusReportDtlsByScheduledCountIsEqualToSomething() throws Exception {
        // Initialize the database
        customerStatusReportDtlRepository.saveAndFlush(customerStatusReportDtl);

        // Get all the customerStatusReportDtlList where scheduledCount equals to DEFAULT_SCHEDULED_COUNT
        defaultCustomerStatusReportDtlShouldBeFound("scheduledCount.equals=" + DEFAULT_SCHEDULED_COUNT);

        // Get all the customerStatusReportDtlList where scheduledCount equals to UPDATED_SCHEDULED_COUNT
        defaultCustomerStatusReportDtlShouldNotBeFound("scheduledCount.equals=" + UPDATED_SCHEDULED_COUNT);
    }

    @Test
    @Transactional
    public void getAllCustomerStatusReportDtlsByScheduledCountIsInShouldWork() throws Exception {
        // Initialize the database
        customerStatusReportDtlRepository.saveAndFlush(customerStatusReportDtl);

        // Get all the customerStatusReportDtlList where scheduledCount in DEFAULT_SCHEDULED_COUNT or UPDATED_SCHEDULED_COUNT
        defaultCustomerStatusReportDtlShouldBeFound("scheduledCount.in=" + DEFAULT_SCHEDULED_COUNT + "," + UPDATED_SCHEDULED_COUNT);

        // Get all the customerStatusReportDtlList where scheduledCount equals to UPDATED_SCHEDULED_COUNT
        defaultCustomerStatusReportDtlShouldNotBeFound("scheduledCount.in=" + UPDATED_SCHEDULED_COUNT);
    }

    @Test
    @Transactional
    public void getAllCustomerStatusReportDtlsByScheduledCountIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerStatusReportDtlRepository.saveAndFlush(customerStatusReportDtl);

        // Get all the customerStatusReportDtlList where scheduledCount is not null
        defaultCustomerStatusReportDtlShouldBeFound("scheduledCount.specified=true");

        // Get all the customerStatusReportDtlList where scheduledCount is null
        defaultCustomerStatusReportDtlShouldNotBeFound("scheduledCount.specified=false");
    }

    @Test
    @Transactional
    public void getAllCustomerStatusReportDtlsByScheduledCountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        customerStatusReportDtlRepository.saveAndFlush(customerStatusReportDtl);

        // Get all the customerStatusReportDtlList where scheduledCount greater than or equals to DEFAULT_SCHEDULED_COUNT
        defaultCustomerStatusReportDtlShouldBeFound("scheduledCount.greaterOrEqualThan=" + DEFAULT_SCHEDULED_COUNT);

        // Get all the customerStatusReportDtlList where scheduledCount greater than or equals to UPDATED_SCHEDULED_COUNT
        defaultCustomerStatusReportDtlShouldNotBeFound("scheduledCount.greaterOrEqualThan=" + UPDATED_SCHEDULED_COUNT);
    }

    @Test
    @Transactional
    public void getAllCustomerStatusReportDtlsByScheduledCountIsLessThanSomething() throws Exception {
        // Initialize the database
        customerStatusReportDtlRepository.saveAndFlush(customerStatusReportDtl);

        // Get all the customerStatusReportDtlList where scheduledCount less than or equals to DEFAULT_SCHEDULED_COUNT
        defaultCustomerStatusReportDtlShouldNotBeFound("scheduledCount.lessThan=" + DEFAULT_SCHEDULED_COUNT);

        // Get all the customerStatusReportDtlList where scheduledCount less than or equals to UPDATED_SCHEDULED_COUNT
        defaultCustomerStatusReportDtlShouldBeFound("scheduledCount.lessThan=" + UPDATED_SCHEDULED_COUNT);
    }


    @Test
    @Transactional
    public void getAllCustomerStatusReportDtlsByDealedCountIsEqualToSomething() throws Exception {
        // Initialize the database
        customerStatusReportDtlRepository.saveAndFlush(customerStatusReportDtl);

        // Get all the customerStatusReportDtlList where dealedCount equals to DEFAULT_DEALED_COUNT
        defaultCustomerStatusReportDtlShouldBeFound("dealedCount.equals=" + DEFAULT_DEALED_COUNT);

        // Get all the customerStatusReportDtlList where dealedCount equals to UPDATED_DEALED_COUNT
        defaultCustomerStatusReportDtlShouldNotBeFound("dealedCount.equals=" + UPDATED_DEALED_COUNT);
    }

    @Test
    @Transactional
    public void getAllCustomerStatusReportDtlsByDealedCountIsInShouldWork() throws Exception {
        // Initialize the database
        customerStatusReportDtlRepository.saveAndFlush(customerStatusReportDtl);

        // Get all the customerStatusReportDtlList where dealedCount in DEFAULT_DEALED_COUNT or UPDATED_DEALED_COUNT
        defaultCustomerStatusReportDtlShouldBeFound("dealedCount.in=" + DEFAULT_DEALED_COUNT + "," + UPDATED_DEALED_COUNT);

        // Get all the customerStatusReportDtlList where dealedCount equals to UPDATED_DEALED_COUNT
        defaultCustomerStatusReportDtlShouldNotBeFound("dealedCount.in=" + UPDATED_DEALED_COUNT);
    }

    @Test
    @Transactional
    public void getAllCustomerStatusReportDtlsByDealedCountIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerStatusReportDtlRepository.saveAndFlush(customerStatusReportDtl);

        // Get all the customerStatusReportDtlList where dealedCount is not null
        defaultCustomerStatusReportDtlShouldBeFound("dealedCount.specified=true");

        // Get all the customerStatusReportDtlList where dealedCount is null
        defaultCustomerStatusReportDtlShouldNotBeFound("dealedCount.specified=false");
    }

    @Test
    @Transactional
    public void getAllCustomerStatusReportDtlsByDealedCountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        customerStatusReportDtlRepository.saveAndFlush(customerStatusReportDtl);

        // Get all the customerStatusReportDtlList where dealedCount greater than or equals to DEFAULT_DEALED_COUNT
        defaultCustomerStatusReportDtlShouldBeFound("dealedCount.greaterOrEqualThan=" + DEFAULT_DEALED_COUNT);

        // Get all the customerStatusReportDtlList where dealedCount greater than or equals to UPDATED_DEALED_COUNT
        defaultCustomerStatusReportDtlShouldNotBeFound("dealedCount.greaterOrEqualThan=" + UPDATED_DEALED_COUNT);
    }

    @Test
    @Transactional
    public void getAllCustomerStatusReportDtlsByDealedCountIsLessThanSomething() throws Exception {
        // Initialize the database
        customerStatusReportDtlRepository.saveAndFlush(customerStatusReportDtl);

        // Get all the customerStatusReportDtlList where dealedCount less than or equals to DEFAULT_DEALED_COUNT
        defaultCustomerStatusReportDtlShouldNotBeFound("dealedCount.lessThan=" + DEFAULT_DEALED_COUNT);

        // Get all the customerStatusReportDtlList where dealedCount less than or equals to UPDATED_DEALED_COUNT
        defaultCustomerStatusReportDtlShouldBeFound("dealedCount.lessThan=" + UPDATED_DEALED_COUNT);
    }


    @Test
    @Transactional
    public void getAllCustomerStatusReportDtlsByNewCreatedCountIsEqualToSomething() throws Exception {
        // Initialize the database
        customerStatusReportDtlRepository.saveAndFlush(customerStatusReportDtl);

        // Get all the customerStatusReportDtlList where newCreatedCount equals to DEFAULT_NEW_CREATED_COUNT
        defaultCustomerStatusReportDtlShouldBeFound("newCreatedCount.equals=" + DEFAULT_NEW_CREATED_COUNT);

        // Get all the customerStatusReportDtlList where newCreatedCount equals to UPDATED_NEW_CREATED_COUNT
        defaultCustomerStatusReportDtlShouldNotBeFound("newCreatedCount.equals=" + UPDATED_NEW_CREATED_COUNT);
    }

    @Test
    @Transactional
    public void getAllCustomerStatusReportDtlsByNewCreatedCountIsInShouldWork() throws Exception {
        // Initialize the database
        customerStatusReportDtlRepository.saveAndFlush(customerStatusReportDtl);

        // Get all the customerStatusReportDtlList where newCreatedCount in DEFAULT_NEW_CREATED_COUNT or UPDATED_NEW_CREATED_COUNT
        defaultCustomerStatusReportDtlShouldBeFound("newCreatedCount.in=" + DEFAULT_NEW_CREATED_COUNT + "," + UPDATED_NEW_CREATED_COUNT);

        // Get all the customerStatusReportDtlList where newCreatedCount equals to UPDATED_NEW_CREATED_COUNT
        defaultCustomerStatusReportDtlShouldNotBeFound("newCreatedCount.in=" + UPDATED_NEW_CREATED_COUNT);
    }

    @Test
    @Transactional
    public void getAllCustomerStatusReportDtlsByNewCreatedCountIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerStatusReportDtlRepository.saveAndFlush(customerStatusReportDtl);

        // Get all the customerStatusReportDtlList where newCreatedCount is not null
        defaultCustomerStatusReportDtlShouldBeFound("newCreatedCount.specified=true");

        // Get all the customerStatusReportDtlList where newCreatedCount is null
        defaultCustomerStatusReportDtlShouldNotBeFound("newCreatedCount.specified=false");
    }

    @Test
    @Transactional
    public void getAllCustomerStatusReportDtlsByNewCreatedCountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        customerStatusReportDtlRepository.saveAndFlush(customerStatusReportDtl);

        // Get all the customerStatusReportDtlList where newCreatedCount greater than or equals to DEFAULT_NEW_CREATED_COUNT
        defaultCustomerStatusReportDtlShouldBeFound("newCreatedCount.greaterOrEqualThan=" + DEFAULT_NEW_CREATED_COUNT);

        // Get all the customerStatusReportDtlList where newCreatedCount greater than or equals to UPDATED_NEW_CREATED_COUNT
        defaultCustomerStatusReportDtlShouldNotBeFound("newCreatedCount.greaterOrEqualThan=" + UPDATED_NEW_CREATED_COUNT);
    }

    @Test
    @Transactional
    public void getAllCustomerStatusReportDtlsByNewCreatedCountIsLessThanSomething() throws Exception {
        // Initialize the database
        customerStatusReportDtlRepository.saveAndFlush(customerStatusReportDtl);

        // Get all the customerStatusReportDtlList where newCreatedCount less than or equals to DEFAULT_NEW_CREATED_COUNT
        defaultCustomerStatusReportDtlShouldNotBeFound("newCreatedCount.lessThan=" + DEFAULT_NEW_CREATED_COUNT);

        // Get all the customerStatusReportDtlList where newCreatedCount less than or equals to UPDATED_NEW_CREATED_COUNT
        defaultCustomerStatusReportDtlShouldBeFound("newCreatedCount.lessThan=" + UPDATED_NEW_CREATED_COUNT);
    }


    @Test
    @Transactional
    public void getAllCustomerStatusReportDtlsByTotalCountIsEqualToSomething() throws Exception {
        // Initialize the database
        customerStatusReportDtlRepository.saveAndFlush(customerStatusReportDtl);

        // Get all the customerStatusReportDtlList where totalCount equals to DEFAULT_TOTAL_COUNT
        defaultCustomerStatusReportDtlShouldBeFound("totalCount.equals=" + DEFAULT_TOTAL_COUNT);

        // Get all the customerStatusReportDtlList where totalCount equals to UPDATED_TOTAL_COUNT
        defaultCustomerStatusReportDtlShouldNotBeFound("totalCount.equals=" + UPDATED_TOTAL_COUNT);
    }

    @Test
    @Transactional
    public void getAllCustomerStatusReportDtlsByTotalCountIsInShouldWork() throws Exception {
        // Initialize the database
        customerStatusReportDtlRepository.saveAndFlush(customerStatusReportDtl);

        // Get all the customerStatusReportDtlList where totalCount in DEFAULT_TOTAL_COUNT or UPDATED_TOTAL_COUNT
        defaultCustomerStatusReportDtlShouldBeFound("totalCount.in=" + DEFAULT_TOTAL_COUNT + "," + UPDATED_TOTAL_COUNT);

        // Get all the customerStatusReportDtlList where totalCount equals to UPDATED_TOTAL_COUNT
        defaultCustomerStatusReportDtlShouldNotBeFound("totalCount.in=" + UPDATED_TOTAL_COUNT);
    }

    @Test
    @Transactional
    public void getAllCustomerStatusReportDtlsByTotalCountIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerStatusReportDtlRepository.saveAndFlush(customerStatusReportDtl);

        // Get all the customerStatusReportDtlList where totalCount is not null
        defaultCustomerStatusReportDtlShouldBeFound("totalCount.specified=true");

        // Get all the customerStatusReportDtlList where totalCount is null
        defaultCustomerStatusReportDtlShouldNotBeFound("totalCount.specified=false");
    }

    @Test
    @Transactional
    public void getAllCustomerStatusReportDtlsByTotalCountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        customerStatusReportDtlRepository.saveAndFlush(customerStatusReportDtl);

        // Get all the customerStatusReportDtlList where totalCount greater than or equals to DEFAULT_TOTAL_COUNT
        defaultCustomerStatusReportDtlShouldBeFound("totalCount.greaterOrEqualThan=" + DEFAULT_TOTAL_COUNT);

        // Get all the customerStatusReportDtlList where totalCount greater than or equals to UPDATED_TOTAL_COUNT
        defaultCustomerStatusReportDtlShouldNotBeFound("totalCount.greaterOrEqualThan=" + UPDATED_TOTAL_COUNT);
    }

    @Test
    @Transactional
    public void getAllCustomerStatusReportDtlsByTotalCountIsLessThanSomething() throws Exception {
        // Initialize the database
        customerStatusReportDtlRepository.saveAndFlush(customerStatusReportDtl);

        // Get all the customerStatusReportDtlList where totalCount less than or equals to DEFAULT_TOTAL_COUNT
        defaultCustomerStatusReportDtlShouldNotBeFound("totalCount.lessThan=" + DEFAULT_TOTAL_COUNT);

        // Get all the customerStatusReportDtlList where totalCount less than or equals to UPDATED_TOTAL_COUNT
        defaultCustomerStatusReportDtlShouldBeFound("totalCount.lessThan=" + UPDATED_TOTAL_COUNT);
    }


    @Test
    @Transactional
    public void getAllCustomerStatusReportDtlsByFinishRateIsEqualToSomething() throws Exception {
        // Initialize the database
        customerStatusReportDtlRepository.saveAndFlush(customerStatusReportDtl);

        // Get all the customerStatusReportDtlList where finishRate equals to DEFAULT_FINISH_RATE
        defaultCustomerStatusReportDtlShouldBeFound("finishRate.equals=" + DEFAULT_FINISH_RATE);

        // Get all the customerStatusReportDtlList where finishRate equals to UPDATED_FINISH_RATE
        defaultCustomerStatusReportDtlShouldNotBeFound("finishRate.equals=" + UPDATED_FINISH_RATE);
    }

    @Test
    @Transactional
    public void getAllCustomerStatusReportDtlsByFinishRateIsInShouldWork() throws Exception {
        // Initialize the database
        customerStatusReportDtlRepository.saveAndFlush(customerStatusReportDtl);

        // Get all the customerStatusReportDtlList where finishRate in DEFAULT_FINISH_RATE or UPDATED_FINISH_RATE
        defaultCustomerStatusReportDtlShouldBeFound("finishRate.in=" + DEFAULT_FINISH_RATE + "," + UPDATED_FINISH_RATE);

        // Get all the customerStatusReportDtlList where finishRate equals to UPDATED_FINISH_RATE
        defaultCustomerStatusReportDtlShouldNotBeFound("finishRate.in=" + UPDATED_FINISH_RATE);
    }

    @Test
    @Transactional
    public void getAllCustomerStatusReportDtlsByFinishRateIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerStatusReportDtlRepository.saveAndFlush(customerStatusReportDtl);

        // Get all the customerStatusReportDtlList where finishRate is not null
        defaultCustomerStatusReportDtlShouldBeFound("finishRate.specified=true");

        // Get all the customerStatusReportDtlList where finishRate is null
        defaultCustomerStatusReportDtlShouldNotBeFound("finishRate.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultCustomerStatusReportDtlShouldBeFound(String filter) throws Exception {
        restCustomerStatusReportDtlMockMvc.perform(get("/api/customer-status-report-dtls?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customerStatusReportDtl.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].userName").value(hasItem(DEFAULT_USER_NAME)))
            .andExpect(jsonPath("$.[*].ageTooSmallCount").value(hasItem(DEFAULT_AGE_TOO_SMALL_COUNT)))
            .andExpect(jsonPath("$.[*].errorInformation").value(hasItem(DEFAULT_ERROR_INFORMATION)))
            .andExpect(jsonPath("$.[*].noWillingCount").value(hasItem(DEFAULT_NO_WILLING_COUNT)))
            .andExpect(jsonPath("$.[*].consideringCount").value(hasItem(DEFAULT_CONSIDERING_COUNT)))
            .andExpect(jsonPath("$.[*].scheduledCount").value(hasItem(DEFAULT_SCHEDULED_COUNT)))
            .andExpect(jsonPath("$.[*].dealedCount").value(hasItem(DEFAULT_DEALED_COUNT)))
            .andExpect(jsonPath("$.[*].newCreatedCount").value(hasItem(DEFAULT_NEW_CREATED_COUNT)))
            .andExpect(jsonPath("$.[*].totalCount").value(hasItem(DEFAULT_TOTAL_COUNT)))
            .andExpect(jsonPath("$.[*].finishRate").value(hasItem(DEFAULT_FINISH_RATE.doubleValue())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultCustomerStatusReportDtlShouldNotBeFound(String filter) throws Exception {
        restCustomerStatusReportDtlMockMvc.perform(get("/api/customer-status-report-dtls?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingCustomerStatusReportDtl() throws Exception {
        // Get the customerStatusReportDtl
        restCustomerStatusReportDtlMockMvc.perform(get("/api/customer-status-report-dtls/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCustomerStatusReportDtl() throws Exception {
        // Initialize the database
        customerStatusReportDtlService.save(customerStatusReportDtl);

        int databaseSizeBeforeUpdate = customerStatusReportDtlRepository.findAll().size();

        // Update the customerStatusReportDtl
        CustomerStatusReportDtl updatedCustomerStatusReportDtl = customerStatusReportDtlRepository.findOne(customerStatusReportDtl.getId());
        updatedCustomerStatusReportDtl
            .userId(UPDATED_USER_ID)
            .userName(UPDATED_USER_NAME)
            .ageTooSmallCount(UPDATED_AGE_TOO_SMALL_COUNT)
            .errorInformation(UPDATED_ERROR_INFORMATION)
            .noWillingCount(UPDATED_NO_WILLING_COUNT)
            .consideringCount(UPDATED_CONSIDERING_COUNT)
            .scheduledCount(UPDATED_SCHEDULED_COUNT)
            .dealedCount(UPDATED_DEALED_COUNT)
            .newCreatedCount(UPDATED_NEW_CREATED_COUNT)
            .totalCount(UPDATED_TOTAL_COUNT)
            .finishRate(UPDATED_FINISH_RATE);

        restCustomerStatusReportDtlMockMvc.perform(put("/api/customer-status-report-dtls")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCustomerStatusReportDtl)))
            .andExpect(status().isOk());

        // Validate the CustomerStatusReportDtl in the database
        List<CustomerStatusReportDtl> customerStatusReportDtlList = customerStatusReportDtlRepository.findAll();
        assertThat(customerStatusReportDtlList).hasSize(databaseSizeBeforeUpdate);
        CustomerStatusReportDtl testCustomerStatusReportDtl = customerStatusReportDtlList.get(customerStatusReportDtlList.size() - 1);
        assertThat(testCustomerStatusReportDtl.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testCustomerStatusReportDtl.getUserName()).isEqualTo(UPDATED_USER_NAME);
        assertThat(testCustomerStatusReportDtl.getAgeTooSmallCount()).isEqualTo(UPDATED_AGE_TOO_SMALL_COUNT);
        assertThat(testCustomerStatusReportDtl.getErrorInformation()).isEqualTo(UPDATED_ERROR_INFORMATION);
        assertThat(testCustomerStatusReportDtl.getNoWillingCount()).isEqualTo(UPDATED_NO_WILLING_COUNT);
        assertThat(testCustomerStatusReportDtl.getConsideringCount()).isEqualTo(UPDATED_CONSIDERING_COUNT);
        assertThat(testCustomerStatusReportDtl.getScheduledCount()).isEqualTo(UPDATED_SCHEDULED_COUNT);
        assertThat(testCustomerStatusReportDtl.getDealedCount()).isEqualTo(UPDATED_DEALED_COUNT);
        assertThat(testCustomerStatusReportDtl.getNewCreatedCount()).isEqualTo(UPDATED_NEW_CREATED_COUNT);
        assertThat(testCustomerStatusReportDtl.getTotalCount()).isEqualTo(UPDATED_TOTAL_COUNT);
        assertThat(testCustomerStatusReportDtl.getFinishRate()).isEqualTo(UPDATED_FINISH_RATE);
    }

    @Test
    @Transactional
    public void updateNonExistingCustomerStatusReportDtl() throws Exception {
        int databaseSizeBeforeUpdate = customerStatusReportDtlRepository.findAll().size();

        // Create the CustomerStatusReportDtl

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCustomerStatusReportDtlMockMvc.perform(put("/api/customer-status-report-dtls")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerStatusReportDtl)))
            .andExpect(status().isCreated());

        // Validate the CustomerStatusReportDtl in the database
        List<CustomerStatusReportDtl> customerStatusReportDtlList = customerStatusReportDtlRepository.findAll();
        assertThat(customerStatusReportDtlList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCustomerStatusReportDtl() throws Exception {
        // Initialize the database
        customerStatusReportDtlService.save(customerStatusReportDtl);

        int databaseSizeBeforeDelete = customerStatusReportDtlRepository.findAll().size();

        // Get the customerStatusReportDtl
        restCustomerStatusReportDtlMockMvc.perform(delete("/api/customer-status-report-dtls/{id}", customerStatusReportDtl.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CustomerStatusReportDtl> customerStatusReportDtlList = customerStatusReportDtlRepository.findAll();
        assertThat(customerStatusReportDtlList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomerStatusReportDtl.class);
        CustomerStatusReportDtl customerStatusReportDtl1 = new CustomerStatusReportDtl();
        customerStatusReportDtl1.setId(1L);
        CustomerStatusReportDtl customerStatusReportDtl2 = new CustomerStatusReportDtl();
        customerStatusReportDtl2.setId(customerStatusReportDtl1.getId());
        assertThat(customerStatusReportDtl1).isEqualTo(customerStatusReportDtl2);
        customerStatusReportDtl2.setId(2L);
        assertThat(customerStatusReportDtl1).isNotEqualTo(customerStatusReportDtl2);
        customerStatusReportDtl1.setId(null);
        assertThat(customerStatusReportDtl1).isNotEqualTo(customerStatusReportDtl2);
    }
}
