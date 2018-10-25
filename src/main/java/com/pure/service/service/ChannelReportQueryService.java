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

import com.pure.service.domain.ChannelReport;
import com.pure.service.domain.*; // for static metamodels
import com.pure.service.repository.ChannelReportRepository;
import com.pure.service.service.dto.ChannelReportCriteria;


/**
 * Service for executing complex queries for ChannelReport entities in the database.
 * The main input is a {@link ChannelReportCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {%link ChannelReport} or a {@link Page} of {%link ChannelReport} which fulfills the criterias
 */
@Service
@Transactional(readOnly = true)
public class ChannelReportQueryService extends QueryService<ChannelReport> {

    private final Logger log = LoggerFactory.getLogger(ChannelReportQueryService.class);


    private final ChannelReportRepository channelReportRepository;

    public ChannelReportQueryService(ChannelReportRepository channelReportRepository) {
        this.channelReportRepository = channelReportRepository;
    }

    /**
     * Return a {@link List} of {%link ChannelReport} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ChannelReport> findByCriteria(ChannelReportCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<ChannelReport> specification = createSpecification(criteria);
        return channelReportRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {%link ChannelReport} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ChannelReport> findByCriteria(ChannelReportCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<ChannelReport> specification = createSpecification(criteria);
        return channelReportRepository.findAll(specification, page);
    }

    /**
     * Function to convert ChannelReportCriteria to a {@link Specifications}
     */
    private Specifications<ChannelReport> createSpecification(ChannelReportCriteria criteria) {
        Specifications<ChannelReport> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ChannelReport_.id));
            }
            if (criteria.getMonth() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMonth(), ChannelReport_.month));
            }
            if (criteria.getYear() != null) {
                specification = specification.and(buildStringSpecification(criteria.getYear(), ChannelReport_.year));
            }
            if (criteria.getChannelId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getChannelId(), ChannelReport_.channelId));
            }
            if (criteria.getChannelName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getChannelName(), ChannelReport_.channelName));
            }
            if (criteria.getVisitedCustomerCount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVisitedCustomerCount(), ChannelReport_.visitedCustomerCount));
            }
            if (criteria.getCardCount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCardCount(), ChannelReport_.cardCount));
            }
            if (criteria.getContractCount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getContractCount(), ChannelReport_.contractCount));
            }
            if (criteria.getMoneyCollected() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMoneyCollected(), ChannelReport_.moneyCollected));
            }
            if (criteria.getContractMadeRate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getContractMadeRate(), ChannelReport_.contractMadeRate));
            }
            if (criteria.getRegionId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRegionId(), ChannelReport_.regionId));
            }
        }
        return specification;
    }

}
