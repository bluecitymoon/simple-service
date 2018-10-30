package com.pure.service.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pure.service.domain.UserGuideDocument;
import com.pure.service.service.UserGuideDocumentService;
import com.pure.service.service.dto.dto.UserGuideDocumentDto;
import com.pure.service.web.rest.util.HeaderUtil;
import com.pure.service.web.rest.util.PaginationUtil;
import com.pure.service.service.dto.UserGuideDocumentCriteria;
import com.pure.service.service.UserGuideDocumentQueryService;
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
 * REST controller for managing UserGuideDocument.
 */
@RestController
@RequestMapping("/api")
public class UserGuideDocumentResource {

    private final Logger log = LoggerFactory.getLogger(UserGuideDocumentResource.class);

    private static final String ENTITY_NAME = "userGuideDocument";

    private final UserGuideDocumentService userGuideDocumentService;

    private final UserGuideDocumentQueryService userGuideDocumentQueryService;

    public UserGuideDocumentResource(UserGuideDocumentService userGuideDocumentService, UserGuideDocumentQueryService userGuideDocumentQueryService) {
        this.userGuideDocumentService = userGuideDocumentService;
        this.userGuideDocumentQueryService = userGuideDocumentQueryService;
    }

    /**
     * POST  /user-guide-documents : Create a new userGuideDocument.
     *
     * @param userGuideDocument the userGuideDocument to create
     * @return the ResponseEntity with status 201 (Created) and with body the new userGuideDocument, or with status 400 (Bad Request) if the userGuideDocument has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/user-guide-documents")
    @Timed
    public ResponseEntity<UserGuideDocument> createUserGuideDocument(@RequestBody UserGuideDocument userGuideDocument) throws URISyntaxException {
        log.debug("REST request to save UserGuideDocument : {}", userGuideDocument);
        if (userGuideDocument.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new userGuideDocument cannot already have an ID")).body(null);
        }
        UserGuideDocument result = userGuideDocumentService.save(userGuideDocument);
        return ResponseEntity.created(new URI("/api/user-guide-documents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PostMapping("/user-guide-documents/save-with-authority")
    @Timed
    public ResponseEntity<UserGuideDocument> saveWithAuthority(@RequestBody UserGuideDocumentDto userGuideDocument) throws URISyntaxException {
        log.debug("REST request to save UserGuideDocument : {}", userGuideDocument);
//        if (userGuideDocument.getId() != null) {
//            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new userGuideDocument cannot already have an ID")).body(null);
//        }
        UserGuideDocument result = userGuideDocumentService.saveWithAuthorities(userGuideDocument);
        return ResponseEntity.created(new URI("/api/user-guide-documents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /user-guide-documents : Updates an existing userGuideDocument.
     *
     * @param userGuideDocument the userGuideDocument to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated userGuideDocument,
     * or with status 400 (Bad Request) if the userGuideDocument is not valid,
     * or with status 500 (Internal Server Error) if the userGuideDocument couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/user-guide-documents")
    @Timed
    public ResponseEntity<UserGuideDocument> updateUserGuideDocument(@RequestBody UserGuideDocument userGuideDocument) throws URISyntaxException {
        log.debug("REST request to update UserGuideDocument : {}", userGuideDocument);
        if (userGuideDocument.getId() == null) {
            return createUserGuideDocument(userGuideDocument);
        }
        UserGuideDocument result = userGuideDocumentService.save(userGuideDocument);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, userGuideDocument.getId().toString()))
            .body(result);
    }

    /**
     * GET  /user-guide-documents : get all the userGuideDocuments.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of userGuideDocuments in body
     */
    @GetMapping("/user-guide-documents")
    @Timed
    public ResponseEntity<List<UserGuideDocument>> getAllUserGuideDocuments(UserGuideDocumentCriteria criteria,@ApiParam Pageable pageable) {
        log.debug("REST request to get UserGuideDocuments by criteria: {}", criteria);
        Page<UserGuideDocument> page = userGuideDocumentQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/user-guide-documents");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/user-guide-documents/mine")
    @Timed
    public ResponseEntity<List<UserGuideDocument>> getAllCurrentUserGuideDocuments() {

        List<UserGuideDocument> page = userGuideDocumentService.getCurrentUserDocuments();

        return new ResponseEntity<>(page, null, HttpStatus.OK);
    }


    @GetMapping("/user-guide-documents/with-authorities")
    @Timed
    public ResponseEntity<List<UserGuideDocumentDto>> getAllUserGuideDocumentsWithAuthorities(UserGuideDocumentCriteria criteria, @ApiParam Pageable pageable) {
        log.debug("REST request to get UserGuideDocuments by criteria: {}", criteria);
        Page<UserGuideDocumentDto> page = userGuideDocumentService.searchDocumentsWithAuthorites(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/user-guide-documents");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /user-guide-documents/:id : get the "id" userGuideDocument.
     *
     * @param id the id of the userGuideDocument to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the userGuideDocument, or with status 404 (Not Found)
     */
    @GetMapping("/user-guide-documents/{id}")
    @Timed
    public ResponseEntity<UserGuideDocument> getUserGuideDocument(@PathVariable Long id) {
        log.debug("REST request to get UserGuideDocument : {}", id);
        UserGuideDocument userGuideDocument = userGuideDocumentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(userGuideDocument));
    }

    /**
     * DELETE  /user-guide-documents/:id : delete the "id" userGuideDocument.
     *
     * @param id the id of the userGuideDocument to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/user-guide-documents/{id}")
    @Timed
    public ResponseEntity<Void> deleteUserGuideDocument(@PathVariable Long id) {
        log.debug("REST request to delete UserGuideDocument : {}", id);
        userGuideDocumentService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
