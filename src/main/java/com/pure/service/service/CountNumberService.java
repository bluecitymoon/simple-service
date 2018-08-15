package com.pure.service.service;

import com.pure.service.domain.CountNumber;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing CountNumber.
 */
public interface CountNumberService {

    /**
     * Save a countNumber.
     *
     * @param countNumber the entity to save
     * @return the persisted entity
     */
    CountNumber save(CountNumber countNumber);

    /**
     *  Get all the countNumbers.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<CountNumber> findAll(Pageable pageable);

    /**
     *  Get the "id" countNumber.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    CountNumber findOne(Long id);

    /**
     *  Delete the "id" countNumber.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
