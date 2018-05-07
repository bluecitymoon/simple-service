package com.pure.service.service;

import com.pure.service.domain.ClassArrangementRuleLoopWay;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing ClassArrangementRuleLoopWay.
 */
public interface ClassArrangementRuleLoopWayService {

    /**
     * Save a classArrangementRuleLoopWay.
     *
     * @param classArrangementRuleLoopWay the entity to save
     * @return the persisted entity
     */
    ClassArrangementRuleLoopWay save(ClassArrangementRuleLoopWay classArrangementRuleLoopWay);

    /**
     *  Get all the classArrangementRuleLoopWays.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ClassArrangementRuleLoopWay> findAll(Pageable pageable);

    /**
     *  Get the "id" classArrangementRuleLoopWay.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ClassArrangementRuleLoopWay findOne(Long id);

    /**
     *  Delete the "id" classArrangementRuleLoopWay.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
