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

import com.pure.service.domain.Region;
import com.pure.service.domain.*; // for static metamodels
import com.pure.service.repository.RegionRepository;
import com.pure.service.service.dto.RegionCriteria;


/**
 * Service for executing complex queries for Region entities in the database.
 * The main input is a {@link RegionCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {%link Region} or a {@link Page} of {%link Region} which fulfills the criterias
 */
@Service
@Transactional(readOnly = true)
public class RegionQueryService extends QueryService<Region> {

    private final Logger log = LoggerFactory.getLogger(RegionQueryService.class);


    private final RegionRepository regionRepository;

    public RegionQueryService(RegionRepository regionRepository) {
        this.regionRepository = regionRepository;
    }

    /**
     * Return a {@link List} of {%link Region} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Region> findByCriteria(RegionCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<Region> specification = createSpecification(criteria);
        return regionRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {%link Region} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Region> findByCriteria(RegionCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<Region> specification = createSpecification(criteria);
        return regionRepository.findAll(specification, page);
    }

    /**
     * Function to convert RegionCriteria to a {@link Specifications}
     */
    private Specifications<Region> createSpecification(RegionCriteria criteria) {
        Specifications<Region> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Region_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Region_.name));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), Region_.code));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), Region_.active));
            }
            if (criteria.getAddress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAddress(), Region_.address));
            }
            if (criteria.getPhone() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPhone(), Region_.phone));
            }
        }
        return specification;
    }

}
