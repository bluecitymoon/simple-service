package com.pure.service.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pure.service.domain.ContractPackageType;
import com.pure.service.service.ContractPackageTypeService;
import com.pure.service.web.rest.util.HeaderUtil;
import com.pure.service.web.rest.util.PaginationUtil;
import com.pure.service.service.dto.ContractPackageTypeCriteria;
import com.pure.service.service.ContractPackageTypeQueryService;
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
 * REST controller for managing ContractPackageType.
 */
@RestController
@RequestMapping("/api")
public class ContractPackageTypeResource {

    private final Logger log = LoggerFactory.getLogger(ContractPackageTypeResource.class);

    private static final String ENTITY_NAME = "contractPackageType";

    private final ContractPackageTypeService contractPackageTypeService;

    private final ContractPackageTypeQueryService contractPackageTypeQueryService;

    public ContractPackageTypeResource(ContractPackageTypeService contractPackageTypeService, ContractPackageTypeQueryService contractPackageTypeQueryService) {
        this.contractPackageTypeService = contractPackageTypeService;
        this.contractPackageTypeQueryService = contractPackageTypeQueryService;
    }

    /**
     * POST  /contract-package-types : Create a new contractPackageType.
     *
     * @param contractPackageType the contractPackageType to create
     * @return the ResponseEntity with status 201 (Created) and with body the new contractPackageType, or with status 400 (Bad Request) if the contractPackageType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/contract-package-types")
    @Timed
    public ResponseEntity<ContractPackageType> createContractPackageType(@RequestBody ContractPackageType contractPackageType) throws URISyntaxException {
        log.debug("REST request to save ContractPackageType : {}", contractPackageType);
        if (contractPackageType.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new contractPackageType cannot already have an ID")).body(null);
        }
        ContractPackageType result = contractPackageTypeService.save(contractPackageType);
        return ResponseEntity.created(new URI("/api/contract-package-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /contract-package-types : Updates an existing contractPackageType.
     *
     * @param contractPackageType the contractPackageType to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated contractPackageType,
     * or with status 400 (Bad Request) if the contractPackageType is not valid,
     * or with status 500 (Internal Server Error) if the contractPackageType couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/contract-package-types")
    @Timed
    public ResponseEntity<ContractPackageType> updateContractPackageType(@RequestBody ContractPackageType contractPackageType) throws URISyntaxException {
        log.debug("REST request to update ContractPackageType : {}", contractPackageType);
        if (contractPackageType.getId() == null) {
            return createContractPackageType(contractPackageType);
        }
        ContractPackageType result = contractPackageTypeService.save(contractPackageType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, contractPackageType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /contract-package-types : get all the contractPackageTypes.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of contractPackageTypes in body
     */
    @GetMapping("/contract-package-types")
    @Timed
    public ResponseEntity<List<ContractPackageType>> getAllContractPackageTypes(ContractPackageTypeCriteria criteria,@ApiParam Pageable pageable) {
        log.debug("REST request to get ContractPackageTypes by criteria: {}", criteria);
        Page<ContractPackageType> page = contractPackageTypeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/contract-package-types");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /contract-package-types/:id : get the "id" contractPackageType.
     *
     * @param id the id of the contractPackageType to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the contractPackageType, or with status 404 (Not Found)
     */
    @GetMapping("/contract-package-types/{id}")
    @Timed
    public ResponseEntity<ContractPackageType> getContractPackageType(@PathVariable Long id) {
        log.debug("REST request to get ContractPackageType : {}", id);
        ContractPackageType contractPackageType = contractPackageTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(contractPackageType));
    }

    /**
     * DELETE  /contract-package-types/:id : delete the "id" contractPackageType.
     *
     * @param id the id of the contractPackageType to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/contract-package-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteContractPackageType(@PathVariable Long id) {
        log.debug("REST request to delete ContractPackageType : {}", id);
        contractPackageTypeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
