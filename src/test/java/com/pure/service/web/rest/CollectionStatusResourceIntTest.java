package com.pure.service.web.rest;

import com.pure.service.SimpleServiceApp;

import com.pure.service.domain.CollectionStatus;
import com.pure.service.repository.CollectionStatusRepository;
import com.pure.service.service.CollectionStatusService;
import com.pure.service.web.rest.errors.ExceptionTranslator;
import com.pure.service.service.dto.CollectionStatusCriteria;
import com.pure.service.service.CollectionStatusQueryService;

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
 * Test class for the CollectionStatusResource REST controller.
 *
 * @see CollectionStatusResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SimpleServiceApp.class)
public class CollectionStatusResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    @Autowired
    private CollectionStatusRepository collectionStatusRepository;

    @Autowired
    private CollectionStatusService collectionStatusService;

    @Autowired
    private CollectionStatusQueryService collectionStatusQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCollectionStatusMockMvc;

    private CollectionStatus collectionStatus;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CollectionStatusResource collectionStatusResource = new CollectionStatusResource(collectionStatusService, collectionStatusQueryService);
        this.restCollectionStatusMockMvc = MockMvcBuilders.standaloneSetup(collectionStatusResource)
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
    public static CollectionStatus createEntity(EntityManager em) {
        CollectionStatus collectionStatus = new CollectionStatus()
            .name(DEFAULT_NAME)
            .code(DEFAULT_CODE);
        return collectionStatus;
    }

    @Before
    public void initTest() {
        collectionStatus = createEntity(em);
    }

    @Test
    @Transactional
    public void createCollectionStatus() throws Exception {
        int databaseSizeBeforeCreate = collectionStatusRepository.findAll().size();

        // Create the CollectionStatus
        restCollectionStatusMockMvc.perform(post("/api/collection-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(collectionStatus)))
            .andExpect(status().isCreated());

        // Validate the CollectionStatus in the database
        List<CollectionStatus> collectionStatusList = collectionStatusRepository.findAll();
        assertThat(collectionStatusList).hasSize(databaseSizeBeforeCreate + 1);
        CollectionStatus testCollectionStatus = collectionStatusList.get(collectionStatusList.size() - 1);
        assertThat(testCollectionStatus.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCollectionStatus.getCode()).isEqualTo(DEFAULT_CODE);
    }

    @Test
    @Transactional
    public void createCollectionStatusWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = collectionStatusRepository.findAll().size();

        // Create the CollectionStatus with an existing ID
        collectionStatus.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCollectionStatusMockMvc.perform(post("/api/collection-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(collectionStatus)))
            .andExpect(status().isBadRequest());

        // Validate the CollectionStatus in the database
        List<CollectionStatus> collectionStatusList = collectionStatusRepository.findAll();
        assertThat(collectionStatusList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCollectionStatuses() throws Exception {
        // Initialize the database
        collectionStatusRepository.saveAndFlush(collectionStatus);

        // Get all the collectionStatusList
        restCollectionStatusMockMvc.perform(get("/api/collection-statuses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(collectionStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())));
    }

    @Test
    @Transactional
    public void getCollectionStatus() throws Exception {
        // Initialize the database
        collectionStatusRepository.saveAndFlush(collectionStatus);

        // Get the collectionStatus
        restCollectionStatusMockMvc.perform(get("/api/collection-statuses/{id}", collectionStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(collectionStatus.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()));
    }

    @Test
    @Transactional
    public void getAllCollectionStatusesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        collectionStatusRepository.saveAndFlush(collectionStatus);

        // Get all the collectionStatusList where name equals to DEFAULT_NAME
        defaultCollectionStatusShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the collectionStatusList where name equals to UPDATED_NAME
        defaultCollectionStatusShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCollectionStatusesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        collectionStatusRepository.saveAndFlush(collectionStatus);

        // Get all the collectionStatusList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCollectionStatusShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the collectionStatusList where name equals to UPDATED_NAME
        defaultCollectionStatusShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCollectionStatusesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        collectionStatusRepository.saveAndFlush(collectionStatus);

        // Get all the collectionStatusList where name is not null
        defaultCollectionStatusShouldBeFound("name.specified=true");

        // Get all the collectionStatusList where name is null
        defaultCollectionStatusShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllCollectionStatusesByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        collectionStatusRepository.saveAndFlush(collectionStatus);

        // Get all the collectionStatusList where code equals to DEFAULT_CODE
        defaultCollectionStatusShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the collectionStatusList where code equals to UPDATED_CODE
        defaultCollectionStatusShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllCollectionStatusesByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        collectionStatusRepository.saveAndFlush(collectionStatus);

        // Get all the collectionStatusList where code in DEFAULT_CODE or UPDATED_CODE
        defaultCollectionStatusShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the collectionStatusList where code equals to UPDATED_CODE
        defaultCollectionStatusShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllCollectionStatusesByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        collectionStatusRepository.saveAndFlush(collectionStatus);

        // Get all the collectionStatusList where code is not null
        defaultCollectionStatusShouldBeFound("code.specified=true");

        // Get all the collectionStatusList where code is null
        defaultCollectionStatusShouldNotBeFound("code.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultCollectionStatusShouldBeFound(String filter) throws Exception {
        restCollectionStatusMockMvc.perform(get("/api/collection-statuses?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(collectionStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultCollectionStatusShouldNotBeFound(String filter) throws Exception {
        restCollectionStatusMockMvc.perform(get("/api/collection-statuses?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingCollectionStatus() throws Exception {
        // Get the collectionStatus
        restCollectionStatusMockMvc.perform(get("/api/collection-statuses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCollectionStatus() throws Exception {
        // Initialize the database
        collectionStatusService.save(collectionStatus);

        int databaseSizeBeforeUpdate = collectionStatusRepository.findAll().size();

        // Update the collectionStatus
        CollectionStatus updatedCollectionStatus = collectionStatusRepository.findOne(collectionStatus.getId());
        updatedCollectionStatus
            .name(UPDATED_NAME)
            .code(UPDATED_CODE);

        restCollectionStatusMockMvc.perform(put("/api/collection-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCollectionStatus)))
            .andExpect(status().isOk());

        // Validate the CollectionStatus in the database
        List<CollectionStatus> collectionStatusList = collectionStatusRepository.findAll();
        assertThat(collectionStatusList).hasSize(databaseSizeBeforeUpdate);
        CollectionStatus testCollectionStatus = collectionStatusList.get(collectionStatusList.size() - 1);
        assertThat(testCollectionStatus.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCollectionStatus.getCode()).isEqualTo(UPDATED_CODE);
    }

    @Test
    @Transactional
    public void updateNonExistingCollectionStatus() throws Exception {
        int databaseSizeBeforeUpdate = collectionStatusRepository.findAll().size();

        // Create the CollectionStatus

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCollectionStatusMockMvc.perform(put("/api/collection-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(collectionStatus)))
            .andExpect(status().isCreated());

        // Validate the CollectionStatus in the database
        List<CollectionStatus> collectionStatusList = collectionStatusRepository.findAll();
        assertThat(collectionStatusList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCollectionStatus() throws Exception {
        // Initialize the database
        collectionStatusService.save(collectionStatus);

        int databaseSizeBeforeDelete = collectionStatusRepository.findAll().size();

        // Get the collectionStatus
        restCollectionStatusMockMvc.perform(delete("/api/collection-statuses/{id}", collectionStatus.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CollectionStatus> collectionStatusList = collectionStatusRepository.findAll();
        assertThat(collectionStatusList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CollectionStatus.class);
        CollectionStatus collectionStatus1 = new CollectionStatus();
        collectionStatus1.setId(1L);
        CollectionStatus collectionStatus2 = new CollectionStatus();
        collectionStatus2.setId(collectionStatus1.getId());
        assertThat(collectionStatus1).isEqualTo(collectionStatus2);
        collectionStatus2.setId(2L);
        assertThat(collectionStatus1).isNotEqualTo(collectionStatus2);
        collectionStatus1.setId(null);
        assertThat(collectionStatus1).isNotEqualTo(collectionStatus2);
    }
}
