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

import com.pure.service.domain.ScheduledTaskLog;
import com.pure.service.domain.*; // for static metamodels
import com.pure.service.repository.ScheduledTaskLogRepository;
import com.pure.service.service.dto.ScheduledTaskLogCriteria;


/**
 * Service for executing complex queries for ScheduledTaskLog entities in the database.
 * The main input is a {@link ScheduledTaskLogCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {%link ScheduledTaskLog} or a {@link Page} of {%link ScheduledTaskLog} which fulfills the criterias
 */
@Service
@Transactional(readOnly = true)
public class ScheduledTaskLogQueryService extends QueryService<ScheduledTaskLog> {

    private final Logger log = LoggerFactory.getLogger(ScheduledTaskLogQueryService.class);


    private final ScheduledTaskLogRepository scheduledTaskLogRepository;

    public ScheduledTaskLogQueryService(ScheduledTaskLogRepository scheduledTaskLogRepository) {
        this.scheduledTaskLogRepository = scheduledTaskLogRepository;
    }

    /**
     * Return a {@link List} of {%link ScheduledTaskLog} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ScheduledTaskLog> findByCriteria(ScheduledTaskLogCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<ScheduledTaskLog> specification = createSpecification(criteria);
        return scheduledTaskLogRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {%link ScheduledTaskLog} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ScheduledTaskLog> findByCriteria(ScheduledTaskLogCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<ScheduledTaskLog> specification = createSpecification(criteria);
        return scheduledTaskLogRepository.findAll(specification, page);
    }

    /**
     * Function to convert ScheduledTaskLogCriteria to a {@link Specifications}
     */
    private Specifications<ScheduledTaskLog> createSpecification(ScheduledTaskLogCriteria criteria) {
        Specifications<ScheduledTaskLog> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ScheduledTaskLog_.id));
            }
            if (criteria.getClassName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getClassName(), ScheduledTaskLog_.className));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), ScheduledTaskLog_.name));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), ScheduledTaskLog_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), ScheduledTaskLog_.createdDate));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), ScheduledTaskLog_.lastModifiedBy));
            }
            if (criteria.getLastModifiedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModifiedDate(), ScheduledTaskLog_.lastModifiedDate));
            }
        }
        return specification;
    }

}
