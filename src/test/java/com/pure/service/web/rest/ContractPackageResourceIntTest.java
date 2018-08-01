package com.pure.service.web.rest;

import com.pure.service.SimpleServiceApp;

import com.pure.service.domain.ContractPackage;
import com.pure.service.repository.ContractPackageRepository;
import com.pure.service.service.ContractPackageService;
import com.pure.service.web.rest.errors.ExceptionTranslator;
import com.pure.service.service.dto.ContractPackageCriteria;
import com.pure.service.service.ContractPackageQueryService;

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
 * Test class for the ContractPackageResource REST controller.
 *
 * @see ContractPackageResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SimpleServiceApp.class)
public class ContractPackageResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTS = "BBBBBBBBBB";

    @Autowired
    private ContractPackageRepository contractPackageRepository;

    @Autowired
    private ContractPackageService contractPackageService;

    @Autowired
    private ContractPackageQueryService contractPackageQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restContractPackageMockMvc;

    private ContractPackage contractPackage;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ContractPackageResource contractPackageResource = new ContractPackageResource(contractPackageService, contractPackageQueryService);
        this.restContractPackageMockMvc = MockMvcBuilders.standaloneSetup(contractPackageResource)
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
    public static ContractPackage createEntity(EntityManager em) {
        ContractPackage contractPackage = new ContractPackage()
            .name(DEFAULT_NAME)
            .code(DEFAULT_CODE)
            .comments(DEFAULT_COMMENTS);
        return contractPackage;
    }

    @Before
    public void initTest() {
        contractPackage = createEntity(em);
    }

    @Test
    @Transactional
    public void createContractPackage() throws Exception {
        int databaseSizeBeforeCreate = contractPackageRepository.findAll().size();

        // Create the ContractPackage
        restContractPackageMockMvc.perform(post("/api/contract-packages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contractPackage)))
            .andExpect(status().isCreated());

        // Validate the ContractPackage in the database
        List<ContractPackage> contractPackageList = contractPackageRepository.findAll();
        assertThat(contractPackageList).hasSize(databaseSizeBeforeCreate + 1);
        ContractPackage testContractPackage = contractPackageList.get(contractPackageList.size() - 1);
        assertThat(testContractPackage.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testContractPackage.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testContractPackage.getComments()).isEqualTo(DEFAULT_COMMENTS);
    }

    @Test
    @Transactional
    public void createContractPackageWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = contractPackageRepository.findAll().size();

        // Create the ContractPackage with an existing ID
        contractPackage.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restContractPackageMockMvc.perform(post("/api/contract-packages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contractPackage)))
            .andExpect(status().isBadRequest());

        // Validate the ContractPackage in the database
        List<ContractPackage> contractPackageList = contractPackageRepository.findAll();
        assertThat(contractPackageList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllContractPackages() throws Exception {
        // Initialize the database
        contractPackageRepository.saveAndFlush(contractPackage);

        // Get all the contractPackageList
        restContractPackageMockMvc.perform(get("/api/contract-packages?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contractPackage.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS.toString())));
    }

    @Test
    @Transactional
    public void getContractPackage() throws Exception {
        // Initialize the database
        contractPackageRepository.saveAndFlush(contractPackage);

        // Get the contractPackage
        restContractPackageMockMvc.perform(get("/api/contract-packages/{id}", contractPackage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(contractPackage.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS.toString()));
    }

    @Test
    @Transactional
    public void getAllContractPackagesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        contractPackageRepository.saveAndFlush(contractPackage);

        // Get all the contractPackageList where name equals to DEFAULT_NAME
        defaultContractPackageShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the contractPackageList where name equals to UPDATED_NAME
        defaultContractPackageShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllContractPackagesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        contractPackageRepository.saveAndFlush(contractPackage);

        // Get all the contractPackageList where name in DEFAULT_NAME or UPDATED_NAME
        defaultContractPackageShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the contractPackageList where name equals to UPDATED_NAME
        defaultContractPackageShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllContractPackagesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        contractPackageRepository.saveAndFlush(contractPackage);

        // Get all the contractPackageList where name is not null
        defaultContractPackageShouldBeFound("name.specified=true");

        // Get all the contractPackageList where name is null
        defaultContractPackageShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllContractPackagesByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        contractPackageRepository.saveAndFlush(contractPackage);

        // Get all the contractPackageList where code equals to DEFAULT_CODE
        defaultContractPackageShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the contractPackageList where code equals to UPDATED_CODE
        defaultContractPackageShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllContractPackagesByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        contractPackageRepository.saveAndFlush(contractPackage);

        // Get all the contractPackageList where code in DEFAULT_CODE or UPDATED_CODE
        defaultContractPackageShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the contractPackageList where code equals to UPDATED_CODE
        defaultContractPackageShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllContractPackagesByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        contractPackageRepository.saveAndFlush(contractPackage);

        // Get all the contractPackageList where code is not null
        defaultContractPackageShouldBeFound("code.specified=true");

        // Get all the contractPackageList where code is null
        defaultContractPackageShouldNotBeFound("code.specified=false");
    }

    @Test
    @Transactional
    public void getAllContractPackagesByCommentsIsEqualToSomething() throws Exception {
        // Initialize the database
        contractPackageRepository.saveAndFlush(contractPackage);

        // Get all the contractPackageList where comments equals to DEFAULT_COMMENTS
        defaultContractPackageShouldBeFound("comments.equals=" + DEFAULT_COMMENTS);

        // Get all the contractPackageList where comments equals to UPDATED_COMMENTS
        defaultContractPackageShouldNotBeFound("comments.equals=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllContractPackagesByCommentsIsInShouldWork() throws Exception {
        // Initialize the database
        contractPackageRepository.saveAndFlush(contractPackage);

        // Get all the contractPackageList where comments in DEFAULT_COMMENTS or UPDATED_COMMENTS
        defaultContractPackageShouldBeFound("comments.in=" + DEFAULT_COMMENTS + "," + UPDATED_COMMENTS);

        // Get all the contractPackageList where comments equals to UPDATED_COMMENTS
        defaultContractPackageShouldNotBeFound("comments.in=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllContractPackagesByCommentsIsNullOrNotNull() throws Exception {
        // Initialize the database
        contractPackageRepository.saveAndFlush(contractPackage);

        // Get all the contractPackageList where comments is not null
        defaultContractPackageShouldBeFound("comments.specified=true");

        // Get all the contractPackageList where comments is null
        defaultContractPackageShouldNotBeFound("comments.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultContractPackageShouldBeFound(String filter) throws Exception {
        restContractPackageMockMvc.perform(get("/api/contract-packages?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contractPackage.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultContractPackageShouldNotBeFound(String filter) throws Exception {
        restContractPackageMockMvc.perform(get("/api/contract-packages?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingContractPackage() throws Exception {
        // Get the contractPackage
        restContractPackageMockMvc.perform(get("/api/contract-packages/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateContractPackage() throws Exception {
        // Initialize the database
        contractPackageService.save(contractPackage);

        int databaseSizeBeforeUpdate = contractPackageRepository.findAll().size();

        // Update the contractPackage
        ContractPackage updatedContractPackage = contractPackageRepository.findOne(contractPackage.getId());
        updatedContractPackage
            .name(UPDATED_NAME)
            .code(UPDATED_CODE)
            .comments(UPDATED_COMMENTS);

        restContractPackageMockMvc.perform(put("/api/contract-packages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedContractPackage)))
            .andExpect(status().isOk());

        // Validate the ContractPackage in the database
        List<ContractPackage> contractPackageList = contractPackageRepository.findAll();
        assertThat(contractPackageList).hasSize(databaseSizeBeforeUpdate);
        ContractPackage testContractPackage = contractPackageList.get(contractPackageList.size() - 1);
        assertThat(testContractPackage.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testContractPackage.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testContractPackage.getComments()).isEqualTo(UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    public void updateNonExistingContractPackage() throws Exception {
        int databaseSizeBeforeUpdate = contractPackageRepository.findAll().size();

        // Create the ContractPackage

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restContractPackageMockMvc.perform(put("/api/contract-packages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contractPackage)))
            .andExpect(status().isCreated());

        // Validate the ContractPackage in the database
        List<ContractPackage> contractPackageList = contractPackageRepository.findAll();
        assertThat(contractPackageList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteContractPackage() throws Exception {
        // Initialize the database
        contractPackageService.save(contractPackage);

        int databaseSizeBeforeDelete = contractPackageRepository.findAll().size();

        // Get the contractPackage
        restContractPackageMockMvc.perform(delete("/api/contract-packages/{id}", contractPackage.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ContractPackage> contractPackageList = contractPackageRepository.findAll();
        assertThat(contractPackageList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContractPackage.class);
        ContractPackage contractPackage1 = new ContractPackage();
        contractPackage1.setId(1L);
        ContractPackage contractPackage2 = new ContractPackage();
        contractPackage2.setId(contractPackage1.getId());
        assertThat(contractPackage1).isEqualTo(contractPackage2);
        contractPackage2.setId(2L);
        assertThat(contractPackage1).isNotEqualTo(contractPackage2);
        contractPackage1.setId(null);
        assertThat(contractPackage1).isNotEqualTo(contractPackage2);
    }
}
