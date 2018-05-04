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

import com.pure.service.domain.CustomerScheduleStatus;
import com.pure.service.domain.*; // for static metamodels
import com.pure.service.repository.CustomerScheduleStatusRepository;
import com.pure.service.service.dto.CustomerScheduleStatusCriteria;


/**
 * Service for executing complex queries for CustomerScheduleStatus entities in the database.
 * The main input is a {@link CustomerScheduleStatusCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {%link CustomerScheduleStatus} or a {@link Page} of {%link CustomerScheduleStatus} which fulfills the criterias
 */
@Service
@Transactional(readOnly = true)
public class CustomerScheduleStatusQueryService extends QueryService<CustomerScheduleStatus> {

    private final Logger log = LoggerFactory.getLogger(CustomerScheduleStatusQueryService.class);


    private final CustomerScheduleStatusRepository customerScheduleStatusRepository;

    public CustomerScheduleStatusQueryService(CustomerScheduleStatusRepository customerScheduleStatusRepository) {
        this.customerScheduleStatusRepository = customerScheduleStatusRepository;
    }

    /**
     * Return a {@link List} of {%link CustomerScheduleStatus} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CustomerScheduleStatus> findByCriteria(CustomerScheduleStatusCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<CustomerScheduleStatus> specification = createSpecification(criteria);
        return customerScheduleStatusRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {%link CustomerScheduleStatus} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CustomerScheduleStatus> findByCriteria(CustomerScheduleStatusCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<CustomerScheduleStatus> specification = createSpecification(criteria);
        return customerScheduleStatusRepository.findAll(specification, page);
    }

    /**
     * Function to convert CustomerScheduleStatusCriteria to a {@link Specifications}
     */
    private Specifications<CustomerScheduleStatus> createSpecification(CustomerScheduleStatusCriteria criteria) {
        Specifications<CustomerScheduleStatus> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), CustomerScheduleStatus_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), CustomerScheduleStatus_.name));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), CustomerScheduleStatus_.code));
            }
            if (criteria.getLabelStyle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLabelStyle(), CustomerScheduleStatus_.labelStyle));
            }
        }
        return specification;
    }

}
