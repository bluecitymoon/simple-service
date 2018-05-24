package com.pure.service.service;

import com.pure.service.domain.MarketingNewOrderPlan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing MarketingNewOrderPlan.
 */
public interface MarketingNewOrderPlanService {

    /**
     * Save a marketingNewOrderPlan.
     *
     * @param marketingNewOrderPlan the entity to save
     * @return the persisted entity
     */
    MarketingNewOrderPlan save(MarketingNewOrderPlan marketingNewOrderPlan);

    List<MarketingNewOrderPlan> generateReport(List<MarketingNewOrderPlan> marketingNewOrderPlans);

    /**
     *  Get all the marketingNewOrderPlans.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<MarketingNewOrderPlan> findAll(Pageable pageable);

    /**
     *  Get the "id" marketingNewOrderPlan.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    MarketingNewOrderPlan findOne(Long id);

    /**
     *  Delete the "id" marketingNewOrderPlan.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
