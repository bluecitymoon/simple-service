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

import com.pure.service.domain.School;
import com.pure.service.domain.*; // for static metamodels
import com.pure.service.repository.SchoolRepository;
import com.pure.service.service.dto.SchoolCriteria;


/**
 * Service for executing complex queries for School entities in the database.
 * The main input is a {@link SchoolCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {%link School} or a {@link Page} of {%link School} which fulfills the criterias
 */
@Service
@Transactional(readOnly = true)
public class SchoolQueryService extends QueryService<School> {

    private final Logger log = LoggerFactory.getLogger(SchoolQueryService.class);


    private final SchoolRepository schoolRepository;

    public SchoolQueryService(SchoolRepository schoolRepository) {
        this.schoolRepository = schoolRepository;
    }

    /**
     * Return a {@link List} of {%link School} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<School> findByCriteria(SchoolCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<School> specification = createSpecification(criteria);
        return schoolRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {%link School} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<School> findByCriteria(SchoolCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<School> specification = createSpecification(criteria);
        return schoolRepository.findAll(specification, page);
    }

    /**
     * Function to convert SchoolCriteria to a {@link Specifications}
     */
    private Specifications<School> createSpecification(SchoolCriteria criteria) {
        Specifications<School> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), School_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), School_.name));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), School_.code));
            }
            if (criteria.getStartDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartDate(), School_.startDate));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), School_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), School_.createdDate));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), School_.lastModifiedBy));
            }
            if (criteria.getLastModifiedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModifiedDate(), School_.lastModifiedDate));
            }
            if (criteria.getAddress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAddress(), School_.address));
            }
            if (criteria.getPhone() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPhone(), School_.phone));
            }
        }
        return specification;
    }

}
