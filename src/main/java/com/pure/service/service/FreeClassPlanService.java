package com.pure.service.service;

import com.pure.service.domain.FreeClassPlan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing FreeClassPlan.
 */
public interface FreeClassPlanService {

    /**
     * Save a freeClassPlan.
     *
     * @param freeClassPlan the entity to save
     * @return the persisted entity
     */
    FreeClassPlan save(FreeClassPlan freeClassPlan);

    /**
     *  Get all the freeClassPlans.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<FreeClassPlan> findAll(Pageable pageable);

    /**
     *  Get the "id" freeClassPlan.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    FreeClassPlan findOne(Long id);

    /**
     *  Delete the "id" freeClassPlan.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
