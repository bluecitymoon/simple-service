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

import com.pure.service.domain.Collection;
import com.pure.service.domain.*; // for static metamodels
import com.pure.service.repository.CollectionRepository;
import com.pure.service.service.dto.CollectionCriteria;


/**
 * Service for executing complex queries for Collection entities in the database.
 * The main input is a {@link CollectionCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {%link Collection} or a {@link Page} of {%link Collection} which fulfills the criterias
 */
@Service
@Transactional(readOnly = true)
public class CollectionQueryService extends QueryService<Collection> {

    private final Logger log = LoggerFactory.getLogger(CollectionQueryService.class);


    private final CollectionRepository collectionRepository;

    public CollectionQueryService(CollectionRepository collectionRepository) {
        this.collectionRepository = collectionRepository;
    }

    /**
     * Return a {@link List} of {%link Collection} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Collection> findByCriteria(CollectionCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<Collection> specification = createSpecification(criteria);
        return collectionRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {%link Collection} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Collection> findByCriteria(CollectionCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<Collection> specification = createSpecification(criteria);
        return collectionRepository.findAll(specification, page);
    }

    /**
     * Function to convert CollectionCriteria to a {@link Specifications}
     */
    private Specifications<Collection> createSpecification(CollectionCriteria criteria) {
        Specifications<Collection> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Collection_.id));
            }
            if (criteria.getMoneyShouldCollected() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMoneyShouldCollected(), Collection_.moneyShouldCollected));
            }
            if (criteria.getMoneyCollected() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMoneyCollected(), Collection_.moneyCollected));
            }
            if (criteria.getBalance() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBalance(), Collection_.balance));
            }
            if (criteria.getSequenceNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSequenceNumber(), Collection_.sequenceNumber));
            }
            if (criteria.getPayerName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPayerName(), Collection_.payerName));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), Collection_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), Collection_.createdDate));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), Collection_.lastModifiedBy));
            }
            if (criteria.getLastModifiedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModifiedDate(), Collection_.lastModifiedDate));
            }
            if (criteria.getFinanceCategoryId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getFinanceCategoryId(), Collection_.financeCategory, FinanceCategory_.id));
            }
            if (criteria.getPaymentTypeId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getPaymentTypeId(), Collection_.paymentType, PaymentType_.id));
            }
            if (criteria.getStatusId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getStatusId(), Collection_.status, CollectionStatus_.id));
            }
        }
        return specification;
    }

}
