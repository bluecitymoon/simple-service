package com.pure.service.web.rest;

import com.pure.service.SimpleServiceApp;

import com.pure.service.domain.Payment;
import com.pure.service.repository.PaymentRepository;
import com.pure.service.service.PaymentService;
import com.pure.service.web.rest.errors.ExceptionTranslator;
import com.pure.service.service.dto.PaymentCriteria;
import com.pure.service.service.PaymentQueryService;

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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PaymentResource REST controller.
 *
 * @see PaymentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SimpleServiceApp.class)
public class PaymentResourceIntTest {

    private static final String DEFAULT_PROJECT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PROJECT_NAME = "BBBBBBBBBB";

    private static final Float DEFAULT_ESTIMATE_AMOUNT = 1F;
    private static final Float UPDATED_ESTIMATE_AMOUNT = 2F;

    private static final Float DEFAULT_ACTUAL_AMOUNT = 1F;
    private static final Float UPDATED_ACTUAL_AMOUNT = 2F;

    private static final Float DEFAULT_PAIED = 1F;
    private static final Float UPDATED_PAIED = 2F;

    private static final Float DEFAULT_UNPAID = 1F;
    private static final Float UPDATED_UNPAID = 2F;

    private static final Instant DEFAULT_PAID_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_PAID_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTS = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private PaymentQueryService paymentQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPaymentMockMvc;

    private Payment payment;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PaymentResource paymentResource = new PaymentResource(paymentService, paymentQueryService);
        this.restPaymentMockMvc = MockMvcBuilders.standaloneSetup(paymentResource)
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
    public static Payment createEntity(EntityManager em) {
        Payment payment = new Payment()
            .projectName(DEFAULT_PROJECT_NAME)
            .estimateAmount(DEFAULT_ESTIMATE_AMOUNT)
            .actualAmount(DEFAULT_ACTUAL_AMOUNT)
            .paied(DEFAULT_PAIED)
            .unpaid(DEFAULT_UNPAID)
            .paidDate(DEFAULT_PAID_DATE)
            .comments(DEFAULT_COMMENTS)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return payment;
    }

    @Before
    public void initTest() {
        payment = createEntity(em);
    }

    @Test
    @Transactional
    public void createPayment() throws Exception {
        int databaseSizeBeforeCreate = paymentRepository.findAll().size();

        // Create the Payment
        restPaymentMockMvc.perform(post("/api/payments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(payment)))
            .andExpect(status().isCreated());

        // Validate the Payment in the database
        List<Payment> paymentList = paymentRepository.findAll();
        assertThat(paymentList).hasSize(databaseSizeBeforeCreate + 1);
        Payment testPayment = paymentList.get(paymentList.size() - 1);
        assertThat(testPayment.getProjectName()).isEqualTo(DEFAULT_PROJECT_NAME);
        assertThat(testPayment.getEstimateAmount()).isEqualTo(DEFAULT_ESTIMATE_AMOUNT);
        assertThat(testPayment.getActualAmount()).isEqualTo(DEFAULT_ACTUAL_AMOUNT);
        assertThat(testPayment.getPaied()).isEqualTo(DEFAULT_PAIED);
        assertThat(testPayment.getUnpaid()).isEqualTo(DEFAULT_UNPAID);
        assertThat(testPayment.getPaidDate()).isEqualTo(DEFAULT_PAID_DATE);
        assertThat(testPayment.getComments()).isEqualTo(DEFAULT_COMMENTS);
        assertThat(testPayment.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testPayment.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testPayment.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testPayment.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createPaymentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = paymentRepository.findAll().size();

        // Create the Payment with an existing ID
        payment.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPaymentMockMvc.perform(post("/api/payments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(payment)))
            .andExpect(status().isBadRequest());

        // Validate the Payment in the database
        List<Payment> paymentList = paymentRepository.findAll();
        assertThat(paymentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPayments() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList
        restPaymentMockMvc.perform(get("/api/payments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(payment.getId().intValue())))
            .andExpect(jsonPath("$.[*].projectName").value(hasItem(DEFAULT_PROJECT_NAME)))
            .andExpect(jsonPath("$.[*].estimateAmount").value(hasItem(DEFAULT_ESTIMATE_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].actualAmount").value(hasItem(DEFAULT_ACTUAL_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].paied").value(hasItem(DEFAULT_PAIED.doubleValue())))
            .andExpect(jsonPath("$.[*].unpaid").value(hasItem(DEFAULT_UNPAID.doubleValue())))
            .andExpect(jsonPath("$.[*].paidDate").value(hasItem(DEFAULT_PAID_DATE.toString())))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    public void getPayment() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get the payment
        restPaymentMockMvc.perform(get("/api/payments/{id}", payment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(payment.getId().intValue()))
            .andExpect(jsonPath("$.projectName").value(DEFAULT_PROJECT_NAME))
            .andExpect(jsonPath("$.estimateAmount").value(DEFAULT_ESTIMATE_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.actualAmount").value(DEFAULT_ACTUAL_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.paied").value(DEFAULT_PAIED.doubleValue()))
            .andExpect(jsonPath("$.unpaid").value(DEFAULT_UNPAID.doubleValue()))
            .andExpect(jsonPath("$.paidDate").value(DEFAULT_PAID_DATE.toString()))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    public void getAllPaymentsByProjectNameIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where projectName equals to DEFAULT_PROJECT_NAME
        defaultPaymentShouldBeFound("projectName.equals=" + DEFAULT_PROJECT_NAME);

        // Get all the paymentList where projectName equals to UPDATED_PROJECT_NAME
        defaultPaymentShouldNotBeFound("projectName.equals=" + UPDATED_PROJECT_NAME);
    }

    @Test
    @Transactional
    public void getAllPaymentsByProjectNameIsInShouldWork() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where projectName in DEFAULT_PROJECT_NAME or UPDATED_PROJECT_NAME
        defaultPaymentShouldBeFound("projectName.in=" + DEFAULT_PROJECT_NAME + "," + UPDATED_PROJECT_NAME);

        // Get all the paymentList where projectName equals to UPDATED_PROJECT_NAME
        defaultPaymentShouldNotBeFound("projectName.in=" + UPDATED_PROJECT_NAME);
    }

    @Test
    @Transactional
    public void getAllPaymentsByProjectNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where projectName is not null
        defaultPaymentShouldBeFound("projectName.specified=true");

        // Get all the paymentList where projectName is null
        defaultPaymentShouldNotBeFound("projectName.specified=false");
    }

    @Test
    @Transactional
    public void getAllPaymentsByEstimateAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where estimateAmount equals to DEFAULT_ESTIMATE_AMOUNT
        defaultPaymentShouldBeFound("estimateAmount.equals=" + DEFAULT_ESTIMATE_AMOUNT);

        // Get all the paymentList where estimateAmount equals to UPDATED_ESTIMATE_AMOUNT
        defaultPaymentShouldNotBeFound("estimateAmount.equals=" + UPDATED_ESTIMATE_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllPaymentsByEstimateAmountIsInShouldWork() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where estimateAmount in DEFAULT_ESTIMATE_AMOUNT or UPDATED_ESTIMATE_AMOUNT
        defaultPaymentShouldBeFound("estimateAmount.in=" + DEFAULT_ESTIMATE_AMOUNT + "," + UPDATED_ESTIMATE_AMOUNT);

        // Get all the paymentList where estimateAmount equals to UPDATED_ESTIMATE_AMOUNT
        defaultPaymentShouldNotBeFound("estimateAmount.in=" + UPDATED_ESTIMATE_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllPaymentsByEstimateAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where estimateAmount is not null
        defaultPaymentShouldBeFound("estimateAmount.specified=true");

        // Get all the paymentList where estimateAmount is null
        defaultPaymentShouldNotBeFound("estimateAmount.specified=false");
    }

    @Test
    @Transactional
    public void getAllPaymentsByActualAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where actualAmount equals to DEFAULT_ACTUAL_AMOUNT
        defaultPaymentShouldBeFound("actualAmount.equals=" + DEFAULT_ACTUAL_AMOUNT);

        // Get all the paymentList where actualAmount equals to UPDATED_ACTUAL_AMOUNT
        defaultPaymentShouldNotBeFound("actualAmount.equals=" + UPDATED_ACTUAL_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllPaymentsByActualAmountIsInShouldWork() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where actualAmount in DEFAULT_ACTUAL_AMOUNT or UPDATED_ACTUAL_AMOUNT
        defaultPaymentShouldBeFound("actualAmount.in=" + DEFAULT_ACTUAL_AMOUNT + "," + UPDATED_ACTUAL_AMOUNT);

        // Get all the paymentList where actualAmount equals to UPDATED_ACTUAL_AMOUNT
        defaultPaymentShouldNotBeFound("actualAmount.in=" + UPDATED_ACTUAL_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllPaymentsByActualAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where actualAmount is not null
        defaultPaymentShouldBeFound("actualAmount.specified=true");

        // Get all the paymentList where actualAmount is null
        defaultPaymentShouldNotBeFound("actualAmount.specified=false");
    }

    @Test
    @Transactional
    public void getAllPaymentsByPaiedIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where paied equals to DEFAULT_PAIED
        defaultPaymentShouldBeFound("paied.equals=" + DEFAULT_PAIED);

        // Get all the paymentList where paied equals to UPDATED_PAIED
        defaultPaymentShouldNotBeFound("paied.equals=" + UPDATED_PAIED);
    }

    @Test
    @Transactional
    public void getAllPaymentsByPaiedIsInShouldWork() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where paied in DEFAULT_PAIED or UPDATED_PAIED
        defaultPaymentShouldBeFound("paied.in=" + DEFAULT_PAIED + "," + UPDATED_PAIED);

        // Get all the paymentList where paied equals to UPDATED_PAIED
        defaultPaymentShouldNotBeFound("paied.in=" + UPDATED_PAIED);
    }

    @Test
    @Transactional
    public void getAllPaymentsByPaiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where paied is not null
        defaultPaymentShouldBeFound("paied.specified=true");

        // Get all the paymentList where paied is null
        defaultPaymentShouldNotBeFound("paied.specified=false");
    }

    @Test
    @Transactional
    public void getAllPaymentsByUnpaidIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where unpaid equals to DEFAULT_UNPAID
        defaultPaymentShouldBeFound("unpaid.equals=" + DEFAULT_UNPAID);

        // Get all the paymentList where unpaid equals to UPDATED_UNPAID
        defaultPaymentShouldNotBeFound("unpaid.equals=" + UPDATED_UNPAID);
    }

    @Test
    @Transactional
    public void getAllPaymentsByUnpaidIsInShouldWork() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where unpaid in DEFAULT_UNPAID or UPDATED_UNPAID
        defaultPaymentShouldBeFound("unpaid.in=" + DEFAULT_UNPAID + "," + UPDATED_UNPAID);

        // Get all the paymentList where unpaid equals to UPDATED_UNPAID
        defaultPaymentShouldNotBeFound("unpaid.in=" + UPDATED_UNPAID);
    }

    @Test
    @Transactional
    public void getAllPaymentsByUnpaidIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where unpaid is not null
        defaultPaymentShouldBeFound("unpaid.specified=true");

        // Get all the paymentList where unpaid is null
        defaultPaymentShouldNotBeFound("unpaid.specified=false");
    }

    @Test
    @Transactional
    public void getAllPaymentsByPaidDateIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where paidDate equals to DEFAULT_PAID_DATE
        defaultPaymentShouldBeFound("paidDate.equals=" + DEFAULT_PAID_DATE);

        // Get all the paymentList where paidDate equals to UPDATED_PAID_DATE
        defaultPaymentShouldNotBeFound("paidDate.equals=" + UPDATED_PAID_DATE);
    }

    @Test
    @Transactional
    public void getAllPaymentsByPaidDateIsInShouldWork() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where paidDate in DEFAULT_PAID_DATE or UPDATED_PAID_DATE
        defaultPaymentShouldBeFound("paidDate.in=" + DEFAULT_PAID_DATE + "," + UPDATED_PAID_DATE);

        // Get all the paymentList where paidDate equals to UPDATED_PAID_DATE
        defaultPaymentShouldNotBeFound("paidDate.in=" + UPDATED_PAID_DATE);
    }

    @Test
    @Transactional
    public void getAllPaymentsByPaidDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where paidDate is not null
        defaultPaymentShouldBeFound("paidDate.specified=true");

        // Get all the paymentList where paidDate is null
        defaultPaymentShouldNotBeFound("paidDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllPaymentsByCommentsIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where comments equals to DEFAULT_COMMENTS
        defaultPaymentShouldBeFound("comments.equals=" + DEFAULT_COMMENTS);

        // Get all the paymentList where comments equals to UPDATED_COMMENTS
        defaultPaymentShouldNotBeFound("comments.equals=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllPaymentsByCommentsIsInShouldWork() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where comments in DEFAULT_COMMENTS or UPDATED_COMMENTS
        defaultPaymentShouldBeFound("comments.in=" + DEFAULT_COMMENTS + "," + UPDATED_COMMENTS);

        // Get all the paymentList where comments equals to UPDATED_COMMENTS
        defaultPaymentShouldNotBeFound("comments.in=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllPaymentsByCommentsIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where comments is not null
        defaultPaymentShouldBeFound("comments.specified=true");

        // Get all the paymentList where comments is null
        defaultPaymentShouldNotBeFound("comments.specified=false");
    }

    @Test
    @Transactional
    public void getAllPaymentsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where createdBy equals to DEFAULT_CREATED_BY
        defaultPaymentShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the paymentList where createdBy equals to UPDATED_CREATED_BY
        defaultPaymentShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllPaymentsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultPaymentShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the paymentList where createdBy equals to UPDATED_CREATED_BY
        defaultPaymentShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllPaymentsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where createdBy is not null
        defaultPaymentShouldBeFound("createdBy.specified=true");

        // Get all the paymentList where createdBy is null
        defaultPaymentShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllPaymentsByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where createdDate equals to DEFAULT_CREATED_DATE
        defaultPaymentShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the paymentList where createdDate equals to UPDATED_CREATED_DATE
        defaultPaymentShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllPaymentsByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultPaymentShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the paymentList where createdDate equals to UPDATED_CREATED_DATE
        defaultPaymentShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllPaymentsByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where createdDate is not null
        defaultPaymentShouldBeFound("createdDate.specified=true");

        // Get all the paymentList where createdDate is null
        defaultPaymentShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllPaymentsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultPaymentShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the paymentList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultPaymentShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllPaymentsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultPaymentShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the paymentList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultPaymentShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllPaymentsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where lastModifiedBy is not null
        defaultPaymentShouldBeFound("lastModifiedBy.specified=true");

        // Get all the paymentList where lastModifiedBy is null
        defaultPaymentShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllPaymentsByLastModifiedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where lastModifiedDate equals to DEFAULT_LAST_MODIFIED_DATE
        defaultPaymentShouldBeFound("lastModifiedDate.equals=" + DEFAULT_LAST_MODIFIED_DATE);

        // Get all the paymentList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultPaymentShouldNotBeFound("lastModifiedDate.equals=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllPaymentsByLastModifiedDateIsInShouldWork() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where lastModifiedDate in DEFAULT_LAST_MODIFIED_DATE or UPDATED_LAST_MODIFIED_DATE
        defaultPaymentShouldBeFound("lastModifiedDate.in=" + DEFAULT_LAST_MODIFIED_DATE + "," + UPDATED_LAST_MODIFIED_DATE);

        // Get all the paymentList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultPaymentShouldNotBeFound("lastModifiedDate.in=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllPaymentsByLastModifiedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where lastModifiedDate is not null
        defaultPaymentShouldBeFound("lastModifiedDate.specified=true");

        // Get all the paymentList where lastModifiedDate is null
        defaultPaymentShouldNotBeFound("lastModifiedDate.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultPaymentShouldBeFound(String filter) throws Exception {
        restPaymentMockMvc.perform(get("/api/payments?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(payment.getId().intValue())))
            .andExpect(jsonPath("$.[*].projectName").value(hasItem(DEFAULT_PROJECT_NAME)))
            .andExpect(jsonPath("$.[*].estimateAmount").value(hasItem(DEFAULT_ESTIMATE_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].actualAmount").value(hasItem(DEFAULT_ACTUAL_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].paied").value(hasItem(DEFAULT_PAIED.doubleValue())))
            .andExpect(jsonPath("$.[*].unpaid").value(hasItem(DEFAULT_UNPAID.doubleValue())))
            .andExpect(jsonPath("$.[*].paidDate").value(hasItem(DEFAULT_PAID_DATE.toString())))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultPaymentShouldNotBeFound(String filter) throws Exception {
        restPaymentMockMvc.perform(get("/api/payments?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingPayment() throws Exception {
        // Get the payment
        restPaymentMockMvc.perform(get("/api/payments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePayment() throws Exception {
        // Initialize the database
        paymentService.save(payment);

        int databaseSizeBeforeUpdate = paymentRepository.findAll().size();

        // Update the payment
        Payment updatedPayment = paymentRepository.findOne(payment.getId());
        updatedPayment
            .projectName(UPDATED_PROJECT_NAME)
            .estimateAmount(UPDATED_ESTIMATE_AMOUNT)
            .actualAmount(UPDATED_ACTUAL_AMOUNT)
            .paied(UPDATED_PAIED)
            .unpaid(UPDATED_UNPAID)
            .paidDate(UPDATED_PAID_DATE)
            .comments(UPDATED_COMMENTS)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restPaymentMockMvc.perform(put("/api/payments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPayment)))
            .andExpect(status().isOk());

        // Validate the Payment in the database
        List<Payment> paymentList = paymentRepository.findAll();
        assertThat(paymentList).hasSize(databaseSizeBeforeUpdate);
        Payment testPayment = paymentList.get(paymentList.size() - 1);
        assertThat(testPayment.getProjectName()).isEqualTo(UPDATED_PROJECT_NAME);
        assertThat(testPayment.getEstimateAmount()).isEqualTo(UPDATED_ESTIMATE_AMOUNT);
        assertThat(testPayment.getActualAmount()).isEqualTo(UPDATED_ACTUAL_AMOUNT);
        assertThat(testPayment.getPaied()).isEqualTo(UPDATED_PAIED);
        assertThat(testPayment.getUnpaid()).isEqualTo(UPDATED_UNPAID);
        assertThat(testPayment.getPaidDate()).isEqualTo(UPDATED_PAID_DATE);
        assertThat(testPayment.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testPayment.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testPayment.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testPayment.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testPayment.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingPayment() throws Exception {
        int databaseSizeBeforeUpdate = paymentRepository.findAll().size();

        // Create the Payment

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPaymentMockMvc.perform(put("/api/payments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(payment)))
            .andExpect(status().isCreated());

        // Validate the Payment in the database
        List<Payment> paymentList = paymentRepository.findAll();
        assertThat(paymentList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePayment() throws Exception {
        // Initialize the database
        paymentService.save(payment);

        int databaseSizeBeforeDelete = paymentRepository.findAll().size();

        // Get the payment
        restPaymentMockMvc.perform(delete("/api/payments/{id}", payment.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Payment> paymentList = paymentRepository.findAll();
        assertThat(paymentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Payment.class);
        Payment payment1 = new Payment();
        payment1.setId(1L);
        Payment payment2 = new Payment();
        payment2.setId(payment1.getId());
        assertThat(payment1).isEqualTo(payment2);
        payment2.setId(2L);
        assertThat(payment1).isNotEqualTo(payment2);
        payment1.setId(null);
        assertThat(payment1).isNotEqualTo(payment2);
    }
}
