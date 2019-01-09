package com.pure.service.service;


import com.pure.service.domain.StudentClassLogDailyReport;
import com.pure.service.domain.StudentClassLogDailyReport_;
import com.pure.service.repository.StudentClassLogDailyReportRepository;
import com.pure.service.service.dto.StudentClassLogDailyReportCriteria;
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
 * Service for executing complex queries for StudentClassLogDailyReport entities in the database.
 * The main input is a {@link StudentClassLogDailyReportCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {%link StudentClassLogDailyReport} or a {@link Page} of {%link StudentClassLogDailyReport} which fulfills the criterias
 */
@Service
@Transactional(readOnly = true)
public class StudentClassLogDailyReportQueryService extends QueryService<StudentClassLogDailyReport> {

    private final Logger log = LoggerFactory.getLogger(StudentClassLogDailyReportQueryService.class);


    private final StudentClassLogDailyReportRepository studentClassLogDailyReportRepository;

    public StudentClassLogDailyReportQueryService(StudentClassLogDailyReportRepository studentClassLogDailyReportRepository) {
        this.studentClassLogDailyReportRepository = studentClassLogDailyReportRepository;
    }

    /**
     * Return a {@link List} of {%link StudentClassLogDailyReport} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<StudentClassLogDailyReport> findByCriteria(StudentClassLogDailyReportCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<StudentClassLogDailyReport> specification = createSpecification(criteria);
        return studentClassLogDailyReportRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {%link StudentClassLogDailyReport} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<StudentClassLogDailyReport> findByCriteria(StudentClassLogDailyReportCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<StudentClassLogDailyReport> specification = createSpecification(criteria);
        return studentClassLogDailyReportRepository.findAll(specification, page);
    }

    /**
     * Function to convert StudentClassLogDailyReportCriteria to a {@link Specifications}
     */
    private Specifications<StudentClassLogDailyReport> createSpecification(StudentClassLogDailyReportCriteria criteria) {
        Specifications<StudentClassLogDailyReport> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getRegionId() != null) {
                specification = specification.and(buildSpecification(criteria.getRegionId(), StudentClassLogDailyReport_.regionId));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), StudentClassLogDailyReport_.id));
            }
            if (criteria.getShouldTaken() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getShouldTaken(), StudentClassLogDailyReport_.shouldTaken));
            }
            if (criteria.getLeave() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLeave(), StudentClassLogDailyReport_.leave));
            }
            if (criteria.getAbsence() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAbsence(), StudentClassLogDailyReport_.absence));
            }
            if (criteria.getAdded() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAdded(), StudentClassLogDailyReport_.added));
            }
            if (criteria.getActualTaken() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getActualTaken(), StudentClassLogDailyReport_.actualTaken));
            }
            if (criteria.getLogDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLogDate(), StudentClassLogDailyReport_.logDate));
            }
        }
        return specification;
    }

}
