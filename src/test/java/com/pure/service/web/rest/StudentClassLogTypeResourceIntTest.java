package com.pure.service.web.rest;

import com.pure.service.SimpleServiceApp;

import com.pure.service.domain.StudentClassLogType;
import com.pure.service.repository.StudentClassLogTypeRepository;
import com.pure.service.service.StudentClassLogTypeService;
import com.pure.service.web.rest.errors.ExceptionTranslator;
import com.pure.service.service.dto.StudentClassLogTypeCriteria;
import com.pure.service.service.StudentClassLogTypeQueryService;

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
 * Test class for the StudentClassLogTypeResource REST controller.
 *
 * @see StudentClassLogTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SimpleServiceApp.class)
public class StudentClassLogTypeResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTS = "BBBBBBBBBB";

    @Autowired
    private StudentClassLogTypeRepository studentClassLogTypeRepository;

    @Autowired
    private StudentClassLogTypeService studentClassLogTypeService;

    @Autowired
    private StudentClassLogTypeQueryService studentClassLogTypeQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restStudentClassLogTypeMockMvc;

    private StudentClassLogType studentClassLogType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final StudentClassLogTypeResource studentClassLogTypeResource = new StudentClassLogTypeResource(studentClassLogTypeService, studentClassLogTypeQueryService);
        this.restStudentClassLogTypeMockMvc = MockMvcBuilders.standaloneSetup(studentClassLogTypeResource)
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
    public static StudentClassLogType createEntity(EntityManager em) {
        StudentClassLogType studentClassLogType = new StudentClassLogType()
            .name(DEFAULT_NAME)
            .code(DEFAULT_CODE)
            .comments(DEFAULT_COMMENTS);
        return studentClassLogType;
    }

    @Before
    public void initTest() {
        studentClassLogType = createEntity(em);
    }

    @Test
    @Transactional
    public void createStudentClassLogType() throws Exception {
        int databaseSizeBeforeCreate = studentClassLogTypeRepository.findAll().size();

        // Create the StudentClassLogType
        restStudentClassLogTypeMockMvc.perform(post("/api/student-class-log-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentClassLogType)))
            .andExpect(status().isCreated());

        // Validate the StudentClassLogType in the database
        List<StudentClassLogType> studentClassLogTypeList = studentClassLogTypeRepository.findAll();
        assertThat(studentClassLogTypeList).hasSize(databaseSizeBeforeCreate + 1);
        StudentClassLogType testStudentClassLogType = studentClassLogTypeList.get(studentClassLogTypeList.size() - 1);
        assertThat(testStudentClassLogType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testStudentClassLogType.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testStudentClassLogType.getComments()).isEqualTo(DEFAULT_COMMENTS);
    }

    @Test
    @Transactional
    public void createStudentClassLogTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = studentClassLogTypeRepository.findAll().size();

        // Create the StudentClassLogType with an existing ID
        studentClassLogType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStudentClassLogTypeMockMvc.perform(post("/api/student-class-log-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentClassLogType)))
            .andExpect(status().isBadRequest());

        // Validate the StudentClassLogType in the database
        List<StudentClassLogType> studentClassLogTypeList = studentClassLogTypeRepository.findAll();
        assertThat(studentClassLogTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllStudentClassLogTypes() throws Exception {
        // Initialize the database
        studentClassLogTypeRepository.saveAndFlush(studentClassLogType);

        // Get all the studentClassLogTypeList
        restStudentClassLogTypeMockMvc.perform(get("/api/student-class-log-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(studentClassLogType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS)));
    }

    @Test
    @Transactional
    public void getStudentClassLogType() throws Exception {
        // Initialize the database
        studentClassLogTypeRepository.saveAndFlush(studentClassLogType);

        // Get the studentClassLogType
        restStudentClassLogTypeMockMvc.perform(get("/api/student-class-log-types/{id}", studentClassLogType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(studentClassLogType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS));
    }

    @Test
    @Transactional
    public void getAllStudentClassLogTypesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        studentClassLogTypeRepository.saveAndFlush(studentClassLogType);

        // Get all the studentClassLogTypeList where name equals to DEFAULT_NAME
        defaultStudentClassLogTypeShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the studentClassLogTypeList where name equals to UPDATED_NAME
        defaultStudentClassLogTypeShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllStudentClassLogTypesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        studentClassLogTypeRepository.saveAndFlush(studentClassLogType);

        // Get all the studentClassLogTypeList where name in DEFAULT_NAME or UPDATED_NAME
        defaultStudentClassLogTypeShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the studentClassLogTypeList where name equals to UPDATED_NAME
        defaultStudentClassLogTypeShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllStudentClassLogTypesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        studentClassLogTypeRepository.saveAndFlush(studentClassLogType);

        // Get all the studentClassLogTypeList where name is not null
        defaultStudentClassLogTypeShouldBeFound("name.specified=true");

        // Get all the studentClassLogTypeList where name is null
        defaultStudentClassLogTypeShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllStudentClassLogTypesByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        studentClassLogTypeRepository.saveAndFlush(studentClassLogType);

        // Get all the studentClassLogTypeList where code equals to DEFAULT_CODE
        defaultStudentClassLogTypeShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the studentClassLogTypeList where code equals to UPDATED_CODE
        defaultStudentClassLogTypeShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllStudentClassLogTypesByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        studentClassLogTypeRepository.saveAndFlush(studentClassLogType);

        // Get all the studentClassLogTypeList where code in DEFAULT_CODE or UPDATED_CODE
        defaultStudentClassLogTypeShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the studentClassLogTypeList where code equals to UPDATED_CODE
        defaultStudentClassLogTypeShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllStudentClassLogTypesByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        studentClassLogTypeRepository.saveAndFlush(studentClassLogType);

        // Get all the studentClassLogTypeList where code is not null
        defaultStudentClassLogTypeShouldBeFound("code.specified=true");

        // Get all the studentClassLogTypeList where code is null
        defaultStudentClassLogTypeShouldNotBeFound("code.specified=false");
    }

    @Test
    @Transactional
    public void getAllStudentClassLogTypesByCommentsIsEqualToSomething() throws Exception {
        // Initialize the database
        studentClassLogTypeRepository.saveAndFlush(studentClassLogType);

        // Get all the studentClassLogTypeList where comments equals to DEFAULT_COMMENTS
        defaultStudentClassLogTypeShouldBeFound("comments.equals=" + DEFAULT_COMMENTS);

        // Get all the studentClassLogTypeList where comments equals to UPDATED_COMMENTS
        defaultStudentClassLogTypeShouldNotBeFound("comments.equals=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllStudentClassLogTypesByCommentsIsInShouldWork() throws Exception {
        // Initialize the database
        studentClassLogTypeRepository.saveAndFlush(studentClassLogType);

        // Get all the studentClassLogTypeList where comments in DEFAULT_COMMENTS or UPDATED_COMMENTS
        defaultStudentClassLogTypeShouldBeFound("comments.in=" + DEFAULT_COMMENTS + "," + UPDATED_COMMENTS);

        // Get all the studentClassLogTypeList where comments equals to UPDATED_COMMENTS
        defaultStudentClassLogTypeShouldNotBeFound("comments.in=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllStudentClassLogTypesByCommentsIsNullOrNotNull() throws Exception {
        // Initialize the database
        studentClassLogTypeRepository.saveAndFlush(studentClassLogType);

        // Get all the studentClassLogTypeList where comments is not null
        defaultStudentClassLogTypeShouldBeFound("comments.specified=true");

        // Get all the studentClassLogTypeList where comments is null
        defaultStudentClassLogTypeShouldNotBeFound("comments.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultStudentClassLogTypeShouldBeFound(String filter) throws Exception {
        restStudentClassLogTypeMockMvc.perform(get("/api/student-class-log-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(studentClassLogType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS)));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultStudentClassLogTypeShouldNotBeFound(String filter) throws Exception {
        restStudentClassLogTypeMockMvc.perform(get("/api/student-class-log-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingStudentClassLogType() throws Exception {
        // Get the studentClassLogType
        restStudentClassLogTypeMockMvc.perform(get("/api/student-class-log-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStudentClassLogType() throws Exception {
        // Initialize the database
        studentClassLogTypeService.save(studentClassLogType);

        int databaseSizeBeforeUpdate = studentClassLogTypeRepository.findAll().size();

        // Update the studentClassLogType
        StudentClassLogType updatedStudentClassLogType = studentClassLogTypeRepository.findOne(studentClassLogType.getId());
        updatedStudentClassLogType
            .name(UPDATED_NAME)
            .code(UPDATED_CODE)
            .comments(UPDATED_COMMENTS);

        restStudentClassLogTypeMockMvc.perform(put("/api/student-class-log-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedStudentClassLogType)))
            .andExpect(status().isOk());

        // Validate the StudentClassLogType in the database
        List<StudentClassLogType> studentClassLogTypeList = studentClassLogTypeRepository.findAll();
        assertThat(studentClassLogTypeList).hasSize(databaseSizeBeforeUpdate);
        StudentClassLogType testStudentClassLogType = studentClassLogTypeList.get(studentClassLogTypeList.size() - 1);
        assertThat(testStudentClassLogType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testStudentClassLogType.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testStudentClassLogType.getComments()).isEqualTo(UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    public void updateNonExistingStudentClassLogType() throws Exception {
        int databaseSizeBeforeUpdate = studentClassLogTypeRepository.findAll().size();

        // Create the StudentClassLogType

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restStudentClassLogTypeMockMvc.perform(put("/api/student-class-log-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentClassLogType)))
            .andExpect(status().isCreated());

        // Validate the StudentClassLogType in the database
        List<StudentClassLogType> studentClassLogTypeList = studentClassLogTypeRepository.findAll();
        assertThat(studentClassLogTypeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteStudentClassLogType() throws Exception {
        // Initialize the database
        studentClassLogTypeService.save(studentClassLogType);

        int databaseSizeBeforeDelete = studentClassLogTypeRepository.findAll().size();

        // Get the studentClassLogType
        restStudentClassLogTypeMockMvc.perform(delete("/api/student-class-log-types/{id}", studentClassLogType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<StudentClassLogType> studentClassLogTypeList = studentClassLogTypeRepository.findAll();
        assertThat(studentClassLogTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StudentClassLogType.class);
        StudentClassLogType studentClassLogType1 = new StudentClassLogType();
        studentClassLogType1.setId(1L);
        StudentClassLogType studentClassLogType2 = new StudentClassLogType();
        studentClassLogType2.setId(studentClassLogType1.getId());
        assertThat(studentClassLogType1).isEqualTo(studentClassLogType2);
        studentClassLogType2.setId(2L);
        assertThat(studentClassLogType1).isNotEqualTo(studentClassLogType2);
        studentClassLogType1.setId(null);
        assertThat(studentClassLogType1).isNotEqualTo(studentClassLogType2);
    }
}
