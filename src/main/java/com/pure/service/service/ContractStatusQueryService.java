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

import com.pure.service.domain.ContractStatus;
import com.pure.service.domain.*; // for static metamodels
import com.pure.service.repository.ContractStatusRepository;
import com.pure.service.service.dto.ContractStatusCriteria;


/**
 * Service for executing complex queries for ContractStatus entities in the database.
 * The main input is a {@link ContractStatusCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {%link ContractStatus} or a {@link Page} of {%link ContractStatus} which fulfills the criterias
 */
@Service
@Transactional(readOnly = true)
public class ContractStatusQueryService extends QueryService<ContractStatus> {

    private final Logger log = LoggerFactory.getLogger(ContractStatusQueryService.class);


    private final ContractStatusRepository contractStatusRepository;

    public ContractStatusQueryService(ContractStatusRepository contractStatusRepository) {
        this.contractStatusRepository = contractStatusRepository;
    }

    /**
     * Return a {@link List} of {%link ContractStatus} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ContractStatus> findByCriteria(ContractStatusCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<ContractStatus> specification = createSpecification(criteria);
        return contractStatusRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {%link ContractStatus} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ContractStatus> findByCriteria(ContractStatusCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<ContractStatus> specification = createSpecification(criteria);
        return contractStatusRepository.findAll(specification, page);
    }

    /**
     * Function to convert ContractStatusCriteria to a {@link Specifications}
     */
    private Specifications<ContractStatus> createSpecification(ContractStatusCriteria criteria) {
        Specifications<ContractStatus> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ContractStatus_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), ContractStatus_.name));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), ContractStatus_.code));
            }
        }
        return specification;
    }

}
