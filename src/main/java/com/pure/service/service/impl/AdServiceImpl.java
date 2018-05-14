package com.pure.service.service.impl;

import com.pure.service.service.AdService;
import com.pure.service.domain.Ad;
import com.pure.service.repository.AdRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Ad.
 */
@Service
@Transactional
public class AdServiceImpl implements AdService{

    private final Logger log = LoggerFactory.getLogger(AdServiceImpl.class);

    private final AdRepository adRepository;

    public AdServiceImpl(AdRepository adRepository) {
        this.adRepository = adRepository;
    }

    /**
     * Save a ad.
     *
     * @param ad the entity to save
     * @return the persisted entity
     */
    @Override
    public Ad save(Ad ad) {
        log.debug("Request to save Ad : {}", ad);
        return adRepository.save(ad);
    }

    /**
     *  Get all the ads.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Ad> findAll(Pageable pageable) {
        log.debug("Request to get all Ads");
        return adRepository.findAll(pageable);
    }

    /**
     *  Get one ad by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Ad findOne(Long id) {
        log.debug("Request to get Ad : {}", id);
        return adRepository.findOne(id);
    }

    /**
     *  Delete the  ad by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Ad : {}", id);
        adRepository.delete(id);
    }
}
