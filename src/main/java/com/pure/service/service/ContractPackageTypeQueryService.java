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

import com.pure.service.domain.ContractPackageType;
import com.pure.service.domain.*; // for static metamodels
import com.pure.service.repository.ContractPackageTypeRepository;
import com.pure.service.service.dto.ContractPackageTypeCriteria;


/**
 * Service for executing complex queries for ContractPackageType entities in the database.
 * The main input is a {@link ContractPackageTypeCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {%link ContractPackageType} or a {@link Page} of {%link ContractPackageType} which fulfills the criterias
 */
@Service
@Transactional(readOnly = true)
public class ContractPackageTypeQueryService extends QueryService<ContractPackageType> {

    private final Logger log = LoggerFactory.getLogger(ContractPackageTypeQueryService.class);


    private final ContractPackageTypeRepository contractPackageTypeRepository;

    public ContractPackageTypeQueryService(ContractPackageTypeRepository contractPackageTypeRepository) {
        this.contractPackageTypeRepository = contractPackageTypeRepository;
    }

    /**
     * Return a {@link List} of {%link ContractPackageType} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ContractPackageType> findByCriteria(ContractPackageTypeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<ContractPackageType> specification = createSpecification(criteria);
        return contractPackageTypeRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {%link ContractPackageType} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ContractPackageType> findByCriteria(ContractPackageTypeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<ContractPackageType> specification = createSpecification(criteria);
        return contractPackageTypeRepository.findAll(specification, page);
    }

    /**
     * Function to convert ContractPackageTypeCriteria to a {@link Specifications}
     */
    private Specifications<ContractPackageType> createSpecification(ContractPackageTypeCriteria criteria) {
        Specifications<ContractPackageType> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ContractPackageType_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), ContractPackageType_.name));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), ContractPackageType_.code));
            }
            if (criteria.getComments() != null) {
                specification = specification.and(buildStringSpecification(criteria.getComments(), ContractPackageType_.comments));
            }
        }
        return specification;
    }

}
