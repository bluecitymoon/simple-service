package com.pure.service.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pure.service.domain.CustomerCard;
import com.pure.service.service.CustomerCardService;
import com.pure.service.service.dto.CardNumberRequest;
import com.pure.service.service.dto.CustomerCardDTO;
import com.pure.service.web.rest.util.HeaderUtil;
import com.pure.service.web.rest.util.PaginationUtil;
import com.pure.service.service.dto.CustomerCardCriteria;
import com.pure.service.service.CustomerCardQueryService;
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
 * REST controller for managing CustomerCard.
 */
@RestController
@RequestMapping("/api")
public class CustomerCardResource {

    private final Logger log = LoggerFactory.getLogger(CustomerCardResource.class);

    private static final String ENTITY_NAME = "customerCard";

    private final CustomerCardService customerCardService;

    private final CustomerCardQueryService customerCardQueryService;

    public CustomerCardResource(CustomerCardService customerCardService, CustomerCardQueryService customerCardQueryService) {
        this.customerCardService = customerCardService;
        this.customerCardQueryService = customerCardQueryService;
    }

    /**
     * POST  /customer-cards : Create a new customerCard.
     *
     * @param customerCard the customerCard to create
     * @return the ResponseEntity with status 201 (Created) and with body the new customerCard, or with status 400 (Bad Request) if the customerCard has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/customer-cards")
    @Timed
    public ResponseEntity<CustomerCard> createCustomerCard(@RequestBody CustomerCard customerCard) throws URISyntaxException {
        log.debug("REST request to save CustomerCard : {}", customerCard);
        if (customerCard.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new customerCard cannot already have an ID")).body(null);
        }
        CustomerCard result = customerCardService.save(customerCard);
        return ResponseEntity.created(new URI("/api/customer-cards/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PostMapping("/customer-cards/generate-card-number")
    @Timed
    public ResponseEntity<CustomerCardDTO> generateCardNumber(@RequestBody CardNumberRequest cardNumberRequest) {
        String cardNumber = customerCardService.generateCardNumber(cardNumberRequest);

        CustomerCardDTO dto = new CustomerCardDTO();
        dto.setCardNumber(cardNumber);

        return ResponseEntity.ok(dto);
    }

    /**
     * PUT  /customer-cards : Updates an existing customerCard.
     *
     * @param customerCard the customerCard to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated customerCard,
     * or with status 400 (Bad Request) if the customerCard is not valid,
     * or with status 500 (Internal Server Error) if the customerCard couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/customer-cards")
    @Timed
    public ResponseEntity<CustomerCard> updateCustomerCard(@RequestBody CustomerCard customerCard) throws URISyntaxException {
        log.debug("REST request to update CustomerCard : {}", customerCard);
        if (customerCard.getId() == null) {
            return createCustomerCard(customerCard);
        }
        CustomerCard result = customerCardService.save(customerCard);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, customerCard.getId().toString()))
            .body(result);
    }

    /**
     * GET  /customer-cards : get all the customerCards.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of customerCards in body
     */
    @GetMapping("/customer-cards")
    @Timed
    public ResponseEntity<List<CustomerCard>> getAllCustomerCards(CustomerCardCriteria criteria,@ApiParam Pageable pageable) {
        log.debug("REST request to get CustomerCards by criteria: {}", criteria);
        Page<CustomerCard> page = customerCardQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/customer-cards");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /customer-cards/:id : get the "id" customerCard.
     *
     * @param id the id of the customerCard to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the customerCard, or with status 404 (Not Found)
     */
    @GetMapping("/customer-cards/{id}")
    @Timed
    public ResponseEntity<CustomerCard> getCustomerCard(@PathVariable Long id) {
        log.debug("REST request to get CustomerCard : {}", id);
        CustomerCard customerCard = customerCardService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(customerCard));
    }

    /**
     * DELETE  /customer-cards/:id : delete the "id" customerCard.
     *
     * @param id the id of the customerCard to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/customer-cards/{id}")
    @Timed
    public ResponseEntity<Void> deleteCustomerCard(@PathVariable Long id) {
        log.debug("REST request to delete CustomerCard : {}", id);
        customerCardService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
