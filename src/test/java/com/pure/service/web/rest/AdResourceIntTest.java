package com.pure.service.web.rest;

import com.pure.service.SimpleServiceApp;

import com.pure.service.domain.Ad;
import com.pure.service.repository.AdRepository;
import com.pure.service.service.AdService;
import com.pure.service.web.rest.errors.ExceptionTranslator;
import com.pure.service.service.dto.AdCriteria;
import com.pure.service.service.AdQueryService;

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
 * Test class for the AdResource REST controller.
 *
 * @see AdResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SimpleServiceApp.class)
public class AdResourceIntTest {

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    private static final Integer DEFAULT_RPX_WIDTH = 1;
    private static final Integer UPDATED_RPX_WIDTH = 2;

    private static final Integer DEFAULT_RPX_HEIGHT = 1;
    private static final Integer UPDATED_RPX_HEIGHT = 2;

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;

    private static final Integer DEFAULT_SEQUENCE = 1;
    private static final Integer UPDATED_SEQUENCE = 2;

    private static final String DEFAULT_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTS = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    @Autowired
    private AdRepository adRepository;

    @Autowired
    private AdService adService;

    @Autowired
    private AdQueryService adQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAdMockMvc;

    private Ad ad;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AdResource adResource = new AdResource(adService, adQueryService);
        this.restAdMockMvc = MockMvcBuilders.standaloneSetup(adResource)
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
    public static Ad createEntity(EntityManager em) {
        Ad ad = new Ad()
            .type(DEFAULT_TYPE)
            .content(DEFAULT_CONTENT)
            .rpxWidth(DEFAULT_RPX_WIDTH)
            .rpxHeight(DEFAULT_RPX_HEIGHT)
            .status(DEFAULT_STATUS)
            .sequence(DEFAULT_SEQUENCE)
            .comments(DEFAULT_COMMENTS)
            .createdBy(DEFAULT_CREATED_BY);
        return ad;
    }

    @Before
    public void initTest() {
        ad = createEntity(em);
    }

    @Test
    @Transactional
    public void createAd() throws Exception {
        int databaseSizeBeforeCreate = adRepository.findAll().size();

        // Create the Ad
        restAdMockMvc.perform(post("/api/ads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ad)))
            .andExpect(status().isCreated());

        // Validate the Ad in the database
        List<Ad> adList = adRepository.findAll();
        assertThat(adList).hasSize(databaseSizeBeforeCreate + 1);
        Ad testAd = adList.get(adList.size() - 1);
        assertThat(testAd.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testAd.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testAd.getRpxWidth()).isEqualTo(DEFAULT_RPX_WIDTH);
        assertThat(testAd.getRpxHeight()).isEqualTo(DEFAULT_RPX_HEIGHT);
        assertThat(testAd.isStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testAd.getSequence()).isEqualTo(DEFAULT_SEQUENCE);
        assertThat(testAd.getComments()).isEqualTo(DEFAULT_COMMENTS);
        assertThat(testAd.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
    }

    @Test
    @Transactional
    public void createAdWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = adRepository.findAll().size();

        // Create the Ad with an existing ID
        ad.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdMockMvc.perform(post("/api/ads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ad)))
            .andExpect(status().isBadRequest());

        // Validate the Ad in the database
        List<Ad> adList = adRepository.findAll();
        assertThat(adList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAds() throws Exception {
        // Initialize the database
        adRepository.saveAndFlush(ad);

        // Get all the adList
        restAdMockMvc.perform(get("/api/ads?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ad.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT.toString())))
            .andExpect(jsonPath("$.[*].rpxWidth").value(hasItem(DEFAULT_RPX_WIDTH)))
            .andExpect(jsonPath("$.[*].rpxHeight").value(hasItem(DEFAULT_RPX_HEIGHT)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())))
            .andExpect(jsonPath("$.[*].sequence").value(hasItem(DEFAULT_SEQUENCE)))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())));
    }

    @Test
    @Transactional
    public void getAd() throws Exception {
        // Initialize the database
        adRepository.saveAndFlush(ad);

        // Get the ad
        restAdMockMvc.perform(get("/api/ads/{id}", ad.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ad.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT.toString()))
            .andExpect(jsonPath("$.rpxWidth").value(DEFAULT_RPX_WIDTH))
            .andExpect(jsonPath("$.rpxHeight").value(DEFAULT_RPX_HEIGHT))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()))
            .andExpect(jsonPath("$.sequence").value(DEFAULT_SEQUENCE))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()));
    }

    @Test
    @Transactional
    public void getAllAdsByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        adRepository.saveAndFlush(ad);

        // Get all the adList where type equals to DEFAULT_TYPE
        defaultAdShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the adList where type equals to UPDATED_TYPE
        defaultAdShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllAdsByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        adRepository.saveAndFlush(ad);

        // Get all the adList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultAdShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the adList where type equals to UPDATED_TYPE
        defaultAdShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllAdsByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        adRepository.saveAndFlush(ad);

        // Get all the adList where type is not null
        defaultAdShouldBeFound("type.specified=true");

        // Get all the adList where type is null
        defaultAdShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    public void getAllAdsByContentIsEqualToSomething() throws Exception {
        // Initialize the database
        adRepository.saveAndFlush(ad);

        // Get all the adList where content equals to DEFAULT_CONTENT
        defaultAdShouldBeFound("content.equals=" + DEFAULT_CONTENT);

        // Get all the adList where content equals to UPDATED_CONTENT
        defaultAdShouldNotBeFound("content.equals=" + UPDATED_CONTENT);
    }

    @Test
    @Transactional
    public void getAllAdsByContentIsInShouldWork() throws Exception {
        // Initialize the database
        adRepository.saveAndFlush(ad);

        // Get all the adList where content in DEFAULT_CONTENT or UPDATED_CONTENT
        defaultAdShouldBeFound("content.in=" + DEFAULT_CONTENT + "," + UPDATED_CONTENT);

        // Get all the adList where content equals to UPDATED_CONTENT
        defaultAdShouldNotBeFound("content.in=" + UPDATED_CONTENT);
    }

    @Test
    @Transactional
    public void getAllAdsByContentIsNullOrNotNull() throws Exception {
        // Initialize the database
        adRepository.saveAndFlush(ad);

        // Get all the adList where content is not null
        defaultAdShouldBeFound("content.specified=true");

        // Get all the adList where content is null
        defaultAdShouldNotBeFound("content.specified=false");
    }

    @Test
    @Transactional
    public void getAllAdsByRpxWidthIsEqualToSomething() throws Exception {
        // Initialize the database
        adRepository.saveAndFlush(ad);

        // Get all the adList where rpxWidth equals to DEFAULT_RPX_WIDTH
        defaultAdShouldBeFound("rpxWidth.equals=" + DEFAULT_RPX_WIDTH);

        // Get all the adList where rpxWidth equals to UPDATED_RPX_WIDTH
        defaultAdShouldNotBeFound("rpxWidth.equals=" + UPDATED_RPX_WIDTH);
    }

    @Test
    @Transactional
    public void getAllAdsByRpxWidthIsInShouldWork() throws Exception {
        // Initialize the database
        adRepository.saveAndFlush(ad);

        // Get all the adList where rpxWidth in DEFAULT_RPX_WIDTH or UPDATED_RPX_WIDTH
        defaultAdShouldBeFound("rpxWidth.in=" + DEFAULT_RPX_WIDTH + "," + UPDATED_RPX_WIDTH);

        // Get all the adList where rpxWidth equals to UPDATED_RPX_WIDTH
        defaultAdShouldNotBeFound("rpxWidth.in=" + UPDATED_RPX_WIDTH);
    }

    @Test
    @Transactional
    public void getAllAdsByRpxWidthIsNullOrNotNull() throws Exception {
        // Initialize the database
        adRepository.saveAndFlush(ad);

        // Get all the adList where rpxWidth is not null
        defaultAdShouldBeFound("rpxWidth.specified=true");

        // Get all the adList where rpxWidth is null
        defaultAdShouldNotBeFound("rpxWidth.specified=false");
    }

    @Test
    @Transactional
    public void getAllAdsByRpxWidthIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        adRepository.saveAndFlush(ad);

        // Get all the adList where rpxWidth greater than or equals to DEFAULT_RPX_WIDTH
        defaultAdShouldBeFound("rpxWidth.greaterOrEqualThan=" + DEFAULT_RPX_WIDTH);

        // Get all the adList where rpxWidth greater than or equals to UPDATED_RPX_WIDTH
        defaultAdShouldNotBeFound("rpxWidth.greaterOrEqualThan=" + UPDATED_RPX_WIDTH);
    }

    @Test
    @Transactional
    public void getAllAdsByRpxWidthIsLessThanSomething() throws Exception {
        // Initialize the database
        adRepository.saveAndFlush(ad);

        // Get all the adList where rpxWidth less than or equals to DEFAULT_RPX_WIDTH
        defaultAdShouldNotBeFound("rpxWidth.lessThan=" + DEFAULT_RPX_WIDTH);

        // Get all the adList where rpxWidth less than or equals to UPDATED_RPX_WIDTH
        defaultAdShouldBeFound("rpxWidth.lessThan=" + UPDATED_RPX_WIDTH);
    }


    @Test
    @Transactional
    public void getAllAdsByRpxHeightIsEqualToSomething() throws Exception {
        // Initialize the database
        adRepository.saveAndFlush(ad);

        // Get all the adList where rpxHeight equals to DEFAULT_RPX_HEIGHT
        defaultAdShouldBeFound("rpxHeight.equals=" + DEFAULT_RPX_HEIGHT);

        // Get all the adList where rpxHeight equals to UPDATED_RPX_HEIGHT
        defaultAdShouldNotBeFound("rpxHeight.equals=" + UPDATED_RPX_HEIGHT);
    }

    @Test
    @Transactional
    public void getAllAdsByRpxHeightIsInShouldWork() throws Exception {
        // Initialize the database
        adRepository.saveAndFlush(ad);

        // Get all the adList where rpxHeight in DEFAULT_RPX_HEIGHT or UPDATED_RPX_HEIGHT
        defaultAdShouldBeFound("rpxHeight.in=" + DEFAULT_RPX_HEIGHT + "," + UPDATED_RPX_HEIGHT);

        // Get all the adList where rpxHeight equals to UPDATED_RPX_HEIGHT
        defaultAdShouldNotBeFound("rpxHeight.in=" + UPDATED_RPX_HEIGHT);
    }

    @Test
    @Transactional
    public void getAllAdsByRpxHeightIsNullOrNotNull() throws Exception {
        // Initialize the database
        adRepository.saveAndFlush(ad);

        // Get all the adList where rpxHeight is not null
        defaultAdShouldBeFound("rpxHeight.specified=true");

        // Get all the adList where rpxHeight is null
        defaultAdShouldNotBeFound("rpxHeight.specified=false");
    }

    @Test
    @Transactional
    public void getAllAdsByRpxHeightIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        adRepository.saveAndFlush(ad);

        // Get all the adList where rpxHeight greater than or equals to DEFAULT_RPX_HEIGHT
        defaultAdShouldBeFound("rpxHeight.greaterOrEqualThan=" + DEFAULT_RPX_HEIGHT);

        // Get all the adList where rpxHeight greater than or equals to UPDATED_RPX_HEIGHT
        defaultAdShouldNotBeFound("rpxHeight.greaterOrEqualThan=" + UPDATED_RPX_HEIGHT);
    }

    @Test
    @Transactional
    public void getAllAdsByRpxHeightIsLessThanSomething() throws Exception {
        // Initialize the database
        adRepository.saveAndFlush(ad);

        // Get all the adList where rpxHeight less than or equals to DEFAULT_RPX_HEIGHT
        defaultAdShouldNotBeFound("rpxHeight.lessThan=" + DEFAULT_RPX_HEIGHT);

        // Get all the adList where rpxHeight less than or equals to UPDATED_RPX_HEIGHT
        defaultAdShouldBeFound("rpxHeight.lessThan=" + UPDATED_RPX_HEIGHT);
    }


    @Test
    @Transactional
    public void getAllAdsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        adRepository.saveAndFlush(ad);

        // Get all the adList where status equals to DEFAULT_STATUS
        defaultAdShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the adList where status equals to UPDATED_STATUS
        defaultAdShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllAdsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        adRepository.saveAndFlush(ad);

        // Get all the adList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultAdShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the adList where status equals to UPDATED_STATUS
        defaultAdShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllAdsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        adRepository.saveAndFlush(ad);

        // Get all the adList where status is not null
        defaultAdShouldBeFound("status.specified=true");

        // Get all the adList where status is null
        defaultAdShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    public void getAllAdsBySequenceIsEqualToSomething() throws Exception {
        // Initialize the database
        adRepository.saveAndFlush(ad);

        // Get all the adList where sequence equals to DEFAULT_SEQUENCE
        defaultAdShouldBeFound("sequence.equals=" + DEFAULT_SEQUENCE);

        // Get all the adList where sequence equals to UPDATED_SEQUENCE
        defaultAdShouldNotBeFound("sequence.equals=" + UPDATED_SEQUENCE);
    }

    @Test
    @Transactional
    public void getAllAdsBySequenceIsInShouldWork() throws Exception {
        // Initialize the database
        adRepository.saveAndFlush(ad);

        // Get all the adList where sequence in DEFAULT_SEQUENCE or UPDATED_SEQUENCE
        defaultAdShouldBeFound("sequence.in=" + DEFAULT_SEQUENCE + "," + UPDATED_SEQUENCE);

        // Get all the adList where sequence equals to UPDATED_SEQUENCE
        defaultAdShouldNotBeFound("sequence.in=" + UPDATED_SEQUENCE);
    }

    @Test
    @Transactional
    public void getAllAdsBySequenceIsNullOrNotNull() throws Exception {
        // Initialize the database
        adRepository.saveAndFlush(ad);

        // Get all the adList where sequence is not null
        defaultAdShouldBeFound("sequence.specified=true");

        // Get all the adList where sequence is null
        defaultAdShouldNotBeFound("sequence.specified=false");
    }

    @Test
    @Transactional
    public void getAllAdsBySequenceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        adRepository.saveAndFlush(ad);

        // Get all the adList where sequence greater than or equals to DEFAULT_SEQUENCE
        defaultAdShouldBeFound("sequence.greaterOrEqualThan=" + DEFAULT_SEQUENCE);

        // Get all the adList where sequence greater than or equals to UPDATED_SEQUENCE
        defaultAdShouldNotBeFound("sequence.greaterOrEqualThan=" + UPDATED_SEQUENCE);
    }

    @Test
    @Transactional
    public void getAllAdsBySequenceIsLessThanSomething() throws Exception {
        // Initialize the database
        adRepository.saveAndFlush(ad);

        // Get all the adList where sequence less than or equals to DEFAULT_SEQUENCE
        defaultAdShouldNotBeFound("sequence.lessThan=" + DEFAULT_SEQUENCE);

        // Get all the adList where sequence less than or equals to UPDATED_SEQUENCE
        defaultAdShouldBeFound("sequence.lessThan=" + UPDATED_SEQUENCE);
    }


    @Test
    @Transactional
    public void getAllAdsByCommentsIsEqualToSomething() throws Exception {
        // Initialize the database
        adRepository.saveAndFlush(ad);

        // Get all the adList where comments equals to DEFAULT_COMMENTS
        defaultAdShouldBeFound("comments.equals=" + DEFAULT_COMMENTS);

        // Get all the adList where comments equals to UPDATED_COMMENTS
        defaultAdShouldNotBeFound("comments.equals=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllAdsByCommentsIsInShouldWork() throws Exception {
        // Initialize the database
        adRepository.saveAndFlush(ad);

        // Get all the adList where comments in DEFAULT_COMMENTS or UPDATED_COMMENTS
        defaultAdShouldBeFound("comments.in=" + DEFAULT_COMMENTS + "," + UPDATED_COMMENTS);

        // Get all the adList where comments equals to UPDATED_COMMENTS
        defaultAdShouldNotBeFound("comments.in=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllAdsByCommentsIsNullOrNotNull() throws Exception {
        // Initialize the database
        adRepository.saveAndFlush(ad);

        // Get all the adList where comments is not null
        defaultAdShouldBeFound("comments.specified=true");

        // Get all the adList where comments is null
        defaultAdShouldNotBeFound("comments.specified=false");
    }

    @Test
    @Transactional
    public void getAllAdsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        adRepository.saveAndFlush(ad);

        // Get all the adList where createdBy equals to DEFAULT_CREATED_BY
        defaultAdShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the adList where createdBy equals to UPDATED_CREATED_BY
        defaultAdShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllAdsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        adRepository.saveAndFlush(ad);

        // Get all the adList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultAdShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the adList where createdBy equals to UPDATED_CREATED_BY
        defaultAdShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllAdsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        adRepository.saveAndFlush(ad);

        // Get all the adList where createdBy is not null
        defaultAdShouldBeFound("createdBy.specified=true");

        // Get all the adList where createdBy is null
        defaultAdShouldNotBeFound("createdBy.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultAdShouldBeFound(String filter) throws Exception {
        restAdMockMvc.perform(get("/api/ads?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ad.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT.toString())))
            .andExpect(jsonPath("$.[*].rpxWidth").value(hasItem(DEFAULT_RPX_WIDTH)))
            .andExpect(jsonPath("$.[*].rpxHeight").value(hasItem(DEFAULT_RPX_HEIGHT)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())))
            .andExpect(jsonPath("$.[*].sequence").value(hasItem(DEFAULT_SEQUENCE)))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultAdShouldNotBeFound(String filter) throws Exception {
        restAdMockMvc.perform(get("/api/ads?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingAd() throws Exception {
        // Get the ad
        restAdMockMvc.perform(get("/api/ads/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAd() throws Exception {
        // Initialize the database
        adService.save(ad);

        int databaseSizeBeforeUpdate = adRepository.findAll().size();

        // Update the ad
        Ad updatedAd = adRepository.findOne(ad.getId());
        updatedAd
            .type(UPDATED_TYPE)
            .content(UPDATED_CONTENT)
            .rpxWidth(UPDATED_RPX_WIDTH)
            .rpxHeight(UPDATED_RPX_HEIGHT)
            .status(UPDATED_STATUS)
            .sequence(UPDATED_SEQUENCE)
            .comments(UPDATED_COMMENTS)
            .createdBy(UPDATED_CREATED_BY);

        restAdMockMvc.perform(put("/api/ads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAd)))
            .andExpect(status().isOk());

        // Validate the Ad in the database
        List<Ad> adList = adRepository.findAll();
        assertThat(adList).hasSize(databaseSizeBeforeUpdate);
        Ad testAd = adList.get(adList.size() - 1);
        assertThat(testAd.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testAd.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testAd.getRpxWidth()).isEqualTo(UPDATED_RPX_WIDTH);
        assertThat(testAd.getRpxHeight()).isEqualTo(UPDATED_RPX_HEIGHT);
        assertThat(testAd.isStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testAd.getSequence()).isEqualTo(UPDATED_SEQUENCE);
        assertThat(testAd.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testAd.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void updateNonExistingAd() throws Exception {
        int databaseSizeBeforeUpdate = adRepository.findAll().size();

        // Create the Ad

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAdMockMvc.perform(put("/api/ads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ad)))
            .andExpect(status().isCreated());

        // Validate the Ad in the database
        List<Ad> adList = adRepository.findAll();
        assertThat(adList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAd() throws Exception {
        // Initialize the database
        adService.save(ad);

        int databaseSizeBeforeDelete = adRepository.findAll().size();

        // Get the ad
        restAdMockMvc.perform(delete("/api/ads/{id}", ad.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Ad> adList = adRepository.findAll();
        assertThat(adList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Ad.class);
        Ad ad1 = new Ad();
        ad1.setId(1L);
        Ad ad2 = new Ad();
        ad2.setId(ad1.getId());
        assertThat(ad1).isEqualTo(ad2);
        ad2.setId(2L);
        assertThat(ad1).isNotEqualTo(ad2);
        ad1.setId(null);
        assertThat(ad1).isNotEqualTo(ad2);
    }
}
