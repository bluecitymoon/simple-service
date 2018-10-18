package com.pure.service.service;


import com.pure.service.domain.Customer;
import com.pure.service.domain.CustomerStatus_;
import com.pure.service.domain.Customer_;
import com.pure.service.domain.FreeClassRecord_;
import com.pure.service.domain.MarketChannelCategory_;
import com.pure.service.domain.NewOrderResourceLocation_;
import com.pure.service.domain.User_;
import com.pure.service.domain.VistedCustomerStatus_;
import com.pure.service.repository.CustomerRepository;
import com.pure.service.service.dto.CustomerCriteria;
import io.github.jhipster.service.QueryService;
import io.github.jhipster.service.filter.InstantFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.util.List;


/**
 * Service for executing complex queries for Customer entities in the database.
 * The main input is a {@link CustomerCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {%link Customer} or a {@link Page} of {%link Customer} which fulfills the criterias
 */
@Service
@Transactional(readOnly = true)
public class CustomerQueryService extends QueryService<Customer> {

    private final Logger log = LoggerFactory.getLogger(CustomerQueryService.class);


    private final CustomerRepository customerRepository;

    public CustomerQueryService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    /**
     * Return a {@link List} of {%link Customer} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Customer> findByCriteria(CustomerCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<Customer> specification = createSpecification(criteria);
        return customerRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {%link Customer} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Customer> findByCriteria(CustomerCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<Customer> specification = createSpecification(criteria);
        return customerRepository.findAll(specification, page);
    }

    /**
     * Function to convert CustomerCriteria to a {@link Specifications}
     */
    private Specifications<Customer> createSpecification(CustomerCriteria criteria) {
        Specifications<Customer> specification = Specifications.where(null);
        if (criteria != null) {

            if (criteria.getRegionId() != null) {
                specification = specification.and(buildSpecification(criteria.getRegionId(), Customer_.regionId));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Customer_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Customer_.name));
            }
            if (criteria.getAge() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAge(), Customer_.age));
            }
            if (criteria.getContactPhoneNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getContactPhoneNumber(), Customer_.contactPhoneNumber));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), Customer_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), Customer_.createdDate));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), Customer_.lastModifiedBy));
            }
            if (criteria.getLastModifiedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModifiedDate(), Customer_.lastModifiedDate));
            }
            if (criteria.getSex() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSex(), Customer_.sex));
            }
            if (criteria.getBirthday() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBirthday(), Customer_.birthday));
            }
            if (criteria.getAddress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAddress(), Customer_.address));
            }
            if (criteria.getHoby() != null) {
                specification = specification.and(buildStringSpecification(criteria.getHoby(), Customer_.hoby));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), Customer_.email));
            }
            if (criteria.getClassLevel() != null) {
                specification = specification.and(buildStringSpecification(criteria.getClassLevel(), Customer_.classLevel));
            }
            if (criteria.getParentName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getParentName(), Customer_.parentName));
            }
            if (criteria.getParentContractNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getParentContractNumber(), Customer_.parentContractNumber));
            }
            if (criteria.getTrackStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTrackStatus(), Customer_.trackStatus));
            }
            if (criteria.getNextTrackDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNextTrackDate(), Customer_.nextTrackDate));
            }

            if (criteria.getAssignDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAssignDate(), Customer_.assignDate));
            }
            String department = criteria.getDepartment();
            if (!StringUtils.isEmpty(department) && department.equals("operation")) {

                InstantFilter visitDate = new InstantFilter();
                visitDate.setGreaterThan(Instant.EPOCH);

                specification = specification.and(buildRangeSpecification(visitDate, Customer_.visitDate));
            }

            if (!StringUtils.isEmpty(criteria.getCcAssignStatus())) {
                specification = specification.and(assignCcStatus(criteria.getCcAssignStatus()));
            }
            if (criteria.getNewOrderId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getNewOrderId(), Customer_.newOrder, FreeClassRecord_.id));
            }
            if (criteria.getStatusId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getStatusId(), Customer_.status, CustomerStatus_.id));
            }
            if (criteria.getVistedCustomerStatusId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getVistedCustomerStatusId(), Customer_.vistedCustomerStatus, VistedCustomerStatus_.id));
            }
            if (criteria.getChannelId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getChannelId(), Customer_.channel, MarketChannelCategory_.id));
            }

            if (criteria.getSalesFollowerId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getSalesFollowerId(), Customer_.salesFollower, User_.id));
            }

            if (criteria.getCourseConsultantId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getCourseConsultantId(), Customer_.courseConsultant, User_.id));
            }
            if (criteria.getLocationId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getLocationId(), Customer_.newOrderResourceLocation, NewOrderResourceLocation_.id));
            }

            if (!StringUtils.isEmpty(criteria.getSourceType())) {
                specification = specification.and(sourceType(criteria.getSourceType()));
            }
        }
        return specification;
    }

    private Specification assignCcStatus(String status) {

        if (status.equals("assigned")) {
            return (root, query, cb) -> cb.isNotNull(root.get(Customer_.courseConsultant));
        } else if (status.equals("not_assigned")){
            return (root, query, cb) -> cb.isNull(root.get(Customer_.courseConsultant));
        }
        return null;
    }

    private Specification sourceType(String sourceType) {

        return (root, query, cb) -> cb.equal(root.get(Customer_.newOrder).get(FreeClassRecord_.sourceType), sourceType);
    }
}
