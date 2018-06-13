package com.pure.service.web.rest;

import com.pure.service.SimpleServiceApp;

import com.pure.service.domain.PaymentType;
import com.pure.service.repository.PaymentTypeRepository;
import com.pure.service.service.PaymentTypeService;
import com.pure.service.web.rest.errors.ExceptionTranslator;
import com.pure.service.service.dto.PaymentTypeCriteria;
import com.pure.service.service.PaymentTypeQueryService;

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
 * Test class for the PaymentTypeResource REST controller.
 *
 * @see PaymentTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SimpleServiceApp.class)
public class PaymentTypeResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    @Autowired
    private PaymentTypeRepository paymentTypeRepository;

    @Autowired
    private PaymentTypeService paymentTypeService;

    @Autowired
    private PaymentTypeQueryService paymentTypeQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPaymentTypeMockMvc;

    private PaymentType paymentType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PaymentTypeResource paymentTypeResource = new PaymentTypeResource(paymentTypeService, paymentTypeQueryService);
        this.restPaymentTypeMockMvc = MockMvcBuilders.standaloneSetup(paymentTypeResource)
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
    public static PaymentType createEntity(EntityManager em) {
        PaymentType paymentType = new PaymentType()
            .name(DEFAULT_NAME)
            .code(DEFAULT_CODE);
        return paymentType;
    }

    @Before
    public void initTest() {
        paymentType = createEntity(em);
    }

    @Test
    @Transactional
    public void createPaymentType() throws Exception {
        int databaseSizeBeforeCreate = paymentTypeRepository.findAll().size();

        // Create the PaymentType
        restPaymentTypeMockMvc.perform(post("/api/payment-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentType)))
            .andExpect(status().isCreated());

        // Validate the PaymentType in the database
        List<PaymentType> paymentTypeList = paymentTypeRepository.findAll();
        assertThat(paymentTypeList).hasSize(databaseSizeBeforeCreate + 1);
        PaymentType testPaymentType = paymentTypeList.get(paymentTypeList.size() - 1);
        assertThat(testPaymentType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPaymentType.getCode()).isEqualTo(DEFAULT_CODE);
    }

    @Test
    @Transactional
    public void createPaymentTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = paymentTypeRepository.findAll().size();

        // Create the PaymentType with an existing ID
        paymentType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPaymentTypeMockMvc.perform(post("/api/payment-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentType)))
            .andExpect(status().isBadRequest());

        // Validate the PaymentType in the database
        List<PaymentType> paymentTypeList = paymentTypeRepository.findAll();
        assertThat(paymentTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPaymentTypes() throws Exception {
        // Initialize the database
        paymentTypeRepository.saveAndFlush(paymentType);

        // Get all the paymentTypeList
        restPaymentTypeMockMvc.perform(get("/api/payment-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paymentType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)));
    }

    @Test
    @Transactional
    public void getPaymentType() throws Exception {
        // Initialize the database
        paymentTypeRepository.saveAndFlush(paymentType);

        // Get the paymentType
        restPaymentTypeMockMvc.perform(get("/api/payment-types/{id}", paymentType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(paymentType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE));
    }

    @Test
    @Transactional
    public void getAllPaymentTypesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentTypeRepository.saveAndFlush(paymentType);

        // Get all the paymentTypeList where name equals to DEFAULT_NAME
        defaultPaymentTypeShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the paymentTypeList where name equals to UPDATED_NAME
        defaultPaymentTypeShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPaymentTypesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        paymentTypeRepository.saveAndFlush(paymentType);

        // Get all the paymentTypeList where name in DEFAULT_NAME or UPDATED_NAME
        defaultPaymentTypeShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the paymentTypeList where name equals to UPDATED_NAME
        defaultPaymentTypeShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPaymentTypesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentTypeRepository.saveAndFlush(paymentType);

        // Get all the paymentTypeList where name is not null
        defaultPaymentTypeShouldBeFound("name.specified=true");

        // Get all the paymentTypeList where name is null
        defaultPaymentTypeShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllPaymentTypesByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentTypeRepository.saveAndFlush(paymentType);

        // Get all the paymentTypeList where code equals to DEFAULT_CODE
        defaultPaymentTypeShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the paymentTypeList where code equals to UPDATED_CODE
        defaultPaymentTypeShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllPaymentTypesByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        paymentTypeRepository.saveAndFlush(paymentType);

        // Get all the paymentTypeList where code in DEFAULT_CODE or UPDATED_CODE
        defaultPaymentTypeShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the paymentTypeList where code equals to UPDATED_CODE
        defaultPaymentTypeShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllPaymentTypesByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentTypeRepository.saveAndFlush(paymentType);

        // Get all the paymentTypeList where code is not null
        defaultPaymentTypeShouldBeFound("code.specified=true");

        // Get all the paymentTypeList where code is null
        defaultPaymentTypeShouldNotBeFound("code.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultPaymentTypeShouldBeFound(String filter) throws Exception {
        restPaymentTypeMockMvc.perform(get("/api/payment-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paymentType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultPaymentTypeShouldNotBeFound(String filter) throws Exception {
        restPaymentTypeMockMvc.perform(get("/api/payment-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingPaymentType() throws Exception {
        // Get the paymentType
        restPaymentTypeMockMvc.perform(get("/api/payment-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePaymentType() throws Exception {
        // Initialize the database
        paymentTypeService.save(paymentType);

        int databaseSizeBeforeUpdate = paymentTypeRepository.findAll().size();

        // Update the paymentType
        PaymentType updatedPaymentType = paymentTypeRepository.findOne(paymentType.getId());
        updatedPaymentType
            .name(UPDATED_NAME)
            .code(UPDATED_CODE);

        restPaymentTypeMockMvc.perform(put("/api/payment-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPaymentType)))
            .andExpect(status().isOk());

        // Validate the PaymentType in the database
        List<PaymentType> paymentTypeList = paymentTypeRepository.findAll();
        assertThat(paymentTypeList).hasSize(databaseSizeBeforeUpdate);
        PaymentType testPaymentType = paymentTypeList.get(paymentTypeList.size() - 1);
        assertThat(testPaymentType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPaymentType.getCode()).isEqualTo(UPDATED_CODE);
    }

    @Test
    @Transactional
    public void updateNonExistingPaymentType() throws Exception {
        int databaseSizeBeforeUpdate = paymentTypeRepository.findAll().size();

        // Create the PaymentType

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPaymentTypeMockMvc.perform(put("/api/payment-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentType)))
            .andExpect(status().isCreated());

        // Validate the PaymentType in the database
        List<PaymentType> paymentTypeList = paymentTypeRepository.findAll();
        assertThat(paymentTypeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePaymentType() throws Exception {
        // Initialize the database
        paymentTypeService.save(paymentType);

        int databaseSizeBeforeDelete = paymentTypeRepository.findAll().size();

        // Get the paymentType
        restPaymentTypeMockMvc.perform(delete("/api/payment-types/{id}", paymentType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PaymentType> paymentTypeList = paymentTypeRepository.findAll();
        assertThat(paymentTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PaymentType.class);
        PaymentType paymentType1 = new PaymentType();
        paymentType1.setId(1L);
        PaymentType paymentType2 = new PaymentType();
        paymentType2.setId(paymentType1.getId());
        assertThat(paymentType1).isEqualTo(paymentType2);
        paymentType2.setId(2L);
        assertThat(paymentType1).isNotEqualTo(paymentType2);
        paymentType1.setId(null);
        assertThat(paymentType1).isNotEqualTo(paymentType2);
    }
}
