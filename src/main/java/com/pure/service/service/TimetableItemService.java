package com.pure.service.service;

import com.pure.service.domain.TimetableItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing TimetableItem.
 */
public interface TimetableItemService {

    /**
     * Save a timetableItem.
     *
     * @param timetableItem the entity to save
     * @return the persisted entity
     */
    TimetableItem save(TimetableItem timetableItem);

    /**
     *  Get all the timetableItems.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<TimetableItem> findAll(Pageable pageable);

    /**
     *  Get the "id" timetableItem.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    TimetableItem findOne(Long id);

    /**
     *  Delete the "id" timetableItem.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
