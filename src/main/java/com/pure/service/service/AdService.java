package com.pure.service.service;

import com.pure.service.domain.Ad;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Ad.
 */
public interface AdService {

    /**
     * Save a ad.
     *
     * @param ad the entity to save
     * @return the persisted entity
     */
    Ad save(Ad ad);

    /**
     *  Get all the ads.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Ad> findAll(Pageable pageable);

    /**
     *  Get the "id" ad.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Ad findOne(Long id);

    /**
     *  Delete the "id" ad.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
