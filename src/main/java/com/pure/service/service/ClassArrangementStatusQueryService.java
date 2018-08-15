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

import com.pure.service.domain.ClassArrangementStatus;
import com.pure.service.domain.*; // for static metamodels
import com.pure.service.repository.ClassArrangementStatusRepository;
import com.pure.service.service.dto.ClassArrangementStatusCriteria;


/**
 * Service for executing complex queries for ClassArrangementStatus entities in the database.
 * The main input is a {@link ClassArrangementStatusCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {%link ClassArrangementStatus} or a {@link Page} of {%link ClassArrangementStatus} which fulfills the criterias
 */
@Service
@Transactional(readOnly = true)
public class ClassArrangementStatusQueryService extends QueryService<ClassArrangementStatus> {

    private final Logger log = LoggerFactory.getLogger(ClassArrangementStatusQueryService.class);


    private final ClassArrangementStatusRepository classArrangementStatusRepository;

    public ClassArrangementStatusQueryService(ClassArrangementStatusRepository classArrangementStatusRepository) {
        this.classArrangementStatusRepository = classArrangementStatusRepository;
    }

    /**
     * Return a {@link List} of {%link ClassArrangementStatus} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ClassArrangementStatus> findByCriteria(ClassArrangementStatusCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<ClassArrangementStatus> specification = createSpecification(criteria);
        return classArrangementStatusRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {%link ClassArrangementStatus} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ClassArrangementStatus> findByCriteria(ClassArrangementStatusCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<ClassArrangementStatus> specification = createSpecification(criteria);
        return classArrangementStatusRepository.findAll(specification, page);
    }

    /**
     * Function to convert ClassArrangementStatusCriteria to a {@link Specifications}
     */
    private Specifications<ClassArrangementStatus> createSpecification(ClassArrangementStatusCriteria criteria) {
        Specifications<ClassArrangementStatus> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ClassArrangementStatus_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), ClassArrangementStatus_.name));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), ClassArrangementStatus_.code));
            }
            if (criteria.getComments() != null) {
                specification = specification.and(buildStringSpecification(criteria.getComments(), ClassArrangementStatus_.comments));
            }
        }
        return specification;
    }

}
