package com.pure.service.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pure.service.domain.ContractStatus;
import com.pure.service.service.ContractStatusService;
import com.pure.service.web.rest.util.HeaderUtil;
import com.pure.service.web.rest.util.PaginationUtil;
import com.pure.service.service.dto.ContractStatusCriteria;
import com.pure.service.service.ContractStatusQueryService;
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
 * REST controller for managing ContractStatus.
 */
@RestController
@RequestMapping("/api")
public class ContractStatusResource {

    private final Logger log = LoggerFactory.getLogger(ContractStatusResource.class);

    private static final String ENTITY_NAME = "contractStatus";

    private final ContractStatusService contractStatusService;

    private final ContractStatusQueryService contractStatusQueryService;

    public ContractStatusResource(ContractStatusService contractStatusService, ContractStatusQueryService contractStatusQueryService) {
        this.contractStatusService = contractStatusService;
        this.contractStatusQueryService = contractStatusQueryService;
    }

    /**
     * POST  /contract-statuses : Create a new contractStatus.
     *
     * @param contractStatus the contractStatus to create
     * @return the ResponseEntity with status 201 (Created) and with body the new contractStatus, or with status 400 (Bad Request) if the contractStatus has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/contract-statuses")
    @Timed
    public ResponseEntity<ContractStatus> createContractStatus(@RequestBody ContractStatus contractStatus) throws URISyntaxException {
        log.debug("REST request to save ContractStatus : {}", contractStatus);
        if (contractStatus.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new contractStatus cannot already have an ID")).body(null);
        }
        ContractStatus result = contractStatusService.save(contractStatus);
        return ResponseEntity.created(new URI("/api/contract-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /contract-statuses : Updates an existing contractStatus.
     *
     * @param contractStatus the contractStatus to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated contractStatus,
     * or with status 400 (Bad Request) if the contractStatus is not valid,
     * or with status 500 (Internal Server Error) if the contractStatus couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/contract-statuses")
    @Timed
    public ResponseEntity<ContractStatus> updateContractStatus(@RequestBody ContractStatus contractStatus) throws URISyntaxException {
        log.debug("REST request to update ContractStatus : {}", contractStatus);
        if (contractStatus.getId() == null) {
            return createContractStatus(contractStatus);
        }
        ContractStatus result = contractStatusService.save(contractStatus);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, contractStatus.getId().toString()))
            .body(result);
    }

    /**
     * GET  /contract-statuses : get all the contractStatuses.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of contractStatuses in body
     */
    @GetMapping("/contract-statuses")
    @Timed
    public ResponseEntity<List<ContractStatus>> getAllContractStatuses(ContractStatusCriteria criteria,@ApiParam Pageable pageable) {
        log.debug("REST request to get ContractStatuses by criteria: {}", criteria);
        Page<ContractStatus> page = contractStatusQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/contract-statuses");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /contract-statuses/:id : get the "id" contractStatus.
     *
     * @param id the id of the contractStatus to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the contractStatus, or with status 404 (Not Found)
     */
    @GetMapping("/contract-statuses/{id}")
    @Timed
    public ResponseEntity<ContractStatus> getContractStatus(@PathVariable Long id) {
        log.debug("REST request to get ContractStatus : {}", id);
        ContractStatus contractStatus = contractStatusService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(contractStatus));
    }

    /**
     * DELETE  /contract-statuses/:id : delete the "id" contractStatus.
     *
     * @param id the id of the contractStatus to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/contract-statuses/{id}")
    @Timed
    public ResponseEntity<Void> deleteContractStatus(@PathVariable Long id) {
        log.debug("REST request to delete ContractStatus : {}", id);
        contractStatusService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
