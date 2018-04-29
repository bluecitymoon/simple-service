//package com.pure.service.web.rest;
//
//import com.pure.service.SimpleServiceApp;
//
//import com.pure.service.domain.NewOrderWechatUserInfo;
//import com.pure.service.repository.NewOrderWechatUserInfoRepository;
//import com.pure.service.service.NewOrderWechatUserInfoService;
//import com.pure.service.web.rest.errors.ExceptionTranslator;
//import com.pure.service.service.dto.NewOrderWechatUserInfoCriteria;
//import com.pure.service.service.NewOrderWechatUserInfoQueryService;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.MockitoAnnotations;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
//import org.springframework.http.MediaType;
//import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.persistence.EntityManager;
//import java.time.Instant;
//import java.time.temporal.ChronoUnit;
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.hamcrest.Matchers.hasItem;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
///**
// * Test class for the NewOrderWechatUserInfoResource REST controller.
// *
// * @see NewOrderWechatUserInfoResource
// */
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = SimpleServiceApp.class)
//public class NewOrderWechatUserInfoResourceIntTest {
//
//    private static final String DEFAULT_CODE = "AAAAAAAAAA";
//    private static final String UPDATED_CODE = "BBBBBBBBBB";
//
//    private static final String DEFAULT_ENCRYPTED_DATA = "AAAAAAAAAA";
//    private static final String UPDATED_ENCRYPTED_DATA = "BBBBBBBBBB";
//
//    private static final String DEFAULT_IV = "AAAAAAAAAA";
//    private static final String UPDATED_IV = "BBBBBBBBBB";
//
//    private static final String DEFAULT_NICK_NAME = "AAAAAAAAAA";
//    private static final String UPDATED_NICK_NAME = "BBBBBBBBBB";
//
//    private static final Integer DEFAULT_GENDER = 1;
//    private static final Integer UPDATED_GENDER = 2;
//
//    private static final String DEFAULT_LANGUAGE = "AAAAAAAAAA";
//    private static final String UPDATED_LANGUAGE = "BBBBBBBBBB";
//
//    private static final String DEFAULT_CITY = "AAAAAAAAAA";
//    private static final String UPDATED_CITY = "BBBBBBBBBB";
//
//    private static final String DEFAULT_PROVINCE = "AAAAAAAAAA";
//    private static final String UPDATED_PROVINCE = "BBBBBBBBBB";
//
//    private static final String DEFAULT_COUNTRY = "AAAAAAAAAA";
//    private static final String UPDATED_COUNTRY = "BBBBBBBBBB";
//
//    private static final String DEFAULT_AVATAR_URL = "AAAAAAAAAA";
//    private static final String UPDATED_AVATAR_URL = "BBBBBBBBBB";
//
//    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
//    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";
//
//    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
//    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);
//
//    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
//    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";
//
//    private static final Instant DEFAULT_LAST_MODIFIED_DATE = Instant.ofEpochMilli(0L);
//    private static final Instant UPDATED_LAST_MODIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);
//
//    private static final String DEFAULT_OPEN_ID = "AAAAAAAAAA";
//    private static final String UPDATED_OPEN_ID = "BBBBBBBBBB";
//
//    private static final String DEFAULT_COMMENTS = "AAAAAAAAAA";
//    private static final String UPDATED_COMMENTS = "BBBBBBBBBB";
//
//    @Autowired
//    private NewOrderWechatUserInfoRepository newOrderWechatUserInfoRepository;
//
//    @Autowired
//    private NewOrderWechatUserInfoService newOrderWechatUserInfoService;
//
//    @Autowired
//    private NewOrderWechatUserInfoQueryService newOrderWechatUserInfoQueryService;
//
//    @Autowired
//    private MappingJackson2HttpMessageConverter jacksonMessageConverter;
//
//    @Autowired
//    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;
//
//    @Autowired
//    private ExceptionTranslator exceptionTranslator;
//
//    @Autowired
//    private EntityManager em;
//
//    private MockMvc restNewOrderWechatUserInfoMockMvc;
//
//    private NewOrderWechatUserInfo newOrderWechatUserInfo;
//
//    @Before
//    public void setup() {
//        MockitoAnnotations.initMocks(this);
//        final NewOrderWechatUserInfoResource newOrderWechatUserInfoResource = new NewOrderWechatUserInfoResource(newOrderWechatUserInfoService, newOrderWechatUserInfoQueryService);
//        this.restNewOrderWechatUserInfoMockMvc = MockMvcBuilders.standaloneSetup(newOrderWechatUserInfoResource)
//            .setCustomArgumentResolvers(pageableArgumentResolver)
//            .setControllerAdvice(exceptionTranslator)
//            .setMessageConverters(jacksonMessageConverter).build();
//    }
//
//    /**
//     * Create an entity for this test.
//     *
//     * This is a static method, as tests for other entities might also need it,
//     * if they test an entity which requires the current entity.
//     */
//    public static NewOrderWechatUserInfo createEntity(EntityManager em) {
//        NewOrderWechatUserInfo newOrderWechatUserInfo = new NewOrderWechatUserInfo()
//            .code(DEFAULT_CODE)
//            .encryptedData(DEFAULT_ENCRYPTED_DATA)
//            .iv(DEFAULT_IV)
//            .nickName(DEFAULT_NICK_NAME)
//            .gender(DEFAULT_GENDER)
//            .language(DEFAULT_LANGUAGE)
//            .city(DEFAULT_CITY)
//            .province(DEFAULT_PROVINCE)
//            .country(DEFAULT_COUNTRY)
//            .avatarUrl(DEFAULT_AVATAR_URL)
//            .createdBy(DEFAULT_CREATED_BY)
//            .createdDate(DEFAULT_CREATED_DATE)
//            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
//            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE)
//            .openId(DEFAULT_OPEN_ID)
//            .comments(DEFAULT_COMMENTS);
//        return newOrderWechatUserInfo;
//    }
//
//    @Before
//    public void initTest() {
//        newOrderWechatUserInfo = createEntity(em);
//    }
//
//    @Test
//    @Transactional
//    public void createNewOrderWechatUserInfo() throws Exception {
//        int databaseSizeBeforeCreate = newOrderWechatUserInfoRepository.findAll().size();
//
//        // Create the NewOrderWechatUserInfo
//        restNewOrderWechatUserInfoMockMvc.perform(post("/api/new-order-wechat-user-infos")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(newOrderWechatUserInfo)))
//            .andExpect(status().isCreated());
//
//        // Validate the NewOrderWechatUserInfo in the database
//        List<NewOrderWechatUserInfo> newOrderWechatUserInfoList = newOrderWechatUserInfoRepository.findAll();
//        assertThat(newOrderWechatUserInfoList).hasSize(databaseSizeBeforeCreate + 1);
//        NewOrderWechatUserInfo testNewOrderWechatUserInfo = newOrderWechatUserInfoList.get(newOrderWechatUserInfoList.size() - 1);
//        assertThat(testNewOrderWechatUserInfo.getCode()).isEqualTo(DEFAULT_CODE);
//        assertThat(testNewOrderWechatUserInfo.getEncryptedData()).isEqualTo(DEFAULT_ENCRYPTED_DATA);
//        assertThat(testNewOrderWechatUserInfo.getIv()).isEqualTo(DEFAULT_IV);
//        assertThat(testNewOrderWechatUserInfo.getNickName()).isEqualTo(DEFAULT_NICK_NAME);
//        assertThat(testNewOrderWechatUserInfo.getGender()).isEqualTo(DEFAULT_GENDER);
//        assertThat(testNewOrderWechatUserInfo.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
//        assertThat(testNewOrderWechatUserInfo.getCity()).isEqualTo(DEFAULT_CITY);
//        assertThat(testNewOrderWechatUserInfo.getProvince()).isEqualTo(DEFAULT_PROVINCE);
//        assertThat(testNewOrderWechatUserInfo.getCountry()).isEqualTo(DEFAULT_COUNTRY);
//        assertThat(testNewOrderWechatUserInfo.getAvatarUrl()).isEqualTo(DEFAULT_AVATAR_URL);
//        assertThat(testNewOrderWechatUserInfo.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
//        assertThat(testNewOrderWechatUserInfo.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
//        assertThat(testNewOrderWechatUserInfo.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
//        assertThat(testNewOrderWechatUserInfo.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
//        assertThat(testNewOrderWechatUserInfo.getOpenId()).isEqualTo(DEFAULT_OPEN_ID);
//        assertThat(testNewOrderWechatUserInfo.getComments()).isEqualTo(DEFAULT_COMMENTS);
//    }
//
//    @Test
//    @Transactional
//    public void createNewOrderWechatUserInfoWithExistingId() throws Exception {
//        int databaseSizeBeforeCreate = newOrderWechatUserInfoRepository.findAll().size();
//
//        // Create the NewOrderWechatUserInfo with an existing ID
//        newOrderWechatUserInfo.setId(1L);
//
//        // An entity with an existing ID cannot be created, so this API call must fail
//        restNewOrderWechatUserInfoMockMvc.perform(post("/api/new-order-wechat-user-infos")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(newOrderWechatUserInfo)))
//            .andExpect(status().isBadRequest());
//
//        // Validate the NewOrderWechatUserInfo in the database
//        List<NewOrderWechatUserInfo> newOrderWechatUserInfoList = newOrderWechatUserInfoRepository.findAll();
//        assertThat(newOrderWechatUserInfoList).hasSize(databaseSizeBeforeCreate);
//    }
//
//    @Test
//    @Transactional
//    public void getAllNewOrderWechatUserInfos() throws Exception {
//        // Initialize the database
//        newOrderWechatUserInfoRepository.saveAndFlush(newOrderWechatUserInfo);
//
//        // Get all the newOrderWechatUserInfoList
//        restNewOrderWechatUserInfoMockMvc.perform(get("/api/new-order-wechat-user-infos?sort=id,desc"))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
//            .andExpect(jsonPath("$.[*].id").value(hasItem(newOrderWechatUserInfo.getId().intValue())))
//            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
//            .andExpect(jsonPath("$.[*].encryptedData").value(hasItem(DEFAULT_ENCRYPTED_DATA.toString())))
//            .andExpect(jsonPath("$.[*].iv").value(hasItem(DEFAULT_IV.toString())))
//            .andExpect(jsonPath("$.[*].nickName").value(hasItem(DEFAULT_NICK_NAME.toString())))
//            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER)))
//            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE.toString())))
//            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())))
//            .andExpect(jsonPath("$.[*].province").value(hasItem(DEFAULT_PROVINCE.toString())))
//            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY.toString())))
//            .andExpect(jsonPath("$.[*].avatarUrl").value(hasItem(DEFAULT_AVATAR_URL.toString())))
//            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
//            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
//            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY.toString())))
//            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())))
//            .andExpect(jsonPath("$.[*].openId").value(hasItem(DEFAULT_OPEN_ID.toString())))
//            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS.toString())));
//    }
//
//    @Test
//    @Transactional
//    public void getNewOrderWechatUserInfo() throws Exception {
//        // Initialize the database
//        newOrderWechatUserInfoRepository.saveAndFlush(newOrderWechatUserInfo);
//
//        // Get the newOrderWechatUserInfo
//        restNewOrderWechatUserInfoMockMvc.perform(get("/api/new-order-wechat-user-infos/{id}", newOrderWechatUserInfo.getId()))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
//            .andExpect(jsonPath("$.id").value(newOrderWechatUserInfo.getId().intValue()))
//            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
//            .andExpect(jsonPath("$.encryptedData").value(DEFAULT_ENCRYPTED_DATA.toString()))
//            .andExpect(jsonPath("$.iv").value(DEFAULT_IV.toString()))
//            .andExpect(jsonPath("$.nickName").value(DEFAULT_NICK_NAME.toString()))
//            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER))
//            .andExpect(jsonPath("$.language").value(DEFAULT_LANGUAGE.toString()))
//            .andExpect(jsonPath("$.city").value(DEFAULT_CITY.toString()))
//            .andExpect(jsonPath("$.province").value(DEFAULT_PROVINCE.toString()))
//            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY.toString()))
//            .andExpect(jsonPath("$.avatarUrl").value(DEFAULT_AVATAR_URL.toString()))
//            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
//            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
//            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY.toString()))
//            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()))
//            .andExpect(jsonPath("$.openId").value(DEFAULT_OPEN_ID.toString()))
//            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS.toString()));
//    }
//
//    @Test
//    @Transactional
//    public void getAllNewOrderWechatUserInfosByCodeIsEqualToSomething() throws Exception {
//        // Initialize the database
//        newOrderWechatUserInfoRepository.saveAndFlush(newOrderWechatUserInfo);
//
//        // Get all the newOrderWechatUserInfoList where code equals to DEFAULT_CODE
//        defaultNewOrderWechatUserInfoShouldBeFound("code.equals=" + DEFAULT_CODE);
//
//        // Get all the newOrderWechatUserInfoList where code equals to UPDATED_CODE
//        defaultNewOrderWechatUserInfoShouldNotBeFound("code.equals=" + UPDATED_CODE);
//    }
//
//    @Test
//    @Transactional
//    public void getAllNewOrderWechatUserInfosByCodeIsInShouldWork() throws Exception {
//        // Initialize the database
//        newOrderWechatUserInfoRepository.saveAndFlush(newOrderWechatUserInfo);
//
//        // Get all the newOrderWechatUserInfoList where code in DEFAULT_CODE or UPDATED_CODE
//        defaultNewOrderWechatUserInfoShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);
//
//        // Get all the newOrderWechatUserInfoList where code equals to UPDATED_CODE
//        defaultNewOrderWechatUserInfoShouldNotBeFound("code.in=" + UPDATED_CODE);
//    }
//
//    @Test
//    @Transactional
//    public void getAllNewOrderWechatUserInfosByCodeIsNullOrNotNull() throws Exception {
//        // Initialize the database
//        newOrderWechatUserInfoRepository.saveAndFlush(newOrderWechatUserInfo);
//
//        // Get all the newOrderWechatUserInfoList where code is not null
//        defaultNewOrderWechatUserInfoShouldBeFound("code.specified=true");
//
//        // Get all the newOrderWechatUserInfoList where code is null
//        defaultNewOrderWechatUserInfoShouldNotBeFound("code.specified=false");
//    }
//
//    @Test
//    @Transactional
//    public void getAllNewOrderWechatUserInfosByEncryptedDataIsEqualToSomething() throws Exception {
//        // Initialize the database
//        newOrderWechatUserInfoRepository.saveAndFlush(newOrderWechatUserInfo);
//
//        // Get all the newOrderWechatUserInfoList where encryptedData equals to DEFAULT_ENCRYPTED_DATA
//        defaultNewOrderWechatUserInfoShouldBeFound("encryptedData.equals=" + DEFAULT_ENCRYPTED_DATA);
//
//        // Get all the newOrderWechatUserInfoList where encryptedData equals to UPDATED_ENCRYPTED_DATA
//        defaultNewOrderWechatUserInfoShouldNotBeFound("encryptedData.equals=" + UPDATED_ENCRYPTED_DATA);
//    }
//
//    @Test
//    @Transactional
//    public void getAllNewOrderWechatUserInfosByEncryptedDataIsInShouldWork() throws Exception {
//        // Initialize the database
//        newOrderWechatUserInfoRepository.saveAndFlush(newOrderWechatUserInfo);
//
//        // Get all the newOrderWechatUserInfoList where encryptedData in DEFAULT_ENCRYPTED_DATA or UPDATED_ENCRYPTED_DATA
//        defaultNewOrderWechatUserInfoShouldBeFound("encryptedData.in=" + DEFAULT_ENCRYPTED_DATA + "," + UPDATED_ENCRYPTED_DATA);
//
//        // Get all the newOrderWechatUserInfoList where encryptedData equals to UPDATED_ENCRYPTED_DATA
//        defaultNewOrderWechatUserInfoShouldNotBeFound("encryptedData.in=" + UPDATED_ENCRYPTED_DATA);
//    }
//
//    @Test
//    @Transactional
//    public void getAllNewOrderWechatUserInfosByEncryptedDataIsNullOrNotNull() throws Exception {
//        // Initialize the database
//        newOrderWechatUserInfoRepository.saveAndFlush(newOrderWechatUserInfo);
//
//        // Get all the newOrderWechatUserInfoList where encryptedData is not null
//        defaultNewOrderWechatUserInfoShouldBeFound("encryptedData.specified=true");
//
//        // Get all the newOrderWechatUserInfoList where encryptedData is null
//        defaultNewOrderWechatUserInfoShouldNotBeFound("encryptedData.specified=false");
//    }
//
//    @Test
//    @Transactional
//    public void getAllNewOrderWechatUserInfosByIvIsEqualToSomething() throws Exception {
//        // Initialize the database
//        newOrderWechatUserInfoRepository.saveAndFlush(newOrderWechatUserInfo);
//
//        // Get all the newOrderWechatUserInfoList where iv equals to DEFAULT_IV
//        defaultNewOrderWechatUserInfoShouldBeFound("iv.equals=" + DEFAULT_IV);
//
//        // Get all the newOrderWechatUserInfoList where iv equals to UPDATED_IV
//        defaultNewOrderWechatUserInfoShouldNotBeFound("iv.equals=" + UPDATED_IV);
//    }
//
//    @Test
//    @Transactional
//    public void getAllNewOrderWechatUserInfosByIvIsInShouldWork() throws Exception {
//        // Initialize the database
//        newOrderWechatUserInfoRepository.saveAndFlush(newOrderWechatUserInfo);
//
//        // Get all the newOrderWechatUserInfoList where iv in DEFAULT_IV or UPDATED_IV
//        defaultNewOrderWechatUserInfoShouldBeFound("iv.in=" + DEFAULT_IV + "," + UPDATED_IV);
//
//        // Get all the newOrderWechatUserInfoList where iv equals to UPDATED_IV
//        defaultNewOrderWechatUserInfoShouldNotBeFound("iv.in=" + UPDATED_IV);
//    }
//
//    @Test
//    @Transactional
//    public void getAllNewOrderWechatUserInfosByIvIsNullOrNotNull() throws Exception {
//        // Initialize the database
//        newOrderWechatUserInfoRepository.saveAndFlush(newOrderWechatUserInfo);
//
//        // Get all the newOrderWechatUserInfoList where iv is not null
//        defaultNewOrderWechatUserInfoShouldBeFound("iv.specified=true");
//
//        // Get all the newOrderWechatUserInfoList where iv is null
//        defaultNewOrderWechatUserInfoShouldNotBeFound("iv.specified=false");
//    }
//
//    @Test
//    @Transactional
//    public void getAllNewOrderWechatUserInfosByNickNameIsEqualToSomething() throws Exception {
//        // Initialize the database
//        newOrderWechatUserInfoRepository.saveAndFlush(newOrderWechatUserInfo);
//
//        // Get all the newOrderWechatUserInfoList where nickName equals to DEFAULT_NICK_NAME
//        defaultNewOrderWechatUserInfoShouldBeFound("nickName.equals=" + DEFAULT_NICK_NAME);
//
//        // Get all the newOrderWechatUserInfoList where nickName equals to UPDATED_NICK_NAME
//        defaultNewOrderWechatUserInfoShouldNotBeFound("nickName.equals=" + UPDATED_NICK_NAME);
//    }
//
//    @Test
//    @Transactional
//    public void getAllNewOrderWechatUserInfosByNickNameIsInShouldWork() throws Exception {
//        // Initialize the database
//        newOrderWechatUserInfoRepository.saveAndFlush(newOrderWechatUserInfo);
//
//        // Get all the newOrderWechatUserInfoList where nickName in DEFAULT_NICK_NAME or UPDATED_NICK_NAME
//        defaultNewOrderWechatUserInfoShouldBeFound("nickName.in=" + DEFAULT_NICK_NAME + "," + UPDATED_NICK_NAME);
//
//        // Get all the newOrderWechatUserInfoList where nickName equals to UPDATED_NICK_NAME
//        defaultNewOrderWechatUserInfoShouldNotBeFound("nickName.in=" + UPDATED_NICK_NAME);
//    }
//
//    @Test
//    @Transactional
//    public void getAllNewOrderWechatUserInfosByNickNameIsNullOrNotNull() throws Exception {
//        // Initialize the database
//        newOrderWechatUserInfoRepository.saveAndFlush(newOrderWechatUserInfo);
//
//        // Get all the newOrderWechatUserInfoList where nickName is not null
//        defaultNewOrderWechatUserInfoShouldBeFound("nickName.specified=true");
//
//        // Get all the newOrderWechatUserInfoList where nickName is null
//        defaultNewOrderWechatUserInfoShouldNotBeFound("nickName.specified=false");
//    }
//
//    @Test
//    @Transactional
//    public void getAllNewOrderWechatUserInfosByGenderIsEqualToSomething() throws Exception {
//        // Initialize the database
//        newOrderWechatUserInfoRepository.saveAndFlush(newOrderWechatUserInfo);
//
//        // Get all the newOrderWechatUserInfoList where gender equals to DEFAULT_GENDER
//        defaultNewOrderWechatUserInfoShouldBeFound("gender.equals=" + DEFAULT_GENDER);
//
//        // Get all the newOrderWechatUserInfoList where gender equals to UPDATED_GENDER
//        defaultNewOrderWechatUserInfoShouldNotBeFound("gender.equals=" + UPDATED_GENDER);
//    }
//
//    @Test
//    @Transactional
//    public void getAllNewOrderWechatUserInfosByGenderIsInShouldWork() throws Exception {
//        // Initialize the database
//        newOrderWechatUserInfoRepository.saveAndFlush(newOrderWechatUserInfo);
//
//        // Get all the newOrderWechatUserInfoList where gender in DEFAULT_GENDER or UPDATED_GENDER
//        defaultNewOrderWechatUserInfoShouldBeFound("gender.in=" + DEFAULT_GENDER + "," + UPDATED_GENDER);
//
//        // Get all the newOrderWechatUserInfoList where gender equals to UPDATED_GENDER
//        defaultNewOrderWechatUserInfoShouldNotBeFound("gender.in=" + UPDATED_GENDER);
//    }
//
//    @Test
//    @Transactional
//    public void getAllNewOrderWechatUserInfosByGenderIsNullOrNotNull() throws Exception {
//        // Initialize the database
//        newOrderWechatUserInfoRepository.saveAndFlush(newOrderWechatUserInfo);
//
//        // Get all the newOrderWechatUserInfoList where gender is not null
//        defaultNewOrderWechatUserInfoShouldBeFound("gender.specified=true");
//
//        // Get all the newOrderWechatUserInfoList where gender is null
//        defaultNewOrderWechatUserInfoShouldNotBeFound("gender.specified=false");
//    }
//
//    @Test
//    @Transactional
//    public void getAllNewOrderWechatUserInfosByGenderIsGreaterThanOrEqualToSomething() throws Exception {
//        // Initialize the database
//        newOrderWechatUserInfoRepository.saveAndFlush(newOrderWechatUserInfo);
//
//        // Get all the newOrderWechatUserInfoList where gender greater than or equals to DEFAULT_GENDER
//        defaultNewOrderWechatUserInfoShouldBeFound("gender.greaterOrEqualThan=" + DEFAULT_GENDER);
//
//        // Get all the newOrderWechatUserInfoList where gender greater than or equals to UPDATED_GENDER
//        defaultNewOrderWechatUserInfoShouldNotBeFound("gender.greaterOrEqualThan=" + UPDATED_GENDER);
//    }
//
//    @Test
//    @Transactional
//    public void getAllNewOrderWechatUserInfosByGenderIsLessThanSomething() throws Exception {
//        // Initialize the database
//        newOrderWechatUserInfoRepository.saveAndFlush(newOrderWechatUserInfo);
//
//        // Get all the newOrderWechatUserInfoList where gender less than or equals to DEFAULT_GENDER
//        defaultNewOrderWechatUserInfoShouldNotBeFound("gender.lessThan=" + DEFAULT_GENDER);
//
//        // Get all the newOrderWechatUserInfoList where gender less than or equals to UPDATED_GENDER
//        defaultNewOrderWechatUserInfoShouldBeFound("gender.lessThan=" + UPDATED_GENDER);
//    }
//
//
//    @Test
//    @Transactional
//    public void getAllNewOrderWechatUserInfosByLanguageIsEqualToSomething() throws Exception {
//        // Initialize the database
//        newOrderWechatUserInfoRepository.saveAndFlush(newOrderWechatUserInfo);
//
//        // Get all the newOrderWechatUserInfoList where language equals to DEFAULT_LANGUAGE
//        defaultNewOrderWechatUserInfoShouldBeFound("language.equals=" + DEFAULT_LANGUAGE);
//
//        // Get all the newOrderWechatUserInfoList where language equals to UPDATED_LANGUAGE
//        defaultNewOrderWechatUserInfoShouldNotBeFound("language.equals=" + UPDATED_LANGUAGE);
//    }
//
//    @Test
//    @Transactional
//    public void getAllNewOrderWechatUserInfosByLanguageIsInShouldWork() throws Exception {
//        // Initialize the database
//        newOrderWechatUserInfoRepository.saveAndFlush(newOrderWechatUserInfo);
//
//        // Get all the newOrderWechatUserInfoList where language in DEFAULT_LANGUAGE or UPDATED_LANGUAGE
//        defaultNewOrderWechatUserInfoShouldBeFound("language.in=" + DEFAULT_LANGUAGE + "," + UPDATED_LANGUAGE);
//
//        // Get all the newOrderWechatUserInfoList where language equals to UPDATED_LANGUAGE
//        defaultNewOrderWechatUserInfoShouldNotBeFound("language.in=" + UPDATED_LANGUAGE);
//    }
//
//    @Test
//    @Transactional
//    public void getAllNewOrderWechatUserInfosByLanguageIsNullOrNotNull() throws Exception {
//        // Initialize the database
//        newOrderWechatUserInfoRepository.saveAndFlush(newOrderWechatUserInfo);
//
//        // Get all the newOrderWechatUserInfoList where language is not null
//        defaultNewOrderWechatUserInfoShouldBeFound("language.specified=true");
//
//        // Get all the newOrderWechatUserInfoList where language is null
//        defaultNewOrderWechatUserInfoShouldNotBeFound("language.specified=false");
//    }
//
//    @Test
//    @Transactional
//    public void getAllNewOrderWechatUserInfosByCityIsEqualToSomething() throws Exception {
//        // Initialize the database
//        newOrderWechatUserInfoRepository.saveAndFlush(newOrderWechatUserInfo);
//
//        // Get all the newOrderWechatUserInfoList where city equals to DEFAULT_CITY
//        defaultNewOrderWechatUserInfoShouldBeFound("city.equals=" + DEFAULT_CITY);
//
//        // Get all the newOrderWechatUserInfoList where city equals to UPDATED_CITY
//        defaultNewOrderWechatUserInfoShouldNotBeFound("city.equals=" + UPDATED_CITY);
//    }
//
//    @Test
//    @Transactional
//    public void getAllNewOrderWechatUserInfosByCityIsInShouldWork() throws Exception {
//        // Initialize the database
//        newOrderWechatUserInfoRepository.saveAndFlush(newOrderWechatUserInfo);
//
//        // Get all the newOrderWechatUserInfoList where city in DEFAULT_CITY or UPDATED_CITY
//        defaultNewOrderWechatUserInfoShouldBeFound("city.in=" + DEFAULT_CITY + "," + UPDATED_CITY);
//
//        // Get all the newOrderWechatUserInfoList where city equals to UPDATED_CITY
//        defaultNewOrderWechatUserInfoShouldNotBeFound("city.in=" + UPDATED_CITY);
//    }
//
//    @Test
//    @Transactional
//    public void getAllNewOrderWechatUserInfosByCityIsNullOrNotNull() throws Exception {
//        // Initialize the database
//        newOrderWechatUserInfoRepository.saveAndFlush(newOrderWechatUserInfo);
//
//        // Get all the newOrderWechatUserInfoList where city is not null
//        defaultNewOrderWechatUserInfoShouldBeFound("city.specified=true");
//
//        // Get all the newOrderWechatUserInfoList where city is null
//        defaultNewOrderWechatUserInfoShouldNotBeFound("city.specified=false");
//    }
//
//    @Test
//    @Transactional
//    public void getAllNewOrderWechatUserInfosByProvinceIsEqualToSomething() throws Exception {
//        // Initialize the database
//        newOrderWechatUserInfoRepository.saveAndFlush(newOrderWechatUserInfo);
//
//        // Get all the newOrderWechatUserInfoList where province equals to DEFAULT_PROVINCE
//        defaultNewOrderWechatUserInfoShouldBeFound("province.equals=" + DEFAULT_PROVINCE);
//
//        // Get all the newOrderWechatUserInfoList where province equals to UPDATED_PROVINCE
//        defaultNewOrderWechatUserInfoShouldNotBeFound("province.equals=" + UPDATED_PROVINCE);
//    }
//
//    @Test
//    @Transactional
//    public void getAllNewOrderWechatUserInfosByProvinceIsInShouldWork() throws Exception {
//        // Initialize the database
//        newOrderWechatUserInfoRepository.saveAndFlush(newOrderWechatUserInfo);
//
//        // Get all the newOrderWechatUserInfoList where province in DEFAULT_PROVINCE or UPDATED_PROVINCE
//        defaultNewOrderWechatUserInfoShouldBeFound("province.in=" + DEFAULT_PROVINCE + "," + UPDATED_PROVINCE);
//
//        // Get all the newOrderWechatUserInfoList where province equals to UPDATED_PROVINCE
//        defaultNewOrderWechatUserInfoShouldNotBeFound("province.in=" + UPDATED_PROVINCE);
//    }
//
//    @Test
//    @Transactional
//    public void getAllNewOrderWechatUserInfosByProvinceIsNullOrNotNull() throws Exception {
//        // Initialize the database
//        newOrderWechatUserInfoRepository.saveAndFlush(newOrderWechatUserInfo);
//
//        // Get all the newOrderWechatUserInfoList where province is not null
//        defaultNewOrderWechatUserInfoShouldBeFound("province.specified=true");
//
//        // Get all the newOrderWechatUserInfoList where province is null
//        defaultNewOrderWechatUserInfoShouldNotBeFound("province.specified=false");
//    }
//
//    @Test
//    @Transactional
//    public void getAllNewOrderWechatUserInfosByCountryIsEqualToSomething() throws Exception {
//        // Initialize the database
//        newOrderWechatUserInfoRepository.saveAndFlush(newOrderWechatUserInfo);
//
//        // Get all the newOrderWechatUserInfoList where country equals to DEFAULT_COUNTRY
//        defaultNewOrderWechatUserInfoShouldBeFound("country.equals=" + DEFAULT_COUNTRY);
//
//        // Get all the newOrderWechatUserInfoList where country equals to UPDATED_COUNTRY
//        defaultNewOrderWechatUserInfoShouldNotBeFound("country.equals=" + UPDATED_COUNTRY);
//    }
//
//    @Test
//    @Transactional
//    public void getAllNewOrderWechatUserInfosByCountryIsInShouldWork() throws Exception {
//        // Initialize the database
//        newOrderWechatUserInfoRepository.saveAndFlush(newOrderWechatUserInfo);
//
//        // Get all the newOrderWechatUserInfoList where country in DEFAULT_COUNTRY or UPDATED_COUNTRY
//        defaultNewOrderWechatUserInfoShouldBeFound("country.in=" + DEFAULT_COUNTRY + "," + UPDATED_COUNTRY);
//
//        // Get all the newOrderWechatUserInfoList where country equals to UPDATED_COUNTRY
//        defaultNewOrderWechatUserInfoShouldNotBeFound("country.in=" + UPDATED_COUNTRY);
//    }
//
//    @Test
//    @Transactional
//    public void getAllNewOrderWechatUserInfosByCountryIsNullOrNotNull() throws Exception {
//        // Initialize the database
//        newOrderWechatUserInfoRepository.saveAndFlush(newOrderWechatUserInfo);
//
//        // Get all the newOrderWechatUserInfoList where country is not null
//        defaultNewOrderWechatUserInfoShouldBeFound("country.specified=true");
//
//        // Get all the newOrderWechatUserInfoList where country is null
//        defaultNewOrderWechatUserInfoShouldNotBeFound("country.specified=false");
//    }
//
//    @Test
//    @Transactional
//    public void getAllNewOrderWechatUserInfosByAvatarUrlIsEqualToSomething() throws Exception {
//        // Initialize the database
//        newOrderWechatUserInfoRepository.saveAndFlush(newOrderWechatUserInfo);
//
//        // Get all the newOrderWechatUserInfoList where avatarUrl equals to DEFAULT_AVATAR_URL
//        defaultNewOrderWechatUserInfoShouldBeFound("avatarUrl.equals=" + DEFAULT_AVATAR_URL);
//
//        // Get all the newOrderWechatUserInfoList where avatarUrl equals to UPDATED_AVATAR_URL
//        defaultNewOrderWechatUserInfoShouldNotBeFound("avatarUrl.equals=" + UPDATED_AVATAR_URL);
//    }
//
//    @Test
//    @Transactional
//    public void getAllNewOrderWechatUserInfosByAvatarUrlIsInShouldWork() throws Exception {
//        // Initialize the database
//        newOrderWechatUserInfoRepository.saveAndFlush(newOrderWechatUserInfo);
//
//        // Get all the newOrderWechatUserInfoList where avatarUrl in DEFAULT_AVATAR_URL or UPDATED_AVATAR_URL
//        defaultNewOrderWechatUserInfoShouldBeFound("avatarUrl.in=" + DEFAULT_AVATAR_URL + "," + UPDATED_AVATAR_URL);
//
//        // Get all the newOrderWechatUserInfoList where avatarUrl equals to UPDATED_AVATAR_URL
//        defaultNewOrderWechatUserInfoShouldNotBeFound("avatarUrl.in=" + UPDATED_AVATAR_URL);
//    }
//
//    @Test
//    @Transactional
//    public void getAllNewOrderWechatUserInfosByAvatarUrlIsNullOrNotNull() throws Exception {
//        // Initialize the database
//        newOrderWechatUserInfoRepository.saveAndFlush(newOrderWechatUserInfo);
//
//        // Get all the newOrderWechatUserInfoList where avatarUrl is not null
//        defaultNewOrderWechatUserInfoShouldBeFound("avatarUrl.specified=true");
//
//        // Get all the newOrderWechatUserInfoList where avatarUrl is null
//        defaultNewOrderWechatUserInfoShouldNotBeFound("avatarUrl.specified=false");
//    }
//
//    @Test
//    @Transactional
//    public void getAllNewOrderWechatUserInfosByCreatedByIsEqualToSomething() throws Exception {
//        // Initialize the database
//        newOrderWechatUserInfoRepository.saveAndFlush(newOrderWechatUserInfo);
//
//        // Get all the newOrderWechatUserInfoList where createdBy equals to DEFAULT_CREATED_BY
//        defaultNewOrderWechatUserInfoShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);
//
//        // Get all the newOrderWechatUserInfoList where createdBy equals to UPDATED_CREATED_BY
//        defaultNewOrderWechatUserInfoShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
//    }
//
//    @Test
//    @Transactional
//    public void getAllNewOrderWechatUserInfosByCreatedByIsInShouldWork() throws Exception {
//        // Initialize the database
//        newOrderWechatUserInfoRepository.saveAndFlush(newOrderWechatUserInfo);
//
//        // Get all the newOrderWechatUserInfoList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
//        defaultNewOrderWechatUserInfoShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);
//
//        // Get all the newOrderWechatUserInfoList where createdBy equals to UPDATED_CREATED_BY
//        defaultNewOrderWechatUserInfoShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
//    }
//
//    @Test
//    @Transactional
//    public void getAllNewOrderWechatUserInfosByCreatedByIsNullOrNotNull() throws Exception {
//        // Initialize the database
//        newOrderWechatUserInfoRepository.saveAndFlush(newOrderWechatUserInfo);
//
//        // Get all the newOrderWechatUserInfoList where createdBy is not null
//        defaultNewOrderWechatUserInfoShouldBeFound("createdBy.specified=true");
//
//        // Get all the newOrderWechatUserInfoList where createdBy is null
//        defaultNewOrderWechatUserInfoShouldNotBeFound("createdBy.specified=false");
//    }
//
//    @Test
//    @Transactional
//    public void getAllNewOrderWechatUserInfosByCreatedDateIsEqualToSomething() throws Exception {
//        // Initialize the database
//        newOrderWechatUserInfoRepository.saveAndFlush(newOrderWechatUserInfo);
//
//        // Get all the newOrderWechatUserInfoList where createdDate equals to DEFAULT_CREATED_DATE
//        defaultNewOrderWechatUserInfoShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);
//
//        // Get all the newOrderWechatUserInfoList where createdDate equals to UPDATED_CREATED_DATE
//        defaultNewOrderWechatUserInfoShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
//    }
//
//    @Test
//    @Transactional
//    public void getAllNewOrderWechatUserInfosByCreatedDateIsInShouldWork() throws Exception {
//        // Initialize the database
//        newOrderWechatUserInfoRepository.saveAndFlush(newOrderWechatUserInfo);
//
//        // Get all the newOrderWechatUserInfoList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
//        defaultNewOrderWechatUserInfoShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);
//
//        // Get all the newOrderWechatUserInfoList where createdDate equals to UPDATED_CREATED_DATE
//        defaultNewOrderWechatUserInfoShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
//    }
//
//    @Test
//    @Transactional
//    public void getAllNewOrderWechatUserInfosByCreatedDateIsNullOrNotNull() throws Exception {
//        // Initialize the database
//        newOrderWechatUserInfoRepository.saveAndFlush(newOrderWechatUserInfo);
//
//        // Get all the newOrderWechatUserInfoList where createdDate is not null
//        defaultNewOrderWechatUserInfoShouldBeFound("createdDate.specified=true");
//
//        // Get all the newOrderWechatUserInfoList where createdDate is null
//        defaultNewOrderWechatUserInfoShouldNotBeFound("createdDate.specified=false");
//    }
//
//    @Test
//    @Transactional
//    public void getAllNewOrderWechatUserInfosByLastModifiedByIsEqualToSomething() throws Exception {
//        // Initialize the database
//        newOrderWechatUserInfoRepository.saveAndFlush(newOrderWechatUserInfo);
//
//        // Get all the newOrderWechatUserInfoList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
//        defaultNewOrderWechatUserInfoShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);
//
//        // Get all the newOrderWechatUserInfoList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
//        defaultNewOrderWechatUserInfoShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
//    }
//
//    @Test
//    @Transactional
//    public void getAllNewOrderWechatUserInfosByLastModifiedByIsInShouldWork() throws Exception {
//        // Initialize the database
//        newOrderWechatUserInfoRepository.saveAndFlush(newOrderWechatUserInfo);
//
//        // Get all the newOrderWechatUserInfoList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
//        defaultNewOrderWechatUserInfoShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);
//
//        // Get all the newOrderWechatUserInfoList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
//        defaultNewOrderWechatUserInfoShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
//    }
//
//    @Test
//    @Transactional
//    public void getAllNewOrderWechatUserInfosByLastModifiedByIsNullOrNotNull() throws Exception {
//        // Initialize the database
//        newOrderWechatUserInfoRepository.saveAndFlush(newOrderWechatUserInfo);
//
//        // Get all the newOrderWechatUserInfoList where lastModifiedBy is not null
//        defaultNewOrderWechatUserInfoShouldBeFound("lastModifiedBy.specified=true");
//
//        // Get all the newOrderWechatUserInfoList where lastModifiedBy is null
//        defaultNewOrderWechatUserInfoShouldNotBeFound("lastModifiedBy.specified=false");
//    }
//
//    @Test
//    @Transactional
//    public void getAllNewOrderWechatUserInfosByLastModifiedDateIsEqualToSomething() throws Exception {
//        // Initialize the database
//        newOrderWechatUserInfoRepository.saveAndFlush(newOrderWechatUserInfo);
//
//        // Get all the newOrderWechatUserInfoList where lastModifiedDate equals to DEFAULT_LAST_MODIFIED_DATE
//        defaultNewOrderWechatUserInfoShouldBeFound("lastModifiedDate.equals=" + DEFAULT_LAST_MODIFIED_DATE);
//
//        // Get all the newOrderWechatUserInfoList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
//        defaultNewOrderWechatUserInfoShouldNotBeFound("lastModifiedDate.equals=" + UPDATED_LAST_MODIFIED_DATE);
//    }
//
//    @Test
//    @Transactional
//    public void getAllNewOrderWechatUserInfosByLastModifiedDateIsInShouldWork() throws Exception {
//        // Initialize the database
//        newOrderWechatUserInfoRepository.saveAndFlush(newOrderWechatUserInfo);
//
//        // Get all the newOrderWechatUserInfoList where lastModifiedDate in DEFAULT_LAST_MODIFIED_DATE or UPDATED_LAST_MODIFIED_DATE
//        defaultNewOrderWechatUserInfoShouldBeFound("lastModifiedDate.in=" + DEFAULT_LAST_MODIFIED_DATE + "," + UPDATED_LAST_MODIFIED_DATE);
//
//        // Get all the newOrderWechatUserInfoList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
//        defaultNewOrderWechatUserInfoShouldNotBeFound("lastModifiedDate.in=" + UPDATED_LAST_MODIFIED_DATE);
//    }
//
//    @Test
//    @Transactional
//    public void getAllNewOrderWechatUserInfosByLastModifiedDateIsNullOrNotNull() throws Exception {
//        // Initialize the database
//        newOrderWechatUserInfoRepository.saveAndFlush(newOrderWechatUserInfo);
//
//        // Get all the newOrderWechatUserInfoList where lastModifiedDate is not null
//        defaultNewOrderWechatUserInfoShouldBeFound("lastModifiedDate.specified=true");
//
//        // Get all the newOrderWechatUserInfoList where lastModifiedDate is null
//        defaultNewOrderWechatUserInfoShouldNotBeFound("lastModifiedDate.specified=false");
//    }
//
//    @Test
//    @Transactional
//    public void getAllNewOrderWechatUserInfosByOpenIdIsEqualToSomething() throws Exception {
//        // Initialize the database
//        newOrderWechatUserInfoRepository.saveAndFlush(newOrderWechatUserInfo);
//
//        // Get all the newOrderWechatUserInfoList where openId equals to DEFAULT_OPEN_ID
//        defaultNewOrderWechatUserInfoShouldBeFound("openId.equals=" + DEFAULT_OPEN_ID);
//
//        // Get all the newOrderWechatUserInfoList where openId equals to UPDATED_OPEN_ID
//        defaultNewOrderWechatUserInfoShouldNotBeFound("openId.equals=" + UPDATED_OPEN_ID);
//    }
//
//    @Test
//    @Transactional
//    public void getAllNewOrderWechatUserInfosByOpenIdIsInShouldWork() throws Exception {
//        // Initialize the database
//        newOrderWechatUserInfoRepository.saveAndFlush(newOrderWechatUserInfo);
//
//        // Get all the newOrderWechatUserInfoList where openId in DEFAULT_OPEN_ID or UPDATED_OPEN_ID
//        defaultNewOrderWechatUserInfoShouldBeFound("openId.in=" + DEFAULT_OPEN_ID + "," + UPDATED_OPEN_ID);
//
//        // Get all the newOrderWechatUserInfoList where openId equals to UPDATED_OPEN_ID
//        defaultNewOrderWechatUserInfoShouldNotBeFound("openId.in=" + UPDATED_OPEN_ID);
//    }
//
//    @Test
//    @Transactional
//    public void getAllNewOrderWechatUserInfosByOpenIdIsNullOrNotNull() throws Exception {
//        // Initialize the database
//        newOrderWechatUserInfoRepository.saveAndFlush(newOrderWechatUserInfo);
//
//        // Get all the newOrderWechatUserInfoList where openId is not null
//        defaultNewOrderWechatUserInfoShouldBeFound("openId.specified=true");
//
//        // Get all the newOrderWechatUserInfoList where openId is null
//        defaultNewOrderWechatUserInfoShouldNotBeFound("openId.specified=false");
//    }
//
//    @Test
//    @Transactional
//    public void getAllNewOrderWechatUserInfosByCommentsIsEqualToSomething() throws Exception {
//        // Initialize the database
//        newOrderWechatUserInfoRepository.saveAndFlush(newOrderWechatUserInfo);
//
//        // Get all the newOrderWechatUserInfoList where comments equals to DEFAULT_COMMENTS
//        defaultNewOrderWechatUserInfoShouldBeFound("comments.equals=" + DEFAULT_COMMENTS);
//
//        // Get all the newOrderWechatUserInfoList where comments equals to UPDATED_COMMENTS
//        defaultNewOrderWechatUserInfoShouldNotBeFound("comments.equals=" + UPDATED_COMMENTS);
//    }
//
//    @Test
//    @Transactional
//    public void getAllNewOrderWechatUserInfosByCommentsIsInShouldWork() throws Exception {
//        // Initialize the database
//        newOrderWechatUserInfoRepository.saveAndFlush(newOrderWechatUserInfo);
//
//        // Get all the newOrderWechatUserInfoList where comments in DEFAULT_COMMENTS or UPDATED_COMMENTS
//        defaultNewOrderWechatUserInfoShouldBeFound("comments.in=" + DEFAULT_COMMENTS + "," + UPDATED_COMMENTS);
//
//        // Get all the newOrderWechatUserInfoList where comments equals to UPDATED_COMMENTS
//        defaultNewOrderWechatUserInfoShouldNotBeFound("comments.in=" + UPDATED_COMMENTS);
//    }
//
//    @Test
//    @Transactional
//    public void getAllNewOrderWechatUserInfosByCommentsIsNullOrNotNull() throws Exception {
//        // Initialize the database
//        newOrderWechatUserInfoRepository.saveAndFlush(newOrderWechatUserInfo);
//
//        // Get all the newOrderWechatUserInfoList where comments is not null
//        defaultNewOrderWechatUserInfoShouldBeFound("comments.specified=true");
//
//        // Get all the newOrderWechatUserInfoList where comments is null
//        defaultNewOrderWechatUserInfoShouldNotBeFound("comments.specified=false");
//    }
//
//    /**
//     * Executes the search, and checks that the default entity is returned
//     */
//    private void defaultNewOrderWechatUserInfoShouldBeFound(String filter) throws Exception {
//        restNewOrderWechatUserInfoMockMvc.perform(get("/api/new-order-wechat-user-infos?sort=id,desc&" + filter))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
//            .andExpect(jsonPath("$.[*].id").value(hasItem(newOrderWechatUserInfo.getId().intValue())))
//            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
//            .andExpect(jsonPath("$.[*].encryptedData").value(hasItem(DEFAULT_ENCRYPTED_DATA.toString())))
//            .andExpect(jsonPath("$.[*].iv").value(hasItem(DEFAULT_IV.toString())))
//            .andExpect(jsonPath("$.[*].nickName").value(hasItem(DEFAULT_NICK_NAME.toString())))
//            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER)))
//            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE.toString())))
//            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())))
//            .andExpect(jsonPath("$.[*].province").value(hasItem(DEFAULT_PROVINCE.toString())))
//            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY.toString())))
//            .andExpect(jsonPath("$.[*].avatarUrl").value(hasItem(DEFAULT_AVATAR_URL.toString())))
//            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
//            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
//            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY.toString())))
//            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())))
//            .andExpect(jsonPath("$.[*].openId").value(hasItem(DEFAULT_OPEN_ID.toString())))
//            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS.toString())));
//    }
//
//    /**
//     * Executes the search, and checks that the default entity is not returned
//     */
//    private void defaultNewOrderWechatUserInfoShouldNotBeFound(String filter) throws Exception {
//        restNewOrderWechatUserInfoMockMvc.perform(get("/api/new-order-wechat-user-infos?sort=id,desc&" + filter))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
//            .andExpect(jsonPath("$").isArray())
//            .andExpect(jsonPath("$").isEmpty());
//    }
//
//
//    @Test
//    @Transactional
//    public void getNonExistingNewOrderWechatUserInfo() throws Exception {
//        // Get the newOrderWechatUserInfo
//        restNewOrderWechatUserInfoMockMvc.perform(get("/api/new-order-wechat-user-infos/{id}", Long.MAX_VALUE))
//            .andExpect(status().isNotFound());
//    }
//
//    @Test
//    @Transactional
//    public void updateNewOrderWechatUserInfo() throws Exception {
//        // Initialize the database
//        newOrderWechatUserInfoService.save(newOrderWechatUserInfo);
//
//        int databaseSizeBeforeUpdate = newOrderWechatUserInfoRepository.findAll().size();
//
//        // Update the newOrderWechatUserInfo
//        NewOrderWechatUserInfo updatedNewOrderWechatUserInfo = newOrderWechatUserInfoRepository.findOne(newOrderWechatUserInfo.getId());
//        updatedNewOrderWechatUserInfo
//            .code(UPDATED_CODE)
//            .encryptedData(UPDATED_ENCRYPTED_DATA)
//            .iv(UPDATED_IV)
//            .nickName(UPDATED_NICK_NAME)
//            .gender(UPDATED_GENDER)
//            .language(UPDATED_LANGUAGE)
//            .city(UPDATED_CITY)
//            .province(UPDATED_PROVINCE)
//            .country(UPDATED_COUNTRY)
//            .avatarUrl(UPDATED_AVATAR_URL)
//            .createdBy(UPDATED_CREATED_BY)
//            .createdDate(UPDATED_CREATED_DATE)
//            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
//            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE)
//            .openId(UPDATED_OPEN_ID)
//            .comments(UPDATED_COMMENTS);
//
//        restNewOrderWechatUserInfoMockMvc.perform(put("/api/new-order-wechat-user-infos")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(updatedNewOrderWechatUserInfo)))
//            .andExpect(status().isOk());
//
//        // Validate the NewOrderWechatUserInfo in the database
//        List<NewOrderWechatUserInfo> newOrderWechatUserInfoList = newOrderWechatUserInfoRepository.findAll();
//        assertThat(newOrderWechatUserInfoList).hasSize(databaseSizeBeforeUpdate);
//        NewOrderWechatUserInfo testNewOrderWechatUserInfo = newOrderWechatUserInfoList.get(newOrderWechatUserInfoList.size() - 1);
//        assertThat(testNewOrderWechatUserInfo.getCode()).isEqualTo(UPDATED_CODE);
//        assertThat(testNewOrderWechatUserInfo.getEncryptedData()).isEqualTo(UPDATED_ENCRYPTED_DATA);
//        assertThat(testNewOrderWechatUserInfo.getIv()).isEqualTo(UPDATED_IV);
//        assertThat(testNewOrderWechatUserInfo.getNickName()).isEqualTo(UPDATED_NICK_NAME);
//        assertThat(testNewOrderWechatUserInfo.getGender()).isEqualTo(UPDATED_GENDER);
//        assertThat(testNewOrderWechatUserInfo.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
//        assertThat(testNewOrderWechatUserInfo.getCity()).isEqualTo(UPDATED_CITY);
//        assertThat(testNewOrderWechatUserInfo.getProvince()).isEqualTo(UPDATED_PROVINCE);
//        assertThat(testNewOrderWechatUserInfo.getCountry()).isEqualTo(UPDATED_COUNTRY);
//        assertThat(testNewOrderWechatUserInfo.getAvatarUrl()).isEqualTo(UPDATED_AVATAR_URL);
//        assertThat(testNewOrderWechatUserInfo.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
//        assertThat(testNewOrderWechatUserInfo.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
//        assertThat(testNewOrderWechatUserInfo.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
//        assertThat(testNewOrderWechatUserInfo.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
//        assertThat(testNewOrderWechatUserInfo.getOpenId()).isEqualTo(UPDATED_OPEN_ID);
//        assertThat(testNewOrderWechatUserInfo.getComments()).isEqualTo(UPDATED_COMMENTS);
//    }
//
//    @Test
//    @Transactional
//    public void updateNonExistingNewOrderWechatUserInfo() throws Exception {
//        int databaseSizeBeforeUpdate = newOrderWechatUserInfoRepository.findAll().size();
//
//        // Create the NewOrderWechatUserInfo
//
//        // If the entity doesn't have an ID, it will be created instead of just being updated
//        restNewOrderWechatUserInfoMockMvc.perform(put("/api/new-order-wechat-user-infos")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(newOrderWechatUserInfo)))
//            .andExpect(status().isCreated());
//
//        // Validate the NewOrderWechatUserInfo in the database
//        List<NewOrderWechatUserInfo> newOrderWechatUserInfoList = newOrderWechatUserInfoRepository.findAll();
//        assertThat(newOrderWechatUserInfoList).hasSize(databaseSizeBeforeUpdate + 1);
//    }
//
//    @Test
//    @Transactional
//    public void deleteNewOrderWechatUserInfo() throws Exception {
//        // Initialize the database
//        newOrderWechatUserInfoService.save(newOrderWechatUserInfo);
//
//        int databaseSizeBeforeDelete = newOrderWechatUserInfoRepository.findAll().size();
//
//        // Get the newOrderWechatUserInfo
//        restNewOrderWechatUserInfoMockMvc.perform(delete("/api/new-order-wechat-user-infos/{id}", newOrderWechatUserInfo.getId())
//            .accept(TestUtil.APPLICATION_JSON_UTF8))
//            .andExpect(status().isOk());
//
//        // Validate the database is empty
//        List<NewOrderWechatUserInfo> newOrderWechatUserInfoList = newOrderWechatUserInfoRepository.findAll();
//        assertThat(newOrderWechatUserInfoList).hasSize(databaseSizeBeforeDelete - 1);
//    }
//
//    @Test
//    @Transactional
//    public void equalsVerifier() throws Exception {
//        TestUtil.equalsVerifier(NewOrderWechatUserInfo.class);
//        NewOrderWechatUserInfo newOrderWechatUserInfo1 = new NewOrderWechatUserInfo();
//        newOrderWechatUserInfo1.setId(1L);
//        NewOrderWechatUserInfo newOrderWechatUserInfo2 = new NewOrderWechatUserInfo();
//        newOrderWechatUserInfo2.setId(newOrderWechatUserInfo1.getId());
//        assertThat(newOrderWechatUserInfo1).isEqualTo(newOrderWechatUserInfo2);
//        newOrderWechatUserInfo2.setId(2L);
//        assertThat(newOrderWechatUserInfo1).isNotEqualTo(newOrderWechatUserInfo2);
//        newOrderWechatUserInfo1.setId(null);
//        assertThat(newOrderWechatUserInfo1).isNotEqualTo(newOrderWechatUserInfo2);
//    }
//}
