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

import com.pure.service.domain.ConsultantReport;
import com.pure.service.domain.*; // for static metamodels
import com.pure.service.repository.ConsultantReportRepository;
import com.pure.service.service.dto.ConsultantReportCriteria;


/**
 * Service for executing complex queries for ConsultantReport entities in the database.
 * The main input is a {@link ConsultantReportCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {%link ConsultantReport} or a {@link Page} of {%link ConsultantReport} which fulfills the criterias
 */
@Service
@Transactional(readOnly = true)
public class ConsultantReportQueryService extends QueryService<ConsultantReport> {

    private final Logger log = LoggerFactory.getLogger(ConsultantReportQueryService.class);


    private final ConsultantReportRepository consultantReportRepository;

    public ConsultantReportQueryService(ConsultantReportRepository consultantReportRepository) {
        this.consultantReportRepository = consultantReportRepository;
    }

    /**
     * Return a {@link List} of {%link ConsultantReport} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ConsultantReport> findByCriteria(ConsultantReportCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<ConsultantReport> specification = createSpecification(criteria);
        return consultantReportRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {%link ConsultantReport} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ConsultantReport> findByCriteria(ConsultantReportCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<ConsultantReport> specification = createSpecification(criteria);
        return consultantReportRepository.findAll(specification, page);
    }

    /**
     * Function to convert ConsultantReportCriteria to a {@link Specifications}
     */
    private Specifications<ConsultantReport> createSpecification(ConsultantReportCriteria criteria) {
        Specifications<ConsultantReport> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ConsultantReport_.id));
            }
            if (criteria.getWeekName() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getWeekName(), ConsultantReport_.weekName));
            }
            if (criteria.getWeekFromDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getWeekFromDate(), ConsultantReport_.weekFromDate));
            }
            if (criteria.getWeekEndDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getWeekEndDate(), ConsultantReport_.weekEndDate));
            }
            if (criteria.getVisitedCount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVisitedCount(), ConsultantReport_.visitedCount));
            }
            if (criteria.getDealedMoneyAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDealedMoneyAmount(), ConsultantReport_.dealedMoneyAmount));
            }
        }
        return specification;
    }

}
