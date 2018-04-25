package com.pure.service.service.impl;

import com.pure.service.service.NewOrderAssignHistoryService;
import com.pure.service.domain.NewOrderAssignHistory;
import com.pure.service.repository.NewOrderAssignHistoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing NewOrderAssignHistory.
 */
@Service
@Transactional
public class NewOrderAssignHistoryServiceImpl implements NewOrderAssignHistoryService{

    private final Logger log = LoggerFactory.getLogger(NewOrderAssignHistoryServiceImpl.class);

    private final NewOrderAssignHistoryRepository newOrderAssignHistoryRepository;

    public NewOrderAssignHistoryServiceImpl(NewOrderAssignHistoryRepository newOrderAssignHistoryRepository) {
        this.newOrderAssignHistoryRepository = newOrderAssignHistoryRepository;
    }

    /**
     * Save a newOrderAssignHistory.
     *
     * @param newOrderAssignHistory the entity to save
     * @return the persisted entity
     */
    @Override
    public NewOrderAssignHistory save(NewOrderAssignHistory newOrderAssignHistory) {
        log.debug("Request to save NewOrderAssignHistory : {}", newOrderAssignHistory);
        return newOrderAssignHistoryRepository.save(newOrderAssignHistory);
    }

    /**
     *  Get all the newOrderAssignHistories.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<NewOrderAssignHistory> findAll(Pageable pageable) {
        log.debug("Request to get all NewOrderAssignHistories");
        return newOrderAssignHistoryRepository.findAll(pageable);
    }

    /**
     *  Get one newOrderAssignHistory by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public NewOrderAssignHistory findOne(Long id) {
        log.debug("Request to get NewOrderAssignHistory : {}", id);
        return newOrderAssignHistoryRepository.findOne(id);
    }

    /**
     *  Delete the  newOrderAssignHistory by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete NewOrderAssignHistory : {}", id);
        newOrderAssignHistoryRepository.delete(id);
    }
}
