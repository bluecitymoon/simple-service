package com.pure.service.service;

import com.pure.service.domain.UserGuideDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing UserGuideDocument.
 */
public interface UserGuideDocumentService {

    /**
     * Save a userGuideDocument.
     *
     * @param userGuideDocument the entity to save
     * @return the persisted entity
     */
    UserGuideDocument save(UserGuideDocument userGuideDocument);

    /**
     *  Get all the userGuideDocuments.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<UserGuideDocument> findAll(Pageable pageable);

    /**
     *  Get the "id" userGuideDocument.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    UserGuideDocument findOne(Long id);

    /**
     *  Delete the "id" userGuideDocument.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
