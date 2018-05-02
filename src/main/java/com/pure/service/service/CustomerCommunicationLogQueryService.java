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

import com.pure.service.domain.CustomerCommunicationLog;
import com.pure.service.domain.*; // for static metamodels
import com.pure.service.repository.CustomerCommunicationLogRepository;
import com.pure.service.service.dto.CustomerCommunicationLogCriteria;


/**
 * Service for executing complex queries for CustomerCommunicationLog entities in the database.
 * The main input is a {@link CustomerCommunicationLogCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {%link CustomerCommunicationLog} or a {@link Page} of {%link CustomerCommunicationLog} which fulfills the criterias
 */
@Service
@Transactional(readOnly = true)
public class CustomerCommunicationLogQueryService extends QueryService<CustomerCommunicationLog> {

    private final Logger log = LoggerFactory.getLogger(CustomerCommunicationLogQueryService.class);


    private final CustomerCommunicationLogRepository customerCommunicationLogRepository;

    public CustomerCommunicationLogQueryService(CustomerCommunicationLogRepository customerCommunicationLogRepository) {
        this.customerCommunicationLogRepository = customerCommunicationLogRepository;
    }

    /**
     * Return a {@link List} of {%link CustomerCommunicationLog} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CustomerCommunicationLog> findByCriteria(CustomerCommunicationLogCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<CustomerCommunicationLog> specification = createSpecification(criteria);
        return customerCommunicationLogRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {%link CustomerCommunicationLog} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CustomerCommunicationLog> findByCriteria(CustomerCommunicationLogCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<CustomerCommunicationLog> specification = createSpecification(criteria);
        return customerCommunicationLogRepository.findAll(specification, page);
    }

    /**
     * Function to convert CustomerCommunicationLogCriteria to a {@link Specifications}
     */
    private Specifications<CustomerCommunicationLog> createSpecification(CustomerCommunicationLogCriteria criteria) {
        Specifications<CustomerCommunicationLog> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), CustomerCommunicationLog_.id));
            }
            if (criteria.getComments() != null) {
                specification = specification.and(buildStringSpecification(criteria.getComments(), CustomerCommunicationLog_.comments));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), CustomerCommunicationLog_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), CustomerCommunicationLog_.createdDate));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), CustomerCommunicationLog_.lastModifiedBy));
            }
            if (criteria.getLastModifiedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModifiedDate(), CustomerCommunicationLog_.lastModifiedDate));
            }
            if (criteria.getLogTypeId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getLogTypeId(), CustomerCommunicationLog_.logType, CustomerCommunicationLogType_.id));
            }
            if (criteria.getCustomerId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getCustomerId(), CustomerCommunicationLog_.customer, Customer_.id));
            }
            if (criteria.getFreeClassRecordId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getFreeClassRecordId(), CustomerCommunicationLog_.freeClassRecord, FreeClassRecord_.id));
            }
        }
        return specification;
    }

}
