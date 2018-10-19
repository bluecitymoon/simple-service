package com.pure.service.service;


import com.pure.service.domain.CustomerTrackTask;
import com.pure.service.domain.CustomerTrackTask_;
import com.pure.service.domain.Customer_;
import com.pure.service.domain.Task_;
import com.pure.service.repository.CustomerTrackTaskRepository;
import com.pure.service.service.dto.CustomerTrackTaskCriteria;
import com.pure.service.service.util.DateUtil;
import io.github.jhipster.service.QueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;


/**
 * Service for executing complex queries for CustomerTrackTask entities in the database.
 * The main input is a {@link CustomerTrackTaskCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {%link CustomerTrackTask} or a {@link Page} of {%link CustomerTrackTask} which fulfills the criterias
 */
@Service
@Transactional(readOnly = true)
public class CustomerTrackTaskQueryService extends QueryService<CustomerTrackTask> {

    private final Logger log = LoggerFactory.getLogger(CustomerTrackTaskQueryService.class);


    private final CustomerTrackTaskRepository customerTrackTaskRepository;

    public CustomerTrackTaskQueryService(CustomerTrackTaskRepository customerTrackTaskRepository) {
        this.customerTrackTaskRepository = customerTrackTaskRepository;
    }

    /**
     * Return a {@link List} of {%link CustomerTrackTask} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CustomerTrackTask> findByCriteria(CustomerTrackTaskCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<CustomerTrackTask> specification = createSpecification(criteria);
        return customerTrackTaskRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {%link CustomerTrackTask} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CustomerTrackTask> findByCriteria(CustomerTrackTaskCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<CustomerTrackTask> specification = createSpecification(criteria);
        return customerTrackTaskRepository.findAll(specification, page);
    }

    /**
     * Function to convert CustomerTrackTaskCriteria to a {@link Specifications}
     */
    private Specifications<CustomerTrackTask> createSpecification(CustomerTrackTaskCriteria criteria) {
        Specifications<CustomerTrackTask> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getRegionId() != null) {
                specification = specification.and(buildSpecification(criteria.getRegionId(), CustomerTrackTask_.regionId));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), CustomerTrackTask_.id));
            }
            if (criteria.getCustomerId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getCustomerId(), CustomerTrackTask_.customer, Customer_.id));
            }
            if (criteria.getTaskId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getTaskId(), CustomerTrackTask_.task, Task_.id));
            }

            if (criteria.getSalesFollowerId() != null) {
                specification = specification.and(assignSalesFollower(criteria.getSalesFollowerId()));
            }

            if (criteria.isToday()) {
                specification = specification.and(taskEstimateExecutionDateToday());
            }
        }
        return specification;
    }

    private Specification assignSalesFollower(Long userId) {

        return (root, query, cb) -> cb.equal(root.get(CustomerTrackTask_.customer).get(Customer_.salesFollower), userId);
    }

    private Specification taskEstimateExecutionDateToday() {

        Instant start = DateUtil.getSimpleTodayInstantBegin();
        Instant end = DateUtil.getSimpleTodayInstantEnd();

        return (root, query, cb) -> cb.between(root.get(CustomerTrackTask_.task).get(Task_.estimateExecuteDate), start, end);
    }
}
