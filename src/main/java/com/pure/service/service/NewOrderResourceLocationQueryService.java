package com.pure.service.service;


import com.pure.service.domain.NewOrderResourceLocation;
import com.pure.service.domain.NewOrderResourceLocation_;
import com.pure.service.repository.NewOrderResourceLocationRepository;
import com.pure.service.service.dto.NewOrderResourceLocationCriteria;
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
 * Service for executing complex queries for NewOrderResourceLocation entities in the database.
 * The main input is a {@link NewOrderResourceLocationCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {%link NewOrderResourceLocation} or a {@link Page} of {%link NewOrderResourceLocation} which fulfills the criterias
 */
@Service
@Transactional(readOnly = true)
public class NewOrderResourceLocationQueryService extends QueryService<NewOrderResourceLocation> {

    private final Logger log = LoggerFactory.getLogger(NewOrderResourceLocationQueryService.class);


    private final NewOrderResourceLocationRepository newOrderResourceLocationRepository;

    public NewOrderResourceLocationQueryService(NewOrderResourceLocationRepository newOrderResourceLocationRepository) {
        this.newOrderResourceLocationRepository = newOrderResourceLocationRepository;
    }

    /**
     * Return a {@link List} of {%link NewOrderResourceLocation} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<NewOrderResourceLocation> findByCriteria(NewOrderResourceLocationCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<NewOrderResourceLocation> specification = createSpecification(criteria);
        return newOrderResourceLocationRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {%link NewOrderResourceLocation} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<NewOrderResourceLocation> findByCriteria(NewOrderResourceLocationCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<NewOrderResourceLocation> specification = createSpecification(criteria);
        return newOrderResourceLocationRepository.findAll(specification, page);
    }

    /**
     * Function to convert NewOrderResourceLocationCriteria to a {@link Specifications}
     */
    private Specifications<NewOrderResourceLocation> createSpecification(NewOrderResourceLocationCriteria criteria) {
        Specifications<NewOrderResourceLocation> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getRegionId() != null) {
                specification = specification.and(buildSpecification(criteria.getRegionId(), NewOrderResourceLocation_.regionId));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), NewOrderResourceLocation_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), NewOrderResourceLocation_.name));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), NewOrderResourceLocation_.code));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), NewOrderResourceLocation_.description));
            }
        }
        return specification;
    }

}
