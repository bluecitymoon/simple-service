package com.pure.service.service;


import com.pure.service.domain.StudentFrozen;
import com.pure.service.domain.StudentFrozen_;
import com.pure.service.domain.Student_;
import com.pure.service.repository.StudentFrozenRepository;
import com.pure.service.service.dto.StudentFrozenCriteria;
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
 * Service for executing complex queries for StudentFrozen entities in the database.
 * The main input is a {@link StudentFrozenCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {%link StudentFrozen} or a {@link Page} of {%link StudentFrozen} which fulfills the criterias
 */
@Service
@Transactional(readOnly = true)
public class StudentFrozenQueryService extends QueryService<StudentFrozen> {

    private final Logger log = LoggerFactory.getLogger(StudentFrozenQueryService.class);


    private final StudentFrozenRepository studentFrozenRepository;

    public StudentFrozenQueryService(StudentFrozenRepository studentFrozenRepository) {
        this.studentFrozenRepository = studentFrozenRepository;
    }

    /**
     * Return a {@link List} of {%link StudentFrozen} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<StudentFrozen> findByCriteria(StudentFrozenCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<StudentFrozen> specification = createSpecification(criteria);
        return studentFrozenRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {%link StudentFrozen} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<StudentFrozen> findByCriteria(StudentFrozenCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<StudentFrozen> specification = createSpecification(criteria);
        return studentFrozenRepository.findAll(specification, page);
    }

    /**
     * Function to convert StudentFrozenCriteria to a {@link Specifications}
     */
    private Specifications<StudentFrozen> createSpecification(StudentFrozenCriteria criteria) {
        Specifications<StudentFrozen> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getRegionId() != null) {
                specification = specification.and(buildSpecification(criteria.getRegionId(), StudentFrozen_.regionId));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), StudentFrozen_.id));
            }
            if (criteria.getStartDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartDate(), StudentFrozen_.startDate));
            }
            if (criteria.getEndDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndDate(), StudentFrozen_.endDate));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), StudentFrozen_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), StudentFrozen_.createdDate));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), StudentFrozen_.lastModifiedBy));
            }
            if (criteria.getLastModifiedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModifiedDate(), StudentFrozen_.lastModifiedDate));
            }
            if (criteria.getStudentId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getStudentId(), StudentFrozen_.student, Student_.id));
            }
        }
        return specification;
    }

}
