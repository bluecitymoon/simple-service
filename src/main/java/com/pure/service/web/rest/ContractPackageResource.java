package com.pure.service.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pure.service.domain.ContractPackage;
import com.pure.service.repository.ContractPackageRepository;
import com.pure.service.service.ContractPackageQueryService;
import com.pure.service.service.ContractPackageService;
import com.pure.service.service.dto.ContractPackageCriteria;
import com.pure.service.web.rest.util.HeaderUtil;
import com.pure.service.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ContractPackage.
 */
@RestController
@RequestMapping("/api")
public class ContractPackageResource {

    private final Logger log = LoggerFactory.getLogger(ContractPackageResource.class);

    private static final String ENTITY_NAME = "contractPackage";

    private final ContractPackageService contractPackageService;

    private final ContractPackageQueryService contractPackageQueryService;

    @Autowired
    private ContractPackageRepository contractPackageRepository;

    public ContractPackageResource(ContractPackageService contractPackageService, ContractPackageQueryService contractPackageQueryService) {
        this.contractPackageService = contractPackageService;
        this.contractPackageQueryService = contractPackageQueryService;
    }

    /**
     * POST  /contract-packages : Create a new contractPackage.
     *
     * @param contractPackage the contractPackage to create
     * @return the ResponseEntity with status 201 (Created) and with body the new contractPackage, or with status 400 (Bad Request) if the contractPackage has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/contract-packages")
    @Timed
    public ResponseEntity<ContractPackage> createContractPackage(@RequestBody ContractPackage contractPackage) throws URISyntaxException {
        log.debug("REST request to save ContractPackage : {}", contractPackage);
        if (contractPackage.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new contractPackage cannot already have an ID")).body(null);
        }
        ContractPackage result = contractPackageService.save(contractPackage);
        return ResponseEntity.created(new URI("/api/contract-packages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /contract-packages : Updates an existing contractPackage.
     *
     * @param contractPackage the contractPackage to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated contractPackage,
     * or with status 400 (Bad Request) if the contractPackage is not valid,
     * or with status 500 (Internal Server Error) if the contractPackage couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/contract-packages")
    @Timed
    public ResponseEntity<ContractPackage> updateContractPackage(@RequestBody ContractPackage contractPackage) throws URISyntaxException {
        log.debug("REST request to update ContractPackage : {}", contractPackage);
        if (contractPackage.getId() == null) {
            return createContractPackage(contractPackage);
        }
        ContractPackage result = contractPackageService.save(contractPackage);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, contractPackage.getId().toString()))
            .body(result);
    }

    /**
     * GET  /contract-packages : get all the contractPackages.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of contractPackages in body
     */
    @GetMapping("/contract-packages")
    @Timed
    public ResponseEntity<List<ContractPackage>> getAllContractPackages(ContractPackageCriteria criteria,@ApiParam Pageable pageable) {
        log.debug("REST request to get ContractPackages by criteria: {}", criteria);
        Page<ContractPackage> page = contractPackageQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/contract-packages");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/contract-packages/all-renew-packages")
    @Timed
    public ResponseEntity<List<ContractPackage>> getAllRenewContractPackages() {

        List<ContractPackage> page = contractPackageRepository.findByType_Code("renew");

        return new ResponseEntity<>(page, null, HttpStatus.OK);
    }


    /**
     * GET  /contract-packages/:id : get the "id" contractPackage.
     *
     * @param id the id of the contractPackage to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the contractPackage, or with status 404 (Not Found)
     */
    @GetMapping("/contract-packages/{id}")
    @Timed
    public ResponseEntity<ContractPackage> getContractPackage(@PathVariable Long id) {
        log.debug("REST request to get ContractPackage : {}", id);
        ContractPackage contractPackage = contractPackageService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(contractPackage));
    }

    /**
     * DELETE  /contract-packages/:id : delete the "id" contractPackage.
     *
     * @param id the id of the contractPackage to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/contract-packages/{id}")
    @Timed
    public ResponseEntity<Void> deleteContractPackage(@PathVariable Long id) {
        log.debug("REST request to delete ContractPackage : {}", id);
        contractPackageService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
