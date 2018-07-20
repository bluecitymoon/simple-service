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

import com.pure.service.domain.CustomerStatusReportDtl;
import com.pure.service.domain.*; // for static metamodels
import com.pure.service.repository.CustomerStatusReportDtlRepository;
import com.pure.service.service.dto.CustomerStatusReportDtlCriteria;


/**
 * Service for executing complex queries for CustomerStatusReportDtl entities in the database.
 * The main input is a {@link CustomerStatusReportDtlCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {%link CustomerStatusReportDtl} or a {@link Page} of {%link CustomerStatusReportDtl} which fulfills the criterias
 */
@Service
@Transactional(readOnly = true)
public class CustomerStatusReportDtlQueryService extends QueryService<CustomerStatusReportDtl> {

    private final Logger log = LoggerFactory.getLogger(CustomerStatusReportDtlQueryService.class);


    private final CustomerStatusReportDtlRepository customerStatusReportDtlRepository;

    public CustomerStatusReportDtlQueryService(CustomerStatusReportDtlRepository customerStatusReportDtlRepository) {
        this.customerStatusReportDtlRepository = customerStatusReportDtlRepository;
    }

    /**
     * Return a {@link List} of {%link CustomerStatusReportDtl} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CustomerStatusReportDtl> findByCriteria(CustomerStatusReportDtlCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<CustomerStatusReportDtl> specification = createSpecification(criteria);
        return customerStatusReportDtlRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {%link CustomerStatusReportDtl} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CustomerStatusReportDtl> findByCriteria(CustomerStatusReportDtlCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<CustomerStatusReportDtl> specification = createSpecification(criteria);
        return customerStatusReportDtlRepository.findAll(specification, page);
    }

    /**
     * Function to convert CustomerStatusReportDtlCriteria to a {@link Specifications}
     */
    private Specifications<CustomerStatusReportDtl> createSpecification(CustomerStatusReportDtlCriteria criteria) {
        Specifications<CustomerStatusReportDtl> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), CustomerStatusReportDtl_.id));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUserId(), CustomerStatusReportDtl_.userId));
            }
            if (criteria.getUserName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUserName(), CustomerStatusReportDtl_.userName));
            }
            if (criteria.getAgeTooSmallCount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAgeTooSmallCount(), CustomerStatusReportDtl_.ageTooSmallCount));
            }
            if (criteria.getErrorInformation() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getErrorInformation(), CustomerStatusReportDtl_.errorInformation));
            }
            if (criteria.getNoWillingCount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNoWillingCount(), CustomerStatusReportDtl_.noWillingCount));
            }
            if (criteria.getConsideringCount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getConsideringCount(), CustomerStatusReportDtl_.consideringCount));
            }
            if (criteria.getScheduledCount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getScheduledCount(), CustomerStatusReportDtl_.scheduledCount));
            }
            if (criteria.getDealedCount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDealedCount(), CustomerStatusReportDtl_.dealedCount));
            }
            if (criteria.getNewCreatedCount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNewCreatedCount(), CustomerStatusReportDtl_.newCreatedCount));
            }
            if (criteria.getTotalCount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalCount(), CustomerStatusReportDtl_.totalCount));
            }
            if (criteria.getFinishRate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFinishRate(), CustomerStatusReportDtl_.finishRate));
            }
        }
        return specification;
    }

}
