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

import com.pure.service.domain.ContractNature;
import com.pure.service.domain.*; // for static metamodels
import com.pure.service.repository.ContractNatureRepository;
import com.pure.service.service.dto.ContractNatureCriteria;


/**
 * Service for executing complex queries for ContractNature entities in the database.
 * The main input is a {@link ContractNatureCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {%link ContractNature} or a {@link Page} of {%link ContractNature} which fulfills the criterias
 */
@Service
@Transactional(readOnly = true)
public class ContractNatureQueryService extends QueryService<ContractNature> {

    private final Logger log = LoggerFactory.getLogger(ContractNatureQueryService.class);


    private final ContractNatureRepository contractNatureRepository;

    public ContractNatureQueryService(ContractNatureRepository contractNatureRepository) {
        this.contractNatureRepository = contractNatureRepository;
    }

    /**
     * Return a {@link List} of {%link ContractNature} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ContractNature> findByCriteria(ContractNatureCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<ContractNature> specification = createSpecification(criteria);
        return contractNatureRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {%link ContractNature} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ContractNature> findByCriteria(ContractNatureCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<ContractNature> specification = createSpecification(criteria);
        return contractNatureRepository.findAll(specification, page);
    }

    /**
     * Function to convert ContractNatureCriteria to a {@link Specifications}
     */
    private Specifications<ContractNature> createSpecification(ContractNatureCriteria criteria) {
        Specifications<ContractNature> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ContractNature_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), ContractNature_.name));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), ContractNature_.code));
            }
        }
        return specification;
    }

}
