package com.pure.service.service;


import com.pure.service.domain.FreeClassRecord;
import com.pure.service.domain.FreeClassRecord_;
import com.pure.service.domain.MarketChannelCategory_;
import com.pure.service.domain.NewOrderResourceLocation_;
import com.pure.service.domain.User_;
import com.pure.service.repository.FreeClassRecordRepository;
import com.pure.service.service.dto.FreeClassRecordCriteria;
import io.github.jhipster.service.QueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;


/**
 * Service for executing complex queries for FreeClassRecord entities in the database.
 * The main input is a {@link FreeClassRecordCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {%link FreeClassRecord} or a {@link Page} of {%link FreeClassRecord} which fulfills the criterias
 */
@Service
@Transactional(readOnly = true)
public class FreeClassRecordQueryService extends QueryService<FreeClassRecord> {

    private final Logger log = LoggerFactory.getLogger(FreeClassRecordQueryService.class);


    private final FreeClassRecordRepository freeClassRecordRepository;

    public FreeClassRecordQueryService(FreeClassRecordRepository freeClassRecordRepository) {
        this.freeClassRecordRepository = freeClassRecordRepository;
    }

    /**
     * Return a {@link List} of {%link FreeClassRecord} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<FreeClassRecord> findByCriteria(FreeClassRecordCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<FreeClassRecord> specification = createSpecification(criteria);
        return freeClassRecordRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {%link FreeClassRecord} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<FreeClassRecord> findByCriteria(FreeClassRecordCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<FreeClassRecord> specification = createSpecification(criteria);
        return freeClassRecordRepository.findAll(specification, page);
    }

    /**
     * Function to convert FreeClassRecordCriteria to a {@link Specifications}
     */
    private Specifications<FreeClassRecord> createSpecification(FreeClassRecordCriteria criteria) {
        Specifications<FreeClassRecord> specification = Specifications.where(null);
        if (criteria != null) {

            if (criteria.getRegionId() != null) {
                specification = specification.and(buildSpecification(criteria.getRegionId(), FreeClassRecord_.regionId));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), FreeClassRecord_.id));
            }
            if (!StringUtils.isEmpty(criteria.getOuterReferer())) {
                specification = specification.and(buildStringSpecification(criteria.getOuterReferer(), FreeClassRecord_.outerReferer));
            }
            if (criteria.getPersonName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPersonName(), FreeClassRecord_.personName));
            }
            if (criteria.getContactPhoneNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getContactPhoneNumber(), FreeClassRecord_.contactPhoneNumber));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), FreeClassRecord_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), FreeClassRecord_.createdDate));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), FreeClassRecord_.lastModifiedBy));
            }
            if (criteria.getLastModifiedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModifiedDate(), FreeClassRecord_.lastModifiedDate));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatus(), FreeClassRecord_.status));
            }
            if (criteria.getBirthday() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBirthday(), FreeClassRecord_.birthday));
            }

            if (!StringUtils.isEmpty(criteria.getSalesFollowerAssignStatus())) {
                specification = specification.and(assignSalesFollowerStatus(criteria.getSalesFollowerAssignStatus()));
            }
            if (criteria.getMarketChannelCategoryId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getMarketChannelCategoryId(), FreeClassRecord_.marketChannelCategory, MarketChannelCategory_.id));
            }
            if (criteria.getSalesFollowerId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getSalesFollowerId(), FreeClassRecord_.salesFollower, User_.id));
            }
            if (criteria.getLocationId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getLocationId(), FreeClassRecord_.newOrderResourceLocation, NewOrderResourceLocation_.id));
            }
            if (criteria.getAgentId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getAgentId(), FreeClassRecord_.referer, User_.id));
            }
            if (criteria.getSourceType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSourceType(), FreeClassRecord_.sourceType));
            }
        }
        return specification;
    }

    private Specification assignSalesFollowerStatus(String status) {

        if (status.equals("assigned")) {
            return (root, query, cb) -> cb.isNotNull(root.get(FreeClassRecord_.salesFollower));
        } else if (status.equals("not_assigned")){
            return (root, query, cb) -> cb.isNull(root.get(FreeClassRecord_.salesFollower));
        }
        return null;
    }

//    private Specification assignCcStatus(String status) {
//
//        if (status.equals("assigned")) {
//            return (root, query, cb) -> cb.isNotNull(root.get(FreeClassRecord_.));
//        } else if (status.equals("not_assigned")){
//            return (root, query, cb) -> cb.isNull(root.get(FreeClassRecord_.salesFollower));
//        }
//        return null;
//    }
}
