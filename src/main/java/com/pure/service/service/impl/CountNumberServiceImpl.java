package com.pure.service.service.impl;

import com.pure.service.service.CountNumberService;
import com.pure.service.domain.CountNumber;
import com.pure.service.repository.CountNumberRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing CountNumber.
 */
@Service
@Transactional
public class CountNumberServiceImpl implements CountNumberService{

    private final Logger log = LoggerFactory.getLogger(CountNumberServiceImpl.class);

    private final CountNumberRepository countNumberRepository;

    public CountNumberServiceImpl(CountNumberRepository countNumberRepository) {
        this.countNumberRepository = countNumberRepository;
    }

    /**
     * Save a countNumber.
     *
     * @param countNumber the entity to save
     * @return the persisted entity
     */
    @Override
    public CountNumber save(CountNumber countNumber) {
        log.debug("Request to save CountNumber : {}", countNumber);
        return countNumberRepository.save(countNumber);
    }

    /**
     *  Get all the countNumbers.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CountNumber> findAll(Pageable pageable) {
        log.debug("Request to get all CountNumbers");
        return countNumberRepository.findAll(pageable);
    }

    /**
     *  Get one countNumber by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public CountNumber findOne(Long id) {
        log.debug("Request to get CountNumber : {}", id);
        return countNumberRepository.findOne(id);
    }

    /**
     *  Delete the  countNumber by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CountNumber : {}", id);
        countNumberRepository.delete(id);
    }
}
