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

import com.pure.service.domain.CustomerCommunicationSchedule;
import com.pure.service.domain.*; // for static metamodels
import com.pure.service.repository.CustomerCommunicationScheduleRepository;
import com.pure.service.service.dto.CustomerCommunicationScheduleCriteria;


/**
 * Service for executing complex queries for CustomerCommunicationSchedule entities in the database.
 * The main input is a {@link CustomerCommunicationScheduleCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {%link CustomerCommunicationSchedule} or a {@link Page} of {%link CustomerCommunicationSchedule} which fulfills the criterias
 */
@Service
@Transactional(readOnly = true)
public class CustomerCommunicationScheduleQueryService extends QueryService<CustomerCommunicationSchedule> {

    private final Logger log = LoggerFactory.getLogger(CustomerCommunicationScheduleQueryService.class);


    private final CustomerCommunicationScheduleRepository customerCommunicationScheduleRepository;

    public CustomerCommunicationScheduleQueryService(CustomerCommunicationScheduleRepository customerCommunicationScheduleRepository) {
        this.customerCommunicationScheduleRepository = customerCommunicationScheduleRepository;
    }

    /**
     * Return a {@link List} of {%link CustomerCommunicationSchedule} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CustomerCommunicationSchedule> findByCriteria(CustomerCommunicationScheduleCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<CustomerCommunicationSchedule> specification = createSpecification(criteria);
        return customerCommunicationScheduleRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {%link CustomerCommunicationSchedule} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CustomerCommunicationSchedule> findByCriteria(CustomerCommunicationScheduleCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<CustomerCommunicationSchedule> specification = createSpecification(criteria);
        return customerCommunicationScheduleRepository.findAll(specification, page);
    }

    /**
     * Function to convert CustomerCommunicationScheduleCriteria to a {@link Specifications}
     */
    private Specifications<CustomerCommunicationSchedule> createSpecification(CustomerCommunicationScheduleCriteria criteria) {
        Specifications<CustomerCommunicationSchedule> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), CustomerCommunicationSchedule_.id));
            }
            if (criteria.getSceduleDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSceduleDate(), CustomerCommunicationSchedule_.sceduleDate));
            }
            if (criteria.getComments() != null) {
                specification = specification.and(buildStringSpecification(criteria.getComments(), CustomerCommunicationSchedule_.comments));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), CustomerCommunicationSchedule_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), CustomerCommunicationSchedule_.createdDate));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), CustomerCommunicationSchedule_.lastModifiedBy));
            }
            if (criteria.getLastModifiedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModifiedDate(), CustomerCommunicationSchedule_.lastModifiedDate));
            }
            if (criteria.getActuallMeetDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getActuallMeetDate(), CustomerCommunicationSchedule_.actuallMeetDate));
            }
            if (criteria.getCustomerId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getCustomerId(), CustomerCommunicationSchedule_.customer, Customer_.id));
            }
            if (criteria.getFollowerId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getFollowerId(), CustomerCommunicationSchedule_.follower, User_.id));
            }
            if (criteria.getScheduleStatusId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getScheduleStatusId(), CustomerCommunicationSchedule_.scheduleStatus, CustomerScheduleStatus_.id));
            }
        }
        return specification;
    }

}