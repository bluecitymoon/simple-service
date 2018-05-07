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

import com.pure.service.domain.ClassArrangementRuleLoopWay;
import com.pure.service.domain.*; // for static metamodels
import com.pure.service.repository.ClassArrangementRuleLoopWayRepository;
import com.pure.service.service.dto.ClassArrangementRuleLoopWayCriteria;


/**
 * Service for executing complex queries for ClassArrangementRuleLoopWay entities in the database.
 * The main input is a {@link ClassArrangementRuleLoopWayCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {%link ClassArrangementRuleLoopWay} or a {@link Page} of {%link ClassArrangementRuleLoopWay} which fulfills the criterias
 */
@Service
@Transactional(readOnly = true)
public class ClassArrangementRuleLoopWayQueryService extends QueryService<ClassArrangementRuleLoopWay> {

    private final Logger log = LoggerFactory.getLogger(ClassArrangementRuleLoopWayQueryService.class);


    private final ClassArrangementRuleLoopWayRepository classArrangementRuleLoopWayRepository;

    public ClassArrangementRuleLoopWayQueryService(ClassArrangementRuleLoopWayRepository classArrangementRuleLoopWayRepository) {
        this.classArrangementRuleLoopWayRepository = classArrangementRuleLoopWayRepository;
    }

    /**
     * Return a {@link List} of {%link ClassArrangementRuleLoopWay} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ClassArrangementRuleLoopWay> findByCriteria(ClassArrangementRuleLoopWayCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<ClassArrangementRuleLoopWay> specification = createSpecification(criteria);
        return classArrangementRuleLoopWayRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {%link ClassArrangementRuleLoopWay} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ClassArrangementRuleLoopWay> findByCriteria(ClassArrangementRuleLoopWayCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<ClassArrangementRuleLoopWay> specification = createSpecification(criteria);
        return classArrangementRuleLoopWayRepository.findAll(specification, page);
    }

    /**
     * Function to convert ClassArrangementRuleLoopWayCriteria to a {@link Specifications}
     */
    private Specifications<ClassArrangementRuleLoopWay> createSpecification(ClassArrangementRuleLoopWayCriteria criteria) {
        Specifications<ClassArrangementRuleLoopWay> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ClassArrangementRuleLoopWay_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), ClassArrangementRuleLoopWay_.name));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), ClassArrangementRuleLoopWay_.code));
            }
        }
        return specification;
    }

}
