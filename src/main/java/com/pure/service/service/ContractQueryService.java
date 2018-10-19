package com.pure.service.service;


import com.pure.service.domain.Contract;
import com.pure.service.domain.ContractStatus_;
import com.pure.service.domain.Contract_;
import com.pure.service.domain.Course_;
import com.pure.service.domain.CustomerCard_;
import com.pure.service.domain.Customer_;
import com.pure.service.domain.Product_;
import com.pure.service.domain.Student_;
import com.pure.service.repository.ContractRepository;
import com.pure.service.service.dto.ContractCriteria;
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
 * Service for executing complex queries for Contract entities in the database.
 * The main input is a {@link ContractCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {%link Contract} or a {@link Page} of {%link Contract} which fulfills the criterias
 */
@Service
@Transactional(readOnly = true)
public class ContractQueryService extends QueryService<Contract> {

    private final Logger log = LoggerFactory.getLogger(ContractQueryService.class);


    private final ContractRepository contractRepository;

    public ContractQueryService(ContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }

    /**
     * Return a {@link List} of {%link Contract} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Contract> findByCriteria(ContractCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<Contract> specification = createSpecification(criteria);
        return contractRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {%link Contract} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Contract> findByCriteria(ContractCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<Contract> specification = createSpecification(criteria);
        return contractRepository.findAll(specification, page);
    }

    /**
     * Function to convert ContractCriteria to a {@link Specifications}
     */
    private Specifications<Contract> createSpecification(ContractCriteria criteria) {
        Specifications<Contract> specification = Specifications.where(null);
        if (criteria != null) {

            if (criteria.getRegionId() != null) {
                specification = specification.and(buildSpecification(criteria.getRegionId(), Contract_.regionId));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Contract_.id));
            }
            if (criteria.getContractNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getContractNumber(), Contract_.contractNumber));
            }
            if (criteria.getSerialNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSerialNumber(), Contract_.serialNumber));
            }
            if (criteria.getSignDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSignDate(), Contract_.signDate));
            }
            if (criteria.getStartDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartDate(), Contract_.startDate));
            }
            if (criteria.getEndDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndDate(), Contract_.endDate));
            }
            if (criteria.getTotalMoneyAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalMoneyAmount(), Contract_.totalMoneyAmount));
            }
            if (criteria.getMoneyShouldCollected() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMoneyShouldCollected(), Contract_.moneyShouldCollected));
            }
            if (criteria.getMoneyCollected() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMoneyCollected(), Contract_.moneyCollected));
            }
            if (criteria.getPromotionAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPromotionAmount(), Contract_.promotionAmount));
            }
            if (criteria.getTotalHours() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalHours(), Contract_.totalHours));
            }
            if (criteria.getHoursTaken() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getHoursTaken(), Contract_.hoursTaken));
            }
            if (criteria.getComments() != null) {
                specification = specification.and(buildStringSpecification(criteria.getComments(), Contract_.comments));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), Contract_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), Contract_.createdDate));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), Contract_.lastModifiedBy));
            }
            if (criteria.getLastModifiedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModifiedDate(), Contract_.lastModifiedDate));
            }
            if (criteria.getStudentId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getStudentId(), Contract_.student, Student_.id));
            }
            if (criteria.getCourseId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getCourseId(), Contract_.course, Course_.id));
            }
            if (criteria.getContractStatusId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getContractStatusId(), Contract_.contractStatus, ContractStatus_.id));
            }
            if (criteria.getProductId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getProductId(), Contract_.product, Product_.id));
            }
            if (criteria.getCustomerCardId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getCustomerCardId(), Contract_.customerCard, CustomerCard_.id));
            }
            if (criteria.getCustomerId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getCustomerId(), Contract_.customer, Customer_.id));
            }
            if (!StringUtils.isEmpty(criteria.getCustomerName())) {
                specification = specification.and(customerName(criteria.getCustomerName()));
            }

            if (!StringUtils.isEmpty(criteria.getContractNumber())) {
                specification = specification.and(customerContractPhoneNumber(criteria.getCustomerContactPhoneNumber()));
            }

        }
        return specification;
    }

    private Specification customerName(String customerName) {

        return (root, query, cb) -> cb.like(root.get(Contract_.customer).get(Customer_.name), customerName);

    }

    private Specification customerContractPhoneNumber(String phoneNumber) {

        return (root, query, cb) -> cb.like(root.get(Contract_.customer).get(Customer_.contactPhoneNumber), phoneNumber);

    }
}
