package com.pure.service.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pure.service.domain.Collection;
import com.pure.service.service.CollectionService;
import com.pure.service.web.rest.util.HeaderUtil;
import com.pure.service.web.rest.util.PaginationUtil;
import com.pure.service.service.dto.CollectionCriteria;
import com.pure.service.service.CollectionQueryService;
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
 * REST controller for managing Collection.
 */
@RestController
@RequestMapping("/api")
public class CollectionResource {

    private final Logger log = LoggerFactory.getLogger(CollectionResource.class);

    private static final String ENTITY_NAME = "collection";

    private final CollectionService collectionService;

    private final CollectionQueryService collectionQueryService;

    public CollectionResource(CollectionService collectionService, CollectionQueryService collectionQueryService) {
        this.collectionService = collectionService;
        this.collectionQueryService = collectionQueryService;
    }

    /**
     * POST  /collections : Create a new collection.
     *
     * @param collection the collection to create
     * @return the ResponseEntity with status 201 (Created) and with body the new collection, or with status 400 (Bad Request) if the collection has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/collections")
    @Timed
    public ResponseEntity<Collection> createCollection(@RequestBody Collection collection) throws URISyntaxException {
        log.debug("REST request to save Collection : {}", collection);
        if (collection.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new collection cannot already have an ID")).body(null);
        }
        Collection result = collectionService.save(collection);
        return ResponseEntity.created(new URI("/api/collections/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PostMapping("/collections/confirm")
    @Timed
    public ResponseEntity<Collection> confirmCustomerCollection(@RequestBody Collection collection) throws URISyntaxException {
        log.debug("REST request to save Collection : {}", collection);
        if (collection.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new collection cannot already have an ID")).body(null);
        }

        Collection result = collectionService.save(collection);
        return ResponseEntity.created(new URI("/api/collections/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }



    /**
     * PUT  /collections : Updates an existing collection.
     *
     * @param collection the collection to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated collection,
     * or with status 400 (Bad Request) if the collection is not valid,
     * or with status 500 (Internal Server Error) if the collection couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/collections")
    @Timed
    public ResponseEntity<Collection> updateCollection(@RequestBody Collection collection) throws URISyntaxException {
        log.debug("REST request to update Collection : {}", collection);
        if (collection.getId() == null) {
            return createCollection(collection);
        }
        Collection result = collectionService.save(collection);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, collection.getId().toString()))
            .body(result);
    }

    /**
     * GET  /collections : get all the collections.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of collections in body
     */
    @GetMapping("/collections")
    @Timed
    public ResponseEntity<List<Collection>> getAllCollections(CollectionCriteria criteria,@ApiParam Pageable pageable) {
        log.debug("REST request to get Collections by criteria: {}", criteria);
        Page<Collection> page = collectionQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/collections");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /collections/:id : get the "id" collection.
     *
     * @param id the id of the collection to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the collection, or with status 404 (Not Found)
     */
    @GetMapping("/collections/{id}")
    @Timed
    public ResponseEntity<Collection> getCollection(@PathVariable Long id) {
        log.debug("REST request to get Collection : {}", id);
        Collection collection = collectionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(collection));
    }

    /**
     * DELETE  /collections/:id : delete the "id" collection.
     *
     * @param id the id of the collection to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/collections/{id}")
    @Timed
    public ResponseEntity<Void> deleteCollection(@PathVariable Long id) {
        log.debug("REST request to delete Collection : {}", id);
        collectionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
