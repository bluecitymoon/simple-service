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

import com.pure.service.domain.CollectionStatus;
import com.pure.service.domain.*; // for static metamodels
import com.pure.service.repository.CollectionStatusRepository;
import com.pure.service.service.dto.CollectionStatusCriteria;


/**
 * Service for executing complex queries for CollectionStatus entities in the database.
 * The main input is a {@link CollectionStatusCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {%link CollectionStatus} or a {@link Page} of {%link CollectionStatus} which fulfills the criterias
 */
@Service
@Transactional(readOnly = true)
public class CollectionStatusQueryService extends QueryService<CollectionStatus> {

    private final Logger log = LoggerFactory.getLogger(CollectionStatusQueryService.class);


    private final CollectionStatusRepository collectionStatusRepository;

    public CollectionStatusQueryService(CollectionStatusRepository collectionStatusRepository) {
        this.collectionStatusRepository = collectionStatusRepository;
    }

    /**
     * Return a {@link List} of {%link CollectionStatus} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CollectionStatus> findByCriteria(CollectionStatusCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<CollectionStatus> specification = createSpecification(criteria);
        return collectionStatusRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {%link CollectionStatus} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CollectionStatus> findByCriteria(CollectionStatusCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<CollectionStatus> specification = createSpecification(criteria);
        return collectionStatusRepository.findAll(specification, page);
    }

    /**
     * Function to convert CollectionStatusCriteria to a {@link Specifications}
     */
    private Specifications<CollectionStatus> createSpecification(CollectionStatusCriteria criteria) {
        Specifications<CollectionStatus> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), CollectionStatus_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), CollectionStatus_.name));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), CollectionStatus_.code));
            }
        }
        return specification;
    }

}
