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

import com.pure.service.domain.ClassArrangementRule;
import com.pure.service.domain.*; // for static metamodels
import com.pure.service.repository.ClassArrangementRuleRepository;
import com.pure.service.service.dto.ClassArrangementRuleCriteria;


/**
 * Service for executing complex queries for ClassArrangementRule entities in the database.
 * The main input is a {@link ClassArrangementRuleCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {%link ClassArrangementRule} or a {@link Page} of {%link ClassArrangementRule} which fulfills the criterias
 */
@Service
@Transactional(readOnly = true)
public class ClassArrangementRuleQueryService extends QueryService<ClassArrangementRule> {

    private final Logger log = LoggerFactory.getLogger(ClassArrangementRuleQueryService.class);


    private final ClassArrangementRuleRepository classArrangementRuleRepository;

    public ClassArrangementRuleQueryService(ClassArrangementRuleRepository classArrangementRuleRepository) {
        this.classArrangementRuleRepository = classArrangementRuleRepository;
    }

    /**
     * Return a {@link List} of {%link ClassArrangementRule} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ClassArrangementRule> findByCriteria(ClassArrangementRuleCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<ClassArrangementRule> specification = createSpecification(criteria);
        return classArrangementRuleRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {%link ClassArrangementRule} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ClassArrangementRule> findByCriteria(ClassArrangementRuleCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<ClassArrangementRule> specification = createSpecification(criteria);
        return classArrangementRuleRepository.findAll(specification, page);
    }

    /**
     * Function to convert ClassArrangementRuleCriteria to a {@link Specifications}
     */
    private Specifications<ClassArrangementRule> createSpecification(ClassArrangementRuleCriteria criteria) {
        Specifications<ClassArrangementRule> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ClassArrangementRule_.id));
            }
            if (criteria.getEstimateStartDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEstimateStartDate(), ClassArrangementRule_.estimateStartDate));
            }
            if (criteria.getEstimateStartTime() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEstimateStartTime(), ClassArrangementRule_.estimateStartTime));
            }
            if (criteria.getEstimateEndTime() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEstimateEndTime(), ClassArrangementRule_.estimateEndTime));
            }
            if (criteria.getMaxLoopCount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMaxLoopCount(), ClassArrangementRule_.maxLoopCount));
            }
            if (criteria.getTargetClassId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getTargetClassId(), ClassArrangementRule_.targetClass, Product_.id));
            }
            if (criteria.getLoopWayId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getLoopWayId(), ClassArrangementRule_.loopWay, ClassArrangementRuleLoopWay_.id));
            }
        }
        return specification;
    }

}
