package com.pure.service.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pure.service.domain.PaymentType;
import com.pure.service.service.PaymentTypeService;
import com.pure.service.web.rest.util.HeaderUtil;
import com.pure.service.web.rest.util.PaginationUtil;
import com.pure.service.service.dto.PaymentTypeCriteria;
import com.pure.service.service.PaymentTypeQueryService;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing PaymentType.
 */
@RestController
@RequestMapping("/api")
public class PaymentTypeResource {

    private final Logger log = LoggerFactory.getLogger(PaymentTypeResource.class);

    private static final String ENTITY_NAME = "paymentType";

    private final PaymentTypeService paymentTypeService;

    private final PaymentTypeQueryService paymentTypeQueryService;

    public PaymentTypeResource(PaymentTypeService paymentTypeService, PaymentTypeQueryService paymentTypeQueryService) {
        this.paymentTypeService = paymentTypeService;
        this.paymentTypeQueryService = paymentTypeQueryService;
    }

    /**
     * POST  /payment-types : Create a new paymentType.
     *
     * @param paymentType the paymentType to create
     * @return the ResponseEntity with status 201 (Created) and with body the new paymentType, or with status 400 (Bad Request) if the paymentType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/payment-types")
    @Timed
    public ResponseEntity<PaymentType> createPaymentType(@RequestBody PaymentType paymentType) throws URISyntaxException {
        log.debug("REST request to save PaymentType : {}", paymentType);
        if (paymentType.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new paymentType cannot already have an ID")).body(null);
        }
        PaymentType result = paymentTypeService.save(paymentType);
        return ResponseEntity.created(new URI("/api/payment-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /payment-types : Updates an existing paymentType.
     *
     * @param paymentType the paymentType to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated paymentType,
     * or with status 400 (Bad Request) if the paymentType is not valid,
     * or with status 500 (Internal Server Error) if the paymentType couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/payment-types")
    @Timed
    public ResponseEntity<PaymentType> updatePaymentType(@RequestBody PaymentType paymentType) throws URISyntaxException {
        log.debug("REST request to update PaymentType : {}", paymentType);
        if (paymentType.getId() == null) {
            return createPaymentType(paymentType);
        }
        PaymentType result = paymentTypeService.save(paymentType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, paymentType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /payment-types : get all the paymentTypes.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of paymentTypes in body
     */
    @GetMapping("/payment-types")
    @Timed
    public ResponseEntity<List<PaymentType>> getAllPaymentTypes(PaymentTypeCriteria criteria,@ApiParam Pageable pageable) {
        log.debug("REST request to get PaymentTypes by criteria: {}", criteria);
        Page<PaymentType> page = paymentTypeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/payment-types");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /payment-types/:id : get the "id" paymentType.
     *
     * @param id the id of the paymentType to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the paymentType, or with status 404 (Not Found)
     */
    @GetMapping("/payment-types/{id}")
    @Timed
    public ResponseEntity<PaymentType> getPaymentType(@PathVariable Long id) {
        log.debug("REST request to get PaymentType : {}", id);
        PaymentType paymentType = paymentTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(paymentType));
    }

    /**
     * DELETE  /payment-types/:id : delete the "id" paymentType.
     *
     * @param id the id of the paymentType to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/payment-types/{id}")
    @Timed
    public ResponseEntity<Void> deletePaymentType(@PathVariable Long id) {
        log.debug("REST request to delete PaymentType : {}", id);
        paymentTypeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
