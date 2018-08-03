package com.pure.service.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pure.service.domain.VistedCustomerStatus;
import com.pure.service.service.VistedCustomerStatusService;
import com.pure.service.web.rest.util.HeaderUtil;
import com.pure.service.web.rest.util.PaginationUtil;
import com.pure.service.service.dto.VistedCustomerStatusCriteria;
import com.pure.service.service.VistedCustomerStatusQueryService;
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
 * REST controller for managing VistedCustomerStatus.
 */
@RestController
@RequestMapping("/api")
public class VistedCustomerStatusResource {

    private final Logger log = LoggerFactory.getLogger(VistedCustomerStatusResource.class);

    private static final String ENTITY_NAME = "vistedCustomerStatus";

    private final VistedCustomerStatusService vistedCustomerStatusService;

    private final VistedCustomerStatusQueryService vistedCustomerStatusQueryService;

    public VistedCustomerStatusResource(VistedCustomerStatusService vistedCustomerStatusService, VistedCustomerStatusQueryService vistedCustomerStatusQueryService) {
        this.vistedCustomerStatusService = vistedCustomerStatusService;
        this.vistedCustomerStatusQueryService = vistedCustomerStatusQueryService;
    }

    /**
     * POST  /visted-customer-statuses : Create a new vistedCustomerStatus.
     *
     * @param vistedCustomerStatus the vistedCustomerStatus to create
     * @return the ResponseEntity with status 201 (Created) and with body the new vistedCustomerStatus, or with status 400 (Bad Request) if the vistedCustomerStatus has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/visted-customer-statuses")
    @Timed
    public ResponseEntity<VistedCustomerStatus> createVistedCustomerStatus(@RequestBody VistedCustomerStatus vistedCustomerStatus) throws URISyntaxException {
        log.debug("REST request to save VistedCustomerStatus : {}", vistedCustomerStatus);
        if (vistedCustomerStatus.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new vistedCustomerStatus cannot already have an ID")).body(null);
        }
        VistedCustomerStatus result = vistedCustomerStatusService.save(vistedCustomerStatus);
        return ResponseEntity.created(new URI("/api/visted-customer-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /visted-customer-statuses : Updates an existing vistedCustomerStatus.
     *
     * @param vistedCustomerStatus the vistedCustomerStatus to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated vistedCustomerStatus,
     * or with status 400 (Bad Request) if the vistedCustomerStatus is not valid,
     * or with status 500 (Internal Server Error) if the vistedCustomerStatus couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/visted-customer-statuses")
    @Timed
    public ResponseEntity<VistedCustomerStatus> updateVistedCustomerStatus(@RequestBody VistedCustomerStatus vistedCustomerStatus) throws URISyntaxException {
        log.debug("REST request to update VistedCustomerStatus : {}", vistedCustomerStatus);
        if (vistedCustomerStatus.getId() == null) {
            return createVistedCustomerStatus(vistedCustomerStatus);
        }
        VistedCustomerStatus result = vistedCustomerStatusService.save(vistedCustomerStatus);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, vistedCustomerStatus.getId().toString()))
            .body(result);
    }

    /**
     * GET  /visted-customer-statuses : get all the vistedCustomerStatuses.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of vistedCustomerStatuses in body
     */
    @GetMapping("/visted-customer-statuses")
    @Timed
    public ResponseEntity<List<VistedCustomerStatus>> getAllVistedCustomerStatuses(VistedCustomerStatusCriteria criteria,@ApiParam Pageable pageable) {
        log.debug("REST request to get VistedCustomerStatuses by criteria: {}", criteria);
        Page<VistedCustomerStatus> page = vistedCustomerStatusQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/visted-customer-statuses");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /visted-customer-statuses/:id : get the "id" vistedCustomerStatus.
     *
     * @param id the id of the vistedCustomerStatus to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the vistedCustomerStatus, or with status 404 (Not Found)
     */
    @GetMapping("/visted-customer-statuses/{id}")
    @Timed
    public ResponseEntity<VistedCustomerStatus> getVistedCustomerStatus(@PathVariable Long id) {
        log.debug("REST request to get VistedCustomerStatus : {}", id);
        VistedCustomerStatus vistedCustomerStatus = vistedCustomerStatusService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(vistedCustomerStatus));
    }

    /**
     * DELETE  /visted-customer-statuses/:id : delete the "id" vistedCustomerStatus.
     *
     * @param id the id of the vistedCustomerStatus to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/visted-customer-statuses/{id}")
    @Timed
    public ResponseEntity<Void> deleteVistedCustomerStatus(@PathVariable Long id) {
        log.debug("REST request to delete VistedCustomerStatus : {}", id);
        vistedCustomerStatusService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
