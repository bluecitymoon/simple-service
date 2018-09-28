package com.pure.service.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pure.service.domain.TimetableItem;
import com.pure.service.service.TimetableItemService;
import com.pure.service.web.rest.util.HeaderUtil;
import com.pure.service.web.rest.util.PaginationUtil;
import com.pure.service.service.dto.TimetableItemCriteria;
import com.pure.service.service.TimetableItemQueryService;
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
 * REST controller for managing TimetableItem.
 */
@RestController
@RequestMapping("/api")
public class TimetableItemResource {

    private final Logger log = LoggerFactory.getLogger(TimetableItemResource.class);

    private static final String ENTITY_NAME = "timetableItem";

    private final TimetableItemService timetableItemService;

    private final TimetableItemQueryService timetableItemQueryService;

    public TimetableItemResource(TimetableItemService timetableItemService, TimetableItemQueryService timetableItemQueryService) {
        this.timetableItemService = timetableItemService;
        this.timetableItemQueryService = timetableItemQueryService;
    }

    /**
     * POST  /timetable-items : Create a new timetableItem.
     *
     * @param timetableItem the timetableItem to create
     * @return the ResponseEntity with status 201 (Created) and with body the new timetableItem, or with status 400 (Bad Request) if the timetableItem has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/timetable-items")
    @Timed
    public ResponseEntity<TimetableItem> createTimetableItem(@RequestBody TimetableItem timetableItem) throws URISyntaxException {
        log.debug("REST request to save TimetableItem : {}", timetableItem);
        if (timetableItem.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new timetableItem cannot already have an ID")).body(null);
        }
        TimetableItem result = timetableItemService.save(timetableItem);
        return ResponseEntity.created(new URI("/api/timetable-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /timetable-items : Updates an existing timetableItem.
     *
     * @param timetableItem the timetableItem to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated timetableItem,
     * or with status 400 (Bad Request) if the timetableItem is not valid,
     * or with status 500 (Internal Server Error) if the timetableItem couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/timetable-items")
    @Timed
    public ResponseEntity<TimetableItem> updateTimetableItem(@RequestBody TimetableItem timetableItem) throws URISyntaxException {
        log.debug("REST request to update TimetableItem : {}", timetableItem);
        if (timetableItem.getId() == null) {
            return createTimetableItem(timetableItem);
        }
        TimetableItem result = timetableItemService.save(timetableItem);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, timetableItem.getId().toString()))
            .body(result);
    }

    /**
     * GET  /timetable-items : get all the timetableItems.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of timetableItems in body
     */
    @GetMapping("/timetable-items")
    @Timed
    public ResponseEntity<List<TimetableItem>> getAllTimetableItems(TimetableItemCriteria criteria,@ApiParam Pageable pageable) {
        log.debug("REST request to get TimetableItems by criteria: {}", criteria);
        Page<TimetableItem> page = timetableItemQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/timetable-items");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /timetable-items/:id : get the "id" timetableItem.
     *
     * @param id the id of the timetableItem to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the timetableItem, or with status 404 (Not Found)
     */
    @GetMapping("/timetable-items/{id}")
    @Timed
    public ResponseEntity<TimetableItem> getTimetableItem(@PathVariable Long id) {
        log.debug("REST request to get TimetableItem : {}", id);
        TimetableItem timetableItem = timetableItemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(timetableItem));
    }

    /**
     * DELETE  /timetable-items/:id : delete the "id" timetableItem.
     *
     * @param id the id of the timetableItem to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/timetable-items/{id}")
    @Timed
    public ResponseEntity<Void> deleteTimetableItem(@PathVariable Long id) {
        log.debug("REST request to delete TimetableItem : {}", id);
        timetableItemService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
