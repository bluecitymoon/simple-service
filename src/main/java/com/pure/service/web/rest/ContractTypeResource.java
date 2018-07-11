package com.pure.service.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pure.service.domain.ContractType;
import com.pure.service.service.ContractTypeService;
import com.pure.service.web.rest.util.HeaderUtil;
import com.pure.service.web.rest.util.PaginationUtil;
import com.pure.service.service.dto.ContractTypeCriteria;
import com.pure.service.service.ContractTypeQueryService;
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
 * REST controller for managing ContractType.
 */
@RestController
@RequestMapping("/api")
public class ContractTypeResource {

    private final Logger log = LoggerFactory.getLogger(ContractTypeResource.class);

    private static final String ENTITY_NAME = "contractType";

    private final ContractTypeService contractTypeService;

    private final ContractTypeQueryService contractTypeQueryService;

    public ContractTypeResource(ContractTypeService contractTypeService, ContractTypeQueryService contractTypeQueryService) {
        this.contractTypeService = contractTypeService;
        this.contractTypeQueryService = contractTypeQueryService;
    }

    /**
     * POST  /contract-types : Create a new contractType.
     *
     * @param contractType the contractType to create
     * @return the ResponseEntity with status 201 (Created) and with body the new contractType, or with status 400 (Bad Request) if the contractType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/contract-types")
    @Timed
    public ResponseEntity<ContractType> createContractType(@RequestBody ContractType contractType) throws URISyntaxException {
        log.debug("REST request to save ContractType : {}", contractType);
        if (contractType.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new contractType cannot already have an ID")).body(null);
        }
        ContractType result = contractTypeService.save(contractType);
        return ResponseEntity.created(new URI("/api/contract-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /contract-types : Updates an existing contractType.
     *
     * @param contractType the contractType to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated contractType,
     * or with status 400 (Bad Request) if the contractType is not valid,
     * or with status 500 (Internal Server Error) if the contractType couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/contract-types")
    @Timed
    public ResponseEntity<ContractType> updateContractType(@RequestBody ContractType contractType) throws URISyntaxException {
        log.debug("REST request to update ContractType : {}", contractType);
        if (contractType.getId() == null) {
            return createContractType(contractType);
        }
        ContractType result = contractTypeService.save(contractType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, contractType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /contract-types : get all the contractTypes.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of contractTypes in body
     */
    @GetMapping("/contract-types")
    @Timed
    public ResponseEntity<List<ContractType>> getAllContractTypes(ContractTypeCriteria criteria,@ApiParam Pageable pageable) {
        log.debug("REST request to get ContractTypes by criteria: {}", criteria);
        Page<ContractType> page = contractTypeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/contract-types");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /contract-types/:id : get the "id" contractType.
     *
     * @param id the id of the contractType to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the contractType, or with status 404 (Not Found)
     */
    @GetMapping("/contract-types/{id}")
    @Timed
    public ResponseEntity<ContractType> getContractType(@PathVariable Long id) {
        log.debug("REST request to get ContractType : {}", id);
        ContractType contractType = contractTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(contractType));
    }

    /**
     * DELETE  /contract-types/:id : delete the "id" contractType.
     *
     * @param id the id of the contractType to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/contract-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteContractType(@PathVariable Long id) {
        log.debug("REST request to delete ContractType : {}", id);
        contractTypeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
