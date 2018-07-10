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

import com.pure.service.domain.Student;
import com.pure.service.domain.*; // for static metamodels
import com.pure.service.repository.StudentRepository;
import com.pure.service.service.dto.StudentCriteria;


/**
 * Service for executing complex queries for Student entities in the database.
 * The main input is a {@link StudentCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {%link Student} or a {@link Page} of {%link Student} which fulfills the criterias
 */
@Service
@Transactional(readOnly = true)
public class StudentQueryService extends QueryService<Student> {

    private final Logger log = LoggerFactory.getLogger(StudentQueryService.class);


    private final StudentRepository studentRepository;

    public StudentQueryService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    /**
     * Return a {@link List} of {%link Student} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Student> findByCriteria(StudentCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<Student> specification = createSpecification(criteria);
        return studentRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {%link Student} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Student> findByCriteria(StudentCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<Student> specification = createSpecification(criteria);
        return studentRepository.findAll(specification, page);
    }

    /**
     * Function to convert StudentCriteria to a {@link Specifications}
     */
    private Specifications<Student> createSpecification(StudentCriteria criteria) {
        Specifications<Student> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Student_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Student_.name));
            }
            if (criteria.getPhone() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPhone(), Student_.phone));
            }
            if (criteria.getGender() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGender(), Student_.gender));
            }
            if (criteria.getBirthday() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBirthday(), Student_.birthday));
            }
            if (criteria.getAddress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAddress(), Student_.address));
            }
            if (criteria.getSchool() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSchool(), Student_.school));
            }
            if (criteria.getQq() != null) {
                specification = specification.and(buildStringSpecification(criteria.getQq(), Student_.qq));
            }
            if (criteria.getComments() != null) {
                specification = specification.and(buildStringSpecification(criteria.getComments(), Student_.comments));
            }
            if (criteria.getCustomerId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getCustomerId(), Student_.customer, Customer_.id));
            }
        }
        return specification;
    }

}
