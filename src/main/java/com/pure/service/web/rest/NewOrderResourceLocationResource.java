package com.pure.service.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pure.service.domain.NewOrderResourceLocation;
import com.pure.service.region.RegionBasedInsert;
import com.pure.service.region.RegionBasedQuery;
import com.pure.service.service.NewOrderResourceLocationService;
import com.pure.service.web.rest.util.HeaderUtil;
import com.pure.service.web.rest.util.PaginationUtil;
import com.pure.service.service.dto.NewOrderResourceLocationCriteria;
import com.pure.service.service.NewOrderResourceLocationQueryService;
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
 * REST controller for managing NewOrderResourceLocation.
 */
@RestController
@RequestMapping("/api")
public class NewOrderResourceLocationResource {

    private final Logger log = LoggerFactory.getLogger(NewOrderResourceLocationResource.class);

    private static final String ENTITY_NAME = "newOrderResourceLocation";

    private final NewOrderResourceLocationService newOrderResourceLocationService;

    private final NewOrderResourceLocationQueryService newOrderResourceLocationQueryService;

    public NewOrderResourceLocationResource(NewOrderResourceLocationService newOrderResourceLocationService, NewOrderResourceLocationQueryService newOrderResourceLocationQueryService) {
        this.newOrderResourceLocationService = newOrderResourceLocationService;
        this.newOrderResourceLocationQueryService = newOrderResourceLocationQueryService;
    }

    /**
     * POST  /new-order-resource-locations : Create a new newOrderResourceLocation.
     *
     * @param newOrderResourceLocation the newOrderResourceLocation to create
     * @return the ResponseEntity with status 201 (Created) and with body the new newOrderResourceLocation, or with status 400 (Bad Request) if the newOrderResourceLocation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/new-order-resource-locations")
    @Timed
    @RegionBasedInsert
    public ResponseEntity<NewOrderResourceLocation> createNewOrderResourceLocation(@RequestBody NewOrderResourceLocation newOrderResourceLocation) throws URISyntaxException {
        log.debug("REST request to save NewOrderResourceLocation : {}", newOrderResourceLocation);
        if (newOrderResourceLocation.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new newOrderResourceLocation cannot already have an ID")).body(null);
        }
        NewOrderResourceLocation result = newOrderResourceLocationService.save(newOrderResourceLocation);
        return ResponseEntity.created(new URI("/api/new-order-resource-locations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /new-order-resource-locations : Updates an existing newOrderResourceLocation.
     *
     * @param newOrderResourceLocation the newOrderResourceLocation to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated newOrderResourceLocation,
     * or with status 400 (Bad Request) if the newOrderResourceLocation is not valid,
     * or with status 500 (Internal Server Error) if the newOrderResourceLocation couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/new-order-resource-locations")
    @Timed
    public ResponseEntity<NewOrderResourceLocation> updateNewOrderResourceLocation(@RequestBody NewOrderResourceLocation newOrderResourceLocation) throws URISyntaxException {
        log.debug("REST request to update NewOrderResourceLocation : {}", newOrderResourceLocation);
        if (newOrderResourceLocation.getId() == null) {
            return createNewOrderResourceLocation(newOrderResourceLocation);
        }
        NewOrderResourceLocation result = newOrderResourceLocationService.save(newOrderResourceLocation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, newOrderResourceLocation.getId().toString()))
            .body(result);
    }

    /**
     * GET  /new-order-resource-locations : get all the newOrderResourceLocations.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of newOrderResourceLocations in body
     */
    @GetMapping("/new-order-resource-locations")
    @Timed
    @RegionBasedQuery
    public ResponseEntity<List<NewOrderResourceLocation>> getAllNewOrderResourceLocations(NewOrderResourceLocationCriteria criteria,@ApiParam Pageable pageable) {
        log.debug("REST request to get NewOrderResourceLocations by criteria: {}", criteria);
        Page<NewOrderResourceLocation> page = newOrderResourceLocationQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/new-order-resource-locations");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /new-order-resource-locations/:id : get the "id" newOrderResourceLocation.
     *
     * @param id the id of the newOrderResourceLocation to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the newOrderResourceLocation, or with status 404 (Not Found)
     */
    @GetMapping("/new-order-resource-locations/{id}")
    @Timed
    public ResponseEntity<NewOrderResourceLocation> getNewOrderResourceLocation(@PathVariable Long id) {
        log.debug("REST request to get NewOrderResourceLocation : {}", id);
        NewOrderResourceLocation newOrderResourceLocation = newOrderResourceLocationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(newOrderResourceLocation));
    }

    /**
     * DELETE  /new-order-resource-locations/:id : delete the "id" newOrderResourceLocation.
     *
     * @param id the id of the newOrderResourceLocation to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/new-order-resource-locations/{id}")
    @Timed
    public ResponseEntity<Void> deleteNewOrderResourceLocation(@PathVariable Long id) {
        log.debug("REST request to delete NewOrderResourceLocation : {}", id);
        newOrderResourceLocationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
