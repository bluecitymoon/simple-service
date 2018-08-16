package com.pure.service.web.rest;

import com.pure.service.SimpleServiceApp;

import com.pure.service.domain.StudentClass;
import com.pure.service.repository.StudentClassRepository;
import com.pure.service.service.StudentClassService;
import com.pure.service.web.rest.errors.ExceptionTranslator;
import com.pure.service.service.dto.StudentClassCriteria;
import com.pure.service.service.StudentClassQueryService;

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
 * Test class for the StudentClassResource REST controller.
 *
 * @see StudentClassResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SimpleServiceApp.class)
public class StudentClassResourceIntTest {

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private StudentClassRepository studentClassRepository;

    @Autowired
    private StudentClassService studentClassService;

    @Autowired
    private StudentClassQueryService studentClassQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restStudentClassMockMvc;

    private StudentClass studentClass;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final StudentClassResource studentClassResource = new StudentClassResource(studentClassService, studentClassQueryService);
        this.restStudentClassMockMvc = MockMvcBuilders.standaloneSetup(studentClassResource)
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
    public static StudentClass createEntity(EntityManager em) {
        StudentClass studentClass = new StudentClass()
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return studentClass;
    }

    @Before
    public void initTest() {
        studentClass = createEntity(em);
    }

    @Test
    @Transactional
    public void createStudentClass() throws Exception {
        int databaseSizeBeforeCreate = studentClassRepository.findAll().size();

        // Create the StudentClass
        restStudentClassMockMvc.perform(post("/api/student-classes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentClass)))
            .andExpect(status().isCreated());

        // Validate the StudentClass in the database
        List<StudentClass> studentClassList = studentClassRepository.findAll();
        assertThat(studentClassList).hasSize(databaseSizeBeforeCreate + 1);
        StudentClass testStudentClass = studentClassList.get(studentClassList.size() - 1);
        assertThat(testStudentClass.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testStudentClass.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testStudentClass.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testStudentClass.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createStudentClassWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = studentClassRepository.findAll().size();

        // Create the StudentClass with an existing ID
        studentClass.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStudentClassMockMvc.perform(post("/api/student-classes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentClass)))
            .andExpect(status().isBadRequest());

        // Validate the StudentClass in the database
        List<StudentClass> studentClassList = studentClassRepository.findAll();
        assertThat(studentClassList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllStudentClasses() throws Exception {
        // Initialize the database
        studentClassRepository.saveAndFlush(studentClass);

        // Get all the studentClassList
        restStudentClassMockMvc.perform(get("/api/student-classes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(studentClass.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    public void getStudentClass() throws Exception {
        // Initialize the database
        studentClassRepository.saveAndFlush(studentClass);

        // Get the studentClass
        restStudentClassMockMvc.perform(get("/api/student-classes/{id}", studentClass.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(studentClass.getId().intValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    public void getAllStudentClassesByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        studentClassRepository.saveAndFlush(studentClass);

        // Get all the studentClassList where createdBy equals to DEFAULT_CREATED_BY
        defaultStudentClassShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the studentClassList where createdBy equals to UPDATED_CREATED_BY
        defaultStudentClassShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllStudentClassesByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        studentClassRepository.saveAndFlush(studentClass);

        // Get all the studentClassList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultStudentClassShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the studentClassList where createdBy equals to UPDATED_CREATED_BY
        defaultStudentClassShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllStudentClassesByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        studentClassRepository.saveAndFlush(studentClass);

        // Get all the studentClassList where createdBy is not null
        defaultStudentClassShouldBeFound("createdBy.specified=true");

        // Get all the studentClassList where createdBy is null
        defaultStudentClassShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllStudentClassesByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        studentClassRepository.saveAndFlush(studentClass);

        // Get all the studentClassList where createdDate equals to DEFAULT_CREATED_DATE
        defaultStudentClassShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the studentClassList where createdDate equals to UPDATED_CREATED_DATE
        defaultStudentClassShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllStudentClassesByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        studentClassRepository.saveAndFlush(studentClass);

        // Get all the studentClassList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultStudentClassShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the studentClassList where createdDate equals to UPDATED_CREATED_DATE
        defaultStudentClassShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllStudentClassesByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        studentClassRepository.saveAndFlush(studentClass);

        // Get all the studentClassList where createdDate is not null
        defaultStudentClassShouldBeFound("createdDate.specified=true");

        // Get all the studentClassList where createdDate is null
        defaultStudentClassShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllStudentClassesByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        studentClassRepository.saveAndFlush(studentClass);

        // Get all the studentClassList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultStudentClassShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the studentClassList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultStudentClassShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllStudentClassesByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        studentClassRepository.saveAndFlush(studentClass);

        // Get all the studentClassList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultStudentClassShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the studentClassList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultStudentClassShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllStudentClassesByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        studentClassRepository.saveAndFlush(studentClass);

        // Get all the studentClassList where lastModifiedBy is not null
        defaultStudentClassShouldBeFound("lastModifiedBy.specified=true");

        // Get all the studentClassList where lastModifiedBy is null
        defaultStudentClassShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllStudentClassesByLastModifiedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        studentClassRepository.saveAndFlush(studentClass);

        // Get all the studentClassList where lastModifiedDate equals to DEFAULT_LAST_MODIFIED_DATE
        defaultStudentClassShouldBeFound("lastModifiedDate.equals=" + DEFAULT_LAST_MODIFIED_DATE);

        // Get all the studentClassList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultStudentClassShouldNotBeFound("lastModifiedDate.equals=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllStudentClassesByLastModifiedDateIsInShouldWork() throws Exception {
        // Initialize the database
        studentClassRepository.saveAndFlush(studentClass);

        // Get all the studentClassList where lastModifiedDate in DEFAULT_LAST_MODIFIED_DATE or UPDATED_LAST_MODIFIED_DATE
        defaultStudentClassShouldBeFound("lastModifiedDate.in=" + DEFAULT_LAST_MODIFIED_DATE + "," + UPDATED_LAST_MODIFIED_DATE);

        // Get all the studentClassList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultStudentClassShouldNotBeFound("lastModifiedDate.in=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllStudentClassesByLastModifiedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        studentClassRepository.saveAndFlush(studentClass);

        // Get all the studentClassList where lastModifiedDate is not null
        defaultStudentClassShouldBeFound("lastModifiedDate.specified=true");

        // Get all the studentClassList where lastModifiedDate is null
        defaultStudentClassShouldNotBeFound("lastModifiedDate.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultStudentClassShouldBeFound(String filter) throws Exception {
        restStudentClassMockMvc.perform(get("/api/student-classes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(studentClass.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultStudentClassShouldNotBeFound(String filter) throws Exception {
        restStudentClassMockMvc.perform(get("/api/student-classes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingStudentClass() throws Exception {
        // Get the studentClass
        restStudentClassMockMvc.perform(get("/api/student-classes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStudentClass() throws Exception {
        // Initialize the database
        studentClassService.save(studentClass);

        int databaseSizeBeforeUpdate = studentClassRepository.findAll().size();

        // Update the studentClass
        StudentClass updatedStudentClass = studentClassRepository.findOne(studentClass.getId());
        updatedStudentClass
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restStudentClassMockMvc.perform(put("/api/student-classes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedStudentClass)))
            .andExpect(status().isOk());

        // Validate the StudentClass in the database
        List<StudentClass> studentClassList = studentClassRepository.findAll();
        assertThat(studentClassList).hasSize(databaseSizeBeforeUpdate);
        StudentClass testStudentClass = studentClassList.get(studentClassList.size() - 1);
        assertThat(testStudentClass.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testStudentClass.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testStudentClass.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testStudentClass.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingStudentClass() throws Exception {
        int databaseSizeBeforeUpdate = studentClassRepository.findAll().size();

        // Create the StudentClass

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restStudentClassMockMvc.perform(put("/api/student-classes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentClass)))
            .andExpect(status().isCreated());

        // Validate the StudentClass in the database
        List<StudentClass> studentClassList = studentClassRepository.findAll();
        assertThat(studentClassList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteStudentClass() throws Exception {
        // Initialize the database
        studentClassService.save(studentClass);

        int databaseSizeBeforeDelete = studentClassRepository.findAll().size();

        // Get the studentClass
        restStudentClassMockMvc.perform(delete("/api/student-classes/{id}", studentClass.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<StudentClass> studentClassList = studentClassRepository.findAll();
        assertThat(studentClassList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StudentClass.class);
        StudentClass studentClass1 = new StudentClass();
        studentClass1.setId(1L);
        StudentClass studentClass2 = new StudentClass();
        studentClass2.setId(studentClass1.getId());
        assertThat(studentClass1).isEqualTo(studentClass2);
        studentClass2.setId(2L);
        assertThat(studentClass1).isNotEqualTo(studentClass2);
        studentClass1.setId(null);
        assertThat(studentClass1).isNotEqualTo(studentClass2);
    }
}
