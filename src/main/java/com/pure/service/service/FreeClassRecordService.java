package com.pure.service.service;

import com.pure.service.domain.FreeClassRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing FreeClassRecord.
 */
public interface FreeClassRecordService {

    /**
     * Save a freeClassRecord.
     *
     * @param freeClassRecord the entity to save
     * @return the persisted entity
     */
    FreeClassRecord save(FreeClassRecord freeClassRecord);

    /**
     *  Get all the freeClassRecords.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<FreeClassRecord> findAll(Pageable pageable);

    /**
     *  Get the "id" freeClassRecord.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    FreeClassRecord findOne(Long id);

    /**
     *  Delete the "id" freeClassRecord.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
