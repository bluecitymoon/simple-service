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

import com.pure.service.domain.Payment;
import com.pure.service.domain.*; // for static metamodels
import com.pure.service.repository.PaymentRepository;
import com.pure.service.service.dto.PaymentCriteria;


/**
 * Service for executing complex queries for Payment entities in the database.
 * The main input is a {@link PaymentCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {%link Payment} or a {@link Page} of {%link Payment} which fulfills the criterias
 */
@Service
@Transactional(readOnly = true)
public class PaymentQueryService extends QueryService<Payment> {

    private final Logger log = LoggerFactory.getLogger(PaymentQueryService.class);


    private final PaymentRepository paymentRepository;

    public PaymentQueryService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    /**
     * Return a {@link List} of {%link Payment} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Payment> findByCriteria(PaymentCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<Payment> specification = createSpecification(criteria);
        return paymentRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {%link Payment} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Payment> findByCriteria(PaymentCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<Payment> specification = createSpecification(criteria);
        return paymentRepository.findAll(specification, page);
    }

    /**
     * Function to convert PaymentCriteria to a {@link Specifications}
     */
    private Specifications<Payment> createSpecification(PaymentCriteria criteria) {
        Specifications<Payment> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Payment_.id));
            }
            if (criteria.getProjectName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getProjectName(), Payment_.projectName));
            }
            if (criteria.getEstimateAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEstimateAmount(), Payment_.estimateAmount));
            }
            if (criteria.getActualAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getActualAmount(), Payment_.actualAmount));
            }
            if (criteria.getPaied() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPaied(), Payment_.paied));
            }
            if (criteria.getUnpaid() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUnpaid(), Payment_.unpaid));
            }
            if (criteria.getPaidDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPaidDate(), Payment_.paidDate));
            }
            if (criteria.getComments() != null) {
                specification = specification.and(buildStringSpecification(criteria.getComments(), Payment_.comments));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), Payment_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), Payment_.createdDate));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), Payment_.lastModifiedBy));
            }
            if (criteria.getLastModifiedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModifiedDate(), Payment_.lastModifiedDate));
            }
            if (criteria.getPaidUserId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getPaidUserId(), Payment_.paidUser, User_.id));
            }
            if (criteria.getPaymentTypeId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getPaymentTypeId(), Payment_.paymentType, PaymentType_.id));
            }
        }
        return specification;
    }

}
