package com.pure.service.web.rest;

import com.pure.service.SimpleServiceApp;

import com.pure.service.domain.ContractType;
import com.pure.service.repository.ContractTypeRepository;
import com.pure.service.service.ContractTypeService;
import com.pure.service.web.rest.errors.ExceptionTranslator;
import com.pure.service.service.dto.ContractTypeCriteria;
import com.pure.service.service.ContractTypeQueryService;

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
 * Test class for the ContractTypeResource REST controller.
 *
 * @see ContractTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SimpleServiceApp.class)
public class ContractTypeResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    @Autowired
    private ContractTypeRepository contractTypeRepository;

    @Autowired
    private ContractTypeService contractTypeService;

    @Autowired
    private ContractTypeQueryService contractTypeQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restContractTypeMockMvc;

    private ContractType contractType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ContractTypeResource contractTypeResource = new ContractTypeResource(contractTypeService, contractTypeQueryService);
        this.restContractTypeMockMvc = MockMvcBuilders.standaloneSetup(contractTypeResource)
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
    public static ContractType createEntity(EntityManager em) {
        ContractType contractType = new ContractType()
            .name(DEFAULT_NAME)
            .code(DEFAULT_CODE);
        return contractType;
    }

    @Before
    public void initTest() {
        contractType = createEntity(em);
    }

    @Test
    @Transactional
    public void createContractType() throws Exception {
        int databaseSizeBeforeCreate = contractTypeRepository.findAll().size();

        // Create the ContractType
        restContractTypeMockMvc.perform(post("/api/contract-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contractType)))
            .andExpect(status().isCreated());

        // Validate the ContractType in the database
        List<ContractType> contractTypeList = contractTypeRepository.findAll();
        assertThat(contractTypeList).hasSize(databaseSizeBeforeCreate + 1);
        ContractType testContractType = contractTypeList.get(contractTypeList.size() - 1);
        assertThat(testContractType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testContractType.getCode()).isEqualTo(DEFAULT_CODE);
    }

    @Test
    @Transactional
    public void createContractTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = contractTypeRepository.findAll().size();

        // Create the ContractType with an existing ID
        contractType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restContractTypeMockMvc.perform(post("/api/contract-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contractType)))
            .andExpect(status().isBadRequest());

        // Validate the ContractType in the database
        List<ContractType> contractTypeList = contractTypeRepository.findAll();
        assertThat(contractTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllContractTypes() throws Exception {
        // Initialize the database
        contractTypeRepository.saveAndFlush(contractType);

        // Get all the contractTypeList
        restContractTypeMockMvc.perform(get("/api/contract-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contractType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())));
    }

    @Test
    @Transactional
    public void getContractType() throws Exception {
        // Initialize the database
        contractTypeRepository.saveAndFlush(contractType);

        // Get the contractType
        restContractTypeMockMvc.perform(get("/api/contract-types/{id}", contractType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(contractType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()));
    }

    @Test
    @Transactional
    public void getAllContractTypesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        contractTypeRepository.saveAndFlush(contractType);

        // Get all the contractTypeList where name equals to DEFAULT_NAME
        defaultContractTypeShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the contractTypeList where name equals to UPDATED_NAME
        defaultContractTypeShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllContractTypesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        contractTypeRepository.saveAndFlush(contractType);

        // Get all the contractTypeList where name in DEFAULT_NAME or UPDATED_NAME
        defaultContractTypeShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the contractTypeList where name equals to UPDATED_NAME
        defaultContractTypeShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllContractTypesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        contractTypeRepository.saveAndFlush(contractType);

        // Get all the contractTypeList where name is not null
        defaultContractTypeShouldBeFound("name.specified=true");

        // Get all the contractTypeList where name is null
        defaultContractTypeShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllContractTypesByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        contractTypeRepository.saveAndFlush(contractType);

        // Get all the contractTypeList where code equals to DEFAULT_CODE
        defaultContractTypeShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the contractTypeList where code equals to UPDATED_CODE
        defaultContractTypeShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllContractTypesByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        contractTypeRepository.saveAndFlush(contractType);

        // Get all the contractTypeList where code in DEFAULT_CODE or UPDATED_CODE
        defaultContractTypeShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the contractTypeList where code equals to UPDATED_CODE
        defaultContractTypeShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllContractTypesByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        contractTypeRepository.saveAndFlush(contractType);

        // Get all the contractTypeList where code is not null
        defaultContractTypeShouldBeFound("code.specified=true");

        // Get all the contractTypeList where code is null
        defaultContractTypeShouldNotBeFound("code.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultContractTypeShouldBeFound(String filter) throws Exception {
        restContractTypeMockMvc.perform(get("/api/contract-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contractType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultContractTypeShouldNotBeFound(String filter) throws Exception {
        restContractTypeMockMvc.perform(get("/api/contract-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingContractType() throws Exception {
        // Get the contractType
        restContractTypeMockMvc.perform(get("/api/contract-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateContractType() throws Exception {
        // Initialize the database
        contractTypeService.save(contractType);

        int databaseSizeBeforeUpdate = contractTypeRepository.findAll().size();

        // Update the contractType
        ContractType updatedContractType = contractTypeRepository.findOne(contractType.getId());
        updatedContractType
            .name(UPDATED_NAME)
            .code(UPDATED_CODE);

        restContractTypeMockMvc.perform(put("/api/contract-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedContractType)))
            .andExpect(status().isOk());

        // Validate the ContractType in the database
        List<ContractType> contractTypeList = contractTypeRepository.findAll();
        assertThat(contractTypeList).hasSize(databaseSizeBeforeUpdate);
        ContractType testContractType = contractTypeList.get(contractTypeList.size() - 1);
        assertThat(testContractType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testContractType.getCode()).isEqualTo(UPDATED_CODE);
    }

    @Test
    @Transactional
    public void updateNonExistingContractType() throws Exception {
        int databaseSizeBeforeUpdate = contractTypeRepository.findAll().size();

        // Create the ContractType

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restContractTypeMockMvc.perform(put("/api/contract-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contractType)))
            .andExpect(status().isCreated());

        // Validate the ContractType in the database
        List<ContractType> contractTypeList = contractTypeRepository.findAll();
        assertThat(contractTypeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteContractType() throws Exception {
        // Initialize the database
        contractTypeService.save(contractType);

        int databaseSizeBeforeDelete = contractTypeRepository.findAll().size();

        // Get the contractType
        restContractTypeMockMvc.perform(delete("/api/contract-types/{id}", contractType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ContractType> contractTypeList = contractTypeRepository.findAll();
        assertThat(contractTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContractType.class);
        ContractType contractType1 = new ContractType();
        contractType1.setId(1L);
        ContractType contractType2 = new ContractType();
        contractType2.setId(contractType1.getId());
        assertThat(contractType1).isEqualTo(contractType2);
        contractType2.setId(2L);
        assertThat(contractType1).isNotEqualTo(contractType2);
        contractType1.setId(null);
        assertThat(contractType1).isNotEqualTo(contractType2);
    }
}
