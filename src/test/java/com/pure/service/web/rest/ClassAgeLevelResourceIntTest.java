package com.pure.service.web.rest;

import com.pure.service.SimpleServiceApp;

import com.pure.service.domain.ClassAgeLevel;
import com.pure.service.repository.ClassAgeLevelRepository;
import com.pure.service.service.ClassAgeLevelService;
import com.pure.service.web.rest.errors.ExceptionTranslator;
import com.pure.service.service.dto.ClassAgeLevelCriteria;
import com.pure.service.service.ClassAgeLevelQueryService;

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
 * Test class for the ClassAgeLevelResource REST controller.
 *
 * @see ClassAgeLevelResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SimpleServiceApp.class)
public class ClassAgeLevelResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    @Autowired
    private ClassAgeLevelRepository classAgeLevelRepository;

    @Autowired
    private ClassAgeLevelService classAgeLevelService;

    @Autowired
    private ClassAgeLevelQueryService classAgeLevelQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restClassAgeLevelMockMvc;

    private ClassAgeLevel classAgeLevel;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ClassAgeLevelResource classAgeLevelResource = new ClassAgeLevelResource(classAgeLevelService, classAgeLevelQueryService);
        this.restClassAgeLevelMockMvc = MockMvcBuilders.standaloneSetup(classAgeLevelResource)
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
    public static ClassAgeLevel createEntity(EntityManager em) {
        ClassAgeLevel classAgeLevel = new ClassAgeLevel()
            .name(DEFAULT_NAME)
            .code(DEFAULT_CODE);
        return classAgeLevel;
    }

    @Before
    public void initTest() {
        classAgeLevel = createEntity(em);
    }

    @Test
    @Transactional
    public void createClassAgeLevel() throws Exception {
        int databaseSizeBeforeCreate = classAgeLevelRepository.findAll().size();

        // Create the ClassAgeLevel
        restClassAgeLevelMockMvc.perform(post("/api/class-age-levels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(classAgeLevel)))
            .andExpect(status().isCreated());

        // Validate the ClassAgeLevel in the database
        List<ClassAgeLevel> classAgeLevelList = classAgeLevelRepository.findAll();
        assertThat(classAgeLevelList).hasSize(databaseSizeBeforeCreate + 1);
        ClassAgeLevel testClassAgeLevel = classAgeLevelList.get(classAgeLevelList.size() - 1);
        assertThat(testClassAgeLevel.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testClassAgeLevel.getCode()).isEqualTo(DEFAULT_CODE);
    }

    @Test
    @Transactional
    public void createClassAgeLevelWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = classAgeLevelRepository.findAll().size();

        // Create the ClassAgeLevel with an existing ID
        classAgeLevel.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClassAgeLevelMockMvc.perform(post("/api/class-age-levels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(classAgeLevel)))
            .andExpect(status().isBadRequest());

        // Validate the ClassAgeLevel in the database
        List<ClassAgeLevel> classAgeLevelList = classAgeLevelRepository.findAll();
        assertThat(classAgeLevelList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllClassAgeLevels() throws Exception {
        // Initialize the database
        classAgeLevelRepository.saveAndFlush(classAgeLevel);

        // Get all the classAgeLevelList
        restClassAgeLevelMockMvc.perform(get("/api/class-age-levels?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(classAgeLevel.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())));
    }

    @Test
    @Transactional
    public void getClassAgeLevel() throws Exception {
        // Initialize the database
        classAgeLevelRepository.saveAndFlush(classAgeLevel);

        // Get the classAgeLevel
        restClassAgeLevelMockMvc.perform(get("/api/class-age-levels/{id}", classAgeLevel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(classAgeLevel.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()));
    }

    @Test
    @Transactional
    public void getAllClassAgeLevelsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        classAgeLevelRepository.saveAndFlush(classAgeLevel);

        // Get all the classAgeLevelList where name equals to DEFAULT_NAME
        defaultClassAgeLevelShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the classAgeLevelList where name equals to UPDATED_NAME
        defaultClassAgeLevelShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllClassAgeLevelsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        classAgeLevelRepository.saveAndFlush(classAgeLevel);

        // Get all the classAgeLevelList where name in DEFAULT_NAME or UPDATED_NAME
        defaultClassAgeLevelShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the classAgeLevelList where name equals to UPDATED_NAME
        defaultClassAgeLevelShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllClassAgeLevelsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        classAgeLevelRepository.saveAndFlush(classAgeLevel);

        // Get all the classAgeLevelList where name is not null
        defaultClassAgeLevelShouldBeFound("name.specified=true");

        // Get all the classAgeLevelList where name is null
        defaultClassAgeLevelShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllClassAgeLevelsByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        classAgeLevelRepository.saveAndFlush(classAgeLevel);

        // Get all the classAgeLevelList where code equals to DEFAULT_CODE
        defaultClassAgeLevelShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the classAgeLevelList where code equals to UPDATED_CODE
        defaultClassAgeLevelShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllClassAgeLevelsByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        classAgeLevelRepository.saveAndFlush(classAgeLevel);

        // Get all the classAgeLevelList where code in DEFAULT_CODE or UPDATED_CODE
        defaultClassAgeLevelShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the classAgeLevelList where code equals to UPDATED_CODE
        defaultClassAgeLevelShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllClassAgeLevelsByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        classAgeLevelRepository.saveAndFlush(classAgeLevel);

        // Get all the classAgeLevelList where code is not null
        defaultClassAgeLevelShouldBeFound("code.specified=true");

        // Get all the classAgeLevelList where code is null
        defaultClassAgeLevelShouldNotBeFound("code.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultClassAgeLevelShouldBeFound(String filter) throws Exception {
        restClassAgeLevelMockMvc.perform(get("/api/class-age-levels?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(classAgeLevel.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultClassAgeLevelShouldNotBeFound(String filter) throws Exception {
        restClassAgeLevelMockMvc.perform(get("/api/class-age-levels?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingClassAgeLevel() throws Exception {
        // Get the classAgeLevel
        restClassAgeLevelMockMvc.perform(get("/api/class-age-levels/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClassAgeLevel() throws Exception {
        // Initialize the database
        classAgeLevelService.save(classAgeLevel);

        int databaseSizeBeforeUpdate = classAgeLevelRepository.findAll().size();

        // Update the classAgeLevel
        ClassAgeLevel updatedClassAgeLevel = classAgeLevelRepository.findOne(classAgeLevel.getId());
        updatedClassAgeLevel
            .name(UPDATED_NAME)
            .code(UPDATED_CODE);

        restClassAgeLevelMockMvc.perform(put("/api/class-age-levels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedClassAgeLevel)))
            .andExpect(status().isOk());

        // Validate the ClassAgeLevel in the database
        List<ClassAgeLevel> classAgeLevelList = classAgeLevelRepository.findAll();
        assertThat(classAgeLevelList).hasSize(databaseSizeBeforeUpdate);
        ClassAgeLevel testClassAgeLevel = classAgeLevelList.get(classAgeLevelList.size() - 1);
        assertThat(testClassAgeLevel.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testClassAgeLevel.getCode()).isEqualTo(UPDATED_CODE);
    }

    @Test
    @Transactional
    public void updateNonExistingClassAgeLevel() throws Exception {
        int databaseSizeBeforeUpdate = classAgeLevelRepository.findAll().size();

        // Create the ClassAgeLevel

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restClassAgeLevelMockMvc.perform(put("/api/class-age-levels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(classAgeLevel)))
            .andExpect(status().isCreated());

        // Validate the ClassAgeLevel in the database
        List<ClassAgeLevel> classAgeLevelList = classAgeLevelRepository.findAll();
        assertThat(classAgeLevelList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteClassAgeLevel() throws Exception {
        // Initialize the database
        classAgeLevelService.save(classAgeLevel);

        int databaseSizeBeforeDelete = classAgeLevelRepository.findAll().size();

        // Get the classAgeLevel
        restClassAgeLevelMockMvc.perform(delete("/api/class-age-levels/{id}", classAgeLevel.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ClassAgeLevel> classAgeLevelList = classAgeLevelRepository.findAll();
        assertThat(classAgeLevelList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClassAgeLevel.class);
        ClassAgeLevel classAgeLevel1 = new ClassAgeLevel();
        classAgeLevel1.setId(1L);
        ClassAgeLevel classAgeLevel2 = new ClassAgeLevel();
        classAgeLevel2.setId(classAgeLevel1.getId());
        assertThat(classAgeLevel1).isEqualTo(classAgeLevel2);
        classAgeLevel2.setId(2L);
        assertThat(classAgeLevel1).isNotEqualTo(classAgeLevel2);
        classAgeLevel1.setId(null);
        assertThat(classAgeLevel1).isNotEqualTo(classAgeLevel2);
    }
}
