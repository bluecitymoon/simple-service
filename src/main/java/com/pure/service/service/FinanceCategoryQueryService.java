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

import com.pure.service.domain.FinanceCategory;
import com.pure.service.domain.*; // for static metamodels
import com.pure.service.repository.FinanceCategoryRepository;
import com.pure.service.service.dto.FinanceCategoryCriteria;


/**
 * Service for executing complex queries for FinanceCategory entities in the database.
 * The main input is a {@link FinanceCategoryCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {%link FinanceCategory} or a {@link Page} of {%link FinanceCategory} which fulfills the criterias
 */
@Service
@Transactional(readOnly = true)
public class FinanceCategoryQueryService extends QueryService<FinanceCategory> {

    private final Logger log = LoggerFactory.getLogger(FinanceCategoryQueryService.class);


    private final FinanceCategoryRepository financeCategoryRepository;

    public FinanceCategoryQueryService(FinanceCategoryRepository financeCategoryRepository) {
        this.financeCategoryRepository = financeCategoryRepository;
    }

    /**
     * Return a {@link List} of {%link FinanceCategory} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<FinanceCategory> findByCriteria(FinanceCategoryCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<FinanceCategory> specification = createSpecification(criteria);
        return financeCategoryRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {%link FinanceCategory} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<FinanceCategory> findByCriteria(FinanceCategoryCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<FinanceCategory> specification = createSpecification(criteria);
        return financeCategoryRepository.findAll(specification, page);
    }

    /**
     * Function to convert FinanceCategoryCriteria to a {@link Specifications}
     */
    private Specifications<FinanceCategory> createSpecification(FinanceCategoryCriteria criteria) {
        Specifications<FinanceCategory> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), FinanceCategory_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), FinanceCategory_.name));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), FinanceCategory_.code));
            }
            if (criteria.getComments() != null) {
                specification = specification.and(buildStringSpecification(criteria.getComments(), FinanceCategory_.comments));
            }
        }
        return specification;
    }

}
