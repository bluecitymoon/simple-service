package com.pure.service.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pure.service.domain.ClassArrangement;
import com.pure.service.service.ClassArrangementService;
import com.pure.service.web.rest.util.HeaderUtil;
import com.pure.service.web.rest.util.PaginationUtil;
import com.pure.service.service.dto.ClassArrangementCriteria;
import com.pure.service.service.ClassArrangementQueryService;
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
 * REST controller for managing ClassArrangement.
 */
@RestController
@RequestMapping("/api")
public class ClassArrangementResource {

    private final Logger log = LoggerFactory.getLogger(ClassArrangementResource.class);

    private static final String ENTITY_NAME = "classArrangement";

    private final ClassArrangementService classArrangementService;

    private final ClassArrangementQueryService classArrangementQueryService;

    public ClassArrangementResource(ClassArrangementService classArrangementService, ClassArrangementQueryService classArrangementQueryService) {
        this.classArrangementService = classArrangementService;
        this.classArrangementQueryService = classArrangementQueryService;
    }

    /**
     * POST  /class-arrangements : Create a new classArrangement.
     *
     * @param classArrangement the classArrangement to create
     * @return the ResponseEntity with status 201 (Created) and with body the new classArrangement, or with status 400 (Bad Request) if the classArrangement has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/class-arrangements")
    @Timed
    public ResponseEntity<ClassArrangement> createClassArrangement(@RequestBody ClassArrangement classArrangement) throws URISyntaxException {
        log.debug("REST request to save ClassArrangement : {}", classArrangement);
        if (classArrangement.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new classArrangement cannot already have an ID")).body(null);
        }
        ClassArrangement result = classArrangementService.save(classArrangement);
        return ResponseEntity.created(new URI("/api/class-arrangements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /class-arrangements : Updates an existing classArrangement.
     *
     * @param classArrangement the classArrangement to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated classArrangement,
     * or with status 400 (Bad Request) if the classArrangement is not valid,
     * or with status 500 (Internal Server Error) if the classArrangement couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/class-arrangements")
    @Timed
    public ResponseEntity<ClassArrangement> updateClassArrangement(@RequestBody ClassArrangement classArrangement) throws URISyntaxException {
        log.debug("REST request to update ClassArrangement : {}", classArrangement);
        if (classArrangement.getId() == null) {
            return createClassArrangement(classArrangement);
        }
        ClassArrangement result = classArrangementService.save(classArrangement);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, classArrangement.getId().toString()))
            .body(result);
    }

    /**
     * GET  /class-arrangements : get all the classArrangements.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of classArrangements in body
     */
    @GetMapping("/class-arrangements")
    @Timed
    public ResponseEntity<List<ClassArrangement>> getAllClassArrangements(ClassArrangementCriteria criteria,@ApiParam Pageable pageable) {
        log.debug("REST request to get ClassArrangements by criteria: {}", criteria);
        Page<ClassArrangement> page = classArrangementQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/class-arrangements");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /class-arrangements/:id : get the "id" classArrangement.
     *
     * @param id the id of the classArrangement to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the classArrangement, or with status 404 (Not Found)
     */
    @GetMapping("/class-arrangements/{id}")
    @Timed
    public ResponseEntity<ClassArrangement> getClassArrangement(@PathVariable Long id) {
        log.debug("REST request to get ClassArrangement : {}", id);
        ClassArrangement classArrangement = classArrangementService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(classArrangement));
    }

    /**
     * DELETE  /class-arrangements/:id : delete the "id" classArrangement.
     *
     * @param id the id of the classArrangement to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/class-arrangements/{id}")
    @Timed
    public ResponseEntity<Void> deleteClassArrangement(@PathVariable Long id) {
        log.debug("REST request to delete ClassArrangement : {}", id);
        classArrangementService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}