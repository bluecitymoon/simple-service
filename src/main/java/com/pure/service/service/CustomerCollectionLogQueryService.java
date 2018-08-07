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

import com.pure.service.domain.CustomerCollectionLog;
import com.pure.service.domain.*; // for static metamodels
import com.pure.service.repository.CustomerCollectionLogRepository;
import com.pure.service.service.dto.CustomerCollectionLogCriteria;


/**
 * Service for executing complex queries for CustomerCollectionLog entities in the database.
 * The main input is a {@link CustomerCollectionLogCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {%link CustomerCollectionLog} or a {@link Page} of {%link CustomerCollectionLog} which fulfills the criterias
 */
@Service
@Transactional(readOnly = true)
public class CustomerCollectionLogQueryService extends QueryService<CustomerCollectionLog> {

    private final Logger log = LoggerFactory.getLogger(CustomerCollectionLogQueryService.class);


    private final CustomerCollectionLogRepository customerCollectionLogRepository;

    public CustomerCollectionLogQueryService(CustomerCollectionLogRepository customerCollectionLogRepository) {
        this.customerCollectionLogRepository = customerCollectionLogRepository;
    }

    /**
     * Return a {@link List} of {%link CustomerCollectionLog} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CustomerCollectionLog> findByCriteria(CustomerCollectionLogCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<CustomerCollectionLog> specification = createSpecification(criteria);
        return customerCollectionLogRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {%link CustomerCollectionLog} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CustomerCollectionLog> findByCriteria(CustomerCollectionLogCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<CustomerCollectionLog> specification = createSpecification(criteria);
        return customerCollectionLogRepository.findAll(specification, page);
    }

    /**
     * Function to convert CustomerCollectionLogCriteria to a {@link Specifications}
     */
    private Specifications<CustomerCollectionLog> createSpecification(CustomerCollectionLogCriteria criteria) {
        Specifications<CustomerCollectionLog> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), CustomerCollectionLog_.id));
            }
            if (criteria.getSerialNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSerialNumber(), CustomerCollectionLog_.serialNumber));
            }
            if (criteria.getMoneyShouldCollected() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMoneyShouldCollected(), CustomerCollectionLog_.moneyShouldCollected));
            }
            if (criteria.getMoneyCollected() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMoneyCollected(), CustomerCollectionLog_.moneyCollected));
            }
            if (criteria.getBalance() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBalance(), CustomerCollectionLog_.balance));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), CustomerCollectionLog_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), CustomerCollectionLog_.createdDate));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), CustomerCollectionLog_.lastModifiedBy));
            }
            if (criteria.getLastModifiedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModifiedDate(), CustomerCollectionLog_.lastModifiedDate));
            }
            if (criteria.getCollectionId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getCollectionId(), CustomerCollectionLog_.collection, Collection_.id));
            }
            if (criteria.getCustomerId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getCustomerId(), CustomerCollectionLog_.customer, Customer_.id));
            }
        }
        return specification;
    }

}
