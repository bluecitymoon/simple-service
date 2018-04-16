package com.pure.service.web.rest;

import com.pure.service.SimpleServiceApp;

import com.pure.service.domain.MarketChannelCategory;
import com.pure.service.repository.MarketChannelCategoryRepository;
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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the MarketChannelCategoryResource REST controller.
 *
 * @see MarketChannelCategoryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SimpleServiceApp.class)
public class MarketChannelCategoryResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private MarketChannelCategoryRepository marketChannelCategoryRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMarketChannelCategoryMockMvc;

    private MarketChannelCategory marketChannelCategory;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MarketChannelCategoryResource marketChannelCategoryResource = new MarketChannelCategoryResource(marketChannelCategoryRepository);
        this.restMarketChannelCategoryMockMvc = MockMvcBuilders.standaloneSetup(marketChannelCategoryResource)
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
    public static MarketChannelCategory createEntity(EntityManager em) {
        MarketChannelCategory marketChannelCategory = new MarketChannelCategory()
            .name(DEFAULT_NAME)
            .code(DEFAULT_CODE)
            .description(DEFAULT_DESCRIPTION);
        return marketChannelCategory;
    }

    @Before
    public void initTest() {
        marketChannelCategory = createEntity(em);
    }

    @Test
    @Transactional
    public void createMarketChannelCategory() throws Exception {
        int databaseSizeBeforeCreate = marketChannelCategoryRepository.findAll().size();

        // Create the MarketChannelCategory
        restMarketChannelCategoryMockMvc.perform(post("/api/market-channel-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(marketChannelCategory)))
            .andExpect(status().isCreated());

        // Validate the MarketChannelCategory in the database
        List<MarketChannelCategory> marketChannelCategoryList = marketChannelCategoryRepository.findAll();
        assertThat(marketChannelCategoryList).hasSize(databaseSizeBeforeCreate + 1);
        MarketChannelCategory testMarketChannelCategory = marketChannelCategoryList.get(marketChannelCategoryList.size() - 1);
        assertThat(testMarketChannelCategory.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMarketChannelCategory.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testMarketChannelCategory.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createMarketChannelCategoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = marketChannelCategoryRepository.findAll().size();

        // Create the MarketChannelCategory with an existing ID
        marketChannelCategory.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMarketChannelCategoryMockMvc.perform(post("/api/market-channel-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(marketChannelCategory)))
            .andExpect(status().isBadRequest());

        // Validate the MarketChannelCategory in the database
        List<MarketChannelCategory> marketChannelCategoryList = marketChannelCategoryRepository.findAll();
        assertThat(marketChannelCategoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMarketChannelCategories() throws Exception {
        // Initialize the database
        marketChannelCategoryRepository.saveAndFlush(marketChannelCategory);

        // Get all the marketChannelCategoryList
        restMarketChannelCategoryMockMvc.perform(get("/api/market-channel-categories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(marketChannelCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getMarketChannelCategory() throws Exception {
        // Initialize the database
        marketChannelCategoryRepository.saveAndFlush(marketChannelCategory);

        // Get the marketChannelCategory
        restMarketChannelCategoryMockMvc.perform(get("/api/market-channel-categories/{id}", marketChannelCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(marketChannelCategory.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMarketChannelCategory() throws Exception {
        // Get the marketChannelCategory
        restMarketChannelCategoryMockMvc.perform(get("/api/market-channel-categories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMarketChannelCategory() throws Exception {
        // Initialize the database
        marketChannelCategoryRepository.saveAndFlush(marketChannelCategory);
        int databaseSizeBeforeUpdate = marketChannelCategoryRepository.findAll().size();

        // Update the marketChannelCategory
        MarketChannelCategory updatedMarketChannelCategory = marketChannelCategoryRepository.findOne(marketChannelCategory.getId());
        updatedMarketChannelCategory
            .name(UPDATED_NAME)
            .code(UPDATED_CODE)
            .description(UPDATED_DESCRIPTION);

        restMarketChannelCategoryMockMvc.perform(put("/api/market-channel-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMarketChannelCategory)))
            .andExpect(status().isOk());

        // Validate the MarketChannelCategory in the database
        List<MarketChannelCategory> marketChannelCategoryList = marketChannelCategoryRepository.findAll();
        assertThat(marketChannelCategoryList).hasSize(databaseSizeBeforeUpdate);
        MarketChannelCategory testMarketChannelCategory = marketChannelCategoryList.get(marketChannelCategoryList.size() - 1);
        assertThat(testMarketChannelCategory.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMarketChannelCategory.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testMarketChannelCategory.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingMarketChannelCategory() throws Exception {
        int databaseSizeBeforeUpdate = marketChannelCategoryRepository.findAll().size();

        // Create the MarketChannelCategory

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMarketChannelCategoryMockMvc.perform(put("/api/market-channel-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(marketChannelCategory)))
            .andExpect(status().isCreated());

        // Validate the MarketChannelCategory in the database
        List<MarketChannelCategory> marketChannelCategoryList = marketChannelCategoryRepository.findAll();
        assertThat(marketChannelCategoryList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMarketChannelCategory() throws Exception {
        // Initialize the database
        marketChannelCategoryRepository.saveAndFlush(marketChannelCategory);
        int databaseSizeBeforeDelete = marketChannelCategoryRepository.findAll().size();

        // Get the marketChannelCategory
        restMarketChannelCategoryMockMvc.perform(delete("/api/market-channel-categories/{id}", marketChannelCategory.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MarketChannelCategory> marketChannelCategoryList = marketChannelCategoryRepository.findAll();
        assertThat(marketChannelCategoryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MarketChannelCategory.class);
        MarketChannelCategory marketChannelCategory1 = new MarketChannelCategory();
        marketChannelCategory1.setId(1L);
        MarketChannelCategory marketChannelCategory2 = new MarketChannelCategory();
        marketChannelCategory2.setId(marketChannelCategory1.getId());
        assertThat(marketChannelCategory1).isEqualTo(marketChannelCategory2);
        marketChannelCategory2.setId(2L);
        assertThat(marketChannelCategory1).isNotEqualTo(marketChannelCategory2);
        marketChannelCategory1.setId(null);
        assertThat(marketChannelCategory1).isNotEqualTo(marketChannelCategory2);
    }
}
