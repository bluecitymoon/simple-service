package com.pure.service.service;

import com.pure.service.domain.ClassStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing ClassStatus.
 */
public interface ClassStatusService {

    /**
     * Save a classStatus.
     *
     * @param classStatus the entity to save
     * @return the persisted entity
     */
    ClassStatus save(ClassStatus classStatus);

    /**
     *  Get all the classStatuses.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ClassStatus> findAll(Pageable pageable);

    /**
     *  Get the "id" classStatus.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ClassStatus findOne(Long id);

    /**
     *  Delete the "id" classStatus.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
