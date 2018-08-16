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

import com.pure.service.domain.StudentClass;
import com.pure.service.domain.*; // for static metamodels
import com.pure.service.repository.StudentClassRepository;
import com.pure.service.service.dto.StudentClassCriteria;


/**
 * Service for executing complex queries for StudentClass entities in the database.
 * The main input is a {@link StudentClassCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {%link StudentClass} or a {@link Page} of {%link StudentClass} which fulfills the criterias
 */
@Service
@Transactional(readOnly = true)
public class StudentClassQueryService extends QueryService<StudentClass> {

    private final Logger log = LoggerFactory.getLogger(StudentClassQueryService.class);


    private final StudentClassRepository studentClassRepository;

    public StudentClassQueryService(StudentClassRepository studentClassRepository) {
        this.studentClassRepository = studentClassRepository;
    }

    /**
     * Return a {@link List} of {%link StudentClass} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<StudentClass> findByCriteria(StudentClassCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<StudentClass> specification = createSpecification(criteria);
        return studentClassRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {%link StudentClass} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<StudentClass> findByCriteria(StudentClassCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<StudentClass> specification = createSpecification(criteria);
        return studentClassRepository.findAll(specification, page);
    }

    /**
     * Function to convert StudentClassCriteria to a {@link Specifications}
     */
    private Specifications<StudentClass> createSpecification(StudentClassCriteria criteria) {
        Specifications<StudentClass> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), StudentClass_.id));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), StudentClass_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), StudentClass_.createdDate));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), StudentClass_.lastModifiedBy));
            }
            if (criteria.getLastModifiedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModifiedDate(), StudentClass_.lastModifiedDate));
            }
            if (criteria.getStudentId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getStudentId(), StudentClass_.student, Student_.id));
            }
            if (criteria.getProductId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getProductId(), StudentClass_.product, Product_.id));
            }
        }
        return specification;
    }

}
