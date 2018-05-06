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

import com.pure.service.domain.ClassAgeLevel;
import com.pure.service.domain.*; // for static metamodels
import com.pure.service.repository.ClassAgeLevelRepository;
import com.pure.service.service.dto.ClassAgeLevelCriteria;


/**
 * Service for executing complex queries for ClassAgeLevel entities in the database.
 * The main input is a {@link ClassAgeLevelCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {%link ClassAgeLevel} or a {@link Page} of {%link ClassAgeLevel} which fulfills the criterias
 */
@Service
@Transactional(readOnly = true)
public class ClassAgeLevelQueryService extends QueryService<ClassAgeLevel> {

    private final Logger log = LoggerFactory.getLogger(ClassAgeLevelQueryService.class);


    private final ClassAgeLevelRepository classAgeLevelRepository;

    public ClassAgeLevelQueryService(ClassAgeLevelRepository classAgeLevelRepository) {
        this.classAgeLevelRepository = classAgeLevelRepository;
    }

    /**
     * Return a {@link List} of {%link ClassAgeLevel} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ClassAgeLevel> findByCriteria(ClassAgeLevelCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<ClassAgeLevel> specification = createSpecification(criteria);
        return classAgeLevelRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {%link ClassAgeLevel} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ClassAgeLevel> findByCriteria(ClassAgeLevelCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<ClassAgeLevel> specification = createSpecification(criteria);
        return classAgeLevelRepository.findAll(specification, page);
    }

    /**
     * Function to convert ClassAgeLevelCriteria to a {@link Specifications}
     */
    private Specifications<ClassAgeLevel> createSpecification(ClassAgeLevelCriteria criteria) {
        Specifications<ClassAgeLevel> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ClassAgeLevel_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), ClassAgeLevel_.name));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), ClassAgeLevel_.code));
            }
        }
        return specification;
    }

}
