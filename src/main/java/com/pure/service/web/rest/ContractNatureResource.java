package com.pure.service.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pure.service.domain.ContractNature;
import com.pure.service.service.ContractNatureService;
import com.pure.service.web.rest.util.HeaderUtil;
import com.pure.service.web.rest.util.PaginationUtil;
import com.pure.service.service.dto.ContractNatureCriteria;
import com.pure.service.service.ContractNatureQueryService;
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
 * REST controller for managing ContractNature.
 */
@RestController
@RequestMapping("/api")
public class ContractNatureResource {

    private final Logger log = LoggerFactory.getLogger(ContractNatureResource.class);

    private static final String ENTITY_NAME = "contractNature";

    private final ContractNatureService contractNatureService;

    private final ContractNatureQueryService contractNatureQueryService;

    public ContractNatureResource(ContractNatureService contractNatureService, ContractNatureQueryService contractNatureQueryService) {
        this.contractNatureService = contractNatureService;
        this.contractNatureQueryService = contractNatureQueryService;
    }

    /**
     * POST  /contract-natures : Create a new contractNature.
     *
     * @param contractNature the contractNature to create
     * @return the ResponseEntity with status 201 (Created) and with body the new contractNature, or with status 400 (Bad Request) if the contractNature has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/contract-natures")
    @Timed
    public ResponseEntity<ContractNature> createContractNature(@RequestBody ContractNature contractNature) throws URISyntaxException {
        log.debug("REST request to save ContractNature : {}", contractNature);
        if (contractNature.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new contractNature cannot already have an ID")).body(null);
        }
        ContractNature result = contractNatureService.save(contractNature);
        return ResponseEntity.created(new URI("/api/contract-natures/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /contract-natures : Updates an existing contractNature.
     *
     * @param contractNature the contractNature to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated contractNature,
     * or with status 400 (Bad Request) if the contractNature is not valid,
     * or with status 500 (Internal Server Error) if the contractNature couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/contract-natures")
    @Timed
    public ResponseEntity<ContractNature> updateContractNature(@RequestBody ContractNature contractNature) throws URISyntaxException {
        log.debug("REST request to update ContractNature : {}", contractNature);
        if (contractNature.getId() == null) {
            return createContractNature(contractNature);
        }
        ContractNature result = contractNatureService.save(contractNature);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, contractNature.getId().toString()))
            .body(result);
    }

    /**
     * GET  /contract-natures : get all the contractNatures.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of contractNatures in body
     */
    @GetMapping("/contract-natures")
    @Timed
    public ResponseEntity<List<ContractNature>> getAllContractNatures(ContractNatureCriteria criteria,@ApiParam Pageable pageable) {
        log.debug("REST request to get ContractNatures by criteria: {}", criteria);
        Page<ContractNature> page = contractNatureQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/contract-natures");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /contract-natures/:id : get the "id" contractNature.
     *
     * @param id the id of the contractNature to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the contractNature, or with status 404 (Not Found)
     */
    @GetMapping("/contract-natures/{id}")
    @Timed
    public ResponseEntity<ContractNature> getContractNature(@PathVariable Long id) {
        log.debug("REST request to get ContractNature : {}", id);
        ContractNature contractNature = contractNatureService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(contractNature));
    }

    /**
     * DELETE  /contract-natures/:id : delete the "id" contractNature.
     *
     * @param id the id of the contractNature to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/contract-natures/{id}")
    @Timed
    public ResponseEntity<Void> deleteContractNature(@PathVariable Long id) {
        log.debug("REST request to delete ContractNature : {}", id);
        contractNatureService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
