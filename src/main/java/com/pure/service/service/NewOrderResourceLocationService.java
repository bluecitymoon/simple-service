package com.pure.service.service;

import com.pure.service.domain.NewOrderResourceLocation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing NewOrderResourceLocation.
 */
public interface NewOrderResourceLocationService {

    /**
     * Save a newOrderResourceLocation.
     *
     * @param newOrderResourceLocation the entity to save
     * @return the persisted entity
     */
    NewOrderResourceLocation save(NewOrderResourceLocation newOrderResourceLocation);

    /**
     *  Get all the newOrderResourceLocations.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<NewOrderResourceLocation> findAll(Pageable pageable);

    /**
     *  Get the "id" newOrderResourceLocation.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    NewOrderResourceLocation findOne(Long id);

    /**
     *  Delete the "id" newOrderResourceLocation.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
