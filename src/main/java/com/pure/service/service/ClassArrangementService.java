package com.pure.service.service;

import com.pure.service.domain.ClassArrangement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing ClassArrangement.
 */
public interface ClassArrangementService {

    /**
     * Save a classArrangement.
     *
     * @param classArrangement the entity to save
     * @return the persisted entity
     */
    ClassArrangement save(ClassArrangement classArrangement);

    /**
     *  Get all the classArrangements.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ClassArrangement> findAll(Pageable pageable);

    /**
     *  Get the "id" classArrangement.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ClassArrangement findOne(Long id);

    /**
     *  Delete the "id" classArrangement.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
