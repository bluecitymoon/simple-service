package com.pure.service.web.rest;

import com.pure.service.SimpleServiceApp;

import com.pure.service.domain.ContractPackageType;
import com.pure.service.repository.ContractPackageTypeRepository;
import com.pure.service.service.ContractPackageTypeService;
import com.pure.service.web.rest.errors.ExceptionTranslator;
import com.pure.service.service.dto.ContractPackageTypeCriteria;
import com.pure.service.service.ContractPackageTypeQueryService;

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
 * Test class for the ContractPackageTypeResource REST controller.
 *
 * @see ContractPackageTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SimpleServiceApp.class)
public class ContractPackageTypeResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTS = "BBBBBBBBBB";

    @Autowired
    private ContractPackageTypeRepository contractPackageTypeRepository;

    @Autowired
    private ContractPackageTypeService contractPackageTypeService;

    @Autowired
    private ContractPackageTypeQueryService contractPackageTypeQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restContractPackageTypeMockMvc;

    private ContractPackageType contractPackageType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ContractPackageTypeResource contractPackageTypeResource = new ContractPackageTypeResource(contractPackageTypeService, contractPackageTypeQueryService);
        this.restContractPackageTypeMockMvc = MockMvcBuilders.standaloneSetup(contractPackageTypeResource)
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
    public static ContractPackageType createEntity(EntityManager em) {
        ContractPackageType contractPackageType = new ContractPackageType()
            .name(DEFAULT_NAME)
            .code(DEFAULT_CODE)
            .comments(DEFAULT_COMMENTS);
        return contractPackageType;
    }

    @Before
    public void initTest() {
        contractPackageType = createEntity(em);
    }

    @Test
    @Transactional
    public void createContractPackageType() throws Exception {
        int databaseSizeBeforeCreate = contractPackageTypeRepository.findAll().size();

        // Create the ContractPackageType
        restContractPackageTypeMockMvc.perform(post("/api/contract-package-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contractPackageType)))
            .andExpect(status().isCreated());

        // Validate the ContractPackageType in the database
        List<ContractPackageType> contractPackageTypeList = contractPackageTypeRepository.findAll();
        assertThat(contractPackageTypeList).hasSize(databaseSizeBeforeCreate + 1);
        ContractPackageType testContractPackageType = contractPackageTypeList.get(contractPackageTypeList.size() - 1);
        assertThat(testContractPackageType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testContractPackageType.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testContractPackageType.getComments()).isEqualTo(DEFAULT_COMMENTS);
    }

    @Test
    @Transactional
    public void createContractPackageTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = contractPackageTypeRepository.findAll().size();

        // Create the ContractPackageType with an existing ID
        contractPackageType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restContractPackageTypeMockMvc.perform(post("/api/contract-package-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contractPackageType)))
            .andExpect(status().isBadRequest());

        // Validate the ContractPackageType in the database
        List<ContractPackageType> contractPackageTypeList = contractPackageTypeRepository.findAll();
        assertThat(contractPackageTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllContractPackageTypes() throws Exception {
        // Initialize the database
        contractPackageTypeRepository.saveAndFlush(contractPackageType);

        // Get all the contractPackageTypeList
        restContractPackageTypeMockMvc.perform(get("/api/contract-package-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contractPackageType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS)));
    }

    @Test
    @Transactional
    public void getContractPackageType() throws Exception {
        // Initialize the database
        contractPackageTypeRepository.saveAndFlush(contractPackageType);

        // Get the contractPackageType
        restContractPackageTypeMockMvc.perform(get("/api/contract-package-types/{id}", contractPackageType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(contractPackageType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS));
    }

    @Test
    @Transactional
    public void getAllContractPackageTypesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        contractPackageTypeRepository.saveAndFlush(contractPackageType);

        // Get all the contractPackageTypeList where name equals to DEFAULT_NAME
        defaultContractPackageTypeShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the contractPackageTypeList where name equals to UPDATED_NAME
        defaultContractPackageTypeShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllContractPackageTypesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        contractPackageTypeRepository.saveAndFlush(contractPackageType);

        // Get all the contractPackageTypeList where name in DEFAULT_NAME or UPDATED_NAME
        defaultContractPackageTypeShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the contractPackageTypeList where name equals to UPDATED_NAME
        defaultContractPackageTypeShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllContractPackageTypesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        contractPackageTypeRepository.saveAndFlush(contractPackageType);

        // Get all the contractPackageTypeList where name is not null
        defaultContractPackageTypeShouldBeFound("name.specified=true");

        // Get all the contractPackageTypeList where name is null
        defaultContractPackageTypeShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllContractPackageTypesByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        contractPackageTypeRepository.saveAndFlush(contractPackageType);

        // Get all the contractPackageTypeList where code equals to DEFAULT_CODE
        defaultContractPackageTypeShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the contractPackageTypeList where code equals to UPDATED_CODE
        defaultContractPackageTypeShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllContractPackageTypesByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        contractPackageTypeRepository.saveAndFlush(contractPackageType);

        // Get all the contractPackageTypeList where code in DEFAULT_CODE or UPDATED_CODE
        defaultContractPackageTypeShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the contractPackageTypeList where code equals to UPDATED_CODE
        defaultContractPackageTypeShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllContractPackageTypesByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        contractPackageTypeRepository.saveAndFlush(contractPackageType);

        // Get all the contractPackageTypeList where code is not null
        defaultContractPackageTypeShouldBeFound("code.specified=true");

        // Get all the contractPackageTypeList where code is null
        defaultContractPackageTypeShouldNotBeFound("code.specified=false");
    }

    @Test
    @Transactional
    public void getAllContractPackageTypesByCommentsIsEqualToSomething() throws Exception {
        // Initialize the database
        contractPackageTypeRepository.saveAndFlush(contractPackageType);

        // Get all the contractPackageTypeList where comments equals to DEFAULT_COMMENTS
        defaultContractPackageTypeShouldBeFound("comments.equals=" + DEFAULT_COMMENTS);

        // Get all the contractPackageTypeList where comments equals to UPDATED_COMMENTS
        defaultContractPackageTypeShouldNotBeFound("comments.equals=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllContractPackageTypesByCommentsIsInShouldWork() throws Exception {
        // Initialize the database
        contractPackageTypeRepository.saveAndFlush(contractPackageType);

        // Get all the contractPackageTypeList where comments in DEFAULT_COMMENTS or UPDATED_COMMENTS
        defaultContractPackageTypeShouldBeFound("comments.in=" + DEFAULT_COMMENTS + "," + UPDATED_COMMENTS);

        // Get all the contractPackageTypeList where comments equals to UPDATED_COMMENTS
        defaultContractPackageTypeShouldNotBeFound("comments.in=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllContractPackageTypesByCommentsIsNullOrNotNull() throws Exception {
        // Initialize the database
        contractPackageTypeRepository.saveAndFlush(contractPackageType);

        // Get all the contractPackageTypeList where comments is not null
        defaultContractPackageTypeShouldBeFound("comments.specified=true");

        // Get all the contractPackageTypeList where comments is null
        defaultContractPackageTypeShouldNotBeFound("comments.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultContractPackageTypeShouldBeFound(String filter) throws Exception {
        restContractPackageTypeMockMvc.perform(get("/api/contract-package-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contractPackageType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS)));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultContractPackageTypeShouldNotBeFound(String filter) throws Exception {
        restContractPackageTypeMockMvc.perform(get("/api/contract-package-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingContractPackageType() throws Exception {
        // Get the contractPackageType
        restContractPackageTypeMockMvc.perform(get("/api/contract-package-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateContractPackageType() throws Exception {
        // Initialize the database
        contractPackageTypeService.save(contractPackageType);

        int databaseSizeBeforeUpdate = contractPackageTypeRepository.findAll().size();

        // Update the contractPackageType
        ContractPackageType updatedContractPackageType = contractPackageTypeRepository.findOne(contractPackageType.getId());
        updatedContractPackageType
            .name(UPDATED_NAME)
            .code(UPDATED_CODE)
            .comments(UPDATED_COMMENTS);

        restContractPackageTypeMockMvc.perform(put("/api/contract-package-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedContractPackageType)))
            .andExpect(status().isOk());

        // Validate the ContractPackageType in the database
        List<ContractPackageType> contractPackageTypeList = contractPackageTypeRepository.findAll();
        assertThat(contractPackageTypeList).hasSize(databaseSizeBeforeUpdate);
        ContractPackageType testContractPackageType = contractPackageTypeList.get(contractPackageTypeList.size() - 1);
        assertThat(testContractPackageType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testContractPackageType.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testContractPackageType.getComments()).isEqualTo(UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    public void updateNonExistingContractPackageType() throws Exception {
        int databaseSizeBeforeUpdate = contractPackageTypeRepository.findAll().size();

        // Create the ContractPackageType

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restContractPackageTypeMockMvc.perform(put("/api/contract-package-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contractPackageType)))
            .andExpect(status().isCreated());

        // Validate the ContractPackageType in the database
        List<ContractPackageType> contractPackageTypeList = contractPackageTypeRepository.findAll();
        assertThat(contractPackageTypeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteContractPackageType() throws Exception {
        // Initialize the database
        contractPackageTypeService.save(contractPackageType);

        int databaseSizeBeforeDelete = contractPackageTypeRepository.findAll().size();

        // Get the contractPackageType
        restContractPackageTypeMockMvc.perform(delete("/api/contract-package-types/{id}", contractPackageType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ContractPackageType> contractPackageTypeList = contractPackageTypeRepository.findAll();
        assertThat(contractPackageTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContractPackageType.class);
        ContractPackageType contractPackageType1 = new ContractPackageType();
        contractPackageType1.setId(1L);
        ContractPackageType contractPackageType2 = new ContractPackageType();
        contractPackageType2.setId(contractPackageType1.getId());
        assertThat(contractPackageType1).isEqualTo(contractPackageType2);
        contractPackageType2.setId(2L);
        assertThat(contractPackageType1).isNotEqualTo(contractPackageType2);
        contractPackageType1.setId(null);
        assertThat(contractPackageType1).isNotEqualTo(contractPackageType2);
    }
}
