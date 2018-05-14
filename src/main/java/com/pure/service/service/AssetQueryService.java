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

import com.pure.service.domain.Asset;
import com.pure.service.domain.*; // for static metamodels
import com.pure.service.repository.AssetRepository;
import com.pure.service.service.dto.AssetCriteria;


/**
 * Service for executing complex queries for Asset entities in the database.
 * The main input is a {@link AssetCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {%link Asset} or a {@link Page} of {%link Asset} which fulfills the criterias
 */
@Service
@Transactional(readOnly = true)
public class AssetQueryService extends QueryService<Asset> {

    private final Logger log = LoggerFactory.getLogger(AssetQueryService.class);


    private final AssetRepository assetRepository;

    public AssetQueryService(AssetRepository assetRepository) {
        this.assetRepository = assetRepository;
    }

    /**
     * Return a {@link List} of {%link Asset} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Asset> findByCriteria(AssetCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<Asset> specification = createSpecification(criteria);
        return assetRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {%link Asset} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Asset> findByCriteria(AssetCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<Asset> specification = createSpecification(criteria);
        return assetRepository.findAll(specification, page);
    }

    /**
     * Function to convert AssetCriteria to a {@link Specifications}
     */
    private Specifications<Asset> createSpecification(AssetCriteria criteria) {
        Specifications<Asset> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Asset_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Asset_.name));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getType(), Asset_.type));
            }
            if (criteria.getFullPath() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFullPath(), Asset_.fullPath));
            }
            if (criteria.getComments() != null) {
                specification = specification.and(buildStringSpecification(criteria.getComments(), Asset_.comments));
            }
            if (criteria.getResourceId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getResourceId(), Asset_.resourceId));
            }
        }
        return specification;
    }

}
