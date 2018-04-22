package com.pure.service.web.rest;

import com.pure.service.SimpleServiceApp;
import com.pure.service.domain.FreeClassRecord;
import com.pure.service.repository.FreeClassRecordRepository;
import com.pure.service.service.FreeClassRecordQueryService;
import com.pure.service.service.FreeClassRecordService;
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
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;

import static com.pure.service.web.rest.TestUtil.sameInstant;
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
 * Test class for the FreeClassRecordResource REST controller.
 *
 * @see FreeClassRecordResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SimpleServiceApp.class)
public class FreeClassRecordResourceIntTest {

    private static final String DEFAULT_PERSON_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PERSON_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT_PHONE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_LAST_MODIFIED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LAST_MODIFIED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private FreeClassRecordRepository freeClassRecordRepository;

    @Autowired
    private FreeClassRecordService freeClassRecordService;

    @Autowired
    private FreeClassRecordQueryService freeClassRecordQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFreeClassRecordMockMvc;

    private FreeClassRecord freeClassRecord;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FreeClassRecordResource freeClassRecordResource = new FreeClassRecordResource(freeClassRecordService, freeClassRecordQueryService);
        this.restFreeClassRecordMockMvc = MockMvcBuilders.standaloneSetup(freeClassRecordResource)
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
    public static FreeClassRecord createEntity(EntityManager em) {
        FreeClassRecord freeClassRecord = new FreeClassRecord()
            .personName(DEFAULT_PERSON_NAME)
            .contactPhoneNumber(DEFAULT_CONTACT_PHONE_NUMBER);
//            .createdBy(DEFAULT_CREATED_BY)
//            .createdDate(DEFAULT_CREATED_DATE)
//            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
//            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return freeClassRecord;
    }

    @Before
    public void initTest() {
        freeClassRecord = createEntity(em);
    }

    @Test
    @Transactional
    public void createFreeClassRecord() throws Exception {
        int databaseSizeBeforeCreate = freeClassRecordRepository.findAll().size();

        // Create the FreeClassRecord
        restFreeClassRecordMockMvc.perform(post("/api/free-class-records")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(freeClassRecord)))
            .andExpect(status().isCreated());

        // Validate the FreeClassRecord in the database
        List<FreeClassRecord> freeClassRecordList = freeClassRecordRepository.findAll();
        assertThat(freeClassRecordList).hasSize(databaseSizeBeforeCreate + 1);
        FreeClassRecord testFreeClassRecord = freeClassRecordList.get(freeClassRecordList.size() - 1);
        assertThat(testFreeClassRecord.getPersonName()).isEqualTo(DEFAULT_PERSON_NAME);
        assertThat(testFreeClassRecord.getContactPhoneNumber()).isEqualTo(DEFAULT_CONTACT_PHONE_NUMBER);
        assertThat(testFreeClassRecord.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testFreeClassRecord.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testFreeClassRecord.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testFreeClassRecord.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createFreeClassRecordWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = freeClassRecordRepository.findAll().size();

        // Create the FreeClassRecord with an existing ID
        freeClassRecord.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFreeClassRecordMockMvc.perform(post("/api/free-class-records")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(freeClassRecord)))
            .andExpect(status().isBadRequest());

        // Validate the FreeClassRecord in the database
        List<FreeClassRecord> freeClassRecordList = freeClassRecordRepository.findAll();
        assertThat(freeClassRecordList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllFreeClassRecords() throws Exception {
        // Initialize the database
        freeClassRecordRepository.saveAndFlush(freeClassRecord);

        // Get all the freeClassRecordList
        restFreeClassRecordMockMvc.perform(get("/api/free-class-records?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(freeClassRecord.getId().intValue())))
            .andExpect(jsonPath("$.[*].personName").value(hasItem(DEFAULT_PERSON_NAME.toString())))
            .andExpect(jsonPath("$.[*].contactPhoneNumber").value(hasItem(DEFAULT_CONTACT_PHONE_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(sameInstant(DEFAULT_LAST_MODIFIED_DATE))));
    }

    @Test
    @Transactional
    public void getFreeClassRecord() throws Exception {
        // Initialize the database
        freeClassRecordRepository.saveAndFlush(freeClassRecord);

        // Get the freeClassRecord
        restFreeClassRecordMockMvc.perform(get("/api/free-class-records/{id}", freeClassRecord.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(freeClassRecord.getId().intValue()))
            .andExpect(jsonPath("$.personName").value(DEFAULT_PERSON_NAME.toString()))
            .andExpect(jsonPath("$.contactPhoneNumber").value(DEFAULT_CONTACT_PHONE_NUMBER.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.createdDate").value(sameInstant(DEFAULT_CREATED_DATE)))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.lastModifiedDate").value(sameInstant(DEFAULT_LAST_MODIFIED_DATE)));
    }

    @Test
    @Transactional
    public void getAllFreeClassRecordsByPersonNameIsEqualToSomething() throws Exception {
        // Initialize the database
        freeClassRecordRepository.saveAndFlush(freeClassRecord);

        // Get all the freeClassRecordList where personName equals to DEFAULT_PERSON_NAME
        defaultFreeClassRecordShouldBeFound("personName.equals=" + DEFAULT_PERSON_NAME);

        // Get all the freeClassRecordList where personName equals to UPDATED_PERSON_NAME
        defaultFreeClassRecordShouldNotBeFound("personName.equals=" + UPDATED_PERSON_NAME);
    }

    @Test
    @Transactional
    public void getAllFreeClassRecordsByPersonNameIsInShouldWork() throws Exception {
        // Initialize the database
        freeClassRecordRepository.saveAndFlush(freeClassRecord);

        // Get all the freeClassRecordList where personName in DEFAULT_PERSON_NAME or UPDATED_PERSON_NAME
        defaultFreeClassRecordShouldBeFound("personName.in=" + DEFAULT_PERSON_NAME + "," + UPDATED_PERSON_NAME);

        // Get all the freeClassRecordList where personName equals to UPDATED_PERSON_NAME
        defaultFreeClassRecordShouldNotBeFound("personName.in=" + UPDATED_PERSON_NAME);
    }

    @Test
    @Transactional
    public void getAllFreeClassRecordsByPersonNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        freeClassRecordRepository.saveAndFlush(freeClassRecord);

        // Get all the freeClassRecordList where personName is not null
        defaultFreeClassRecordShouldBeFound("personName.specified=true");

        // Get all the freeClassRecordList where personName is null
        defaultFreeClassRecordShouldNotBeFound("personName.specified=false");
    }

    @Test
    @Transactional
    public void getAllFreeClassRecordsByContactPhoneNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        freeClassRecordRepository.saveAndFlush(freeClassRecord);

        // Get all the freeClassRecordList where contactPhoneNumber equals to DEFAULT_CONTACT_PHONE_NUMBER
        defaultFreeClassRecordShouldBeFound("contactPhoneNumber.equals=" + DEFAULT_CONTACT_PHONE_NUMBER);

        // Get all the freeClassRecordList where contactPhoneNumber equals to UPDATED_CONTACT_PHONE_NUMBER
        defaultFreeClassRecordShouldNotBeFound("contactPhoneNumber.equals=" + UPDATED_CONTACT_PHONE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllFreeClassRecordsByContactPhoneNumberIsInShouldWork() throws Exception {
        // Initialize the database
        freeClassRecordRepository.saveAndFlush(freeClassRecord);

        // Get all the freeClassRecordList where contactPhoneNumber in DEFAULT_CONTACT_PHONE_NUMBER or UPDATED_CONTACT_PHONE_NUMBER
        defaultFreeClassRecordShouldBeFound("contactPhoneNumber.in=" + DEFAULT_CONTACT_PHONE_NUMBER + "," + UPDATED_CONTACT_PHONE_NUMBER);

        // Get all the freeClassRecordList where contactPhoneNumber equals to UPDATED_CONTACT_PHONE_NUMBER
        defaultFreeClassRecordShouldNotBeFound("contactPhoneNumber.in=" + UPDATED_CONTACT_PHONE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllFreeClassRecordsByContactPhoneNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        freeClassRecordRepository.saveAndFlush(freeClassRecord);

        // Get all the freeClassRecordList where contactPhoneNumber is not null
        defaultFreeClassRecordShouldBeFound("contactPhoneNumber.specified=true");

        // Get all the freeClassRecordList where contactPhoneNumber is null
        defaultFreeClassRecordShouldNotBeFound("contactPhoneNumber.specified=false");
    }

    @Test
    @Transactional
    public void getAllFreeClassRecordsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        freeClassRecordRepository.saveAndFlush(freeClassRecord);

        // Get all the freeClassRecordList where createdBy equals to DEFAULT_CREATED_BY
        defaultFreeClassRecordShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the freeClassRecordList where createdBy equals to UPDATED_CREATED_BY
        defaultFreeClassRecordShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllFreeClassRecordsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        freeClassRecordRepository.saveAndFlush(freeClassRecord);

        // Get all the freeClassRecordList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultFreeClassRecordShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the freeClassRecordList where createdBy equals to UPDATED_CREATED_BY
        defaultFreeClassRecordShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllFreeClassRecordsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        freeClassRecordRepository.saveAndFlush(freeClassRecord);

        // Get all the freeClassRecordList where createdBy is not null
        defaultFreeClassRecordShouldBeFound("createdBy.specified=true");

        // Get all the freeClassRecordList where createdBy is null
        defaultFreeClassRecordShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllFreeClassRecordsByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        freeClassRecordRepository.saveAndFlush(freeClassRecord);

        // Get all the freeClassRecordList where createdDate equals to DEFAULT_CREATED_DATE
        defaultFreeClassRecordShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the freeClassRecordList where createdDate equals to UPDATED_CREATED_DATE
        defaultFreeClassRecordShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllFreeClassRecordsByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        freeClassRecordRepository.saveAndFlush(freeClassRecord);

        // Get all the freeClassRecordList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultFreeClassRecordShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the freeClassRecordList where createdDate equals to UPDATED_CREATED_DATE
        defaultFreeClassRecordShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllFreeClassRecordsByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        freeClassRecordRepository.saveAndFlush(freeClassRecord);

        // Get all the freeClassRecordList where createdDate is not null
        defaultFreeClassRecordShouldBeFound("createdDate.specified=true");

        // Get all the freeClassRecordList where createdDate is null
        defaultFreeClassRecordShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllFreeClassRecordsByCreatedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        freeClassRecordRepository.saveAndFlush(freeClassRecord);

        // Get all the freeClassRecordList where createdDate greater than or equals to DEFAULT_CREATED_DATE
        defaultFreeClassRecordShouldBeFound("createdDate.greaterOrEqualThan=" + DEFAULT_CREATED_DATE);

        // Get all the freeClassRecordList where createdDate greater than or equals to UPDATED_CREATED_DATE
        defaultFreeClassRecordShouldNotBeFound("createdDate.greaterOrEqualThan=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllFreeClassRecordsByCreatedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        freeClassRecordRepository.saveAndFlush(freeClassRecord);

        // Get all the freeClassRecordList where createdDate less than or equals to DEFAULT_CREATED_DATE
        defaultFreeClassRecordShouldNotBeFound("createdDate.lessThan=" + DEFAULT_CREATED_DATE);

        // Get all the freeClassRecordList where createdDate less than or equals to UPDATED_CREATED_DATE
        defaultFreeClassRecordShouldBeFound("createdDate.lessThan=" + UPDATED_CREATED_DATE);
    }


    @Test
    @Transactional
    public void getAllFreeClassRecordsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        freeClassRecordRepository.saveAndFlush(freeClassRecord);

        // Get all the freeClassRecordList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultFreeClassRecordShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the freeClassRecordList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultFreeClassRecordShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllFreeClassRecordsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        freeClassRecordRepository.saveAndFlush(freeClassRecord);

        // Get all the freeClassRecordList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultFreeClassRecordShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the freeClassRecordList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultFreeClassRecordShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllFreeClassRecordsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        freeClassRecordRepository.saveAndFlush(freeClassRecord);

        // Get all the freeClassRecordList where lastModifiedBy is not null
        defaultFreeClassRecordShouldBeFound("lastModifiedBy.specified=true");

        // Get all the freeClassRecordList where lastModifiedBy is null
        defaultFreeClassRecordShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllFreeClassRecordsByLastModifiedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        freeClassRecordRepository.saveAndFlush(freeClassRecord);

        // Get all the freeClassRecordList where lastModifiedDate equals to DEFAULT_LAST_MODIFIED_DATE
        defaultFreeClassRecordShouldBeFound("lastModifiedDate.equals=" + DEFAULT_LAST_MODIFIED_DATE);

        // Get all the freeClassRecordList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultFreeClassRecordShouldNotBeFound("lastModifiedDate.equals=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllFreeClassRecordsByLastModifiedDateIsInShouldWork() throws Exception {
        // Initialize the database
        freeClassRecordRepository.saveAndFlush(freeClassRecord);

        // Get all the freeClassRecordList where lastModifiedDate in DEFAULT_LAST_MODIFIED_DATE or UPDATED_LAST_MODIFIED_DATE
        defaultFreeClassRecordShouldBeFound("lastModifiedDate.in=" + DEFAULT_LAST_MODIFIED_DATE + "," + UPDATED_LAST_MODIFIED_DATE);

        // Get all the freeClassRecordList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultFreeClassRecordShouldNotBeFound("lastModifiedDate.in=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllFreeClassRecordsByLastModifiedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        freeClassRecordRepository.saveAndFlush(freeClassRecord);

        // Get all the freeClassRecordList where lastModifiedDate is not null
        defaultFreeClassRecordShouldBeFound("lastModifiedDate.specified=true");

        // Get all the freeClassRecordList where lastModifiedDate is null
        defaultFreeClassRecordShouldNotBeFound("lastModifiedDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllFreeClassRecordsByLastModifiedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        freeClassRecordRepository.saveAndFlush(freeClassRecord);

        // Get all the freeClassRecordList where lastModifiedDate greater than or equals to DEFAULT_LAST_MODIFIED_DATE
        defaultFreeClassRecordShouldBeFound("lastModifiedDate.greaterOrEqualThan=" + DEFAULT_LAST_MODIFIED_DATE);

        // Get all the freeClassRecordList where lastModifiedDate greater than or equals to UPDATED_LAST_MODIFIED_DATE
        defaultFreeClassRecordShouldNotBeFound("lastModifiedDate.greaterOrEqualThan=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllFreeClassRecordsByLastModifiedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        freeClassRecordRepository.saveAndFlush(freeClassRecord);

        // Get all the freeClassRecordList where lastModifiedDate less than or equals to DEFAULT_LAST_MODIFIED_DATE
        defaultFreeClassRecordShouldNotBeFound("lastModifiedDate.lessThan=" + DEFAULT_LAST_MODIFIED_DATE);

        // Get all the freeClassRecordList where lastModifiedDate less than or equals to UPDATED_LAST_MODIFIED_DATE
        defaultFreeClassRecordShouldBeFound("lastModifiedDate.lessThan=" + UPDATED_LAST_MODIFIED_DATE);
    }


    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultFreeClassRecordShouldBeFound(String filter) throws Exception {
        restFreeClassRecordMockMvc.perform(get("/api/free-class-records?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(freeClassRecord.getId().intValue())))
            .andExpect(jsonPath("$.[*].personName").value(hasItem(DEFAULT_PERSON_NAME.toString())))
            .andExpect(jsonPath("$.[*].contactPhoneNumber").value(hasItem(DEFAULT_CONTACT_PHONE_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(sameInstant(DEFAULT_LAST_MODIFIED_DATE))));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultFreeClassRecordShouldNotBeFound(String filter) throws Exception {
        restFreeClassRecordMockMvc.perform(get("/api/free-class-records?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingFreeClassRecord() throws Exception {
        // Get the freeClassRecord
        restFreeClassRecordMockMvc.perform(get("/api/free-class-records/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFreeClassRecord() throws Exception {
        // Initialize the database
        freeClassRecordService.save(freeClassRecord);

        int databaseSizeBeforeUpdate = freeClassRecordRepository.findAll().size();

        // Update the freeClassRecord
        FreeClassRecord updatedFreeClassRecord = freeClassRecordRepository.findOne(freeClassRecord.getId());
        updatedFreeClassRecord
            .personName(UPDATED_PERSON_NAME)
            .contactPhoneNumber(UPDATED_CONTACT_PHONE_NUMBER);
           // .createdBy(UPDATED_CREATED_BY)
           // .createdDate(UPDATED_CREATED_DATE)
            //.lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            //.lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restFreeClassRecordMockMvc.perform(put("/api/free-class-records")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFreeClassRecord)))
            .andExpect(status().isOk());

        // Validate the FreeClassRecord in the database
        List<FreeClassRecord> freeClassRecordList = freeClassRecordRepository.findAll();
        assertThat(freeClassRecordList).hasSize(databaseSizeBeforeUpdate);
        FreeClassRecord testFreeClassRecord = freeClassRecordList.get(freeClassRecordList.size() - 1);
        assertThat(testFreeClassRecord.getPersonName()).isEqualTo(UPDATED_PERSON_NAME);
        assertThat(testFreeClassRecord.getContactPhoneNumber()).isEqualTo(UPDATED_CONTACT_PHONE_NUMBER);
        assertThat(testFreeClassRecord.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testFreeClassRecord.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testFreeClassRecord.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testFreeClassRecord.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingFreeClassRecord() throws Exception {
        int databaseSizeBeforeUpdate = freeClassRecordRepository.findAll().size();

        // Create the FreeClassRecord

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFreeClassRecordMockMvc.perform(put("/api/free-class-records")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(freeClassRecord)))
            .andExpect(status().isCreated());

        // Validate the FreeClassRecord in the database
        List<FreeClassRecord> freeClassRecordList = freeClassRecordRepository.findAll();
        assertThat(freeClassRecordList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteFreeClassRecord() throws Exception {
        // Initialize the database
        freeClassRecordService.save(freeClassRecord);

        int databaseSizeBeforeDelete = freeClassRecordRepository.findAll().size();

        // Get the freeClassRecord
        restFreeClassRecordMockMvc.perform(delete("/api/free-class-records/{id}", freeClassRecord.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<FreeClassRecord> freeClassRecordList = freeClassRecordRepository.findAll();
        assertThat(freeClassRecordList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FreeClassRecord.class);
        FreeClassRecord freeClassRecord1 = new FreeClassRecord();
        freeClassRecord1.setId(1L);
        FreeClassRecord freeClassRecord2 = new FreeClassRecord();
        freeClassRecord2.setId(freeClassRecord1.getId());
        assertThat(freeClassRecord1).isEqualTo(freeClassRecord2);
        freeClassRecord2.setId(2L);
        assertThat(freeClassRecord1).isNotEqualTo(freeClassRecord2);
        freeClassRecord1.setId(null);
        assertThat(freeClassRecord1).isNotEqualTo(freeClassRecord2);
    }
}
