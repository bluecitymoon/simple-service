package com.pure.service.service;


import com.pure.service.domain.ClassArrangement_;
import com.pure.service.domain.StudentAbsenceLog;
import com.pure.service.domain.StudentAbsenceLog_;
import com.pure.service.domain.Student_;
import com.pure.service.region.RegionUtils;
import com.pure.service.repository.StudentAbsenceLogRepository;
import com.pure.service.service.dto.StudentAbsenceLogCriteria;
import io.github.jhipster.service.QueryService;
import io.github.jhipster.service.filter.LongFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * Service for executing complex queries for StudentAbsenceLog entities in the database.
 * The main input is a {@link StudentAbsenceLogCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {%link StudentAbsenceLog} or a {@link Page} of {%link StudentAbsenceLog} which fulfills the criterias
 */
@Service
@Transactional(readOnly = true)
public class StudentAbsenceLogQueryService extends QueryService<StudentAbsenceLog> {

    private final Logger log = LoggerFactory.getLogger(StudentAbsenceLogQueryService.class);


    private final StudentAbsenceLogRepository studentAbsenceLogRepository;

    public StudentAbsenceLogQueryService(StudentAbsenceLogRepository studentAbsenceLogRepository) {
        this.studentAbsenceLogRepository = studentAbsenceLogRepository;
    }

    /**
     * Return a {@link List} of {%link StudentAbsenceLog} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<StudentAbsenceLog> findByCriteria(StudentAbsenceLogCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<StudentAbsenceLog> specification = createSpecification(criteria);
        return studentAbsenceLogRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {%link StudentAbsenceLog} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<StudentAbsenceLog> findByCriteria(StudentAbsenceLogCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        Specifications<StudentAbsenceLog> specification = createSpecification(criteria);

        Long regionId = RegionUtils.getRegionIdForCurrentUser();

        if (regionId != null) {
            LongFilter longFilter = new LongFilter();
            longFilter.setEquals(regionId);

            specification = specification.and(buildSpecification(longFilter, StudentAbsenceLog_.regionId));

        }
        return studentAbsenceLogRepository.findAll(specification, page);
    }

    /**
     * Function to convert StudentAbsenceLogCriteria to a {@link Specifications}
     */
    private Specifications<StudentAbsenceLog> createSpecification(StudentAbsenceLogCriteria criteria) {
        Specifications<StudentAbsenceLog> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), StudentAbsenceLog_.id));
            }
            if (criteria.getComments() != null) {
                specification = specification.and(buildStringSpecification(criteria.getComments(), StudentAbsenceLog_.comments));
            }
            if (criteria.getRegionId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRegionId(), StudentAbsenceLog_.regionId));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), StudentAbsenceLog_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), StudentAbsenceLog_.createdDate));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), StudentAbsenceLog_.lastModifiedBy));
            }
            if (criteria.getLastModifiedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModifiedDate(), StudentAbsenceLog_.lastModifiedDate));
            }
            if (criteria.getClassCount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getClassCount(), StudentAbsenceLog_.classCount));
            }
            if (criteria.getStudentId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getStudentId(), StudentAbsenceLog_.student, Student_.id));
            }
            if (criteria.getClassArrangementId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getClassArrangementId(), StudentAbsenceLog_.classArrangement, ClassArrangement_.id));
            }
        }
        return specification;
    }

}
