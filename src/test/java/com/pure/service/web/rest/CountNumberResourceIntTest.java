package com.pure.service.web.rest;

import com.pure.service.SimpleServiceApp;

import com.pure.service.domain.CountNumber;
import com.pure.service.repository.CountNumberRepository;
import com.pure.service.service.CountNumberService;
import com.pure.service.web.rest.errors.ExceptionTranslator;
import com.pure.service.service.dto.CountNumberCriteria;
import com.pure.service.service.CountNumberQueryService;

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
 * Test class for the CountNumberResource REST controller.
 *
 * @see CountNumberResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SimpleServiceApp.class)
public class CountNumberResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_VALUE = 1;
    private static final Integer UPDATED_VALUE = 2;

    @Autowired
    private CountNumberRepository countNumberRepository;

    @Autowired
    private CountNumberService countNumberService;

    @Autowired
    private CountNumberQueryService countNumberQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCountNumberMockMvc;

    private CountNumber countNumber;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CountNumberResource countNumberResource = new CountNumberResource(countNumberService, countNumberQueryService);
        this.restCountNumberMockMvc = MockMvcBuilders.standaloneSetup(countNumberResource)
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
    public static CountNumber createEntity(EntityManager em) {
        CountNumber countNumber = new CountNumber()
            .name(DEFAULT_NAME)
            .value(DEFAULT_VALUE);
        return countNumber;
    }

    @Before
    public void initTest() {
        countNumber = createEntity(em);
    }

    @Test
    @Transactional
    public void createCountNumber() throws Exception {
        int databaseSizeBeforeCreate = countNumberRepository.findAll().size();

        // Create the CountNumber
        restCountNumberMockMvc.perform(post("/api/count-numbers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(countNumber)))
            .andExpect(status().isCreated());

        // Validate the CountNumber in the database
        List<CountNumber> countNumberList = countNumberRepository.findAll();
        assertThat(countNumberList).hasSize(databaseSizeBeforeCreate + 1);
        CountNumber testCountNumber = countNumberList.get(countNumberList.size() - 1);
        assertThat(testCountNumber.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCountNumber.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void createCountNumberWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = countNumberRepository.findAll().size();

        // Create the CountNumber with an existing ID
        countNumber.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCountNumberMockMvc.perform(post("/api/count-numbers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(countNumber)))
            .andExpect(status().isBadRequest());

        // Validate the CountNumber in the database
        List<CountNumber> countNumberList = countNumberRepository.findAll();
        assertThat(countNumberList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCountNumbers() throws Exception {
        // Initialize the database
        countNumberRepository.saveAndFlush(countNumber);

        // Get all the countNumberList
        restCountNumberMockMvc.perform(get("/api/count-numbers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(countNumber.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)));
    }

    @Test
    @Transactional
    public void getCountNumber() throws Exception {
        // Initialize the database
        countNumberRepository.saveAndFlush(countNumber);

        // Get the countNumber
        restCountNumberMockMvc.perform(get("/api/count-numbers/{id}", countNumber.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(countNumber.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE));
    }

    @Test
    @Transactional
    public void getAllCountNumbersByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        countNumberRepository.saveAndFlush(countNumber);

        // Get all the countNumberList where name equals to DEFAULT_NAME
        defaultCountNumberShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the countNumberList where name equals to UPDATED_NAME
        defaultCountNumberShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCountNumbersByNameIsInShouldWork() throws Exception {
        // Initialize the database
        countNumberRepository.saveAndFlush(countNumber);

        // Get all the countNumberList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCountNumberShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the countNumberList where name equals to UPDATED_NAME
        defaultCountNumberShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCountNumbersByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        countNumberRepository.saveAndFlush(countNumber);

        // Get all the countNumberList where name is not null
        defaultCountNumberShouldBeFound("name.specified=true");

        // Get all the countNumberList where name is null
        defaultCountNumberShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllCountNumbersByValueIsEqualToSomething() throws Exception {
        // Initialize the database
        countNumberRepository.saveAndFlush(countNumber);

        // Get all the countNumberList where value equals to DEFAULT_VALUE
        defaultCountNumberShouldBeFound("value.equals=" + DEFAULT_VALUE);

        // Get all the countNumberList where value equals to UPDATED_VALUE
        defaultCountNumberShouldNotBeFound("value.equals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllCountNumbersByValueIsInShouldWork() throws Exception {
        // Initialize the database
        countNumberRepository.saveAndFlush(countNumber);

        // Get all the countNumberList where value in DEFAULT_VALUE or UPDATED_VALUE
        defaultCountNumberShouldBeFound("value.in=" + DEFAULT_VALUE + "," + UPDATED_VALUE);

        // Get all the countNumberList where value equals to UPDATED_VALUE
        defaultCountNumberShouldNotBeFound("value.in=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllCountNumbersByValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        countNumberRepository.saveAndFlush(countNumber);

        // Get all the countNumberList where value is not null
        defaultCountNumberShouldBeFound("value.specified=true");

        // Get all the countNumberList where value is null
        defaultCountNumberShouldNotBeFound("value.specified=false");
    }

    @Test
    @Transactional
    public void getAllCountNumbersByValueIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        countNumberRepository.saveAndFlush(countNumber);

        // Get all the countNumberList where value greater than or equals to DEFAULT_VALUE
        defaultCountNumberShouldBeFound("value.greaterOrEqualThan=" + DEFAULT_VALUE);

        // Get all the countNumberList where value greater than or equals to UPDATED_VALUE
        defaultCountNumberShouldNotBeFound("value.greaterOrEqualThan=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllCountNumbersByValueIsLessThanSomething() throws Exception {
        // Initialize the database
        countNumberRepository.saveAndFlush(countNumber);

        // Get all the countNumberList where value less than or equals to DEFAULT_VALUE
        defaultCountNumberShouldNotBeFound("value.lessThan=" + DEFAULT_VALUE);

        // Get all the countNumberList where value less than or equals to UPDATED_VALUE
        defaultCountNumberShouldBeFound("value.lessThan=" + UPDATED_VALUE);
    }


    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultCountNumberShouldBeFound(String filter) throws Exception {
        restCountNumberMockMvc.perform(get("/api/count-numbers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(countNumber.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultCountNumberShouldNotBeFound(String filter) throws Exception {
        restCountNumberMockMvc.perform(get("/api/count-numbers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingCountNumber() throws Exception {
        // Get the countNumber
        restCountNumberMockMvc.perform(get("/api/count-numbers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCountNumber() throws Exception {
        // Initialize the database
        countNumberService.save(countNumber);

        int databaseSizeBeforeUpdate = countNumberRepository.findAll().size();

        // Update the countNumber
        CountNumber updatedCountNumber = countNumberRepository.findOne(countNumber.getId());
        updatedCountNumber
            .name(UPDATED_NAME)
            .value(UPDATED_VALUE);

        restCountNumberMockMvc.perform(put("/api/count-numbers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCountNumber)))
            .andExpect(status().isOk());

        // Validate the CountNumber in the database
        List<CountNumber> countNumberList = countNumberRepository.findAll();
        assertThat(countNumberList).hasSize(databaseSizeBeforeUpdate);
        CountNumber testCountNumber = countNumberList.get(countNumberList.size() - 1);
        assertThat(testCountNumber.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCountNumber.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingCountNumber() throws Exception {
        int databaseSizeBeforeUpdate = countNumberRepository.findAll().size();

        // Create the CountNumber

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCountNumberMockMvc.perform(put("/api/count-numbers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(countNumber)))
            .andExpect(status().isCreated());

        // Validate the CountNumber in the database
        List<CountNumber> countNumberList = countNumberRepository.findAll();
        assertThat(countNumberList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCountNumber() throws Exception {
        // Initialize the database
        countNumberService.save(countNumber);

        int databaseSizeBeforeDelete = countNumberRepository.findAll().size();

        // Get the countNumber
        restCountNumberMockMvc.perform(delete("/api/count-numbers/{id}", countNumber.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CountNumber> countNumberList = countNumberRepository.findAll();
        assertThat(countNumberList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CountNumber.class);
        CountNumber countNumber1 = new CountNumber();
        countNumber1.setId(1L);
        CountNumber countNumber2 = new CountNumber();
        countNumber2.setId(countNumber1.getId());
        assertThat(countNumber1).isEqualTo(countNumber2);
        countNumber2.setId(2L);
        assertThat(countNumber1).isNotEqualTo(countNumber2);
        countNumber1.setId(null);
        assertThat(countNumber1).isNotEqualTo(countNumber2);
    }
}
