package com.pure.service.service;

import com.pure.service.domain.CollectionStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing CollectionStatus.
 */
public interface CollectionStatusService {

    /**
     * Save a collectionStatus.
     *
     * @param collectionStatus the entity to save
     * @return the persisted entity
     */
    CollectionStatus save(CollectionStatus collectionStatus);

    /**
     *  Get all the collectionStatuses.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<CollectionStatus> findAll(Pageable pageable);

    /**
     *  Get the "id" collectionStatus.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    CollectionStatus findOne(Long id);

    /**
     *  Delete the "id" collectionStatus.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
