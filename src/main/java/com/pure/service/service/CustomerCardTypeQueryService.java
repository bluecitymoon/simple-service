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

import com.pure.service.domain.CustomerCardType;
import com.pure.service.domain.*; // for static metamodels
import com.pure.service.repository.CustomerCardTypeRepository;
import com.pure.service.service.dto.CustomerCardTypeCriteria;


/**
 * Service for executing complex queries for CustomerCardType entities in the database.
 * The main input is a {@link CustomerCardTypeCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {%link CustomerCardType} or a {@link Page} of {%link CustomerCardType} which fulfills the criterias
 */
@Service
@Transactional(readOnly = true)
public class CustomerCardTypeQueryService extends QueryService<CustomerCardType> {

    private final Logger log = LoggerFactory.getLogger(CustomerCardTypeQueryService.class);


    private final CustomerCardTypeRepository customerCardTypeRepository;

    public CustomerCardTypeQueryService(CustomerCardTypeRepository customerCardTypeRepository) {
        this.customerCardTypeRepository = customerCardTypeRepository;
    }

    /**
     * Return a {@link List} of {%link CustomerCardType} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CustomerCardType> findByCriteria(CustomerCardTypeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<CustomerCardType> specification = createSpecification(criteria);
        return customerCardTypeRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {%link CustomerCardType} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CustomerCardType> findByCriteria(CustomerCardTypeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<CustomerCardType> specification = createSpecification(criteria);
        return customerCardTypeRepository.findAll(specification, page);
    }

    /**
     * Function to convert CustomerCardTypeCriteria to a {@link Specifications}
     */
    private Specifications<CustomerCardType> createSpecification(CustomerCardTypeCriteria criteria) {
        Specifications<CustomerCardType> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), CustomerCardType_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), CustomerCardType_.name));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), CustomerCardType_.code));
            }
            if (criteria.getTotalMoneyAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalMoneyAmount(), CustomerCardType_.totalMoneyAmount));
            }
            if (criteria.getClassCount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getClassCount(), CustomerCardType_.classCount));
            }
            if (criteria.getTotalMinutes() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalMinutes(), CustomerCardType_.totalMinutes));
            }
        }
        return specification;
    }

}
