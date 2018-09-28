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

import com.pure.service.domain.TimeSegment;
import com.pure.service.domain.*; // for static metamodels
import com.pure.service.repository.TimeSegmentRepository;
import com.pure.service.service.dto.TimeSegmentCriteria;


/**
 * Service for executing complex queries for TimeSegment entities in the database.
 * The main input is a {@link TimeSegmentCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {%link TimeSegment} or a {@link Page} of {%link TimeSegment} which fulfills the criterias
 */
@Service
@Transactional(readOnly = true)
public class TimeSegmentQueryService extends QueryService<TimeSegment> {

    private final Logger log = LoggerFactory.getLogger(TimeSegmentQueryService.class);


    private final TimeSegmentRepository timeSegmentRepository;

    public TimeSegmentQueryService(TimeSegmentRepository timeSegmentRepository) {
        this.timeSegmentRepository = timeSegmentRepository;
    }

    /**
     * Return a {@link List} of {%link TimeSegment} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TimeSegment> findByCriteria(TimeSegmentCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<TimeSegment> specification = createSpecification(criteria);
        return timeSegmentRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {%link TimeSegment} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TimeSegment> findByCriteria(TimeSegmentCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<TimeSegment> specification = createSpecification(criteria);
        return timeSegmentRepository.findAll(specification, page);
    }

    /**
     * Function to convert TimeSegmentCriteria to a {@link Specifications}
     */
    private Specifications<TimeSegment> createSpecification(TimeSegmentCriteria criteria) {
        Specifications<TimeSegment> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), TimeSegment_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), TimeSegment_.name));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), TimeSegment_.code));
            }
            if (criteria.getStart() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStart(), TimeSegment_.start));
            }
            if (criteria.getEnd() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEnd(), TimeSegment_.end));
            }
        }
        return specification;
    }

}
