package com.pure.service.service;

import com.pure.service.domain.CustomerCollectionLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing CustomerCollectionLog.
 */
public interface CustomerCollectionLogService {

    /**
     * Save a customerCollectionLog.
     *
     * @param customerCollectionLog the entity to save
     * @return the persisted entity
     */
    CustomerCollectionLog save(CustomerCollectionLog customerCollectionLog);

    /**
     *  Get all the customerCollectionLogs.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<CustomerCollectionLog> findAll(Pageable pageable);

    /**
     *  Get the "id" customerCollectionLog.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    CustomerCollectionLog findOne(Long id);

    /**
     *  Delete the "id" customerCollectionLog.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    void fixRegionId();
}
