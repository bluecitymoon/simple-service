package com.pure.service.web.rest;

import com.pure.service.SimpleServiceApp;

import com.pure.service.domain.CustomerStatus;
import com.pure.service.repository.CustomerStatusRepository;
import com.pure.service.service.CustomerStatusService;
import com.pure.service.web.rest.errors.ExceptionTranslator;
import com.pure.service.service.dto.CustomerStatusCriteria;
import com.pure.service.service.CustomerStatusQueryService;

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
 * Test class for the CustomerStatusResource REST controller.
 *
 * @see CustomerStatusResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SimpleServiceApp.class)
public class CustomerStatusResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTS = "BBBBBBBBBB";

    private static final String DEFAULT_CSS_STYLE = "AAAAAAAAAA";
    private static final String UPDATED_CSS_STYLE = "BBBBBBBBBB";

    private static final String DEFAULT_ICON = "AAAAAAAAAA";
    private static final String UPDATED_ICON = "BBBBBBBBBB";

    @Autowired
    private CustomerStatusRepository customerStatusRepository;

    @Autowired
    private CustomerStatusService customerStatusService;

    @Autowired
    private CustomerStatusQueryService customerStatusQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCustomerStatusMockMvc;

    private CustomerStatus customerStatus;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CustomerStatusResource customerStatusResource = new CustomerStatusResource(customerStatusService, customerStatusQueryService);
        this.restCustomerStatusMockMvc = MockMvcBuilders.standaloneSetup(customerStatusResource)
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
    public static CustomerStatus createEntity(EntityManager em) {
        CustomerStatus customerStatus = new CustomerStatus()
            .name(DEFAULT_NAME)
            .code(DEFAULT_CODE)
            .comments(DEFAULT_COMMENTS)
            .cssStyle(DEFAULT_CSS_STYLE)
            .icon(DEFAULT_ICON);
        return customerStatus;
    }

    @Before
    public void initTest() {
        customerStatus = createEntity(em);
    }

    @Test
    @Transactional
    public void createCustomerStatus() throws Exception {
        int databaseSizeBeforeCreate = customerStatusRepository.findAll().size();

        // Create the CustomerStatus
        restCustomerStatusMockMvc.perform(post("/api/customer-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerStatus)))
            .andExpect(status().isCreated());

        // Validate the CustomerStatus in the database
        List<CustomerStatus> customerStatusList = customerStatusRepository.findAll();
        assertThat(customerStatusList).hasSize(databaseSizeBeforeCreate + 1);
        CustomerStatus testCustomerStatus = customerStatusList.get(customerStatusList.size() - 1);
        assertThat(testCustomerStatus.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCustomerStatus.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testCustomerStatus.getComments()).isEqualTo(DEFAULT_COMMENTS);
        assertThat(testCustomerStatus.getCssStyle()).isEqualTo(DEFAULT_CSS_STYLE);
        assertThat(testCustomerStatus.getIcon()).isEqualTo(DEFAULT_ICON);
    }

    @Test
    @Transactional
    public void createCustomerStatusWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = customerStatusRepository.findAll().size();

        // Create the CustomerStatus with an existing ID
        customerStatus.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustomerStatusMockMvc.perform(post("/api/customer-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerStatus)))
            .andExpect(status().isBadRequest());

        // Validate the CustomerStatus in the database
        List<CustomerStatus> customerStatusList = customerStatusRepository.findAll();
        assertThat(customerStatusList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCustomerStatuses() throws Exception {
        // Initialize the database
        customerStatusRepository.saveAndFlush(customerStatus);

        // Get all the customerStatusList
        restCustomerStatusMockMvc.perform(get("/api/customer-statuses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customerStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS.toString())))
            .andExpect(jsonPath("$.[*].cssStyle").value(hasItem(DEFAULT_CSS_STYLE.toString())))
            .andExpect(jsonPath("$.[*].icon").value(hasItem(DEFAULT_ICON.toString())));
    }

    @Test
    @Transactional
    public void getCustomerStatus() throws Exception {
        // Initialize the database
        customerStatusRepository.saveAndFlush(customerStatus);

        // Get the customerStatus
        restCustomerStatusMockMvc.perform(get("/api/customer-statuses/{id}", customerStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(customerStatus.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS.toString()))
            .andExpect(jsonPath("$.cssStyle").value(DEFAULT_CSS_STYLE.toString()))
            .andExpect(jsonPath("$.icon").value(DEFAULT_ICON.toString()));
    }

    @Test
    @Transactional
    public void getAllCustomerStatusesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        customerStatusRepository.saveAndFlush(customerStatus);

        // Get all the customerStatusList where name equals to DEFAULT_NAME
        defaultCustomerStatusShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the customerStatusList where name equals to UPDATED_NAME
        defaultCustomerStatusShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCustomerStatusesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        customerStatusRepository.saveAndFlush(customerStatus);

        // Get all the customerStatusList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCustomerStatusShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the customerStatusList where name equals to UPDATED_NAME
        defaultCustomerStatusShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCustomerStatusesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerStatusRepository.saveAndFlush(customerStatus);

        // Get all the customerStatusList where name is not null
        defaultCustomerStatusShouldBeFound("name.specified=true");

        // Get all the customerStatusList where name is null
        defaultCustomerStatusShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllCustomerStatusesByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        customerStatusRepository.saveAndFlush(customerStatus);

        // Get all the customerStatusList where code equals to DEFAULT_CODE
        defaultCustomerStatusShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the customerStatusList where code equals to UPDATED_CODE
        defaultCustomerStatusShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllCustomerStatusesByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        customerStatusRepository.saveAndFlush(customerStatus);

        // Get all the customerStatusList where code in DEFAULT_CODE or UPDATED_CODE
        defaultCustomerStatusShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the customerStatusList where code equals to UPDATED_CODE
        defaultCustomerStatusShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllCustomerStatusesByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerStatusRepository.saveAndFlush(customerStatus);

        // Get all the customerStatusList where code is not null
        defaultCustomerStatusShouldBeFound("code.specified=true");

        // Get all the customerStatusList where code is null
        defaultCustomerStatusShouldNotBeFound("code.specified=false");
    }

    @Test
    @Transactional
    public void getAllCustomerStatusesByCommentsIsEqualToSomething() throws Exception {
        // Initialize the database
        customerStatusRepository.saveAndFlush(customerStatus);

        // Get all the customerStatusList where comments equals to DEFAULT_COMMENTS
        defaultCustomerStatusShouldBeFound("comments.equals=" + DEFAULT_COMMENTS);

        // Get all the customerStatusList where comments equals to UPDATED_COMMENTS
        defaultCustomerStatusShouldNotBeFound("comments.equals=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllCustomerStatusesByCommentsIsInShouldWork() throws Exception {
        // Initialize the database
        customerStatusRepository.saveAndFlush(customerStatus);

        // Get all the customerStatusList where comments in DEFAULT_COMMENTS or UPDATED_COMMENTS
        defaultCustomerStatusShouldBeFound("comments.in=" + DEFAULT_COMMENTS + "," + UPDATED_COMMENTS);

        // Get all the customerStatusList where comments equals to UPDATED_COMMENTS
        defaultCustomerStatusShouldNotBeFound("comments.in=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllCustomerStatusesByCommentsIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerStatusRepository.saveAndFlush(customerStatus);

        // Get all the customerStatusList where comments is not null
        defaultCustomerStatusShouldBeFound("comments.specified=true");

        // Get all the customerStatusList where comments is null
        defaultCustomerStatusShouldNotBeFound("comments.specified=false");
    }

    @Test
    @Transactional
    public void getAllCustomerStatusesByCssStyleIsEqualToSomething() throws Exception {
        // Initialize the database
        customerStatusRepository.saveAndFlush(customerStatus);

        // Get all the customerStatusList where cssStyle equals to DEFAULT_CSS_STYLE
        defaultCustomerStatusShouldBeFound("cssStyle.equals=" + DEFAULT_CSS_STYLE);

        // Get all the customerStatusList where cssStyle equals to UPDATED_CSS_STYLE
        defaultCustomerStatusShouldNotBeFound("cssStyle.equals=" + UPDATED_CSS_STYLE);
    }

    @Test
    @Transactional
    public void getAllCustomerStatusesByCssStyleIsInShouldWork() throws Exception {
        // Initialize the database
        customerStatusRepository.saveAndFlush(customerStatus);

        // Get all the customerStatusList where cssStyle in DEFAULT_CSS_STYLE or UPDATED_CSS_STYLE
        defaultCustomerStatusShouldBeFound("cssStyle.in=" + DEFAULT_CSS_STYLE + "," + UPDATED_CSS_STYLE);

        // Get all the customerStatusList where cssStyle equals to UPDATED_CSS_STYLE
        defaultCustomerStatusShouldNotBeFound("cssStyle.in=" + UPDATED_CSS_STYLE);
    }

    @Test
    @Transactional
    public void getAllCustomerStatusesByCssStyleIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerStatusRepository.saveAndFlush(customerStatus);

        // Get all the customerStatusList where cssStyle is not null
        defaultCustomerStatusShouldBeFound("cssStyle.specified=true");

        // Get all the customerStatusList where cssStyle is null
        defaultCustomerStatusShouldNotBeFound("cssStyle.specified=false");
    }

    @Test
    @Transactional
    public void getAllCustomerStatusesByIconIsEqualToSomething() throws Exception {
        // Initialize the database
        customerStatusRepository.saveAndFlush(customerStatus);

        // Get all the customerStatusList where icon equals to DEFAULT_ICON
        defaultCustomerStatusShouldBeFound("icon.equals=" + DEFAULT_ICON);

        // Get all the customerStatusList where icon equals to UPDATED_ICON
        defaultCustomerStatusShouldNotBeFound("icon.equals=" + UPDATED_ICON);
    }

    @Test
    @Transactional
    public void getAllCustomerStatusesByIconIsInShouldWork() throws Exception {
        // Initialize the database
        customerStatusRepository.saveAndFlush(customerStatus);

        // Get all the customerStatusList where icon in DEFAULT_ICON or UPDATED_ICON
        defaultCustomerStatusShouldBeFound("icon.in=" + DEFAULT_ICON + "," + UPDATED_ICON);

        // Get all the customerStatusList where icon equals to UPDATED_ICON
        defaultCustomerStatusShouldNotBeFound("icon.in=" + UPDATED_ICON);
    }

    @Test
    @Transactional
    public void getAllCustomerStatusesByIconIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerStatusRepository.saveAndFlush(customerStatus);

        // Get all the customerStatusList where icon is not null
        defaultCustomerStatusShouldBeFound("icon.specified=true");

        // Get all the customerStatusList where icon is null
        defaultCustomerStatusShouldNotBeFound("icon.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultCustomerStatusShouldBeFound(String filter) throws Exception {
        restCustomerStatusMockMvc.perform(get("/api/customer-statuses?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customerStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS.toString())))
            .andExpect(jsonPath("$.[*].cssStyle").value(hasItem(DEFAULT_CSS_STYLE.toString())))
            .andExpect(jsonPath("$.[*].icon").value(hasItem(DEFAULT_ICON.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultCustomerStatusShouldNotBeFound(String filter) throws Exception {
        restCustomerStatusMockMvc.perform(get("/api/customer-statuses?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingCustomerStatus() throws Exception {
        // Get the customerStatus
        restCustomerStatusMockMvc.perform(get("/api/customer-statuses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCustomerStatus() throws Exception {
        // Initialize the database
        customerStatusService.save(customerStatus);

        int databaseSizeBeforeUpdate = customerStatusRepository.findAll().size();

        // Update the customerStatus
        CustomerStatus updatedCustomerStatus = customerStatusRepository.findOne(customerStatus.getId());
        updatedCustomerStatus
            .name(UPDATED_NAME)
            .code(UPDATED_CODE)
            .comments(UPDATED_COMMENTS)
            .cssStyle(UPDATED_CSS_STYLE)
            .icon(UPDATED_ICON);

        restCustomerStatusMockMvc.perform(put("/api/customer-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCustomerStatus)))
            .andExpect(status().isOk());

        // Validate the CustomerStatus in the database
        List<CustomerStatus> customerStatusList = customerStatusRepository.findAll();
        assertThat(customerStatusList).hasSize(databaseSizeBeforeUpdate);
        CustomerStatus testCustomerStatus = customerStatusList.get(customerStatusList.size() - 1);
        assertThat(testCustomerStatus.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCustomerStatus.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testCustomerStatus.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testCustomerStatus.getCssStyle()).isEqualTo(UPDATED_CSS_STYLE);
        assertThat(testCustomerStatus.getIcon()).isEqualTo(UPDATED_ICON);
    }

    @Test
    @Transactional
    public void updateNonExistingCustomerStatus() throws Exception {
        int databaseSizeBeforeUpdate = customerStatusRepository.findAll().size();

        // Create the CustomerStatus

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCustomerStatusMockMvc.perform(put("/api/customer-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerStatus)))
            .andExpect(status().isCreated());

        // Validate the CustomerStatus in the database
        List<CustomerStatus> customerStatusList = customerStatusRepository.findAll();
        assertThat(customerStatusList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCustomerStatus() throws Exception {
        // Initialize the database
        customerStatusService.save(customerStatus);

        int databaseSizeBeforeDelete = customerStatusRepository.findAll().size();

        // Get the customerStatus
        restCustomerStatusMockMvc.perform(delete("/api/customer-statuses/{id}", customerStatus.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CustomerStatus> customerStatusList = customerStatusRepository.findAll();
        assertThat(customerStatusList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomerStatus.class);
        CustomerStatus customerStatus1 = new CustomerStatus();
        customerStatus1.setId(1L);
        CustomerStatus customerStatus2 = new CustomerStatus();
        customerStatus2.setId(customerStatus1.getId());
        assertThat(customerStatus1).isEqualTo(customerStatus2);
        customerStatus2.setId(2L);
        assertThat(customerStatus1).isNotEqualTo(customerStatus2);
        customerStatus1.setId(null);
        assertThat(customerStatus1).isNotEqualTo(customerStatus2);
    }
}
