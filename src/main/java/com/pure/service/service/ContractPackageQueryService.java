package com.pure.service.service;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.pure.service.domain.ContractPackage;
import com.pure.service.domain.*; // for static metamodels
import com.pure.service.repository.ContractPackageRepository;
import com.pure.service.service.dto.ContractPackageCriteria;


/**
 * Service for executing complex queries for ContractPackage entities in the database.
 * The main input is a {@link ContractPackageCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {%link ContractPackage} or a {@link Page} of {%link ContractPackage} which fulfills the criterias
 */
@Service
@Transactional(readOnly = true)
public class ContractPackageQueryService extends QueryService<ContractPackage> {

    private final Logger log = LoggerFactory.getLogger(ContractPackageQueryService.class);


    private final ContractPackageRepository contractPackageRepository;

    public ContractPackageQueryService(ContractPackageRepository contractPackageRepository) {
        this.contractPackageRepository = contractPackageRepository;
    }

    /**
     * Return a {@link List} of {%link ContractPackage} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ContractPackage> findByCriteria(ContractPackageCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<ContractPackage> specification = createSpecification(criteria);
        return contractPackageRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {%link ContractPackage} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ContractPackage> findByCriteria(ContractPackageCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<ContractPackage> specification = createSpecification(criteria);
        return contractPackageRepository.findAll(specification, page);
    }

    /**
     * Function to convert ContractPackageCriteria to a {@link Specifications}
     */
    private Specifications<ContractPackage> createSpecification(ContractPackageCriteria criteria) {
        Specifications<ContractPackage> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ContractPackage_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), ContractPackage_.name));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), ContractPackage_.code));
            }
            if (criteria.getComments() != null) {
                specification = specification.and(buildStringSpecification(criteria.getComments(), ContractPackage_.comments));
            }
            if (criteria.getCustomerCardTypeId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getCustomerCardTypeId(), ContractPackage_.customerCardType, CustomerCardType_.id));
            }
            if (criteria.getTypeId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getTypeId(), ContractPackage_.type, ContractPackageType_.id));
            }
        }
        return specification;
    }

}
