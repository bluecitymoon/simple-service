package com.pure.service.service;

import com.pure.service.domain.TimeSegment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing TimeSegment.
 */
public interface TimeSegmentService {

    /**
     * Save a timeSegment.
     *
     * @param timeSegment the entity to save
     * @return the persisted entity
     */
    TimeSegment save(TimeSegment timeSegment);

    /**
     *  Get all the timeSegments.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<TimeSegment> findAll(Pageable pageable);

    /**
     *  Get the "id" timeSegment.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    TimeSegment findOne(Long id);

    /**
     *  Delete the "id" timeSegment.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
