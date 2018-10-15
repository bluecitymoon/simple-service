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

import com.pure.service.domain.UserRegion;
import com.pure.service.domain.*; // for static metamodels
import com.pure.service.repository.UserRegionRepository;
import com.pure.service.service.dto.UserRegionCriteria;


/**
 * Service for executing complex queries for UserRegion entities in the database.
 * The main input is a {@link UserRegionCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {%link UserRegion} or a {@link Page} of {%link UserRegion} which fulfills the criterias
 */
@Service
@Transactional(readOnly = true)
public class UserRegionQueryService extends QueryService<UserRegion> {

    private final Logger log = LoggerFactory.getLogger(UserRegionQueryService.class);


    private final UserRegionRepository userRegionRepository;

    public UserRegionQueryService(UserRegionRepository userRegionRepository) {
        this.userRegionRepository = userRegionRepository;
    }

    /**
     * Return a {@link List} of {%link UserRegion} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<UserRegion> findByCriteria(UserRegionCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<UserRegion> specification = createSpecification(criteria);
        return userRegionRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {%link UserRegion} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<UserRegion> findByCriteria(UserRegionCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<UserRegion> specification = createSpecification(criteria);
        return userRegionRepository.findAll(specification, page);
    }

    /**
     * Function to convert UserRegionCriteria to a {@link Specifications}
     */
    private Specifications<UserRegion> createSpecification(UserRegionCriteria criteria) {
        Specifications<UserRegion> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), UserRegion_.id));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), UserRegion_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), UserRegion_.createdDate));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), UserRegion_.lastModifiedBy));
            }
            if (criteria.getLastModifiedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModifiedDate(), UserRegion_.lastModifiedDate));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getUserId(), UserRegion_.user, User_.id));
            }
            if (criteria.getRegionId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getRegionId(), UserRegion_.region, Region_.id));
            }
        }
        return specification;
    }

}
