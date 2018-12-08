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

import com.pure.service.domain.StudentClassLogType;
import com.pure.service.domain.*; // for static metamodels
import com.pure.service.repository.StudentClassLogTypeRepository;
import com.pure.service.service.dto.StudentClassLogTypeCriteria;


/**
 * Service for executing complex queries for StudentClassLogType entities in the database.
 * The main input is a {@link StudentClassLogTypeCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {%link StudentClassLogType} or a {@link Page} of {%link StudentClassLogType} which fulfills the criterias
 */
@Service
@Transactional(readOnly = true)
public class StudentClassLogTypeQueryService extends QueryService<StudentClassLogType> {

    private final Logger log = LoggerFactory.getLogger(StudentClassLogTypeQueryService.class);


    private final StudentClassLogTypeRepository studentClassLogTypeRepository;

    public StudentClassLogTypeQueryService(StudentClassLogTypeRepository studentClassLogTypeRepository) {
        this.studentClassLogTypeRepository = studentClassLogTypeRepository;
    }

    /**
     * Return a {@link List} of {%link StudentClassLogType} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<StudentClassLogType> findByCriteria(StudentClassLogTypeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<StudentClassLogType> specification = createSpecification(criteria);
        return studentClassLogTypeRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {%link StudentClassLogType} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<StudentClassLogType> findByCriteria(StudentClassLogTypeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<StudentClassLogType> specification = createSpecification(criteria);
        return studentClassLogTypeRepository.findAll(specification, page);
    }

    /**
     * Function to convert StudentClassLogTypeCriteria to a {@link Specifications}
     */
    private Specifications<StudentClassLogType> createSpecification(StudentClassLogTypeCriteria criteria) {
        Specifications<StudentClassLogType> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), StudentClassLogType_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), StudentClassLogType_.name));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), StudentClassLogType_.code));
            }
            if (criteria.getComments() != null) {
                specification = specification.and(buildStringSpecification(criteria.getComments(), StudentClassLogType_.comments));
            }
        }
        return specification;
    }

}
