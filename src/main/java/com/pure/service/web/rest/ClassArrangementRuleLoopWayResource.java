package com.pure.service.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pure.service.domain.ClassArrangementRuleLoopWay;
import com.pure.service.service.ClassArrangementRuleLoopWayService;
import com.pure.service.web.rest.util.HeaderUtil;
import com.pure.service.web.rest.util.PaginationUtil;
import com.pure.service.service.dto.ClassArrangementRuleLoopWayCriteria;
import com.pure.service.service.ClassArrangementRuleLoopWayQueryService;
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
 * REST controller for managing ClassArrangementRuleLoopWay.
 */
@RestController
@RequestMapping("/api")
public class ClassArrangementRuleLoopWayResource {

    private final Logger log = LoggerFactory.getLogger(ClassArrangementRuleLoopWayResource.class);

    private static final String ENTITY_NAME = "classArrangementRuleLoopWay";

    private final ClassArrangementRuleLoopWayService classArrangementRuleLoopWayService;

    private final ClassArrangementRuleLoopWayQueryService classArrangementRuleLoopWayQueryService;

    public ClassArrangementRuleLoopWayResource(ClassArrangementRuleLoopWayService classArrangementRuleLoopWayService, ClassArrangementRuleLoopWayQueryService classArrangementRuleLoopWayQueryService) {
        this.classArrangementRuleLoopWayService = classArrangementRuleLoopWayService;
        this.classArrangementRuleLoopWayQueryService = classArrangementRuleLoopWayQueryService;
    }

    /**
     * POST  /class-arrangement-rule-loop-ways : Create a new classArrangementRuleLoopWay.
     *
     * @param classArrangementRuleLoopWay the classArrangementRuleLoopWay to create
     * @return the ResponseEntity with status 201 (Created) and with body the new classArrangementRuleLoopWay, or with status 400 (Bad Request) if the classArrangementRuleLoopWay has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/class-arrangement-rule-loop-ways")
    @Timed
    public ResponseEntity<ClassArrangementRuleLoopWay> createClassArrangementRuleLoopWay(@RequestBody ClassArrangementRuleLoopWay classArrangementRuleLoopWay) throws URISyntaxException {
        log.debug("REST request to save ClassArrangementRuleLoopWay : {}", classArrangementRuleLoopWay);
        if (classArrangementRuleLoopWay.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new classArrangementRuleLoopWay cannot already have an ID")).body(null);
        }
        ClassArrangementRuleLoopWay result = classArrangementRuleLoopWayService.save(classArrangementRuleLoopWay);
        return ResponseEntity.created(new URI("/api/class-arrangement-rule-loop-ways/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /class-arrangement-rule-loop-ways : Updates an existing classArrangementRuleLoopWay.
     *
     * @param classArrangementRuleLoopWay the classArrangementRuleLoopWay to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated classArrangementRuleLoopWay,
     * or with status 400 (Bad Request) if the classArrangementRuleLoopWay is not valid,
     * or with status 500 (Internal Server Error) if the classArrangementRuleLoopWay couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/class-arrangement-rule-loop-ways")
    @Timed
    public ResponseEntity<ClassArrangementRuleLoopWay> updateClassArrangementRuleLoopWay(@RequestBody ClassArrangementRuleLoopWay classArrangementRuleLoopWay) throws URISyntaxException {
        log.debug("REST request to update ClassArrangementRuleLoopWay : {}", classArrangementRuleLoopWay);
        if (classArrangementRuleLoopWay.getId() == null) {
            return createClassArrangementRuleLoopWay(classArrangementRuleLoopWay);
        }
        ClassArrangementRuleLoopWay result = classArrangementRuleLoopWayService.save(classArrangementRuleLoopWay);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, classArrangementRuleLoopWay.getId().toString()))
            .body(result);
    }

    /**
     * GET  /class-arrangement-rule-loop-ways : get all the classArrangementRuleLoopWays.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of classArrangementRuleLoopWays in body
     */
    @GetMapping("/class-arrangement-rule-loop-ways")
    @Timed
    public ResponseEntity<List<ClassArrangementRuleLoopWay>> getAllClassArrangementRuleLoopWays(ClassArrangementRuleLoopWayCriteria criteria,@ApiParam Pageable pageable) {
        log.debug("REST request to get ClassArrangementRuleLoopWays by criteria: {}", criteria);
        Page<ClassArrangementRuleLoopWay> page = classArrangementRuleLoopWayQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/class-arrangement-rule-loop-ways");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /class-arrangement-rule-loop-ways/:id : get the "id" classArrangementRuleLoopWay.
     *
     * @param id the id of the classArrangementRuleLoopWay to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the classArrangementRuleLoopWay, or with status 404 (Not Found)
     */
    @GetMapping("/class-arrangement-rule-loop-ways/{id}")
    @Timed
    public ResponseEntity<ClassArrangementRuleLoopWay> getClassArrangementRuleLoopWay(@PathVariable Long id) {
        log.debug("REST request to get ClassArrangementRuleLoopWay : {}", id);
        ClassArrangementRuleLoopWay classArrangementRuleLoopWay = classArrangementRuleLoopWayService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(classArrangementRuleLoopWay));
    }

    /**
     * DELETE  /class-arrangement-rule-loop-ways/:id : delete the "id" classArrangementRuleLoopWay.
     *
     * @param id the id of the classArrangementRuleLoopWay to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/class-arrangement-rule-loop-ways/{id}")
    @Timed
    public ResponseEntity<Void> deleteClassArrangementRuleLoopWay(@PathVariable Long id) {
        log.debug("REST request to delete ClassArrangementRuleLoopWay : {}", id);
        classArrangementRuleLoopWayService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
