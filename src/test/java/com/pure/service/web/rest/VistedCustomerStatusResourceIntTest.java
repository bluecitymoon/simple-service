package com.pure.service.web.rest;

import com.pure.service.SimpleServiceApp;

import com.pure.service.domain.VistedCustomerStatus;
import com.pure.service.repository.VistedCustomerStatusRepository;
import com.pure.service.service.VistedCustomerStatusService;
import com.pure.service.web.rest.errors.ExceptionTranslator;
import com.pure.service.service.dto.VistedCustomerStatusCriteria;
import com.pure.service.service.VistedCustomerStatusQueryService;

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
 * Test class for the VistedCustomerStatusResource REST controller.
 *
 * @see VistedCustomerStatusResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SimpleServiceApp.class)
public class VistedCustomerStatusResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTS = "BBBBBBBBBB";

    @Autowired
    private VistedCustomerStatusRepository vistedCustomerStatusRepository;

    @Autowired
    private VistedCustomerStatusService vistedCustomerStatusService;

    @Autowired
    private VistedCustomerStatusQueryService vistedCustomerStatusQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restVistedCustomerStatusMockMvc;

    private VistedCustomerStatus vistedCustomerStatus;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VistedCustomerStatusResource vistedCustomerStatusResource = new VistedCustomerStatusResource(vistedCustomerStatusService, vistedCustomerStatusQueryService);
        this.restVistedCustomerStatusMockMvc = MockMvcBuilders.standaloneSetup(vistedCustomerStatusResource)
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
    public static VistedCustomerStatus createEntity(EntityManager em) {
        VistedCustomerStatus vistedCustomerStatus = new VistedCustomerStatus()
            .name(DEFAULT_NAME)
            .code(DEFAULT_CODE)
            .comments(DEFAULT_COMMENTS);
        return vistedCustomerStatus;
    }

    @Before
    public void initTest() {
        vistedCustomerStatus = createEntity(em);
    }

    @Test
    @Transactional
    public void createVistedCustomerStatus() throws Exception {
        int databaseSizeBeforeCreate = vistedCustomerStatusRepository.findAll().size();

        // Create the VistedCustomerStatus
        restVistedCustomerStatusMockMvc.perform(post("/api/visted-customer-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vistedCustomerStatus)))
            .andExpect(status().isCreated());

        // Validate the VistedCustomerStatus in the database
        List<VistedCustomerStatus> vistedCustomerStatusList = vistedCustomerStatusRepository.findAll();
        assertThat(vistedCustomerStatusList).hasSize(databaseSizeBeforeCreate + 1);
        VistedCustomerStatus testVistedCustomerStatus = vistedCustomerStatusList.get(vistedCustomerStatusList.size() - 1);
        assertThat(testVistedCustomerStatus.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testVistedCustomerStatus.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testVistedCustomerStatus.getComments()).isEqualTo(DEFAULT_COMMENTS);
    }

    @Test
    @Transactional
    public void createVistedCustomerStatusWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = vistedCustomerStatusRepository.findAll().size();

        // Create the VistedCustomerStatus with an existing ID
        vistedCustomerStatus.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVistedCustomerStatusMockMvc.perform(post("/api/visted-customer-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vistedCustomerStatus)))
            .andExpect(status().isBadRequest());

        // Validate the VistedCustomerStatus in the database
        List<VistedCustomerStatus> vistedCustomerStatusList = vistedCustomerStatusRepository.findAll();
        assertThat(vistedCustomerStatusList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllVistedCustomerStatuses() throws Exception {
        // Initialize the database
        vistedCustomerStatusRepository.saveAndFlush(vistedCustomerStatus);

        // Get all the vistedCustomerStatusList
        restVistedCustomerStatusMockMvc.perform(get("/api/visted-customer-statuses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vistedCustomerStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS.toString())));
    }

    @Test
    @Transactional
    public void getVistedCustomerStatus() throws Exception {
        // Initialize the database
        vistedCustomerStatusRepository.saveAndFlush(vistedCustomerStatus);

        // Get the vistedCustomerStatus
        restVistedCustomerStatusMockMvc.perform(get("/api/visted-customer-statuses/{id}", vistedCustomerStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(vistedCustomerStatus.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS.toString()));
    }

    @Test
    @Transactional
    public void getAllVistedCustomerStatusesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        vistedCustomerStatusRepository.saveAndFlush(vistedCustomerStatus);

        // Get all the vistedCustomerStatusList where name equals to DEFAULT_NAME
        defaultVistedCustomerStatusShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the vistedCustomerStatusList where name equals to UPDATED_NAME
        defaultVistedCustomerStatusShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllVistedCustomerStatusesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        vistedCustomerStatusRepository.saveAndFlush(vistedCustomerStatus);

        // Get all the vistedCustomerStatusList where name in DEFAULT_NAME or UPDATED_NAME
        defaultVistedCustomerStatusShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the vistedCustomerStatusList where name equals to UPDATED_NAME
        defaultVistedCustomerStatusShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllVistedCustomerStatusesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        vistedCustomerStatusRepository.saveAndFlush(vistedCustomerStatus);

        // Get all the vistedCustomerStatusList where name is not null
        defaultVistedCustomerStatusShouldBeFound("name.specified=true");

        // Get all the vistedCustomerStatusList where name is null
        defaultVistedCustomerStatusShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllVistedCustomerStatusesByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        vistedCustomerStatusRepository.saveAndFlush(vistedCustomerStatus);

        // Get all the vistedCustomerStatusList where code equals to DEFAULT_CODE
        defaultVistedCustomerStatusShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the vistedCustomerStatusList where code equals to UPDATED_CODE
        defaultVistedCustomerStatusShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllVistedCustomerStatusesByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        vistedCustomerStatusRepository.saveAndFlush(vistedCustomerStatus);

        // Get all the vistedCustomerStatusList where code in DEFAULT_CODE or UPDATED_CODE
        defaultVistedCustomerStatusShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the vistedCustomerStatusList where code equals to UPDATED_CODE
        defaultVistedCustomerStatusShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllVistedCustomerStatusesByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        vistedCustomerStatusRepository.saveAndFlush(vistedCustomerStatus);

        // Get all the vistedCustomerStatusList where code is not null
        defaultVistedCustomerStatusShouldBeFound("code.specified=true");

        // Get all the vistedCustomerStatusList where code is null
        defaultVistedCustomerStatusShouldNotBeFound("code.specified=false");
    }

    @Test
    @Transactional
    public void getAllVistedCustomerStatusesByCommentsIsEqualToSomething() throws Exception {
        // Initialize the database
        vistedCustomerStatusRepository.saveAndFlush(vistedCustomerStatus);

        // Get all the vistedCustomerStatusList where comments equals to DEFAULT_COMMENTS
        defaultVistedCustomerStatusShouldBeFound("comments.equals=" + DEFAULT_COMMENTS);

        // Get all the vistedCustomerStatusList where comments equals to UPDATED_COMMENTS
        defaultVistedCustomerStatusShouldNotBeFound("comments.equals=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllVistedCustomerStatusesByCommentsIsInShouldWork() throws Exception {
        // Initialize the database
        vistedCustomerStatusRepository.saveAndFlush(vistedCustomerStatus);

        // Get all the vistedCustomerStatusList where comments in DEFAULT_COMMENTS or UPDATED_COMMENTS
        defaultVistedCustomerStatusShouldBeFound("comments.in=" + DEFAULT_COMMENTS + "," + UPDATED_COMMENTS);

        // Get all the vistedCustomerStatusList where comments equals to UPDATED_COMMENTS
        defaultVistedCustomerStatusShouldNotBeFound("comments.in=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllVistedCustomerStatusesByCommentsIsNullOrNotNull() throws Exception {
        // Initialize the database
        vistedCustomerStatusRepository.saveAndFlush(vistedCustomerStatus);

        // Get all the vistedCustomerStatusList where comments is not null
        defaultVistedCustomerStatusShouldBeFound("comments.specified=true");

        // Get all the vistedCustomerStatusList where comments is null
        defaultVistedCustomerStatusShouldNotBeFound("comments.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultVistedCustomerStatusShouldBeFound(String filter) throws Exception {
        restVistedCustomerStatusMockMvc.perform(get("/api/visted-customer-statuses?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vistedCustomerStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultVistedCustomerStatusShouldNotBeFound(String filter) throws Exception {
        restVistedCustomerStatusMockMvc.perform(get("/api/visted-customer-statuses?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingVistedCustomerStatus() throws Exception {
        // Get the vistedCustomerStatus
        restVistedCustomerStatusMockMvc.perform(get("/api/visted-customer-statuses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVistedCustomerStatus() throws Exception {
        // Initialize the database
        vistedCustomerStatusService.save(vistedCustomerStatus);

        int databaseSizeBeforeUpdate = vistedCustomerStatusRepository.findAll().size();

        // Update the vistedCustomerStatus
        VistedCustomerStatus updatedVistedCustomerStatus = vistedCustomerStatusRepository.findOne(vistedCustomerStatus.getId());
        updatedVistedCustomerStatus
            .name(UPDATED_NAME)
            .code(UPDATED_CODE)
            .comments(UPDATED_COMMENTS);

        restVistedCustomerStatusMockMvc.perform(put("/api/visted-customer-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedVistedCustomerStatus)))
            .andExpect(status().isOk());

        // Validate the VistedCustomerStatus in the database
        List<VistedCustomerStatus> vistedCustomerStatusList = vistedCustomerStatusRepository.findAll();
        assertThat(vistedCustomerStatusList).hasSize(databaseSizeBeforeUpdate);
        VistedCustomerStatus testVistedCustomerStatus = vistedCustomerStatusList.get(vistedCustomerStatusList.size() - 1);
        assertThat(testVistedCustomerStatus.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testVistedCustomerStatus.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testVistedCustomerStatus.getComments()).isEqualTo(UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    public void updateNonExistingVistedCustomerStatus() throws Exception {
        int databaseSizeBeforeUpdate = vistedCustomerStatusRepository.findAll().size();

        // Create the VistedCustomerStatus

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restVistedCustomerStatusMockMvc.perform(put("/api/visted-customer-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vistedCustomerStatus)))
            .andExpect(status().isCreated());

        // Validate the VistedCustomerStatus in the database
        List<VistedCustomerStatus> vistedCustomerStatusList = vistedCustomerStatusRepository.findAll();
        assertThat(vistedCustomerStatusList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteVistedCustomerStatus() throws Exception {
        // Initialize the database
        vistedCustomerStatusService.save(vistedCustomerStatus);

        int databaseSizeBeforeDelete = vistedCustomerStatusRepository.findAll().size();

        // Get the vistedCustomerStatus
        restVistedCustomerStatusMockMvc.perform(delete("/api/visted-customer-statuses/{id}", vistedCustomerStatus.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<VistedCustomerStatus> vistedCustomerStatusList = vistedCustomerStatusRepository.findAll();
        assertThat(vistedCustomerStatusList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VistedCustomerStatus.class);
        VistedCustomerStatus vistedCustomerStatus1 = new VistedCustomerStatus();
        vistedCustomerStatus1.setId(1L);
        VistedCustomerStatus vistedCustomerStatus2 = new VistedCustomerStatus();
        vistedCustomerStatus2.setId(vistedCustomerStatus1.getId());
        assertThat(vistedCustomerStatus1).isEqualTo(vistedCustomerStatus2);
        vistedCustomerStatus2.setId(2L);
        assertThat(vistedCustomerStatus1).isNotEqualTo(vistedCustomerStatus2);
        vistedCustomerStatus1.setId(null);
        assertThat(vistedCustomerStatus1).isNotEqualTo(vistedCustomerStatus2);
    }
}
