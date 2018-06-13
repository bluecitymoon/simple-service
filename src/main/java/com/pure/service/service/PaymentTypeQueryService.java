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

import com.pure.service.domain.PaymentType;
import com.pure.service.domain.*; // for static metamodels
import com.pure.service.repository.PaymentTypeRepository;
import com.pure.service.service.dto.PaymentTypeCriteria;


/**
 * Service for executing complex queries for PaymentType entities in the database.
 * The main input is a {@link PaymentTypeCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {%link PaymentType} or a {@link Page} of {%link PaymentType} which fulfills the criterias
 */
@Service
@Transactional(readOnly = true)
public class PaymentTypeQueryService extends QueryService<PaymentType> {

    private final Logger log = LoggerFactory.getLogger(PaymentTypeQueryService.class);


    private final PaymentTypeRepository paymentTypeRepository;

    public PaymentTypeQueryService(PaymentTypeRepository paymentTypeRepository) {
        this.paymentTypeRepository = paymentTypeRepository;
    }

    /**
     * Return a {@link List} of {%link PaymentType} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PaymentType> findByCriteria(PaymentTypeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<PaymentType> specification = createSpecification(criteria);
        return paymentTypeRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {%link PaymentType} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PaymentType> findByCriteria(PaymentTypeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<PaymentType> specification = createSpecification(criteria);
        return paymentTypeRepository.findAll(specification, page);
    }

    /**
     * Function to convert PaymentTypeCriteria to a {@link Specifications}
     */
    private Specifications<PaymentType> createSpecification(PaymentTypeCriteria criteria) {
        Specifications<PaymentType> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), PaymentType_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), PaymentType_.name));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), PaymentType_.code));
            }
        }
        return specification;
    }

}
