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

import com.pure.service.domain.Ad;
import com.pure.service.domain.*; // for static metamodels
import com.pure.service.repository.AdRepository;
import com.pure.service.service.dto.AdCriteria;


/**
 * Service for executing complex queries for Ad entities in the database.
 * The main input is a {@link AdCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {%link Ad} or a {@link Page} of {%link Ad} which fulfills the criterias
 */
@Service
@Transactional(readOnly = true)
public class AdQueryService extends QueryService<Ad> {

    private final Logger log = LoggerFactory.getLogger(AdQueryService.class);


    private final AdRepository adRepository;

    public AdQueryService(AdRepository adRepository) {
        this.adRepository = adRepository;
    }

    /**
     * Return a {@link List} of {%link Ad} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Ad> findByCriteria(AdCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<Ad> specification = createSpecification(criteria);
        return adRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {%link Ad} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Ad> findByCriteria(AdCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<Ad> specification = createSpecification(criteria);
        return adRepository.findAll(specification, page);
    }

    /**
     * Function to convert AdCriteria to a {@link Specifications}
     */
    private Specifications<Ad> createSpecification(AdCriteria criteria) {
        Specifications<Ad> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Ad_.id));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getType(), Ad_.type));
            }
            if (criteria.getContent() != null) {
                specification = specification.and(buildStringSpecification(criteria.getContent(), Ad_.content));
            }
            if (criteria.getRpxWidth() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRpxWidth(), Ad_.rpxWidth));
            }
            if (criteria.getRpxHeight() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRpxHeight(), Ad_.rpxHeight));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), Ad_.status));
            }
            if (criteria.getSequence() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSequence(), Ad_.sequence));
            }
            if (criteria.getComments() != null) {
                specification = specification.and(buildStringSpecification(criteria.getComments(), Ad_.comments));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), Ad_.createdBy));
            }
        }
        return specification;
    }

}
