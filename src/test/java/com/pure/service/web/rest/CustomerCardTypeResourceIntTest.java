package com.pure.service.web.rest;

import com.pure.service.SimpleServiceApp;

import com.pure.service.domain.CustomerCardType;
import com.pure.service.repository.CustomerCardTypeRepository;
import com.pure.service.service.CustomerCardTypeService;
import com.pure.service.web.rest.errors.ExceptionTranslator;
import com.pure.service.service.dto.CustomerCardTypeCriteria;
import com.pure.service.service.CustomerCardTypeQueryService;

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
 * Test class for the CustomerCardTypeResource REST controller.
 *
 * @see CustomerCardTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SimpleServiceApp.class)
public class CustomerCardTypeResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    @Autowired
    private CustomerCardTypeRepository customerCardTypeRepository;

    @Autowired
    private CustomerCardTypeService customerCardTypeService;

    @Autowired
    private CustomerCardTypeQueryService customerCardTypeQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCustomerCardTypeMockMvc;

    private CustomerCardType customerCardType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CustomerCardTypeResource customerCardTypeResource = new CustomerCardTypeResource(customerCardTypeService, customerCardTypeQueryService);
        this.restCustomerCardTypeMockMvc = MockMvcBuilders.standaloneSetup(customerCardTypeResource)
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
    public static CustomerCardType createEntity(EntityManager em) {
        CustomerCardType customerCardType = new CustomerCardType()
            .name(DEFAULT_NAME)
            .code(DEFAULT_CODE);
        return customerCardType;
    }

    @Before
    public void initTest() {
        customerCardType = createEntity(em);
    }

    @Test
    @Transactional
    public void createCustomerCardType() throws Exception {
        int databaseSizeBeforeCreate = customerCardTypeRepository.findAll().size();

        // Create the CustomerCardType
        restCustomerCardTypeMockMvc.perform(post("/api/customer-card-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerCardType)))
            .andExpect(status().isCreated());

        // Validate the CustomerCardType in the database
        List<CustomerCardType> customerCardTypeList = customerCardTypeRepository.findAll();
        assertThat(customerCardTypeList).hasSize(databaseSizeBeforeCreate + 1);
        CustomerCardType testCustomerCardType = customerCardTypeList.get(customerCardTypeList.size() - 1);
        assertThat(testCustomerCardType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCustomerCardType.getCode()).isEqualTo(DEFAULT_CODE);
    }

    @Test
    @Transactional
    public void createCustomerCardTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = customerCardTypeRepository.findAll().size();

        // Create the CustomerCardType with an existing ID
        customerCardType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustomerCardTypeMockMvc.perform(post("/api/customer-card-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerCardType)))
            .andExpect(status().isBadRequest());

        // Validate the CustomerCardType in the database
        List<CustomerCardType> customerCardTypeList = customerCardTypeRepository.findAll();
        assertThat(customerCardTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCustomerCardTypes() throws Exception {
        // Initialize the database
        customerCardTypeRepository.saveAndFlush(customerCardType);

        // Get all the customerCardTypeList
        restCustomerCardTypeMockMvc.perform(get("/api/customer-card-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customerCardType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)));
    }

    @Test
    @Transactional
    public void getCustomerCardType() throws Exception {
        // Initialize the database
        customerCardTypeRepository.saveAndFlush(customerCardType);

        // Get the customerCardType
        restCustomerCardTypeMockMvc.perform(get("/api/customer-card-types/{id}", customerCardType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(customerCardType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE));
    }

    @Test
    @Transactional
    public void getAllCustomerCardTypesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        customerCardTypeRepository.saveAndFlush(customerCardType);

        // Get all the customerCardTypeList where name equals to DEFAULT_NAME
        defaultCustomerCardTypeShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the customerCardTypeList where name equals to UPDATED_NAME
        defaultCustomerCardTypeShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCustomerCardTypesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        customerCardTypeRepository.saveAndFlush(customerCardType);

        // Get all the customerCardTypeList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCustomerCardTypeShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the customerCardTypeList where name equals to UPDATED_NAME
        defaultCustomerCardTypeShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCustomerCardTypesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerCardTypeRepository.saveAndFlush(customerCardType);

        // Get all the customerCardTypeList where name is not null
        defaultCustomerCardTypeShouldBeFound("name.specified=true");

        // Get all the customerCardTypeList where name is null
        defaultCustomerCardTypeShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllCustomerCardTypesByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        customerCardTypeRepository.saveAndFlush(customerCardType);

        // Get all the customerCardTypeList where code equals to DEFAULT_CODE
        defaultCustomerCardTypeShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the customerCardTypeList where code equals to UPDATED_CODE
        defaultCustomerCardTypeShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllCustomerCardTypesByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        customerCardTypeRepository.saveAndFlush(customerCardType);

        // Get all the customerCardTypeList where code in DEFAULT_CODE or UPDATED_CODE
        defaultCustomerCardTypeShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the customerCardTypeList where code equals to UPDATED_CODE
        defaultCustomerCardTypeShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllCustomerCardTypesByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerCardTypeRepository.saveAndFlush(customerCardType);

        // Get all the customerCardTypeList where code is not null
        defaultCustomerCardTypeShouldBeFound("code.specified=true");

        // Get all the customerCardTypeList where code is null
        defaultCustomerCardTypeShouldNotBeFound("code.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultCustomerCardTypeShouldBeFound(String filter) throws Exception {
        restCustomerCardTypeMockMvc.perform(get("/api/customer-card-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customerCardType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultCustomerCardTypeShouldNotBeFound(String filter) throws Exception {
        restCustomerCardTypeMockMvc.perform(get("/api/customer-card-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingCustomerCardType() throws Exception {
        // Get the customerCardType
        restCustomerCardTypeMockMvc.perform(get("/api/customer-card-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCustomerCardType() throws Exception {
        // Initialize the database
        customerCardTypeService.save(customerCardType);

        int databaseSizeBeforeUpdate = customerCardTypeRepository.findAll().size();

        // Update the customerCardType
        CustomerCardType updatedCustomerCardType = customerCardTypeRepository.findOne(customerCardType.getId());
        updatedCustomerCardType
            .name(UPDATED_NAME)
            .code(UPDATED_CODE);

        restCustomerCardTypeMockMvc.perform(put("/api/customer-card-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCustomerCardType)))
            .andExpect(status().isOk());

        // Validate the CustomerCardType in the database
        List<CustomerCardType> customerCardTypeList = customerCardTypeRepository.findAll();
        assertThat(customerCardTypeList).hasSize(databaseSizeBeforeUpdate);
        CustomerCardType testCustomerCardType = customerCardTypeList.get(customerCardTypeList.size() - 1);
        assertThat(testCustomerCardType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCustomerCardType.getCode()).isEqualTo(UPDATED_CODE);
    }

    @Test
    @Transactional
    public void updateNonExistingCustomerCardType() throws Exception {
        int databaseSizeBeforeUpdate = customerCardTypeRepository.findAll().size();

        // Create the CustomerCardType

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCustomerCardTypeMockMvc.perform(put("/api/customer-card-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerCardType)))
            .andExpect(status().isCreated());

        // Validate the CustomerCardType in the database
        List<CustomerCardType> customerCardTypeList = customerCardTypeRepository.findAll();
        assertThat(customerCardTypeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCustomerCardType() throws Exception {
        // Initialize the database
        customerCardTypeService.save(customerCardType);

        int databaseSizeBeforeDelete = customerCardTypeRepository.findAll().size();

        // Get the customerCardType
        restCustomerCardTypeMockMvc.perform(delete("/api/customer-card-types/{id}", customerCardType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CustomerCardType> customerCardTypeList = customerCardTypeRepository.findAll();
        assertThat(customerCardTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomerCardType.class);
        CustomerCardType customerCardType1 = new CustomerCardType();
        customerCardType1.setId(1L);
        CustomerCardType customerCardType2 = new CustomerCardType();
        customerCardType2.setId(customerCardType1.getId());
        assertThat(customerCardType1).isEqualTo(customerCardType2);
        customerCardType2.setId(2L);
        assertThat(customerCardType1).isNotEqualTo(customerCardType2);
        customerCardType1.setId(null);
        assertThat(customerCardType1).isNotEqualTo(customerCardType2);
    }
}
