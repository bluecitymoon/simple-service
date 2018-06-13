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

import com.pure.service.domain.TaskStatus;
import com.pure.service.domain.*; // for static metamodels
import com.pure.service.repository.TaskStatusRepository;
import com.pure.service.service.dto.TaskStatusCriteria;


/**
 * Service for executing complex queries for TaskStatus entities in the database.
 * The main input is a {@link TaskStatusCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {%link TaskStatus} or a {@link Page} of {%link TaskStatus} which fulfills the criterias
 */
@Service
@Transactional(readOnly = true)
public class TaskStatusQueryService extends QueryService<TaskStatus> {

    private final Logger log = LoggerFactory.getLogger(TaskStatusQueryService.class);


    private final TaskStatusRepository taskStatusRepository;

    public TaskStatusQueryService(TaskStatusRepository taskStatusRepository) {
        this.taskStatusRepository = taskStatusRepository;
    }

    /**
     * Return a {@link List} of {%link TaskStatus} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TaskStatus> findByCriteria(TaskStatusCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<TaskStatus> specification = createSpecification(criteria);
        return taskStatusRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {%link TaskStatus} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TaskStatus> findByCriteria(TaskStatusCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<TaskStatus> specification = createSpecification(criteria);
        return taskStatusRepository.findAll(specification, page);
    }

    /**
     * Function to convert TaskStatusCriteria to a {@link Specifications}
     */
    private Specifications<TaskStatus> createSpecification(TaskStatusCriteria criteria) {
        Specifications<TaskStatus> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), TaskStatus_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), TaskStatus_.name));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), TaskStatus_.code));
            }
            if (criteria.getLabelCss() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLabelCss(), TaskStatus_.labelCss));
            }
        }
        return specification;
    }

}
