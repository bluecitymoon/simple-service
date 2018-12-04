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

import com.pure.service.domain.CustomerCousultantAssignHistory;
import com.pure.service.domain.*; // for static metamodels
import com.pure.service.repository.CustomerCousultantAssignHistoryRepository;
import com.pure.service.service.dto.CustomerCousultantAssignHistoryCriteria;


/**
 * Service for executing complex queries for CustomerCousultantAssignHistory entities in the database.
 * The main input is a {@link CustomerCousultantAssignHistoryCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {%link CustomerCousultantAssignHistory} or a {@link Page} of {%link CustomerCousultantAssignHistory} which fulfills the criterias
 */
@Service
@Transactional(readOnly = true)
public class CustomerCousultantAssignHistoryQueryService extends QueryService<CustomerCousultantAssignHistory> {

    private final Logger log = LoggerFactory.getLogger(CustomerCousultantAssignHistoryQueryService.class);


    private final CustomerCousultantAssignHistoryRepository customerCousultantAssignHistoryRepository;

    public CustomerCousultantAssignHistoryQueryService(CustomerCousultantAssignHistoryRepository customerCousultantAssignHistoryRepository) {
        this.customerCousultantAssignHistoryRepository = customerCousultantAssignHistoryRepository;
    }

    /**
     * Return a {@link List} of {%link CustomerCousultantAssignHistory} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CustomerCousultantAssignHistory> findByCriteria(CustomerCousultantAssignHistoryCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<CustomerCousultantAssignHistory> specification = createSpecification(criteria);
        return customerCousultantAssignHistoryRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {%link CustomerCousultantAssignHistory} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CustomerCousultantAssignHistory> findByCriteria(CustomerCousultantAssignHistoryCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<CustomerCousultantAssignHistory> specification = createSpecification(criteria);
        return customerCousultantAssignHistoryRepository.findAll(specification, page);
    }

    /**
     * Function to convert CustomerCousultantAssignHistoryCriteria to a {@link Specifications}
     */
    private Specifications<CustomerCousultantAssignHistory> createSpecification(CustomerCousultantAssignHistoryCriteria criteria) {
        Specifications<CustomerCousultantAssignHistory> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), CustomerCousultantAssignHistory_.id));
            }
            if (criteria.getOlderFollowerLogin() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOlderFollowerLogin(), CustomerCousultantAssignHistory_.olderFollowerLogin));
            }
            if (criteria.getOlderFollowerName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOlderFollowerName(), CustomerCousultantAssignHistory_.olderFollowerName));
            }
            if (criteria.getNewFollowerLogin() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNewFollowerLogin(), CustomerCousultantAssignHistory_.newFollowerLogin));
            }
            if (criteria.getNewFollowerName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNewFollowerName(), CustomerCousultantAssignHistory_.newFollowerName));
            }
            if (criteria.getComments() != null) {
                specification = specification.and(buildStringSpecification(criteria.getComments(), CustomerCousultantAssignHistory_.comments));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), CustomerCousultantAssignHistory_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), CustomerCousultantAssignHistory_.createdDate));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), CustomerCousultantAssignHistory_.lastModifiedBy));
            }
            if (criteria.getLastModifiedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModifiedDate(), CustomerCousultantAssignHistory_.lastModifiedDate));
            }
            if (criteria.getCustomerId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getCustomerId(), CustomerCousultantAssignHistory_.customer, Customer_.id));
            }
        }
        return specification;
    }

}
