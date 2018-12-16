package com.pure.service.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pure.service.domain.Contract;
import com.pure.service.domain.ContractNature;
import com.pure.service.domain.User;
import com.pure.service.region.RegionBasedInsert;
import com.pure.service.region.RegionBasedQuery;
import com.pure.service.repository.ContractNatureRepository;
import com.pure.service.security.SecurityUtils;
import com.pure.service.service.ContractQueryService;
import com.pure.service.service.ContractService;
import com.pure.service.service.UserService;
import com.pure.service.service.dto.ContractCriteria;
import com.pure.service.service.dto.dto.ConsultantWork;
import com.pure.service.service.dto.dto.PackageContractRequest;
import com.pure.service.service.dto.request.CustomerStatusRequest;
import com.pure.service.service.exception.CollectionNotPaidException;
import com.pure.service.service.exception.ContractsExceedLimitException;
import com.pure.service.service.exception.TemplateNotFoundException;
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
import org.springframework.util.StringUtils;
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

import static com.pure.service.service.util.DateUtil.preProccessStatusRequest;

/**
 * REST controller for managing Contract.
 */
@RestController
@RequestMapping("/api")
public class ContractResource {

    private final Logger log = LoggerFactory.getLogger(ContractResource.class);

    private static final String ENTITY_NAME = "contract";

    private final ContractService contractService;

    private final ContractQueryService contractQueryService;

    @Autowired
    private ContractNatureRepository contractNatureRepository;

    @Autowired
    private UserService userService;

    public ContractResource(ContractService contractService, ContractQueryService contractQueryService) {
        this.contractService = contractService;
        this.contractQueryService = contractQueryService;
    }

    /**
     * POST  /contracts : Create a new contract.
     *
     * @param contract the contract to create
     * @return the ResponseEntity with status 201 (Created) and with body the new contract, or with status 400 (Bad Request) if the contract has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/contracts")
    @Timed
    @RegionBasedInsert
    public ResponseEntity<Contract> createContract(@RequestBody Contract contract) throws URISyntaxException, CollectionNotPaidException {
        log.debug("REST request to save Contract : {}", contract);
        if (contract.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new contract cannot already have an ID")).body(null);
        }
        Contract result = contractService.save(contract);
        return ResponseEntity.created(new URI("/api/contracts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PostMapping("/contracts/free")
    @Timed
    @RegionBasedInsert
    public ResponseEntity<Contract> createFreeContract(@RequestBody Contract contract) throws URISyntaxException, CollectionNotPaidException {
        log.debug("REST request to save Contract : {}", contract);
        if (contract.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new contract cannot already have an ID")).body(null);
        }

        ContractNature free = contractNatureRepository.findByCode("free");
        contract.setContractNature(free);

        Contract result = contractService.save(contract);
        return ResponseEntity.created(new URI("/api/contracts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PostMapping("/contracts/package")
    @Timed
    public ResponseEntity<List<Contract>> createPackageContract(@RequestBody PackageContractRequest packageContractRequest) throws CollectionNotPaidException, ContractsExceedLimitException {

        log.debug("REST request to save Contract in package : {}", packageContractRequest);

        List<Contract> result;

        try {
            result = contractService.generatePackagedContract(packageContractRequest);
        } catch (TemplateNotFoundException e) {

            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "templatenotexists", e.getMessage())).body(null);

        }

        return ResponseEntity.ok().body(result);
    }

    /**
     * PUT  /contracts : Updates an existing contract.
     *
     * @param contract the contract to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated contract,
     * or with status 400 (Bad Request) if the contract is not valid,
     * or with status 500 (Internal Server Error) if the contract couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/contracts")
    @Timed
    public ResponseEntity<Contract> updateContract(@RequestBody Contract contract) throws URISyntaxException, CollectionNotPaidException {
        log.debug("REST request to update Contract : {}", contract);
        if (contract.getId() == null) {
            return createContract(contract);
        }
        Contract result = contractService.save(contract);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, contract.getId().toString()))
            .body(result);
    }

    /**
     * GET  /contracts : get all the contracts.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of contracts in body
     */
    @GetMapping("/contracts")
    @Timed
    @RegionBasedQuery
    public ResponseEntity<List<Contract>> getAllContracts(ContractCriteria criteria,@ApiParam Pageable pageable) {
        log.debug("REST request to get Contracts by criteria: {}", criteria);

        if (!SecurityUtils.isCurrentUserHeadmasterOrAdmin()) {

            User currentUser = userService.getUserWithAuthorities();

            criteria.setFollowerId(currentUser.getId());
        }

        Page<Contract> page = contractQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/contracts");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /contracts/:id : get the "id" contract.
     *
     * @param id the id of the contract to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the contract, or with status 404 (Not Found)
     */
    @GetMapping("/contracts/{id}")
    @Timed
    public ResponseEntity<Contract> getContract(@PathVariable Long id) {
        log.debug("REST request to get Contract : {}", id);
        Contract contract = contractService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(contract));
    }

    /**
     * DELETE  /contracts/:id : delete the "id" contract.
     *
     * @param id the id of the contract to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/contracts/{id}")
    @Timed
    public ResponseEntity<Void> deleteContract(@PathVariable Long id) {
        log.debug("REST request to delete Contract : {}", id);
        contractService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @PostMapping("/contracts/consultant-work-report")
    @Timed
    public ResponseEntity<List<ConsultantWork>> getCourseConsultantWorkReport(@RequestBody CustomerStatusRequest request) {

        switch (request.getQueryType()) {
            case "monthly":

                if (StringUtils.isEmpty(request.getYear()) || StringUtils.isEmpty(request.getMonth())) {
                    return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "conditionneeded", "请输入搜索条件")).body(null);
                }

                preProccessStatusRequest(request);

                break;
            case "dateRange":

                if (StringUtils.isEmpty(request.getStartDate()) || StringUtils.isEmpty(request.getEndDate())) {
                    return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "conditionneeded", "请输入搜索条件")).body(null);
                }
                break;
            default:
                break;
        }

        List<ConsultantWork> reports = contractService.getCourseConsultantWorkReport(request);

        return new ResponseEntity<>(reports, null, HttpStatus.OK);
    }
}
