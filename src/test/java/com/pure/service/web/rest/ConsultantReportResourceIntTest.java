package com.pure.service.web.rest;

import com.pure.service.SimpleServiceApp;

import com.pure.service.domain.ConsultantReport;
import com.pure.service.repository.ConsultantReportRepository;
import com.pure.service.service.ConsultantReportService;
import com.pure.service.web.rest.errors.ExceptionTranslator;
import com.pure.service.service.dto.ConsultantReportCriteria;
import com.pure.service.service.ConsultantReportQueryService;

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
 * Test class for the ConsultantReportResource REST controller.
 *
 * @see ConsultantReportResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SimpleServiceApp.class)
public class ConsultantReportResourceIntTest {

    private static final Integer DEFAULT_WEEK_NAME = 1;
    private static final Integer UPDATED_WEEK_NAME = 2;

    private static final Instant DEFAULT_WEEK_FROM_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_WEEK_FROM_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_WEEK_END_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_WEEK_END_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_VISITED_COUNT = 1;
    private static final Integer UPDATED_VISITED_COUNT = 2;

    private static final Float DEFAULT_DEALED_MONEY_AMOUNT = 1F;
    private static final Float UPDATED_DEALED_MONEY_AMOUNT = 2F;

    @Autowired
    private ConsultantReportRepository consultantReportRepository;

    @Autowired
    private ConsultantReportService consultantReportService;

    @Autowired
    private ConsultantReportQueryService consultantReportQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restConsultantReportMockMvc;

    private ConsultantReport consultantReport;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ConsultantReportResource consultantReportResource = new ConsultantReportResource(consultantReportService, consultantReportQueryService);
        this.restConsultantReportMockMvc = MockMvcBuilders.standaloneSetup(consultantReportResource)
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
    public static ConsultantReport createEntity(EntityManager em) {
        ConsultantReport consultantReport = new ConsultantReport()
            .weekName(DEFAULT_WEEK_NAME)
            .weekFromDate(DEFAULT_WEEK_FROM_DATE)
            .weekEndDate(DEFAULT_WEEK_END_DATE)
            .visitedCount(DEFAULT_VISITED_COUNT)
            .dealedMoneyAmount(DEFAULT_DEALED_MONEY_AMOUNT);
        return consultantReport;
    }

    @Before
    public void initTest() {
        consultantReport = createEntity(em);
    }

    @Test
    @Transactional
    public void createConsultantReport() throws Exception {
        int databaseSizeBeforeCreate = consultantReportRepository.findAll().size();

        // Create the ConsultantReport
        restConsultantReportMockMvc.perform(post("/api/consultant-reports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(consultantReport)))
            .andExpect(status().isCreated());

        // Validate the ConsultantReport in the database
        List<ConsultantReport> consultantReportList = consultantReportRepository.findAll();
        assertThat(consultantReportList).hasSize(databaseSizeBeforeCreate + 1);
        ConsultantReport testConsultantReport = consultantReportList.get(consultantReportList.size() - 1);
        assertThat(testConsultantReport.getWeekName()).isEqualTo(DEFAULT_WEEK_NAME);
        assertThat(testConsultantReport.getWeekFromDate()).isEqualTo(DEFAULT_WEEK_FROM_DATE);
        assertThat(testConsultantReport.getWeekEndDate()).isEqualTo(DEFAULT_WEEK_END_DATE);
        assertThat(testConsultantReport.getVisitedCount()).isEqualTo(DEFAULT_VISITED_COUNT);
        assertThat(testConsultantReport.getDealedMoneyAmount()).isEqualTo(DEFAULT_DEALED_MONEY_AMOUNT);
    }

    @Test
    @Transactional
    public void createConsultantReportWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = consultantReportRepository.findAll().size();

        // Create the ConsultantReport with an existing ID
        consultantReport.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restConsultantReportMockMvc.perform(post("/api/consultant-reports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(consultantReport)))
            .andExpect(status().isBadRequest());

        // Validate the ConsultantReport in the database
        List<ConsultantReport> consultantReportList = consultantReportRepository.findAll();
        assertThat(consultantReportList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllConsultantReports() throws Exception {
        // Initialize the database
        consultantReportRepository.saveAndFlush(consultantReport);

        // Get all the consultantReportList
        restConsultantReportMockMvc.perform(get("/api/consultant-reports?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(consultantReport.getId().intValue())))
            .andExpect(jsonPath("$.[*].weekName").value(hasItem(DEFAULT_WEEK_NAME)))
            .andExpect(jsonPath("$.[*].weekFromDate").value(hasItem(DEFAULT_WEEK_FROM_DATE.toString())))
            .andExpect(jsonPath("$.[*].weekEndDate").value(hasItem(DEFAULT_WEEK_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].visitedCount").value(hasItem(DEFAULT_VISITED_COUNT)))
            .andExpect(jsonPath("$.[*].dealedMoneyAmount").value(hasItem(DEFAULT_DEALED_MONEY_AMOUNT.doubleValue())));
    }

    @Test
    @Transactional
    public void getConsultantReport() throws Exception {
        // Initialize the database
        consultantReportRepository.saveAndFlush(consultantReport);

        // Get the consultantReport
        restConsultantReportMockMvc.perform(get("/api/consultant-reports/{id}", consultantReport.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(consultantReport.getId().intValue()))
            .andExpect(jsonPath("$.weekName").value(DEFAULT_WEEK_NAME))
            .andExpect(jsonPath("$.weekFromDate").value(DEFAULT_WEEK_FROM_DATE.toString()))
            .andExpect(jsonPath("$.weekEndDate").value(DEFAULT_WEEK_END_DATE.toString()))
            .andExpect(jsonPath("$.visitedCount").value(DEFAULT_VISITED_COUNT))
            .andExpect(jsonPath("$.dealedMoneyAmount").value(DEFAULT_DEALED_MONEY_AMOUNT.doubleValue()));
    }

    @Test
    @Transactional
    public void getAllConsultantReportsByWeekNameIsEqualToSomething() throws Exception {
        // Initialize the database
        consultantReportRepository.saveAndFlush(consultantReport);

        // Get all the consultantReportList where weekName equals to DEFAULT_WEEK_NAME
        defaultConsultantReportShouldBeFound("weekName.equals=" + DEFAULT_WEEK_NAME);

        // Get all the consultantReportList where weekName equals to UPDATED_WEEK_NAME
        defaultConsultantReportShouldNotBeFound("weekName.equals=" + UPDATED_WEEK_NAME);
    }

    @Test
    @Transactional
    public void getAllConsultantReportsByWeekNameIsInShouldWork() throws Exception {
        // Initialize the database
        consultantReportRepository.saveAndFlush(consultantReport);

        // Get all the consultantReportList where weekName in DEFAULT_WEEK_NAME or UPDATED_WEEK_NAME
        defaultConsultantReportShouldBeFound("weekName.in=" + DEFAULT_WEEK_NAME + "," + UPDATED_WEEK_NAME);

        // Get all the consultantReportList where weekName equals to UPDATED_WEEK_NAME
        defaultConsultantReportShouldNotBeFound("weekName.in=" + UPDATED_WEEK_NAME);
    }

    @Test
    @Transactional
    public void getAllConsultantReportsByWeekNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        consultantReportRepository.saveAndFlush(consultantReport);

        // Get all the consultantReportList where weekName is not null
        defaultConsultantReportShouldBeFound("weekName.specified=true");

        // Get all the consultantReportList where weekName is null
        defaultConsultantReportShouldNotBeFound("weekName.specified=false");
    }

    @Test
    @Transactional
    public void getAllConsultantReportsByWeekNameIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        consultantReportRepository.saveAndFlush(consultantReport);

        // Get all the consultantReportList where weekName greater than or equals to DEFAULT_WEEK_NAME
        defaultConsultantReportShouldBeFound("weekName.greaterOrEqualThan=" + DEFAULT_WEEK_NAME);

        // Get all the consultantReportList where weekName greater than or equals to UPDATED_WEEK_NAME
        defaultConsultantReportShouldNotBeFound("weekName.greaterOrEqualThan=" + UPDATED_WEEK_NAME);
    }

    @Test
    @Transactional
    public void getAllConsultantReportsByWeekNameIsLessThanSomething() throws Exception {
        // Initialize the database
        consultantReportRepository.saveAndFlush(consultantReport);

        // Get all the consultantReportList where weekName less than or equals to DEFAULT_WEEK_NAME
        defaultConsultantReportShouldNotBeFound("weekName.lessThan=" + DEFAULT_WEEK_NAME);

        // Get all the consultantReportList where weekName less than or equals to UPDATED_WEEK_NAME
        defaultConsultantReportShouldBeFound("weekName.lessThan=" + UPDATED_WEEK_NAME);
    }


    @Test
    @Transactional
    public void getAllConsultantReportsByWeekFromDateIsEqualToSomething() throws Exception {
        // Initialize the database
        consultantReportRepository.saveAndFlush(consultantReport);

        // Get all the consultantReportList where weekFromDate equals to DEFAULT_WEEK_FROM_DATE
        defaultConsultantReportShouldBeFound("weekFromDate.equals=" + DEFAULT_WEEK_FROM_DATE);

        // Get all the consultantReportList where weekFromDate equals to UPDATED_WEEK_FROM_DATE
        defaultConsultantReportShouldNotBeFound("weekFromDate.equals=" + UPDATED_WEEK_FROM_DATE);
    }

    @Test
    @Transactional
    public void getAllConsultantReportsByWeekFromDateIsInShouldWork() throws Exception {
        // Initialize the database
        consultantReportRepository.saveAndFlush(consultantReport);

        // Get all the consultantReportList where weekFromDate in DEFAULT_WEEK_FROM_DATE or UPDATED_WEEK_FROM_DATE
        defaultConsultantReportShouldBeFound("weekFromDate.in=" + DEFAULT_WEEK_FROM_DATE + "," + UPDATED_WEEK_FROM_DATE);

        // Get all the consultantReportList where weekFromDate equals to UPDATED_WEEK_FROM_DATE
        defaultConsultantReportShouldNotBeFound("weekFromDate.in=" + UPDATED_WEEK_FROM_DATE);
    }

    @Test
    @Transactional
    public void getAllConsultantReportsByWeekFromDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        consultantReportRepository.saveAndFlush(consultantReport);

        // Get all the consultantReportList where weekFromDate is not null
        defaultConsultantReportShouldBeFound("weekFromDate.specified=true");

        // Get all the consultantReportList where weekFromDate is null
        defaultConsultantReportShouldNotBeFound("weekFromDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllConsultantReportsByWeekEndDateIsEqualToSomething() throws Exception {
        // Initialize the database
        consultantReportRepository.saveAndFlush(consultantReport);

        // Get all the consultantReportList where weekEndDate equals to DEFAULT_WEEK_END_DATE
        defaultConsultantReportShouldBeFound("weekEndDate.equals=" + DEFAULT_WEEK_END_DATE);

        // Get all the consultantReportList where weekEndDate equals to UPDATED_WEEK_END_DATE
        defaultConsultantReportShouldNotBeFound("weekEndDate.equals=" + UPDATED_WEEK_END_DATE);
    }

    @Test
    @Transactional
    public void getAllConsultantReportsByWeekEndDateIsInShouldWork() throws Exception {
        // Initialize the database
        consultantReportRepository.saveAndFlush(consultantReport);

        // Get all the consultantReportList where weekEndDate in DEFAULT_WEEK_END_DATE or UPDATED_WEEK_END_DATE
        defaultConsultantReportShouldBeFound("weekEndDate.in=" + DEFAULT_WEEK_END_DATE + "," + UPDATED_WEEK_END_DATE);

        // Get all the consultantReportList where weekEndDate equals to UPDATED_WEEK_END_DATE
        defaultConsultantReportShouldNotBeFound("weekEndDate.in=" + UPDATED_WEEK_END_DATE);
    }

    @Test
    @Transactional
    public void getAllConsultantReportsByWeekEndDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        consultantReportRepository.saveAndFlush(consultantReport);

        // Get all the consultantReportList where weekEndDate is not null
        defaultConsultantReportShouldBeFound("weekEndDate.specified=true");

        // Get all the consultantReportList where weekEndDate is null
        defaultConsultantReportShouldNotBeFound("weekEndDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllConsultantReportsByVisitedCountIsEqualToSomething() throws Exception {
        // Initialize the database
        consultantReportRepository.saveAndFlush(consultantReport);

        // Get all the consultantReportList where visitedCount equals to DEFAULT_VISITED_COUNT
        defaultConsultantReportShouldBeFound("visitedCount.equals=" + DEFAULT_VISITED_COUNT);

        // Get all the consultantReportList where visitedCount equals to UPDATED_VISITED_COUNT
        defaultConsultantReportShouldNotBeFound("visitedCount.equals=" + UPDATED_VISITED_COUNT);
    }

    @Test
    @Transactional
    public void getAllConsultantReportsByVisitedCountIsInShouldWork() throws Exception {
        // Initialize the database
        consultantReportRepository.saveAndFlush(consultantReport);

        // Get all the consultantReportList where visitedCount in DEFAULT_VISITED_COUNT or UPDATED_VISITED_COUNT
        defaultConsultantReportShouldBeFound("visitedCount.in=" + DEFAULT_VISITED_COUNT + "," + UPDATED_VISITED_COUNT);

        // Get all the consultantReportList where visitedCount equals to UPDATED_VISITED_COUNT
        defaultConsultantReportShouldNotBeFound("visitedCount.in=" + UPDATED_VISITED_COUNT);
    }

    @Test
    @Transactional
    public void getAllConsultantReportsByVisitedCountIsNullOrNotNull() throws Exception {
        // Initialize the database
        consultantReportRepository.saveAndFlush(consultantReport);

        // Get all the consultantReportList where visitedCount is not null
        defaultConsultantReportShouldBeFound("visitedCount.specified=true");

        // Get all the consultantReportList where visitedCount is null
        defaultConsultantReportShouldNotBeFound("visitedCount.specified=false");
    }

    @Test
    @Transactional
    public void getAllConsultantReportsByVisitedCountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        consultantReportRepository.saveAndFlush(consultantReport);

        // Get all the consultantReportList where visitedCount greater than or equals to DEFAULT_VISITED_COUNT
        defaultConsultantReportShouldBeFound("visitedCount.greaterOrEqualThan=" + DEFAULT_VISITED_COUNT);

        // Get all the consultantReportList where visitedCount greater than or equals to UPDATED_VISITED_COUNT
        defaultConsultantReportShouldNotBeFound("visitedCount.greaterOrEqualThan=" + UPDATED_VISITED_COUNT);
    }

    @Test
    @Transactional
    public void getAllConsultantReportsByVisitedCountIsLessThanSomething() throws Exception {
        // Initialize the database
        consultantReportRepository.saveAndFlush(consultantReport);

        // Get all the consultantReportList where visitedCount less than or equals to DEFAULT_VISITED_COUNT
        defaultConsultantReportShouldNotBeFound("visitedCount.lessThan=" + DEFAULT_VISITED_COUNT);

        // Get all the consultantReportList where visitedCount less than or equals to UPDATED_VISITED_COUNT
        defaultConsultantReportShouldBeFound("visitedCount.lessThan=" + UPDATED_VISITED_COUNT);
    }


    @Test
    @Transactional
    public void getAllConsultantReportsByDealedMoneyAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        consultantReportRepository.saveAndFlush(consultantReport);

        // Get all the consultantReportList where dealedMoneyAmount equals to DEFAULT_DEALED_MONEY_AMOUNT
        defaultConsultantReportShouldBeFound("dealedMoneyAmount.equals=" + DEFAULT_DEALED_MONEY_AMOUNT);

        // Get all the consultantReportList where dealedMoneyAmount equals to UPDATED_DEALED_MONEY_AMOUNT
        defaultConsultantReportShouldNotBeFound("dealedMoneyAmount.equals=" + UPDATED_DEALED_MONEY_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllConsultantReportsByDealedMoneyAmountIsInShouldWork() throws Exception {
        // Initialize the database
        consultantReportRepository.saveAndFlush(consultantReport);

        // Get all the consultantReportList where dealedMoneyAmount in DEFAULT_DEALED_MONEY_AMOUNT or UPDATED_DEALED_MONEY_AMOUNT
        defaultConsultantReportShouldBeFound("dealedMoneyAmount.in=" + DEFAULT_DEALED_MONEY_AMOUNT + "," + UPDATED_DEALED_MONEY_AMOUNT);

        // Get all the consultantReportList where dealedMoneyAmount equals to UPDATED_DEALED_MONEY_AMOUNT
        defaultConsultantReportShouldNotBeFound("dealedMoneyAmount.in=" + UPDATED_DEALED_MONEY_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllConsultantReportsByDealedMoneyAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        consultantReportRepository.saveAndFlush(consultantReport);

        // Get all the consultantReportList where dealedMoneyAmount is not null
        defaultConsultantReportShouldBeFound("dealedMoneyAmount.specified=true");

        // Get all the consultantReportList where dealedMoneyAmount is null
        defaultConsultantReportShouldNotBeFound("dealedMoneyAmount.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultConsultantReportShouldBeFound(String filter) throws Exception {
        restConsultantReportMockMvc.perform(get("/api/consultant-reports?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(consultantReport.getId().intValue())))
            .andExpect(jsonPath("$.[*].weekName").value(hasItem(DEFAULT_WEEK_NAME)))
            .andExpect(jsonPath("$.[*].weekFromDate").value(hasItem(DEFAULT_WEEK_FROM_DATE.toString())))
            .andExpect(jsonPath("$.[*].weekEndDate").value(hasItem(DEFAULT_WEEK_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].visitedCount").value(hasItem(DEFAULT_VISITED_COUNT)))
            .andExpect(jsonPath("$.[*].dealedMoneyAmount").value(hasItem(DEFAULT_DEALED_MONEY_AMOUNT.doubleValue())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultConsultantReportShouldNotBeFound(String filter) throws Exception {
        restConsultantReportMockMvc.perform(get("/api/consultant-reports?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingConsultantReport() throws Exception {
        // Get the consultantReport
        restConsultantReportMockMvc.perform(get("/api/consultant-reports/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConsultantReport() throws Exception {
        // Initialize the database
        consultantReportService.save(consultantReport);

        int databaseSizeBeforeUpdate = consultantReportRepository.findAll().size();

        // Update the consultantReport
        ConsultantReport updatedConsultantReport = consultantReportRepository.findOne(consultantReport.getId());
        updatedConsultantReport
            .weekName(UPDATED_WEEK_NAME)
            .weekFromDate(UPDATED_WEEK_FROM_DATE)
            .weekEndDate(UPDATED_WEEK_END_DATE)
            .visitedCount(UPDATED_VISITED_COUNT)
            .dealedMoneyAmount(UPDATED_DEALED_MONEY_AMOUNT);

        restConsultantReportMockMvc.perform(put("/api/consultant-reports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedConsultantReport)))
            .andExpect(status().isOk());

        // Validate the ConsultantReport in the database
        List<ConsultantReport> consultantReportList = consultantReportRepository.findAll();
        assertThat(consultantReportList).hasSize(databaseSizeBeforeUpdate);
        ConsultantReport testConsultantReport = consultantReportList.get(consultantReportList.size() - 1);
        assertThat(testConsultantReport.getWeekName()).isEqualTo(UPDATED_WEEK_NAME);
        assertThat(testConsultantReport.getWeekFromDate()).isEqualTo(UPDATED_WEEK_FROM_DATE);
        assertThat(testConsultantReport.getWeekEndDate()).isEqualTo(UPDATED_WEEK_END_DATE);
        assertThat(testConsultantReport.getVisitedCount()).isEqualTo(UPDATED_VISITED_COUNT);
        assertThat(testConsultantReport.getDealedMoneyAmount()).isEqualTo(UPDATED_DEALED_MONEY_AMOUNT);
    }

    @Test
    @Transactional
    public void updateNonExistingConsultantReport() throws Exception {
        int databaseSizeBeforeUpdate = consultantReportRepository.findAll().size();

        // Create the ConsultantReport

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restConsultantReportMockMvc.perform(put("/api/consultant-reports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(consultantReport)))
            .andExpect(status().isCreated());

        // Validate the ConsultantReport in the database
        List<ConsultantReport> consultantReportList = consultantReportRepository.findAll();
        assertThat(consultantReportList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteConsultantReport() throws Exception {
        // Initialize the database
        consultantReportService.save(consultantReport);

        int databaseSizeBeforeDelete = consultantReportRepository.findAll().size();

        // Get the consultantReport
        restConsultantReportMockMvc.perform(delete("/api/consultant-reports/{id}", consultantReport.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ConsultantReport> consultantReportList = consultantReportRepository.findAll();
        assertThat(consultantReportList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConsultantReport.class);
        ConsultantReport consultantReport1 = new ConsultantReport();
        consultantReport1.setId(1L);
        ConsultantReport consultantReport2 = new ConsultantReport();
        consultantReport2.setId(consultantReport1.getId());
        assertThat(consultantReport1).isEqualTo(consultantReport2);
        consultantReport2.setId(2L);
        assertThat(consultantReport1).isNotEqualTo(consultantReport2);
        consultantReport1.setId(null);
        assertThat(consultantReport1).isNotEqualTo(consultantReport2);
    }
}
