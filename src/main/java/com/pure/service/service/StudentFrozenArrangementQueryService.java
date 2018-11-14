package com.pure.service.service;


import com.pure.service.domain.ClassArrangement_;
import com.pure.service.domain.StudentFrozenArrangement;
import com.pure.service.domain.StudentFrozenArrangement_;
import com.pure.service.domain.StudentFrozen_;
import com.pure.service.domain.Student_;
import com.pure.service.repository.StudentFrozenArrangementRepository;
import com.pure.service.service.dto.StudentFrozenArrangementCriteria;
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
 * Service for executing complex queries for StudentFrozenArrangement entities in the database.
 * The main input is a {@link StudentFrozenArrangementCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {%link StudentFrozenArrangement} or a {@link Page} of {%link StudentFrozenArrangement} which fulfills the criterias
 */
@Service
@Transactional(readOnly = true)
public class StudentFrozenArrangementQueryService extends QueryService<StudentFrozenArrangement> {

    private final Logger log = LoggerFactory.getLogger(StudentFrozenArrangementQueryService.class);


    private final StudentFrozenArrangementRepository studentFrozenArrangementRepository;

    public StudentFrozenArrangementQueryService(StudentFrozenArrangementRepository studentFrozenArrangementRepository) {
        this.studentFrozenArrangementRepository = studentFrozenArrangementRepository;
    }

    /**
     * Return a {@link List} of {%link StudentFrozenArrangement} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<StudentFrozenArrangement> findByCriteria(StudentFrozenArrangementCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<StudentFrozenArrangement> specification = createSpecification(criteria);
        return studentFrozenArrangementRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {%link StudentFrozenArrangement} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<StudentFrozenArrangement> findByCriteria(StudentFrozenArrangementCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<StudentFrozenArrangement> specification = createSpecification(criteria);
        return studentFrozenArrangementRepository.findAll(specification, page);
    }

    /**
     * Function to convert StudentFrozenArrangementCriteria to a {@link Specifications}
     */
    private Specifications<StudentFrozenArrangement> createSpecification(StudentFrozenArrangementCriteria criteria) {
        Specifications<StudentFrozenArrangement> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getRegionId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRegionId(), StudentFrozenArrangement_.regionId));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), StudentFrozenArrangement_.id));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), StudentFrozenArrangement_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), StudentFrozenArrangement_.createdDate));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), StudentFrozenArrangement_.lastModifiedBy));
            }
            if (criteria.getLastModifiedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModifiedDate(), StudentFrozenArrangement_.lastModifiedDate));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), StudentFrozenArrangement_.active));
            }
            if (criteria.getStudentId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getStudentId(), StudentFrozenArrangement_.student, Student_.id));
            }
            if (criteria.getClassArrangementId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getClassArrangementId(), StudentFrozenArrangement_.classArrangement, ClassArrangement_.id));
            }
            if (criteria.getStudentFrozenId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getStudentFrozenId(), StudentFrozenArrangement_.studentFrozen, StudentFrozen_.id));
            }
        }
        return specification;
    }

}
