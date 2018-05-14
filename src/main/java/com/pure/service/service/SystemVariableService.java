package com.pure.service.service;

import com.pure.service.domain.SystemVariable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing SystemVariable.
 */
public interface SystemVariableService {

    /**
     * Save a systemVariable.
     *
     * @param systemVariable the entity to save
     * @return the persisted entity
     */
    SystemVariable save(SystemVariable systemVariable);

    /**
     *  Get all the systemVariables.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<SystemVariable> findAll(Pageable pageable);

    /**
     *  Get the "id" systemVariable.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    SystemVariable findOne(Long id);

    /**
     *  Delete the "id" systemVariable.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
