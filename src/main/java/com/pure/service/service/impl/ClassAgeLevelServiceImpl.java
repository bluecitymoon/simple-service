package com.pure.service.service.impl;

import com.pure.service.service.ClassAgeLevelService;
import com.pure.service.domain.ClassAgeLevel;
import com.pure.service.repository.ClassAgeLevelRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing ClassAgeLevel.
 */
@Service
@Transactional
public class ClassAgeLevelServiceImpl implements ClassAgeLevelService{

    private final Logger log = LoggerFactory.getLogger(ClassAgeLevelServiceImpl.class);

    private final ClassAgeLevelRepository classAgeLevelRepository;

    public ClassAgeLevelServiceImpl(ClassAgeLevelRepository classAgeLevelRepository) {
        this.classAgeLevelRepository = classAgeLevelRepository;
    }

    /**
     * Save a classAgeLevel.
     *
     * @param classAgeLevel the entity to save
     * @return the persisted entity
     */
    @Override
    public ClassAgeLevel save(ClassAgeLevel classAgeLevel) {
        log.debug("Request to save ClassAgeLevel : {}", classAgeLevel);
        return classAgeLevelRepository.save(classAgeLevel);
    }

    /**
     *  Get all the classAgeLevels.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ClassAgeLevel> findAll(Pageable pageable) {
        log.debug("Request to get all ClassAgeLevels");
        return classAgeLevelRepository.findAll(pageable);
    }

    /**
     *  Get one classAgeLevel by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ClassAgeLevel findOne(Long id) {
        log.debug("Request to get ClassAgeLevel : {}", id);
        return classAgeLevelRepository.findOne(id);
    }

    /**
     *  Delete the  classAgeLevel by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ClassAgeLevel : {}", id);
        classAgeLevelRepository.delete(id);
    }
}
