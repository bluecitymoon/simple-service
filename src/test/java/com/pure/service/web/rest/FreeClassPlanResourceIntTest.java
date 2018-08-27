package com.pure.service.web.rest;

import com.pure.service.SimpleServiceApp;

import com.pure.service.domain.FreeClassPlan;
import com.pure.service.repository.FreeClassPlanRepository;
import com.pure.service.service.FreeClassPlanService;
import com.pure.service.web.rest.errors.ExceptionTranslator;
import com.pure.service.service.dto.FreeClassPlanCriteria;
import com.pure.service.service.FreeClassPlanQueryService;

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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the FreeClassPlanResource REST controller.
 *
 * @see FreeClassPlanResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SimpleServiceApp.class)
public class FreeClassPlanResourceIntTest {

    private static final Instant DEFAULT_PLAN_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_PLAN_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_LIMIT_COUNT = 1;
    private static final Integer UPDATED_LIMIT_COUNT = 2;

    private static final Integer DEFAULT_ACTUAL_COUNT = 1;
    private static final Integer UPDATED_ACTUAL_COUNT = 2;

    @Autowired
    private FreeClassPlanRepository freeClassPlanRepository;

    @Autowired
    private FreeClassPlanService freeClassPlanService;

    @Autowired
    private FreeClassPlanQueryService freeClassPlanQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFreeClassPlanMockMvc;

    private FreeClassPlan freeClassPlan;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FreeClassPlanResource freeClassPlanResource = new FreeClassPlanResource(freeClassPlanService, freeClassPlanQueryService);
        this.restFreeClassPlanMockMvc = MockMvcBuilders.standaloneSetup(freeClassPlanResource)
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
    public static FreeClassPlan createEntity(EntityManager em) {
        FreeClassPlan freeClassPlan = new FreeClassPlan()
            .planDate(DEFAULT_PLAN_DATE)
            .limitCount(DEFAULT_LIMIT_COUNT)
            .actualCount(DEFAULT_ACTUAL_COUNT);
        return freeClassPlan;
    }

    @Before
    public void initTest() {
        freeClassPlan = createEntity(em);
    }

    @Test
    @Transactional
    public void createFreeClassPlan() throws Exception {
        int databaseSizeBeforeCreate = freeClassPlanRepository.findAll().size();

        // Create the FreeClassPlan
        restFreeClassPlanMockMvc.perform(post("/api/free-class-plans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(freeClassPlan)))
            .andExpect(status().isCreated());

        // Validate the FreeClassPlan in the database
        List<FreeClassPlan> freeClassPlanList = freeClassPlanRepository.findAll();
        assertThat(freeClassPlanList).hasSize(databaseSizeBeforeCreate + 1);
        FreeClassPlan testFreeClassPlan = freeClassPlanList.get(freeClassPlanList.size() - 1);
        assertThat(testFreeClassPlan.getPlanDate()).isEqualTo(DEFAULT_PLAN_DATE);
        assertThat(testFreeClassPlan.getLimitCount()).isEqualTo(DEFAULT_LIMIT_COUNT);
        assertThat(testFreeClassPlan.getActualCount()).isEqualTo(DEFAULT_ACTUAL_COUNT);
    }

    @Test
    @Transactional
    public void createFreeClassPlanWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = freeClassPlanRepository.findAll().size();

        // Create the FreeClassPlan with an existing ID
        freeClassPlan.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFreeClassPlanMockMvc.perform(post("/api/free-class-plans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(freeClassPlan)))
            .andExpect(status().isBadRequest());

        // Validate the FreeClassPlan in the database
        List<FreeClassPlan> freeClassPlanList = freeClassPlanRepository.findAll();
        assertThat(freeClassPlanList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllFreeClassPlans() throws Exception {
        // Initialize the database
        freeClassPlanRepository.saveAndFlush(freeClassPlan);

        // Get all the freeClassPlanList
        restFreeClassPlanMockMvc.perform(get("/api/free-class-plans?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(freeClassPlan.getId().intValue())))
            .andExpect(jsonPath("$.[*].planDate").value(hasItem(DEFAULT_PLAN_DATE.toString())))
            .andExpect(jsonPath("$.[*].limitCount").value(hasItem(DEFAULT_LIMIT_COUNT)))
            .andExpect(jsonPath("$.[*].actualCount").value(hasItem(DEFAULT_ACTUAL_COUNT)));
    }

    @Test
    @Transactional
    public void getFreeClassPlan() throws Exception {
        // Initialize the database
        freeClassPlanRepository.saveAndFlush(freeClassPlan);

        // Get the freeClassPlan
        restFreeClassPlanMockMvc.perform(get("/api/free-class-plans/{id}", freeClassPlan.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(freeClassPlan.getId().intValue()))
            .andExpect(jsonPath("$.planDate").value(DEFAULT_PLAN_DATE.toString()))
            .andExpect(jsonPath("$.limitCount").value(DEFAULT_LIMIT_COUNT))
            .andExpect(jsonPath("$.actualCount").value(DEFAULT_ACTUAL_COUNT));
    }

    @Test
    @Transactional
    public void getAllFreeClassPlansByPlanDateIsEqualToSomething() throws Exception {
        // Initialize the database
        freeClassPlanRepository.saveAndFlush(freeClassPlan);

        // Get all the freeClassPlanList where planDate equals to DEFAULT_PLAN_DATE
        defaultFreeClassPlanShouldBeFound("planDate.equals=" + DEFAULT_PLAN_DATE);

        // Get all the freeClassPlanList where planDate equals to UPDATED_PLAN_DATE
        defaultFreeClassPlanShouldNotBeFound("planDate.equals=" + UPDATED_PLAN_DATE);
    }

    @Test
    @Transactional
    public void getAllFreeClassPlansByPlanDateIsInShouldWork() throws Exception {
        // Initialize the database
        freeClassPlanRepository.saveAndFlush(freeClassPlan);

        // Get all the freeClassPlanList where planDate in DEFAULT_PLAN_DATE or UPDATED_PLAN_DATE
        defaultFreeClassPlanShouldBeFound("planDate.in=" + DEFAULT_PLAN_DATE + "," + UPDATED_PLAN_DATE);

        // Get all the freeClassPlanList where planDate equals to UPDATED_PLAN_DATE
        defaultFreeClassPlanShouldNotBeFound("planDate.in=" + UPDATED_PLAN_DATE);
    }

    @Test
    @Transactional
    public void getAllFreeClassPlansByPlanDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        freeClassPlanRepository.saveAndFlush(freeClassPlan);

        // Get all the freeClassPlanList where planDate is not null
        defaultFreeClassPlanShouldBeFound("planDate.specified=true");

        // Get all the freeClassPlanList where planDate is null
        defaultFreeClassPlanShouldNotBeFound("planDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllFreeClassPlansByLimitCountIsEqualToSomething() throws Exception {
        // Initialize the database
        freeClassPlanRepository.saveAndFlush(freeClassPlan);

        // Get all the freeClassPlanList where limitCount equals to DEFAULT_LIMIT_COUNT
        defaultFreeClassPlanShouldBeFound("limitCount.equals=" + DEFAULT_LIMIT_COUNT);

        // Get all the freeClassPlanList where limitCount equals to UPDATED_LIMIT_COUNT
        defaultFreeClassPlanShouldNotBeFound("limitCount.equals=" + UPDATED_LIMIT_COUNT);
    }

    @Test
    @Transactional
    public void getAllFreeClassPlansByLimitCountIsInShouldWork() throws Exception {
        // Initialize the database
        freeClassPlanRepository.saveAndFlush(freeClassPlan);

        // Get all the freeClassPlanList where limitCount in DEFAULT_LIMIT_COUNT or UPDATED_LIMIT_COUNT
        defaultFreeClassPlanShouldBeFound("limitCount.in=" + DEFAULT_LIMIT_COUNT + "," + UPDATED_LIMIT_COUNT);

        // Get all the freeClassPlanList where limitCount equals to UPDATED_LIMIT_COUNT
        defaultFreeClassPlanShouldNotBeFound("limitCount.in=" + UPDATED_LIMIT_COUNT);
    }

    @Test
    @Transactional
    public void getAllFreeClassPlansByLimitCountIsNullOrNotNull() throws Exception {
        // Initialize the database
        freeClassPlanRepository.saveAndFlush(freeClassPlan);

        // Get all the freeClassPlanList where limitCount is not null
        defaultFreeClassPlanShouldBeFound("limitCount.specified=true");

        // Get all the freeClassPlanList where limitCount is null
        defaultFreeClassPlanShouldNotBeFound("limitCount.specified=false");
    }

    @Test
    @Transactional
    public void getAllFreeClassPlansByLimitCountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        freeClassPlanRepository.saveAndFlush(freeClassPlan);

        // Get all the freeClassPlanList where limitCount greater than or equals to DEFAULT_LIMIT_COUNT
        defaultFreeClassPlanShouldBeFound("limitCount.greaterOrEqualThan=" + DEFAULT_LIMIT_COUNT);

        // Get all the freeClassPlanList where limitCount greater than or equals to UPDATED_LIMIT_COUNT
        defaultFreeClassPlanShouldNotBeFound("limitCount.greaterOrEqualThan=" + UPDATED_LIMIT_COUNT);
    }

    @Test
    @Transactional
    public void getAllFreeClassPlansByLimitCountIsLessThanSomething() throws Exception {
        // Initialize the database
        freeClassPlanRepository.saveAndFlush(freeClassPlan);

        // Get all the freeClassPlanList where limitCount less than or equals to DEFAULT_LIMIT_COUNT
        defaultFreeClassPlanShouldNotBeFound("limitCount.lessThan=" + DEFAULT_LIMIT_COUNT);

        // Get all the freeClassPlanList where limitCount less than or equals to UPDATED_LIMIT_COUNT
        defaultFreeClassPlanShouldBeFound("limitCount.lessThan=" + UPDATED_LIMIT_COUNT);
    }


    @Test
    @Transactional
    public void getAllFreeClassPlansByActualCountIsEqualToSomething() throws Exception {
        // Initialize the database
        freeClassPlanRepository.saveAndFlush(freeClassPlan);

        // Get all the freeClassPlanList where actualCount equals to DEFAULT_ACTUAL_COUNT
        defaultFreeClassPlanShouldBeFound("actualCount.equals=" + DEFAULT_ACTUAL_COUNT);

        // Get all the freeClassPlanList where actualCount equals to UPDATED_ACTUAL_COUNT
        defaultFreeClassPlanShouldNotBeFound("actualCount.equals=" + UPDATED_ACTUAL_COUNT);
    }

    @Test
    @Transactional
    public void getAllFreeClassPlansByActualCountIsInShouldWork() throws Exception {
        // Initialize the database
        freeClassPlanRepository.saveAndFlush(freeClassPlan);

        // Get all the freeClassPlanList where actualCount in DEFAULT_ACTUAL_COUNT or UPDATED_ACTUAL_COUNT
        defaultFreeClassPlanShouldBeFound("actualCount.in=" + DEFAULT_ACTUAL_COUNT + "," + UPDATED_ACTUAL_COUNT);

        // Get all the freeClassPlanList where actualCount equals to UPDATED_ACTUAL_COUNT
        defaultFreeClassPlanShouldNotBeFound("actualCount.in=" + UPDATED_ACTUAL_COUNT);
    }

    @Test
    @Transactional
    public void getAllFreeClassPlansByActualCountIsNullOrNotNull() throws Exception {
        // Initialize the database
        freeClassPlanRepository.saveAndFlush(freeClassPlan);

        // Get all the freeClassPlanList where actualCount is not null
        defaultFreeClassPlanShouldBeFound("actualCount.specified=true");

        // Get all the freeClassPlanList where actualCount is null
        defaultFreeClassPlanShouldNotBeFound("actualCount.specified=false");
    }

    @Test
    @Transactional
    public void getAllFreeClassPlansByActualCountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        freeClassPlanRepository.saveAndFlush(freeClassPlan);

        // Get all the freeClassPlanList where actualCount greater than or equals to DEFAULT_ACTUAL_COUNT
        defaultFreeClassPlanShouldBeFound("actualCount.greaterOrEqualThan=" + DEFAULT_ACTUAL_COUNT);

        // Get all the freeClassPlanList where actualCount greater than or equals to UPDATED_ACTUAL_COUNT
        defaultFreeClassPlanShouldNotBeFound("actualCount.greaterOrEqualThan=" + UPDATED_ACTUAL_COUNT);
    }

    @Test
    @Transactional
    public void getAllFreeClassPlansByActualCountIsLessThanSomething() throws Exception {
        // Initialize the database
        freeClassPlanRepository.saveAndFlush(freeClassPlan);

        // Get all the freeClassPlanList where actualCount less than or equals to DEFAULT_ACTUAL_COUNT
        defaultFreeClassPlanShouldNotBeFound("actualCount.lessThan=" + DEFAULT_ACTUAL_COUNT);

        // Get all the freeClassPlanList where actualCount less than or equals to UPDATED_ACTUAL_COUNT
        defaultFreeClassPlanShouldBeFound("actualCount.lessThan=" + UPDATED_ACTUAL_COUNT);
    }


    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultFreeClassPlanShouldBeFound(String filter) throws Exception {
        restFreeClassPlanMockMvc.perform(get("/api/free-class-plans?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(freeClassPlan.getId().intValue())))
            .andExpect(jsonPath("$.[*].planDate").value(hasItem(DEFAULT_PLAN_DATE.toString())))
            .andExpect(jsonPath("$.[*].limitCount").value(hasItem(DEFAULT_LIMIT_COUNT)))
            .andExpect(jsonPath("$.[*].actualCount").value(hasItem(DEFAULT_ACTUAL_COUNT)));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultFreeClassPlanShouldNotBeFound(String filter) throws Exception {
        restFreeClassPlanMockMvc.perform(get("/api/free-class-plans?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingFreeClassPlan() throws Exception {
        // Get the freeClassPlan
        restFreeClassPlanMockMvc.perform(get("/api/free-class-plans/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFreeClassPlan() throws Exception {
        // Initialize the database
        freeClassPlanService.save(freeClassPlan);

        int databaseSizeBeforeUpdate = freeClassPlanRepository.findAll().size();

        // Update the freeClassPlan
        FreeClassPlan updatedFreeClassPlan = freeClassPlanRepository.findOne(freeClassPlan.getId());
        updatedFreeClassPlan
            .planDate(UPDATED_PLAN_DATE)
            .limitCount(UPDATED_LIMIT_COUNT)
            .actualCount(UPDATED_ACTUAL_COUNT);

        restFreeClassPlanMockMvc.perform(put("/api/free-class-plans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFreeClassPlan)))
            .andExpect(status().isOk());

        // Validate the FreeClassPlan in the database
        List<FreeClassPlan> freeClassPlanList = freeClassPlanRepository.findAll();
        assertThat(freeClassPlanList).hasSize(databaseSizeBeforeUpdate);
        FreeClassPlan testFreeClassPlan = freeClassPlanList.get(freeClassPlanList.size() - 1);
        assertThat(testFreeClassPlan.getPlanDate()).isEqualTo(UPDATED_PLAN_DATE);
        assertThat(testFreeClassPlan.getLimitCount()).isEqualTo(UPDATED_LIMIT_COUNT);
        assertThat(testFreeClassPlan.getActualCount()).isEqualTo(UPDATED_ACTUAL_COUNT);
    }

    @Test
    @Transactional
    public void updateNonExistingFreeClassPlan() throws Exception {
        int databaseSizeBeforeUpdate = freeClassPlanRepository.findAll().size();

        // Create the FreeClassPlan

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFreeClassPlanMockMvc.perform(put("/api/free-class-plans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(freeClassPlan)))
            .andExpect(status().isCreated());

        // Validate the FreeClassPlan in the database
        List<FreeClassPlan> freeClassPlanList = freeClassPlanRepository.findAll();
        assertThat(freeClassPlanList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteFreeClassPlan() throws Exception {
        // Initialize the database
        freeClassPlanService.save(freeClassPlan);

        int databaseSizeBeforeDelete = freeClassPlanRepository.findAll().size();

        // Get the freeClassPlan
        restFreeClassPlanMockMvc.perform(delete("/api/free-class-plans/{id}", freeClassPlan.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<FreeClassPlan> freeClassPlanList = freeClassPlanRepository.findAll();
        assertThat(freeClassPlanList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FreeClassPlan.class);
        FreeClassPlan freeClassPlan1 = new FreeClassPlan();
        freeClassPlan1.setId(1L);
        FreeClassPlan freeClassPlan2 = new FreeClassPlan();
        freeClassPlan2.setId(freeClassPlan1.getId());
        assertThat(freeClassPlan1).isEqualTo(freeClassPlan2);
        freeClassPlan2.setId(2L);
        assertThat(freeClassPlan1).isNotEqualTo(freeClassPlan2);
        freeClassPlan1.setId(null);
        assertThat(freeClassPlan1).isNotEqualTo(freeClassPlan2);
    }
}
