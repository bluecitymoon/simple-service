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

import com.pure.service.domain.VistedCustomerStatus;
import com.pure.service.domain.*; // for static metamodels
import com.pure.service.repository.VistedCustomerStatusRepository;
import com.pure.service.service.dto.VistedCustomerStatusCriteria;


/**
 * Service for executing complex queries for VistedCustomerStatus entities in the database.
 * The main input is a {@link VistedCustomerStatusCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {%link VistedCustomerStatus} or a {@link Page} of {%link VistedCustomerStatus} which fulfills the criterias
 */
@Service
@Transactional(readOnly = true)
public class VistedCustomerStatusQueryService extends QueryService<VistedCustomerStatus> {

    private final Logger log = LoggerFactory.getLogger(VistedCustomerStatusQueryService.class);


    private final VistedCustomerStatusRepository vistedCustomerStatusRepository;

    public VistedCustomerStatusQueryService(VistedCustomerStatusRepository vistedCustomerStatusRepository) {
        this.vistedCustomerStatusRepository = vistedCustomerStatusRepository;
    }

    /**
     * Return a {@link List} of {%link VistedCustomerStatus} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<VistedCustomerStatus> findByCriteria(VistedCustomerStatusCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<VistedCustomerStatus> specification = createSpecification(criteria);
        return vistedCustomerStatusRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {%link VistedCustomerStatus} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<VistedCustomerStatus> findByCriteria(VistedCustomerStatusCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<VistedCustomerStatus> specification = createSpecification(criteria);
        return vistedCustomerStatusRepository.findAll(specification, page);
    }

    /**
     * Function to convert VistedCustomerStatusCriteria to a {@link Specifications}
     */
    private Specifications<VistedCustomerStatus> createSpecification(VistedCustomerStatusCriteria criteria) {
        Specifications<VistedCustomerStatus> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), VistedCustomerStatus_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), VistedCustomerStatus_.name));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), VistedCustomerStatus_.code));
            }
            if (criteria.getComments() != null) {
                specification = specification.and(buildStringSpecification(criteria.getComments(), VistedCustomerStatus_.comments));
            }
        }
        return specification;
    }

}
