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

import com.pure.service.domain.TimetableItem;
import com.pure.service.domain.*; // for static metamodels
import com.pure.service.repository.TimetableItemRepository;
import com.pure.service.service.dto.TimetableItemCriteria;


/**
 * Service for executing complex queries for TimetableItem entities in the database.
 * The main input is a {@link TimetableItemCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {%link TimetableItem} or a {@link Page} of {%link TimetableItem} which fulfills the criterias
 */
@Service
@Transactional(readOnly = true)
public class TimetableItemQueryService extends QueryService<TimetableItem> {

    private final Logger log = LoggerFactory.getLogger(TimetableItemQueryService.class);


    private final TimetableItemRepository timetableItemRepository;

    public TimetableItemQueryService(TimetableItemRepository timetableItemRepository) {
        this.timetableItemRepository = timetableItemRepository;
    }

    /**
     * Return a {@link List} of {%link TimetableItem} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TimetableItem> findByCriteria(TimetableItemCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<TimetableItem> specification = createSpecification(criteria);
        return timetableItemRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {%link TimetableItem} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TimetableItem> findByCriteria(TimetableItemCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<TimetableItem> specification = createSpecification(criteria);
        return timetableItemRepository.findAll(specification, page);
    }

    /**
     * Function to convert TimetableItemCriteria to a {@link Specifications}
     */
    private Specifications<TimetableItem> createSpecification(TimetableItemCriteria criteria) {
        Specifications<TimetableItem> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), TimetableItem_.id));
            }
            if (criteria.getWeekdayName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getWeekdayName(), TimetableItem_.weekdayName));
            }
            if (criteria.getClassroom() != null) {
                specification = specification.and(buildStringSpecification(criteria.getClassroom(), TimetableItem_.classroom));
            }
            if (criteria.getCourseName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCourseName(), TimetableItem_.courseName));
            }
            if (criteria.getTeacherName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTeacherName(), TimetableItem_.teacherName));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), TimetableItem_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), TimetableItem_.createdDate));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), TimetableItem_.lastModifiedBy));
            }
            if (criteria.getLastModifiedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModifiedDate(), TimetableItem_.lastModifiedDate));
            }
            if (criteria.getClazzId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getClazzId(), TimetableItem_.clazz, Product_.id));
            }
            if (criteria.getTimeSegmentId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getTimeSegmentId(), TimetableItem_.timeSegment, TimeSegment_.id));
            }
        }
        return specification;
    }

}
