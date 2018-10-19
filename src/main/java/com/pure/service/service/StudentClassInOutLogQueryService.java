package com.pure.service.service;


import com.pure.service.domain.Product_;
import com.pure.service.domain.StudentClassInOutLog;
import com.pure.service.domain.StudentClassInOutLog_;
import com.pure.service.domain.Student_;
import com.pure.service.repository.StudentClassInOutLogRepository;
import com.pure.service.service.dto.StudentClassInOutLogCriteria;
import io.github.jhipster.service.QueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * Service for executing complex queries for StudentClassInOutLog entities in the database.
 * The main input is a {@link StudentClassInOutLogCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {%link StudentClassInOutLog} or a {@link Page} of {%link StudentClassInOutLog} which fulfills the criterias
 */
@Service
@Transactional(readOnly = true)
public class StudentClassInOutLogQueryService extends QueryService<StudentClassInOutLog> {

    private final Logger log = LoggerFactory.getLogger(StudentClassInOutLogQueryService.class);


    private final StudentClassInOutLogRepository studentClassInOutLogRepository;

    public StudentClassInOutLogQueryService(StudentClassInOutLogRepository studentClassInOutLogRepository) {
        this.studentClassInOutLogRepository = studentClassInOutLogRepository;
    }

    /**
     * Return a {@link List} of {%link StudentClassInOutLog} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<StudentClassInOutLog> findByCriteria(StudentClassInOutLogCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<StudentClassInOutLog> specification = createSpecification(criteria);
        return studentClassInOutLogRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {%link StudentClassInOutLog} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<StudentClassInOutLog> findByCriteria(StudentClassInOutLogCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<StudentClassInOutLog> specification = createSpecification(criteria);
        return studentClassInOutLogRepository.findAll(specification, page);
    }

    /**
     * Function to convert StudentClassInOutLogCriteria to a {@link Specifications}
     */
    private Specifications<StudentClassInOutLog> createSpecification(StudentClassInOutLogCriteria criteria) {
        Specifications<StudentClassInOutLog> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getRegionId() != null) {
                specification = specification.and(buildSpecification(criteria.getRegionId(), StudentClassInOutLog_.regionId));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), StudentClassInOutLog_.id));
            }
            if (criteria.getComments() != null) {
                specification = specification.and(buildStringSpecification(criteria.getComments(), StudentClassInOutLog_.comments));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), StudentClassInOutLog_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), StudentClassInOutLog_.createdDate));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), StudentClassInOutLog_.lastModifiedBy));
            }
            if (criteria.getLastModifiedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModifiedDate(), StudentClassInOutLog_.lastModifiedDate));
            }
            if (criteria.getStudentId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getStudentId(), StudentClassInOutLog_.student, Student_.id));
            }
            if (criteria.getOldClassId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getOldClassId(), StudentClassInOutLog_.oldClass, Product_.id));
            }
            if (criteria.getNewClassId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getNewClassId(), StudentClassInOutLog_.newClass, Product_.id));
            }
        }
        return specification;
    }

}
