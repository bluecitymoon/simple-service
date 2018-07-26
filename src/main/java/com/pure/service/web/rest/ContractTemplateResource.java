package com.pure.service.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pure.service.domain.ContractTemplate;
import com.pure.service.service.ContractTemplateService;
import com.pure.service.web.rest.util.HeaderUtil;
import com.pure.service.web.rest.util.PaginationUtil;
import com.pure.service.service.dto.ContractTemplateCriteria;
import com.pure.service.service.ContractTemplateQueryService;
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
 * REST controller for managing ContractTemplate.
 */
@RestController
@RequestMapping("/api")
public class ContractTemplateResource {

    private final Logger log = LoggerFactory.getLogger(ContractTemplateResource.class);

    private static final String ENTITY_NAME = "contractTemplate";

    private final ContractTemplateService contractTemplateService;

    private final ContractTemplateQueryService contractTemplateQueryService;

    public ContractTemplateResource(ContractTemplateService contractTemplateService, ContractTemplateQueryService contractTemplateQueryService) {
        this.contractTemplateService = contractTemplateService;
        this.contractTemplateQueryService = contractTemplateQueryService;
    }

    /**
     * POST  /contract-templates : Create a new contractTemplate.
     *
     * @param contractTemplate the contractTemplate to create
     * @return the ResponseEntity with status 201 (Created) and with body the new contractTemplate, or with status 400 (Bad Request) if the contractTemplate has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/contract-templates")
    @Timed
    public ResponseEntity<ContractTemplate> createContractTemplate(@RequestBody ContractTemplate contractTemplate) throws URISyntaxException {
        log.debug("REST request to save ContractTemplate : {}", contractTemplate);
        if (contractTemplate.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new contractTemplate cannot already have an ID")).body(null);
        }
        ContractTemplate result = contractTemplateService.save(contractTemplate);
        return ResponseEntity.created(new URI("/api/contract-templates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /contract-templates : Updates an existing contractTemplate.
     *
     * @param contractTemplate the contractTemplate to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated contractTemplate,
     * or with status 400 (Bad Request) if the contractTemplate is not valid,
     * or with status 500 (Internal Server Error) if the contractTemplate couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/contract-templates")
    @Timed
    public ResponseEntity<ContractTemplate> updateContractTemplate(@RequestBody ContractTemplate contractTemplate) throws URISyntaxException {
        log.debug("REST request to update ContractTemplate : {}", contractTemplate);
        if (contractTemplate.getId() == null) {
            return createContractTemplate(contractTemplate);
        }
        ContractTemplate result = contractTemplateService.save(contractTemplate);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, contractTemplate.getId().toString()))
            .body(result);
    }

    /**
     * GET  /contract-templates : get all the contractTemplates.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of contractTemplates in body
     */
    @GetMapping("/contract-templates")
    @Timed
    public ResponseEntity<List<ContractTemplate>> getAllContractTemplates(ContractTemplateCriteria criteria,@ApiParam Pageable pageable) {
        log.debug("REST request to get ContractTemplates by criteria: {}", criteria);
        Page<ContractTemplate> page = contractTemplateQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/contract-templates");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /contract-templates/:id : get the "id" contractTemplate.
     *
     * @param id the id of the contractTemplate to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the contractTemplate, or with status 404 (Not Found)
     */
    @GetMapping("/contract-templates/{id}")
    @Timed
    public ResponseEntity<ContractTemplate> getContractTemplate(@PathVariable Long id) {
        log.debug("REST request to get ContractTemplate : {}", id);
        ContractTemplate contractTemplate = contractTemplateService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(contractTemplate));
    }

    /**
     * DELETE  /contract-templates/:id : delete the "id" contractTemplate.
     *
     * @param id the id of the contractTemplate to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/contract-templates/{id}")
    @Timed
    public ResponseEntity<Void> deleteContractTemplate(@PathVariable Long id) {
        log.debug("REST request to delete ContractTemplate : {}", id);
        contractTemplateService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
