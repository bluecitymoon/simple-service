package com.pure.service.service;


import com.pure.service.domain.FreeClassPlan;
import com.pure.service.domain.FreeClassPlan_;
import com.pure.service.repository.FreeClassPlanRepository;
import com.pure.service.service.dto.FreeClassPlanCriteria;
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
 * Service for executing complex queries for FreeClassPlan entities in the database.
 * The main input is a {@link FreeClassPlanCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {%link FreeClassPlan} or a {@link Page} of {%link FreeClassPlan} which fulfills the criterias
 */
@Service
@Transactional(readOnly = true)
public class FreeClassPlanQueryService extends QueryService<FreeClassPlan> {

    private final Logger log = LoggerFactory.getLogger(FreeClassPlanQueryService.class);


    private final FreeClassPlanRepository freeClassPlanRepository;

    public FreeClassPlanQueryService(FreeClassPlanRepository freeClassPlanRepository) {
        this.freeClassPlanRepository = freeClassPlanRepository;
    }

    /**
     * Return a {@link List} of {%link FreeClassPlan} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<FreeClassPlan> findByCriteria(FreeClassPlanCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<FreeClassPlan> specification = createSpecification(criteria);
        return freeClassPlanRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {%link FreeClassPlan} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<FreeClassPlan> findByCriteria(FreeClassPlanCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<FreeClassPlan> specification = createSpecification(criteria);
        return freeClassPlanRepository.findAll(specification, page);
    }

    /**
     * Function to convert FreeClassPlanCriteria to a {@link Specifications}
     */
    private Specifications<FreeClassPlan> createSpecification(FreeClassPlanCriteria criteria) {
        Specifications<FreeClassPlan> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getRegionId() != null) {
                specification = specification.and(buildSpecification(criteria.getRegionId(), FreeClassPlan_.regionId));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), FreeClassPlan_.id));
            }
            if (criteria.getPlanDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPlanDate(), FreeClassPlan_.planDate));
            }
            if (criteria.getLimitCount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLimitCount(), FreeClassPlan_.limitCount));
            }
            if (criteria.getActualCount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getActualCount(), FreeClassPlan_.actualCount));
            }
        }
        return specification;
    }

}
