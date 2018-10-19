package com.pure.service.service;


import com.pure.service.domain.CustomerCardType_;
import com.pure.service.domain.CustomerCardUpgradeLog;
import com.pure.service.domain.CustomerCardUpgradeLog_;
import com.pure.service.repository.CustomerCardUpgradeLogRepository;
import com.pure.service.service.dto.CustomerCardUpgradeLogCriteria;
import io.github.jhipster.service.QueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * Service for executing complex queries for CustomerCardUpgradeLog entities in the database.
 * The main input is a {@link CustomerCardUpgradeLogCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {%link CustomerCardUpgradeLog} or a {@link Page} of {%link CustomerCardUpgradeLog} which fulfills the criterias
 */
@Service
@Transactional(readOnly = true)
public class CustomerCardUpgradeLogQueryService extends QueryService<CustomerCardUpgradeLog> {

    private final Logger log = LoggerFactory.getLogger(CustomerCardUpgradeLogQueryService.class);


    private final CustomerCardUpgradeLogRepository customerCardUpgradeLogRepository;

    public CustomerCardUpgradeLogQueryService(CustomerCardUpgradeLogRepository customerCardUpgradeLogRepository) {
        this.customerCardUpgradeLogRepository = customerCardUpgradeLogRepository;
    }

    /**
     * Return a {@link List} of {%link CustomerCardUpgradeLog} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CustomerCardUpgradeLog> findByCriteria(CustomerCardUpgradeLogCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<CustomerCardUpgradeLog> specification = createSpecification(criteria);
        return customerCardUpgradeLogRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {%link CustomerCardUpgradeLog} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CustomerCardUpgradeLog> findByCriteria(CustomerCardUpgradeLogCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<CustomerCardUpgradeLog> specification = createSpecification(criteria);
        return customerCardUpgradeLogRepository.findAll(specification, page);
    }

    /**
     * Function to convert CustomerCardUpgradeLogCriteria to a {@link Specifications}
     */
    private Specifications<CustomerCardUpgradeLog> createSpecification(CustomerCardUpgradeLogCriteria criteria) {
        Specifications<CustomerCardUpgradeLog> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getRegionId() != null) {
                specification = specification.and(buildSpecification(criteria.getRegionId(), CustomerCardUpgradeLog_.regionId));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), CustomerCardUpgradeLog_.id));
            }
            if (criteria.getCustomerName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCustomerName(), CustomerCardUpgradeLog_.customerName));
            }
            if (criteria.getCustomerId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCustomerId(), CustomerCardUpgradeLog_.customerId));
            }
            if (criteria.getSerialNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSerialNumber(), CustomerCardUpgradeLog_.serialNumber));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), CustomerCardUpgradeLog_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), CustomerCardUpgradeLog_.createdDate));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), CustomerCardUpgradeLog_.lastModifiedBy));
            }
            if (criteria.getLastModifiedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModifiedDate(), CustomerCardUpgradeLog_.lastModifiedDate));
            }
            if (criteria.getOriginalCardTypeId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getOriginalCardTypeId(), CustomerCardUpgradeLog_.originalCardType, CustomerCardType_.id));
            }
            if (criteria.getNewCardTypeId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getNewCardTypeId(), CustomerCardUpgradeLog_.newCardType, CustomerCardType_.id));
            }
        }
        return specification;
    }

}
