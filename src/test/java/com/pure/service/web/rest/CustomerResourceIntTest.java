package com.pure.service.web.rest;

import com.pure.service.SimpleServiceApp;
import com.pure.service.domain.Customer;
import com.pure.service.repository.CustomerRepository;
import com.pure.service.service.CustomerQueryService;
import com.pure.service.service.CustomerService;
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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 * Test class for the CustomerResource REST controller.
 *
 * @see CustomerResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SimpleServiceApp.class)
public class CustomerResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_AGE = 1;
    private static final Integer UPDATED_AGE = 2;

    private static final String DEFAULT_CONTACT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT_PHONE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerQueryService customerQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCustomerMockMvc;

    private Customer customer;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CustomerResource customerResource = new CustomerResource(customerService, customerQueryService);
        this.restCustomerMockMvc = MockMvcBuilders.standaloneSetup(customerResource)
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
    public static Customer createEntity(EntityManager em) {
        Customer customer = new Customer()
            .name(DEFAULT_NAME)
            .age(DEFAULT_AGE)
            .contactPhoneNumber(DEFAULT_CONTACT_PHONE_NUMBER);
//            .createdBy(DEFAULT_CREATED_BY)
//            .createdDate(DEFAULT_CREATED_DATE)
//            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
//            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return customer;
    }

    @Before
    public void initTest() {
        customer = createEntity(em);
    }

    @Test
    @Transactional
    public void createCustomer() throws Exception {
        int databaseSizeBeforeCreate = customerRepository.findAll().size();

        // Create the Customer
        restCustomerMockMvc.perform(post("/api/customers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customer)))
            .andExpect(status().isCreated());

        // Validate the Customer in the database
        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeCreate + 1);
        Customer testCustomer = customerList.get(customerList.size() - 1);
        assertThat(testCustomer.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCustomer.getAge()).isEqualTo(DEFAULT_AGE);
        assertThat(testCustomer.getContactPhoneNumber()).isEqualTo(DEFAULT_CONTACT_PHONE_NUMBER);
        assertThat(testCustomer.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testCustomer.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testCustomer.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testCustomer.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createCustomerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = customerRepository.findAll().size();

        // Create the Customer with an existing ID
        customer.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustomerMockMvc.perform(post("/api/customers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customer)))
            .andExpect(status().isBadRequest());

        // Validate the Customer in the database
        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCustomers() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList
        restCustomerMockMvc.perform(get("/api/customers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customer.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE)))
            .andExpect(jsonPath("$.[*].contactPhoneNumber").value(hasItem(DEFAULT_CONTACT_PHONE_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    public void getCustomer() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get the customer
        restCustomerMockMvc.perform(get("/api/customers/{id}", customer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(customer.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.age").value(DEFAULT_AGE))
            .andExpect(jsonPath("$.contactPhoneNumber").value(DEFAULT_CONTACT_PHONE_NUMBER.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    public void getAllCustomersByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where name equals to DEFAULT_NAME
        defaultCustomerShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the customerList where name equals to UPDATED_NAME
        defaultCustomerShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCustomersByNameIsInShouldWork() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCustomerShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the customerList where name equals to UPDATED_NAME
        defaultCustomerShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCustomersByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where name is not null
        defaultCustomerShouldBeFound("name.specified=true");

        // Get all the customerList where name is null
        defaultCustomerShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllCustomersByAgeIsEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where age equals to DEFAULT_AGE
        defaultCustomerShouldBeFound("age.equals=" + DEFAULT_AGE);

        // Get all the customerList where age equals to UPDATED_AGE
        defaultCustomerShouldNotBeFound("age.equals=" + UPDATED_AGE);
    }

    @Test
    @Transactional
    public void getAllCustomersByAgeIsInShouldWork() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where age in DEFAULT_AGE or UPDATED_AGE
        defaultCustomerShouldBeFound("age.in=" + DEFAULT_AGE + "," + UPDATED_AGE);

        // Get all the customerList where age equals to UPDATED_AGE
        defaultCustomerShouldNotBeFound("age.in=" + UPDATED_AGE);
    }

    @Test
    @Transactional
    public void getAllCustomersByAgeIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where age is not null
        defaultCustomerShouldBeFound("age.specified=true");

        // Get all the customerList where age is null
        defaultCustomerShouldNotBeFound("age.specified=false");
    }

    @Test
    @Transactional
    public void getAllCustomersByAgeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where age greater than or equals to DEFAULT_AGE
        defaultCustomerShouldBeFound("age.greaterOrEqualThan=" + DEFAULT_AGE);

        // Get all the customerList where age greater than or equals to UPDATED_AGE
        defaultCustomerShouldNotBeFound("age.greaterOrEqualThan=" + UPDATED_AGE);
    }

    @Test
    @Transactional
    public void getAllCustomersByAgeIsLessThanSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where age less than or equals to DEFAULT_AGE
        defaultCustomerShouldNotBeFound("age.lessThan=" + DEFAULT_AGE);

        // Get all the customerList where age less than or equals to UPDATED_AGE
        defaultCustomerShouldBeFound("age.lessThan=" + UPDATED_AGE);
    }


    @Test
    @Transactional
    public void getAllCustomersByContactPhoneNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where contactPhoneNumber equals to DEFAULT_CONTACT_PHONE_NUMBER
        defaultCustomerShouldBeFound("contactPhoneNumber.equals=" + DEFAULT_CONTACT_PHONE_NUMBER);

        // Get all the customerList where contactPhoneNumber equals to UPDATED_CONTACT_PHONE_NUMBER
        defaultCustomerShouldNotBeFound("contactPhoneNumber.equals=" + UPDATED_CONTACT_PHONE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllCustomersByContactPhoneNumberIsInShouldWork() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where contactPhoneNumber in DEFAULT_CONTACT_PHONE_NUMBER or UPDATED_CONTACT_PHONE_NUMBER
        defaultCustomerShouldBeFound("contactPhoneNumber.in=" + DEFAULT_CONTACT_PHONE_NUMBER + "," + UPDATED_CONTACT_PHONE_NUMBER);

        // Get all the customerList where contactPhoneNumber equals to UPDATED_CONTACT_PHONE_NUMBER
        defaultCustomerShouldNotBeFound("contactPhoneNumber.in=" + UPDATED_CONTACT_PHONE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllCustomersByContactPhoneNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where contactPhoneNumber is not null
        defaultCustomerShouldBeFound("contactPhoneNumber.specified=true");

        // Get all the customerList where contactPhoneNumber is null
        defaultCustomerShouldNotBeFound("contactPhoneNumber.specified=false");
    }

    @Test
    @Transactional
    public void getAllCustomersByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where createdBy equals to DEFAULT_CREATED_BY
        defaultCustomerShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the customerList where createdBy equals to UPDATED_CREATED_BY
        defaultCustomerShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllCustomersByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultCustomerShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the customerList where createdBy equals to UPDATED_CREATED_BY
        defaultCustomerShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllCustomersByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where createdBy is not null
        defaultCustomerShouldBeFound("createdBy.specified=true");

        // Get all the customerList where createdBy is null
        defaultCustomerShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllCustomersByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where createdDate equals to DEFAULT_CREATED_DATE
        defaultCustomerShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the customerList where createdDate equals to UPDATED_CREATED_DATE
        defaultCustomerShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllCustomersByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultCustomerShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the customerList where createdDate equals to UPDATED_CREATED_DATE
        defaultCustomerShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllCustomersByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where createdDate is not null
        defaultCustomerShouldBeFound("createdDate.specified=true");

        // Get all the customerList where createdDate is null
        defaultCustomerShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllCustomersByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultCustomerShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the customerList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultCustomerShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllCustomersByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultCustomerShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the customerList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultCustomerShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllCustomersByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where lastModifiedBy is not null
        defaultCustomerShouldBeFound("lastModifiedBy.specified=true");

        // Get all the customerList where lastModifiedBy is null
        defaultCustomerShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllCustomersByLastModifiedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where lastModifiedDate equals to DEFAULT_LAST_MODIFIED_DATE
        defaultCustomerShouldBeFound("lastModifiedDate.equals=" + DEFAULT_LAST_MODIFIED_DATE);

        // Get all the customerList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultCustomerShouldNotBeFound("lastModifiedDate.equals=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllCustomersByLastModifiedDateIsInShouldWork() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where lastModifiedDate in DEFAULT_LAST_MODIFIED_DATE or UPDATED_LAST_MODIFIED_DATE
        defaultCustomerShouldBeFound("lastModifiedDate.in=" + DEFAULT_LAST_MODIFIED_DATE + "," + UPDATED_LAST_MODIFIED_DATE);

        // Get all the customerList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultCustomerShouldNotBeFound("lastModifiedDate.in=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllCustomersByLastModifiedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where lastModifiedDate is not null
        defaultCustomerShouldBeFound("lastModifiedDate.specified=true");

        // Get all the customerList where lastModifiedDate is null
        defaultCustomerShouldNotBeFound("lastModifiedDate.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultCustomerShouldBeFound(String filter) throws Exception {
        restCustomerMockMvc.perform(get("/api/customers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customer.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE)))
            .andExpect(jsonPath("$.[*].contactPhoneNumber").value(hasItem(DEFAULT_CONTACT_PHONE_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultCustomerShouldNotBeFound(String filter) throws Exception {
        restCustomerMockMvc.perform(get("/api/customers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingCustomer() throws Exception {
        // Get the customer
        restCustomerMockMvc.perform(get("/api/customers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCustomer() throws Exception {
        // Initialize the database
        customerService.save(customer);

        int databaseSizeBeforeUpdate = customerRepository.findAll().size();

        // Update the customer
        Customer updatedCustomer = customerRepository.findOne(customer.getId());
        updatedCustomer
            .name(UPDATED_NAME)
            .age(UPDATED_AGE)
            .contactPhoneNumber(UPDATED_CONTACT_PHONE_NUMBER);
//            .createdBy(UPDATED_CREATED_BY)
//            .createdDate(UPDATED_CREATED_DATE)
//            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
//            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restCustomerMockMvc.perform(put("/api/customers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCustomer)))
            .andExpect(status().isOk());

        // Validate the Customer in the database
        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeUpdate);
        Customer testCustomer = customerList.get(customerList.size() - 1);
        assertThat(testCustomer.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCustomer.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testCustomer.getContactPhoneNumber()).isEqualTo(UPDATED_CONTACT_PHONE_NUMBER);
        assertThat(testCustomer.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testCustomer.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testCustomer.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testCustomer.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingCustomer() throws Exception {
        int databaseSizeBeforeUpdate = customerRepository.findAll().size();

        // Create the Customer

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCustomerMockMvc.perform(put("/api/customers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customer)))
            .andExpect(status().isCreated());

        // Validate the Customer in the database
        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCustomer() throws Exception {
        // Initialize the database
        customerService.save(customer);

        int databaseSizeBeforeDelete = customerRepository.findAll().size();

        // Get the customer
        restCustomerMockMvc.perform(delete("/api/customers/{id}", customer.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Customer.class);
        Customer customer1 = new Customer();
        customer1.setId(1L);
        Customer customer2 = new Customer();
        customer2.setId(customer1.getId());
        assertThat(customer1).isEqualTo(customer2);
        customer2.setId(2L);
        assertThat(customer1).isNotEqualTo(customer2);
        customer1.setId(null);
        assertThat(customer1).isNotEqualTo(customer2);
    }
}
