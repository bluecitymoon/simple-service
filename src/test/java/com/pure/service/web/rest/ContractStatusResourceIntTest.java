package com.pure.service.web.rest;

import com.pure.service.SimpleServiceApp;

import com.pure.service.domain.ContractStatus;
import com.pure.service.repository.ContractStatusRepository;
import com.pure.service.service.ContractStatusService;
import com.pure.service.web.rest.errors.ExceptionTranslator;
import com.pure.service.service.dto.ContractStatusCriteria;
import com.pure.service.service.ContractStatusQueryService;

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
 * Test class for the ContractStatusResource REST controller.
 *
 * @see ContractStatusResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SimpleServiceApp.class)
public class ContractStatusResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    @Autowired
    private ContractStatusRepository contractStatusRepository;

    @Autowired
    private ContractStatusService contractStatusService;

    @Autowired
    private ContractStatusQueryService contractStatusQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restContractStatusMockMvc;

    private ContractStatus contractStatus;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ContractStatusResource contractStatusResource = new ContractStatusResource(contractStatusService, contractStatusQueryService);
        this.restContractStatusMockMvc = MockMvcBuilders.standaloneSetup(contractStatusResource)
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
    public static ContractStatus createEntity(EntityManager em) {
        ContractStatus contractStatus = new ContractStatus()
            .name(DEFAULT_NAME)
            .code(DEFAULT_CODE);
        return contractStatus;
    }

    @Before
    public void initTest() {
        contractStatus = createEntity(em);
    }

    @Test
    @Transactional
    public void createContractStatus() throws Exception {
        int databaseSizeBeforeCreate = contractStatusRepository.findAll().size();

        // Create the ContractStatus
        restContractStatusMockMvc.perform(post("/api/contract-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contractStatus)))
            .andExpect(status().isCreated());

        // Validate the ContractStatus in the database
        List<ContractStatus> contractStatusList = contractStatusRepository.findAll();
        assertThat(contractStatusList).hasSize(databaseSizeBeforeCreate + 1);
        ContractStatus testContractStatus = contractStatusList.get(contractStatusList.size() - 1);
        assertThat(testContractStatus.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testContractStatus.getCode()).isEqualTo(DEFAULT_CODE);
    }

    @Test
    @Transactional
    public void createContractStatusWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = contractStatusRepository.findAll().size();

        // Create the ContractStatus with an existing ID
        contractStatus.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restContractStatusMockMvc.perform(post("/api/contract-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contractStatus)))
            .andExpect(status().isBadRequest());

        // Validate the ContractStatus in the database
        List<ContractStatus> contractStatusList = contractStatusRepository.findAll();
        assertThat(contractStatusList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllContractStatuses() throws Exception {
        // Initialize the database
        contractStatusRepository.saveAndFlush(contractStatus);

        // Get all the contractStatusList
        restContractStatusMockMvc.perform(get("/api/contract-statuses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contractStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)));
    }

    @Test
    @Transactional
    public void getContractStatus() throws Exception {
        // Initialize the database
        contractStatusRepository.saveAndFlush(contractStatus);

        // Get the contractStatus
        restContractStatusMockMvc.perform(get("/api/contract-statuses/{id}", contractStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(contractStatus.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE));
    }

    @Test
    @Transactional
    public void getAllContractStatusesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        contractStatusRepository.saveAndFlush(contractStatus);

        // Get all the contractStatusList where name equals to DEFAULT_NAME
        defaultContractStatusShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the contractStatusList where name equals to UPDATED_NAME
        defaultContractStatusShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllContractStatusesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        contractStatusRepository.saveAndFlush(contractStatus);

        // Get all the contractStatusList where name in DEFAULT_NAME or UPDATED_NAME
        defaultContractStatusShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the contractStatusList where name equals to UPDATED_NAME
        defaultContractStatusShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllContractStatusesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        contractStatusRepository.saveAndFlush(contractStatus);

        // Get all the contractStatusList where name is not null
        defaultContractStatusShouldBeFound("name.specified=true");

        // Get all the contractStatusList where name is null
        defaultContractStatusShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllContractStatusesByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        contractStatusRepository.saveAndFlush(contractStatus);

        // Get all the contractStatusList where code equals to DEFAULT_CODE
        defaultContractStatusShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the contractStatusList where code equals to UPDATED_CODE
        defaultContractStatusShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllContractStatusesByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        contractStatusRepository.saveAndFlush(contractStatus);

        // Get all the contractStatusList where code in DEFAULT_CODE or UPDATED_CODE
        defaultContractStatusShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the contractStatusList where code equals to UPDATED_CODE
        defaultContractStatusShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllContractStatusesByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        contractStatusRepository.saveAndFlush(contractStatus);

        // Get all the contractStatusList where code is not null
        defaultContractStatusShouldBeFound("code.specified=true");

        // Get all the contractStatusList where code is null
        defaultContractStatusShouldNotBeFound("code.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultContractStatusShouldBeFound(String filter) throws Exception {
        restContractStatusMockMvc.perform(get("/api/contract-statuses?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contractStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultContractStatusShouldNotBeFound(String filter) throws Exception {
        restContractStatusMockMvc.perform(get("/api/contract-statuses?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingContractStatus() throws Exception {
        // Get the contractStatus
        restContractStatusMockMvc.perform(get("/api/contract-statuses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateContractStatus() throws Exception {
        // Initialize the database
        contractStatusService.save(contractStatus);

        int databaseSizeBeforeUpdate = contractStatusRepository.findAll().size();

        // Update the contractStatus
        ContractStatus updatedContractStatus = contractStatusRepository.findOne(contractStatus.getId());
        updatedContractStatus
            .name(UPDATED_NAME)
            .code(UPDATED_CODE);

        restContractStatusMockMvc.perform(put("/api/contract-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedContractStatus)))
            .andExpect(status().isOk());

        // Validate the ContractStatus in the database
        List<ContractStatus> contractStatusList = contractStatusRepository.findAll();
        assertThat(contractStatusList).hasSize(databaseSizeBeforeUpdate);
        ContractStatus testContractStatus = contractStatusList.get(contractStatusList.size() - 1);
        assertThat(testContractStatus.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testContractStatus.getCode()).isEqualTo(UPDATED_CODE);
    }

    @Test
    @Transactional
    public void updateNonExistingContractStatus() throws Exception {
        int databaseSizeBeforeUpdate = contractStatusRepository.findAll().size();

        // Create the ContractStatus

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restContractStatusMockMvc.perform(put("/api/contract-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contractStatus)))
            .andExpect(status().isCreated());

        // Validate the ContractStatus in the database
        List<ContractStatus> contractStatusList = contractStatusRepository.findAll();
        assertThat(contractStatusList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteContractStatus() throws Exception {
        // Initialize the database
        contractStatusService.save(contractStatus);

        int databaseSizeBeforeDelete = contractStatusRepository.findAll().size();

        // Get the contractStatus
        restContractStatusMockMvc.perform(delete("/api/contract-statuses/{id}", contractStatus.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ContractStatus> contractStatusList = contractStatusRepository.findAll();
        assertThat(contractStatusList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContractStatus.class);
        ContractStatus contractStatus1 = new ContractStatus();
        contractStatus1.setId(1L);
        ContractStatus contractStatus2 = new ContractStatus();
        contractStatus2.setId(contractStatus1.getId());
        assertThat(contractStatus1).isEqualTo(contractStatus2);
        contractStatus2.setId(2L);
        assertThat(contractStatus1).isNotEqualTo(contractStatus2);
        contractStatus1.setId(null);
        assertThat(contractStatus1).isNotEqualTo(contractStatus2);
    }
}
