package com.pure.service.web.rest;

import com.pure.service.SimpleServiceApp;
import com.pure.service.domain.MarketingQrcode;
import com.pure.service.repository.MarketingQrcodeRepository;
import com.pure.service.service.MarketingQrcodeQueryService;
import com.pure.service.service.MarketingQrcodeService;
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
 * Test class for the MarketingQrcodeResource REST controller.
 *
 * @see MarketingQrcodeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SimpleServiceApp.class)
public class MarketingQrcodeResourceIntTest {

    private static final String DEFAULT_FILE_URL = "AAAAAAAAAA";
    private static final String UPDATED_FILE_URL = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private MarketingQrcodeRepository marketingQrcodeRepository;

    @Autowired
    private MarketingQrcodeService marketingQrcodeService;

    @Autowired
    private MarketingQrcodeQueryService marketingQrcodeQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMarketingQrcodeMockMvc;

    private MarketingQrcode marketingQrcode;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MarketingQrcodeResource marketingQrcodeResource = new MarketingQrcodeResource(marketingQrcodeService, marketingQrcodeQueryService);
        this.restMarketingQrcodeMockMvc = MockMvcBuilders.standaloneSetup(marketingQrcodeResource)
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
    public static MarketingQrcode createEntity(EntityManager em) {
        MarketingQrcode marketingQrcode = new MarketingQrcode()
            .fileUrl(DEFAULT_FILE_URL);
        return marketingQrcode;
    }

    @Before
    public void initTest() {
        marketingQrcode = createEntity(em);
    }

    @Test
    @Transactional
    public void createMarketingQrcode() throws Exception {
        int databaseSizeBeforeCreate = marketingQrcodeRepository.findAll().size();

        // Create the MarketingQrcode
        restMarketingQrcodeMockMvc.perform(post("/api/marketing-qrcodes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(marketingQrcode)))
            .andExpect(status().isCreated());

        // Validate the MarketingQrcode in the database
        List<MarketingQrcode> marketingQrcodeList = marketingQrcodeRepository.findAll();
        assertThat(marketingQrcodeList).hasSize(databaseSizeBeforeCreate + 1);
        MarketingQrcode testMarketingQrcode = marketingQrcodeList.get(marketingQrcodeList.size() - 1);
        assertThat(testMarketingQrcode.getFileUrl()).isEqualTo(DEFAULT_FILE_URL);
        assertThat(testMarketingQrcode.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testMarketingQrcode.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testMarketingQrcode.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testMarketingQrcode.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createMarketingQrcodeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = marketingQrcodeRepository.findAll().size();

        // Create the MarketingQrcode with an existing ID
        marketingQrcode.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMarketingQrcodeMockMvc.perform(post("/api/marketing-qrcodes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(marketingQrcode)))
            .andExpect(status().isBadRequest());

        // Validate the MarketingQrcode in the database
        List<MarketingQrcode> marketingQrcodeList = marketingQrcodeRepository.findAll();
        assertThat(marketingQrcodeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMarketingQrcodes() throws Exception {
        // Initialize the database
        marketingQrcodeRepository.saveAndFlush(marketingQrcode);

        // Get all the marketingQrcodeList
        restMarketingQrcodeMockMvc.perform(get("/api/marketing-qrcodes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(marketingQrcode.getId().intValue())))
            .andExpect(jsonPath("$.[*].fileUrl").value(hasItem(DEFAULT_FILE_URL)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    public void getMarketingQrcode() throws Exception {
        // Initialize the database
        marketingQrcodeRepository.saveAndFlush(marketingQrcode);

        // Get the marketingQrcode
        restMarketingQrcodeMockMvc.perform(get("/api/marketing-qrcodes/{id}", marketingQrcode.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(marketingQrcode.getId().intValue()))
            .andExpect(jsonPath("$.fileUrl").value(DEFAULT_FILE_URL))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    public void getAllMarketingQrcodesByFileUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        marketingQrcodeRepository.saveAndFlush(marketingQrcode);

        // Get all the marketingQrcodeList where fileUrl equals to DEFAULT_FILE_URL
        defaultMarketingQrcodeShouldBeFound("fileUrl.equals=" + DEFAULT_FILE_URL);

        // Get all the marketingQrcodeList where fileUrl equals to UPDATED_FILE_URL
        defaultMarketingQrcodeShouldNotBeFound("fileUrl.equals=" + UPDATED_FILE_URL);
    }

    @Test
    @Transactional
    public void getAllMarketingQrcodesByFileUrlIsInShouldWork() throws Exception {
        // Initialize the database
        marketingQrcodeRepository.saveAndFlush(marketingQrcode);

        // Get all the marketingQrcodeList where fileUrl in DEFAULT_FILE_URL or UPDATED_FILE_URL
        defaultMarketingQrcodeShouldBeFound("fileUrl.in=" + DEFAULT_FILE_URL + "," + UPDATED_FILE_URL);

        // Get all the marketingQrcodeList where fileUrl equals to UPDATED_FILE_URL
        defaultMarketingQrcodeShouldNotBeFound("fileUrl.in=" + UPDATED_FILE_URL);
    }

    @Test
    @Transactional
    public void getAllMarketingQrcodesByFileUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        marketingQrcodeRepository.saveAndFlush(marketingQrcode);

        // Get all the marketingQrcodeList where fileUrl is not null
        defaultMarketingQrcodeShouldBeFound("fileUrl.specified=true");

        // Get all the marketingQrcodeList where fileUrl is null
        defaultMarketingQrcodeShouldNotBeFound("fileUrl.specified=false");
    }

    @Test
    @Transactional
    public void getAllMarketingQrcodesByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        marketingQrcodeRepository.saveAndFlush(marketingQrcode);

        // Get all the marketingQrcodeList where createdBy equals to DEFAULT_CREATED_BY
        defaultMarketingQrcodeShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the marketingQrcodeList where createdBy equals to UPDATED_CREATED_BY
        defaultMarketingQrcodeShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllMarketingQrcodesByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        marketingQrcodeRepository.saveAndFlush(marketingQrcode);

        // Get all the marketingQrcodeList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultMarketingQrcodeShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the marketingQrcodeList where createdBy equals to UPDATED_CREATED_BY
        defaultMarketingQrcodeShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllMarketingQrcodesByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        marketingQrcodeRepository.saveAndFlush(marketingQrcode);

        // Get all the marketingQrcodeList where createdBy is not null
        defaultMarketingQrcodeShouldBeFound("createdBy.specified=true");

        // Get all the marketingQrcodeList where createdBy is null
        defaultMarketingQrcodeShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllMarketingQrcodesByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        marketingQrcodeRepository.saveAndFlush(marketingQrcode);

        // Get all the marketingQrcodeList where createdDate equals to DEFAULT_CREATED_DATE
        defaultMarketingQrcodeShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the marketingQrcodeList where createdDate equals to UPDATED_CREATED_DATE
        defaultMarketingQrcodeShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllMarketingQrcodesByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        marketingQrcodeRepository.saveAndFlush(marketingQrcode);

        // Get all the marketingQrcodeList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultMarketingQrcodeShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the marketingQrcodeList where createdDate equals to UPDATED_CREATED_DATE
        defaultMarketingQrcodeShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllMarketingQrcodesByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        marketingQrcodeRepository.saveAndFlush(marketingQrcode);

        // Get all the marketingQrcodeList where createdDate is not null
        defaultMarketingQrcodeShouldBeFound("createdDate.specified=true");

        // Get all the marketingQrcodeList where createdDate is null
        defaultMarketingQrcodeShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllMarketingQrcodesByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        marketingQrcodeRepository.saveAndFlush(marketingQrcode);

        // Get all the marketingQrcodeList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultMarketingQrcodeShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the marketingQrcodeList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultMarketingQrcodeShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllMarketingQrcodesByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        marketingQrcodeRepository.saveAndFlush(marketingQrcode);

        // Get all the marketingQrcodeList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultMarketingQrcodeShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the marketingQrcodeList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultMarketingQrcodeShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllMarketingQrcodesByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        marketingQrcodeRepository.saveAndFlush(marketingQrcode);

        // Get all the marketingQrcodeList where lastModifiedBy is not null
        defaultMarketingQrcodeShouldBeFound("lastModifiedBy.specified=true");

        // Get all the marketingQrcodeList where lastModifiedBy is null
        defaultMarketingQrcodeShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllMarketingQrcodesByLastModifiedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        marketingQrcodeRepository.saveAndFlush(marketingQrcode);

        // Get all the marketingQrcodeList where lastModifiedDate equals to DEFAULT_LAST_MODIFIED_DATE
        defaultMarketingQrcodeShouldBeFound("lastModifiedDate.equals=" + DEFAULT_LAST_MODIFIED_DATE);

        // Get all the marketingQrcodeList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultMarketingQrcodeShouldNotBeFound("lastModifiedDate.equals=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllMarketingQrcodesByLastModifiedDateIsInShouldWork() throws Exception {
        // Initialize the database
        marketingQrcodeRepository.saveAndFlush(marketingQrcode);

        // Get all the marketingQrcodeList where lastModifiedDate in DEFAULT_LAST_MODIFIED_DATE or UPDATED_LAST_MODIFIED_DATE
        defaultMarketingQrcodeShouldBeFound("lastModifiedDate.in=" + DEFAULT_LAST_MODIFIED_DATE + "," + UPDATED_LAST_MODIFIED_DATE);

        // Get all the marketingQrcodeList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultMarketingQrcodeShouldNotBeFound("lastModifiedDate.in=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllMarketingQrcodesByLastModifiedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        marketingQrcodeRepository.saveAndFlush(marketingQrcode);

        // Get all the marketingQrcodeList where lastModifiedDate is not null
        defaultMarketingQrcodeShouldBeFound("lastModifiedDate.specified=true");

        // Get all the marketingQrcodeList where lastModifiedDate is null
        defaultMarketingQrcodeShouldNotBeFound("lastModifiedDate.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultMarketingQrcodeShouldBeFound(String filter) throws Exception {
        restMarketingQrcodeMockMvc.perform(get("/api/marketing-qrcodes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(marketingQrcode.getId().intValue())))
            .andExpect(jsonPath("$.[*].fileUrl").value(hasItem(DEFAULT_FILE_URL)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultMarketingQrcodeShouldNotBeFound(String filter) throws Exception {
        restMarketingQrcodeMockMvc.perform(get("/api/marketing-qrcodes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingMarketingQrcode() throws Exception {
        // Get the marketingQrcode
        restMarketingQrcodeMockMvc.perform(get("/api/marketing-qrcodes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMarketingQrcode() throws Exception {
        // Initialize the database
        marketingQrcodeService.save(marketingQrcode);

        int databaseSizeBeforeUpdate = marketingQrcodeRepository.findAll().size();

        // Update the marketingQrcode
        MarketingQrcode updatedMarketingQrcode = marketingQrcodeRepository.findOne(marketingQrcode.getId());
        updatedMarketingQrcode
            .fileUrl(UPDATED_FILE_URL);

        restMarketingQrcodeMockMvc.perform(put("/api/marketing-qrcodes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMarketingQrcode)))
            .andExpect(status().isOk());

        // Validate the MarketingQrcode in the database
        List<MarketingQrcode> marketingQrcodeList = marketingQrcodeRepository.findAll();
        assertThat(marketingQrcodeList).hasSize(databaseSizeBeforeUpdate);
        MarketingQrcode testMarketingQrcode = marketingQrcodeList.get(marketingQrcodeList.size() - 1);
        assertThat(testMarketingQrcode.getFileUrl()).isEqualTo(UPDATED_FILE_URL);
        assertThat(testMarketingQrcode.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testMarketingQrcode.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testMarketingQrcode.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testMarketingQrcode.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingMarketingQrcode() throws Exception {
        int databaseSizeBeforeUpdate = marketingQrcodeRepository.findAll().size();

        // Create the MarketingQrcode

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMarketingQrcodeMockMvc.perform(put("/api/marketing-qrcodes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(marketingQrcode)))
            .andExpect(status().isCreated());

        // Validate the MarketingQrcode in the database
        List<MarketingQrcode> marketingQrcodeList = marketingQrcodeRepository.findAll();
        assertThat(marketingQrcodeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMarketingQrcode() throws Exception {
        // Initialize the database
        marketingQrcodeService.save(marketingQrcode);

        int databaseSizeBeforeDelete = marketingQrcodeRepository.findAll().size();

        // Get the marketingQrcode
        restMarketingQrcodeMockMvc.perform(delete("/api/marketing-qrcodes/{id}", marketingQrcode.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MarketingQrcode> marketingQrcodeList = marketingQrcodeRepository.findAll();
        assertThat(marketingQrcodeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MarketingQrcode.class);
        MarketingQrcode marketingQrcode1 = new MarketingQrcode();
        marketingQrcode1.setId(1L);
        MarketingQrcode marketingQrcode2 = new MarketingQrcode();
        marketingQrcode2.setId(marketingQrcode1.getId());
        assertThat(marketingQrcode1).isEqualTo(marketingQrcode2);
        marketingQrcode2.setId(2L);
        assertThat(marketingQrcode1).isNotEqualTo(marketingQrcode2);
        marketingQrcode1.setId(null);
        assertThat(marketingQrcode1).isNotEqualTo(marketingQrcode2);
    }
}
