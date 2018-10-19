package com.pure.service.service;


import com.pure.service.domain.FreeClassRecord_;
import com.pure.service.domain.NewOrderAssignHistory;
import com.pure.service.domain.NewOrderAssignHistory_;
import com.pure.service.repository.NewOrderAssignHistoryRepository;
import com.pure.service.service.dto.NewOrderAssignHistoryCriteria;
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
 * Service for executing complex queries for NewOrderAssignHistory entities in the database.
 * The main input is a {@link NewOrderAssignHistoryCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {%link NewOrderAssignHistory} or a {@link Page} of {%link NewOrderAssignHistory} which fulfills the criterias
 */
@Service
@Transactional(readOnly = true)
public class NewOrderAssignHistoryQueryService extends QueryService<NewOrderAssignHistory> {

    private final Logger log = LoggerFactory.getLogger(NewOrderAssignHistoryQueryService.class);


    private final NewOrderAssignHistoryRepository newOrderAssignHistoryRepository;

    public NewOrderAssignHistoryQueryService(NewOrderAssignHistoryRepository newOrderAssignHistoryRepository) {
        this.newOrderAssignHistoryRepository = newOrderAssignHistoryRepository;
    }

    /**
     * Return a {@link List} of {%link NewOrderAssignHistory} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<NewOrderAssignHistory> findByCriteria(NewOrderAssignHistoryCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<NewOrderAssignHistory> specification = createSpecification(criteria);
        return newOrderAssignHistoryRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {%link NewOrderAssignHistory} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<NewOrderAssignHistory> findByCriteria(NewOrderAssignHistoryCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<NewOrderAssignHistory> specification = createSpecification(criteria);
        return newOrderAssignHistoryRepository.findAll(specification, page);
    }

    /**
     * Function to convert NewOrderAssignHistoryCriteria to a {@link Specifications}
     */
    private Specifications<NewOrderAssignHistory> createSpecification(NewOrderAssignHistoryCriteria criteria) {
        Specifications<NewOrderAssignHistory> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getRegionId() != null) {
                specification = specification.and(buildSpecification(criteria.getRegionId(), NewOrderAssignHistory_.regionId));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), NewOrderAssignHistory_.id));
            }
            if (criteria.getOlderFollowerLogin() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOlderFollowerLogin(), NewOrderAssignHistory_.olderFollowerLogin));
            }
            if (criteria.getOlderFollowerName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOlderFollowerName(), NewOrderAssignHistory_.olderFollowerName));
            }
            if (criteria.getNewFollowerLogin() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNewFollowerLogin(), NewOrderAssignHistory_.newFollowerLogin));
            }
            if (criteria.getNewFollowerName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNewFollowerName(), NewOrderAssignHistory_.newFollowerName));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), NewOrderAssignHistory_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), NewOrderAssignHistory_.createdDate));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), NewOrderAssignHistory_.lastModifiedBy));
            }
            if (criteria.getLastModifiedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModifiedDate(), NewOrderAssignHistory_.lastModifiedDate));
            }
            if (criteria.getNewOrderId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getNewOrderId(), NewOrderAssignHistory_.newOrder, FreeClassRecord_.id));
            }
        }
        return specification;
    }

}
