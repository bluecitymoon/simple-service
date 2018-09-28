package com.pure.service.service.impl;

import com.pure.service.service.TimetableItemService;
import com.pure.service.domain.TimetableItem;
import com.pure.service.repository.TimetableItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing TimetableItem.
 */
@Service
@Transactional
public class TimetableItemServiceImpl implements TimetableItemService{

    private final Logger log = LoggerFactory.getLogger(TimetableItemServiceImpl.class);

    private final TimetableItemRepository timetableItemRepository;

    public TimetableItemServiceImpl(TimetableItemRepository timetableItemRepository) {
        this.timetableItemRepository = timetableItemRepository;
    }

    /**
     * Save a timetableItem.
     *
     * @param timetableItem the entity to save
     * @return the persisted entity
     */
    @Override
    public TimetableItem save(TimetableItem timetableItem) {
        log.debug("Request to save TimetableItem : {}", timetableItem);
        return timetableItemRepository.save(timetableItem);
    }

    /**
     *  Get all the timetableItems.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TimetableItem> findAll(Pageable pageable) {
        log.debug("Request to get all TimetableItems");
        return timetableItemRepository.findAll(pageable);
    }

    /**
     *  Get one timetableItem by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public TimetableItem findOne(Long id) {
        log.debug("Request to get TimetableItem : {}", id);
        return timetableItemRepository.findOne(id);
    }

    /**
     *  Delete the  timetableItem by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TimetableItem : {}", id);
        timetableItemRepository.delete(id);
    }
}
