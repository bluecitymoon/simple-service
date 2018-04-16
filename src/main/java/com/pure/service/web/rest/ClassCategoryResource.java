package com.pure.service.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pure.service.domain.ClassCategory;

import com.pure.service.repository.ClassCategoryRepository;
import com.pure.service.web.rest.util.HeaderUtil;
import com.pure.service.web.rest.util.PaginationUtil;
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
 * REST controller for managing ClassCategory.
 */
@RestController
@RequestMapping("/api")
public class ClassCategoryResource {

    private final Logger log = LoggerFactory.getLogger(ClassCategoryResource.class);

    private static final String ENTITY_NAME = "classCategory";

    private final ClassCategoryRepository classCategoryRepository;

    public ClassCategoryResource(ClassCategoryRepository classCategoryRepository) {
        this.classCategoryRepository = classCategoryRepository;
    }

    /**
     * POST  /class-categories : Create a new classCategory.
     *
     * @param classCategory the classCategory to create
     * @return the ResponseEntity with status 201 (Created) and with body the new classCategory, or with status 400 (Bad Request) if the classCategory has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/class-categories")
    @Timed
    public ResponseEntity<ClassCategory> createClassCategory(@RequestBody ClassCategory classCategory) throws URISyntaxException {
        log.debug("REST request to save ClassCategory : {}", classCategory);
        if (classCategory.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new classCategory cannot already have an ID")).body(null);
        }
        ClassCategory result = classCategoryRepository.save(classCategory);
        return ResponseEntity.created(new URI("/api/class-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /class-categories : Updates an existing classCategory.
     *
     * @param classCategory the classCategory to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated classCategory,
     * or with status 400 (Bad Request) if the classCategory is not valid,
     * or with status 500 (Internal Server Error) if the classCategory couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/class-categories")
    @Timed
    public ResponseEntity<ClassCategory> updateClassCategory(@RequestBody ClassCategory classCategory) throws URISyntaxException {
        log.debug("REST request to update ClassCategory : {}", classCategory);
        if (classCategory.getId() == null) {
            return createClassCategory(classCategory);
        }
        ClassCategory result = classCategoryRepository.save(classCategory);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, classCategory.getId().toString()))
            .body(result);
    }

    /**
     * GET  /class-categories : get all the classCategories.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of classCategories in body
     */
    @GetMapping("/class-categories")
    @Timed
    public ResponseEntity<List<ClassCategory>> getAllClassCategories(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of ClassCategories");
        Page<ClassCategory> page = classCategoryRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/class-categories");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /class-categories/:id : get the "id" classCategory.
     *
     * @param id the id of the classCategory to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the classCategory, or with status 404 (Not Found)
     */
    @GetMapping("/class-categories/{id}")
    @Timed
    public ResponseEntity<ClassCategory> getClassCategory(@PathVariable Long id) {
        log.debug("REST request to get ClassCategory : {}", id);
        ClassCategory classCategory = classCategoryRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(classCategory));
    }

    /**
     * DELETE  /class-categories/:id : delete the "id" classCategory.
     *
     * @param id the id of the classCategory to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/class-categories/{id}")
    @Timed
    public ResponseEntity<Void> deleteClassCategory(@PathVariable Long id) {
        log.debug("REST request to delete ClassCategory : {}", id);
        classCategoryRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
