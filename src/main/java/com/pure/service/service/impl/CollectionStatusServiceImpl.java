package com.pure.service.service.impl;

import com.pure.service.service.CollectionStatusService;
import com.pure.service.domain.CollectionStatus;
import com.pure.service.repository.CollectionStatusRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing CollectionStatus.
 */
@Service
@Transactional
public class CollectionStatusServiceImpl implements CollectionStatusService{

    private final Logger log = LoggerFactory.getLogger(CollectionStatusServiceImpl.class);

    private final CollectionStatusRepository collectionStatusRepository;

    public CollectionStatusServiceImpl(CollectionStatusRepository collectionStatusRepository) {
        this.collectionStatusRepository = collectionStatusRepository;
    }

    /**
     * Save a collectionStatus.
     *
     * @param collectionStatus the entity to save
     * @return the persisted entity
     */
    @Override
    public CollectionStatus save(CollectionStatus collectionStatus) {
        log.debug("Request to save CollectionStatus : {}", collectionStatus);
        return collectionStatusRepository.save(collectionStatus);
    }

    /**
     *  Get all the collectionStatuses.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CollectionStatus> findAll(Pageable pageable) {
        log.debug("Request to get all CollectionStatuses");
        return collectionStatusRepository.findAll(pageable);
    }

    /**
     *  Get one collectionStatus by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public CollectionStatus findOne(Long id) {
        log.debug("Request to get CollectionStatus : {}", id);
        return collectionStatusRepository.findOne(id);
    }

    /**
     *  Delete the  collectionStatus by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CollectionStatus : {}", id);
        collectionStatusRepository.delete(id);
    }
}
