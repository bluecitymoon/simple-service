package com.pure.service.web.rest;

import com.pure.service.SimpleServiceApp;

import com.pure.service.domain.EducationLevel;
import com.pure.service.repository.EducationLevelRepository;
import com.pure.service.service.EducationLevelService;
import com.pure.service.web.rest.errors.ExceptionTranslator;
import com.pure.service.service.dto.EducationLevelCriteria;
import com.pure.service.service.EducationLevelQueryService;

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
 * Test class for the EducationLevelResource REST controller.
 *
 * @see EducationLevelResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SimpleServiceApp.class)
public class EducationLevelResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    @Autowired
    private EducationLevelRepository educationLevelRepository;

    @Autowired
    private EducationLevelService educationLevelService;

    @Autowired
    private EducationLevelQueryService educationLevelQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEducationLevelMockMvc;

    private EducationLevel educationLevel;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EducationLevelResource educationLevelResource = new EducationLevelResource(educationLevelService, educationLevelQueryService);
        this.restEducationLevelMockMvc = MockMvcBuilders.standaloneSetup(educationLevelResource)
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
    public static EducationLevel createEntity(EntityManager em) {
        EducationLevel educationLevel = new EducationLevel()
            .name(DEFAULT_NAME)
            .code(DEFAULT_CODE);
        return educationLevel;
    }

    @Before
    public void initTest() {
        educationLevel = createEntity(em);
    }

    @Test
    @Transactional
    public void createEducationLevel() throws Exception {
        int databaseSizeBeforeCreate = educationLevelRepository.findAll().size();

        // Create the EducationLevel
        restEducationLevelMockMvc.perform(post("/api/education-levels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(educationLevel)))
            .andExpect(status().isCreated());

        // Validate the EducationLevel in the database
        List<EducationLevel> educationLevelList = educationLevelRepository.findAll();
        assertThat(educationLevelList).hasSize(databaseSizeBeforeCreate + 1);
        EducationLevel testEducationLevel = educationLevelList.get(educationLevelList.size() - 1);
        assertThat(testEducationLevel.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testEducationLevel.getCode()).isEqualTo(DEFAULT_CODE);
    }

    @Test
    @Transactional
    public void createEducationLevelWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = educationLevelRepository.findAll().size();

        // Create the EducationLevel with an existing ID
        educationLevel.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEducationLevelMockMvc.perform(post("/api/education-levels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(educationLevel)))
            .andExpect(status().isBadRequest());

        // Validate the EducationLevel in the database
        List<EducationLevel> educationLevelList = educationLevelRepository.findAll();
        assertThat(educationLevelList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllEducationLevels() throws Exception {
        // Initialize the database
        educationLevelRepository.saveAndFlush(educationLevel);

        // Get all the educationLevelList
        restEducationLevelMockMvc.perform(get("/api/education-levels?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(educationLevel.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())));
    }

    @Test
    @Transactional
    public void getEducationLevel() throws Exception {
        // Initialize the database
        educationLevelRepository.saveAndFlush(educationLevel);

        // Get the educationLevel
        restEducationLevelMockMvc.perform(get("/api/education-levels/{id}", educationLevel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(educationLevel.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()));
    }

    @Test
    @Transactional
    public void getAllEducationLevelsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        educationLevelRepository.saveAndFlush(educationLevel);

        // Get all the educationLevelList where name equals to DEFAULT_NAME
        defaultEducationLevelShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the educationLevelList where name equals to UPDATED_NAME
        defaultEducationLevelShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllEducationLevelsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        educationLevelRepository.saveAndFlush(educationLevel);

        // Get all the educationLevelList where name in DEFAULT_NAME or UPDATED_NAME
        defaultEducationLevelShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the educationLevelList where name equals to UPDATED_NAME
        defaultEducationLevelShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllEducationLevelsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        educationLevelRepository.saveAndFlush(educationLevel);

        // Get all the educationLevelList where name is not null
        defaultEducationLevelShouldBeFound("name.specified=true");

        // Get all the educationLevelList where name is null
        defaultEducationLevelShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllEducationLevelsByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        educationLevelRepository.saveAndFlush(educationLevel);

        // Get all the educationLevelList where code equals to DEFAULT_CODE
        defaultEducationLevelShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the educationLevelList where code equals to UPDATED_CODE
        defaultEducationLevelShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllEducationLevelsByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        educationLevelRepository.saveAndFlush(educationLevel);

        // Get all the educationLevelList where code in DEFAULT_CODE or UPDATED_CODE
        defaultEducationLevelShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the educationLevelList where code equals to UPDATED_CODE
        defaultEducationLevelShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllEducationLevelsByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        educationLevelRepository.saveAndFlush(educationLevel);

        // Get all the educationLevelList where code is not null
        defaultEducationLevelShouldBeFound("code.specified=true");

        // Get all the educationLevelList where code is null
        defaultEducationLevelShouldNotBeFound("code.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultEducationLevelShouldBeFound(String filter) throws Exception {
        restEducationLevelMockMvc.perform(get("/api/education-levels?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(educationLevel.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultEducationLevelShouldNotBeFound(String filter) throws Exception {
        restEducationLevelMockMvc.perform(get("/api/education-levels?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingEducationLevel() throws Exception {
        // Get the educationLevel
        restEducationLevelMockMvc.perform(get("/api/education-levels/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEducationLevel() throws Exception {
        // Initialize the database
        educationLevelService.save(educationLevel);

        int databaseSizeBeforeUpdate = educationLevelRepository.findAll().size();

        // Update the educationLevel
        EducationLevel updatedEducationLevel = educationLevelRepository.findOne(educationLevel.getId());
        updatedEducationLevel
            .name(UPDATED_NAME)
            .code(UPDATED_CODE);

        restEducationLevelMockMvc.perform(put("/api/education-levels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEducationLevel)))
            .andExpect(status().isOk());

        // Validate the EducationLevel in the database
        List<EducationLevel> educationLevelList = educationLevelRepository.findAll();
        assertThat(educationLevelList).hasSize(databaseSizeBeforeUpdate);
        EducationLevel testEducationLevel = educationLevelList.get(educationLevelList.size() - 1);
        assertThat(testEducationLevel.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testEducationLevel.getCode()).isEqualTo(UPDATED_CODE);
    }

    @Test
    @Transactional
    public void updateNonExistingEducationLevel() throws Exception {
        int databaseSizeBeforeUpdate = educationLevelRepository.findAll().size();

        // Create the EducationLevel

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEducationLevelMockMvc.perform(put("/api/education-levels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(educationLevel)))
            .andExpect(status().isCreated());

        // Validate the EducationLevel in the database
        List<EducationLevel> educationLevelList = educationLevelRepository.findAll();
        assertThat(educationLevelList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteEducationLevel() throws Exception {
        // Initialize the database
        educationLevelService.save(educationLevel);

        int databaseSizeBeforeDelete = educationLevelRepository.findAll().size();

        // Get the educationLevel
        restEducationLevelMockMvc.perform(delete("/api/education-levels/{id}", educationLevel.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<EducationLevel> educationLevelList = educationLevelRepository.findAll();
        assertThat(educationLevelList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EducationLevel.class);
        EducationLevel educationLevel1 = new EducationLevel();
        educationLevel1.setId(1L);
        EducationLevel educationLevel2 = new EducationLevel();
        educationLevel2.setId(educationLevel1.getId());
        assertThat(educationLevel1).isEqualTo(educationLevel2);
        educationLevel2.setId(2L);
        assertThat(educationLevel1).isNotEqualTo(educationLevel2);
        educationLevel1.setId(null);
        assertThat(educationLevel1).isNotEqualTo(educationLevel2);
    }
}
