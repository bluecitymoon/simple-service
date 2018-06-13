package com.pure.service.web.rest;

import com.pure.service.SimpleServiceApp;

import com.pure.service.domain.TaskStatus;
import com.pure.service.repository.TaskStatusRepository;
import com.pure.service.service.TaskStatusService;
import com.pure.service.web.rest.errors.ExceptionTranslator;
import com.pure.service.service.dto.TaskStatusCriteria;
import com.pure.service.service.TaskStatusQueryService;

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
 * Test class for the TaskStatusResource REST controller.
 *
 * @see TaskStatusResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SimpleServiceApp.class)
public class TaskStatusResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_LABEL_CSS = "AAAAAAAAAA";
    private static final String UPDATED_LABEL_CSS = "BBBBBBBBBB";

    @Autowired
    private TaskStatusRepository taskStatusRepository;

    @Autowired
    private TaskStatusService taskStatusService;

    @Autowired
    private TaskStatusQueryService taskStatusQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTaskStatusMockMvc;

    private TaskStatus taskStatus;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TaskStatusResource taskStatusResource = new TaskStatusResource(taskStatusService, taskStatusQueryService);
        this.restTaskStatusMockMvc = MockMvcBuilders.standaloneSetup(taskStatusResource)
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
    public static TaskStatus createEntity(EntityManager em) {
        TaskStatus taskStatus = new TaskStatus()
            .name(DEFAULT_NAME)
            .code(DEFAULT_CODE)
            .labelCss(DEFAULT_LABEL_CSS);
        return taskStatus;
    }

    @Before
    public void initTest() {
        taskStatus = createEntity(em);
    }

    @Test
    @Transactional
    public void createTaskStatus() throws Exception {
        int databaseSizeBeforeCreate = taskStatusRepository.findAll().size();

        // Create the TaskStatus
        restTaskStatusMockMvc.perform(post("/api/task-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(taskStatus)))
            .andExpect(status().isCreated());

        // Validate the TaskStatus in the database
        List<TaskStatus> taskStatusList = taskStatusRepository.findAll();
        assertThat(taskStatusList).hasSize(databaseSizeBeforeCreate + 1);
        TaskStatus testTaskStatus = taskStatusList.get(taskStatusList.size() - 1);
        assertThat(testTaskStatus.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTaskStatus.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testTaskStatus.getLabelCss()).isEqualTo(DEFAULT_LABEL_CSS);
    }

    @Test
    @Transactional
    public void createTaskStatusWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = taskStatusRepository.findAll().size();

        // Create the TaskStatus with an existing ID
        taskStatus.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTaskStatusMockMvc.perform(post("/api/task-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(taskStatus)))
            .andExpect(status().isBadRequest());

        // Validate the TaskStatus in the database
        List<TaskStatus> taskStatusList = taskStatusRepository.findAll();
        assertThat(taskStatusList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTaskStatuses() throws Exception {
        // Initialize the database
        taskStatusRepository.saveAndFlush(taskStatus);

        // Get all the taskStatusList
        restTaskStatusMockMvc.perform(get("/api/task-statuses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(taskStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].labelCss").value(hasItem(DEFAULT_LABEL_CSS.toString())));
    }

    @Test
    @Transactional
    public void getTaskStatus() throws Exception {
        // Initialize the database
        taskStatusRepository.saveAndFlush(taskStatus);

        // Get the taskStatus
        restTaskStatusMockMvc.perform(get("/api/task-statuses/{id}", taskStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(taskStatus.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.labelCss").value(DEFAULT_LABEL_CSS.toString()));
    }

    @Test
    @Transactional
    public void getAllTaskStatusesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        taskStatusRepository.saveAndFlush(taskStatus);

        // Get all the taskStatusList where name equals to DEFAULT_NAME
        defaultTaskStatusShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the taskStatusList where name equals to UPDATED_NAME
        defaultTaskStatusShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTaskStatusesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        taskStatusRepository.saveAndFlush(taskStatus);

        // Get all the taskStatusList where name in DEFAULT_NAME or UPDATED_NAME
        defaultTaskStatusShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the taskStatusList where name equals to UPDATED_NAME
        defaultTaskStatusShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTaskStatusesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        taskStatusRepository.saveAndFlush(taskStatus);

        // Get all the taskStatusList where name is not null
        defaultTaskStatusShouldBeFound("name.specified=true");

        // Get all the taskStatusList where name is null
        defaultTaskStatusShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllTaskStatusesByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        taskStatusRepository.saveAndFlush(taskStatus);

        // Get all the taskStatusList where code equals to DEFAULT_CODE
        defaultTaskStatusShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the taskStatusList where code equals to UPDATED_CODE
        defaultTaskStatusShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllTaskStatusesByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        taskStatusRepository.saveAndFlush(taskStatus);

        // Get all the taskStatusList where code in DEFAULT_CODE or UPDATED_CODE
        defaultTaskStatusShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the taskStatusList where code equals to UPDATED_CODE
        defaultTaskStatusShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllTaskStatusesByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        taskStatusRepository.saveAndFlush(taskStatus);

        // Get all the taskStatusList where code is not null
        defaultTaskStatusShouldBeFound("code.specified=true");

        // Get all the taskStatusList where code is null
        defaultTaskStatusShouldNotBeFound("code.specified=false");
    }

    @Test
    @Transactional
    public void getAllTaskStatusesByLabelCssIsEqualToSomething() throws Exception {
        // Initialize the database
        taskStatusRepository.saveAndFlush(taskStatus);

        // Get all the taskStatusList where labelCss equals to DEFAULT_LABEL_CSS
        defaultTaskStatusShouldBeFound("labelCss.equals=" + DEFAULT_LABEL_CSS);

        // Get all the taskStatusList where labelCss equals to UPDATED_LABEL_CSS
        defaultTaskStatusShouldNotBeFound("labelCss.equals=" + UPDATED_LABEL_CSS);
    }

    @Test
    @Transactional
    public void getAllTaskStatusesByLabelCssIsInShouldWork() throws Exception {
        // Initialize the database
        taskStatusRepository.saveAndFlush(taskStatus);

        // Get all the taskStatusList where labelCss in DEFAULT_LABEL_CSS or UPDATED_LABEL_CSS
        defaultTaskStatusShouldBeFound("labelCss.in=" + DEFAULT_LABEL_CSS + "," + UPDATED_LABEL_CSS);

        // Get all the taskStatusList where labelCss equals to UPDATED_LABEL_CSS
        defaultTaskStatusShouldNotBeFound("labelCss.in=" + UPDATED_LABEL_CSS);
    }

    @Test
    @Transactional
    public void getAllTaskStatusesByLabelCssIsNullOrNotNull() throws Exception {
        // Initialize the database
        taskStatusRepository.saveAndFlush(taskStatus);

        // Get all the taskStatusList where labelCss is not null
        defaultTaskStatusShouldBeFound("labelCss.specified=true");

        // Get all the taskStatusList where labelCss is null
        defaultTaskStatusShouldNotBeFound("labelCss.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultTaskStatusShouldBeFound(String filter) throws Exception {
        restTaskStatusMockMvc.perform(get("/api/task-statuses?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(taskStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].labelCss").value(hasItem(DEFAULT_LABEL_CSS.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultTaskStatusShouldNotBeFound(String filter) throws Exception {
        restTaskStatusMockMvc.perform(get("/api/task-statuses?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingTaskStatus() throws Exception {
        // Get the taskStatus
        restTaskStatusMockMvc.perform(get("/api/task-statuses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTaskStatus() throws Exception {
        // Initialize the database
        taskStatusService.save(taskStatus);

        int databaseSizeBeforeUpdate = taskStatusRepository.findAll().size();

        // Update the taskStatus
        TaskStatus updatedTaskStatus = taskStatusRepository.findOne(taskStatus.getId());
        updatedTaskStatus
            .name(UPDATED_NAME)
            .code(UPDATED_CODE)
            .labelCss(UPDATED_LABEL_CSS);

        restTaskStatusMockMvc.perform(put("/api/task-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTaskStatus)))
            .andExpect(status().isOk());

        // Validate the TaskStatus in the database
        List<TaskStatus> taskStatusList = taskStatusRepository.findAll();
        assertThat(taskStatusList).hasSize(databaseSizeBeforeUpdate);
        TaskStatus testTaskStatus = taskStatusList.get(taskStatusList.size() - 1);
        assertThat(testTaskStatus.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTaskStatus.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testTaskStatus.getLabelCss()).isEqualTo(UPDATED_LABEL_CSS);
    }

    @Test
    @Transactional
    public void updateNonExistingTaskStatus() throws Exception {
        int databaseSizeBeforeUpdate = taskStatusRepository.findAll().size();

        // Create the TaskStatus

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTaskStatusMockMvc.perform(put("/api/task-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(taskStatus)))
            .andExpect(status().isCreated());

        // Validate the TaskStatus in the database
        List<TaskStatus> taskStatusList = taskStatusRepository.findAll();
        assertThat(taskStatusList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTaskStatus() throws Exception {
        // Initialize the database
        taskStatusService.save(taskStatus);

        int databaseSizeBeforeDelete = taskStatusRepository.findAll().size();

        // Get the taskStatus
        restTaskStatusMockMvc.perform(delete("/api/task-statuses/{id}", taskStatus.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TaskStatus> taskStatusList = taskStatusRepository.findAll();
        assertThat(taskStatusList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TaskStatus.class);
        TaskStatus taskStatus1 = new TaskStatus();
        taskStatus1.setId(1L);
        TaskStatus taskStatus2 = new TaskStatus();
        taskStatus2.setId(taskStatus1.getId());
        assertThat(taskStatus1).isEqualTo(taskStatus2);
        taskStatus2.setId(2L);
        assertThat(taskStatus1).isNotEqualTo(taskStatus2);
        taskStatus1.setId(null);
        assertThat(taskStatus1).isNotEqualTo(taskStatus2);
    }
}
