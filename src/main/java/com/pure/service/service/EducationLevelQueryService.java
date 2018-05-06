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

import com.pure.service.domain.EducationLevel;
import com.pure.service.domain.*; // for static metamodels
import com.pure.service.repository.EducationLevelRepository;
import com.pure.service.service.dto.EducationLevelCriteria;


/**
 * Service for executing complex queries for EducationLevel entities in the database.
 * The main input is a {@link EducationLevelCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {%link EducationLevel} or a {@link Page} of {%link EducationLevel} which fulfills the criterias
 */
@Service
@Transactional(readOnly = true)
public class EducationLevelQueryService extends QueryService<EducationLevel> {

    private final Logger log = LoggerFactory.getLogger(EducationLevelQueryService.class);


    private final EducationLevelRepository educationLevelRepository;

    public EducationLevelQueryService(EducationLevelRepository educationLevelRepository) {
        this.educationLevelRepository = educationLevelRepository;
    }

    /**
     * Return a {@link List} of {%link EducationLevel} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EducationLevel> findByCriteria(EducationLevelCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<EducationLevel> specification = createSpecification(criteria);
        return educationLevelRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {%link EducationLevel} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EducationLevel> findByCriteria(EducationLevelCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<EducationLevel> specification = createSpecification(criteria);
        return educationLevelRepository.findAll(specification, page);
    }

    /**
     * Function to convert EducationLevelCriteria to a {@link Specifications}
     */
    private Specifications<EducationLevel> createSpecification(EducationLevelCriteria criteria) {
        Specifications<EducationLevel> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), EducationLevel_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), EducationLevel_.name));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), EducationLevel_.code));
            }
        }
        return specification;
    }

}
