package com.pure.service.service;

import com.pure.service.domain.ClassArrangementStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing ClassArrangementStatus.
 */
public interface ClassArrangementStatusService {

    /**
     * Save a classArrangementStatus.
     *
     * @param classArrangementStatus the entity to save
     * @return the persisted entity
     */
    ClassArrangementStatus save(ClassArrangementStatus classArrangementStatus);

    /**
     *  Get all the classArrangementStatuses.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ClassArrangementStatus> findAll(Pageable pageable);

    /**
     *  Get the "id" classArrangementStatus.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ClassArrangementStatus findOne(Long id);

    /**
     *  Delete the "id" classArrangementStatus.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
