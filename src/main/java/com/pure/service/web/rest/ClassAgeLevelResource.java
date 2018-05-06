package com.pure.service.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pure.service.domain.ClassAgeLevel;
import com.pure.service.service.ClassAgeLevelService;
import com.pure.service.web.rest.util.HeaderUtil;
import com.pure.service.web.rest.util.PaginationUtil;
import com.pure.service.service.dto.ClassAgeLevelCriteria;
import com.pure.service.service.ClassAgeLevelQueryService;
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
 * REST controller for managing ClassAgeLevel.
 */
@RestController
@RequestMapping("/api")
public class ClassAgeLevelResource {

    private final Logger log = LoggerFactory.getLogger(ClassAgeLevelResource.class);

    private static final String ENTITY_NAME = "classAgeLevel";

    private final ClassAgeLevelService classAgeLevelService;

    private final ClassAgeLevelQueryService classAgeLevelQueryService;

    public ClassAgeLevelResource(ClassAgeLevelService classAgeLevelService, ClassAgeLevelQueryService classAgeLevelQueryService) {
        this.classAgeLevelService = classAgeLevelService;
        this.classAgeLevelQueryService = classAgeLevelQueryService;
    }

    /**
     * POST  /class-age-levels : Create a new classAgeLevel.
     *
     * @param classAgeLevel the classAgeLevel to create
     * @return the ResponseEntity with status 201 (Created) and with body the new classAgeLevel, or with status 400 (Bad Request) if the classAgeLevel has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/class-age-levels")
    @Timed
    public ResponseEntity<ClassAgeLevel> createClassAgeLevel(@RequestBody ClassAgeLevel classAgeLevel) throws URISyntaxException {
        log.debug("REST request to save ClassAgeLevel : {}", classAgeLevel);
        if (classAgeLevel.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new classAgeLevel cannot already have an ID")).body(null);
        }
        ClassAgeLevel result = classAgeLevelService.save(classAgeLevel);
        return ResponseEntity.created(new URI("/api/class-age-levels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /class-age-levels : Updates an existing classAgeLevel.
     *
     * @param classAgeLevel the classAgeLevel to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated classAgeLevel,
     * or with status 400 (Bad Request) if the classAgeLevel is not valid,
     * or with status 500 (Internal Server Error) if the classAgeLevel couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/class-age-levels")
    @Timed
    public ResponseEntity<ClassAgeLevel> updateClassAgeLevel(@RequestBody ClassAgeLevel classAgeLevel) throws URISyntaxException {
        log.debug("REST request to update ClassAgeLevel : {}", classAgeLevel);
        if (classAgeLevel.getId() == null) {
            return createClassAgeLevel(classAgeLevel);
        }
        ClassAgeLevel result = classAgeLevelService.save(classAgeLevel);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, classAgeLevel.getId().toString()))
            .body(result);
    }

    /**
     * GET  /class-age-levels : get all the classAgeLevels.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of classAgeLevels in body
     */
    @GetMapping("/class-age-levels")
    @Timed
    public ResponseEntity<List<ClassAgeLevel>> getAllClassAgeLevels(ClassAgeLevelCriteria criteria,@ApiParam Pageable pageable) {
        log.debug("REST request to get ClassAgeLevels by criteria: {}", criteria);
        Page<ClassAgeLevel> page = classAgeLevelQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/class-age-levels");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /class-age-levels/:id : get the "id" classAgeLevel.
     *
     * @param id the id of the classAgeLevel to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the classAgeLevel, or with status 404 (Not Found)
     */
    @GetMapping("/class-age-levels/{id}")
    @Timed
    public ResponseEntity<ClassAgeLevel> getClassAgeLevel(@PathVariable Long id) {
        log.debug("REST request to get ClassAgeLevel : {}", id);
        ClassAgeLevel classAgeLevel = classAgeLevelService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(classAgeLevel));
    }

    /**
     * DELETE  /class-age-levels/:id : delete the "id" classAgeLevel.
     *
     * @param id the id of the classAgeLevel to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/class-age-levels/{id}")
    @Timed
    public ResponseEntity<Void> deleteClassAgeLevel(@PathVariable Long id) {
        log.debug("REST request to delete ClassAgeLevel : {}", id);
        classAgeLevelService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
