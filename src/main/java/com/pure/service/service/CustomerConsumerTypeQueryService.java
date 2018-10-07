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

import com.pure.service.domain.CustomerConsumerType;
import com.pure.service.domain.*; // for static metamodels
import com.pure.service.repository.CustomerConsumerTypeRepository;
import com.pure.service.service.dto.CustomerConsumerTypeCriteria;


/**
 * Service for executing complex queries for CustomerConsumerType entities in the database.
 * The main input is a {@link CustomerConsumerTypeCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {%link CustomerConsumerType} or a {@link Page} of {%link CustomerConsumerType} which fulfills the criterias
 */
@Service
@Transactional(readOnly = true)
public class CustomerConsumerTypeQueryService extends QueryService<CustomerConsumerType> {

    private final Logger log = LoggerFactory.getLogger(CustomerConsumerTypeQueryService.class);


    private final CustomerConsumerTypeRepository customerConsumerTypeRepository;

    public CustomerConsumerTypeQueryService(CustomerConsumerTypeRepository customerConsumerTypeRepository) {
        this.customerConsumerTypeRepository = customerConsumerTypeRepository;
    }

    /**
     * Return a {@link List} of {%link CustomerConsumerType} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CustomerConsumerType> findByCriteria(CustomerConsumerTypeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<CustomerConsumerType> specification = createSpecification(criteria);
        return customerConsumerTypeRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {%link CustomerConsumerType} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CustomerConsumerType> findByCriteria(CustomerConsumerTypeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<CustomerConsumerType> specification = createSpecification(criteria);
        return customerConsumerTypeRepository.findAll(specification, page);
    }

    /**
     * Function to convert CustomerConsumerTypeCriteria to a {@link Specifications}
     */
    private Specifications<CustomerConsumerType> createSpecification(CustomerConsumerTypeCriteria criteria) {
        Specifications<CustomerConsumerType> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), CustomerConsumerType_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), CustomerConsumerType_.name));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), CustomerConsumerType_.code));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), CustomerConsumerType_.description));
            }
        }
        return specification;
    }

}
