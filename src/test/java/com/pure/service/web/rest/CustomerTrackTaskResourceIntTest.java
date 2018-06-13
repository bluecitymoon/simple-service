package com.pure.service.web.rest;

import com.pure.service.SimpleServiceApp;

import com.pure.service.domain.CustomerTrackTask;
import com.pure.service.repository.CustomerTrackTaskRepository;
import com.pure.service.service.CustomerTrackTaskService;
import com.pure.service.web.rest.errors.ExceptionTranslator;
import com.pure.service.service.dto.CustomerTrackTaskCriteria;
import com.pure.service.service.CustomerTrackTaskQueryService;

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
 * Test class for the CustomerTrackTaskResource REST controller.
 *
 * @see CustomerTrackTaskResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SimpleServiceApp.class)
public class CustomerTrackTaskResourceIntTest {

    @Autowired
    private CustomerTrackTaskRepository customerTrackTaskRepository;

    @Autowired
    private CustomerTrackTaskService customerTrackTaskService;

    @Autowired
    private CustomerTrackTaskQueryService customerTrackTaskQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCustomerTrackTaskMockMvc;

    private CustomerTrackTask customerTrackTask;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CustomerTrackTaskResource customerTrackTaskResource = new CustomerTrackTaskResource(customerTrackTaskService, customerTrackTaskQueryService);
        this.restCustomerTrackTaskMockMvc = MockMvcBuilders.standaloneSetup(customerTrackTaskResource)
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
    public static CustomerTrackTask createEntity(EntityManager em) {
        CustomerTrackTask customerTrackTask = new CustomerTrackTask();
        return customerTrackTask;
    }

    @Before
    public void initTest() {
        customerTrackTask = createEntity(em);
    }

    @Test
    @Transactional
    public void createCustomerTrackTask() throws Exception {
        int databaseSizeBeforeCreate = customerTrackTaskRepository.findAll().size();

        // Create the CustomerTrackTask
        restCustomerTrackTaskMockMvc.perform(post("/api/customer-track-tasks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerTrackTask)))
            .andExpect(status().isCreated());

        // Validate the CustomerTrackTask in the database
        List<CustomerTrackTask> customerTrackTaskList = customerTrackTaskRepository.findAll();
        assertThat(customerTrackTaskList).hasSize(databaseSizeBeforeCreate + 1);
        CustomerTrackTask testCustomerTrackTask = customerTrackTaskList.get(customerTrackTaskList.size() - 1);
    }

    @Test
    @Transactional
    public void createCustomerTrackTaskWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = customerTrackTaskRepository.findAll().size();

        // Create the CustomerTrackTask with an existing ID
        customerTrackTask.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustomerTrackTaskMockMvc.perform(post("/api/customer-track-tasks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerTrackTask)))
            .andExpect(status().isBadRequest());

        // Validate the CustomerTrackTask in the database
        List<CustomerTrackTask> customerTrackTaskList = customerTrackTaskRepository.findAll();
        assertThat(customerTrackTaskList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCustomerTrackTasks() throws Exception {
        // Initialize the database
        customerTrackTaskRepository.saveAndFlush(customerTrackTask);

        // Get all the customerTrackTaskList
        restCustomerTrackTaskMockMvc.perform(get("/api/customer-track-tasks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customerTrackTask.getId().intValue())));
    }

    @Test
    @Transactional
    public void getCustomerTrackTask() throws Exception {
        // Initialize the database
        customerTrackTaskRepository.saveAndFlush(customerTrackTask);

        // Get the customerTrackTask
        restCustomerTrackTaskMockMvc.perform(get("/api/customer-track-tasks/{id}", customerTrackTask.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(customerTrackTask.getId().intValue()));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultCustomerTrackTaskShouldBeFound(String filter) throws Exception {
        restCustomerTrackTaskMockMvc.perform(get("/api/customer-track-tasks?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customerTrackTask.getId().intValue())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultCustomerTrackTaskShouldNotBeFound(String filter) throws Exception {
        restCustomerTrackTaskMockMvc.perform(get("/api/customer-track-tasks?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingCustomerTrackTask() throws Exception {
        // Get the customerTrackTask
        restCustomerTrackTaskMockMvc.perform(get("/api/customer-track-tasks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCustomerTrackTask() throws Exception {
        // Initialize the database
        customerTrackTaskService.save(customerTrackTask);

        int databaseSizeBeforeUpdate = customerTrackTaskRepository.findAll().size();

        // Update the customerTrackTask
        CustomerTrackTask updatedCustomerTrackTask = customerTrackTaskRepository.findOne(customerTrackTask.getId());

        restCustomerTrackTaskMockMvc.perform(put("/api/customer-track-tasks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCustomerTrackTask)))
            .andExpect(status().isOk());

        // Validate the CustomerTrackTask in the database
        List<CustomerTrackTask> customerTrackTaskList = customerTrackTaskRepository.findAll();
        assertThat(customerTrackTaskList).hasSize(databaseSizeBeforeUpdate);
        CustomerTrackTask testCustomerTrackTask = customerTrackTaskList.get(customerTrackTaskList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingCustomerTrackTask() throws Exception {
        int databaseSizeBeforeUpdate = customerTrackTaskRepository.findAll().size();

        // Create the CustomerTrackTask

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCustomerTrackTaskMockMvc.perform(put("/api/customer-track-tasks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerTrackTask)))
            .andExpect(status().isCreated());

        // Validate the CustomerTrackTask in the database
        List<CustomerTrackTask> customerTrackTaskList = customerTrackTaskRepository.findAll();
        assertThat(customerTrackTaskList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCustomerTrackTask() throws Exception {
        // Initialize the database
        customerTrackTaskService.save(customerTrackTask);

        int databaseSizeBeforeDelete = customerTrackTaskRepository.findAll().size();

        // Get the customerTrackTask
        restCustomerTrackTaskMockMvc.perform(delete("/api/customer-track-tasks/{id}", customerTrackTask.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CustomerTrackTask> customerTrackTaskList = customerTrackTaskRepository.findAll();
        assertThat(customerTrackTaskList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomerTrackTask.class);
        CustomerTrackTask customerTrackTask1 = new CustomerTrackTask();
        customerTrackTask1.setId(1L);
        CustomerTrackTask customerTrackTask2 = new CustomerTrackTask();
        customerTrackTask2.setId(customerTrackTask1.getId());
        assertThat(customerTrackTask1).isEqualTo(customerTrackTask2);
        customerTrackTask2.setId(2L);
        assertThat(customerTrackTask1).isNotEqualTo(customerTrackTask2);
        customerTrackTask1.setId(null);
        assertThat(customerTrackTask1).isNotEqualTo(customerTrackTask2);
    }
}
