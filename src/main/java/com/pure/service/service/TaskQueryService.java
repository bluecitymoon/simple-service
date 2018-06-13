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

import com.pure.service.domain.Task;
import com.pure.service.domain.*; // for static metamodels
import com.pure.service.repository.TaskRepository;
import com.pure.service.service.dto.TaskCriteria;


/**
 * Service for executing complex queries for Task entities in the database.
 * The main input is a {@link TaskCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {%link Task} or a {@link Page} of {%link Task} which fulfills the criterias
 */
@Service
@Transactional(readOnly = true)
public class TaskQueryService extends QueryService<Task> {

    private final Logger log = LoggerFactory.getLogger(TaskQueryService.class);


    private final TaskRepository taskRepository;

    public TaskQueryService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    /**
     * Return a {@link List} of {%link Task} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Task> findByCriteria(TaskCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<Task> specification = createSpecification(criteria);
        return taskRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {%link Task} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Task> findByCriteria(TaskCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<Task> specification = createSpecification(criteria);
        return taskRepository.findAll(specification, page);
    }

    /**
     * Function to convert TaskCriteria to a {@link Specifications}
     */
    private Specifications<Task> createSpecification(TaskCriteria criteria) {
        Specifications<Task> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Task_.id));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Task_.description));
            }
            if (criteria.getEstimateExecuteDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEstimateExecuteDate(), Task_.estimateExecuteDate));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), Task_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), Task_.createdDate));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), Task_.lastModifiedBy));
            }
            if (criteria.getLastModifiedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModifiedDate(), Task_.lastModifiedDate));
            }
            if (criteria.getAssigneeId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getAssigneeId(), Task_.assignee, User_.id));
            }
            if (criteria.getTaskStatusId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getTaskStatusId(), Task_.taskStatus, TaskStatus_.id));
            }
            if (criteria.getReporterId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getReporterId(), Task_.reporter, User_.id));
            }
        }
        return specification;
    }

}
