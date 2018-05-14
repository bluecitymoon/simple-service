package com.pure.service.web.rest;

import com.pure.service.SimpleServiceApp;

import com.pure.service.domain.Asset;
import com.pure.service.repository.AssetRepository;
import com.pure.service.service.AssetService;
import com.pure.service.web.rest.errors.ExceptionTranslator;
import com.pure.service.service.dto.AssetCriteria;
import com.pure.service.service.AssetQueryService;

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
 * Test class for the AssetResource REST controller.
 *
 * @see AssetResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SimpleServiceApp.class)
public class AssetResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_FULL_PATH = "AAAAAAAAAA";
    private static final String UPDATED_FULL_PATH = "BBBBBBBBBB";

    private static final String DEFAULT_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTS = "BBBBBBBBBB";

    private static final String DEFAULT_RESOURCE_ID = "AAAAAAAAAA";
    private static final String UPDATED_RESOURCE_ID = "BBBBBBBBBB";

    @Autowired
    private AssetRepository assetRepository;

    @Autowired
    private AssetService assetService;

    @Autowired
    private AssetQueryService assetQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAssetMockMvc;

    private Asset asset;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AssetResource assetResource = new AssetResource(assetService, assetQueryService);
        this.restAssetMockMvc = MockMvcBuilders.standaloneSetup(assetResource)
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
    public static Asset createEntity(EntityManager em) {
        Asset asset = new Asset()
            .name(DEFAULT_NAME)
            .type(DEFAULT_TYPE)
            .fullPath(DEFAULT_FULL_PATH)
            .comments(DEFAULT_COMMENTS)
            .resourceId(DEFAULT_RESOURCE_ID);
        return asset;
    }

    @Before
    public void initTest() {
        asset = createEntity(em);
    }

    @Test
    @Transactional
    public void createAsset() throws Exception {
        int databaseSizeBeforeCreate = assetRepository.findAll().size();

        // Create the Asset
        restAssetMockMvc.perform(post("/api/assets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(asset)))
            .andExpect(status().isCreated());

        // Validate the Asset in the database
        List<Asset> assetList = assetRepository.findAll();
        assertThat(assetList).hasSize(databaseSizeBeforeCreate + 1);
        Asset testAsset = assetList.get(assetList.size() - 1);
        assertThat(testAsset.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAsset.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testAsset.getFullPath()).isEqualTo(DEFAULT_FULL_PATH);
        assertThat(testAsset.getComments()).isEqualTo(DEFAULT_COMMENTS);
        assertThat(testAsset.getResourceId()).isEqualTo(DEFAULT_RESOURCE_ID);
    }

    @Test
    @Transactional
    public void createAssetWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = assetRepository.findAll().size();

        // Create the Asset with an existing ID
        asset.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAssetMockMvc.perform(post("/api/assets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(asset)))
            .andExpect(status().isBadRequest());

        // Validate the Asset in the database
        List<Asset> assetList = assetRepository.findAll();
        assertThat(assetList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAssets() throws Exception {
        // Initialize the database
        assetRepository.saveAndFlush(asset);

        // Get all the assetList
        restAssetMockMvc.perform(get("/api/assets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(asset.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].fullPath").value(hasItem(DEFAULT_FULL_PATH.toString())))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS.toString())))
            .andExpect(jsonPath("$.[*].resourceId").value(hasItem(DEFAULT_RESOURCE_ID.toString())));
    }

    @Test
    @Transactional
    public void getAsset() throws Exception {
        // Initialize the database
        assetRepository.saveAndFlush(asset);

        // Get the asset
        restAssetMockMvc.perform(get("/api/assets/{id}", asset.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(asset.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.fullPath").value(DEFAULT_FULL_PATH.toString()))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS.toString()))
            .andExpect(jsonPath("$.resourceId").value(DEFAULT_RESOURCE_ID.toString()));
    }

    @Test
    @Transactional
    public void getAllAssetsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        assetRepository.saveAndFlush(asset);

        // Get all the assetList where name equals to DEFAULT_NAME
        defaultAssetShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the assetList where name equals to UPDATED_NAME
        defaultAssetShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllAssetsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        assetRepository.saveAndFlush(asset);

        // Get all the assetList where name in DEFAULT_NAME or UPDATED_NAME
        defaultAssetShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the assetList where name equals to UPDATED_NAME
        defaultAssetShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllAssetsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        assetRepository.saveAndFlush(asset);

        // Get all the assetList where name is not null
        defaultAssetShouldBeFound("name.specified=true");

        // Get all the assetList where name is null
        defaultAssetShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllAssetsByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        assetRepository.saveAndFlush(asset);

        // Get all the assetList where type equals to DEFAULT_TYPE
        defaultAssetShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the assetList where type equals to UPDATED_TYPE
        defaultAssetShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllAssetsByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        assetRepository.saveAndFlush(asset);

        // Get all the assetList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultAssetShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the assetList where type equals to UPDATED_TYPE
        defaultAssetShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllAssetsByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        assetRepository.saveAndFlush(asset);

        // Get all the assetList where type is not null
        defaultAssetShouldBeFound("type.specified=true");

        // Get all the assetList where type is null
        defaultAssetShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    public void getAllAssetsByFullPathIsEqualToSomething() throws Exception {
        // Initialize the database
        assetRepository.saveAndFlush(asset);

        // Get all the assetList where fullPath equals to DEFAULT_FULL_PATH
        defaultAssetShouldBeFound("fullPath.equals=" + DEFAULT_FULL_PATH);

        // Get all the assetList where fullPath equals to UPDATED_FULL_PATH
        defaultAssetShouldNotBeFound("fullPath.equals=" + UPDATED_FULL_PATH);
    }

    @Test
    @Transactional
    public void getAllAssetsByFullPathIsInShouldWork() throws Exception {
        // Initialize the database
        assetRepository.saveAndFlush(asset);

        // Get all the assetList where fullPath in DEFAULT_FULL_PATH or UPDATED_FULL_PATH
        defaultAssetShouldBeFound("fullPath.in=" + DEFAULT_FULL_PATH + "," + UPDATED_FULL_PATH);

        // Get all the assetList where fullPath equals to UPDATED_FULL_PATH
        defaultAssetShouldNotBeFound("fullPath.in=" + UPDATED_FULL_PATH);
    }

    @Test
    @Transactional
    public void getAllAssetsByFullPathIsNullOrNotNull() throws Exception {
        // Initialize the database
        assetRepository.saveAndFlush(asset);

        // Get all the assetList where fullPath is not null
        defaultAssetShouldBeFound("fullPath.specified=true");

        // Get all the assetList where fullPath is null
        defaultAssetShouldNotBeFound("fullPath.specified=false");
    }

    @Test
    @Transactional
    public void getAllAssetsByCommentsIsEqualToSomething() throws Exception {
        // Initialize the database
        assetRepository.saveAndFlush(asset);

        // Get all the assetList where comments equals to DEFAULT_COMMENTS
        defaultAssetShouldBeFound("comments.equals=" + DEFAULT_COMMENTS);

        // Get all the assetList where comments equals to UPDATED_COMMENTS
        defaultAssetShouldNotBeFound("comments.equals=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllAssetsByCommentsIsInShouldWork() throws Exception {
        // Initialize the database
        assetRepository.saveAndFlush(asset);

        // Get all the assetList where comments in DEFAULT_COMMENTS or UPDATED_COMMENTS
        defaultAssetShouldBeFound("comments.in=" + DEFAULT_COMMENTS + "," + UPDATED_COMMENTS);

        // Get all the assetList where comments equals to UPDATED_COMMENTS
        defaultAssetShouldNotBeFound("comments.in=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllAssetsByCommentsIsNullOrNotNull() throws Exception {
        // Initialize the database
        assetRepository.saveAndFlush(asset);

        // Get all the assetList where comments is not null
        defaultAssetShouldBeFound("comments.specified=true");

        // Get all the assetList where comments is null
        defaultAssetShouldNotBeFound("comments.specified=false");
    }

    @Test
    @Transactional
    public void getAllAssetsByResourceIdIsEqualToSomething() throws Exception {
        // Initialize the database
        assetRepository.saveAndFlush(asset);

        // Get all the assetList where resourceId equals to DEFAULT_RESOURCE_ID
        defaultAssetShouldBeFound("resourceId.equals=" + DEFAULT_RESOURCE_ID);

        // Get all the assetList where resourceId equals to UPDATED_RESOURCE_ID
        defaultAssetShouldNotBeFound("resourceId.equals=" + UPDATED_RESOURCE_ID);
    }

    @Test
    @Transactional
    public void getAllAssetsByResourceIdIsInShouldWork() throws Exception {
        // Initialize the database
        assetRepository.saveAndFlush(asset);

        // Get all the assetList where resourceId in DEFAULT_RESOURCE_ID or UPDATED_RESOURCE_ID
        defaultAssetShouldBeFound("resourceId.in=" + DEFAULT_RESOURCE_ID + "," + UPDATED_RESOURCE_ID);

        // Get all the assetList where resourceId equals to UPDATED_RESOURCE_ID
        defaultAssetShouldNotBeFound("resourceId.in=" + UPDATED_RESOURCE_ID);
    }

    @Test
    @Transactional
    public void getAllAssetsByResourceIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        assetRepository.saveAndFlush(asset);

        // Get all the assetList where resourceId is not null
        defaultAssetShouldBeFound("resourceId.specified=true");

        // Get all the assetList where resourceId is null
        defaultAssetShouldNotBeFound("resourceId.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultAssetShouldBeFound(String filter) throws Exception {
        restAssetMockMvc.perform(get("/api/assets?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(asset.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].fullPath").value(hasItem(DEFAULT_FULL_PATH.toString())))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS.toString())))
            .andExpect(jsonPath("$.[*].resourceId").value(hasItem(DEFAULT_RESOURCE_ID.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultAssetShouldNotBeFound(String filter) throws Exception {
        restAssetMockMvc.perform(get("/api/assets?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingAsset() throws Exception {
        // Get the asset
        restAssetMockMvc.perform(get("/api/assets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAsset() throws Exception {
        // Initialize the database
        assetService.save(asset);

        int databaseSizeBeforeUpdate = assetRepository.findAll().size();

        // Update the asset
        Asset updatedAsset = assetRepository.findOne(asset.getId());
        updatedAsset
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .fullPath(UPDATED_FULL_PATH)
            .comments(UPDATED_COMMENTS)
            .resourceId(UPDATED_RESOURCE_ID);

        restAssetMockMvc.perform(put("/api/assets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAsset)))
            .andExpect(status().isOk());

        // Validate the Asset in the database
        List<Asset> assetList = assetRepository.findAll();
        assertThat(assetList).hasSize(databaseSizeBeforeUpdate);
        Asset testAsset = assetList.get(assetList.size() - 1);
        assertThat(testAsset.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAsset.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testAsset.getFullPath()).isEqualTo(UPDATED_FULL_PATH);
        assertThat(testAsset.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testAsset.getResourceId()).isEqualTo(UPDATED_RESOURCE_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingAsset() throws Exception {
        int databaseSizeBeforeUpdate = assetRepository.findAll().size();

        // Create the Asset

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAssetMockMvc.perform(put("/api/assets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(asset)))
            .andExpect(status().isCreated());

        // Validate the Asset in the database
        List<Asset> assetList = assetRepository.findAll();
        assertThat(assetList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAsset() throws Exception {
        // Initialize the database
        assetService.save(asset);

        int databaseSizeBeforeDelete = assetRepository.findAll().size();

        // Get the asset
        restAssetMockMvc.perform(delete("/api/assets/{id}", asset.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Asset> assetList = assetRepository.findAll();
        assertThat(assetList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Asset.class);
        Asset asset1 = new Asset();
        asset1.setId(1L);
        Asset asset2 = new Asset();
        asset2.setId(asset1.getId());
        assertThat(asset1).isEqualTo(asset2);
        asset2.setId(2L);
        assertThat(asset1).isNotEqualTo(asset2);
        asset1.setId(null);
        assertThat(asset1).isNotEqualTo(asset2);
    }
}
