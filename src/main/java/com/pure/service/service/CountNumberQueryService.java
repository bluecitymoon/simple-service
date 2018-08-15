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

import com.pure.service.domain.CountNumber;
import com.pure.service.domain.*; // for static metamodels
import com.pure.service.repository.CountNumberRepository;
import com.pure.service.service.dto.CountNumberCriteria;


/**
 * Service for executing complex queries for CountNumber entities in the database.
 * The main input is a {@link CountNumberCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {%link CountNumber} or a {@link Page} of {%link CountNumber} which fulfills the criterias
 */
@Service
@Transactional(readOnly = true)
public class CountNumberQueryService extends QueryService<CountNumber> {

    private final Logger log = LoggerFactory.getLogger(CountNumberQueryService.class);


    private final CountNumberRepository countNumberRepository;

    public CountNumberQueryService(CountNumberRepository countNumberRepository) {
        this.countNumberRepository = countNumberRepository;
    }

    /**
     * Return a {@link List} of {%link CountNumber} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CountNumber> findByCriteria(CountNumberCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<CountNumber> specification = createSpecification(criteria);
        return countNumberRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {%link CountNumber} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CountNumber> findByCriteria(CountNumberCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<CountNumber> specification = createSpecification(criteria);
        return countNumberRepository.findAll(specification, page);
    }

    /**
     * Function to convert CountNumberCriteria to a {@link Specifications}
     */
    private Specifications<CountNumber> createSpecification(CountNumberCriteria criteria) {
        Specifications<CountNumber> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), CountNumber_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), CountNumber_.name));
            }
            if (criteria.getValue() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValue(), CountNumber_.value));
            }
            if (criteria.getLoopWayId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getLoopWayId(), CountNumber_.loopWay, ClassArrangementRuleLoopWay_.id));
            }
        }
        return specification;
    }

}
