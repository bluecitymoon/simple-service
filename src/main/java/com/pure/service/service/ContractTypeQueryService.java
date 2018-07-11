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

import com.pure.service.domain.ContractType;
import com.pure.service.domain.*; // for static metamodels
import com.pure.service.repository.ContractTypeRepository;
import com.pure.service.service.dto.ContractTypeCriteria;


/**
 * Service for executing complex queries for ContractType entities in the database.
 * The main input is a {@link ContractTypeCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {%link ContractType} or a {@link Page} of {%link ContractType} which fulfills the criterias
 */
@Service
@Transactional(readOnly = true)
public class ContractTypeQueryService extends QueryService<ContractType> {

    private final Logger log = LoggerFactory.getLogger(ContractTypeQueryService.class);


    private final ContractTypeRepository contractTypeRepository;

    public ContractTypeQueryService(ContractTypeRepository contractTypeRepository) {
        this.contractTypeRepository = contractTypeRepository;
    }

    /**
     * Return a {@link List} of {%link ContractType} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ContractType> findByCriteria(ContractTypeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<ContractType> specification = createSpecification(criteria);
        return contractTypeRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {%link ContractType} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ContractType> findByCriteria(ContractTypeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<ContractType> specification = createSpecification(criteria);
        return contractTypeRepository.findAll(specification, page);
    }

    /**
     * Function to convert ContractTypeCriteria to a {@link Specifications}
     */
    private Specifications<ContractType> createSpecification(ContractTypeCriteria criteria) {
        Specifications<ContractType> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ContractType_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), ContractType_.name));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), ContractType_.code));
            }
        }
        return specification;
    }

}
