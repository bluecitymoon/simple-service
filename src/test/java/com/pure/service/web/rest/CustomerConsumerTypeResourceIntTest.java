package com.pure.service.web.rest;

import com.pure.service.SimpleServiceApp;

import com.pure.service.domain.CustomerConsumerType;
import com.pure.service.repository.CustomerConsumerTypeRepository;
import com.pure.service.service.CustomerConsumerTypeService;
import com.pure.service.web.rest.errors.ExceptionTranslator;
import com.pure.service.service.dto.CustomerConsumerTypeCriteria;
import com.pure.service.service.CustomerConsumerTypeQueryService;

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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CustomerConsumerTypeResource REST controller.
 *
 * @see CustomerConsumerTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SimpleServiceApp.class)
public class CustomerConsumerTypeResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private CustomerConsumerTypeRepository customerConsumerTypeRepository;

    @Autowired
    private CustomerConsumerTypeService customerConsumerTypeService;

    @Autowired
    private CustomerConsumerTypeQueryService customerConsumerTypeQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCustomerConsumerTypeMockMvc;

    private CustomerConsumerType customerConsumerType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CustomerConsumerTypeResource customerConsumerTypeResource = new CustomerConsumerTypeResource(customerConsumerTypeService, customerConsumerTypeQueryService);
        this.restCustomerConsumerTypeMockMvc = MockMvcBuilders.standaloneSetup(customerConsumerTypeResource)
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
    public static CustomerConsumerType createEntity(EntityManager em) {
        CustomerConsumerType customerConsumerType = new CustomerConsumerType()
            .name(DEFAULT_NAME)
            .code(DEFAULT_CODE)
            .description(DEFAULT_DESCRIPTION);
        return customerConsumerType;
    }

    @Before
    public void initTest() {
        customerConsumerType = createEntity(em);
    }

    @Test
    @Transactional
    public void createCustomerConsumerType() throws Exception {
        int databaseSizeBeforeCreate = customerConsumerTypeRepository.findAll().size();

        // Create the CustomerConsumerType
        restCustomerConsumerTypeMockMvc.perform(post("/api/customer-consumer-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerConsumerType)))
            .andExpect(status().isCreated());

        // Validate the CustomerConsumerType in the database
        List<CustomerConsumerType> customerConsumerTypeList = customerConsumerTypeRepository.findAll();
        assertThat(customerConsumerTypeList).hasSize(databaseSizeBeforeCreate + 1);
        CustomerConsumerType testCustomerConsumerType = customerConsumerTypeList.get(customerConsumerTypeList.size() - 1);
        assertThat(testCustomerConsumerType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCustomerConsumerType.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testCustomerConsumerType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createCustomerConsumerTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = customerConsumerTypeRepository.findAll().size();

        // Create the CustomerConsumerType with an existing ID
        customerConsumerType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustomerConsumerTypeMockMvc.perform(post("/api/customer-consumer-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerConsumerType)))
            .andExpect(status().isBadRequest());

        // Validate the CustomerConsumerType in the database
        List<CustomerConsumerType> customerConsumerTypeList = customerConsumerTypeRepository.findAll();
        assertThat(customerConsumerTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCustomerConsumerTypes() throws Exception {
        // Initialize the database
        customerConsumerTypeRepository.saveAndFlush(customerConsumerType);

        // Get all the customerConsumerTypeList
        restCustomerConsumerTypeMockMvc.perform(get("/api/customer-consumer-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customerConsumerType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    public void getCustomerConsumerType() throws Exception {
        // Initialize the database
        customerConsumerTypeRepository.saveAndFlush(customerConsumerType);

        // Get the customerConsumerType
        restCustomerConsumerTypeMockMvc.perform(get("/api/customer-consumer-types/{id}", customerConsumerType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(customerConsumerType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    public void getAllCustomerConsumerTypesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        customerConsumerTypeRepository.saveAndFlush(customerConsumerType);

        // Get all the customerConsumerTypeList where name equals to DEFAULT_NAME
        defaultCustomerConsumerTypeShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the customerConsumerTypeList where name equals to UPDATED_NAME
        defaultCustomerConsumerTypeShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCustomerConsumerTypesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        customerConsumerTypeRepository.saveAndFlush(customerConsumerType);

        // Get all the customerConsumerTypeList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCustomerConsumerTypeShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the customerConsumerTypeList where name equals to UPDATED_NAME
        defaultCustomerConsumerTypeShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCustomerConsumerTypesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerConsumerTypeRepository.saveAndFlush(customerConsumerType);

        // Get all the customerConsumerTypeList where name is not null
        defaultCustomerConsumerTypeShouldBeFound("name.specified=true");

        // Get all the customerConsumerTypeList where name is null
        defaultCustomerConsumerTypeShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllCustomerConsumerTypesByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        customerConsumerTypeRepository.saveAndFlush(customerConsumerType);

        // Get all the customerConsumerTypeList where code equals to DEFAULT_CODE
        defaultCustomerConsumerTypeShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the customerConsumerTypeList where code equals to UPDATED_CODE
        defaultCustomerConsumerTypeShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllCustomerConsumerTypesByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        customerConsumerTypeRepository.saveAndFlush(customerConsumerType);

        // Get all the customerConsumerTypeList where code in DEFAULT_CODE or UPDATED_CODE
        defaultCustomerConsumerTypeShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the customerConsumerTypeList where code equals to UPDATED_CODE
        defaultCustomerConsumerTypeShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllCustomerConsumerTypesByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerConsumerTypeRepository.saveAndFlush(customerConsumerType);

        // Get all the customerConsumerTypeList where code is not null
        defaultCustomerConsumerTypeShouldBeFound("code.specified=true");

        // Get all the customerConsumerTypeList where code is null
        defaultCustomerConsumerTypeShouldNotBeFound("code.specified=false");
    }

    @Test
    @Transactional
    public void getAllCustomerConsumerTypesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        customerConsumerTypeRepository.saveAndFlush(customerConsumerType);

        // Get all the customerConsumerTypeList where description equals to DEFAULT_DESCRIPTION
        defaultCustomerConsumerTypeShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the customerConsumerTypeList where description equals to UPDATED_DESCRIPTION
        defaultCustomerConsumerTypeShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCustomerConsumerTypesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        customerConsumerTypeRepository.saveAndFlush(customerConsumerType);

        // Get all the customerConsumerTypeList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultCustomerConsumerTypeShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the customerConsumerTypeList where description equals to UPDATED_DESCRIPTION
        defaultCustomerConsumerTypeShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCustomerConsumerTypesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerConsumerTypeRepository.saveAndFlush(customerConsumerType);

        // Get all the customerConsumerTypeList where description is not null
        defaultCustomerConsumerTypeShouldBeFound("description.specified=true");

        // Get all the customerConsumerTypeList where description is null
        defaultCustomerConsumerTypeShouldNotBeFound("description.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultCustomerConsumerTypeShouldBeFound(String filter) throws Exception {
        restCustomerConsumerTypeMockMvc.perform(get("/api/customer-consumer-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customerConsumerType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultCustomerConsumerTypeShouldNotBeFound(String filter) throws Exception {
        restCustomerConsumerTypeMockMvc.perform(get("/api/customer-consumer-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingCustomerConsumerType() throws Exception {
        // Get the customerConsumerType
        restCustomerConsumerTypeMockMvc.perform(get("/api/customer-consumer-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCustomerConsumerType() throws Exception {
        // Initialize the database
        customerConsumerTypeService.save(customerConsumerType);

        int databaseSizeBeforeUpdate = customerConsumerTypeRepository.findAll().size();

        // Update the customerConsumerType
        CustomerConsumerType updatedCustomerConsumerType = customerConsumerTypeRepository.findOne(customerConsumerType.getId());
        updatedCustomerConsumerType
            .name(UPDATED_NAME)
            .code(UPDATED_CODE)
            .description(UPDATED_DESCRIPTION);

        restCustomerConsumerTypeMockMvc.perform(put("/api/customer-consumer-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCustomerConsumerType)))
            .andExpect(status().isOk());

        // Validate the CustomerConsumerType in the database
        List<CustomerConsumerType> customerConsumerTypeList = customerConsumerTypeRepository.findAll();
        assertThat(customerConsumerTypeList).hasSize(databaseSizeBeforeUpdate);
        CustomerConsumerType testCustomerConsumerType = customerConsumerTypeList.get(customerConsumerTypeList.size() - 1);
        assertThat(testCustomerConsumerType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCustomerConsumerType.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testCustomerConsumerType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingCustomerConsumerType() throws Exception {
        int databaseSizeBeforeUpdate = customerConsumerTypeRepository.findAll().size();

        // Create the CustomerConsumerType

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCustomerConsumerTypeMockMvc.perform(put("/api/customer-consumer-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerConsumerType)))
            .andExpect(status().isCreated());

        // Validate the CustomerConsumerType in the database
        List<CustomerConsumerType> customerConsumerTypeList = customerConsumerTypeRepository.findAll();
        assertThat(customerConsumerTypeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCustomerConsumerType() throws Exception {
        // Initialize the database
        customerConsumerTypeService.save(customerConsumerType);

        int databaseSizeBeforeDelete = customerConsumerTypeRepository.findAll().size();

        // Get the customerConsumerType
        restCustomerConsumerTypeMockMvc.perform(delete("/api/customer-consumer-types/{id}", customerConsumerType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CustomerConsumerType> customerConsumerTypeList = customerConsumerTypeRepository.findAll();
        assertThat(customerConsumerTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomerConsumerType.class);
        CustomerConsumerType customerConsumerType1 = new CustomerConsumerType();
        customerConsumerType1.setId(1L);
        CustomerConsumerType customerConsumerType2 = new CustomerConsumerType();
        customerConsumerType2.setId(customerConsumerType1.getId());
        assertThat(customerConsumerType1).isEqualTo(customerConsumerType2);
        customerConsumerType2.setId(2L);
        assertThat(customerConsumerType1).isNotEqualTo(customerConsumerType2);
        customerConsumerType1.setId(null);
        assertThat(customerConsumerType1).isNotEqualTo(customerConsumerType2);
    }
}
