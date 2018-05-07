package com.pure.service.service;

import com.pure.service.domain.ClassArrangementRule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing ClassArrangementRule.
 */
public interface ClassArrangementRuleService {

    /**
     * Save a classArrangementRule.
     *
     * @param classArrangementRule the entity to save
     * @return the persisted entity
     */
    ClassArrangementRule save(ClassArrangementRule classArrangementRule);

    /**
     *  Get all the classArrangementRules.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ClassArrangementRule> findAll(Pageable pageable);

    /**
     *  Get the "id" classArrangementRule.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ClassArrangementRule findOne(Long id);

    /**
     *  Delete the "id" classArrangementRule.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
