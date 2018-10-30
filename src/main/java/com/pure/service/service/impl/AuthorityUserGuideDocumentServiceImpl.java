package com.pure.service.service.impl;

import com.pure.service.service.AuthorityUserGuideDocumentService;
import com.pure.service.domain.AuthorityUserGuideDocument;
import com.pure.service.repository.AuthorityUserGuideDocumentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing AuthorityUserGuideDocument.
 */
@Service
@Transactional
public class AuthorityUserGuideDocumentServiceImpl implements AuthorityUserGuideDocumentService{

    private final Logger log = LoggerFactory.getLogger(AuthorityUserGuideDocumentServiceImpl.class);

    private final AuthorityUserGuideDocumentRepository authorityUserGuideDocumentRepository;

    public AuthorityUserGuideDocumentServiceImpl(AuthorityUserGuideDocumentRepository authorityUserGuideDocumentRepository) {
        this.authorityUserGuideDocumentRepository = authorityUserGuideDocumentRepository;
    }

    /**
     * Save a authorityUserGuideDocument.
     *
     * @param authorityUserGuideDocument the entity to save
     * @return the persisted entity
     */
    @Override
    public AuthorityUserGuideDocument save(AuthorityUserGuideDocument authorityUserGuideDocument) {
        log.debug("Request to save AuthorityUserGuideDocument : {}", authorityUserGuideDocument);
        return authorityUserGuideDocumentRepository.save(authorityUserGuideDocument);
    }

    /**
     *  Get all the authorityUserGuideDocuments.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AuthorityUserGuideDocument> findAll(Pageable pageable) {
        log.debug("Request to get all AuthorityUserGuideDocuments");
        return authorityUserGuideDocumentRepository.findAll(pageable);
    }

    /**
     *  Get one authorityUserGuideDocument by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public AuthorityUserGuideDocument findOne(Long id) {
        log.debug("Request to get AuthorityUserGuideDocument : {}", id);
        return authorityUserGuideDocumentRepository.findOne(id);
    }

    /**
     *  Delete the  authorityUserGuideDocument by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AuthorityUserGuideDocument : {}", id);
        authorityUserGuideDocumentRepository.delete(id);
    }
}
