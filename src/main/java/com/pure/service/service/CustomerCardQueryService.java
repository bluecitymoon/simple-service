package com.pure.service.service;


import com.pure.service.domain.Course_;
import com.pure.service.domain.CustomerCard;
import com.pure.service.domain.CustomerCardType_;
import com.pure.service.domain.CustomerCard_;
import com.pure.service.domain.Customer_;
import com.pure.service.repository.CustomerCardRepository;
import com.pure.service.service.dto.CustomerCardCriteria;
import io.github.jhipster.service.QueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;


/**
 * Service for executing complex queries for CustomerCard entities in the database.
 * The main input is a {@link CustomerCardCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {%link CustomerCard} or a {@link Page} of {%link CustomerCard} which fulfills the criterias
 */
@Service
@Transactional(readOnly = true)
public class CustomerCardQueryService extends QueryService<CustomerCard> {

    private final Logger log = LoggerFactory.getLogger(CustomerCardQueryService.class);


    private final CustomerCardRepository customerCardRepository;

    public CustomerCardQueryService(CustomerCardRepository customerCardRepository) {
        this.customerCardRepository = customerCardRepository;
    }

    /**
     * Return a {@link List} of {%link CustomerCard} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CustomerCard> findByCriteria(CustomerCardCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<CustomerCard> specification = createSpecification(criteria);
        return customerCardRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {%link CustomerCard} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CustomerCard> findByCriteria(CustomerCardCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<CustomerCard> specification = createSpecification(criteria);
        return customerCardRepository.findAll(specification, page);
    }

    /**
     * Function to convert CustomerCardCriteria to a {@link Specifications}
     */
    private Specifications<CustomerCard> createSpecification(CustomerCardCriteria criteria) {
        Specifications<CustomerCard> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), CustomerCard_.id));
            }
            if (criteria.getNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNumber(), CustomerCard_.number));
            }
            if (criteria.getSerialNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSerialNumber(), CustomerCard_.serialNumber));
            }
            if (criteria.getSignDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSignDate(), CustomerCard_.signDate));
            }
            if (criteria.getStartDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartDate(), CustomerCard_.startDate));
            }
            if (criteria.getEndDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndDate(), CustomerCard_.endDate));
            }
            if (criteria.getMoneyCollected() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMoneyCollected(), CustomerCard_.moneyCollected));
            }
            if (criteria.getBalance() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBalance(), CustomerCard_.balance));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), CustomerCard_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), CustomerCard_.createdDate));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), CustomerCard_.lastModifiedBy));
            }
            if (criteria.getLastModifiedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModifiedDate(), CustomerCard_.lastModifiedDate));
            }
            if (criteria.getTotalMoneyAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalMoneyAmount(), CustomerCard_.totalMoneyAmount));
            }
            if (criteria.getPromotionAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPromotionAmount(), CustomerCard_.promotionAmount));
            }
            if (criteria.getClassCount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getClassCount(), CustomerCard_.classCount));
            }
            if (criteria.getTotalMinutes() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalMinutes(), CustomerCard_.totalMinutes));
            }
            if (criteria.getSpecialPromotionAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSpecialPromotionAmount(), CustomerCard_.specialPromotionAmount));
            }
            if (criteria.getCustomerId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getCustomerId(), CustomerCard_.customer, Customer_.id));
            }
            if (criteria.getCustomerCardTypeId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getCustomerCardTypeId(), CustomerCard_.customerCardType, CustomerCardType_.id));
            }
            if (criteria.getCourseId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getCourseId(), CustomerCard_.course, Course_.id));
            }

            if (!StringUtils.isEmpty(criteria.getCustomerName())) {
                specification = specification.and(customerName(criteria.getCustomerName()));
            }

            if (!StringUtils.isEmpty(criteria.getCustomerPhoneNumber())) {
                specification = specification.and(customerPhoneNumber(criteria.getCustomerPhoneNumber()));
            }
        }

        return specification;
    }
    private Specification customerName(String customerName) {

        return (root, query, cb) -> cb.like(root.get(CustomerCard_.customer).get(Customer_.name), customerName);
    }
    private Specification customerPhoneNumber(String phoneNumber) {

        return (root, query, cb) -> cb.like(root.get(CustomerCard_.customer).get(Customer_.contactPhoneNumber), phoneNumber);
    }
}
