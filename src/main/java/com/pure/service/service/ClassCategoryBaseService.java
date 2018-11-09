package com.pure.service.service;

import com.pure.service.domain.ClassCategoryBase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing ClassCategoryBase.
 */
public interface ClassCategoryBaseService {

    /**
     * Save a classCategoryBase.
     *
     * @param classCategoryBase the entity to save
     * @return the persisted entity
     */
    ClassCategoryBase save(ClassCategoryBase classCategoryBase);

    /**
     *  Get all the classCategoryBases.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ClassCategoryBase> findAll(Pageable pageable);

    /**
     *  Get the "id" classCategoryBase.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ClassCategoryBase findOne(Long id);

    /**
     *  Delete the "id" classCategoryBase.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
