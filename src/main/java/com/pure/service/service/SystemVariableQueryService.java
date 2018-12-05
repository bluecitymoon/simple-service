package com.pure.service.service;


import com.pure.service.domain.SystemVariable;
import com.pure.service.domain.SystemVariable_;
import com.pure.service.repository.SystemVariableRepository;
import com.pure.service.service.dto.SystemVariableCriteria;
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
 * Service for executing complex queries for SystemVariable entities in the database.
 * The main input is a {@link SystemVariableCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {%link SystemVariable} or a {@link Page} of {%link SystemVariable} which fulfills the criterias
 */
@Service
@Transactional(readOnly = true)
public class SystemVariableQueryService extends QueryService<SystemVariable> {

    private final Logger log = LoggerFactory.getLogger(SystemVariableQueryService.class);


    private final SystemVariableRepository systemVariableRepository;

    public SystemVariableQueryService(SystemVariableRepository systemVariableRepository) {
        this.systemVariableRepository = systemVariableRepository;
    }

    /**
     * Return a {@link List} of {%link SystemVariable} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SystemVariable> findByCriteria(SystemVariableCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<SystemVariable> specification = createSpecification(criteria);
        return systemVariableRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {%link SystemVariable} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SystemVariable> findByCriteria(SystemVariableCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<SystemVariable> specification = createSpecification(criteria);
        return systemVariableRepository.findAll(specification, page);
    }

    /**
     * Function to convert SystemVariableCriteria to a {@link Specifications}
     */
    private Specifications<SystemVariable> createSpecification(SystemVariableCriteria criteria) {
        Specifications<SystemVariable> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getRegionId() != null) {
                specification = specification.and(buildSpecification(criteria.getRegionId(), SystemVariable_.regionId));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), SystemVariable_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), SystemVariable_.name));
            }
            if (criteria.getAttrValue() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAttrValue(), SystemVariable_.attrValue));
            }
            if (criteria.getComments() != null) {
                specification = specification.and(buildStringSpecification(criteria.getComments(), SystemVariable_.comments));
            }
        }
        return specification;
    }

}
