package com.pure.service.service;

import com.pure.service.domain.Collection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Collection.
 */
public interface CollectionService {

    /**
     * Save a collection.
     *
     * @param collection the entity to save
     * @return the persisted entity
     */
    Collection save(Collection collection);

    /**
     *  Get all the collections.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Collection> findAll(Pageable pageable);

    /**
     *  Get the "id" collection.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Collection findOne(Long id);

    /**
     *  Delete the "id" collection.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    void confirmCustomerCollection(Collection collection);
}
