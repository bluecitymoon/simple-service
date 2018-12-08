package com.pure.service.web.rest;

import com.pure.service.SimpleServiceApp;

import com.pure.service.domain.StudentClassLogDailyReport;
import com.pure.service.repository.StudentClassLogDailyReportRepository;
import com.pure.service.service.StudentClassLogDailyReportService;
import com.pure.service.web.rest.errors.ExceptionTranslator;
import com.pure.service.service.dto.StudentClassLogDailyReportCriteria;
import com.pure.service.service.StudentClassLogDailyReportQueryService;

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
 * Test class for the StudentClassLogDailyReportResource REST controller.
 *
 * @see StudentClassLogDailyReportResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SimpleServiceApp.class)
public class StudentClassLogDailyReportResourceIntTest {

    private static final Integer DEFAULT_SHOULD_TAKEN = 1;
    private static final Integer UPDATED_SHOULD_TAKEN = 2;

    private static final Integer DEFAULT_LEAVE = 1;
    private static final Integer UPDATED_LEAVE = 2;

    private static final Integer DEFAULT_ABSENCE = 1;
    private static final Integer UPDATED_ABSENCE = 2;

    private static final Integer DEFAULT_ADDED = 1;
    private static final Integer UPDATED_ADDED = 2;

    private static final Integer DEFAULT_ACTUAL_TAKEN = 1;
    private static final Integer UPDATED_ACTUAL_TAKEN = 2;

    private static final Instant DEFAULT_LOG_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LOG_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private StudentClassLogDailyReportRepository studentClassLogDailyReportRepository;

    @Autowired
    private StudentClassLogDailyReportService studentClassLogDailyReportService;

    @Autowired
    private StudentClassLogDailyReportQueryService studentClassLogDailyReportQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restStudentClassLogDailyReportMockMvc;

    private StudentClassLogDailyReport studentClassLogDailyReport;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final StudentClassLogDailyReportResource studentClassLogDailyReportResource = new StudentClassLogDailyReportResource(studentClassLogDailyReportService, studentClassLogDailyReportQueryService);
        this.restStudentClassLogDailyReportMockMvc = MockMvcBuilders.standaloneSetup(studentClassLogDailyReportResource)
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
    public static StudentClassLogDailyReport createEntity(EntityManager em) {
        StudentClassLogDailyReport studentClassLogDailyReport = new StudentClassLogDailyReport()
            .shouldTaken(DEFAULT_SHOULD_TAKEN)
            .leave(DEFAULT_LEAVE)
            .absence(DEFAULT_ABSENCE)
            .added(DEFAULT_ADDED)
            .actualTaken(DEFAULT_ACTUAL_TAKEN)
            .logDate(DEFAULT_LOG_DATE);
        return studentClassLogDailyReport;
    }

    @Before
    public void initTest() {
        studentClassLogDailyReport = createEntity(em);
    }

    @Test
    @Transactional
    public void createStudentClassLogDailyReport() throws Exception {
        int databaseSizeBeforeCreate = studentClassLogDailyReportRepository.findAll().size();

        // Create the StudentClassLogDailyReport
        restStudentClassLogDailyReportMockMvc.perform(post("/api/student-class-log-daily-reports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentClassLogDailyReport)))
            .andExpect(status().isCreated());

        // Validate the StudentClassLogDailyReport in the database
        List<StudentClassLogDailyReport> studentClassLogDailyReportList = studentClassLogDailyReportRepository.findAll();
        assertThat(studentClassLogDailyReportList).hasSize(databaseSizeBeforeCreate + 1);
        StudentClassLogDailyReport testStudentClassLogDailyReport = studentClassLogDailyReportList.get(studentClassLogDailyReportList.size() - 1);
        assertThat(testStudentClassLogDailyReport.getShouldTaken()).isEqualTo(DEFAULT_SHOULD_TAKEN);
        assertThat(testStudentClassLogDailyReport.getLeave()).isEqualTo(DEFAULT_LEAVE);
        assertThat(testStudentClassLogDailyReport.getAbsence()).isEqualTo(DEFAULT_ABSENCE);
        assertThat(testStudentClassLogDailyReport.getAdded()).isEqualTo(DEFAULT_ADDED);
        assertThat(testStudentClassLogDailyReport.getActualTaken()).isEqualTo(DEFAULT_ACTUAL_TAKEN);
        assertThat(testStudentClassLogDailyReport.getLogDate()).isEqualTo(DEFAULT_LOG_DATE);
    }

    @Test
    @Transactional
    public void createStudentClassLogDailyReportWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = studentClassLogDailyReportRepository.findAll().size();

        // Create the StudentClassLogDailyReport with an existing ID
        studentClassLogDailyReport.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStudentClassLogDailyReportMockMvc.perform(post("/api/student-class-log-daily-reports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentClassLogDailyReport)))
            .andExpect(status().isBadRequest());

        // Validate the StudentClassLogDailyReport in the database
        List<StudentClassLogDailyReport> studentClassLogDailyReportList = studentClassLogDailyReportRepository.findAll();
        assertThat(studentClassLogDailyReportList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllStudentClassLogDailyReports() throws Exception {
        // Initialize the database
        studentClassLogDailyReportRepository.saveAndFlush(studentClassLogDailyReport);

        // Get all the studentClassLogDailyReportList
        restStudentClassLogDailyReportMockMvc.perform(get("/api/student-class-log-daily-reports?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(studentClassLogDailyReport.getId().intValue())))
            .andExpect(jsonPath("$.[*].shouldTaken").value(hasItem(DEFAULT_SHOULD_TAKEN)))
            .andExpect(jsonPath("$.[*].leave").value(hasItem(DEFAULT_LEAVE)))
            .andExpect(jsonPath("$.[*].absence").value(hasItem(DEFAULT_ABSENCE)))
            .andExpect(jsonPath("$.[*].added").value(hasItem(DEFAULT_ADDED)))
            .andExpect(jsonPath("$.[*].actualTaken").value(hasItem(DEFAULT_ACTUAL_TAKEN)))
            .andExpect(jsonPath("$.[*].logDate").value(hasItem(DEFAULT_LOG_DATE.toString())));
    }

    @Test
    @Transactional
    public void getStudentClassLogDailyReport() throws Exception {
        // Initialize the database
        studentClassLogDailyReportRepository.saveAndFlush(studentClassLogDailyReport);

        // Get the studentClassLogDailyReport
        restStudentClassLogDailyReportMockMvc.perform(get("/api/student-class-log-daily-reports/{id}", studentClassLogDailyReport.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(studentClassLogDailyReport.getId().intValue()))
            .andExpect(jsonPath("$.shouldTaken").value(DEFAULT_SHOULD_TAKEN))
            .andExpect(jsonPath("$.leave").value(DEFAULT_LEAVE))
            .andExpect(jsonPath("$.absence").value(DEFAULT_ABSENCE))
            .andExpect(jsonPath("$.added").value(DEFAULT_ADDED))
            .andExpect(jsonPath("$.actualTaken").value(DEFAULT_ACTUAL_TAKEN))
            .andExpect(jsonPath("$.logDate").value(DEFAULT_LOG_DATE.toString()));
    }

    @Test
    @Transactional
    public void getAllStudentClassLogDailyReportsByShouldTakenIsEqualToSomething() throws Exception {
        // Initialize the database
        studentClassLogDailyReportRepository.saveAndFlush(studentClassLogDailyReport);

        // Get all the studentClassLogDailyReportList where shouldTaken equals to DEFAULT_SHOULD_TAKEN
        defaultStudentClassLogDailyReportShouldBeFound("shouldTaken.equals=" + DEFAULT_SHOULD_TAKEN);

        // Get all the studentClassLogDailyReportList where shouldTaken equals to UPDATED_SHOULD_TAKEN
        defaultStudentClassLogDailyReportShouldNotBeFound("shouldTaken.equals=" + UPDATED_SHOULD_TAKEN);
    }

    @Test
    @Transactional
    public void getAllStudentClassLogDailyReportsByShouldTakenIsInShouldWork() throws Exception {
        // Initialize the database
        studentClassLogDailyReportRepository.saveAndFlush(studentClassLogDailyReport);

        // Get all the studentClassLogDailyReportList where shouldTaken in DEFAULT_SHOULD_TAKEN or UPDATED_SHOULD_TAKEN
        defaultStudentClassLogDailyReportShouldBeFound("shouldTaken.in=" + DEFAULT_SHOULD_TAKEN + "," + UPDATED_SHOULD_TAKEN);

        // Get all the studentClassLogDailyReportList where shouldTaken equals to UPDATED_SHOULD_TAKEN
        defaultStudentClassLogDailyReportShouldNotBeFound("shouldTaken.in=" + UPDATED_SHOULD_TAKEN);
    }

    @Test
    @Transactional
    public void getAllStudentClassLogDailyReportsByShouldTakenIsNullOrNotNull() throws Exception {
        // Initialize the database
        studentClassLogDailyReportRepository.saveAndFlush(studentClassLogDailyReport);

        // Get all the studentClassLogDailyReportList where shouldTaken is not null
        defaultStudentClassLogDailyReportShouldBeFound("shouldTaken.specified=true");

        // Get all the studentClassLogDailyReportList where shouldTaken is null
        defaultStudentClassLogDailyReportShouldNotBeFound("shouldTaken.specified=false");
    }

    @Test
    @Transactional
    public void getAllStudentClassLogDailyReportsByShouldTakenIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        studentClassLogDailyReportRepository.saveAndFlush(studentClassLogDailyReport);

        // Get all the studentClassLogDailyReportList where shouldTaken greater than or equals to DEFAULT_SHOULD_TAKEN
        defaultStudentClassLogDailyReportShouldBeFound("shouldTaken.greaterOrEqualThan=" + DEFAULT_SHOULD_TAKEN);

        // Get all the studentClassLogDailyReportList where shouldTaken greater than or equals to UPDATED_SHOULD_TAKEN
        defaultStudentClassLogDailyReportShouldNotBeFound("shouldTaken.greaterOrEqualThan=" + UPDATED_SHOULD_TAKEN);
    }

    @Test
    @Transactional
    public void getAllStudentClassLogDailyReportsByShouldTakenIsLessThanSomething() throws Exception {
        // Initialize the database
        studentClassLogDailyReportRepository.saveAndFlush(studentClassLogDailyReport);

        // Get all the studentClassLogDailyReportList where shouldTaken less than or equals to DEFAULT_SHOULD_TAKEN
        defaultStudentClassLogDailyReportShouldNotBeFound("shouldTaken.lessThan=" + DEFAULT_SHOULD_TAKEN);

        // Get all the studentClassLogDailyReportList where shouldTaken less than or equals to UPDATED_SHOULD_TAKEN
        defaultStudentClassLogDailyReportShouldBeFound("shouldTaken.lessThan=" + UPDATED_SHOULD_TAKEN);
    }


    @Test
    @Transactional
    public void getAllStudentClassLogDailyReportsByLeaveIsEqualToSomething() throws Exception {
        // Initialize the database
        studentClassLogDailyReportRepository.saveAndFlush(studentClassLogDailyReport);

        // Get all the studentClassLogDailyReportList where leave equals to DEFAULT_LEAVE
        defaultStudentClassLogDailyReportShouldBeFound("leave.equals=" + DEFAULT_LEAVE);

        // Get all the studentClassLogDailyReportList where leave equals to UPDATED_LEAVE
        defaultStudentClassLogDailyReportShouldNotBeFound("leave.equals=" + UPDATED_LEAVE);
    }

    @Test
    @Transactional
    public void getAllStudentClassLogDailyReportsByLeaveIsInShouldWork() throws Exception {
        // Initialize the database
        studentClassLogDailyReportRepository.saveAndFlush(studentClassLogDailyReport);

        // Get all the studentClassLogDailyReportList where leave in DEFAULT_LEAVE or UPDATED_LEAVE
        defaultStudentClassLogDailyReportShouldBeFound("leave.in=" + DEFAULT_LEAVE + "," + UPDATED_LEAVE);

        // Get all the studentClassLogDailyReportList where leave equals to UPDATED_LEAVE
        defaultStudentClassLogDailyReportShouldNotBeFound("leave.in=" + UPDATED_LEAVE);
    }

    @Test
    @Transactional
    public void getAllStudentClassLogDailyReportsByLeaveIsNullOrNotNull() throws Exception {
        // Initialize the database
        studentClassLogDailyReportRepository.saveAndFlush(studentClassLogDailyReport);

        // Get all the studentClassLogDailyReportList where leave is not null
        defaultStudentClassLogDailyReportShouldBeFound("leave.specified=true");

        // Get all the studentClassLogDailyReportList where leave is null
        defaultStudentClassLogDailyReportShouldNotBeFound("leave.specified=false");
    }

    @Test
    @Transactional
    public void getAllStudentClassLogDailyReportsByLeaveIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        studentClassLogDailyReportRepository.saveAndFlush(studentClassLogDailyReport);

        // Get all the studentClassLogDailyReportList where leave greater than or equals to DEFAULT_LEAVE
        defaultStudentClassLogDailyReportShouldBeFound("leave.greaterOrEqualThan=" + DEFAULT_LEAVE);

        // Get all the studentClassLogDailyReportList where leave greater than or equals to UPDATED_LEAVE
        defaultStudentClassLogDailyReportShouldNotBeFound("leave.greaterOrEqualThan=" + UPDATED_LEAVE);
    }

    @Test
    @Transactional
    public void getAllStudentClassLogDailyReportsByLeaveIsLessThanSomething() throws Exception {
        // Initialize the database
        studentClassLogDailyReportRepository.saveAndFlush(studentClassLogDailyReport);

        // Get all the studentClassLogDailyReportList where leave less than or equals to DEFAULT_LEAVE
        defaultStudentClassLogDailyReportShouldNotBeFound("leave.lessThan=" + DEFAULT_LEAVE);

        // Get all the studentClassLogDailyReportList where leave less than or equals to UPDATED_LEAVE
        defaultStudentClassLogDailyReportShouldBeFound("leave.lessThan=" + UPDATED_LEAVE);
    }


    @Test
    @Transactional
    public void getAllStudentClassLogDailyReportsByAbsenceIsEqualToSomething() throws Exception {
        // Initialize the database
        studentClassLogDailyReportRepository.saveAndFlush(studentClassLogDailyReport);

        // Get all the studentClassLogDailyReportList where absence equals to DEFAULT_ABSENCE
        defaultStudentClassLogDailyReportShouldBeFound("absence.equals=" + DEFAULT_ABSENCE);

        // Get all the studentClassLogDailyReportList where absence equals to UPDATED_ABSENCE
        defaultStudentClassLogDailyReportShouldNotBeFound("absence.equals=" + UPDATED_ABSENCE);
    }

    @Test
    @Transactional
    public void getAllStudentClassLogDailyReportsByAbsenceIsInShouldWork() throws Exception {
        // Initialize the database
        studentClassLogDailyReportRepository.saveAndFlush(studentClassLogDailyReport);

        // Get all the studentClassLogDailyReportList where absence in DEFAULT_ABSENCE or UPDATED_ABSENCE
        defaultStudentClassLogDailyReportShouldBeFound("absence.in=" + DEFAULT_ABSENCE + "," + UPDATED_ABSENCE);

        // Get all the studentClassLogDailyReportList where absence equals to UPDATED_ABSENCE
        defaultStudentClassLogDailyReportShouldNotBeFound("absence.in=" + UPDATED_ABSENCE);
    }

    @Test
    @Transactional
    public void getAllStudentClassLogDailyReportsByAbsenceIsNullOrNotNull() throws Exception {
        // Initialize the database
        studentClassLogDailyReportRepository.saveAndFlush(studentClassLogDailyReport);

        // Get all the studentClassLogDailyReportList where absence is not null
        defaultStudentClassLogDailyReportShouldBeFound("absence.specified=true");

        // Get all the studentClassLogDailyReportList where absence is null
        defaultStudentClassLogDailyReportShouldNotBeFound("absence.specified=false");
    }

    @Test
    @Transactional
    public void getAllStudentClassLogDailyReportsByAbsenceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        studentClassLogDailyReportRepository.saveAndFlush(studentClassLogDailyReport);

        // Get all the studentClassLogDailyReportList where absence greater than or equals to DEFAULT_ABSENCE
        defaultStudentClassLogDailyReportShouldBeFound("absence.greaterOrEqualThan=" + DEFAULT_ABSENCE);

        // Get all the studentClassLogDailyReportList where absence greater than or equals to UPDATED_ABSENCE
        defaultStudentClassLogDailyReportShouldNotBeFound("absence.greaterOrEqualThan=" + UPDATED_ABSENCE);
    }

    @Test
    @Transactional
    public void getAllStudentClassLogDailyReportsByAbsenceIsLessThanSomething() throws Exception {
        // Initialize the database
        studentClassLogDailyReportRepository.saveAndFlush(studentClassLogDailyReport);

        // Get all the studentClassLogDailyReportList where absence less than or equals to DEFAULT_ABSENCE
        defaultStudentClassLogDailyReportShouldNotBeFound("absence.lessThan=" + DEFAULT_ABSENCE);

        // Get all the studentClassLogDailyReportList where absence less than or equals to UPDATED_ABSENCE
        defaultStudentClassLogDailyReportShouldBeFound("absence.lessThan=" + UPDATED_ABSENCE);
    }


    @Test
    @Transactional
    public void getAllStudentClassLogDailyReportsByAddedIsEqualToSomething() throws Exception {
        // Initialize the database
        studentClassLogDailyReportRepository.saveAndFlush(studentClassLogDailyReport);

        // Get all the studentClassLogDailyReportList where added equals to DEFAULT_ADDED
        defaultStudentClassLogDailyReportShouldBeFound("added.equals=" + DEFAULT_ADDED);

        // Get all the studentClassLogDailyReportList where added equals to UPDATED_ADDED
        defaultStudentClassLogDailyReportShouldNotBeFound("added.equals=" + UPDATED_ADDED);
    }

    @Test
    @Transactional
    public void getAllStudentClassLogDailyReportsByAddedIsInShouldWork() throws Exception {
        // Initialize the database
        studentClassLogDailyReportRepository.saveAndFlush(studentClassLogDailyReport);

        // Get all the studentClassLogDailyReportList where added in DEFAULT_ADDED or UPDATED_ADDED
        defaultStudentClassLogDailyReportShouldBeFound("added.in=" + DEFAULT_ADDED + "," + UPDATED_ADDED);

        // Get all the studentClassLogDailyReportList where added equals to UPDATED_ADDED
        defaultStudentClassLogDailyReportShouldNotBeFound("added.in=" + UPDATED_ADDED);
    }

    @Test
    @Transactional
    public void getAllStudentClassLogDailyReportsByAddedIsNullOrNotNull() throws Exception {
        // Initialize the database
        studentClassLogDailyReportRepository.saveAndFlush(studentClassLogDailyReport);

        // Get all the studentClassLogDailyReportList where added is not null
        defaultStudentClassLogDailyReportShouldBeFound("added.specified=true");

        // Get all the studentClassLogDailyReportList where added is null
        defaultStudentClassLogDailyReportShouldNotBeFound("added.specified=false");
    }

    @Test
    @Transactional
    public void getAllStudentClassLogDailyReportsByAddedIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        studentClassLogDailyReportRepository.saveAndFlush(studentClassLogDailyReport);

        // Get all the studentClassLogDailyReportList where added greater than or equals to DEFAULT_ADDED
        defaultStudentClassLogDailyReportShouldBeFound("added.greaterOrEqualThan=" + DEFAULT_ADDED);

        // Get all the studentClassLogDailyReportList where added greater than or equals to UPDATED_ADDED
        defaultStudentClassLogDailyReportShouldNotBeFound("added.greaterOrEqualThan=" + UPDATED_ADDED);
    }

    @Test
    @Transactional
    public void getAllStudentClassLogDailyReportsByAddedIsLessThanSomething() throws Exception {
        // Initialize the database
        studentClassLogDailyReportRepository.saveAndFlush(studentClassLogDailyReport);

        // Get all the studentClassLogDailyReportList where added less than or equals to DEFAULT_ADDED
        defaultStudentClassLogDailyReportShouldNotBeFound("added.lessThan=" + DEFAULT_ADDED);

        // Get all the studentClassLogDailyReportList where added less than or equals to UPDATED_ADDED
        defaultStudentClassLogDailyReportShouldBeFound("added.lessThan=" + UPDATED_ADDED);
    }


    @Test
    @Transactional
    public void getAllStudentClassLogDailyReportsByActualTakenIsEqualToSomething() throws Exception {
        // Initialize the database
        studentClassLogDailyReportRepository.saveAndFlush(studentClassLogDailyReport);

        // Get all the studentClassLogDailyReportList where actualTaken equals to DEFAULT_ACTUAL_TAKEN
        defaultStudentClassLogDailyReportShouldBeFound("actualTaken.equals=" + DEFAULT_ACTUAL_TAKEN);

        // Get all the studentClassLogDailyReportList where actualTaken equals to UPDATED_ACTUAL_TAKEN
        defaultStudentClassLogDailyReportShouldNotBeFound("actualTaken.equals=" + UPDATED_ACTUAL_TAKEN);
    }

    @Test
    @Transactional
    public void getAllStudentClassLogDailyReportsByActualTakenIsInShouldWork() throws Exception {
        // Initialize the database
        studentClassLogDailyReportRepository.saveAndFlush(studentClassLogDailyReport);

        // Get all the studentClassLogDailyReportList where actualTaken in DEFAULT_ACTUAL_TAKEN or UPDATED_ACTUAL_TAKEN
        defaultStudentClassLogDailyReportShouldBeFound("actualTaken.in=" + DEFAULT_ACTUAL_TAKEN + "," + UPDATED_ACTUAL_TAKEN);

        // Get all the studentClassLogDailyReportList where actualTaken equals to UPDATED_ACTUAL_TAKEN
        defaultStudentClassLogDailyReportShouldNotBeFound("actualTaken.in=" + UPDATED_ACTUAL_TAKEN);
    }

    @Test
    @Transactional
    public void getAllStudentClassLogDailyReportsByActualTakenIsNullOrNotNull() throws Exception {
        // Initialize the database
        studentClassLogDailyReportRepository.saveAndFlush(studentClassLogDailyReport);

        // Get all the studentClassLogDailyReportList where actualTaken is not null
        defaultStudentClassLogDailyReportShouldBeFound("actualTaken.specified=true");

        // Get all the studentClassLogDailyReportList where actualTaken is null
        defaultStudentClassLogDailyReportShouldNotBeFound("actualTaken.specified=false");
    }

    @Test
    @Transactional
    public void getAllStudentClassLogDailyReportsByActualTakenIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        studentClassLogDailyReportRepository.saveAndFlush(studentClassLogDailyReport);

        // Get all the studentClassLogDailyReportList where actualTaken greater than or equals to DEFAULT_ACTUAL_TAKEN
        defaultStudentClassLogDailyReportShouldBeFound("actualTaken.greaterOrEqualThan=" + DEFAULT_ACTUAL_TAKEN);

        // Get all the studentClassLogDailyReportList where actualTaken greater than or equals to UPDATED_ACTUAL_TAKEN
        defaultStudentClassLogDailyReportShouldNotBeFound("actualTaken.greaterOrEqualThan=" + UPDATED_ACTUAL_TAKEN);
    }

    @Test
    @Transactional
    public void getAllStudentClassLogDailyReportsByActualTakenIsLessThanSomething() throws Exception {
        // Initialize the database
        studentClassLogDailyReportRepository.saveAndFlush(studentClassLogDailyReport);

        // Get all the studentClassLogDailyReportList where actualTaken less than or equals to DEFAULT_ACTUAL_TAKEN
        defaultStudentClassLogDailyReportShouldNotBeFound("actualTaken.lessThan=" + DEFAULT_ACTUAL_TAKEN);

        // Get all the studentClassLogDailyReportList where actualTaken less than or equals to UPDATED_ACTUAL_TAKEN
        defaultStudentClassLogDailyReportShouldBeFound("actualTaken.lessThan=" + UPDATED_ACTUAL_TAKEN);
    }


    @Test
    @Transactional
    public void getAllStudentClassLogDailyReportsByLogDateIsEqualToSomething() throws Exception {
        // Initialize the database
        studentClassLogDailyReportRepository.saveAndFlush(studentClassLogDailyReport);

        // Get all the studentClassLogDailyReportList where logDate equals to DEFAULT_LOG_DATE
        defaultStudentClassLogDailyReportShouldBeFound("logDate.equals=" + DEFAULT_LOG_DATE);

        // Get all the studentClassLogDailyReportList where logDate equals to UPDATED_LOG_DATE
        defaultStudentClassLogDailyReportShouldNotBeFound("logDate.equals=" + UPDATED_LOG_DATE);
    }

    @Test
    @Transactional
    public void getAllStudentClassLogDailyReportsByLogDateIsInShouldWork() throws Exception {
        // Initialize the database
        studentClassLogDailyReportRepository.saveAndFlush(studentClassLogDailyReport);

        // Get all the studentClassLogDailyReportList where logDate in DEFAULT_LOG_DATE or UPDATED_LOG_DATE
        defaultStudentClassLogDailyReportShouldBeFound("logDate.in=" + DEFAULT_LOG_DATE + "," + UPDATED_LOG_DATE);

        // Get all the studentClassLogDailyReportList where logDate equals to UPDATED_LOG_DATE
        defaultStudentClassLogDailyReportShouldNotBeFound("logDate.in=" + UPDATED_LOG_DATE);
    }

    @Test
    @Transactional
    public void getAllStudentClassLogDailyReportsByLogDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        studentClassLogDailyReportRepository.saveAndFlush(studentClassLogDailyReport);

        // Get all the studentClassLogDailyReportList where logDate is not null
        defaultStudentClassLogDailyReportShouldBeFound("logDate.specified=true");

        // Get all the studentClassLogDailyReportList where logDate is null
        defaultStudentClassLogDailyReportShouldNotBeFound("logDate.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultStudentClassLogDailyReportShouldBeFound(String filter) throws Exception {
        restStudentClassLogDailyReportMockMvc.perform(get("/api/student-class-log-daily-reports?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(studentClassLogDailyReport.getId().intValue())))
            .andExpect(jsonPath("$.[*].shouldTaken").value(hasItem(DEFAULT_SHOULD_TAKEN)))
            .andExpect(jsonPath("$.[*].leave").value(hasItem(DEFAULT_LEAVE)))
            .andExpect(jsonPath("$.[*].absence").value(hasItem(DEFAULT_ABSENCE)))
            .andExpect(jsonPath("$.[*].added").value(hasItem(DEFAULT_ADDED)))
            .andExpect(jsonPath("$.[*].actualTaken").value(hasItem(DEFAULT_ACTUAL_TAKEN)))
            .andExpect(jsonPath("$.[*].logDate").value(hasItem(DEFAULT_LOG_DATE.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultStudentClassLogDailyReportShouldNotBeFound(String filter) throws Exception {
        restStudentClassLogDailyReportMockMvc.perform(get("/api/student-class-log-daily-reports?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingStudentClassLogDailyReport() throws Exception {
        // Get the studentClassLogDailyReport
        restStudentClassLogDailyReportMockMvc.perform(get("/api/student-class-log-daily-reports/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStudentClassLogDailyReport() throws Exception {
        // Initialize the database
        studentClassLogDailyReportService.save(studentClassLogDailyReport);

        int databaseSizeBeforeUpdate = studentClassLogDailyReportRepository.findAll().size();

        // Update the studentClassLogDailyReport
        StudentClassLogDailyReport updatedStudentClassLogDailyReport = studentClassLogDailyReportRepository.findOne(studentClassLogDailyReport.getId());
        updatedStudentClassLogDailyReport
            .shouldTaken(UPDATED_SHOULD_TAKEN)
            .leave(UPDATED_LEAVE)
            .absence(UPDATED_ABSENCE)
            .added(UPDATED_ADDED)
            .actualTaken(UPDATED_ACTUAL_TAKEN)
            .logDate(UPDATED_LOG_DATE);

        restStudentClassLogDailyReportMockMvc.perform(put("/api/student-class-log-daily-reports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedStudentClassLogDailyReport)))
            .andExpect(status().isOk());

        // Validate the StudentClassLogDailyReport in the database
        List<StudentClassLogDailyReport> studentClassLogDailyReportList = studentClassLogDailyReportRepository.findAll();
        assertThat(studentClassLogDailyReportList).hasSize(databaseSizeBeforeUpdate);
        StudentClassLogDailyReport testStudentClassLogDailyReport = studentClassLogDailyReportList.get(studentClassLogDailyReportList.size() - 1);
        assertThat(testStudentClassLogDailyReport.getShouldTaken()).isEqualTo(UPDATED_SHOULD_TAKEN);
        assertThat(testStudentClassLogDailyReport.getLeave()).isEqualTo(UPDATED_LEAVE);
        assertThat(testStudentClassLogDailyReport.getAbsence()).isEqualTo(UPDATED_ABSENCE);
        assertThat(testStudentClassLogDailyReport.getAdded()).isEqualTo(UPDATED_ADDED);
        assertThat(testStudentClassLogDailyReport.getActualTaken()).isEqualTo(UPDATED_ACTUAL_TAKEN);
        assertThat(testStudentClassLogDailyReport.getLogDate()).isEqualTo(UPDATED_LOG_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingStudentClassLogDailyReport() throws Exception {
        int databaseSizeBeforeUpdate = studentClassLogDailyReportRepository.findAll().size();

        // Create the StudentClassLogDailyReport

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restStudentClassLogDailyReportMockMvc.perform(put("/api/student-class-log-daily-reports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentClassLogDailyReport)))
            .andExpect(status().isCreated());

        // Validate the StudentClassLogDailyReport in the database
        List<StudentClassLogDailyReport> studentClassLogDailyReportList = studentClassLogDailyReportRepository.findAll();
        assertThat(studentClassLogDailyReportList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteStudentClassLogDailyReport() throws Exception {
        // Initialize the database
        studentClassLogDailyReportService.save(studentClassLogDailyReport);

        int databaseSizeBeforeDelete = studentClassLogDailyReportRepository.findAll().size();

        // Get the studentClassLogDailyReport
        restStudentClassLogDailyReportMockMvc.perform(delete("/api/student-class-log-daily-reports/{id}", studentClassLogDailyReport.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<StudentClassLogDailyReport> studentClassLogDailyReportList = studentClassLogDailyReportRepository.findAll();
        assertThat(studentClassLogDailyReportList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StudentClassLogDailyReport.class);
        StudentClassLogDailyReport studentClassLogDailyReport1 = new StudentClassLogDailyReport();
        studentClassLogDailyReport1.setId(1L);
        StudentClassLogDailyReport studentClassLogDailyReport2 = new StudentClassLogDailyReport();
        studentClassLogDailyReport2.setId(studentClassLogDailyReport1.getId());
        assertThat(studentClassLogDailyReport1).isEqualTo(studentClassLogDailyReport2);
        studentClassLogDailyReport2.setId(2L);
        assertThat(studentClassLogDailyReport1).isNotEqualTo(studentClassLogDailyReport2);
        studentClassLogDailyReport1.setId(null);
        assertThat(studentClassLogDailyReport1).isNotEqualTo(studentClassLogDailyReport2);
    }
}
