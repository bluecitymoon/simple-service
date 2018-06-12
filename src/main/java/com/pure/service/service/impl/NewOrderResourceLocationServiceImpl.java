package com.pure.service.service.impl;

import com.pure.service.service.NewOrderResourceLocationService;
import com.pure.service.domain.NewOrderResourceLocation;
import com.pure.service.repository.NewOrderResourceLocationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing NewOrderResourceLocation.
 */
@Service
@Transactional
public class NewOrderResourceLocationServiceImpl implements NewOrderResourceLocationService{

    private final Logger log = LoggerFactory.getLogger(NewOrderResourceLocationServiceImpl.class);

    private final NewOrderResourceLocationRepository newOrderResourceLocationRepository;

    public NewOrderResourceLocationServiceImpl(NewOrderResourceLocationRepository newOrderResourceLocationRepository) {
        this.newOrderResourceLocationRepository = newOrderResourceLocationRepository;
    }

    /**
     * Save a newOrderResourceLocation.
     *
     * @param newOrderResourceLocation the entity to save
     * @return the persisted entity
     */
    @Override
    public NewOrderResourceLocation save(NewOrderResourceLocation newOrderResourceLocation) {
        log.debug("Request to save NewOrderResourceLocation : {}", newOrderResourceLocation);
        return newOrderResourceLocationRepository.save(newOrderResourceLocation);
    }

    /**
     *  Get all the newOrderResourceLocations.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<NewOrderResourceLocation> findAll(Pageable pageable) {
        log.debug("Request to get all NewOrderResourceLocations");
        return newOrderResourceLocationRepository.findAll(pageable);
    }

    /**
     *  Get one newOrderResourceLocation by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public NewOrderResourceLocation findOne(Long id) {
        log.debug("Request to get NewOrderResourceLocation : {}", id);
        return newOrderResourceLocationRepository.findOne(id);
    }

    /**
     *  Delete the  newOrderResourceLocation by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete NewOrderResourceLocation : {}", id);
        newOrderResourceLocationRepository.delete(id);
    }
}
