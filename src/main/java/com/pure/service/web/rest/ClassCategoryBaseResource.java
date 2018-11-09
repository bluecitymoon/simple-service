package com.pure.service.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pure.service.domain.ClassCategoryBase;
import com.pure.service.service.ClassCategoryBaseService;
import com.pure.service.web.rest.util.HeaderUtil;
import com.pure.service.web.rest.util.PaginationUtil;
import com.pure.service.service.dto.ClassCategoryBaseCriteria;
import com.pure.service.service.ClassCategoryBaseQueryService;
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
 * REST controller for managing ClassCategoryBase.
 */
@RestController
@RequestMapping("/api")
public class ClassCategoryBaseResource {

    private final Logger log = LoggerFactory.getLogger(ClassCategoryBaseResource.class);

    private static final String ENTITY_NAME = "classCategoryBase";

    private final ClassCategoryBaseService classCategoryBaseService;

    private final ClassCategoryBaseQueryService classCategoryBaseQueryService;

    public ClassCategoryBaseResource(ClassCategoryBaseService classCategoryBaseService, ClassCategoryBaseQueryService classCategoryBaseQueryService) {
        this.classCategoryBaseService = classCategoryBaseService;
        this.classCategoryBaseQueryService = classCategoryBaseQueryService;
    }

    /**
     * POST  /class-category-bases : Create a new classCategoryBase.
     *
     * @param classCategoryBase the classCategoryBase to create
     * @return the ResponseEntity with status 201 (Created) and with body the new classCategoryBase, or with status 400 (Bad Request) if the classCategoryBase has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/class-category-bases")
    @Timed
    public ResponseEntity<ClassCategoryBase> createClassCategoryBase(@RequestBody ClassCategoryBase classCategoryBase) throws URISyntaxException {
        log.debug("REST request to save ClassCategoryBase : {}", classCategoryBase);
        if (classCategoryBase.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new classCategoryBase cannot already have an ID")).body(null);
        }
        ClassCategoryBase result = classCategoryBaseService.save(classCategoryBase);
        return ResponseEntity.created(new URI("/api/class-category-bases/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /class-category-bases : Updates an existing classCategoryBase.
     *
     * @param classCategoryBase the classCategoryBase to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated classCategoryBase,
     * or with status 400 (Bad Request) if the classCategoryBase is not valid,
     * or with status 500 (Internal Server Error) if the classCategoryBase couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/class-category-bases")
    @Timed
    public ResponseEntity<ClassCategoryBase> updateClassCategoryBase(@RequestBody ClassCategoryBase classCategoryBase) throws URISyntaxException {
        log.debug("REST request to update ClassCategoryBase : {}", classCategoryBase);
        if (classCategoryBase.getId() == null) {
            return createClassCategoryBase(classCategoryBase);
        }
        ClassCategoryBase result = classCategoryBaseService.save(classCategoryBase);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, classCategoryBase.getId().toString()))
            .body(result);
    }

    /**
     * GET  /class-category-bases : get all the classCategoryBases.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of classCategoryBases in body
     */
    @GetMapping("/class-category-bases")
    @Timed
    public ResponseEntity<List<ClassCategoryBase>> getAllClassCategoryBases(ClassCategoryBaseCriteria criteria,@ApiParam Pageable pageable) {
        log.debug("REST request to get ClassCategoryBases by criteria: {}", criteria);
        Page<ClassCategoryBase> page = classCategoryBaseQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/class-category-bases");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /class-category-bases/:id : get the "id" classCategoryBase.
     *
     * @param id the id of the classCategoryBase to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the classCategoryBase, or with status 404 (Not Found)
     */
    @GetMapping("/class-category-bases/{id}")
    @Timed
    public ResponseEntity<ClassCategoryBase> getClassCategoryBase(@PathVariable Long id) {
        log.debug("REST request to get ClassCategoryBase : {}", id);
        ClassCategoryBase classCategoryBase = classCategoryBaseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(classCategoryBase));
    }

    /**
     * DELETE  /class-category-bases/:id : delete the "id" classCategoryBase.
     *
     * @param id the id of the classCategoryBase to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/class-category-bases/{id}")
    @Timed
    public ResponseEntity<Void> deleteClassCategoryBase(@PathVariable Long id) {
        log.debug("REST request to delete ClassCategoryBase : {}", id);
        classCategoryBaseService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
