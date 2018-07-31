package com.pure.service.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pure.service.domain.CollectionStatus;
import com.pure.service.service.CollectionStatusService;
import com.pure.service.web.rest.util.HeaderUtil;
import com.pure.service.web.rest.util.PaginationUtil;
import com.pure.service.service.dto.CollectionStatusCriteria;
import com.pure.service.service.CollectionStatusQueryService;
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
 * REST controller for managing CollectionStatus.
 */
@RestController
@RequestMapping("/api")
public class CollectionStatusResource {

    private final Logger log = LoggerFactory.getLogger(CollectionStatusResource.class);

    private static final String ENTITY_NAME = "collectionStatus";

    private final CollectionStatusService collectionStatusService;

    private final CollectionStatusQueryService collectionStatusQueryService;

    public CollectionStatusResource(CollectionStatusService collectionStatusService, CollectionStatusQueryService collectionStatusQueryService) {
        this.collectionStatusService = collectionStatusService;
        this.collectionStatusQueryService = collectionStatusQueryService;
    }

    /**
     * POST  /collection-statuses : Create a new collectionStatus.
     *
     * @param collectionStatus the collectionStatus to create
     * @return the ResponseEntity with status 201 (Created) and with body the new collectionStatus, or with status 400 (Bad Request) if the collectionStatus has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/collection-statuses")
    @Timed
    public ResponseEntity<CollectionStatus> createCollectionStatus(@RequestBody CollectionStatus collectionStatus) throws URISyntaxException {
        log.debug("REST request to save CollectionStatus : {}", collectionStatus);
        if (collectionStatus.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new collectionStatus cannot already have an ID")).body(null);
        }
        CollectionStatus result = collectionStatusService.save(collectionStatus);
        return ResponseEntity.created(new URI("/api/collection-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /collection-statuses : Updates an existing collectionStatus.
     *
     * @param collectionStatus the collectionStatus to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated collectionStatus,
     * or with status 400 (Bad Request) if the collectionStatus is not valid,
     * or with status 500 (Internal Server Error) if the collectionStatus couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/collection-statuses")
    @Timed
    public ResponseEntity<CollectionStatus> updateCollectionStatus(@RequestBody CollectionStatus collectionStatus) throws URISyntaxException {
        log.debug("REST request to update CollectionStatus : {}", collectionStatus);
        if (collectionStatus.getId() == null) {
            return createCollectionStatus(collectionStatus);
        }
        CollectionStatus result = collectionStatusService.save(collectionStatus);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, collectionStatus.getId().toString()))
            .body(result);
    }

    /**
     * GET  /collection-statuses : get all the collectionStatuses.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of collectionStatuses in body
     */
    @GetMapping("/collection-statuses")
    @Timed
    public ResponseEntity<List<CollectionStatus>> getAllCollectionStatuses(CollectionStatusCriteria criteria,@ApiParam Pageable pageable) {
        log.debug("REST request to get CollectionStatuses by criteria: {}", criteria);
        Page<CollectionStatus> page = collectionStatusQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/collection-statuses");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /collection-statuses/:id : get the "id" collectionStatus.
     *
     * @param id the id of the collectionStatus to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the collectionStatus, or with status 404 (Not Found)
     */
    @GetMapping("/collection-statuses/{id}")
    @Timed
    public ResponseEntity<CollectionStatus> getCollectionStatus(@PathVariable Long id) {
        log.debug("REST request to get CollectionStatus : {}", id);
        CollectionStatus collectionStatus = collectionStatusService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(collectionStatus));
    }

    /**
     * DELETE  /collection-statuses/:id : delete the "id" collectionStatus.
     *
     * @param id the id of the collectionStatus to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/collection-statuses/{id}")
    @Timed
    public ResponseEntity<Void> deleteCollectionStatus(@PathVariable Long id) {
        log.debug("REST request to delete CollectionStatus : {}", id);
        collectionStatusService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
