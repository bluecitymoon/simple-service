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

import com.pure.service.domain.CustomerStatus;
import com.pure.service.domain.*; // for static metamodels
import com.pure.service.repository.CustomerStatusRepository;
import com.pure.service.service.dto.CustomerStatusCriteria;


/**
 * Service for executing complex queries for CustomerStatus entities in the database.
 * The main input is a {@link CustomerStatusCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {%link CustomerStatus} or a {@link Page} of {%link CustomerStatus} which fulfills the criterias
 */
@Service
@Transactional(readOnly = true)
public class CustomerStatusQueryService extends QueryService<CustomerStatus> {

    private final Logger log = LoggerFactory.getLogger(CustomerStatusQueryService.class);


    private final CustomerStatusRepository customerStatusRepository;

    public CustomerStatusQueryService(CustomerStatusRepository customerStatusRepository) {
        this.customerStatusRepository = customerStatusRepository;
    }

    /**
     * Return a {@link List} of {%link CustomerStatus} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CustomerStatus> findByCriteria(CustomerStatusCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<CustomerStatus> specification = createSpecification(criteria);
        return customerStatusRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {%link CustomerStatus} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CustomerStatus> findByCriteria(CustomerStatusCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<CustomerStatus> specification = createSpecification(criteria);
        return customerStatusRepository.findAll(specification, page);
    }

    /**
     * Function to convert CustomerStatusCriteria to a {@link Specifications}
     */
    private Specifications<CustomerStatus> createSpecification(CustomerStatusCriteria criteria) {
        Specifications<CustomerStatus> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), CustomerStatus_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), CustomerStatus_.name));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), CustomerStatus_.code));
            }
            if (criteria.getComments() != null) {
                specification = specification.and(buildStringSpecification(criteria.getComments(), CustomerStatus_.comments));
            }
            if (criteria.getCssStyle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCssStyle(), CustomerStatus_.cssStyle));
            }
            if (criteria.getIcon() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIcon(), CustomerStatus_.icon));
            }
        }
        return specification;
    }

}
