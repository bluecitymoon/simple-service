package com.pure.service.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pure.service.domain.FreeClassPlan;
import com.pure.service.region.RegionBasedInsert;
import com.pure.service.region.RegionBasedQuery;
import com.pure.service.service.FreeClassPlanService;
import com.pure.service.web.rest.util.HeaderUtil;
import com.pure.service.web.rest.util.PaginationUtil;
import com.pure.service.service.dto.FreeClassPlanCriteria;
import com.pure.service.service.FreeClassPlanQueryService;
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
 * REST controller for managing FreeClassPlan.
 */
@RestController
@RequestMapping("/api")
public class FreeClassPlanResource {

    private final Logger log = LoggerFactory.getLogger(FreeClassPlanResource.class);

    private static final String ENTITY_NAME = "freeClassPlan";

    private final FreeClassPlanService freeClassPlanService;

    private final FreeClassPlanQueryService freeClassPlanQueryService;

    public FreeClassPlanResource(FreeClassPlanService freeClassPlanService, FreeClassPlanQueryService freeClassPlanQueryService) {
        this.freeClassPlanService = freeClassPlanService;
        this.freeClassPlanQueryService = freeClassPlanQueryService;
    }

    /**
     * POST  /free-class-plans : Create a new freeClassPlan.
     *
     * @param freeClassPlan the freeClassPlan to create
     * @return the ResponseEntity with status 201 (Created) and with body the new freeClassPlan, or with status 400 (Bad Request) if the freeClassPlan has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/free-class-plans")
    @Timed
    @RegionBasedInsert
    public ResponseEntity<FreeClassPlan> createFreeClassPlan(@RequestBody FreeClassPlan freeClassPlan) throws URISyntaxException {
        log.debug("REST request to save FreeClassPlan : {}", freeClassPlan);
        if (freeClassPlan.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new freeClassPlan cannot already have an ID")).body(null);
        }
        FreeClassPlan result = freeClassPlanService.save(freeClassPlan);
        return ResponseEntity.created(new URI("/api/free-class-plans/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /free-class-plans : Updates an existing freeClassPlan.
     *
     * @param freeClassPlan the freeClassPlan to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated freeClassPlan,
     * or with status 400 (Bad Request) if the freeClassPlan is not valid,
     * or with status 500 (Internal Server Error) if the freeClassPlan couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/free-class-plans")
    @Timed
    public ResponseEntity<FreeClassPlan> updateFreeClassPlan(@RequestBody FreeClassPlan freeClassPlan) throws URISyntaxException {
        log.debug("REST request to update FreeClassPlan : {}", freeClassPlan);
        if (freeClassPlan.getId() == null) {
            return createFreeClassPlan(freeClassPlan);
        }
        FreeClassPlan result = freeClassPlanService.save(freeClassPlan);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, freeClassPlan.getId().toString()))
            .body(result);
    }

    /**
     * GET  /free-class-plans : get all the freeClassPlans.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of freeClassPlans in body
     */
    @GetMapping("/free-class-plans")
    @Timed
    @RegionBasedQuery
    public ResponseEntity<List<FreeClassPlan>> getAllFreeClassPlans(FreeClassPlanCriteria criteria,@ApiParam Pageable pageable) {
        log.debug("REST request to get FreeClassPlans by criteria: {}", criteria);
        Page<FreeClassPlan> page = freeClassPlanQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/free-class-plans");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /free-class-plans/:id : get the "id" freeClassPlan.
     *
     * @param id the id of the freeClassPlan to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the freeClassPlan, or with status 404 (Not Found)
     */
    @GetMapping("/free-class-plans/{id}")
    @Timed
    public ResponseEntity<FreeClassPlan> getFreeClassPlan(@PathVariable Long id) {
        log.debug("REST request to get FreeClassPlan : {}", id);
        FreeClassPlan freeClassPlan = freeClassPlanService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(freeClassPlan));
    }

    /**
     * DELETE  /free-class-plans/:id : delete the "id" freeClassPlan.
     *
     * @param id the id of the freeClassPlan to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/free-class-plans/{id}")
    @Timed
    public ResponseEntity<Void> deleteFreeClassPlan(@PathVariable Long id) {
        log.debug("REST request to delete FreeClassPlan : {}", id);
        freeClassPlanService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
