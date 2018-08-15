package com.pure.service.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pure.service.domain.ClassArrangementStatus;
import com.pure.service.service.ClassArrangementStatusService;
import com.pure.service.web.rest.util.HeaderUtil;
import com.pure.service.web.rest.util.PaginationUtil;
import com.pure.service.service.dto.ClassArrangementStatusCriteria;
import com.pure.service.service.ClassArrangementStatusQueryService;
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
 * REST controller for managing ClassArrangementStatus.
 */
@RestController
@RequestMapping("/api")
public class ClassArrangementStatusResource {

    private final Logger log = LoggerFactory.getLogger(ClassArrangementStatusResource.class);

    private static final String ENTITY_NAME = "classArrangementStatus";

    private final ClassArrangementStatusService classArrangementStatusService;

    private final ClassArrangementStatusQueryService classArrangementStatusQueryService;

    public ClassArrangementStatusResource(ClassArrangementStatusService classArrangementStatusService, ClassArrangementStatusQueryService classArrangementStatusQueryService) {
        this.classArrangementStatusService = classArrangementStatusService;
        this.classArrangementStatusQueryService = classArrangementStatusQueryService;
    }

    /**
     * POST  /class-arrangement-statuses : Create a new classArrangementStatus.
     *
     * @param classArrangementStatus the classArrangementStatus to create
     * @return the ResponseEntity with status 201 (Created) and with body the new classArrangementStatus, or with status 400 (Bad Request) if the classArrangementStatus has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/class-arrangement-statuses")
    @Timed
    public ResponseEntity<ClassArrangementStatus> createClassArrangementStatus(@RequestBody ClassArrangementStatus classArrangementStatus) throws URISyntaxException {
        log.debug("REST request to save ClassArrangementStatus : {}", classArrangementStatus);
        if (classArrangementStatus.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new classArrangementStatus cannot already have an ID")).body(null);
        }
        ClassArrangementStatus result = classArrangementStatusService.save(classArrangementStatus);
        return ResponseEntity.created(new URI("/api/class-arrangement-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /class-arrangement-statuses : Updates an existing classArrangementStatus.
     *
     * @param classArrangementStatus the classArrangementStatus to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated classArrangementStatus,
     * or with status 400 (Bad Request) if the classArrangementStatus is not valid,
     * or with status 500 (Internal Server Error) if the classArrangementStatus couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/class-arrangement-statuses")
    @Timed
    public ResponseEntity<ClassArrangementStatus> updateClassArrangementStatus(@RequestBody ClassArrangementStatus classArrangementStatus) throws URISyntaxException {
        log.debug("REST request to update ClassArrangementStatus : {}", classArrangementStatus);
        if (classArrangementStatus.getId() == null) {
            return createClassArrangementStatus(classArrangementStatus);
        }
        ClassArrangementStatus result = classArrangementStatusService.save(classArrangementStatus);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, classArrangementStatus.getId().toString()))
            .body(result);
    }

    /**
     * GET  /class-arrangement-statuses : get all the classArrangementStatuses.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of classArrangementStatuses in body
     */
    @GetMapping("/class-arrangement-statuses")
    @Timed
    public ResponseEntity<List<ClassArrangementStatus>> getAllClassArrangementStatuses(ClassArrangementStatusCriteria criteria,@ApiParam Pageable pageable) {
        log.debug("REST request to get ClassArrangementStatuses by criteria: {}", criteria);
        Page<ClassArrangementStatus> page = classArrangementStatusQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/class-arrangement-statuses");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /class-arrangement-statuses/:id : get the "id" classArrangementStatus.
     *
     * @param id the id of the classArrangementStatus to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the classArrangementStatus, or with status 404 (Not Found)
     */
    @GetMapping("/class-arrangement-statuses/{id}")
    @Timed
    public ResponseEntity<ClassArrangementStatus> getClassArrangementStatus(@PathVariable Long id) {
        log.debug("REST request to get ClassArrangementStatus : {}", id);
        ClassArrangementStatus classArrangementStatus = classArrangementStatusService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(classArrangementStatus));
    }

    /**
     * DELETE  /class-arrangement-statuses/:id : delete the "id" classArrangementStatus.
     *
     * @param id the id of the classArrangementStatus to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/class-arrangement-statuses/{id}")
    @Timed
    public ResponseEntity<Void> deleteClassArrangementStatus(@PathVariable Long id) {
        log.debug("REST request to delete ClassArrangementStatus : {}", id);
        classArrangementStatusService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
