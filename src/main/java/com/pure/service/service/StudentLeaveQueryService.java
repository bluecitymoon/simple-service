package com.pure.service.service;


import com.pure.service.domain.ClassArrangement_;
import com.pure.service.domain.StudentLeave;
import com.pure.service.domain.StudentLeave_;
import com.pure.service.domain.Student_;
import com.pure.service.repository.StudentLeaveRepository;
import com.pure.service.service.dto.StudentLeaveCriteria;
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
 * Service for executing complex queries for StudentLeave entities in the database.
 * The main input is a {@link StudentLeaveCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {%link StudentLeave} or a {@link Page} of {%link StudentLeave} which fulfills the criterias
 */
@Service
@Transactional(readOnly = true)
public class StudentLeaveQueryService extends QueryService<StudentLeave> {

    private final Logger log = LoggerFactory.getLogger(StudentLeaveQueryService.class);


    private final StudentLeaveRepository studentLeaveRepository;

    public StudentLeaveQueryService(StudentLeaveRepository studentLeaveRepository) {
        this.studentLeaveRepository = studentLeaveRepository;
    }

    /**
     * Return a {@link List} of {%link StudentLeave} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<StudentLeave> findByCriteria(StudentLeaveCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<StudentLeave> specification = createSpecification(criteria);
        return studentLeaveRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {%link StudentLeave} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<StudentLeave> findByCriteria(StudentLeaveCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<StudentLeave> specification = createSpecification(criteria);
        return studentLeaveRepository.findAll(specification, page);
    }

    /**
     * Function to convert StudentLeaveCriteria to a {@link Specifications}
     */
    private Specifications<StudentLeave> createSpecification(StudentLeaveCriteria criteria) {
        Specifications<StudentLeave> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getRegionId() != null) {
                specification = specification.and(buildSpecification(criteria.getRegionId(), StudentLeave_.regionId));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), StudentLeave_.id));
            }
            if (criteria.getBalance() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBalance(), StudentLeave_.balance));
            }
            if (criteria.getCalculateStartDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCalculateStartDate(), StudentLeave_.calculateStartDate));
            }
            if (criteria.getCalculateEndDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCalculateEndDate(), StudentLeave_.calculateEndDate));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), StudentLeave_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), StudentLeave_.createdDate));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), StudentLeave_.lastModifiedBy));
            }
            if (criteria.getLastModifiedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModifiedDate(), StudentLeave_.lastModifiedDate));
            }
            if (criteria.getStudentId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getStudentId(), StudentLeave_.student, Student_.id));
            }
            if (criteria.getClassArrangementId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getClassArrangementId(), StudentLeave_.classArrangement, ClassArrangement_.id));
            }
        }
        return specification;
    }

}
