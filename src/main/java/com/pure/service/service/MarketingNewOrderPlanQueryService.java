package com.pure.service.service;


import com.pure.service.domain.MarketingNewOrderPlan;
import com.pure.service.domain.MarketingNewOrderPlan_;
import com.pure.service.domain.User_;
import com.pure.service.repository.MarketingNewOrderPlanRepository;
import com.pure.service.service.dto.MarketingNewOrderPlanCriteria;
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
 * Service for executing complex queries for MarketingNewOrderPlan entities in the database.
 * The main input is a {@link MarketingNewOrderPlanCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {%link MarketingNewOrderPlan} or a {@link Page} of {%link MarketingNewOrderPlan} which fulfills the criterias
 */
@Service
@Transactional(readOnly = true)
public class MarketingNewOrderPlanQueryService extends QueryService<MarketingNewOrderPlan> {

    private final Logger log = LoggerFactory.getLogger(MarketingNewOrderPlanQueryService.class);


    private final MarketingNewOrderPlanRepository marketingNewOrderPlanRepository;

    public MarketingNewOrderPlanQueryService(MarketingNewOrderPlanRepository marketingNewOrderPlanRepository) {
        this.marketingNewOrderPlanRepository = marketingNewOrderPlanRepository;
    }

    /**
     * Return a {@link List} of {%link MarketingNewOrderPlan} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MarketingNewOrderPlan> findByCriteria(MarketingNewOrderPlanCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<MarketingNewOrderPlan> specification = createSpecification(criteria);
        return marketingNewOrderPlanRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {%link MarketingNewOrderPlan} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MarketingNewOrderPlan> findByCriteria(MarketingNewOrderPlanCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<MarketingNewOrderPlan> specification = createSpecification(criteria);
        return marketingNewOrderPlanRepository.findAll(specification, page);
    }

    /**
     * Function to convert MarketingNewOrderPlanCriteria to a {@link Specifications}
     */
    private Specifications<MarketingNewOrderPlan> createSpecification(MarketingNewOrderPlanCriteria criteria) {
        Specifications<MarketingNewOrderPlan> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getRegionId() != null) {
                specification = specification.and(buildSpecification(criteria.getRegionId(), MarketingNewOrderPlan_.regionId));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), MarketingNewOrderPlan_.id));
            }
            if (criteria.getYear() != null) {
                specification = specification.and(buildStringSpecification(criteria.getYear(), MarketingNewOrderPlan_.year));
            }
            if (criteria.getMonth() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMonth(), MarketingNewOrderPlan_.month));
            }
            if (criteria.getTargetNumber() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTargetNumber(), MarketingNewOrderPlan_.targetNumber));
            }
            if (criteria.getCurrentNumber() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCurrentNumber(), MarketingNewOrderPlan_.currentNumber));
            }
            if (criteria.getFinished() != null) {
                specification = specification.and(buildSpecification(criteria.getFinished(), MarketingNewOrderPlan_.finished));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), MarketingNewOrderPlan_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), MarketingNewOrderPlan_.createdDate));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), MarketingNewOrderPlan_.lastModifiedBy));
            }
            if (criteria.getLastModifiedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModifiedDate(), MarketingNewOrderPlan_.lastModifiedDate));
            }
            if (criteria.getPercentage() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPercentage(), MarketingNewOrderPlan_.percentage));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getUserId(), MarketingNewOrderPlan_.user, User_.id));
            }
        }
        return specification;
    }

}
