package com.pure.service.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pure.service.domain.SystemVariable;
import com.pure.service.region.RegionBasedInsert;
import com.pure.service.region.RegionBasedQuery;
import com.pure.service.service.SystemVariableService;
import com.pure.service.web.rest.util.HeaderUtil;
import com.pure.service.web.rest.util.PaginationUtil;
import com.pure.service.service.dto.SystemVariableCriteria;
import com.pure.service.service.SystemVariableQueryService;
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
 * REST controller for managing SystemVariable.
 */
@RestController
@RequestMapping("/api")
public class SystemVariableResource {

    private final Logger log = LoggerFactory.getLogger(SystemVariableResource.class);

    private static final String ENTITY_NAME = "systemVariable";

    private final SystemVariableService systemVariableService;

    private final SystemVariableQueryService systemVariableQueryService;

    public SystemVariableResource(SystemVariableService systemVariableService, SystemVariableQueryService systemVariableQueryService) {
        this.systemVariableService = systemVariableService;
        this.systemVariableQueryService = systemVariableQueryService;
    }

    /**
     * POST  /system-variables : Create a new systemVariable.
     *
     * @param systemVariable the systemVariable to create
     * @return the ResponseEntity with status 201 (Created) and with body the new systemVariable, or with status 400 (Bad Request) if the systemVariable has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/system-variables")
    @Timed
    @RegionBasedInsert
    public ResponseEntity<SystemVariable> createSystemVariable(@RequestBody SystemVariable systemVariable) throws URISyntaxException {
        log.debug("REST request to save SystemVariable : {}", systemVariable);
        if (systemVariable.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new systemVariable cannot already have an ID")).body(null);
        }
        SystemVariable result = systemVariableService.save(systemVariable);
        return ResponseEntity.created(new URI("/api/system-variables/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /system-variables : Updates an existing systemVariable.
     *
     * @param systemVariable the systemVariable to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated systemVariable,
     * or with status 400 (Bad Request) if the systemVariable is not valid,
     * or with status 500 (Internal Server Error) if the systemVariable couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/system-variables")
    @Timed
    public ResponseEntity<SystemVariable> updateSystemVariable(@RequestBody SystemVariable systemVariable) throws URISyntaxException {
        log.debug("REST request to update SystemVariable : {}", systemVariable);
        if (systemVariable.getId() == null) {
            return createSystemVariable(systemVariable);
        }
        SystemVariable result = systemVariableService.save(systemVariable);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, systemVariable.getId().toString()))
            .body(result);
    }

    /**
     * GET  /system-variables : get all the systemVariables.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of systemVariables in body
     */
    @GetMapping("/system-variables")
    @Timed
    @RegionBasedQuery
    public ResponseEntity<List<SystemVariable>> getAllSystemVariables(SystemVariableCriteria criteria,@ApiParam Pageable pageable) {
        log.debug("REST request to get SystemVariables by criteria: {}", criteria);
        Page<SystemVariable> page = systemVariableQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/system-variables");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /system-variables/:id : get the "id" systemVariable.
     *
     * @param id the id of the systemVariable to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the systemVariable, or with status 404 (Not Found)
     */
    @GetMapping("/system-variables/{id}")
    @Timed
    public ResponseEntity<SystemVariable> getSystemVariable(@PathVariable Long id) {
        log.debug("REST request to get SystemVariable : {}", id);
        SystemVariable systemVariable = systemVariableService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(systemVariable));
    }

    /**
     * DELETE  /system-variables/:id : delete the "id" systemVariable.
     *
     * @param id the id of the systemVariable to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/system-variables/{id}")
    @Timed
    public ResponseEntity<Void> deleteSystemVariable(@PathVariable Long id) {
        log.debug("REST request to delete SystemVariable : {}", id);
        systemVariableService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
