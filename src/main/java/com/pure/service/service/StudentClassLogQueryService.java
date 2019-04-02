package com.pure.service.service;


import com.pure.service.domain.ClassArrangement_;
import com.pure.service.domain.Product_;
import com.pure.service.domain.StudentClassLog;
import com.pure.service.domain.StudentClassLog_;
import com.pure.service.domain.Student_;
import com.pure.service.repository.StudentClassLogRepository;
import com.pure.service.service.dto.StudentClassLogCriteria;
import io.github.jhipster.service.QueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;


/**
 * Service for executing complex queries for StudentClassLog entities in the database.
 * The main input is a {@link StudentClassLogCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {%link StudentClassLog} or a {@link Page} of {%link StudentClassLog} which fulfills the criterias
 */
@Service
@Transactional(readOnly = true)
public class StudentClassLogQueryService extends QueryService<StudentClassLog> {

    private final Logger log = LoggerFactory.getLogger(StudentClassLogQueryService.class);


    private final StudentClassLogRepository studentClassLogRepository;

    public StudentClassLogQueryService(StudentClassLogRepository studentClassLogRepository) {
        this.studentClassLogRepository = studentClassLogRepository;
    }

    /**
     * Return a {@link List} of {%link StudentClassLog} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<StudentClassLog> findByCriteria(StudentClassLogCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<StudentClassLog> specification = createSpecification(criteria);
        return studentClassLogRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {%link StudentClassLog} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<StudentClassLog> findByCriteria(StudentClassLogCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<StudentClassLog> specification = createSpecification(criteria);
        return studentClassLogRepository.findAll(specification, page);
    }

    /**
     * Function to convert StudentClassLogCriteria to a {@link Specifications}
     */
    private Specifications<StudentClassLog> createSpecification(StudentClassLogCriteria criteria) {
        Specifications<StudentClassLog> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getRegionId() != null) {
                specification = specification.and(buildSpecification(criteria.getRegionId(), StudentClassLog_.regionId));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), StudentClassLog_.id));
            }
            if (criteria.getActualTakenDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getActualTakenDate(), StudentClassLog_.actualTakenDate));
            }
            if (criteria.getComments() != null) {
                specification = specification.and(buildStringSpecification(criteria.getComments(), StudentClassLog_.comments));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), StudentClassLog_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), StudentClassLog_.createdDate));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), StudentClassLog_.lastModifiedBy));
            }
            if (criteria.getLastModifiedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModifiedDate(), StudentClassLog_.lastModifiedDate));
            }
            if (criteria.getPoint() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPoint(), StudentClassLog_.point));
            }
            if (criteria.getStudentId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getStudentId(), StudentClassLog_.student, Student_.id));
            }
            if (criteria.getArrangementId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getArrangementId(), StudentClassLog_.arrangement, ClassArrangement_.id));
            }
            if (criteria.getClassId() != null) {
                specification = specification.and(classId(criteria.getClassId()));
            }
            if (criteria.getArrangementStart() != null && criteria.getArrangementEnd() != null) {
                specification = specification.and(classArrangementDateStartBetween(criteria.getArrangementStart(), criteria.getArrangementEnd()));
            }
        }
        return specification;
    }

    private Specification classId(Long classId) {

        return (root, query, cb) -> cb.equal(root.get(StudentClassLog_.arrangement).get(ClassArrangement_.clazz).get(Product_.id), classId);
    }

    private Specification classArrangementDateStartBetween(Instant start, Instant end) {
        return (root, query, cb) -> cb.between(root.get(StudentClassLog_.arrangement).get(ClassArrangement_.startDate), start, end);
    }
}
