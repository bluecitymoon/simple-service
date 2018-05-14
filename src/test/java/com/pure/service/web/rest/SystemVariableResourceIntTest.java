package com.pure.service.web.rest;

import com.pure.service.SimpleServiceApp;

import com.pure.service.domain.SystemVariable;
import com.pure.service.repository.SystemVariableRepository;
import com.pure.service.service.SystemVariableService;
import com.pure.service.web.rest.errors.ExceptionTranslator;
import com.pure.service.service.dto.SystemVariableCriteria;
import com.pure.service.service.SystemVariableQueryService;

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
 * Test class for the SystemVariableResource REST controller.
 *
 * @see SystemVariableResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SimpleServiceApp.class)
public class SystemVariableResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ATTR_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_ATTR_VALUE = "BBBBBBBBBB";

    @Autowired
    private SystemVariableRepository systemVariableRepository;

    @Autowired
    private SystemVariableService systemVariableService;

    @Autowired
    private SystemVariableQueryService systemVariableQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSystemVariableMockMvc;

    private SystemVariable systemVariable;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SystemVariableResource systemVariableResource = new SystemVariableResource(systemVariableService, systemVariableQueryService);
        this.restSystemVariableMockMvc = MockMvcBuilders.standaloneSetup(systemVariableResource)
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
    public static SystemVariable createEntity(EntityManager em) {
        SystemVariable systemVariable = new SystemVariable()
            .name(DEFAULT_NAME)
            .attrValue(DEFAULT_ATTR_VALUE);
        return systemVariable;
    }

    @Before
    public void initTest() {
        systemVariable = createEntity(em);
    }

    @Test
    @Transactional
    public void createSystemVariable() throws Exception {
        int databaseSizeBeforeCreate = systemVariableRepository.findAll().size();

        // Create the SystemVariable
        restSystemVariableMockMvc.perform(post("/api/system-variables")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(systemVariable)))
            .andExpect(status().isCreated());

        // Validate the SystemVariable in the database
        List<SystemVariable> systemVariableList = systemVariableRepository.findAll();
        assertThat(systemVariableList).hasSize(databaseSizeBeforeCreate + 1);
        SystemVariable testSystemVariable = systemVariableList.get(systemVariableList.size() - 1);
        assertThat(testSystemVariable.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSystemVariable.getAttrValue()).isEqualTo(DEFAULT_ATTR_VALUE);
    }

    @Test
    @Transactional
    public void createSystemVariableWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = systemVariableRepository.findAll().size();

        // Create the SystemVariable with an existing ID
        systemVariable.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSystemVariableMockMvc.perform(post("/api/system-variables")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(systemVariable)))
            .andExpect(status().isBadRequest());

        // Validate the SystemVariable in the database
        List<SystemVariable> systemVariableList = systemVariableRepository.findAll();
        assertThat(systemVariableList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSystemVariables() throws Exception {
        // Initialize the database
        systemVariableRepository.saveAndFlush(systemVariable);

        // Get all the systemVariableList
        restSystemVariableMockMvc.perform(get("/api/system-variables?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(systemVariable.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].attrValue").value(hasItem(DEFAULT_ATTR_VALUE.toString())));
    }

    @Test
    @Transactional
    public void getSystemVariable() throws Exception {
        // Initialize the database
        systemVariableRepository.saveAndFlush(systemVariable);

        // Get the systemVariable
        restSystemVariableMockMvc.perform(get("/api/system-variables/{id}", systemVariable.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(systemVariable.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.attrValue").value(DEFAULT_ATTR_VALUE.toString()));
    }

    @Test
    @Transactional
    public void getAllSystemVariablesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        systemVariableRepository.saveAndFlush(systemVariable);

        // Get all the systemVariableList where name equals to DEFAULT_NAME
        defaultSystemVariableShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the systemVariableList where name equals to UPDATED_NAME
        defaultSystemVariableShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllSystemVariablesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        systemVariableRepository.saveAndFlush(systemVariable);

        // Get all the systemVariableList where name in DEFAULT_NAME or UPDATED_NAME
        defaultSystemVariableShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the systemVariableList where name equals to UPDATED_NAME
        defaultSystemVariableShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllSystemVariablesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        systemVariableRepository.saveAndFlush(systemVariable);

        // Get all the systemVariableList where name is not null
        defaultSystemVariableShouldBeFound("name.specified=true");

        // Get all the systemVariableList where name is null
        defaultSystemVariableShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllSystemVariablesByAttrValueIsEqualToSomething() throws Exception {
        // Initialize the database
        systemVariableRepository.saveAndFlush(systemVariable);

        // Get all the systemVariableList where attrValue equals to DEFAULT_ATTR_VALUE
        defaultSystemVariableShouldBeFound("attrValue.equals=" + DEFAULT_ATTR_VALUE);

        // Get all the systemVariableList where attrValue equals to UPDATED_ATTR_VALUE
        defaultSystemVariableShouldNotBeFound("attrValue.equals=" + UPDATED_ATTR_VALUE);
    }

    @Test
    @Transactional
    public void getAllSystemVariablesByAttrValueIsInShouldWork() throws Exception {
        // Initialize the database
        systemVariableRepository.saveAndFlush(systemVariable);

        // Get all the systemVariableList where attrValue in DEFAULT_ATTR_VALUE or UPDATED_ATTR_VALUE
        defaultSystemVariableShouldBeFound("attrValue.in=" + DEFAULT_ATTR_VALUE + "," + UPDATED_ATTR_VALUE);

        // Get all the systemVariableList where attrValue equals to UPDATED_ATTR_VALUE
        defaultSystemVariableShouldNotBeFound("attrValue.in=" + UPDATED_ATTR_VALUE);
    }

    @Test
    @Transactional
    public void getAllSystemVariablesByAttrValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        systemVariableRepository.saveAndFlush(systemVariable);

        // Get all the systemVariableList where attrValue is not null
        defaultSystemVariableShouldBeFound("attrValue.specified=true");

        // Get all the systemVariableList where attrValue is null
        defaultSystemVariableShouldNotBeFound("attrValue.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultSystemVariableShouldBeFound(String filter) throws Exception {
        restSystemVariableMockMvc.perform(get("/api/system-variables?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(systemVariable.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].attrValue").value(hasItem(DEFAULT_ATTR_VALUE.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultSystemVariableShouldNotBeFound(String filter) throws Exception {
        restSystemVariableMockMvc.perform(get("/api/system-variables?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingSystemVariable() throws Exception {
        // Get the systemVariable
        restSystemVariableMockMvc.perform(get("/api/system-variables/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSystemVariable() throws Exception {
        // Initialize the database
        systemVariableService.save(systemVariable);

        int databaseSizeBeforeUpdate = systemVariableRepository.findAll().size();

        // Update the systemVariable
        SystemVariable updatedSystemVariable = systemVariableRepository.findOne(systemVariable.getId());
        updatedSystemVariable
            .name(UPDATED_NAME)
            .attrValue(UPDATED_ATTR_VALUE);

        restSystemVariableMockMvc.perform(put("/api/system-variables")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSystemVariable)))
            .andExpect(status().isOk());

        // Validate the SystemVariable in the database
        List<SystemVariable> systemVariableList = systemVariableRepository.findAll();
        assertThat(systemVariableList).hasSize(databaseSizeBeforeUpdate);
        SystemVariable testSystemVariable = systemVariableList.get(systemVariableList.size() - 1);
        assertThat(testSystemVariable.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSystemVariable.getAttrValue()).isEqualTo(UPDATED_ATTR_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingSystemVariable() throws Exception {
        int databaseSizeBeforeUpdate = systemVariableRepository.findAll().size();

        // Create the SystemVariable

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSystemVariableMockMvc.perform(put("/api/system-variables")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(systemVariable)))
            .andExpect(status().isCreated());

        // Validate the SystemVariable in the database
        List<SystemVariable> systemVariableList = systemVariableRepository.findAll();
        assertThat(systemVariableList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSystemVariable() throws Exception {
        // Initialize the database
        systemVariableService.save(systemVariable);

        int databaseSizeBeforeDelete = systemVariableRepository.findAll().size();

        // Get the systemVariable
        restSystemVariableMockMvc.perform(delete("/api/system-variables/{id}", systemVariable.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SystemVariable> systemVariableList = systemVariableRepository.findAll();
        assertThat(systemVariableList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SystemVariable.class);
        SystemVariable systemVariable1 = new SystemVariable();
        systemVariable1.setId(1L);
        SystemVariable systemVariable2 = new SystemVariable();
        systemVariable2.setId(systemVariable1.getId());
        assertThat(systemVariable1).isEqualTo(systemVariable2);
        systemVariable2.setId(2L);
        assertThat(systemVariable1).isNotEqualTo(systemVariable2);
        systemVariable1.setId(null);
        assertThat(systemVariable1).isNotEqualTo(systemVariable2);
    }
}
