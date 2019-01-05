package com.pure.service.web.rest;

import com.pure.service.SimpleServiceApp;

import com.pure.service.domain.CustomerCardCourse;
import com.pure.service.repository.CustomerCardCourseRepository;
import com.pure.service.service.CustomerCardCourseService;
import com.pure.service.web.rest.errors.ExceptionTranslator;
import com.pure.service.service.dto.CustomerCardCourseCriteria;
import com.pure.service.service.CustomerCardCourseQueryService;

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
 * Test class for the CustomerCardCourseResource REST controller.
 *
 * @see CustomerCardCourseResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SimpleServiceApp.class)
public class CustomerCardCourseResourceIntTest {

    @Autowired
    private CustomerCardCourseRepository customerCardCourseRepository;

    @Autowired
    private CustomerCardCourseService customerCardCourseService;

    @Autowired
    private CustomerCardCourseQueryService customerCardCourseQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCustomerCardCourseMockMvc;

    private CustomerCardCourse customerCardCourse;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CustomerCardCourseResource customerCardCourseResource = new CustomerCardCourseResource(customerCardCourseService, customerCardCourseQueryService);
        this.restCustomerCardCourseMockMvc = MockMvcBuilders.standaloneSetup(customerCardCourseResource)
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
    public static CustomerCardCourse createEntity(EntityManager em) {
        CustomerCardCourse customerCardCourse = new CustomerCardCourse();
        return customerCardCourse;
    }

    @Before
    public void initTest() {
        customerCardCourse = createEntity(em);
    }

    @Test
    @Transactional
    public void createCustomerCardCourse() throws Exception {
        int databaseSizeBeforeCreate = customerCardCourseRepository.findAll().size();

        // Create the CustomerCardCourse
        restCustomerCardCourseMockMvc.perform(post("/api/customer-card-courses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerCardCourse)))
            .andExpect(status().isCreated());

        // Validate the CustomerCardCourse in the database
        List<CustomerCardCourse> customerCardCourseList = customerCardCourseRepository.findAll();
        assertThat(customerCardCourseList).hasSize(databaseSizeBeforeCreate + 1);
        CustomerCardCourse testCustomerCardCourse = customerCardCourseList.get(customerCardCourseList.size() - 1);
    }

    @Test
    @Transactional
    public void createCustomerCardCourseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = customerCardCourseRepository.findAll().size();

        // Create the CustomerCardCourse with an existing ID
        customerCardCourse.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustomerCardCourseMockMvc.perform(post("/api/customer-card-courses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerCardCourse)))
            .andExpect(status().isBadRequest());

        // Validate the CustomerCardCourse in the database
        List<CustomerCardCourse> customerCardCourseList = customerCardCourseRepository.findAll();
        assertThat(customerCardCourseList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCustomerCardCourses() throws Exception {
        // Initialize the database
        customerCardCourseRepository.saveAndFlush(customerCardCourse);

        // Get all the customerCardCourseList
        restCustomerCardCourseMockMvc.perform(get("/api/customer-card-courses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customerCardCourse.getId().intValue())));
    }

    @Test
    @Transactional
    public void getCustomerCardCourse() throws Exception {
        // Initialize the database
        customerCardCourseRepository.saveAndFlush(customerCardCourse);

        // Get the customerCardCourse
        restCustomerCardCourseMockMvc.perform(get("/api/customer-card-courses/{id}", customerCardCourse.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(customerCardCourse.getId().intValue()));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultCustomerCardCourseShouldBeFound(String filter) throws Exception {
        restCustomerCardCourseMockMvc.perform(get("/api/customer-card-courses?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customerCardCourse.getId().intValue())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultCustomerCardCourseShouldNotBeFound(String filter) throws Exception {
        restCustomerCardCourseMockMvc.perform(get("/api/customer-card-courses?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingCustomerCardCourse() throws Exception {
        // Get the customerCardCourse
        restCustomerCardCourseMockMvc.perform(get("/api/customer-card-courses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCustomerCardCourse() throws Exception {
        // Initialize the database
        customerCardCourseService.save(customerCardCourse);

        int databaseSizeBeforeUpdate = customerCardCourseRepository.findAll().size();

        // Update the customerCardCourse
        CustomerCardCourse updatedCustomerCardCourse = customerCardCourseRepository.findOne(customerCardCourse.getId());

        restCustomerCardCourseMockMvc.perform(put("/api/customer-card-courses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCustomerCardCourse)))
            .andExpect(status().isOk());

        // Validate the CustomerCardCourse in the database
        List<CustomerCardCourse> customerCardCourseList = customerCardCourseRepository.findAll();
        assertThat(customerCardCourseList).hasSize(databaseSizeBeforeUpdate);
        CustomerCardCourse testCustomerCardCourse = customerCardCourseList.get(customerCardCourseList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingCustomerCardCourse() throws Exception {
        int databaseSizeBeforeUpdate = customerCardCourseRepository.findAll().size();

        // Create the CustomerCardCourse

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCustomerCardCourseMockMvc.perform(put("/api/customer-card-courses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerCardCourse)))
            .andExpect(status().isCreated());

        // Validate the CustomerCardCourse in the database
        List<CustomerCardCourse> customerCardCourseList = customerCardCourseRepository.findAll();
        assertThat(customerCardCourseList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCustomerCardCourse() throws Exception {
        // Initialize the database
        customerCardCourseService.save(customerCardCourse);

        int databaseSizeBeforeDelete = customerCardCourseRepository.findAll().size();

        // Get the customerCardCourse
        restCustomerCardCourseMockMvc.perform(delete("/api/customer-card-courses/{id}", customerCardCourse.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CustomerCardCourse> customerCardCourseList = customerCardCourseRepository.findAll();
        assertThat(customerCardCourseList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomerCardCourse.class);
        CustomerCardCourse customerCardCourse1 = new CustomerCardCourse();
        customerCardCourse1.setId(1L);
        CustomerCardCourse customerCardCourse2 = new CustomerCardCourse();
        customerCardCourse2.setId(customerCardCourse1.getId());
        assertThat(customerCardCourse1).isEqualTo(customerCardCourse2);
        customerCardCourse2.setId(2L);
        assertThat(customerCardCourse1).isNotEqualTo(customerCardCourse2);
        customerCardCourse1.setId(null);
        assertThat(customerCardCourse1).isNotEqualTo(customerCardCourse2);
    }
}
