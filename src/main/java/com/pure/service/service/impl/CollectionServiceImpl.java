package com.pure.service.service.impl;

import com.pure.service.service.CollectionService;
import com.pure.service.domain.Collection;
import com.pure.service.repository.CollectionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Collection.
 */
@Service
@Transactional
public class CollectionServiceImpl implements CollectionService{

    private final Logger log = LoggerFactory.getLogger(CollectionServiceImpl.class);

    private final CollectionRepository collectionRepository;

    public CollectionServiceImpl(CollectionRepository collectionRepository) {
        this.collectionRepository = collectionRepository;
    }

    /**
     * Save a collection.
     *
     * @param collection the entity to save
     * @return the persisted entity
     */
    @Override
    public Collection save(Collection collection) {
        log.debug("Request to save Collection : {}", collection);
        return collectionRepository.save(collection);
    }

    /**
     *  Get all the collections.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Collection> findAll(Pageable pageable) {
        log.debug("Request to get all Collections");
        return collectionRepository.findAll(pageable);
    }

    /**
     *  Get one collection by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Collection findOne(Long id) {
        log.debug("Request to get Collection : {}", id);
        return collectionRepository.findOne(id);
    }

    /**
     *  Delete the  collection by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Collection : {}", id);
        collectionRepository.delete(id);
    }
}
