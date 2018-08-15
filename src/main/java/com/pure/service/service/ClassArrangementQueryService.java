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

import com.pure.service.domain.ClassArrangement;
import com.pure.service.domain.*; // for static metamodels
import com.pure.service.repository.ClassArrangementRepository;
import com.pure.service.service.dto.ClassArrangementCriteria;


/**
 * Service for executing complex queries for ClassArrangement entities in the database.
 * The main input is a {@link ClassArrangementCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {%link ClassArrangement} or a {@link Page} of {%link ClassArrangement} which fulfills the criterias
 */
@Service
@Transactional(readOnly = true)
public class ClassArrangementQueryService extends QueryService<ClassArrangement> {

    private final Logger log = LoggerFactory.getLogger(ClassArrangementQueryService.class);


    private final ClassArrangementRepository classArrangementRepository;

    public ClassArrangementQueryService(ClassArrangementRepository classArrangementRepository) {
        this.classArrangementRepository = classArrangementRepository;
    }

    /**
     * Return a {@link List} of {%link ClassArrangement} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ClassArrangement> findByCriteria(ClassArrangementCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<ClassArrangement> specification = createSpecification(criteria);
        return classArrangementRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {%link ClassArrangement} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ClassArrangement> findByCriteria(ClassArrangementCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<ClassArrangement> specification = createSpecification(criteria);
        return classArrangementRepository.findAll(specification, page);
    }

    /**
     * Function to convert ClassArrangementCriteria to a {@link Specifications}
     */
    private Specifications<ClassArrangement> createSpecification(ClassArrangementCriteria criteria) {
        Specifications<ClassArrangement> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ClassArrangement_.id));
            }
            if (criteria.getStartDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartDate(), ClassArrangement_.startDate));
            }
            if (criteria.getEndDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndDate(), ClassArrangement_.endDate));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), ClassArrangement_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), ClassArrangement_.createdDate));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), ClassArrangement_.lastModifiedBy));
            }
            if (criteria.getLastModifiedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModifiedDate(), ClassArrangement_.lastModifiedDate));
            }
            if (criteria.getPlanedTeacherId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getPlanedTeacherId(), ClassArrangement_.planedTeacher, Teacher_.id));
            }
            if (criteria.getActualTeacherId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getActualTeacherId(), ClassArrangement_.actualTeacher, Teacher_.id));
            }
            if (criteria.getStatusId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getStatusId(), ClassArrangement_.status, ClassArrangementStatus_.id));
            }
            if (criteria.getClazzId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getClazzId(), ClassArrangement_.clazz, Product_.id));
            }
        }
        return specification;
    }

}
