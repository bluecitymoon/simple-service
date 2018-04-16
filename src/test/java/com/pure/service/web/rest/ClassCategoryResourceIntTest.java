package com.pure.service.web.rest;

import com.pure.service.SimpleServiceApp;

import com.pure.service.domain.ClassCategory;
import com.pure.service.repository.ClassCategoryRepository;
import com.pure.service.web.rest.errors.ExceptionTranslator;

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
 * Test class for the ClassCategoryResource REST controller.
 *
 * @see ClassCategoryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SimpleServiceApp.class)
public class ClassCategoryResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private ClassCategoryRepository classCategoryRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restClassCategoryMockMvc;

    private ClassCategory classCategory;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ClassCategoryResource classCategoryResource = new ClassCategoryResource(classCategoryRepository);
        this.restClassCategoryMockMvc = MockMvcBuilders.standaloneSetup(classCategoryResource)
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
    public static ClassCategory createEntity(EntityManager em) {
        ClassCategory classCategory = new ClassCategory()
            .name(DEFAULT_NAME)
            .code(DEFAULT_CODE)
            .description(DEFAULT_DESCRIPTION);
        return classCategory;
    }

    @Before
    public void initTest() {
        classCategory = createEntity(em);
    }

    @Test
    @Transactional
    public void createClassCategory() throws Exception {
        int databaseSizeBeforeCreate = classCategoryRepository.findAll().size();

        // Create the ClassCategory
        restClassCategoryMockMvc.perform(post("/api/class-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(classCategory)))
            .andExpect(status().isCreated());

        // Validate the ClassCategory in the database
        List<ClassCategory> classCategoryList = classCategoryRepository.findAll();
        assertThat(classCategoryList).hasSize(databaseSizeBeforeCreate + 1);
        ClassCategory testClassCategory = classCategoryList.get(classCategoryList.size() - 1);
        assertThat(testClassCategory.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testClassCategory.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testClassCategory.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createClassCategoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = classCategoryRepository.findAll().size();

        // Create the ClassCategory with an existing ID
        classCategory.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClassCategoryMockMvc.perform(post("/api/class-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(classCategory)))
            .andExpect(status().isBadRequest());

        // Validate the ClassCategory in the database
        List<ClassCategory> classCategoryList = classCategoryRepository.findAll();
        assertThat(classCategoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllClassCategories() throws Exception {
        // Initialize the database
        classCategoryRepository.saveAndFlush(classCategory);

        // Get all the classCategoryList
        restClassCategoryMockMvc.perform(get("/api/class-categories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(classCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getClassCategory() throws Exception {
        // Initialize the database
        classCategoryRepository.saveAndFlush(classCategory);

        // Get the classCategory
        restClassCategoryMockMvc.perform(get("/api/class-categories/{id}", classCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(classCategory.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingClassCategory() throws Exception {
        // Get the classCategory
        restClassCategoryMockMvc.perform(get("/api/class-categories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClassCategory() throws Exception {
        // Initialize the database
        classCategoryRepository.saveAndFlush(classCategory);
        int databaseSizeBeforeUpdate = classCategoryRepository.findAll().size();

        // Update the classCategory
        ClassCategory updatedClassCategory = classCategoryRepository.findOne(classCategory.getId());
        updatedClassCategory
            .name(UPDATED_NAME)
            .code(UPDATED_CODE)
            .description(UPDATED_DESCRIPTION);

        restClassCategoryMockMvc.perform(put("/api/class-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedClassCategory)))
            .andExpect(status().isOk());

        // Validate the ClassCategory in the database
        List<ClassCategory> classCategoryList = classCategoryRepository.findAll();
        assertThat(classCategoryList).hasSize(databaseSizeBeforeUpdate);
        ClassCategory testClassCategory = classCategoryList.get(classCategoryList.size() - 1);
        assertThat(testClassCategory.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testClassCategory.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testClassCategory.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingClassCategory() throws Exception {
        int databaseSizeBeforeUpdate = classCategoryRepository.findAll().size();

        // Create the ClassCategory

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restClassCategoryMockMvc.perform(put("/api/class-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(classCategory)))
            .andExpect(status().isCreated());

        // Validate the ClassCategory in the database
        List<ClassCategory> classCategoryList = classCategoryRepository.findAll();
        assertThat(classCategoryList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteClassCategory() throws Exception {
        // Initialize the database
        classCategoryRepository.saveAndFlush(classCategory);
        int databaseSizeBeforeDelete = classCategoryRepository.findAll().size();

        // Get the classCategory
        restClassCategoryMockMvc.perform(delete("/api/class-categories/{id}", classCategory.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ClassCategory> classCategoryList = classCategoryRepository.findAll();
        assertThat(classCategoryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClassCategory.class);
        ClassCategory classCategory1 = new ClassCategory();
        classCategory1.setId(1L);
        ClassCategory classCategory2 = new ClassCategory();
        classCategory2.setId(classCategory1.getId());
        assertThat(classCategory1).isEqualTo(classCategory2);
        classCategory2.setId(2L);
        assertThat(classCategory1).isNotEqualTo(classCategory2);
        classCategory1.setId(null);
        assertThat(classCategory1).isNotEqualTo(classCategory2);
    }
}
