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

import com.pure.service.domain.ClassStatus;
import com.pure.service.domain.*; // for static metamodels
import com.pure.service.repository.ClassStatusRepository;
import com.pure.service.service.dto.ClassStatusCriteria;


/**
 * Service for executing complex queries for ClassStatus entities in the database.
 * The main input is a {@link ClassStatusCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {%link ClassStatus} or a {@link Page} of {%link ClassStatus} which fulfills the criterias
 */
@Service
@Transactional(readOnly = true)
public class ClassStatusQueryService extends QueryService<ClassStatus> {

    private final Logger log = LoggerFactory.getLogger(ClassStatusQueryService.class);


    private final ClassStatusRepository classStatusRepository;

    public ClassStatusQueryService(ClassStatusRepository classStatusRepository) {
        this.classStatusRepository = classStatusRepository;
    }

    /**
     * Return a {@link List} of {%link ClassStatus} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ClassStatus> findByCriteria(ClassStatusCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<ClassStatus> specification = createSpecification(criteria);
        return classStatusRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {%link ClassStatus} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ClassStatus> findByCriteria(ClassStatusCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<ClassStatus> specification = createSpecification(criteria);
        return classStatusRepository.findAll(specification, page);
    }

    /**
     * Function to convert ClassStatusCriteria to a {@link Specifications}
     */
    private Specifications<ClassStatus> createSpecification(ClassStatusCriteria criteria) {
        Specifications<ClassStatus> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ClassStatus_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), ClassStatus_.name));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), ClassStatus_.code));
            }
        }
        return specification;
    }

}
