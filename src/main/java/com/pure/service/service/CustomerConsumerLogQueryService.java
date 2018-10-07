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

import com.pure.service.domain.CustomerConsumerLog;
import com.pure.service.domain.*; // for static metamodels
import com.pure.service.repository.CustomerConsumerLogRepository;
import com.pure.service.service.dto.CustomerConsumerLogCriteria;


/**
 * Service for executing complex queries for CustomerConsumerLog entities in the database.
 * The main input is a {@link CustomerConsumerLogCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {%link CustomerConsumerLog} or a {@link Page} of {%link CustomerConsumerLog} which fulfills the criterias
 */
@Service
@Transactional(readOnly = true)
public class CustomerConsumerLogQueryService extends QueryService<CustomerConsumerLog> {

    private final Logger log = LoggerFactory.getLogger(CustomerConsumerLogQueryService.class);


    private final CustomerConsumerLogRepository customerConsumerLogRepository;

    public CustomerConsumerLogQueryService(CustomerConsumerLogRepository customerConsumerLogRepository) {
        this.customerConsumerLogRepository = customerConsumerLogRepository;
    }

    /**
     * Return a {@link List} of {%link CustomerConsumerLog} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CustomerConsumerLog> findByCriteria(CustomerConsumerLogCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<CustomerConsumerLog> specification = createSpecification(criteria);
        return customerConsumerLogRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {%link CustomerConsumerLog} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CustomerConsumerLog> findByCriteria(CustomerConsumerLogCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<CustomerConsumerLog> specification = createSpecification(criteria);
        return customerConsumerLogRepository.findAll(specification, page);
    }

    /**
     * Function to convert CustomerConsumerLogCriteria to a {@link Specifications}
     */
    private Specifications<CustomerConsumerLog> createSpecification(CustomerConsumerLogCriteria criteria) {
        Specifications<CustomerConsumerLog> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), CustomerConsumerLog_.id));
            }
            if (criteria.getCount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCount(), CustomerConsumerLog_.count));
            }
            if (criteria.getUnit() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUnit(), CustomerConsumerLog_.unit));
            }
            if (criteria.getUniqueNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUniqueNumber(), CustomerConsumerLog_.uniqueNumber));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), CustomerConsumerLog_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), CustomerConsumerLog_.createdDate));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), CustomerConsumerLog_.lastModifiedBy));
            }
            if (criteria.getLastModifiedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModifiedDate(), CustomerConsumerLog_.lastModifiedDate));
            }
            if (criteria.getConsumerName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getConsumerName(), CustomerConsumerLog_.consumerName));
            }
            if (criteria.getCustomerConsumerTypeId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getCustomerConsumerTypeId(), CustomerConsumerLog_.customerConsumerType, CustomerConsumerType_.id));
            }
            if (criteria.getStudentId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getStudentId(), CustomerConsumerLog_.student, Student_.id));
            }
        }
        return specification;
    }

}
