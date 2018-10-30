package com.pure.service.service;

import com.pure.service.domain.AuthorityUserGuideDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing AuthorityUserGuideDocument.
 */
public interface AuthorityUserGuideDocumentService {

    /**
     * Save a authorityUserGuideDocument.
     *
     * @param authorityUserGuideDocument the entity to save
     * @return the persisted entity
     */
    AuthorityUserGuideDocument save(AuthorityUserGuideDocument authorityUserGuideDocument);

    /**
     *  Get all the authorityUserGuideDocuments.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<AuthorityUserGuideDocument> findAll(Pageable pageable);

    /**
     *  Get the "id" authorityUserGuideDocument.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    AuthorityUserGuideDocument findOne(Long id);

    /**
     *  Delete the "id" authorityUserGuideDocument.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
