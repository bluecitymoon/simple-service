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

import com.pure.service.domain.CustomerCardCourse;
import com.pure.service.domain.*; // for static metamodels
import com.pure.service.repository.CustomerCardCourseRepository;
import com.pure.service.service.dto.CustomerCardCourseCriteria;


/**
 * Service for executing complex queries for CustomerCardCourse entities in the database.
 * The main input is a {@link CustomerCardCourseCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {%link CustomerCardCourse} or a {@link Page} of {%link CustomerCardCourse} which fulfills the criterias
 */
@Service
@Transactional(readOnly = true)
public class CustomerCardCourseQueryService extends QueryService<CustomerCardCourse> {

    private final Logger log = LoggerFactory.getLogger(CustomerCardCourseQueryService.class);


    private final CustomerCardCourseRepository customerCardCourseRepository;

    public CustomerCardCourseQueryService(CustomerCardCourseRepository customerCardCourseRepository) {
        this.customerCardCourseRepository = customerCardCourseRepository;
    }

    /**
     * Return a {@link List} of {%link CustomerCardCourse} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CustomerCardCourse> findByCriteria(CustomerCardCourseCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<CustomerCardCourse> specification = createSpecification(criteria);
        return customerCardCourseRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {%link CustomerCardCourse} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CustomerCardCourse> findByCriteria(CustomerCardCourseCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<CustomerCardCourse> specification = createSpecification(criteria);
        return customerCardCourseRepository.findAll(specification, page);
    }

    /**
     * Function to convert CustomerCardCourseCriteria to a {@link Specifications}
     */
    private Specifications<CustomerCardCourse> createSpecification(CustomerCardCourseCriteria criteria) {
        Specifications<CustomerCardCourse> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), CustomerCardCourse_.id));
            }
            if (criteria.getCustomerCardId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getCustomerCardId(), CustomerCardCourse_.customerCard, CustomerCard_.id));
            }
            if (criteria.getCourseId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getCourseId(), CustomerCardCourse_.course, Course_.id));
            }
        }
        return specification;
    }

}
