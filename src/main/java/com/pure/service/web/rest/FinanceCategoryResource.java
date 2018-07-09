package com.pure.service.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pure.service.domain.FinanceCategory;
import com.pure.service.service.FinanceCategoryService;
import com.pure.service.web.rest.util.HeaderUtil;
import com.pure.service.web.rest.util.PaginationUtil;
import com.pure.service.service.dto.FinanceCategoryCriteria;
import com.pure.service.service.FinanceCategoryQueryService;
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
 * REST controller for managing FinanceCategory.
 */
@RestController
@RequestMapping("/api")
public class FinanceCategoryResource {

    private final Logger log = LoggerFactory.getLogger(FinanceCategoryResource.class);

    private static final String ENTITY_NAME = "financeCategory";

    private final FinanceCategoryService financeCategoryService;

    private final FinanceCategoryQueryService financeCategoryQueryService;

    public FinanceCategoryResource(FinanceCategoryService financeCategoryService, FinanceCategoryQueryService financeCategoryQueryService) {
        this.financeCategoryService = financeCategoryService;
        this.financeCategoryQueryService = financeCategoryQueryService;
    }

    /**
     * POST  /finance-categories : Create a new financeCategory.
     *
     * @param financeCategory the financeCategory to create
     * @return the ResponseEntity with status 201 (Created) and with body the new financeCategory, or with status 400 (Bad Request) if the financeCategory has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/finance-categories")
    @Timed
    public ResponseEntity<FinanceCategory> createFinanceCategory(@RequestBody FinanceCategory financeCategory) throws URISyntaxException {
        log.debug("REST request to save FinanceCategory : {}", financeCategory);
        if (financeCategory.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new financeCategory cannot already have an ID")).body(null);
        }
        FinanceCategory result = financeCategoryService.save(financeCategory);
        return ResponseEntity.created(new URI("/api/finance-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /finance-categories : Updates an existing financeCategory.
     *
     * @param financeCategory the financeCategory to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated financeCategory,
     * or with status 400 (Bad Request) if the financeCategory is not valid,
     * or with status 500 (Internal Server Error) if the financeCategory couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/finance-categories")
    @Timed
    public ResponseEntity<FinanceCategory> updateFinanceCategory(@RequestBody FinanceCategory financeCategory) throws URISyntaxException {
        log.debug("REST request to update FinanceCategory : {}", financeCategory);
        if (financeCategory.getId() == null) {
            return createFinanceCategory(financeCategory);
        }
        FinanceCategory result = financeCategoryService.save(financeCategory);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, financeCategory.getId().toString()))
            .body(result);
    }

    /**
     * GET  /finance-categories : get all the financeCategories.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of financeCategories in body
     */
    @GetMapping("/finance-categories")
    @Timed
    public ResponseEntity<List<FinanceCategory>> getAllFinanceCategories(FinanceCategoryCriteria criteria,@ApiParam Pageable pageable) {
        log.debug("REST request to get FinanceCategories by criteria: {}", criteria);
        Page<FinanceCategory> page = financeCategoryQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/finance-categories");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /finance-categories/:id : get the "id" financeCategory.
     *
     * @param id the id of the financeCategory to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the financeCategory, or with status 404 (Not Found)
     */
    @GetMapping("/finance-categories/{id}")
    @Timed
    public ResponseEntity<FinanceCategory> getFinanceCategory(@PathVariable Long id) {
        log.debug("REST request to get FinanceCategory : {}", id);
        FinanceCategory financeCategory = financeCategoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(financeCategory));
    }

    /**
     * DELETE  /finance-categories/:id : delete the "id" financeCategory.
     *
     * @param id the id of the financeCategory to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/finance-categories/{id}")
    @Timed
    public ResponseEntity<Void> deleteFinanceCategory(@PathVariable Long id) {
        log.debug("REST request to delete FinanceCategory : {}", id);
        financeCategoryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
