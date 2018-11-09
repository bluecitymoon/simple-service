package com.pure.service.web.rest;

import com.pure.service.SimpleServiceApp;

import com.pure.service.domain.ClassCategoryBase;
import com.pure.service.repository.ClassCategoryBaseRepository;
import com.pure.service.service.ClassCategoryBaseService;
import com.pure.service.web.rest.errors.ExceptionTranslator;
import com.pure.service.service.dto.ClassCategoryBaseCriteria;
import com.pure.service.service.ClassCategoryBaseQueryService;

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
 * Test class for the ClassCategoryBaseResource REST controller.
 *
 * @see ClassCategoryBaseResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SimpleServiceApp.class)
public class ClassCategoryBaseResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    @Autowired
    private ClassCategoryBaseRepository classCategoryBaseRepository;

    @Autowired
    private ClassCategoryBaseService classCategoryBaseService;

    @Autowired
    private ClassCategoryBaseQueryService classCategoryBaseQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restClassCategoryBaseMockMvc;

    private ClassCategoryBase classCategoryBase;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ClassCategoryBaseResource classCategoryBaseResource = new ClassCategoryBaseResource(classCategoryBaseService, classCategoryBaseQueryService);
        this.restClassCategoryBaseMockMvc = MockMvcBuilders.standaloneSetup(classCategoryBaseResource)
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
    public static ClassCategoryBase createEntity(EntityManager em) {
        ClassCategoryBase classCategoryBase = new ClassCategoryBase()
            .name(DEFAULT_NAME)
            .code(DEFAULT_CODE);
        return classCategoryBase;
    }

    @Before
    public void initTest() {
        classCategoryBase = createEntity(em);
    }

    @Test
    @Transactional
    public void createClassCategoryBase() throws Exception {
        int databaseSizeBeforeCreate = classCategoryBaseRepository.findAll().size();

        // Create the ClassCategoryBase
        restClassCategoryBaseMockMvc.perform(post("/api/class-category-bases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(classCategoryBase)))
            .andExpect(status().isCreated());

        // Validate the ClassCategoryBase in the database
        List<ClassCategoryBase> classCategoryBaseList = classCategoryBaseRepository.findAll();
        assertThat(classCategoryBaseList).hasSize(databaseSizeBeforeCreate + 1);
        ClassCategoryBase testClassCategoryBase = classCategoryBaseList.get(classCategoryBaseList.size() - 1);
        assertThat(testClassCategoryBase.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testClassCategoryBase.getCode()).isEqualTo(DEFAULT_CODE);
    }

    @Test
    @Transactional
    public void createClassCategoryBaseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = classCategoryBaseRepository.findAll().size();

        // Create the ClassCategoryBase with an existing ID
        classCategoryBase.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClassCategoryBaseMockMvc.perform(post("/api/class-category-bases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(classCategoryBase)))
            .andExpect(status().isBadRequest());

        // Validate the ClassCategoryBase in the database
        List<ClassCategoryBase> classCategoryBaseList = classCategoryBaseRepository.findAll();
        assertThat(classCategoryBaseList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllClassCategoryBases() throws Exception {
        // Initialize the database
        classCategoryBaseRepository.saveAndFlush(classCategoryBase);

        // Get all the classCategoryBaseList
        restClassCategoryBaseMockMvc.perform(get("/api/class-category-bases?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(classCategoryBase.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())));
    }

    @Test
    @Transactional
    public void getClassCategoryBase() throws Exception {
        // Initialize the database
        classCategoryBaseRepository.saveAndFlush(classCategoryBase);

        // Get the classCategoryBase
        restClassCategoryBaseMockMvc.perform(get("/api/class-category-bases/{id}", classCategoryBase.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(classCategoryBase.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()));
    }

    @Test
    @Transactional
    public void getAllClassCategoryBasesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        classCategoryBaseRepository.saveAndFlush(classCategoryBase);

        // Get all the classCategoryBaseList where name equals to DEFAULT_NAME
        defaultClassCategoryBaseShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the classCategoryBaseList where name equals to UPDATED_NAME
        defaultClassCategoryBaseShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllClassCategoryBasesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        classCategoryBaseRepository.saveAndFlush(classCategoryBase);

        // Get all the classCategoryBaseList where name in DEFAULT_NAME or UPDATED_NAME
        defaultClassCategoryBaseShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the classCategoryBaseList where name equals to UPDATED_NAME
        defaultClassCategoryBaseShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllClassCategoryBasesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        classCategoryBaseRepository.saveAndFlush(classCategoryBase);

        // Get all the classCategoryBaseList where name is not null
        defaultClassCategoryBaseShouldBeFound("name.specified=true");

        // Get all the classCategoryBaseList where name is null
        defaultClassCategoryBaseShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllClassCategoryBasesByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        classCategoryBaseRepository.saveAndFlush(classCategoryBase);

        // Get all the classCategoryBaseList where code equals to DEFAULT_CODE
        defaultClassCategoryBaseShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the classCategoryBaseList where code equals to UPDATED_CODE
        defaultClassCategoryBaseShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllClassCategoryBasesByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        classCategoryBaseRepository.saveAndFlush(classCategoryBase);

        // Get all the classCategoryBaseList where code in DEFAULT_CODE or UPDATED_CODE
        defaultClassCategoryBaseShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the classCategoryBaseList where code equals to UPDATED_CODE
        defaultClassCategoryBaseShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllClassCategoryBasesByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        classCategoryBaseRepository.saveAndFlush(classCategoryBase);

        // Get all the classCategoryBaseList where code is not null
        defaultClassCategoryBaseShouldBeFound("code.specified=true");

        // Get all the classCategoryBaseList where code is null
        defaultClassCategoryBaseShouldNotBeFound("code.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultClassCategoryBaseShouldBeFound(String filter) throws Exception {
        restClassCategoryBaseMockMvc.perform(get("/api/class-category-bases?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(classCategoryBase.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultClassCategoryBaseShouldNotBeFound(String filter) throws Exception {
        restClassCategoryBaseMockMvc.perform(get("/api/class-category-bases?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingClassCategoryBase() throws Exception {
        // Get the classCategoryBase
        restClassCategoryBaseMockMvc.perform(get("/api/class-category-bases/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClassCategoryBase() throws Exception {
        // Initialize the database
        classCategoryBaseService.save(classCategoryBase);

        int databaseSizeBeforeUpdate = classCategoryBaseRepository.findAll().size();

        // Update the classCategoryBase
        ClassCategoryBase updatedClassCategoryBase = classCategoryBaseRepository.findOne(classCategoryBase.getId());
        updatedClassCategoryBase
            .name(UPDATED_NAME)
            .code(UPDATED_CODE);

        restClassCategoryBaseMockMvc.perform(put("/api/class-category-bases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedClassCategoryBase)))
            .andExpect(status().isOk());

        // Validate the ClassCategoryBase in the database
        List<ClassCategoryBase> classCategoryBaseList = classCategoryBaseRepository.findAll();
        assertThat(classCategoryBaseList).hasSize(databaseSizeBeforeUpdate);
        ClassCategoryBase testClassCategoryBase = classCategoryBaseList.get(classCategoryBaseList.size() - 1);
        assertThat(testClassCategoryBase.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testClassCategoryBase.getCode()).isEqualTo(UPDATED_CODE);
    }

    @Test
    @Transactional
    public void updateNonExistingClassCategoryBase() throws Exception {
        int databaseSizeBeforeUpdate = classCategoryBaseRepository.findAll().size();

        // Create the ClassCategoryBase

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restClassCategoryBaseMockMvc.perform(put("/api/class-category-bases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(classCategoryBase)))
            .andExpect(status().isCreated());

        // Validate the ClassCategoryBase in the database
        List<ClassCategoryBase> classCategoryBaseList = classCategoryBaseRepository.findAll();
        assertThat(classCategoryBaseList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteClassCategoryBase() throws Exception {
        // Initialize the database
        classCategoryBaseService.save(classCategoryBase);

        int databaseSizeBeforeDelete = classCategoryBaseRepository.findAll().size();

        // Get the classCategoryBase
        restClassCategoryBaseMockMvc.perform(delete("/api/class-category-bases/{id}", classCategoryBase.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ClassCategoryBase> classCategoryBaseList = classCategoryBaseRepository.findAll();
        assertThat(classCategoryBaseList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClassCategoryBase.class);
        ClassCategoryBase classCategoryBase1 = new ClassCategoryBase();
        classCategoryBase1.setId(1L);
        ClassCategoryBase classCategoryBase2 = new ClassCategoryBase();
        classCategoryBase2.setId(classCategoryBase1.getId());
        assertThat(classCategoryBase1).isEqualTo(classCategoryBase2);
        classCategoryBase2.setId(2L);
        assertThat(classCategoryBase1).isNotEqualTo(classCategoryBase2);
        classCategoryBase1.setId(null);
        assertThat(classCategoryBase1).isNotEqualTo(classCategoryBase2);
    }
}
