package com.pure.service.web.rest;

import com.pure.service.SimpleServiceApp;

import com.pure.service.domain.ChannelReport;
import com.pure.service.repository.ChannelReportRepository;
import com.pure.service.service.ChannelReportService;
import com.pure.service.web.rest.errors.ExceptionTranslator;
import com.pure.service.service.dto.ChannelReportCriteria;
import com.pure.service.service.ChannelReportQueryService;

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
 * Test class for the ChannelReportResource REST controller.
 *
 * @see ChannelReportResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SimpleServiceApp.class)
public class ChannelReportResourceIntTest {

    private static final String DEFAULT_MONTH = "AAAAAAAAAA";
    private static final String UPDATED_MONTH = "BBBBBBBBBB";

    private static final String DEFAULT_YEAR = "AAAAAAAAAA";
    private static final String UPDATED_YEAR = "BBBBBBBBBB";

    private static final Long DEFAULT_CHANNEL_ID = 1L;
    private static final Long UPDATED_CHANNEL_ID = 2L;

    private static final String DEFAULT_CHANNEL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CHANNEL_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_VISITED_CUSTOMER_COUNT = 1;
    private static final Integer UPDATED_VISITED_CUSTOMER_COUNT = 2;

    private static final Integer DEFAULT_CARD_COUNT = 1;
    private static final Integer UPDATED_CARD_COUNT = 2;

    private static final Integer DEFAULT_CONTRACT_COUNT = 1;
    private static final Integer UPDATED_CONTRACT_COUNT = 2;

    private static final Double DEFAULT_MONEY_COLLECTED = 1D;
    private static final Double UPDATED_MONEY_COLLECTED = 2D;

    private static final Double DEFAULT_CONTRACT_MADE_RATE = 1D;
    private static final Double UPDATED_CONTRACT_MADE_RATE = 2D;

    private static final Long DEFAULT_REGION_ID = 1L;
    private static final Long UPDATED_REGION_ID = 2L;

    @Autowired
    private ChannelReportRepository channelReportRepository;

    @Autowired
    private ChannelReportService channelReportService;

    @Autowired
    private ChannelReportQueryService channelReportQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restChannelReportMockMvc;

    private ChannelReport channelReport;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ChannelReportResource channelReportResource = new ChannelReportResource(channelReportService, channelReportQueryService);
        this.restChannelReportMockMvc = MockMvcBuilders.standaloneSetup(channelReportResource)
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
    public static ChannelReport createEntity(EntityManager em) {
        ChannelReport channelReport = new ChannelReport()
            .month(DEFAULT_MONTH)
            .year(DEFAULT_YEAR)
            .channelId(DEFAULT_CHANNEL_ID)
            .channelName(DEFAULT_CHANNEL_NAME)
            .visitedCustomerCount(DEFAULT_VISITED_CUSTOMER_COUNT)
            .cardCount(DEFAULT_CARD_COUNT)
            .contractCount(DEFAULT_CONTRACT_COUNT)
            .moneyCollected(DEFAULT_MONEY_COLLECTED)
            .contractMadeRate(DEFAULT_CONTRACT_MADE_RATE)
            .regionId(DEFAULT_REGION_ID);
        return channelReport;
    }

    @Before
    public void initTest() {
        channelReport = createEntity(em);
    }

    @Test
    @Transactional
    public void createChannelReport() throws Exception {
        int databaseSizeBeforeCreate = channelReportRepository.findAll().size();

        // Create the ChannelReport
        restChannelReportMockMvc.perform(post("/api/channel-reports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(channelReport)))
            .andExpect(status().isCreated());

        // Validate the ChannelReport in the database
        List<ChannelReport> channelReportList = channelReportRepository.findAll();
        assertThat(channelReportList).hasSize(databaseSizeBeforeCreate + 1);
        ChannelReport testChannelReport = channelReportList.get(channelReportList.size() - 1);
        assertThat(testChannelReport.getMonth()).isEqualTo(DEFAULT_MONTH);
        assertThat(testChannelReport.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testChannelReport.getChannelId()).isEqualTo(DEFAULT_CHANNEL_ID);
        assertThat(testChannelReport.getChannelName()).isEqualTo(DEFAULT_CHANNEL_NAME);
        assertThat(testChannelReport.getVisitedCustomerCount()).isEqualTo(DEFAULT_VISITED_CUSTOMER_COUNT);
        assertThat(testChannelReport.getCardCount()).isEqualTo(DEFAULT_CARD_COUNT);
        assertThat(testChannelReport.getContractCount()).isEqualTo(DEFAULT_CONTRACT_COUNT);
        assertThat(testChannelReport.getMoneyCollected()).isEqualTo(DEFAULT_MONEY_COLLECTED);
        assertThat(testChannelReport.getContractMadeRate()).isEqualTo(DEFAULT_CONTRACT_MADE_RATE);
        assertThat(testChannelReport.getRegionId()).isEqualTo(DEFAULT_REGION_ID);
    }

    @Test
    @Transactional
    public void createChannelReportWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = channelReportRepository.findAll().size();

        // Create the ChannelReport with an existing ID
        channelReport.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restChannelReportMockMvc.perform(post("/api/channel-reports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(channelReport)))
            .andExpect(status().isBadRequest());

        // Validate the ChannelReport in the database
        List<ChannelReport> channelReportList = channelReportRepository.findAll();
        assertThat(channelReportList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllChannelReports() throws Exception {
        // Initialize the database
        channelReportRepository.saveAndFlush(channelReport);

        // Get all the channelReportList
        restChannelReportMockMvc.perform(get("/api/channel-reports?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(channelReport.getId().intValue())))
            .andExpect(jsonPath("$.[*].month").value(hasItem(DEFAULT_MONTH)))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
            .andExpect(jsonPath("$.[*].channelId").value(hasItem(DEFAULT_CHANNEL_ID.intValue())))
            .andExpect(jsonPath("$.[*].channelName").value(hasItem(DEFAULT_CHANNEL_NAME)))
            .andExpect(jsonPath("$.[*].visitedCustomerCount").value(hasItem(DEFAULT_VISITED_CUSTOMER_COUNT)))
            .andExpect(jsonPath("$.[*].cardCount").value(hasItem(DEFAULT_CARD_COUNT)))
            .andExpect(jsonPath("$.[*].contractCount").value(hasItem(DEFAULT_CONTRACT_COUNT)))
            .andExpect(jsonPath("$.[*].moneyCollected").value(hasItem(DEFAULT_MONEY_COLLECTED.doubleValue())))
            .andExpect(jsonPath("$.[*].contractMadeRate").value(hasItem(DEFAULT_CONTRACT_MADE_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].regionId").value(hasItem(DEFAULT_REGION_ID.intValue())));
    }

    @Test
    @Transactional
    public void getChannelReport() throws Exception {
        // Initialize the database
        channelReportRepository.saveAndFlush(channelReport);

        // Get the channelReport
        restChannelReportMockMvc.perform(get("/api/channel-reports/{id}", channelReport.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(channelReport.getId().intValue()))
            .andExpect(jsonPath("$.month").value(DEFAULT_MONTH))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR))
            .andExpect(jsonPath("$.channelId").value(DEFAULT_CHANNEL_ID.intValue()))
            .andExpect(jsonPath("$.channelName").value(DEFAULT_CHANNEL_NAME))
            .andExpect(jsonPath("$.visitedCustomerCount").value(DEFAULT_VISITED_CUSTOMER_COUNT))
            .andExpect(jsonPath("$.cardCount").value(DEFAULT_CARD_COUNT))
            .andExpect(jsonPath("$.contractCount").value(DEFAULT_CONTRACT_COUNT))
            .andExpect(jsonPath("$.moneyCollected").value(DEFAULT_MONEY_COLLECTED.doubleValue()))
            .andExpect(jsonPath("$.contractMadeRate").value(DEFAULT_CONTRACT_MADE_RATE.doubleValue()))
            .andExpect(jsonPath("$.regionId").value(DEFAULT_REGION_ID.intValue()));
    }

    @Test
    @Transactional
    public void getAllChannelReportsByMonthIsEqualToSomething() throws Exception {
        // Initialize the database
        channelReportRepository.saveAndFlush(channelReport);

        // Get all the channelReportList where month equals to DEFAULT_MONTH
        defaultChannelReportShouldBeFound("month.equals=" + DEFAULT_MONTH);

        // Get all the channelReportList where month equals to UPDATED_MONTH
        defaultChannelReportShouldNotBeFound("month.equals=" + UPDATED_MONTH);
    }

    @Test
    @Transactional
    public void getAllChannelReportsByMonthIsInShouldWork() throws Exception {
        // Initialize the database
        channelReportRepository.saveAndFlush(channelReport);

        // Get all the channelReportList where month in DEFAULT_MONTH or UPDATED_MONTH
        defaultChannelReportShouldBeFound("month.in=" + DEFAULT_MONTH + "," + UPDATED_MONTH);

        // Get all the channelReportList where month equals to UPDATED_MONTH
        defaultChannelReportShouldNotBeFound("month.in=" + UPDATED_MONTH);
    }

    @Test
    @Transactional
    public void getAllChannelReportsByMonthIsNullOrNotNull() throws Exception {
        // Initialize the database
        channelReportRepository.saveAndFlush(channelReport);

        // Get all the channelReportList where month is not null
        defaultChannelReportShouldBeFound("month.specified=true");

        // Get all the channelReportList where month is null
        defaultChannelReportShouldNotBeFound("month.specified=false");
    }

    @Test
    @Transactional
    public void getAllChannelReportsByYearIsEqualToSomething() throws Exception {
        // Initialize the database
        channelReportRepository.saveAndFlush(channelReport);

        // Get all the channelReportList where year equals to DEFAULT_YEAR
        defaultChannelReportShouldBeFound("year.equals=" + DEFAULT_YEAR);

        // Get all the channelReportList where year equals to UPDATED_YEAR
        defaultChannelReportShouldNotBeFound("year.equals=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    public void getAllChannelReportsByYearIsInShouldWork() throws Exception {
        // Initialize the database
        channelReportRepository.saveAndFlush(channelReport);

        // Get all the channelReportList where year in DEFAULT_YEAR or UPDATED_YEAR
        defaultChannelReportShouldBeFound("year.in=" + DEFAULT_YEAR + "," + UPDATED_YEAR);

        // Get all the channelReportList where year equals to UPDATED_YEAR
        defaultChannelReportShouldNotBeFound("year.in=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    public void getAllChannelReportsByYearIsNullOrNotNull() throws Exception {
        // Initialize the database
        channelReportRepository.saveAndFlush(channelReport);

        // Get all the channelReportList where year is not null
        defaultChannelReportShouldBeFound("year.specified=true");

        // Get all the channelReportList where year is null
        defaultChannelReportShouldNotBeFound("year.specified=false");
    }

    @Test
    @Transactional
    public void getAllChannelReportsByChannelIdIsEqualToSomething() throws Exception {
        // Initialize the database
        channelReportRepository.saveAndFlush(channelReport);

        // Get all the channelReportList where channelId equals to DEFAULT_CHANNEL_ID
        defaultChannelReportShouldBeFound("channelId.equals=" + DEFAULT_CHANNEL_ID);

        // Get all the channelReportList where channelId equals to UPDATED_CHANNEL_ID
        defaultChannelReportShouldNotBeFound("channelId.equals=" + UPDATED_CHANNEL_ID);
    }

    @Test
    @Transactional
    public void getAllChannelReportsByChannelIdIsInShouldWork() throws Exception {
        // Initialize the database
        channelReportRepository.saveAndFlush(channelReport);

        // Get all the channelReportList where channelId in DEFAULT_CHANNEL_ID or UPDATED_CHANNEL_ID
        defaultChannelReportShouldBeFound("channelId.in=" + DEFAULT_CHANNEL_ID + "," + UPDATED_CHANNEL_ID);

        // Get all the channelReportList where channelId equals to UPDATED_CHANNEL_ID
        defaultChannelReportShouldNotBeFound("channelId.in=" + UPDATED_CHANNEL_ID);
    }

    @Test
    @Transactional
    public void getAllChannelReportsByChannelIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        channelReportRepository.saveAndFlush(channelReport);

        // Get all the channelReportList where channelId is not null
        defaultChannelReportShouldBeFound("channelId.specified=true");

        // Get all the channelReportList where channelId is null
        defaultChannelReportShouldNotBeFound("channelId.specified=false");
    }

    @Test
    @Transactional
    public void getAllChannelReportsByChannelIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        channelReportRepository.saveAndFlush(channelReport);

        // Get all the channelReportList where channelId greater than or equals to DEFAULT_CHANNEL_ID
        defaultChannelReportShouldBeFound("channelId.greaterOrEqualThan=" + DEFAULT_CHANNEL_ID);

        // Get all the channelReportList where channelId greater than or equals to UPDATED_CHANNEL_ID
        defaultChannelReportShouldNotBeFound("channelId.greaterOrEqualThan=" + UPDATED_CHANNEL_ID);
    }

    @Test
    @Transactional
    public void getAllChannelReportsByChannelIdIsLessThanSomething() throws Exception {
        // Initialize the database
        channelReportRepository.saveAndFlush(channelReport);

        // Get all the channelReportList where channelId less than or equals to DEFAULT_CHANNEL_ID
        defaultChannelReportShouldNotBeFound("channelId.lessThan=" + DEFAULT_CHANNEL_ID);

        // Get all the channelReportList where channelId less than or equals to UPDATED_CHANNEL_ID
        defaultChannelReportShouldBeFound("channelId.lessThan=" + UPDATED_CHANNEL_ID);
    }


    @Test
    @Transactional
    public void getAllChannelReportsByChannelNameIsEqualToSomething() throws Exception {
        // Initialize the database
        channelReportRepository.saveAndFlush(channelReport);

        // Get all the channelReportList where channelName equals to DEFAULT_CHANNEL_NAME
        defaultChannelReportShouldBeFound("channelName.equals=" + DEFAULT_CHANNEL_NAME);

        // Get all the channelReportList where channelName equals to UPDATED_CHANNEL_NAME
        defaultChannelReportShouldNotBeFound("channelName.equals=" + UPDATED_CHANNEL_NAME);
    }

    @Test
    @Transactional
    public void getAllChannelReportsByChannelNameIsInShouldWork() throws Exception {
        // Initialize the database
        channelReportRepository.saveAndFlush(channelReport);

        // Get all the channelReportList where channelName in DEFAULT_CHANNEL_NAME or UPDATED_CHANNEL_NAME
        defaultChannelReportShouldBeFound("channelName.in=" + DEFAULT_CHANNEL_NAME + "," + UPDATED_CHANNEL_NAME);

        // Get all the channelReportList where channelName equals to UPDATED_CHANNEL_NAME
        defaultChannelReportShouldNotBeFound("channelName.in=" + UPDATED_CHANNEL_NAME);
    }

    @Test
    @Transactional
    public void getAllChannelReportsByChannelNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        channelReportRepository.saveAndFlush(channelReport);

        // Get all the channelReportList where channelName is not null
        defaultChannelReportShouldBeFound("channelName.specified=true");

        // Get all the channelReportList where channelName is null
        defaultChannelReportShouldNotBeFound("channelName.specified=false");
    }

    @Test
    @Transactional
    public void getAllChannelReportsByVisitedCustomerCountIsEqualToSomething() throws Exception {
        // Initialize the database
        channelReportRepository.saveAndFlush(channelReport);

        // Get all the channelReportList where visitedCustomerCount equals to DEFAULT_VISITED_CUSTOMER_COUNT
        defaultChannelReportShouldBeFound("visitedCustomerCount.equals=" + DEFAULT_VISITED_CUSTOMER_COUNT);

        // Get all the channelReportList where visitedCustomerCount equals to UPDATED_VISITED_CUSTOMER_COUNT
        defaultChannelReportShouldNotBeFound("visitedCustomerCount.equals=" + UPDATED_VISITED_CUSTOMER_COUNT);
    }

    @Test
    @Transactional
    public void getAllChannelReportsByVisitedCustomerCountIsInShouldWork() throws Exception {
        // Initialize the database
        channelReportRepository.saveAndFlush(channelReport);

        // Get all the channelReportList where visitedCustomerCount in DEFAULT_VISITED_CUSTOMER_COUNT or UPDATED_VISITED_CUSTOMER_COUNT
        defaultChannelReportShouldBeFound("visitedCustomerCount.in=" + DEFAULT_VISITED_CUSTOMER_COUNT + "," + UPDATED_VISITED_CUSTOMER_COUNT);

        // Get all the channelReportList where visitedCustomerCount equals to UPDATED_VISITED_CUSTOMER_COUNT
        defaultChannelReportShouldNotBeFound("visitedCustomerCount.in=" + UPDATED_VISITED_CUSTOMER_COUNT);
    }

    @Test
    @Transactional
    public void getAllChannelReportsByVisitedCustomerCountIsNullOrNotNull() throws Exception {
        // Initialize the database
        channelReportRepository.saveAndFlush(channelReport);

        // Get all the channelReportList where visitedCustomerCount is not null
        defaultChannelReportShouldBeFound("visitedCustomerCount.specified=true");

        // Get all the channelReportList where visitedCustomerCount is null
        defaultChannelReportShouldNotBeFound("visitedCustomerCount.specified=false");
    }

    @Test
    @Transactional
    public void getAllChannelReportsByVisitedCustomerCountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        channelReportRepository.saveAndFlush(channelReport);

        // Get all the channelReportList where visitedCustomerCount greater than or equals to DEFAULT_VISITED_CUSTOMER_COUNT
        defaultChannelReportShouldBeFound("visitedCustomerCount.greaterOrEqualThan=" + DEFAULT_VISITED_CUSTOMER_COUNT);

        // Get all the channelReportList where visitedCustomerCount greater than or equals to UPDATED_VISITED_CUSTOMER_COUNT
        defaultChannelReportShouldNotBeFound("visitedCustomerCount.greaterOrEqualThan=" + UPDATED_VISITED_CUSTOMER_COUNT);
    }

    @Test
    @Transactional
    public void getAllChannelReportsByVisitedCustomerCountIsLessThanSomething() throws Exception {
        // Initialize the database
        channelReportRepository.saveAndFlush(channelReport);

        // Get all the channelReportList where visitedCustomerCount less than or equals to DEFAULT_VISITED_CUSTOMER_COUNT
        defaultChannelReportShouldNotBeFound("visitedCustomerCount.lessThan=" + DEFAULT_VISITED_CUSTOMER_COUNT);

        // Get all the channelReportList where visitedCustomerCount less than or equals to UPDATED_VISITED_CUSTOMER_COUNT
        defaultChannelReportShouldBeFound("visitedCustomerCount.lessThan=" + UPDATED_VISITED_CUSTOMER_COUNT);
    }


    @Test
    @Transactional
    public void getAllChannelReportsByCardCountIsEqualToSomething() throws Exception {
        // Initialize the database
        channelReportRepository.saveAndFlush(channelReport);

        // Get all the channelReportList where cardCount equals to DEFAULT_CARD_COUNT
        defaultChannelReportShouldBeFound("cardCount.equals=" + DEFAULT_CARD_COUNT);

        // Get all the channelReportList where cardCount equals to UPDATED_CARD_COUNT
        defaultChannelReportShouldNotBeFound("cardCount.equals=" + UPDATED_CARD_COUNT);
    }

    @Test
    @Transactional
    public void getAllChannelReportsByCardCountIsInShouldWork() throws Exception {
        // Initialize the database
        channelReportRepository.saveAndFlush(channelReport);

        // Get all the channelReportList where cardCount in DEFAULT_CARD_COUNT or UPDATED_CARD_COUNT
        defaultChannelReportShouldBeFound("cardCount.in=" + DEFAULT_CARD_COUNT + "," + UPDATED_CARD_COUNT);

        // Get all the channelReportList where cardCount equals to UPDATED_CARD_COUNT
        defaultChannelReportShouldNotBeFound("cardCount.in=" + UPDATED_CARD_COUNT);
    }

    @Test
    @Transactional
    public void getAllChannelReportsByCardCountIsNullOrNotNull() throws Exception {
        // Initialize the database
        channelReportRepository.saveAndFlush(channelReport);

        // Get all the channelReportList where cardCount is not null
        defaultChannelReportShouldBeFound("cardCount.specified=true");

        // Get all the channelReportList where cardCount is null
        defaultChannelReportShouldNotBeFound("cardCount.specified=false");
    }

    @Test
    @Transactional
    public void getAllChannelReportsByCardCountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        channelReportRepository.saveAndFlush(channelReport);

        // Get all the channelReportList where cardCount greater than or equals to DEFAULT_CARD_COUNT
        defaultChannelReportShouldBeFound("cardCount.greaterOrEqualThan=" + DEFAULT_CARD_COUNT);

        // Get all the channelReportList where cardCount greater than or equals to UPDATED_CARD_COUNT
        defaultChannelReportShouldNotBeFound("cardCount.greaterOrEqualThan=" + UPDATED_CARD_COUNT);
    }

    @Test
    @Transactional
    public void getAllChannelReportsByCardCountIsLessThanSomething() throws Exception {
        // Initialize the database
        channelReportRepository.saveAndFlush(channelReport);

        // Get all the channelReportList where cardCount less than or equals to DEFAULT_CARD_COUNT
        defaultChannelReportShouldNotBeFound("cardCount.lessThan=" + DEFAULT_CARD_COUNT);

        // Get all the channelReportList where cardCount less than or equals to UPDATED_CARD_COUNT
        defaultChannelReportShouldBeFound("cardCount.lessThan=" + UPDATED_CARD_COUNT);
    }


    @Test
    @Transactional
    public void getAllChannelReportsByContractCountIsEqualToSomething() throws Exception {
        // Initialize the database
        channelReportRepository.saveAndFlush(channelReport);

        // Get all the channelReportList where contractCount equals to DEFAULT_CONTRACT_COUNT
        defaultChannelReportShouldBeFound("contractCount.equals=" + DEFAULT_CONTRACT_COUNT);

        // Get all the channelReportList where contractCount equals to UPDATED_CONTRACT_COUNT
        defaultChannelReportShouldNotBeFound("contractCount.equals=" + UPDATED_CONTRACT_COUNT);
    }

    @Test
    @Transactional
    public void getAllChannelReportsByContractCountIsInShouldWork() throws Exception {
        // Initialize the database
        channelReportRepository.saveAndFlush(channelReport);

        // Get all the channelReportList where contractCount in DEFAULT_CONTRACT_COUNT or UPDATED_CONTRACT_COUNT
        defaultChannelReportShouldBeFound("contractCount.in=" + DEFAULT_CONTRACT_COUNT + "," + UPDATED_CONTRACT_COUNT);

        // Get all the channelReportList where contractCount equals to UPDATED_CONTRACT_COUNT
        defaultChannelReportShouldNotBeFound("contractCount.in=" + UPDATED_CONTRACT_COUNT);
    }

    @Test
    @Transactional
    public void getAllChannelReportsByContractCountIsNullOrNotNull() throws Exception {
        // Initialize the database
        channelReportRepository.saveAndFlush(channelReport);

        // Get all the channelReportList where contractCount is not null
        defaultChannelReportShouldBeFound("contractCount.specified=true");

        // Get all the channelReportList where contractCount is null
        defaultChannelReportShouldNotBeFound("contractCount.specified=false");
    }

    @Test
    @Transactional
    public void getAllChannelReportsByContractCountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        channelReportRepository.saveAndFlush(channelReport);

        // Get all the channelReportList where contractCount greater than or equals to DEFAULT_CONTRACT_COUNT
        defaultChannelReportShouldBeFound("contractCount.greaterOrEqualThan=" + DEFAULT_CONTRACT_COUNT);

        // Get all the channelReportList where contractCount greater than or equals to UPDATED_CONTRACT_COUNT
        defaultChannelReportShouldNotBeFound("contractCount.greaterOrEqualThan=" + UPDATED_CONTRACT_COUNT);
    }

    @Test
    @Transactional
    public void getAllChannelReportsByContractCountIsLessThanSomething() throws Exception {
        // Initialize the database
        channelReportRepository.saveAndFlush(channelReport);

        // Get all the channelReportList where contractCount less than or equals to DEFAULT_CONTRACT_COUNT
        defaultChannelReportShouldNotBeFound("contractCount.lessThan=" + DEFAULT_CONTRACT_COUNT);

        // Get all the channelReportList where contractCount less than or equals to UPDATED_CONTRACT_COUNT
        defaultChannelReportShouldBeFound("contractCount.lessThan=" + UPDATED_CONTRACT_COUNT);
    }


    @Test
    @Transactional
    public void getAllChannelReportsByMoneyCollectedIsEqualToSomething() throws Exception {
        // Initialize the database
        channelReportRepository.saveAndFlush(channelReport);

        // Get all the channelReportList where moneyCollected equals to DEFAULT_MONEY_COLLECTED
        defaultChannelReportShouldBeFound("moneyCollected.equals=" + DEFAULT_MONEY_COLLECTED);

        // Get all the channelReportList where moneyCollected equals to UPDATED_MONEY_COLLECTED
        defaultChannelReportShouldNotBeFound("moneyCollected.equals=" + UPDATED_MONEY_COLLECTED);
    }

    @Test
    @Transactional
    public void getAllChannelReportsByMoneyCollectedIsInShouldWork() throws Exception {
        // Initialize the database
        channelReportRepository.saveAndFlush(channelReport);

        // Get all the channelReportList where moneyCollected in DEFAULT_MONEY_COLLECTED or UPDATED_MONEY_COLLECTED
        defaultChannelReportShouldBeFound("moneyCollected.in=" + DEFAULT_MONEY_COLLECTED + "," + UPDATED_MONEY_COLLECTED);

        // Get all the channelReportList where moneyCollected equals to UPDATED_MONEY_COLLECTED
        defaultChannelReportShouldNotBeFound("moneyCollected.in=" + UPDATED_MONEY_COLLECTED);
    }

    @Test
    @Transactional
    public void getAllChannelReportsByMoneyCollectedIsNullOrNotNull() throws Exception {
        // Initialize the database
        channelReportRepository.saveAndFlush(channelReport);

        // Get all the channelReportList where moneyCollected is not null
        defaultChannelReportShouldBeFound("moneyCollected.specified=true");

        // Get all the channelReportList where moneyCollected is null
        defaultChannelReportShouldNotBeFound("moneyCollected.specified=false");
    }

    @Test
    @Transactional
    public void getAllChannelReportsByContractMadeRateIsEqualToSomething() throws Exception {
        // Initialize the database
        channelReportRepository.saveAndFlush(channelReport);

        // Get all the channelReportList where contractMadeRate equals to DEFAULT_CONTRACT_MADE_RATE
        defaultChannelReportShouldBeFound("contractMadeRate.equals=" + DEFAULT_CONTRACT_MADE_RATE);

        // Get all the channelReportList where contractMadeRate equals to UPDATED_CONTRACT_MADE_RATE
        defaultChannelReportShouldNotBeFound("contractMadeRate.equals=" + UPDATED_CONTRACT_MADE_RATE);
    }

    @Test
    @Transactional
    public void getAllChannelReportsByContractMadeRateIsInShouldWork() throws Exception {
        // Initialize the database
        channelReportRepository.saveAndFlush(channelReport);

        // Get all the channelReportList where contractMadeRate in DEFAULT_CONTRACT_MADE_RATE or UPDATED_CONTRACT_MADE_RATE
        defaultChannelReportShouldBeFound("contractMadeRate.in=" + DEFAULT_CONTRACT_MADE_RATE + "," + UPDATED_CONTRACT_MADE_RATE);

        // Get all the channelReportList where contractMadeRate equals to UPDATED_CONTRACT_MADE_RATE
        defaultChannelReportShouldNotBeFound("contractMadeRate.in=" + UPDATED_CONTRACT_MADE_RATE);
    }

    @Test
    @Transactional
    public void getAllChannelReportsByContractMadeRateIsNullOrNotNull() throws Exception {
        // Initialize the database
        channelReportRepository.saveAndFlush(channelReport);

        // Get all the channelReportList where contractMadeRate is not null
        defaultChannelReportShouldBeFound("contractMadeRate.specified=true");

        // Get all the channelReportList where contractMadeRate is null
        defaultChannelReportShouldNotBeFound("contractMadeRate.specified=false");
    }

    @Test
    @Transactional
    public void getAllChannelReportsByRegionIdIsEqualToSomething() throws Exception {
        // Initialize the database
        channelReportRepository.saveAndFlush(channelReport);

        // Get all the channelReportList where regionId equals to DEFAULT_REGION_ID
        defaultChannelReportShouldBeFound("regionId.equals=" + DEFAULT_REGION_ID);

        // Get all the channelReportList where regionId equals to UPDATED_REGION_ID
        defaultChannelReportShouldNotBeFound("regionId.equals=" + UPDATED_REGION_ID);
    }

    @Test
    @Transactional
    public void getAllChannelReportsByRegionIdIsInShouldWork() throws Exception {
        // Initialize the database
        channelReportRepository.saveAndFlush(channelReport);

        // Get all the channelReportList where regionId in DEFAULT_REGION_ID or UPDATED_REGION_ID
        defaultChannelReportShouldBeFound("regionId.in=" + DEFAULT_REGION_ID + "," + UPDATED_REGION_ID);

        // Get all the channelReportList where regionId equals to UPDATED_REGION_ID
        defaultChannelReportShouldNotBeFound("regionId.in=" + UPDATED_REGION_ID);
    }

    @Test
    @Transactional
    public void getAllChannelReportsByRegionIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        channelReportRepository.saveAndFlush(channelReport);

        // Get all the channelReportList where regionId is not null
        defaultChannelReportShouldBeFound("regionId.specified=true");

        // Get all the channelReportList where regionId is null
        defaultChannelReportShouldNotBeFound("regionId.specified=false");
    }

    @Test
    @Transactional
    public void getAllChannelReportsByRegionIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        channelReportRepository.saveAndFlush(channelReport);

        // Get all the channelReportList where regionId greater than or equals to DEFAULT_REGION_ID
        defaultChannelReportShouldBeFound("regionId.greaterOrEqualThan=" + DEFAULT_REGION_ID);

        // Get all the channelReportList where regionId greater than or equals to UPDATED_REGION_ID
        defaultChannelReportShouldNotBeFound("regionId.greaterOrEqualThan=" + UPDATED_REGION_ID);
    }

    @Test
    @Transactional
    public void getAllChannelReportsByRegionIdIsLessThanSomething() throws Exception {
        // Initialize the database
        channelReportRepository.saveAndFlush(channelReport);

        // Get all the channelReportList where regionId less than or equals to DEFAULT_REGION_ID
        defaultChannelReportShouldNotBeFound("regionId.lessThan=" + DEFAULT_REGION_ID);

        // Get all the channelReportList where regionId less than or equals to UPDATED_REGION_ID
        defaultChannelReportShouldBeFound("regionId.lessThan=" + UPDATED_REGION_ID);
    }


    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultChannelReportShouldBeFound(String filter) throws Exception {
        restChannelReportMockMvc.perform(get("/api/channel-reports?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(channelReport.getId().intValue())))
            .andExpect(jsonPath("$.[*].month").value(hasItem(DEFAULT_MONTH)))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
            .andExpect(jsonPath("$.[*].channelId").value(hasItem(DEFAULT_CHANNEL_ID.intValue())))
            .andExpect(jsonPath("$.[*].channelName").value(hasItem(DEFAULT_CHANNEL_NAME)))
            .andExpect(jsonPath("$.[*].visitedCustomerCount").value(hasItem(DEFAULT_VISITED_CUSTOMER_COUNT)))
            .andExpect(jsonPath("$.[*].cardCount").value(hasItem(DEFAULT_CARD_COUNT)))
            .andExpect(jsonPath("$.[*].contractCount").value(hasItem(DEFAULT_CONTRACT_COUNT)))
            .andExpect(jsonPath("$.[*].moneyCollected").value(hasItem(DEFAULT_MONEY_COLLECTED.doubleValue())))
            .andExpect(jsonPath("$.[*].contractMadeRate").value(hasItem(DEFAULT_CONTRACT_MADE_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].regionId").value(hasItem(DEFAULT_REGION_ID.intValue())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultChannelReportShouldNotBeFound(String filter) throws Exception {
        restChannelReportMockMvc.perform(get("/api/channel-reports?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingChannelReport() throws Exception {
        // Get the channelReport
        restChannelReportMockMvc.perform(get("/api/channel-reports/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateChannelReport() throws Exception {
        // Initialize the database
        channelReportService.save(channelReport);

        int databaseSizeBeforeUpdate = channelReportRepository.findAll().size();

        // Update the channelReport
        ChannelReport updatedChannelReport = channelReportRepository.findOne(channelReport.getId());
        updatedChannelReport
            .month(UPDATED_MONTH)
            .year(UPDATED_YEAR)
            .channelId(UPDATED_CHANNEL_ID)
            .channelName(UPDATED_CHANNEL_NAME)
            .visitedCustomerCount(UPDATED_VISITED_CUSTOMER_COUNT)
            .cardCount(UPDATED_CARD_COUNT)
            .contractCount(UPDATED_CONTRACT_COUNT)
            .moneyCollected(UPDATED_MONEY_COLLECTED)
            .contractMadeRate(UPDATED_CONTRACT_MADE_RATE)
            .regionId(UPDATED_REGION_ID);

        restChannelReportMockMvc.perform(put("/api/channel-reports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedChannelReport)))
            .andExpect(status().isOk());

        // Validate the ChannelReport in the database
        List<ChannelReport> channelReportList = channelReportRepository.findAll();
        assertThat(channelReportList).hasSize(databaseSizeBeforeUpdate);
        ChannelReport testChannelReport = channelReportList.get(channelReportList.size() - 1);
        assertThat(testChannelReport.getMonth()).isEqualTo(UPDATED_MONTH);
        assertThat(testChannelReport.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testChannelReport.getChannelId()).isEqualTo(UPDATED_CHANNEL_ID);
        assertThat(testChannelReport.getChannelName()).isEqualTo(UPDATED_CHANNEL_NAME);
        assertThat(testChannelReport.getVisitedCustomerCount()).isEqualTo(UPDATED_VISITED_CUSTOMER_COUNT);
        assertThat(testChannelReport.getCardCount()).isEqualTo(UPDATED_CARD_COUNT);
        assertThat(testChannelReport.getContractCount()).isEqualTo(UPDATED_CONTRACT_COUNT);
        assertThat(testChannelReport.getMoneyCollected()).isEqualTo(UPDATED_MONEY_COLLECTED);
        assertThat(testChannelReport.getContractMadeRate()).isEqualTo(UPDATED_CONTRACT_MADE_RATE);
        assertThat(testChannelReport.getRegionId()).isEqualTo(UPDATED_REGION_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingChannelReport() throws Exception {
        int databaseSizeBeforeUpdate = channelReportRepository.findAll().size();

        // Create the ChannelReport

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restChannelReportMockMvc.perform(put("/api/channel-reports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(channelReport)))
            .andExpect(status().isCreated());

        // Validate the ChannelReport in the database
        List<ChannelReport> channelReportList = channelReportRepository.findAll();
        assertThat(channelReportList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteChannelReport() throws Exception {
        // Initialize the database
        channelReportService.save(channelReport);

        int databaseSizeBeforeDelete = channelReportRepository.findAll().size();

        // Get the channelReport
        restChannelReportMockMvc.perform(delete("/api/channel-reports/{id}", channelReport.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ChannelReport> channelReportList = channelReportRepository.findAll();
        assertThat(channelReportList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ChannelReport.class);
        ChannelReport channelReport1 = new ChannelReport();
        channelReport1.setId(1L);
        ChannelReport channelReport2 = new ChannelReport();
        channelReport2.setId(channelReport1.getId());
        assertThat(channelReport1).isEqualTo(channelReport2);
        channelReport2.setId(2L);
        assertThat(channelReport1).isNotEqualTo(channelReport2);
        channelReport1.setId(null);
        assertThat(channelReport1).isNotEqualTo(channelReport2);
    }
}
