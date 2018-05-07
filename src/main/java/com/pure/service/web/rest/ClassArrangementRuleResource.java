package com.pure.service.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pure.service.domain.ClassArrangementRule;
import com.pure.service.service.ClassArrangementRuleService;
import com.pure.service.web.rest.util.HeaderUtil;
import com.pure.service.web.rest.util.PaginationUtil;
import com.pure.service.service.dto.ClassArrangementRuleCriteria;
import com.pure.service.service.ClassArrangementRuleQueryService;
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
 * REST controller for managing ClassArrangementRule.
 */
@RestController
@RequestMapping("/api")
public class ClassArrangementRuleResource {

    private final Logger log = LoggerFactory.getLogger(ClassArrangementRuleResource.class);

    private static final String ENTITY_NAME = "classArrangementRule";

    private final ClassArrangementRuleService classArrangementRuleService;

    private final ClassArrangementRuleQueryService classArrangementRuleQueryService;

    public ClassArrangementRuleResource(ClassArrangementRuleService classArrangementRuleService, ClassArrangementRuleQueryService classArrangementRuleQueryService) {
        this.classArrangementRuleService = classArrangementRuleService;
        this.classArrangementRuleQueryService = classArrangementRuleQueryService;
    }

    /**
     * POST  /class-arrangement-rules : Create a new classArrangementRule.
     *
     * @param classArrangementRule the classArrangementRule to create
     * @return the ResponseEntity with status 201 (Created) and with body the new classArrangementRule, or with status 400 (Bad Request) if the classArrangementRule has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/class-arrangement-rules")
    @Timed
    public ResponseEntity<ClassArrangementRule> createClassArrangementRule(@RequestBody ClassArrangementRule classArrangementRule) throws URISyntaxException {
        log.debug("REST request to save ClassArrangementRule : {}", classArrangementRule);
        if (classArrangementRule.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new classArrangementRule cannot already have an ID")).body(null);
        }
        ClassArrangementRule result = classArrangementRuleService.save(classArrangementRule);
        return ResponseEntity.created(new URI("/api/class-arrangement-rules/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /class-arrangement-rules : Updates an existing classArrangementRule.
     *
     * @param classArrangementRule the classArrangementRule to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated classArrangementRule,
     * or with status 400 (Bad Request) if the classArrangementRule is not valid,
     * or with status 500 (Internal Server Error) if the classArrangementRule couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/class-arrangement-rules")
    @Timed
    public ResponseEntity<ClassArrangementRule> updateClassArrangementRule(@RequestBody ClassArrangementRule classArrangementRule) throws URISyntaxException {
        log.debug("REST request to update ClassArrangementRule : {}", classArrangementRule);
        if (classArrangementRule.getId() == null) {
            return createClassArrangementRule(classArrangementRule);
        }
        ClassArrangementRule result = classArrangementRuleService.save(classArrangementRule);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, classArrangementRule.getId().toString()))
            .body(result);
    }

    /**
     * GET  /class-arrangement-rules : get all the classArrangementRules.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of classArrangementRules in body
     */
    @GetMapping("/class-arrangement-rules")
    @Timed
    public ResponseEntity<List<ClassArrangementRule>> getAllClassArrangementRules(ClassArrangementRuleCriteria criteria,@ApiParam Pageable pageable) {
        log.debug("REST request to get ClassArrangementRules by criteria: {}", criteria);
        Page<ClassArrangementRule> page = classArrangementRuleQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/class-arrangement-rules");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /class-arrangement-rules/:id : get the "id" classArrangementRule.
     *
     * @param id the id of the classArrangementRule to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the classArrangementRule, or with status 404 (Not Found)
     */
    @GetMapping("/class-arrangement-rules/{id}")
    @Timed
    public ResponseEntity<ClassArrangementRule> getClassArrangementRule(@PathVariable Long id) {
        log.debug("REST request to get ClassArrangementRule : {}", id);
        ClassArrangementRule classArrangementRule = classArrangementRuleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(classArrangementRule));
    }

    /**
     * DELETE  /class-arrangement-rules/:id : delete the "id" classArrangementRule.
     *
     * @param id the id of the classArrangementRule to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/class-arrangement-rules/{id}")
    @Timed
    public ResponseEntity<Void> deleteClassArrangementRule(@PathVariable Long id) {
        log.debug("REST request to delete ClassArrangementRule : {}", id);
        classArrangementRuleService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
