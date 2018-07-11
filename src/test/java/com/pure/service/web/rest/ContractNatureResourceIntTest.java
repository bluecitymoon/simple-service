package com.pure.service.web.rest;

import com.pure.service.SimpleServiceApp;

import com.pure.service.domain.ContractNature;
import com.pure.service.repository.ContractNatureRepository;
import com.pure.service.service.ContractNatureService;
import com.pure.service.web.rest.errors.ExceptionTranslator;
import com.pure.service.service.dto.ContractNatureCriteria;
import com.pure.service.service.ContractNatureQueryService;

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
 * Test class for the ContractNatureResource REST controller.
 *
 * @see ContractNatureResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SimpleServiceApp.class)
public class ContractNatureResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    @Autowired
    private ContractNatureRepository contractNatureRepository;

    @Autowired
    private ContractNatureService contractNatureService;

    @Autowired
    private ContractNatureQueryService contractNatureQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restContractNatureMockMvc;

    private ContractNature contractNature;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ContractNatureResource contractNatureResource = new ContractNatureResource(contractNatureService, contractNatureQueryService);
        this.restContractNatureMockMvc = MockMvcBuilders.standaloneSetup(contractNatureResource)
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
    public static ContractNature createEntity(EntityManager em) {
        ContractNature contractNature = new ContractNature()
            .name(DEFAULT_NAME)
            .code(DEFAULT_CODE);
        return contractNature;
    }

    @Before
    public void initTest() {
        contractNature = createEntity(em);
    }

    @Test
    @Transactional
    public void createContractNature() throws Exception {
        int databaseSizeBeforeCreate = contractNatureRepository.findAll().size();

        // Create the ContractNature
        restContractNatureMockMvc.perform(post("/api/contract-natures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contractNature)))
            .andExpect(status().isCreated());

        // Validate the ContractNature in the database
        List<ContractNature> contractNatureList = contractNatureRepository.findAll();
        assertThat(contractNatureList).hasSize(databaseSizeBeforeCreate + 1);
        ContractNature testContractNature = contractNatureList.get(contractNatureList.size() - 1);
        assertThat(testContractNature.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testContractNature.getCode()).isEqualTo(DEFAULT_CODE);
    }

    @Test
    @Transactional
    public void createContractNatureWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = contractNatureRepository.findAll().size();

        // Create the ContractNature with an existing ID
        contractNature.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restContractNatureMockMvc.perform(post("/api/contract-natures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contractNature)))
            .andExpect(status().isBadRequest());

        // Validate the ContractNature in the database
        List<ContractNature> contractNatureList = contractNatureRepository.findAll();
        assertThat(contractNatureList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllContractNatures() throws Exception {
        // Initialize the database
        contractNatureRepository.saveAndFlush(contractNature);

        // Get all the contractNatureList
        restContractNatureMockMvc.perform(get("/api/contract-natures?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contractNature.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)));
    }

    @Test
    @Transactional
    public void getContractNature() throws Exception {
        // Initialize the database
        contractNatureRepository.saveAndFlush(contractNature);

        // Get the contractNature
        restContractNatureMockMvc.perform(get("/api/contract-natures/{id}", contractNature.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(contractNature.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE));
    }

    @Test
    @Transactional
    public void getAllContractNaturesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        contractNatureRepository.saveAndFlush(contractNature);

        // Get all the contractNatureList where name equals to DEFAULT_NAME
        defaultContractNatureShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the contractNatureList where name equals to UPDATED_NAME
        defaultContractNatureShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllContractNaturesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        contractNatureRepository.saveAndFlush(contractNature);

        // Get all the contractNatureList where name in DEFAULT_NAME or UPDATED_NAME
        defaultContractNatureShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the contractNatureList where name equals to UPDATED_NAME
        defaultContractNatureShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllContractNaturesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        contractNatureRepository.saveAndFlush(contractNature);

        // Get all the contractNatureList where name is not null
        defaultContractNatureShouldBeFound("name.specified=true");

        // Get all the contractNatureList where name is null
        defaultContractNatureShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllContractNaturesByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        contractNatureRepository.saveAndFlush(contractNature);

        // Get all the contractNatureList where code equals to DEFAULT_CODE
        defaultContractNatureShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the contractNatureList where code equals to UPDATED_CODE
        defaultContractNatureShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllContractNaturesByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        contractNatureRepository.saveAndFlush(contractNature);

        // Get all the contractNatureList where code in DEFAULT_CODE or UPDATED_CODE
        defaultContractNatureShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the contractNatureList where code equals to UPDATED_CODE
        defaultContractNatureShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllContractNaturesByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        contractNatureRepository.saveAndFlush(contractNature);

        // Get all the contractNatureList where code is not null
        defaultContractNatureShouldBeFound("code.specified=true");

        // Get all the contractNatureList where code is null
        defaultContractNatureShouldNotBeFound("code.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultContractNatureShouldBeFound(String filter) throws Exception {
        restContractNatureMockMvc.perform(get("/api/contract-natures?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contractNature.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultContractNatureShouldNotBeFound(String filter) throws Exception {
        restContractNatureMockMvc.perform(get("/api/contract-natures?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingContractNature() throws Exception {
        // Get the contractNature
        restContractNatureMockMvc.perform(get("/api/contract-natures/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateContractNature() throws Exception {
        // Initialize the database
        contractNatureService.save(contractNature);

        int databaseSizeBeforeUpdate = contractNatureRepository.findAll().size();

        // Update the contractNature
        ContractNature updatedContractNature = contractNatureRepository.findOne(contractNature.getId());
        updatedContractNature
            .name(UPDATED_NAME)
            .code(UPDATED_CODE);

        restContractNatureMockMvc.perform(put("/api/contract-natures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedContractNature)))
            .andExpect(status().isOk());

        // Validate the ContractNature in the database
        List<ContractNature> contractNatureList = contractNatureRepository.findAll();
        assertThat(contractNatureList).hasSize(databaseSizeBeforeUpdate);
        ContractNature testContractNature = contractNatureList.get(contractNatureList.size() - 1);
        assertThat(testContractNature.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testContractNature.getCode()).isEqualTo(UPDATED_CODE);
    }

    @Test
    @Transactional
    public void updateNonExistingContractNature() throws Exception {
        int databaseSizeBeforeUpdate = contractNatureRepository.findAll().size();

        // Create the ContractNature

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restContractNatureMockMvc.perform(put("/api/contract-natures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contractNature)))
            .andExpect(status().isCreated());

        // Validate the ContractNature in the database
        List<ContractNature> contractNatureList = contractNatureRepository.findAll();
        assertThat(contractNatureList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteContractNature() throws Exception {
        // Initialize the database
        contractNatureService.save(contractNature);

        int databaseSizeBeforeDelete = contractNatureRepository.findAll().size();

        // Get the contractNature
        restContractNatureMockMvc.perform(delete("/api/contract-natures/{id}", contractNature.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ContractNature> contractNatureList = contractNatureRepository.findAll();
        assertThat(contractNatureList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContractNature.class);
        ContractNature contractNature1 = new ContractNature();
        contractNature1.setId(1L);
        ContractNature contractNature2 = new ContractNature();
        contractNature2.setId(contractNature1.getId());
        assertThat(contractNature1).isEqualTo(contractNature2);
        contractNature2.setId(2L);
        assertThat(contractNature1).isNotEqualTo(contractNature2);
        contractNature1.setId(null);
        assertThat(contractNature1).isNotEqualTo(contractNature2);
    }
}
