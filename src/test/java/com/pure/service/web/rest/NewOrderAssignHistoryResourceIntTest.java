package com.pure.service.web.rest;

import com.pure.service.SimpleServiceApp;
import com.pure.service.domain.NewOrderAssignHistory;
import com.pure.service.repository.NewOrderAssignHistoryRepository;
import com.pure.service.service.NewOrderAssignHistoryQueryService;
import com.pure.service.service.NewOrderAssignHistoryService;
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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test class for the NewOrderAssignHistoryResource REST controller.
 *
 * @see NewOrderAssignHistoryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SimpleServiceApp.class)
public class NewOrderAssignHistoryResourceIntTest {

    private static final String DEFAULT_OLDER_FOLLOWER_LOGIN = "AAAAAAAAAA";
    private static final String UPDATED_OLDER_FOLLOWER_LOGIN = "BBBBBBBBBB";

    private static final String DEFAULT_OLDER_FOLLOWER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_OLDER_FOLLOWER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_NEW_FOLLOWER_LOGIN = "AAAAAAAAAA";
    private static final String UPDATED_NEW_FOLLOWER_LOGIN = "BBBBBBBBBB";

    private static final String DEFAULT_NEW_FOLLOWER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NEW_FOLLOWER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private NewOrderAssignHistoryRepository newOrderAssignHistoryRepository;

    @Autowired
    private NewOrderAssignHistoryService newOrderAssignHistoryService;

    @Autowired
    private NewOrderAssignHistoryQueryService newOrderAssignHistoryQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restNewOrderAssignHistoryMockMvc;

    private NewOrderAssignHistory newOrderAssignHistory;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final NewOrderAssignHistoryResource newOrderAssignHistoryResource = new NewOrderAssignHistoryResource(newOrderAssignHistoryService, newOrderAssignHistoryQueryService);
        this.restNewOrderAssignHistoryMockMvc = MockMvcBuilders.standaloneSetup(newOrderAssignHistoryResource)
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
    public static NewOrderAssignHistory createEntity(EntityManager em) {
        NewOrderAssignHistory newOrderAssignHistory = new NewOrderAssignHistory()
            .olderFollowerLogin(DEFAULT_OLDER_FOLLOWER_LOGIN)
            .olderFollowerName(DEFAULT_OLDER_FOLLOWER_NAME)
            .newFollowerLogin(DEFAULT_NEW_FOLLOWER_LOGIN)
            .newFollowerName(DEFAULT_NEW_FOLLOWER_NAME);
//            .createdBy(DEFAULT_CREATED_BY)
//            .createdDate(DEFAULT_CREATED_DATE)
//            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
//            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return newOrderAssignHistory;
    }

    @Before
    public void initTest() {
        newOrderAssignHistory = createEntity(em);
    }

    @Test
    @Transactional
    public void createNewOrderAssignHistory() throws Exception {
        int databaseSizeBeforeCreate = newOrderAssignHistoryRepository.findAll().size();

        // Create the NewOrderAssignHistory
        restNewOrderAssignHistoryMockMvc.perform(post("/api/new-order-assign-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(newOrderAssignHistory)))
            .andExpect(status().isCreated());

        // Validate the NewOrderAssignHistory in the database
        List<NewOrderAssignHistory> newOrderAssignHistoryList = newOrderAssignHistoryRepository.findAll();
        assertThat(newOrderAssignHistoryList).hasSize(databaseSizeBeforeCreate + 1);
        NewOrderAssignHistory testNewOrderAssignHistory = newOrderAssignHistoryList.get(newOrderAssignHistoryList.size() - 1);
        assertThat(testNewOrderAssignHistory.getOlderFollowerLogin()).isEqualTo(DEFAULT_OLDER_FOLLOWER_LOGIN);
        assertThat(testNewOrderAssignHistory.getOlderFollowerName()).isEqualTo(DEFAULT_OLDER_FOLLOWER_NAME);
        assertThat(testNewOrderAssignHistory.getNewFollowerLogin()).isEqualTo(DEFAULT_NEW_FOLLOWER_LOGIN);
        assertThat(testNewOrderAssignHistory.getNewFollowerName()).isEqualTo(DEFAULT_NEW_FOLLOWER_NAME);
        assertThat(testNewOrderAssignHistory.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testNewOrderAssignHistory.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testNewOrderAssignHistory.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testNewOrderAssignHistory.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createNewOrderAssignHistoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = newOrderAssignHistoryRepository.findAll().size();

        // Create the NewOrderAssignHistory with an existing ID
        newOrderAssignHistory.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNewOrderAssignHistoryMockMvc.perform(post("/api/new-order-assign-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(newOrderAssignHistory)))
            .andExpect(status().isBadRequest());

        // Validate the NewOrderAssignHistory in the database
        List<NewOrderAssignHistory> newOrderAssignHistoryList = newOrderAssignHistoryRepository.findAll();
        assertThat(newOrderAssignHistoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllNewOrderAssignHistories() throws Exception {
        // Initialize the database
        newOrderAssignHistoryRepository.saveAndFlush(newOrderAssignHistory);

        // Get all the newOrderAssignHistoryList
        restNewOrderAssignHistoryMockMvc.perform(get("/api/new-order-assign-histories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(newOrderAssignHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].olderFollowerLogin").value(hasItem(DEFAULT_OLDER_FOLLOWER_LOGIN.toString())))
            .andExpect(jsonPath("$.[*].olderFollowerName").value(hasItem(DEFAULT_OLDER_FOLLOWER_NAME.toString())))
            .andExpect(jsonPath("$.[*].newFollowerLogin").value(hasItem(DEFAULT_NEW_FOLLOWER_LOGIN.toString())))
            .andExpect(jsonPath("$.[*].newFollowerName").value(hasItem(DEFAULT_NEW_FOLLOWER_NAME.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    public void getNewOrderAssignHistory() throws Exception {
        // Initialize the database
        newOrderAssignHistoryRepository.saveAndFlush(newOrderAssignHistory);

        // Get the newOrderAssignHistory
        restNewOrderAssignHistoryMockMvc.perform(get("/api/new-order-assign-histories/{id}", newOrderAssignHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(newOrderAssignHistory.getId().intValue()))
            .andExpect(jsonPath("$.olderFollowerLogin").value(DEFAULT_OLDER_FOLLOWER_LOGIN.toString()))
            .andExpect(jsonPath("$.olderFollowerName").value(DEFAULT_OLDER_FOLLOWER_NAME.toString()))
            .andExpect(jsonPath("$.newFollowerLogin").value(DEFAULT_NEW_FOLLOWER_LOGIN.toString()))
            .andExpect(jsonPath("$.newFollowerName").value(DEFAULT_NEW_FOLLOWER_NAME.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    public void getAllNewOrderAssignHistoriesByOlderFollowerLoginIsEqualToSomething() throws Exception {
        // Initialize the database
        newOrderAssignHistoryRepository.saveAndFlush(newOrderAssignHistory);

        // Get all the newOrderAssignHistoryList where olderFollowerLogin equals to DEFAULT_OLDER_FOLLOWER_LOGIN
        defaultNewOrderAssignHistoryShouldBeFound("olderFollowerLogin.equals=" + DEFAULT_OLDER_FOLLOWER_LOGIN);

        // Get all the newOrderAssignHistoryList where olderFollowerLogin equals to UPDATED_OLDER_FOLLOWER_LOGIN
        defaultNewOrderAssignHistoryShouldNotBeFound("olderFollowerLogin.equals=" + UPDATED_OLDER_FOLLOWER_LOGIN);
    }

    @Test
    @Transactional
    public void getAllNewOrderAssignHistoriesByOlderFollowerLoginIsInShouldWork() throws Exception {
        // Initialize the database
        newOrderAssignHistoryRepository.saveAndFlush(newOrderAssignHistory);

        // Get all the newOrderAssignHistoryList where olderFollowerLogin in DEFAULT_OLDER_FOLLOWER_LOGIN or UPDATED_OLDER_FOLLOWER_LOGIN
        defaultNewOrderAssignHistoryShouldBeFound("olderFollowerLogin.in=" + DEFAULT_OLDER_FOLLOWER_LOGIN + "," + UPDATED_OLDER_FOLLOWER_LOGIN);

        // Get all the newOrderAssignHistoryList where olderFollowerLogin equals to UPDATED_OLDER_FOLLOWER_LOGIN
        defaultNewOrderAssignHistoryShouldNotBeFound("olderFollowerLogin.in=" + UPDATED_OLDER_FOLLOWER_LOGIN);
    }

    @Test
    @Transactional
    public void getAllNewOrderAssignHistoriesByOlderFollowerLoginIsNullOrNotNull() throws Exception {
        // Initialize the database
        newOrderAssignHistoryRepository.saveAndFlush(newOrderAssignHistory);

        // Get all the newOrderAssignHistoryList where olderFollowerLogin is not null
        defaultNewOrderAssignHistoryShouldBeFound("olderFollowerLogin.specified=true");

        // Get all the newOrderAssignHistoryList where olderFollowerLogin is null
        defaultNewOrderAssignHistoryShouldNotBeFound("olderFollowerLogin.specified=false");
    }

    @Test
    @Transactional
    public void getAllNewOrderAssignHistoriesByOlderFollowerNameIsEqualToSomething() throws Exception {
        // Initialize the database
        newOrderAssignHistoryRepository.saveAndFlush(newOrderAssignHistory);

        // Get all the newOrderAssignHistoryList where olderFollowerName equals to DEFAULT_OLDER_FOLLOWER_NAME
        defaultNewOrderAssignHistoryShouldBeFound("olderFollowerName.equals=" + DEFAULT_OLDER_FOLLOWER_NAME);

        // Get all the newOrderAssignHistoryList where olderFollowerName equals to UPDATED_OLDER_FOLLOWER_NAME
        defaultNewOrderAssignHistoryShouldNotBeFound("olderFollowerName.equals=" + UPDATED_OLDER_FOLLOWER_NAME);
    }

    @Test
    @Transactional
    public void getAllNewOrderAssignHistoriesByOlderFollowerNameIsInShouldWork() throws Exception {
        // Initialize the database
        newOrderAssignHistoryRepository.saveAndFlush(newOrderAssignHistory);

        // Get all the newOrderAssignHistoryList where olderFollowerName in DEFAULT_OLDER_FOLLOWER_NAME or UPDATED_OLDER_FOLLOWER_NAME
        defaultNewOrderAssignHistoryShouldBeFound("olderFollowerName.in=" + DEFAULT_OLDER_FOLLOWER_NAME + "," + UPDATED_OLDER_FOLLOWER_NAME);

        // Get all the newOrderAssignHistoryList where olderFollowerName equals to UPDATED_OLDER_FOLLOWER_NAME
        defaultNewOrderAssignHistoryShouldNotBeFound("olderFollowerName.in=" + UPDATED_OLDER_FOLLOWER_NAME);
    }

    @Test
    @Transactional
    public void getAllNewOrderAssignHistoriesByOlderFollowerNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        newOrderAssignHistoryRepository.saveAndFlush(newOrderAssignHistory);

        // Get all the newOrderAssignHistoryList where olderFollowerName is not null
        defaultNewOrderAssignHistoryShouldBeFound("olderFollowerName.specified=true");

        // Get all the newOrderAssignHistoryList where olderFollowerName is null
        defaultNewOrderAssignHistoryShouldNotBeFound("olderFollowerName.specified=false");
    }

    @Test
    @Transactional
    public void getAllNewOrderAssignHistoriesByNewFollowerLoginIsEqualToSomething() throws Exception {
        // Initialize the database
        newOrderAssignHistoryRepository.saveAndFlush(newOrderAssignHistory);

        // Get all the newOrderAssignHistoryList where newFollowerLogin equals to DEFAULT_NEW_FOLLOWER_LOGIN
        defaultNewOrderAssignHistoryShouldBeFound("newFollowerLogin.equals=" + DEFAULT_NEW_FOLLOWER_LOGIN);

        // Get all the newOrderAssignHistoryList where newFollowerLogin equals to UPDATED_NEW_FOLLOWER_LOGIN
        defaultNewOrderAssignHistoryShouldNotBeFound("newFollowerLogin.equals=" + UPDATED_NEW_FOLLOWER_LOGIN);
    }

    @Test
    @Transactional
    public void getAllNewOrderAssignHistoriesByNewFollowerLoginIsInShouldWork() throws Exception {
        // Initialize the database
        newOrderAssignHistoryRepository.saveAndFlush(newOrderAssignHistory);

        // Get all the newOrderAssignHistoryList where newFollowerLogin in DEFAULT_NEW_FOLLOWER_LOGIN or UPDATED_NEW_FOLLOWER_LOGIN
        defaultNewOrderAssignHistoryShouldBeFound("newFollowerLogin.in=" + DEFAULT_NEW_FOLLOWER_LOGIN + "," + UPDATED_NEW_FOLLOWER_LOGIN);

        // Get all the newOrderAssignHistoryList where newFollowerLogin equals to UPDATED_NEW_FOLLOWER_LOGIN
        defaultNewOrderAssignHistoryShouldNotBeFound("newFollowerLogin.in=" + UPDATED_NEW_FOLLOWER_LOGIN);
    }

    @Test
    @Transactional
    public void getAllNewOrderAssignHistoriesByNewFollowerLoginIsNullOrNotNull() throws Exception {
        // Initialize the database
        newOrderAssignHistoryRepository.saveAndFlush(newOrderAssignHistory);

        // Get all the newOrderAssignHistoryList where newFollowerLogin is not null
        defaultNewOrderAssignHistoryShouldBeFound("newFollowerLogin.specified=true");

        // Get all the newOrderAssignHistoryList where newFollowerLogin is null
        defaultNewOrderAssignHistoryShouldNotBeFound("newFollowerLogin.specified=false");
    }

    @Test
    @Transactional
    public void getAllNewOrderAssignHistoriesByNewFollowerNameIsEqualToSomething() throws Exception {
        // Initialize the database
        newOrderAssignHistoryRepository.saveAndFlush(newOrderAssignHistory);

        // Get all the newOrderAssignHistoryList where newFollowerName equals to DEFAULT_NEW_FOLLOWER_NAME
        defaultNewOrderAssignHistoryShouldBeFound("newFollowerName.equals=" + DEFAULT_NEW_FOLLOWER_NAME);

        // Get all the newOrderAssignHistoryList where newFollowerName equals to UPDATED_NEW_FOLLOWER_NAME
        defaultNewOrderAssignHistoryShouldNotBeFound("newFollowerName.equals=" + UPDATED_NEW_FOLLOWER_NAME);
    }

    @Test
    @Transactional
    public void getAllNewOrderAssignHistoriesByNewFollowerNameIsInShouldWork() throws Exception {
        // Initialize the database
        newOrderAssignHistoryRepository.saveAndFlush(newOrderAssignHistory);

        // Get all the newOrderAssignHistoryList where newFollowerName in DEFAULT_NEW_FOLLOWER_NAME or UPDATED_NEW_FOLLOWER_NAME
        defaultNewOrderAssignHistoryShouldBeFound("newFollowerName.in=" + DEFAULT_NEW_FOLLOWER_NAME + "," + UPDATED_NEW_FOLLOWER_NAME);

        // Get all the newOrderAssignHistoryList where newFollowerName equals to UPDATED_NEW_FOLLOWER_NAME
        defaultNewOrderAssignHistoryShouldNotBeFound("newFollowerName.in=" + UPDATED_NEW_FOLLOWER_NAME);
    }

    @Test
    @Transactional
    public void getAllNewOrderAssignHistoriesByNewFollowerNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        newOrderAssignHistoryRepository.saveAndFlush(newOrderAssignHistory);

        // Get all the newOrderAssignHistoryList where newFollowerName is not null
        defaultNewOrderAssignHistoryShouldBeFound("newFollowerName.specified=true");

        // Get all the newOrderAssignHistoryList where newFollowerName is null
        defaultNewOrderAssignHistoryShouldNotBeFound("newFollowerName.specified=false");
    }

    @Test
    @Transactional
    public void getAllNewOrderAssignHistoriesByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        newOrderAssignHistoryRepository.saveAndFlush(newOrderAssignHistory);

        // Get all the newOrderAssignHistoryList where createdBy equals to DEFAULT_CREATED_BY
        defaultNewOrderAssignHistoryShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the newOrderAssignHistoryList where createdBy equals to UPDATED_CREATED_BY
        defaultNewOrderAssignHistoryShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllNewOrderAssignHistoriesByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        newOrderAssignHistoryRepository.saveAndFlush(newOrderAssignHistory);

        // Get all the newOrderAssignHistoryList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultNewOrderAssignHistoryShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the newOrderAssignHistoryList where createdBy equals to UPDATED_CREATED_BY
        defaultNewOrderAssignHistoryShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllNewOrderAssignHistoriesByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        newOrderAssignHistoryRepository.saveAndFlush(newOrderAssignHistory);

        // Get all the newOrderAssignHistoryList where createdBy is not null
        defaultNewOrderAssignHistoryShouldBeFound("createdBy.specified=true");

        // Get all the newOrderAssignHistoryList where createdBy is null
        defaultNewOrderAssignHistoryShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllNewOrderAssignHistoriesByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        newOrderAssignHistoryRepository.saveAndFlush(newOrderAssignHistory);

        // Get all the newOrderAssignHistoryList where createdDate equals to DEFAULT_CREATED_DATE
        defaultNewOrderAssignHistoryShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the newOrderAssignHistoryList where createdDate equals to UPDATED_CREATED_DATE
        defaultNewOrderAssignHistoryShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllNewOrderAssignHistoriesByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        newOrderAssignHistoryRepository.saveAndFlush(newOrderAssignHistory);

        // Get all the newOrderAssignHistoryList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultNewOrderAssignHistoryShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the newOrderAssignHistoryList where createdDate equals to UPDATED_CREATED_DATE
        defaultNewOrderAssignHistoryShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllNewOrderAssignHistoriesByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        newOrderAssignHistoryRepository.saveAndFlush(newOrderAssignHistory);

        // Get all the newOrderAssignHistoryList where createdDate is not null
        defaultNewOrderAssignHistoryShouldBeFound("createdDate.specified=true");

        // Get all the newOrderAssignHistoryList where createdDate is null
        defaultNewOrderAssignHistoryShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllNewOrderAssignHistoriesByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        newOrderAssignHistoryRepository.saveAndFlush(newOrderAssignHistory);

        // Get all the newOrderAssignHistoryList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultNewOrderAssignHistoryShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the newOrderAssignHistoryList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultNewOrderAssignHistoryShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllNewOrderAssignHistoriesByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        newOrderAssignHistoryRepository.saveAndFlush(newOrderAssignHistory);

        // Get all the newOrderAssignHistoryList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultNewOrderAssignHistoryShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the newOrderAssignHistoryList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultNewOrderAssignHistoryShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllNewOrderAssignHistoriesByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        newOrderAssignHistoryRepository.saveAndFlush(newOrderAssignHistory);

        // Get all the newOrderAssignHistoryList where lastModifiedBy is not null
        defaultNewOrderAssignHistoryShouldBeFound("lastModifiedBy.specified=true");

        // Get all the newOrderAssignHistoryList where lastModifiedBy is null
        defaultNewOrderAssignHistoryShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllNewOrderAssignHistoriesByLastModifiedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        newOrderAssignHistoryRepository.saveAndFlush(newOrderAssignHistory);

        // Get all the newOrderAssignHistoryList where lastModifiedDate equals to DEFAULT_LAST_MODIFIED_DATE
        defaultNewOrderAssignHistoryShouldBeFound("lastModifiedDate.equals=" + DEFAULT_LAST_MODIFIED_DATE);

        // Get all the newOrderAssignHistoryList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultNewOrderAssignHistoryShouldNotBeFound("lastModifiedDate.equals=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllNewOrderAssignHistoriesByLastModifiedDateIsInShouldWork() throws Exception {
        // Initialize the database
        newOrderAssignHistoryRepository.saveAndFlush(newOrderAssignHistory);

        // Get all the newOrderAssignHistoryList where lastModifiedDate in DEFAULT_LAST_MODIFIED_DATE or UPDATED_LAST_MODIFIED_DATE
        defaultNewOrderAssignHistoryShouldBeFound("lastModifiedDate.in=" + DEFAULT_LAST_MODIFIED_DATE + "," + UPDATED_LAST_MODIFIED_DATE);

        // Get all the newOrderAssignHistoryList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultNewOrderAssignHistoryShouldNotBeFound("lastModifiedDate.in=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllNewOrderAssignHistoriesByLastModifiedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        newOrderAssignHistoryRepository.saveAndFlush(newOrderAssignHistory);

        // Get all the newOrderAssignHistoryList where lastModifiedDate is not null
        defaultNewOrderAssignHistoryShouldBeFound("lastModifiedDate.specified=true");

        // Get all the newOrderAssignHistoryList where lastModifiedDate is null
        defaultNewOrderAssignHistoryShouldNotBeFound("lastModifiedDate.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultNewOrderAssignHistoryShouldBeFound(String filter) throws Exception {
        restNewOrderAssignHistoryMockMvc.perform(get("/api/new-order-assign-histories?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(newOrderAssignHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].olderFollowerLogin").value(hasItem(DEFAULT_OLDER_FOLLOWER_LOGIN.toString())))
            .andExpect(jsonPath("$.[*].olderFollowerName").value(hasItem(DEFAULT_OLDER_FOLLOWER_NAME.toString())))
            .andExpect(jsonPath("$.[*].newFollowerLogin").value(hasItem(DEFAULT_NEW_FOLLOWER_LOGIN.toString())))
            .andExpect(jsonPath("$.[*].newFollowerName").value(hasItem(DEFAULT_NEW_FOLLOWER_NAME.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultNewOrderAssignHistoryShouldNotBeFound(String filter) throws Exception {
        restNewOrderAssignHistoryMockMvc.perform(get("/api/new-order-assign-histories?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingNewOrderAssignHistory() throws Exception {
        // Get the newOrderAssignHistory
        restNewOrderAssignHistoryMockMvc.perform(get("/api/new-order-assign-histories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNewOrderAssignHistory() throws Exception {
        // Initialize the database
        newOrderAssignHistoryService.save(newOrderAssignHistory);

        int databaseSizeBeforeUpdate = newOrderAssignHistoryRepository.findAll().size();

        // Update the newOrderAssignHistory
        NewOrderAssignHistory updatedNewOrderAssignHistory = newOrderAssignHistoryRepository.findOne(newOrderAssignHistory.getId());
        updatedNewOrderAssignHistory
            .olderFollowerLogin(UPDATED_OLDER_FOLLOWER_LOGIN)
            .olderFollowerName(UPDATED_OLDER_FOLLOWER_NAME)
            .newFollowerLogin(UPDATED_NEW_FOLLOWER_LOGIN)
            .newFollowerName(UPDATED_NEW_FOLLOWER_NAME);
//            .createdBy(UPDATED_CREATED_BY)
//            .createdDate(UPDATED_CREATED_DATE)
//            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
//            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restNewOrderAssignHistoryMockMvc.perform(put("/api/new-order-assign-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedNewOrderAssignHistory)))
            .andExpect(status().isOk());

        // Validate the NewOrderAssignHistory in the database
        List<NewOrderAssignHistory> newOrderAssignHistoryList = newOrderAssignHistoryRepository.findAll();
        assertThat(newOrderAssignHistoryList).hasSize(databaseSizeBeforeUpdate);
        NewOrderAssignHistory testNewOrderAssignHistory = newOrderAssignHistoryList.get(newOrderAssignHistoryList.size() - 1);
        assertThat(testNewOrderAssignHistory.getOlderFollowerLogin()).isEqualTo(UPDATED_OLDER_FOLLOWER_LOGIN);
        assertThat(testNewOrderAssignHistory.getOlderFollowerName()).isEqualTo(UPDATED_OLDER_FOLLOWER_NAME);
        assertThat(testNewOrderAssignHistory.getNewFollowerLogin()).isEqualTo(UPDATED_NEW_FOLLOWER_LOGIN);
        assertThat(testNewOrderAssignHistory.getNewFollowerName()).isEqualTo(UPDATED_NEW_FOLLOWER_NAME);
        assertThat(testNewOrderAssignHistory.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testNewOrderAssignHistory.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testNewOrderAssignHistory.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testNewOrderAssignHistory.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingNewOrderAssignHistory() throws Exception {
        int databaseSizeBeforeUpdate = newOrderAssignHistoryRepository.findAll().size();

        // Create the NewOrderAssignHistory

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restNewOrderAssignHistoryMockMvc.perform(put("/api/new-order-assign-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(newOrderAssignHistory)))
            .andExpect(status().isCreated());

        // Validate the NewOrderAssignHistory in the database
        List<NewOrderAssignHistory> newOrderAssignHistoryList = newOrderAssignHistoryRepository.findAll();
        assertThat(newOrderAssignHistoryList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteNewOrderAssignHistory() throws Exception {
        // Initialize the database
        newOrderAssignHistoryService.save(newOrderAssignHistory);

        int databaseSizeBeforeDelete = newOrderAssignHistoryRepository.findAll().size();

        // Get the newOrderAssignHistory
        restNewOrderAssignHistoryMockMvc.perform(delete("/api/new-order-assign-histories/{id}", newOrderAssignHistory.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<NewOrderAssignHistory> newOrderAssignHistoryList = newOrderAssignHistoryRepository.findAll();
        assertThat(newOrderAssignHistoryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NewOrderAssignHistory.class);
        NewOrderAssignHistory newOrderAssignHistory1 = new NewOrderAssignHistory();
        newOrderAssignHistory1.setId(1L);
        NewOrderAssignHistory newOrderAssignHistory2 = new NewOrderAssignHistory();
        newOrderAssignHistory2.setId(newOrderAssignHistory1.getId());
        assertThat(newOrderAssignHistory1).isEqualTo(newOrderAssignHistory2);
        newOrderAssignHistory2.setId(2L);
        assertThat(newOrderAssignHistory1).isNotEqualTo(newOrderAssignHistory2);
        newOrderAssignHistory1.setId(null);
        assertThat(newOrderAssignHistory1).isNotEqualTo(newOrderAssignHistory2);
    }
}
