package com.pure.service.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pure.service.domain.ClassStatus;
import com.pure.service.service.ClassStatusService;
import com.pure.service.web.rest.util.HeaderUtil;
import com.pure.service.web.rest.util.PaginationUtil;
import com.pure.service.service.dto.ClassStatusCriteria;
import com.pure.service.service.ClassStatusQueryService;
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
 * REST controller for managing ClassStatus.
 */
@RestController
@RequestMapping("/api")
public class ClassStatusResource {

    private final Logger log = LoggerFactory.getLogger(ClassStatusResource.class);

    private static final String ENTITY_NAME = "classStatus";

    private final ClassStatusService classStatusService;

    private final ClassStatusQueryService classStatusQueryService;

    public ClassStatusResource(ClassStatusService classStatusService, ClassStatusQueryService classStatusQueryService) {
        this.classStatusService = classStatusService;
        this.classStatusQueryService = classStatusQueryService;
    }

    /**
     * POST  /class-statuses : Create a new classStatus.
     *
     * @param classStatus the classStatus to create
     * @return the ResponseEntity with status 201 (Created) and with body the new classStatus, or with status 400 (Bad Request) if the classStatus has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/class-statuses")
    @Timed
    public ResponseEntity<ClassStatus> createClassStatus(@RequestBody ClassStatus classStatus) throws URISyntaxException {
        log.debug("REST request to save ClassStatus : {}", classStatus);
        if (classStatus.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new classStatus cannot already have an ID")).body(null);
        }
        ClassStatus result = classStatusService.save(classStatus);
        return ResponseEntity.created(new URI("/api/class-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /class-statuses : Updates an existing classStatus.
     *
     * @param classStatus the classStatus to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated classStatus,
     * or with status 400 (Bad Request) if the classStatus is not valid,
     * or with status 500 (Internal Server Error) if the classStatus couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/class-statuses")
    @Timed
    public ResponseEntity<ClassStatus> updateClassStatus(@RequestBody ClassStatus classStatus) throws URISyntaxException {
        log.debug("REST request to update ClassStatus : {}", classStatus);
        if (classStatus.getId() == null) {
            return createClassStatus(classStatus);
        }
        ClassStatus result = classStatusService.save(classStatus);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, classStatus.getId().toString()))
            .body(result);
    }

    /**
     * GET  /class-statuses : get all the classStatuses.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of classStatuses in body
     */
    @GetMapping("/class-statuses")
    @Timed
    public ResponseEntity<List<ClassStatus>> getAllClassStatuses(ClassStatusCriteria criteria,@ApiParam Pageable pageable) {
        log.debug("REST request to get ClassStatuses by criteria: {}", criteria);
        Page<ClassStatus> page = classStatusQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/class-statuses");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /class-statuses/:id : get the "id" classStatus.
     *
     * @param id the id of the classStatus to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the classStatus, or with status 404 (Not Found)
     */
    @GetMapping("/class-statuses/{id}")
    @Timed
    public ResponseEntity<ClassStatus> getClassStatus(@PathVariable Long id) {
        log.debug("REST request to get ClassStatus : {}", id);
        ClassStatus classStatus = classStatusService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(classStatus));
    }

    /**
     * DELETE  /class-statuses/:id : delete the "id" classStatus.
     *
     * @param id the id of the classStatus to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/class-statuses/{id}")
    @Timed
    public ResponseEntity<Void> deleteClassStatus(@PathVariable Long id) {
        log.debug("REST request to delete ClassStatus : {}", id);
        classStatusService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
