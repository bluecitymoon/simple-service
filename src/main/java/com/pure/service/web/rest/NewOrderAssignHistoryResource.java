package com.pure.service.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pure.service.domain.NewOrderAssignHistory;
import com.pure.service.service.NewOrderAssignHistoryService;
import com.pure.service.web.rest.util.HeaderUtil;
import com.pure.service.web.rest.util.PaginationUtil;
import com.pure.service.service.dto.NewOrderAssignHistoryCriteria;
import com.pure.service.service.NewOrderAssignHistoryQueryService;
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
 * REST controller for managing NewOrderAssignHistory.
 */
@RestController
@RequestMapping("/api")
public class NewOrderAssignHistoryResource {

    private final Logger log = LoggerFactory.getLogger(NewOrderAssignHistoryResource.class);

    private static final String ENTITY_NAME = "newOrderAssignHistory";

    private final NewOrderAssignHistoryService newOrderAssignHistoryService;

    private final NewOrderAssignHistoryQueryService newOrderAssignHistoryQueryService;

    public NewOrderAssignHistoryResource(NewOrderAssignHistoryService newOrderAssignHistoryService, NewOrderAssignHistoryQueryService newOrderAssignHistoryQueryService) {
        this.newOrderAssignHistoryService = newOrderAssignHistoryService;
        this.newOrderAssignHistoryQueryService = newOrderAssignHistoryQueryService;
    }

    /**
     * POST  /new-order-assign-histories : Create a new newOrderAssignHistory.
     *
     * @param newOrderAssignHistory the newOrderAssignHistory to create
     * @return the ResponseEntity with status 201 (Created) and with body the new newOrderAssignHistory, or with status 400 (Bad Request) if the newOrderAssignHistory has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/new-order-assign-histories")
    @Timed
    public ResponseEntity<NewOrderAssignHistory> createNewOrderAssignHistory(@RequestBody NewOrderAssignHistory newOrderAssignHistory) throws URISyntaxException {
        log.debug("REST request to save NewOrderAssignHistory : {}", newOrderAssignHistory);
        if (newOrderAssignHistory.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new newOrderAssignHistory cannot already have an ID")).body(null);
        }
        NewOrderAssignHistory result = newOrderAssignHistoryService.save(newOrderAssignHistory);
        return ResponseEntity.created(new URI("/api/new-order-assign-histories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /new-order-assign-histories : Updates an existing newOrderAssignHistory.
     *
     * @param newOrderAssignHistory the newOrderAssignHistory to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated newOrderAssignHistory,
     * or with status 400 (Bad Request) if the newOrderAssignHistory is not valid,
     * or with status 500 (Internal Server Error) if the newOrderAssignHistory couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/new-order-assign-histories")
    @Timed
    public ResponseEntity<NewOrderAssignHistory> updateNewOrderAssignHistory(@RequestBody NewOrderAssignHistory newOrderAssignHistory) throws URISyntaxException {
        log.debug("REST request to update NewOrderAssignHistory : {}", newOrderAssignHistory);
        if (newOrderAssignHistory.getId() == null) {
            return createNewOrderAssignHistory(newOrderAssignHistory);
        }
        NewOrderAssignHistory result = newOrderAssignHistoryService.save(newOrderAssignHistory);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, newOrderAssignHistory.getId().toString()))
            .body(result);
    }

    /**
     * GET  /new-order-assign-histories : get all the newOrderAssignHistories.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of newOrderAssignHistories in body
     */
    @GetMapping("/new-order-assign-histories")
    @Timed
    public ResponseEntity<List<NewOrderAssignHistory>> getAllNewOrderAssignHistories(NewOrderAssignHistoryCriteria criteria,@ApiParam Pageable pageable) {
        log.debug("REST request to get NewOrderAssignHistories by criteria: {}", criteria);
        Page<NewOrderAssignHistory> page = newOrderAssignHistoryQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/new-order-assign-histories");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /new-order-assign-histories/:id : get the "id" newOrderAssignHistory.
     *
     * @param id the id of the newOrderAssignHistory to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the newOrderAssignHistory, or with status 404 (Not Found)
     */
    @GetMapping("/new-order-assign-histories/{id}")
    @Timed
    public ResponseEntity<NewOrderAssignHistory> getNewOrderAssignHistory(@PathVariable Long id) {
        log.debug("REST request to get NewOrderAssignHistory : {}", id);
        NewOrderAssignHistory newOrderAssignHistory = newOrderAssignHistoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(newOrderAssignHistory));
    }

    /**
     * DELETE  /new-order-assign-histories/:id : delete the "id" newOrderAssignHistory.
     *
     * @param id the id of the newOrderAssignHistory to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/new-order-assign-histories/{id}")
    @Timed
    public ResponseEntity<Void> deleteNewOrderAssignHistory(@PathVariable Long id) {
        log.debug("REST request to delete NewOrderAssignHistory : {}", id);
        newOrderAssignHistoryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
