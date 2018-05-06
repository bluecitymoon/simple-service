package com.pure.service.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pure.service.domain.EducationLevel;
import com.pure.service.service.EducationLevelService;
import com.pure.service.web.rest.util.HeaderUtil;
import com.pure.service.web.rest.util.PaginationUtil;
import com.pure.service.service.dto.EducationLevelCriteria;
import com.pure.service.service.EducationLevelQueryService;
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
 * REST controller for managing EducationLevel.
 */
@RestController
@RequestMapping("/api")
public class EducationLevelResource {

    private final Logger log = LoggerFactory.getLogger(EducationLevelResource.class);

    private static final String ENTITY_NAME = "educationLevel";

    private final EducationLevelService educationLevelService;

    private final EducationLevelQueryService educationLevelQueryService;

    public EducationLevelResource(EducationLevelService educationLevelService, EducationLevelQueryService educationLevelQueryService) {
        this.educationLevelService = educationLevelService;
        this.educationLevelQueryService = educationLevelQueryService;
    }

    /**
     * POST  /education-levels : Create a new educationLevel.
     *
     * @param educationLevel the educationLevel to create
     * @return the ResponseEntity with status 201 (Created) and with body the new educationLevel, or with status 400 (Bad Request) if the educationLevel has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/education-levels")
    @Timed
    public ResponseEntity<EducationLevel> createEducationLevel(@RequestBody EducationLevel educationLevel) throws URISyntaxException {
        log.debug("REST request to save EducationLevel : {}", educationLevel);
        if (educationLevel.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new educationLevel cannot already have an ID")).body(null);
        }
        EducationLevel result = educationLevelService.save(educationLevel);
        return ResponseEntity.created(new URI("/api/education-levels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /education-levels : Updates an existing educationLevel.
     *
     * @param educationLevel the educationLevel to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated educationLevel,
     * or with status 400 (Bad Request) if the educationLevel is not valid,
     * or with status 500 (Internal Server Error) if the educationLevel couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/education-levels")
    @Timed
    public ResponseEntity<EducationLevel> updateEducationLevel(@RequestBody EducationLevel educationLevel) throws URISyntaxException {
        log.debug("REST request to update EducationLevel : {}", educationLevel);
        if (educationLevel.getId() == null) {
            return createEducationLevel(educationLevel);
        }
        EducationLevel result = educationLevelService.save(educationLevel);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, educationLevel.getId().toString()))
            .body(result);
    }

    /**
     * GET  /education-levels : get all the educationLevels.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of educationLevels in body
     */
    @GetMapping("/education-levels")
    @Timed
    public ResponseEntity<List<EducationLevel>> getAllEducationLevels(EducationLevelCriteria criteria,@ApiParam Pageable pageable) {
        log.debug("REST request to get EducationLevels by criteria: {}", criteria);
        Page<EducationLevel> page = educationLevelQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/education-levels");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /education-levels/:id : get the "id" educationLevel.
     *
     * @param id the id of the educationLevel to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the educationLevel, or with status 404 (Not Found)
     */
    @GetMapping("/education-levels/{id}")
    @Timed
    public ResponseEntity<EducationLevel> getEducationLevel(@PathVariable Long id) {
        log.debug("REST request to get EducationLevel : {}", id);
        EducationLevel educationLevel = educationLevelService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(educationLevel));
    }

    /**
     * DELETE  /education-levels/:id : delete the "id" educationLevel.
     *
     * @param id the id of the educationLevel to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/education-levels/{id}")
    @Timed
    public ResponseEntity<Void> deleteEducationLevel(@PathVariable Long id) {
        log.debug("REST request to delete EducationLevel : {}", id);
        educationLevelService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
