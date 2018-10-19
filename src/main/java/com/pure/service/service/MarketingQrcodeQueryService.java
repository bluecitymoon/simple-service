package com.pure.service.service;


import com.pure.service.domain.MarketingQrcode;
import com.pure.service.domain.MarketingQrcode_;
import com.pure.service.domain.User_;
import com.pure.service.repository.MarketingQrcodeRepository;
import com.pure.service.service.dto.MarketingQrcodeCriteria;
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
 * Service for executing complex queries for MarketingQrcode entities in the database.
 * The main input is a {@link MarketingQrcodeCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {%link MarketingQrcode} or a {@link Page} of {%link MarketingQrcode} which fulfills the criterias
 */
@Service
@Transactional(readOnly = true)
public class MarketingQrcodeQueryService extends QueryService<MarketingQrcode> {

    private final Logger log = LoggerFactory.getLogger(MarketingQrcodeQueryService.class);


    private final MarketingQrcodeRepository marketingQrcodeRepository;

    public MarketingQrcodeQueryService(MarketingQrcodeRepository marketingQrcodeRepository) {
        this.marketingQrcodeRepository = marketingQrcodeRepository;
    }

    /**
     * Return a {@link List} of {%link MarketingQrcode} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MarketingQrcode> findByCriteria(MarketingQrcodeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<MarketingQrcode> specification = createSpecification(criteria);
        return marketingQrcodeRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {%link MarketingQrcode} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MarketingQrcode> findByCriteria(MarketingQrcodeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<MarketingQrcode> specification = createSpecification(criteria);
        return marketingQrcodeRepository.findAll(specification, page);
    }

    /**
     * Function to convert MarketingQrcodeCriteria to a {@link Specifications}
     */
    private Specifications<MarketingQrcode> createSpecification(MarketingQrcodeCriteria criteria) {
        Specifications<MarketingQrcode> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getRegionId() != null) {
                specification = specification.and(buildSpecification(criteria.getRegionId(), MarketingQrcode_.regionId));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), MarketingQrcode_.id));
            }
            if (criteria.getFileUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFileUrl(), MarketingQrcode_.fileUrl));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), MarketingQrcode_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), MarketingQrcode_.createdDate));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), MarketingQrcode_.lastModifiedBy));
            }
            if (criteria.getLastModifiedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModifiedDate(), MarketingQrcode_.lastModifiedDate));
            }
            if (criteria.getAgentId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getAgentId(), MarketingQrcode_.agent, User_.id));
            }
        }
        return specification;
    }

}
