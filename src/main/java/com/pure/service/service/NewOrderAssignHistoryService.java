package com.pure.service.service;

import com.pure.service.domain.NewOrderAssignHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing NewOrderAssignHistory.
 */
public interface NewOrderAssignHistoryService {

    /**
     * Save a newOrderAssignHistory.
     *
     * @param newOrderAssignHistory the entity to save
     * @return the persisted entity
     */
    NewOrderAssignHistory save(NewOrderAssignHistory newOrderAssignHistory);

    /**
     *  Get all the newOrderAssignHistories.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<NewOrderAssignHistory> findAll(Pageable pageable);

    /**
     *  Get the "id" newOrderAssignHistory.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    NewOrderAssignHistory findOne(Long id);

    /**
     *  Delete the "id" newOrderAssignHistory.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
