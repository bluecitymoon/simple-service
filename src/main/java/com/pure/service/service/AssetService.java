package com.pure.service.service;

import com.pure.service.domain.Asset;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Asset.
 */
public interface AssetService {

    /**
     * Save a asset.
     *
     * @param asset the entity to save
     * @return the persisted entity
     */
    Asset save(Asset asset);

    /**
     *  Get all the assets.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Asset> findAll(Pageable pageable);

    /**
     *  Get the "id" asset.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Asset findOne(Long id);

    /**
     *  Delete the "id" asset.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
