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

import com.pure.service.domain.CustomerScheduleFeedback;
import com.pure.service.domain.*; // for static metamodels
import com.pure.service.repository.CustomerScheduleFeedbackRepository;
import com.pure.service.service.dto.CustomerScheduleFeedbackCriteria;


/**
 * Service for executing complex queries for CustomerScheduleFeedback entities in the database.
 * The main input is a {@link CustomerScheduleFeedbackCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {%link CustomerScheduleFeedback} or a {@link Page} of {%link CustomerScheduleFeedback} which fulfills the criterias
 */
@Service
@Transactional(readOnly = true)
public class CustomerScheduleFeedbackQueryService extends QueryService<CustomerScheduleFeedback> {

    private final Logger log = LoggerFactory.getLogger(CustomerScheduleFeedbackQueryService.class);


    private final CustomerScheduleFeedbackRepository customerScheduleFeedbackRepository;

    public CustomerScheduleFeedbackQueryService(CustomerScheduleFeedbackRepository customerScheduleFeedbackRepository) {
        this.customerScheduleFeedbackRepository = customerScheduleFeedbackRepository;
    }

    /**
     * Return a {@link List} of {%link CustomerScheduleFeedback} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CustomerScheduleFeedback> findByCriteria(CustomerScheduleFeedbackCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<CustomerScheduleFeedback> specification = createSpecification(criteria);
        return customerScheduleFeedbackRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {%link CustomerScheduleFeedback} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CustomerScheduleFeedback> findByCriteria(CustomerScheduleFeedbackCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<CustomerScheduleFeedback> specification = createSpecification(criteria);
        return customerScheduleFeedbackRepository.findAll(specification, page);
    }

    /**
     * Function to convert CustomerScheduleFeedbackCriteria to a {@link Specifications}
     */
    private Specifications<CustomerScheduleFeedback> createSpecification(CustomerScheduleFeedbackCriteria criteria) {
        Specifications<CustomerScheduleFeedback> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), CustomerScheduleFeedback_.id));
            }
            if (criteria.getGiftCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGiftCode(), CustomerScheduleFeedback_.giftCode));
            }
            if (criteria.getGiftStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGiftStatus(), CustomerScheduleFeedback_.giftStatus));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), CustomerScheduleFeedback_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), CustomerScheduleFeedback_.createdDate));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), CustomerScheduleFeedback_.lastModifiedBy));
            }
            if (criteria.getLastModifiedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModifiedDate(), CustomerScheduleFeedback_.lastModifiedDate));
            }
            if (criteria.getCustomerId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getCustomerId(), CustomerScheduleFeedback_.customer, Customer_.id));
            }
            if (criteria.getScheduleId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getScheduleId(), CustomerScheduleFeedback_.schedule, CustomerCommunicationSchedule_.id));
            }
        }
        return specification;
    }

}
