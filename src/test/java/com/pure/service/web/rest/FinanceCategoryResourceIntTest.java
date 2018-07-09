package com.pure.service.web.rest;

import com.pure.service.SimpleServiceApp;

import com.pure.service.domain.FinanceCategory;
import com.pure.service.repository.FinanceCategoryRepository;
import com.pure.service.service.FinanceCategoryService;
import com.pure.service.web.rest.errors.ExceptionTranslator;
import com.pure.service.service.dto.FinanceCategoryCriteria;
import com.pure.service.service.FinanceCategoryQueryService;

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
 * Test class for the FinanceCategoryResource REST controller.
 *
 * @see FinanceCategoryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SimpleServiceApp.class)
public class FinanceCategoryResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTS = "BBBBBBBBBB";

    @Autowired
    private FinanceCategoryRepository financeCategoryRepository;

    @Autowired
    private FinanceCategoryService financeCategoryService;

    @Autowired
    private FinanceCategoryQueryService financeCategoryQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFinanceCategoryMockMvc;

    private FinanceCategory financeCategory;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FinanceCategoryResource financeCategoryResource = new FinanceCategoryResource(financeCategoryService, financeCategoryQueryService);
        this.restFinanceCategoryMockMvc = MockMvcBuilders.standaloneSetup(financeCategoryResource)
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
    public static FinanceCategory createEntity(EntityManager em) {
        FinanceCategory financeCategory = new FinanceCategory()
            .name(DEFAULT_NAME)
            .code(DEFAULT_CODE)
            .comments(DEFAULT_COMMENTS);
        return financeCategory;
    }

    @Before
    public void initTest() {
        financeCategory = createEntity(em);
    }

    @Test
    @Transactional
    public void createFinanceCategory() throws Exception {
        int databaseSizeBeforeCreate = financeCategoryRepository.findAll().size();

        // Create the FinanceCategory
        restFinanceCategoryMockMvc.perform(post("/api/finance-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(financeCategory)))
            .andExpect(status().isCreated());

        // Validate the FinanceCategory in the database
        List<FinanceCategory> financeCategoryList = financeCategoryRepository.findAll();
        assertThat(financeCategoryList).hasSize(databaseSizeBeforeCreate + 1);
        FinanceCategory testFinanceCategory = financeCategoryList.get(financeCategoryList.size() - 1);
        assertThat(testFinanceCategory.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testFinanceCategory.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testFinanceCategory.getComments()).isEqualTo(DEFAULT_COMMENTS);
    }

    @Test
    @Transactional
    public void createFinanceCategoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = financeCategoryRepository.findAll().size();

        // Create the FinanceCategory with an existing ID
        financeCategory.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFinanceCategoryMockMvc.perform(post("/api/finance-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(financeCategory)))
            .andExpect(status().isBadRequest());

        // Validate the FinanceCategory in the database
        List<FinanceCategory> financeCategoryList = financeCategoryRepository.findAll();
        assertThat(financeCategoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllFinanceCategories() throws Exception {
        // Initialize the database
        financeCategoryRepository.saveAndFlush(financeCategory);

        // Get all the financeCategoryList
        restFinanceCategoryMockMvc.perform(get("/api/finance-categories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(financeCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS.toString())));
    }

    @Test
    @Transactional
    public void getFinanceCategory() throws Exception {
        // Initialize the database
        financeCategoryRepository.saveAndFlush(financeCategory);

        // Get the financeCategory
        restFinanceCategoryMockMvc.perform(get("/api/finance-categories/{id}", financeCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(financeCategory.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS.toString()));
    }

    @Test
    @Transactional
    public void getAllFinanceCategoriesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        financeCategoryRepository.saveAndFlush(financeCategory);

        // Get all the financeCategoryList where name equals to DEFAULT_NAME
        defaultFinanceCategoryShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the financeCategoryList where name equals to UPDATED_NAME
        defaultFinanceCategoryShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllFinanceCategoriesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        financeCategoryRepository.saveAndFlush(financeCategory);

        // Get all the financeCategoryList where name in DEFAULT_NAME or UPDATED_NAME
        defaultFinanceCategoryShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the financeCategoryList where name equals to UPDATED_NAME
        defaultFinanceCategoryShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllFinanceCategoriesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        financeCategoryRepository.saveAndFlush(financeCategory);

        // Get all the financeCategoryList where name is not null
        defaultFinanceCategoryShouldBeFound("name.specified=true");

        // Get all the financeCategoryList where name is null
        defaultFinanceCategoryShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllFinanceCategoriesByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        financeCategoryRepository.saveAndFlush(financeCategory);

        // Get all the financeCategoryList where code equals to DEFAULT_CODE
        defaultFinanceCategoryShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the financeCategoryList where code equals to UPDATED_CODE
        defaultFinanceCategoryShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllFinanceCategoriesByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        financeCategoryRepository.saveAndFlush(financeCategory);

        // Get all the financeCategoryList where code in DEFAULT_CODE or UPDATED_CODE
        defaultFinanceCategoryShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the financeCategoryList where code equals to UPDATED_CODE
        defaultFinanceCategoryShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllFinanceCategoriesByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        financeCategoryRepository.saveAndFlush(financeCategory);

        // Get all the financeCategoryList where code is not null
        defaultFinanceCategoryShouldBeFound("code.specified=true");

        // Get all the financeCategoryList where code is null
        defaultFinanceCategoryShouldNotBeFound("code.specified=false");
    }

    @Test
    @Transactional
    public void getAllFinanceCategoriesByCommentsIsEqualToSomething() throws Exception {
        // Initialize the database
        financeCategoryRepository.saveAndFlush(financeCategory);

        // Get all the financeCategoryList where comments equals to DEFAULT_COMMENTS
        defaultFinanceCategoryShouldBeFound("comments.equals=" + DEFAULT_COMMENTS);

        // Get all the financeCategoryList where comments equals to UPDATED_COMMENTS
        defaultFinanceCategoryShouldNotBeFound("comments.equals=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllFinanceCategoriesByCommentsIsInShouldWork() throws Exception {
        // Initialize the database
        financeCategoryRepository.saveAndFlush(financeCategory);

        // Get all the financeCategoryList where comments in DEFAULT_COMMENTS or UPDATED_COMMENTS
        defaultFinanceCategoryShouldBeFound("comments.in=" + DEFAULT_COMMENTS + "," + UPDATED_COMMENTS);

        // Get all the financeCategoryList where comments equals to UPDATED_COMMENTS
        defaultFinanceCategoryShouldNotBeFound("comments.in=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllFinanceCategoriesByCommentsIsNullOrNotNull() throws Exception {
        // Initialize the database
        financeCategoryRepository.saveAndFlush(financeCategory);

        // Get all the financeCategoryList where comments is not null
        defaultFinanceCategoryShouldBeFound("comments.specified=true");

        // Get all the financeCategoryList where comments is null
        defaultFinanceCategoryShouldNotBeFound("comments.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultFinanceCategoryShouldBeFound(String filter) throws Exception {
        restFinanceCategoryMockMvc.perform(get("/api/finance-categories?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(financeCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultFinanceCategoryShouldNotBeFound(String filter) throws Exception {
        restFinanceCategoryMockMvc.perform(get("/api/finance-categories?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingFinanceCategory() throws Exception {
        // Get the financeCategory
        restFinanceCategoryMockMvc.perform(get("/api/finance-categories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFinanceCategory() throws Exception {
        // Initialize the database
        financeCategoryService.save(financeCategory);

        int databaseSizeBeforeUpdate = financeCategoryRepository.findAll().size();

        // Update the financeCategory
        FinanceCategory updatedFinanceCategory = financeCategoryRepository.findOne(financeCategory.getId());
        updatedFinanceCategory
            .name(UPDATED_NAME)
            .code(UPDATED_CODE)
            .comments(UPDATED_COMMENTS);

        restFinanceCategoryMockMvc.perform(put("/api/finance-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFinanceCategory)))
            .andExpect(status().isOk());

        // Validate the FinanceCategory in the database
        List<FinanceCategory> financeCategoryList = financeCategoryRepository.findAll();
        assertThat(financeCategoryList).hasSize(databaseSizeBeforeUpdate);
        FinanceCategory testFinanceCategory = financeCategoryList.get(financeCategoryList.size() - 1);
        assertThat(testFinanceCategory.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFinanceCategory.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testFinanceCategory.getComments()).isEqualTo(UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    public void updateNonExistingFinanceCategory() throws Exception {
        int databaseSizeBeforeUpdate = financeCategoryRepository.findAll().size();

        // Create the FinanceCategory

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFinanceCategoryMockMvc.perform(put("/api/finance-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(financeCategory)))
            .andExpect(status().isCreated());

        // Validate the FinanceCategory in the database
        List<FinanceCategory> financeCategoryList = financeCategoryRepository.findAll();
        assertThat(financeCategoryList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteFinanceCategory() throws Exception {
        // Initialize the database
        financeCategoryService.save(financeCategory);

        int databaseSizeBeforeDelete = financeCategoryRepository.findAll().size();

        // Get the financeCategory
        restFinanceCategoryMockMvc.perform(delete("/api/finance-categories/{id}", financeCategory.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<FinanceCategory> financeCategoryList = financeCategoryRepository.findAll();
        assertThat(financeCategoryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FinanceCategory.class);
        FinanceCategory financeCategory1 = new FinanceCategory();
        financeCategory1.setId(1L);
        FinanceCategory financeCategory2 = new FinanceCategory();
        financeCategory2.setId(financeCategory1.getId());
        assertThat(financeCategory1).isEqualTo(financeCategory2);
        financeCategory2.setId(2L);
        assertThat(financeCategory1).isNotEqualTo(financeCategory2);
        financeCategory1.setId(null);
        assertThat(financeCategory1).isNotEqualTo(financeCategory2);
    }
}
