package com.pure.service.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pure.service.domain.MarketingNewOrderPlan;
import com.pure.service.service.MarketingNewOrderPlanService;
import com.pure.service.web.rest.util.HeaderUtil;
import com.pure.service.web.rest.util.PaginationUtil;
import com.pure.service.service.dto.MarketingNewOrderPlanCriteria;
import com.pure.service.service.MarketingNewOrderPlanQueryService;
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
 * REST controller for managing MarketingNewOrderPlan.
 */
@RestController
@RequestMapping("/api")
public class MarketingNewOrderPlanResource {

    private final Logger log = LoggerFactory.getLogger(MarketingNewOrderPlanResource.class);

    private static final String ENTITY_NAME = "marketingNewOrderPlan";

    private final MarketingNewOrderPlanService marketingNewOrderPlanService;

    private final MarketingNewOrderPlanQueryService marketingNewOrderPlanQueryService;

    public MarketingNewOrderPlanResource(MarketingNewOrderPlanService marketingNewOrderPlanService, MarketingNewOrderPlanQueryService marketingNewOrderPlanQueryService) {
        this.marketingNewOrderPlanService = marketingNewOrderPlanService;
        this.marketingNewOrderPlanQueryService = marketingNewOrderPlanQueryService;
    }

    /**
     * POST  /marketing-new-order-plans : Create a new marketingNewOrderPlan.
     *
     * @param marketingNewOrderPlan the marketingNewOrderPlan to create
     * @return the ResponseEntity with status 201 (Created) and with body the new marketingNewOrderPlan, or with status 400 (Bad Request) if the marketingNewOrderPlan has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/marketing-new-order-plans")
    @Timed
    public ResponseEntity<MarketingNewOrderPlan> createMarketingNewOrderPlan(@RequestBody MarketingNewOrderPlan marketingNewOrderPlan) throws URISyntaxException {
        log.debug("REST request to save MarketingNewOrderPlan : {}", marketingNewOrderPlan);
        if (marketingNewOrderPlan.getId() != null) {

            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new marketingNewOrderPlan cannot already have an ID")).body(null);
        } else {
            marketingNewOrderPlan = marketingNewOrderPlan
                                    .finished(false)
                                    .currentNumber(0)
                                    .percentage(0f);
        }

        MarketingNewOrderPlan result = marketingNewOrderPlanService.save(marketingNewOrderPlan);
        return ResponseEntity.created(new URI("/api/marketing-new-order-plans/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /marketing-new-order-plans : Updates an existing marketingNewOrderPlan.
     *
     * @param marketingNewOrderPlan the marketingNewOrderPlan to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated marketingNewOrderPlan,
     * or with status 400 (Bad Request) if the marketingNewOrderPlan is not valid,
     * or with status 500 (Internal Server Error) if the marketingNewOrderPlan couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/marketing-new-order-plans")
    @Timed
    public ResponseEntity<MarketingNewOrderPlan> updateMarketingNewOrderPlan(@RequestBody MarketingNewOrderPlan marketingNewOrderPlan) throws URISyntaxException {
        log.debug("REST request to update MarketingNewOrderPlan : {}", marketingNewOrderPlan);
        if (marketingNewOrderPlan.getId() == null) {
            return createMarketingNewOrderPlan(marketingNewOrderPlan);
        }
        MarketingNewOrderPlan result = marketingNewOrderPlanService.save(marketingNewOrderPlan);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, marketingNewOrderPlan.getId().toString()))
            .body(result);
    }

    /**
     * GET  /marketing-new-order-plans : get all the marketingNewOrderPlans.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of marketingNewOrderPlans in body
     */
    @GetMapping("/marketing-new-order-plans")
    @Timed
    public ResponseEntity<List<MarketingNewOrderPlan>> getAllMarketingNewOrderPlans(MarketingNewOrderPlanCriteria criteria,@ApiParam Pageable pageable) {
        log.debug("REST request to get MarketingNewOrderPlans by criteria: {}", criteria);
        Page<MarketingNewOrderPlan> page = marketingNewOrderPlanQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/marketing-new-order-plans");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /marketing-new-order-plans/:id : get the "id" marketingNewOrderPlan.
     *
     * @param id the id of the marketingNewOrderPlan to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the marketingNewOrderPlan, or with status 404 (Not Found)
     */
    @GetMapping("/marketing-new-order-plans/{id}")
    @Timed
    public ResponseEntity<MarketingNewOrderPlan> getMarketingNewOrderPlan(@PathVariable Long id) {
        log.debug("REST request to get MarketingNewOrderPlan : {}", id);
        MarketingNewOrderPlan marketingNewOrderPlan = marketingNewOrderPlanService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(marketingNewOrderPlan));
    }

    /**
     * DELETE  /marketing-new-order-plans/:id : delete the "id" marketingNewOrderPlan.
     *
     * @param id the id of the marketingNewOrderPlan to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/marketing-new-order-plans/{id}")
    @Timed
    public ResponseEntity<Void> deleteMarketingNewOrderPlan(@PathVariable Long id) {
        log.debug("REST request to delete MarketingNewOrderPlan : {}", id);
        marketingNewOrderPlanService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
