package com.pure.service.web.rest;

import com.pure.service.SimpleServiceApp;

import com.pure.service.domain.TimeSegment;
import com.pure.service.repository.TimeSegmentRepository;
import com.pure.service.service.TimeSegmentService;
import com.pure.service.web.rest.errors.ExceptionTranslator;
import com.pure.service.service.dto.TimeSegmentCriteria;
import com.pure.service.service.TimeSegmentQueryService;

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
 * Test class for the TimeSegmentResource REST controller.
 *
 * @see TimeSegmentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SimpleServiceApp.class)
public class TimeSegmentResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_START = "AAAAAAAAAA";
    private static final String UPDATED_START = "BBBBBBBBBB";

    private static final String DEFAULT_END = "AAAAAAAAAA";
    private static final String UPDATED_END = "BBBBBBBBBB";

    @Autowired
    private TimeSegmentRepository timeSegmentRepository;

    @Autowired
    private TimeSegmentService timeSegmentService;

    @Autowired
    private TimeSegmentQueryService timeSegmentQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTimeSegmentMockMvc;

    private TimeSegment timeSegment;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TimeSegmentResource timeSegmentResource = new TimeSegmentResource(timeSegmentService, timeSegmentQueryService);
        this.restTimeSegmentMockMvc = MockMvcBuilders.standaloneSetup(timeSegmentResource)
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
    public static TimeSegment createEntity(EntityManager em) {
        TimeSegment timeSegment = new TimeSegment()
            .name(DEFAULT_NAME)
            .code(DEFAULT_CODE)
            .start(DEFAULT_START)
            .end(DEFAULT_END);
        return timeSegment;
    }

    @Before
    public void initTest() {
        timeSegment = createEntity(em);
    }

    @Test
    @Transactional
    public void createTimeSegment() throws Exception {
        int databaseSizeBeforeCreate = timeSegmentRepository.findAll().size();

        // Create the TimeSegment
        restTimeSegmentMockMvc.perform(post("/api/time-segments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(timeSegment)))
            .andExpect(status().isCreated());

        // Validate the TimeSegment in the database
        List<TimeSegment> timeSegmentList = timeSegmentRepository.findAll();
        assertThat(timeSegmentList).hasSize(databaseSizeBeforeCreate + 1);
        TimeSegment testTimeSegment = timeSegmentList.get(timeSegmentList.size() - 1);
        assertThat(testTimeSegment.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTimeSegment.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testTimeSegment.getStart()).isEqualTo(DEFAULT_START);
        assertThat(testTimeSegment.getEnd()).isEqualTo(DEFAULT_END);
    }

    @Test
    @Transactional
    public void createTimeSegmentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = timeSegmentRepository.findAll().size();

        // Create the TimeSegment with an existing ID
        timeSegment.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTimeSegmentMockMvc.perform(post("/api/time-segments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(timeSegment)))
            .andExpect(status().isBadRequest());

        // Validate the TimeSegment in the database
        List<TimeSegment> timeSegmentList = timeSegmentRepository.findAll();
        assertThat(timeSegmentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTimeSegments() throws Exception {
        // Initialize the database
        timeSegmentRepository.saveAndFlush(timeSegment);

        // Get all the timeSegmentList
        restTimeSegmentMockMvc.perform(get("/api/time-segments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(timeSegment.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].start").value(hasItem(DEFAULT_START)))
            .andExpect(jsonPath("$.[*].end").value(hasItem(DEFAULT_END)));
    }

    @Test
    @Transactional
    public void getTimeSegment() throws Exception {
        // Initialize the database
        timeSegmentRepository.saveAndFlush(timeSegment);

        // Get the timeSegment
        restTimeSegmentMockMvc.perform(get("/api/time-segments/{id}", timeSegment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(timeSegment.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.start").value(DEFAULT_START))
            .andExpect(jsonPath("$.end").value(DEFAULT_END));
    }

    @Test
    @Transactional
    public void getAllTimeSegmentsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        timeSegmentRepository.saveAndFlush(timeSegment);

        // Get all the timeSegmentList where name equals to DEFAULT_NAME
        defaultTimeSegmentShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the timeSegmentList where name equals to UPDATED_NAME
        defaultTimeSegmentShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTimeSegmentsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        timeSegmentRepository.saveAndFlush(timeSegment);

        // Get all the timeSegmentList where name in DEFAULT_NAME or UPDATED_NAME
        defaultTimeSegmentShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the timeSegmentList where name equals to UPDATED_NAME
        defaultTimeSegmentShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTimeSegmentsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        timeSegmentRepository.saveAndFlush(timeSegment);

        // Get all the timeSegmentList where name is not null
        defaultTimeSegmentShouldBeFound("name.specified=true");

        // Get all the timeSegmentList where name is null
        defaultTimeSegmentShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllTimeSegmentsByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        timeSegmentRepository.saveAndFlush(timeSegment);

        // Get all the timeSegmentList where code equals to DEFAULT_CODE
        defaultTimeSegmentShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the timeSegmentList where code equals to UPDATED_CODE
        defaultTimeSegmentShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllTimeSegmentsByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        timeSegmentRepository.saveAndFlush(timeSegment);

        // Get all the timeSegmentList where code in DEFAULT_CODE or UPDATED_CODE
        defaultTimeSegmentShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the timeSegmentList where code equals to UPDATED_CODE
        defaultTimeSegmentShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllTimeSegmentsByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        timeSegmentRepository.saveAndFlush(timeSegment);

        // Get all the timeSegmentList where code is not null
        defaultTimeSegmentShouldBeFound("code.specified=true");

        // Get all the timeSegmentList where code is null
        defaultTimeSegmentShouldNotBeFound("code.specified=false");
    }

    @Test
    @Transactional
    public void getAllTimeSegmentsByStartIsEqualToSomething() throws Exception {
        // Initialize the database
        timeSegmentRepository.saveAndFlush(timeSegment);

        // Get all the timeSegmentList where start equals to DEFAULT_START
        defaultTimeSegmentShouldBeFound("start.equals=" + DEFAULT_START);

        // Get all the timeSegmentList where start equals to UPDATED_START
        defaultTimeSegmentShouldNotBeFound("start.equals=" + UPDATED_START);
    }

    @Test
    @Transactional
    public void getAllTimeSegmentsByStartIsInShouldWork() throws Exception {
        // Initialize the database
        timeSegmentRepository.saveAndFlush(timeSegment);

        // Get all the timeSegmentList where start in DEFAULT_START or UPDATED_START
        defaultTimeSegmentShouldBeFound("start.in=" + DEFAULT_START + "," + UPDATED_START);

        // Get all the timeSegmentList where start equals to UPDATED_START
        defaultTimeSegmentShouldNotBeFound("start.in=" + UPDATED_START);
    }

    @Test
    @Transactional
    public void getAllTimeSegmentsByStartIsNullOrNotNull() throws Exception {
        // Initialize the database
        timeSegmentRepository.saveAndFlush(timeSegment);

        // Get all the timeSegmentList where start is not null
        defaultTimeSegmentShouldBeFound("start.specified=true");

        // Get all the timeSegmentList where start is null
        defaultTimeSegmentShouldNotBeFound("start.specified=false");
    }

    @Test
    @Transactional
    public void getAllTimeSegmentsByEndIsEqualToSomething() throws Exception {
        // Initialize the database
        timeSegmentRepository.saveAndFlush(timeSegment);

        // Get all the timeSegmentList where end equals to DEFAULT_END
        defaultTimeSegmentShouldBeFound("end.equals=" + DEFAULT_END);

        // Get all the timeSegmentList where end equals to UPDATED_END
        defaultTimeSegmentShouldNotBeFound("end.equals=" + UPDATED_END);
    }

    @Test
    @Transactional
    public void getAllTimeSegmentsByEndIsInShouldWork() throws Exception {
        // Initialize the database
        timeSegmentRepository.saveAndFlush(timeSegment);

        // Get all the timeSegmentList where end in DEFAULT_END or UPDATED_END
        defaultTimeSegmentShouldBeFound("end.in=" + DEFAULT_END + "," + UPDATED_END);

        // Get all the timeSegmentList where end equals to UPDATED_END
        defaultTimeSegmentShouldNotBeFound("end.in=" + UPDATED_END);
    }

    @Test
    @Transactional
    public void getAllTimeSegmentsByEndIsNullOrNotNull() throws Exception {
        // Initialize the database
        timeSegmentRepository.saveAndFlush(timeSegment);

        // Get all the timeSegmentList where end is not null
        defaultTimeSegmentShouldBeFound("end.specified=true");

        // Get all the timeSegmentList where end is null
        defaultTimeSegmentShouldNotBeFound("end.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultTimeSegmentShouldBeFound(String filter) throws Exception {
        restTimeSegmentMockMvc.perform(get("/api/time-segments?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(timeSegment.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].start").value(hasItem(DEFAULT_START)))
            .andExpect(jsonPath("$.[*].end").value(hasItem(DEFAULT_END)));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultTimeSegmentShouldNotBeFound(String filter) throws Exception {
        restTimeSegmentMockMvc.perform(get("/api/time-segments?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingTimeSegment() throws Exception {
        // Get the timeSegment
        restTimeSegmentMockMvc.perform(get("/api/time-segments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTimeSegment() throws Exception {
        // Initialize the database
        timeSegmentService.save(timeSegment);

        int databaseSizeBeforeUpdate = timeSegmentRepository.findAll().size();

        // Update the timeSegment
        TimeSegment updatedTimeSegment = timeSegmentRepository.findOne(timeSegment.getId());
        updatedTimeSegment
            .name(UPDATED_NAME)
            .code(UPDATED_CODE)
            .start(UPDATED_START)
            .end(UPDATED_END);

        restTimeSegmentMockMvc.perform(put("/api/time-segments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTimeSegment)))
            .andExpect(status().isOk());

        // Validate the TimeSegment in the database
        List<TimeSegment> timeSegmentList = timeSegmentRepository.findAll();
        assertThat(timeSegmentList).hasSize(databaseSizeBeforeUpdate);
        TimeSegment testTimeSegment = timeSegmentList.get(timeSegmentList.size() - 1);
        assertThat(testTimeSegment.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTimeSegment.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testTimeSegment.getStart()).isEqualTo(UPDATED_START);
        assertThat(testTimeSegment.getEnd()).isEqualTo(UPDATED_END);
    }

    @Test
    @Transactional
    public void updateNonExistingTimeSegment() throws Exception {
        int databaseSizeBeforeUpdate = timeSegmentRepository.findAll().size();

        // Create the TimeSegment

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTimeSegmentMockMvc.perform(put("/api/time-segments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(timeSegment)))
            .andExpect(status().isCreated());

        // Validate the TimeSegment in the database
        List<TimeSegment> timeSegmentList = timeSegmentRepository.findAll();
        assertThat(timeSegmentList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTimeSegment() throws Exception {
        // Initialize the database
        timeSegmentService.save(timeSegment);

        int databaseSizeBeforeDelete = timeSegmentRepository.findAll().size();

        // Get the timeSegment
        restTimeSegmentMockMvc.perform(delete("/api/time-segments/{id}", timeSegment.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TimeSegment> timeSegmentList = timeSegmentRepository.findAll();
        assertThat(timeSegmentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TimeSegment.class);
        TimeSegment timeSegment1 = new TimeSegment();
        timeSegment1.setId(1L);
        TimeSegment timeSegment2 = new TimeSegment();
        timeSegment2.setId(timeSegment1.getId());
        assertThat(timeSegment1).isEqualTo(timeSegment2);
        timeSegment2.setId(2L);
        assertThat(timeSegment1).isNotEqualTo(timeSegment2);
        timeSegment1.setId(null);
        assertThat(timeSegment1).isNotEqualTo(timeSegment2);
    }
}
