package com.pure.service.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pure.service.domain.AuthorityUserGuideDocument;
import com.pure.service.service.AuthorityUserGuideDocumentService;
import com.pure.service.web.rest.util.HeaderUtil;
import com.pure.service.web.rest.util.PaginationUtil;
import com.pure.service.service.dto.AuthorityUserGuideDocumentCriteria;
import com.pure.service.service.AuthorityUserGuideDocumentQueryService;
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
 * REST controller for managing AuthorityUserGuideDocument.
 */
@RestController
@RequestMapping("/api")
public class AuthorityUserGuideDocumentResource {

    private final Logger log = LoggerFactory.getLogger(AuthorityUserGuideDocumentResource.class);

    private static final String ENTITY_NAME = "authorityUserGuideDocument";

    private final AuthorityUserGuideDocumentService authorityUserGuideDocumentService;

    private final AuthorityUserGuideDocumentQueryService authorityUserGuideDocumentQueryService;

    public AuthorityUserGuideDocumentResource(AuthorityUserGuideDocumentService authorityUserGuideDocumentService, AuthorityUserGuideDocumentQueryService authorityUserGuideDocumentQueryService) {
        this.authorityUserGuideDocumentService = authorityUserGuideDocumentService;
        this.authorityUserGuideDocumentQueryService = authorityUserGuideDocumentQueryService;
    }

    /**
     * POST  /authority-user-guide-documents : Create a new authorityUserGuideDocument.
     *
     * @param authorityUserGuideDocument the authorityUserGuideDocument to create
     * @return the ResponseEntity with status 201 (Created) and with body the new authorityUserGuideDocument, or with status 400 (Bad Request) if the authorityUserGuideDocument has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/authority-user-guide-documents")
    @Timed
    public ResponseEntity<AuthorityUserGuideDocument> createAuthorityUserGuideDocument(@RequestBody AuthorityUserGuideDocument authorityUserGuideDocument) throws URISyntaxException {
        log.debug("REST request to save AuthorityUserGuideDocument : {}", authorityUserGuideDocument);
        if (authorityUserGuideDocument.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new authorityUserGuideDocument cannot already have an ID")).body(null);
        }
        AuthorityUserGuideDocument result = authorityUserGuideDocumentService.save(authorityUserGuideDocument);
        return ResponseEntity.created(new URI("/api/authority-user-guide-documents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /authority-user-guide-documents : Updates an existing authorityUserGuideDocument.
     *
     * @param authorityUserGuideDocument the authorityUserGuideDocument to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated authorityUserGuideDocument,
     * or with status 400 (Bad Request) if the authorityUserGuideDocument is not valid,
     * or with status 500 (Internal Server Error) if the authorityUserGuideDocument couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/authority-user-guide-documents")
    @Timed
    public ResponseEntity<AuthorityUserGuideDocument> updateAuthorityUserGuideDocument(@RequestBody AuthorityUserGuideDocument authorityUserGuideDocument) throws URISyntaxException {
        log.debug("REST request to update AuthorityUserGuideDocument : {}", authorityUserGuideDocument);
        if (authorityUserGuideDocument.getId() == null) {
            return createAuthorityUserGuideDocument(authorityUserGuideDocument);
        }
        AuthorityUserGuideDocument result = authorityUserGuideDocumentService.save(authorityUserGuideDocument);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, authorityUserGuideDocument.getId().toString()))
            .body(result);
    }

    /**
     * GET  /authority-user-guide-documents : get all the authorityUserGuideDocuments.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of authorityUserGuideDocuments in body
     */
    @GetMapping("/authority-user-guide-documents")
    @Timed
    public ResponseEntity<List<AuthorityUserGuideDocument>> getAllAuthorityUserGuideDocuments(AuthorityUserGuideDocumentCriteria criteria,@ApiParam Pageable pageable) {
        log.debug("REST request to get AuthorityUserGuideDocuments by criteria: {}", criteria);
        Page<AuthorityUserGuideDocument> page = authorityUserGuideDocumentQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/authority-user-guide-documents");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /authority-user-guide-documents/:id : get the "id" authorityUserGuideDocument.
     *
     * @param id the id of the authorityUserGuideDocument to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the authorityUserGuideDocument, or with status 404 (Not Found)
     */
    @GetMapping("/authority-user-guide-documents/{id}")
    @Timed
    public ResponseEntity<AuthorityUserGuideDocument> getAuthorityUserGuideDocument(@PathVariable Long id) {
        log.debug("REST request to get AuthorityUserGuideDocument : {}", id);
        AuthorityUserGuideDocument authorityUserGuideDocument = authorityUserGuideDocumentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(authorityUserGuideDocument));
    }

    /**
     * DELETE  /authority-user-guide-documents/:id : delete the "id" authorityUserGuideDocument.
     *
     * @param id the id of the authorityUserGuideDocument to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/authority-user-guide-documents/{id}")
    @Timed
    public ResponseEntity<Void> deleteAuthorityUserGuideDocument(@PathVariable Long id) {
        log.debug("REST request to delete AuthorityUserGuideDocument : {}", id);
        authorityUserGuideDocumentService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
