package com.pure.service.service;

import com.pure.service.domain.ClassAgeLevel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing ClassAgeLevel.
 */
public interface ClassAgeLevelService {

    /**
     * Save a classAgeLevel.
     *
     * @param classAgeLevel the entity to save
     * @return the persisted entity
     */
    ClassAgeLevel save(ClassAgeLevel classAgeLevel);

    /**
     *  Get all the classAgeLevels.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ClassAgeLevel> findAll(Pageable pageable);

    /**
     *  Get the "id" classAgeLevel.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ClassAgeLevel findOne(Long id);

    /**
     *  Delete the "id" classAgeLevel.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
