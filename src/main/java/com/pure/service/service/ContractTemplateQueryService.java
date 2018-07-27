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

import com.pure.service.domain.ContractTemplate;
import com.pure.service.domain.*; // for static metamodels
import com.pure.service.repository.ContractTemplateRepository;
import com.pure.service.service.dto.ContractTemplateCriteria;


/**
 * Service for executing complex queries for ContractTemplate entities in the database.
 * The main input is a {@link ContractTemplateCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {%link ContractTemplate} or a {@link Page} of {%link ContractTemplate} which fulfills the criterias
 */
@Service
@Transactional(readOnly = true)
public class ContractTemplateQueryService extends QueryService<ContractTemplate> {

    private final Logger log = LoggerFactory.getLogger(ContractTemplateQueryService.class);


    private final ContractTemplateRepository contractTemplateRepository;

    public ContractTemplateQueryService(ContractTemplateRepository contractTemplateRepository) {
        this.contractTemplateRepository = contractTemplateRepository;
    }

    /**
     * Return a {@link List} of {%link ContractTemplate} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ContractTemplate> findByCriteria(ContractTemplateCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<ContractTemplate> specification = createSpecification(criteria);
        return contractTemplateRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {%link ContractTemplate} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ContractTemplate> findByCriteria(ContractTemplateCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<ContractTemplate> specification = createSpecification(criteria);
        return contractTemplateRepository.findAll(specification, page);
    }

    /**
     * Function to convert ContractTemplateCriteria to a {@link Specifications}
     */
    private Specifications<ContractTemplate> createSpecification(ContractTemplateCriteria criteria) {
        Specifications<ContractTemplate> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ContractTemplate_.id));
            }
            if (criteria.getTotalMoneyAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalMoneyAmount(), ContractTemplate_.totalMoneyAmount));
            }
            if (criteria.getClassCount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getClassCount(), ContractTemplate_.classCount));
            }
            if (criteria.getTotalMinutes() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalMinutes(), ContractTemplate_.totalMinutes));
            }
            if (criteria.getTotalHours() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalHours(), ContractTemplate_.totalHours));
            }
            if (criteria.getYears() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getYears(), ContractTemplate_.years));
            }
            if (criteria.getPromotionAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPromotionAmount(), ContractTemplate_.promotionAmount));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), ContractTemplate_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), ContractTemplate_.createdDate));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), ContractTemplate_.lastModifiedBy));
            }
            if (criteria.getLastModifiedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModifiedDate(), ContractTemplate_.lastModifiedDate));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), ContractTemplate_.name));
            }
            if (criteria.getCustomerCardTypeId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getCustomerCardTypeId(), ContractTemplate_.customerCardType, CustomerCardType_.id));
            }
            if (criteria.getContractNatureId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getContractNatureId(), ContractTemplate_.contractNature, ContractNature_.id));
            }
        }
        return specification;
    }

}
