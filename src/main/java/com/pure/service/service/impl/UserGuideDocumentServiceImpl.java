package com.pure.service.service.impl;

import com.pure.service.service.UserGuideDocumentService;
import com.pure.service.domain.UserGuideDocument;
import com.pure.service.repository.UserGuideDocumentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing UserGuideDocument.
 */
@Service
@Transactional
public class UserGuideDocumentServiceImpl implements UserGuideDocumentService{

    private final Logger log = LoggerFactory.getLogger(UserGuideDocumentServiceImpl.class);

    private final UserGuideDocumentRepository userGuideDocumentRepository;

    public UserGuideDocumentServiceImpl(UserGuideDocumentRepository userGuideDocumentRepository) {
        this.userGuideDocumentRepository = userGuideDocumentRepository;
    }

    /**
     * Save a userGuideDocument.
     *
     * @param userGuideDocument the entity to save
     * @return the persisted entity
     */
    @Override
    public UserGuideDocument save(UserGuideDocument userGuideDocument) {
        log.debug("Request to save UserGuideDocument : {}", userGuideDocument);
        return userGuideDocumentRepository.save(userGuideDocument);
    }

    /**
     *  Get all the userGuideDocuments.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<UserGuideDocument> findAll(Pageable pageable) {
        log.debug("Request to get all UserGuideDocuments");
        return userGuideDocumentRepository.findAll(pageable);
    }

    /**
     *  Get one userGuideDocument by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public UserGuideDocument findOne(Long id) {
        log.debug("Request to get UserGuideDocument : {}", id);
        return userGuideDocumentRepository.findOne(id);
    }

    /**
     *  Delete the  userGuideDocument by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserGuideDocument : {}", id);
        userGuideDocumentRepository.delete(id);
    }
}
