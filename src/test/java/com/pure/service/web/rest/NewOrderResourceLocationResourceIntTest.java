package com.pure.service.web.rest;

import com.pure.service.SimpleServiceApp;

import com.pure.service.domain.NewOrderResourceLocation;
import com.pure.service.repository.NewOrderResourceLocationRepository;
import com.pure.service.service.NewOrderResourceLocationService;
import com.pure.service.web.rest.errors.ExceptionTranslator;
import com.pure.service.service.dto.NewOrderResourceLocationCriteria;
import com.pure.service.service.NewOrderResourceLocationQueryService;

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
 * Test class for the NewOrderResourceLocationResource REST controller.
 *
 * @see NewOrderResourceLocationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SimpleServiceApp.class)
public class NewOrderResourceLocationResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private NewOrderResourceLocationRepository newOrderResourceLocationRepository;

    @Autowired
    private NewOrderResourceLocationService newOrderResourceLocationService;

    @Autowired
    private NewOrderResourceLocationQueryService newOrderResourceLocationQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restNewOrderResourceLocationMockMvc;

    private NewOrderResourceLocation newOrderResourceLocation;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final NewOrderResourceLocationResource newOrderResourceLocationResource = new NewOrderResourceLocationResource(newOrderResourceLocationService, newOrderResourceLocationQueryService);
        this.restNewOrderResourceLocationMockMvc = MockMvcBuilders.standaloneSetup(newOrderResourceLocationResource)
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
    public static NewOrderResourceLocation createEntity(EntityManager em) {
        NewOrderResourceLocation newOrderResourceLocation = new NewOrderResourceLocation()
            .name(DEFAULT_NAME)
            .code(DEFAULT_CODE)
            .description(DEFAULT_DESCRIPTION);
        return newOrderResourceLocation;
    }

    @Before
    public void initTest() {
        newOrderResourceLocation = createEntity(em);
    }

    @Test
    @Transactional
    public void createNewOrderResourceLocation() throws Exception {
        int databaseSizeBeforeCreate = newOrderResourceLocationRepository.findAll().size();

        // Create the NewOrderResourceLocation
        restNewOrderResourceLocationMockMvc.perform(post("/api/new-order-resource-locations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(newOrderResourceLocation)))
            .andExpect(status().isCreated());

        // Validate the NewOrderResourceLocation in the database
        List<NewOrderResourceLocation> newOrderResourceLocationList = newOrderResourceLocationRepository.findAll();
        assertThat(newOrderResourceLocationList).hasSize(databaseSizeBeforeCreate + 1);
        NewOrderResourceLocation testNewOrderResourceLocation = newOrderResourceLocationList.get(newOrderResourceLocationList.size() - 1);
        assertThat(testNewOrderResourceLocation.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testNewOrderResourceLocation.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testNewOrderResourceLocation.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createNewOrderResourceLocationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = newOrderResourceLocationRepository.findAll().size();

        // Create the NewOrderResourceLocation with an existing ID
        newOrderResourceLocation.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNewOrderResourceLocationMockMvc.perform(post("/api/new-order-resource-locations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(newOrderResourceLocation)))
            .andExpect(status().isBadRequest());

        // Validate the NewOrderResourceLocation in the database
        List<NewOrderResourceLocation> newOrderResourceLocationList = newOrderResourceLocationRepository.findAll();
        assertThat(newOrderResourceLocationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllNewOrderResourceLocations() throws Exception {
        // Initialize the database
        newOrderResourceLocationRepository.saveAndFlush(newOrderResourceLocation);

        // Get all the newOrderResourceLocationList
        restNewOrderResourceLocationMockMvc.perform(get("/api/new-order-resource-locations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(newOrderResourceLocation.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    public void getNewOrderResourceLocation() throws Exception {
        // Initialize the database
        newOrderResourceLocationRepository.saveAndFlush(newOrderResourceLocation);

        // Get the newOrderResourceLocation
        restNewOrderResourceLocationMockMvc.perform(get("/api/new-order-resource-locations/{id}", newOrderResourceLocation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(newOrderResourceLocation.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    public void getAllNewOrderResourceLocationsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        newOrderResourceLocationRepository.saveAndFlush(newOrderResourceLocation);

        // Get all the newOrderResourceLocationList where name equals to DEFAULT_NAME
        defaultNewOrderResourceLocationShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the newOrderResourceLocationList where name equals to UPDATED_NAME
        defaultNewOrderResourceLocationShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllNewOrderResourceLocationsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        newOrderResourceLocationRepository.saveAndFlush(newOrderResourceLocation);

        // Get all the newOrderResourceLocationList where name in DEFAULT_NAME or UPDATED_NAME
        defaultNewOrderResourceLocationShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the newOrderResourceLocationList where name equals to UPDATED_NAME
        defaultNewOrderResourceLocationShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllNewOrderResourceLocationsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        newOrderResourceLocationRepository.saveAndFlush(newOrderResourceLocation);

        // Get all the newOrderResourceLocationList where name is not null
        defaultNewOrderResourceLocationShouldBeFound("name.specified=true");

        // Get all the newOrderResourceLocationList where name is null
        defaultNewOrderResourceLocationShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllNewOrderResourceLocationsByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        newOrderResourceLocationRepository.saveAndFlush(newOrderResourceLocation);

        // Get all the newOrderResourceLocationList where code equals to DEFAULT_CODE
        defaultNewOrderResourceLocationShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the newOrderResourceLocationList where code equals to UPDATED_CODE
        defaultNewOrderResourceLocationShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllNewOrderResourceLocationsByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        newOrderResourceLocationRepository.saveAndFlush(newOrderResourceLocation);

        // Get all the newOrderResourceLocationList where code in DEFAULT_CODE or UPDATED_CODE
        defaultNewOrderResourceLocationShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the newOrderResourceLocationList where code equals to UPDATED_CODE
        defaultNewOrderResourceLocationShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllNewOrderResourceLocationsByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        newOrderResourceLocationRepository.saveAndFlush(newOrderResourceLocation);

        // Get all the newOrderResourceLocationList where code is not null
        defaultNewOrderResourceLocationShouldBeFound("code.specified=true");

        // Get all the newOrderResourceLocationList where code is null
        defaultNewOrderResourceLocationShouldNotBeFound("code.specified=false");
    }

    @Test
    @Transactional
    public void getAllNewOrderResourceLocationsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        newOrderResourceLocationRepository.saveAndFlush(newOrderResourceLocation);

        // Get all the newOrderResourceLocationList where description equals to DEFAULT_DESCRIPTION
        defaultNewOrderResourceLocationShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the newOrderResourceLocationList where description equals to UPDATED_DESCRIPTION
        defaultNewOrderResourceLocationShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllNewOrderResourceLocationsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        newOrderResourceLocationRepository.saveAndFlush(newOrderResourceLocation);

        // Get all the newOrderResourceLocationList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultNewOrderResourceLocationShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the newOrderResourceLocationList where description equals to UPDATED_DESCRIPTION
        defaultNewOrderResourceLocationShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllNewOrderResourceLocationsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        newOrderResourceLocationRepository.saveAndFlush(newOrderResourceLocation);

        // Get all the newOrderResourceLocationList where description is not null
        defaultNewOrderResourceLocationShouldBeFound("description.specified=true");

        // Get all the newOrderResourceLocationList where description is null
        defaultNewOrderResourceLocationShouldNotBeFound("description.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultNewOrderResourceLocationShouldBeFound(String filter) throws Exception {
        restNewOrderResourceLocationMockMvc.perform(get("/api/new-order-resource-locations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(newOrderResourceLocation.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultNewOrderResourceLocationShouldNotBeFound(String filter) throws Exception {
        restNewOrderResourceLocationMockMvc.perform(get("/api/new-order-resource-locations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingNewOrderResourceLocation() throws Exception {
        // Get the newOrderResourceLocation
        restNewOrderResourceLocationMockMvc.perform(get("/api/new-order-resource-locations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNewOrderResourceLocation() throws Exception {
        // Initialize the database
        newOrderResourceLocationService.save(newOrderResourceLocation);

        int databaseSizeBeforeUpdate = newOrderResourceLocationRepository.findAll().size();

        // Update the newOrderResourceLocation
        NewOrderResourceLocation updatedNewOrderResourceLocation = newOrderResourceLocationRepository.findOne(newOrderResourceLocation.getId());
        updatedNewOrderResourceLocation
            .name(UPDATED_NAME)
            .code(UPDATED_CODE)
            .description(UPDATED_DESCRIPTION);

        restNewOrderResourceLocationMockMvc.perform(put("/api/new-order-resource-locations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedNewOrderResourceLocation)))
            .andExpect(status().isOk());

        // Validate the NewOrderResourceLocation in the database
        List<NewOrderResourceLocation> newOrderResourceLocationList = newOrderResourceLocationRepository.findAll();
        assertThat(newOrderResourceLocationList).hasSize(databaseSizeBeforeUpdate);
        NewOrderResourceLocation testNewOrderResourceLocation = newOrderResourceLocationList.get(newOrderResourceLocationList.size() - 1);
        assertThat(testNewOrderResourceLocation.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testNewOrderResourceLocation.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testNewOrderResourceLocation.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingNewOrderResourceLocation() throws Exception {
        int databaseSizeBeforeUpdate = newOrderResourceLocationRepository.findAll().size();

        // Create the NewOrderResourceLocation

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restNewOrderResourceLocationMockMvc.perform(put("/api/new-order-resource-locations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(newOrderResourceLocation)))
            .andExpect(status().isCreated());

        // Validate the NewOrderResourceLocation in the database
        List<NewOrderResourceLocation> newOrderResourceLocationList = newOrderResourceLocationRepository.findAll();
        assertThat(newOrderResourceLocationList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteNewOrderResourceLocation() throws Exception {
        // Initialize the database
        newOrderResourceLocationService.save(newOrderResourceLocation);

        int databaseSizeBeforeDelete = newOrderResourceLocationRepository.findAll().size();

        // Get the newOrderResourceLocation
        restNewOrderResourceLocationMockMvc.perform(delete("/api/new-order-resource-locations/{id}", newOrderResourceLocation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<NewOrderResourceLocation> newOrderResourceLocationList = newOrderResourceLocationRepository.findAll();
        assertThat(newOrderResourceLocationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NewOrderResourceLocation.class);
        NewOrderResourceLocation newOrderResourceLocation1 = new NewOrderResourceLocation();
        newOrderResourceLocation1.setId(1L);
        NewOrderResourceLocation newOrderResourceLocation2 = new NewOrderResourceLocation();
        newOrderResourceLocation2.setId(newOrderResourceLocation1.getId());
        assertThat(newOrderResourceLocation1).isEqualTo(newOrderResourceLocation2);
        newOrderResourceLocation2.setId(2L);
        assertThat(newOrderResourceLocation1).isNotEqualTo(newOrderResourceLocation2);
        newOrderResourceLocation1.setId(null);
        assertThat(newOrderResourceLocation1).isNotEqualTo(newOrderResourceLocation2);
    }
}
