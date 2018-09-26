package com.pure.service.web.rest;

import com.pure.service.SimpleServiceApp;

import com.pure.service.domain.StudentLeave;
import com.pure.service.repository.StudentLeaveRepository;
import com.pure.service.service.StudentLeaveService;
import com.pure.service.web.rest.errors.ExceptionTranslator;
import com.pure.service.service.dto.StudentLeaveCriteria;
import com.pure.service.service.StudentLeaveQueryService;

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
 * Test class for the StudentLeaveResource REST controller.
 *
 * @see StudentLeaveResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SimpleServiceApp.class)
public class StudentLeaveResourceIntTest {

    private static final Integer DEFAULT_BALANCE = 1;
    private static final Integer UPDATED_BALANCE = 2;

    private static final Instant DEFAULT_CALCULATE_START_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CALCULATE_START_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_CALCULATE_END_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CALCULATE_END_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private StudentLeaveRepository studentLeaveRepository;

    @Autowired
    private StudentLeaveService studentLeaveService;

    @Autowired
    private StudentLeaveQueryService studentLeaveQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restStudentLeaveMockMvc;

    private StudentLeave studentLeave;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final StudentLeaveResource studentLeaveResource = new StudentLeaveResource(studentLeaveService, studentLeaveQueryService);
        this.restStudentLeaveMockMvc = MockMvcBuilders.standaloneSetup(studentLeaveResource)
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
    public static StudentLeave createEntity(EntityManager em) {
        StudentLeave studentLeave = new StudentLeave()
            .balance(DEFAULT_BALANCE)
            .calculateStartDate(DEFAULT_CALCULATE_START_DATE)
            .calculateEndDate(DEFAULT_CALCULATE_END_DATE)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return studentLeave;
    }

    @Before
    public void initTest() {
        studentLeave = createEntity(em);
    }

    @Test
    @Transactional
    public void createStudentLeave() throws Exception {
        int databaseSizeBeforeCreate = studentLeaveRepository.findAll().size();

        // Create the StudentLeave
        restStudentLeaveMockMvc.perform(post("/api/student-leaves")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentLeave)))
            .andExpect(status().isCreated());

        // Validate the StudentLeave in the database
        List<StudentLeave> studentLeaveList = studentLeaveRepository.findAll();
        assertThat(studentLeaveList).hasSize(databaseSizeBeforeCreate + 1);
        StudentLeave testStudentLeave = studentLeaveList.get(studentLeaveList.size() - 1);
        assertThat(testStudentLeave.getBalance()).isEqualTo(DEFAULT_BALANCE);
        assertThat(testStudentLeave.getCalculateStartDate()).isEqualTo(DEFAULT_CALCULATE_START_DATE);
        assertThat(testStudentLeave.getCalculateEndDate()).isEqualTo(DEFAULT_CALCULATE_END_DATE);
        assertThat(testStudentLeave.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testStudentLeave.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testStudentLeave.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testStudentLeave.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createStudentLeaveWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = studentLeaveRepository.findAll().size();

        // Create the StudentLeave with an existing ID
        studentLeave.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStudentLeaveMockMvc.perform(post("/api/student-leaves")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentLeave)))
            .andExpect(status().isBadRequest());

        // Validate the StudentLeave in the database
        List<StudentLeave> studentLeaveList = studentLeaveRepository.findAll();
        assertThat(studentLeaveList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllStudentLeaves() throws Exception {
        // Initialize the database
        studentLeaveRepository.saveAndFlush(studentLeave);

        // Get all the studentLeaveList
        restStudentLeaveMockMvc.perform(get("/api/student-leaves?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(studentLeave.getId().intValue())))
            .andExpect(jsonPath("$.[*].balance").value(hasItem(DEFAULT_BALANCE)))
            .andExpect(jsonPath("$.[*].calculateStartDate").value(hasItem(DEFAULT_CALCULATE_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].calculateEndDate").value(hasItem(DEFAULT_CALCULATE_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    public void getStudentLeave() throws Exception {
        // Initialize the database
        studentLeaveRepository.saveAndFlush(studentLeave);

        // Get the studentLeave
        restStudentLeaveMockMvc.perform(get("/api/student-leaves/{id}", studentLeave.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(studentLeave.getId().intValue()))
            .andExpect(jsonPath("$.balance").value(DEFAULT_BALANCE))
            .andExpect(jsonPath("$.calculateStartDate").value(DEFAULT_CALCULATE_START_DATE.toString()))
            .andExpect(jsonPath("$.calculateEndDate").value(DEFAULT_CALCULATE_END_DATE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    public void getAllStudentLeavesByBalanceIsEqualToSomething() throws Exception {
        // Initialize the database
        studentLeaveRepository.saveAndFlush(studentLeave);

        // Get all the studentLeaveList where balance equals to DEFAULT_BALANCE
        defaultStudentLeaveShouldBeFound("balance.equals=" + DEFAULT_BALANCE);

        // Get all the studentLeaveList where balance equals to UPDATED_BALANCE
        defaultStudentLeaveShouldNotBeFound("balance.equals=" + UPDATED_BALANCE);
    }

    @Test
    @Transactional
    public void getAllStudentLeavesByBalanceIsInShouldWork() throws Exception {
        // Initialize the database
        studentLeaveRepository.saveAndFlush(studentLeave);

        // Get all the studentLeaveList where balance in DEFAULT_BALANCE or UPDATED_BALANCE
        defaultStudentLeaveShouldBeFound("balance.in=" + DEFAULT_BALANCE + "," + UPDATED_BALANCE);

        // Get all the studentLeaveList where balance equals to UPDATED_BALANCE
        defaultStudentLeaveShouldNotBeFound("balance.in=" + UPDATED_BALANCE);
    }

    @Test
    @Transactional
    public void getAllStudentLeavesByBalanceIsNullOrNotNull() throws Exception {
        // Initialize the database
        studentLeaveRepository.saveAndFlush(studentLeave);

        // Get all the studentLeaveList where balance is not null
        defaultStudentLeaveShouldBeFound("balance.specified=true");

        // Get all the studentLeaveList where balance is null
        defaultStudentLeaveShouldNotBeFound("balance.specified=false");
    }

    @Test
    @Transactional
    public void getAllStudentLeavesByBalanceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        studentLeaveRepository.saveAndFlush(studentLeave);

        // Get all the studentLeaveList where balance greater than or equals to DEFAULT_BALANCE
        defaultStudentLeaveShouldBeFound("balance.greaterOrEqualThan=" + DEFAULT_BALANCE);

        // Get all the studentLeaveList where balance greater than or equals to UPDATED_BALANCE
        defaultStudentLeaveShouldNotBeFound("balance.greaterOrEqualThan=" + UPDATED_BALANCE);
    }

    @Test
    @Transactional
    public void getAllStudentLeavesByBalanceIsLessThanSomething() throws Exception {
        // Initialize the database
        studentLeaveRepository.saveAndFlush(studentLeave);

        // Get all the studentLeaveList where balance less than or equals to DEFAULT_BALANCE
        defaultStudentLeaveShouldNotBeFound("balance.lessThan=" + DEFAULT_BALANCE);

        // Get all the studentLeaveList where balance less than or equals to UPDATED_BALANCE
        defaultStudentLeaveShouldBeFound("balance.lessThan=" + UPDATED_BALANCE);
    }


    @Test
    @Transactional
    public void getAllStudentLeavesByCalculateStartDateIsEqualToSomething() throws Exception {
        // Initialize the database
        studentLeaveRepository.saveAndFlush(studentLeave);

        // Get all the studentLeaveList where calculateStartDate equals to DEFAULT_CALCULATE_START_DATE
        defaultStudentLeaveShouldBeFound("calculateStartDate.equals=" + DEFAULT_CALCULATE_START_DATE);

        // Get all the studentLeaveList where calculateStartDate equals to UPDATED_CALCULATE_START_DATE
        defaultStudentLeaveShouldNotBeFound("calculateStartDate.equals=" + UPDATED_CALCULATE_START_DATE);
    }

    @Test
    @Transactional
    public void getAllStudentLeavesByCalculateStartDateIsInShouldWork() throws Exception {
        // Initialize the database
        studentLeaveRepository.saveAndFlush(studentLeave);

        // Get all the studentLeaveList where calculateStartDate in DEFAULT_CALCULATE_START_DATE or UPDATED_CALCULATE_START_DATE
        defaultStudentLeaveShouldBeFound("calculateStartDate.in=" + DEFAULT_CALCULATE_START_DATE + "," + UPDATED_CALCULATE_START_DATE);

        // Get all the studentLeaveList where calculateStartDate equals to UPDATED_CALCULATE_START_DATE
        defaultStudentLeaveShouldNotBeFound("calculateStartDate.in=" + UPDATED_CALCULATE_START_DATE);
    }

    @Test
    @Transactional
    public void getAllStudentLeavesByCalculateStartDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        studentLeaveRepository.saveAndFlush(studentLeave);

        // Get all the studentLeaveList where calculateStartDate is not null
        defaultStudentLeaveShouldBeFound("calculateStartDate.specified=true");

        // Get all the studentLeaveList where calculateStartDate is null
        defaultStudentLeaveShouldNotBeFound("calculateStartDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllStudentLeavesByCalculateEndDateIsEqualToSomething() throws Exception {
        // Initialize the database
        studentLeaveRepository.saveAndFlush(studentLeave);

        // Get all the studentLeaveList where calculateEndDate equals to DEFAULT_CALCULATE_END_DATE
        defaultStudentLeaveShouldBeFound("calculateEndDate.equals=" + DEFAULT_CALCULATE_END_DATE);

        // Get all the studentLeaveList where calculateEndDate equals to UPDATED_CALCULATE_END_DATE
        defaultStudentLeaveShouldNotBeFound("calculateEndDate.equals=" + UPDATED_CALCULATE_END_DATE);
    }

    @Test
    @Transactional
    public void getAllStudentLeavesByCalculateEndDateIsInShouldWork() throws Exception {
        // Initialize the database
        studentLeaveRepository.saveAndFlush(studentLeave);

        // Get all the studentLeaveList where calculateEndDate in DEFAULT_CALCULATE_END_DATE or UPDATED_CALCULATE_END_DATE
        defaultStudentLeaveShouldBeFound("calculateEndDate.in=" + DEFAULT_CALCULATE_END_DATE + "," + UPDATED_CALCULATE_END_DATE);

        // Get all the studentLeaveList where calculateEndDate equals to UPDATED_CALCULATE_END_DATE
        defaultStudentLeaveShouldNotBeFound("calculateEndDate.in=" + UPDATED_CALCULATE_END_DATE);
    }

    @Test
    @Transactional
    public void getAllStudentLeavesByCalculateEndDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        studentLeaveRepository.saveAndFlush(studentLeave);

        // Get all the studentLeaveList where calculateEndDate is not null
        defaultStudentLeaveShouldBeFound("calculateEndDate.specified=true");

        // Get all the studentLeaveList where calculateEndDate is null
        defaultStudentLeaveShouldNotBeFound("calculateEndDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllStudentLeavesByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        studentLeaveRepository.saveAndFlush(studentLeave);

        // Get all the studentLeaveList where createdBy equals to DEFAULT_CREATED_BY
        defaultStudentLeaveShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the studentLeaveList where createdBy equals to UPDATED_CREATED_BY
        defaultStudentLeaveShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllStudentLeavesByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        studentLeaveRepository.saveAndFlush(studentLeave);

        // Get all the studentLeaveList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultStudentLeaveShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the studentLeaveList where createdBy equals to UPDATED_CREATED_BY
        defaultStudentLeaveShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllStudentLeavesByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        studentLeaveRepository.saveAndFlush(studentLeave);

        // Get all the studentLeaveList where createdBy is not null
        defaultStudentLeaveShouldBeFound("createdBy.specified=true");

        // Get all the studentLeaveList where createdBy is null
        defaultStudentLeaveShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllStudentLeavesByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        studentLeaveRepository.saveAndFlush(studentLeave);

        // Get all the studentLeaveList where createdDate equals to DEFAULT_CREATED_DATE
        defaultStudentLeaveShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the studentLeaveList where createdDate equals to UPDATED_CREATED_DATE
        defaultStudentLeaveShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllStudentLeavesByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        studentLeaveRepository.saveAndFlush(studentLeave);

        // Get all the studentLeaveList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultStudentLeaveShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the studentLeaveList where createdDate equals to UPDATED_CREATED_DATE
        defaultStudentLeaveShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllStudentLeavesByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        studentLeaveRepository.saveAndFlush(studentLeave);

        // Get all the studentLeaveList where createdDate is not null
        defaultStudentLeaveShouldBeFound("createdDate.specified=true");

        // Get all the studentLeaveList where createdDate is null
        defaultStudentLeaveShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllStudentLeavesByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        studentLeaveRepository.saveAndFlush(studentLeave);

        // Get all the studentLeaveList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultStudentLeaveShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the studentLeaveList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultStudentLeaveShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllStudentLeavesByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        studentLeaveRepository.saveAndFlush(studentLeave);

        // Get all the studentLeaveList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultStudentLeaveShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the studentLeaveList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultStudentLeaveShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllStudentLeavesByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        studentLeaveRepository.saveAndFlush(studentLeave);

        // Get all the studentLeaveList where lastModifiedBy is not null
        defaultStudentLeaveShouldBeFound("lastModifiedBy.specified=true");

        // Get all the studentLeaveList where lastModifiedBy is null
        defaultStudentLeaveShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllStudentLeavesByLastModifiedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        studentLeaveRepository.saveAndFlush(studentLeave);

        // Get all the studentLeaveList where lastModifiedDate equals to DEFAULT_LAST_MODIFIED_DATE
        defaultStudentLeaveShouldBeFound("lastModifiedDate.equals=" + DEFAULT_LAST_MODIFIED_DATE);

        // Get all the studentLeaveList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultStudentLeaveShouldNotBeFound("lastModifiedDate.equals=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllStudentLeavesByLastModifiedDateIsInShouldWork() throws Exception {
        // Initialize the database
        studentLeaveRepository.saveAndFlush(studentLeave);

        // Get all the studentLeaveList where lastModifiedDate in DEFAULT_LAST_MODIFIED_DATE or UPDATED_LAST_MODIFIED_DATE
        defaultStudentLeaveShouldBeFound("lastModifiedDate.in=" + DEFAULT_LAST_MODIFIED_DATE + "," + UPDATED_LAST_MODIFIED_DATE);

        // Get all the studentLeaveList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultStudentLeaveShouldNotBeFound("lastModifiedDate.in=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllStudentLeavesByLastModifiedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        studentLeaveRepository.saveAndFlush(studentLeave);

        // Get all the studentLeaveList where lastModifiedDate is not null
        defaultStudentLeaveShouldBeFound("lastModifiedDate.specified=true");

        // Get all the studentLeaveList where lastModifiedDate is null
        defaultStudentLeaveShouldNotBeFound("lastModifiedDate.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultStudentLeaveShouldBeFound(String filter) throws Exception {
        restStudentLeaveMockMvc.perform(get("/api/student-leaves?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(studentLeave.getId().intValue())))
            .andExpect(jsonPath("$.[*].balance").value(hasItem(DEFAULT_BALANCE)))
            .andExpect(jsonPath("$.[*].calculateStartDate").value(hasItem(DEFAULT_CALCULATE_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].calculateEndDate").value(hasItem(DEFAULT_CALCULATE_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultStudentLeaveShouldNotBeFound(String filter) throws Exception {
        restStudentLeaveMockMvc.perform(get("/api/student-leaves?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingStudentLeave() throws Exception {
        // Get the studentLeave
        restStudentLeaveMockMvc.perform(get("/api/student-leaves/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStudentLeave() throws Exception {
        // Initialize the database
        studentLeaveService.save(studentLeave);

        int databaseSizeBeforeUpdate = studentLeaveRepository.findAll().size();

        // Update the studentLeave
        StudentLeave updatedStudentLeave = studentLeaveRepository.findOne(studentLeave.getId());
        updatedStudentLeave
            .balance(UPDATED_BALANCE)
            .calculateStartDate(UPDATED_CALCULATE_START_DATE)
            .calculateEndDate(UPDATED_CALCULATE_END_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restStudentLeaveMockMvc.perform(put("/api/student-leaves")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedStudentLeave)))
            .andExpect(status().isOk());

        // Validate the StudentLeave in the database
        List<StudentLeave> studentLeaveList = studentLeaveRepository.findAll();
        assertThat(studentLeaveList).hasSize(databaseSizeBeforeUpdate);
        StudentLeave testStudentLeave = studentLeaveList.get(studentLeaveList.size() - 1);
        assertThat(testStudentLeave.getBalance()).isEqualTo(UPDATED_BALANCE);
        assertThat(testStudentLeave.getCalculateStartDate()).isEqualTo(UPDATED_CALCULATE_START_DATE);
        assertThat(testStudentLeave.getCalculateEndDate()).isEqualTo(UPDATED_CALCULATE_END_DATE);
        assertThat(testStudentLeave.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testStudentLeave.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testStudentLeave.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testStudentLeave.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingStudentLeave() throws Exception {
        int databaseSizeBeforeUpdate = studentLeaveRepository.findAll().size();

        // Create the StudentLeave

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restStudentLeaveMockMvc.perform(put("/api/student-leaves")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentLeave)))
            .andExpect(status().isCreated());

        // Validate the StudentLeave in the database
        List<StudentLeave> studentLeaveList = studentLeaveRepository.findAll();
        assertThat(studentLeaveList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteStudentLeave() throws Exception {
        // Initialize the database
        studentLeaveService.save(studentLeave);

        int databaseSizeBeforeDelete = studentLeaveRepository.findAll().size();

        // Get the studentLeave
        restStudentLeaveMockMvc.perform(delete("/api/student-leaves/{id}", studentLeave.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<StudentLeave> studentLeaveList = studentLeaveRepository.findAll();
        assertThat(studentLeaveList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StudentLeave.class);
        StudentLeave studentLeave1 = new StudentLeave();
        studentLeave1.setId(1L);
        StudentLeave studentLeave2 = new StudentLeave();
        studentLeave2.setId(studentLeave1.getId());
        assertThat(studentLeave1).isEqualTo(studentLeave2);
        studentLeave2.setId(2L);
        assertThat(studentLeave1).isNotEqualTo(studentLeave2);
        studentLeave1.setId(null);
        assertThat(studentLeave1).isNotEqualTo(studentLeave2);
    }
}
