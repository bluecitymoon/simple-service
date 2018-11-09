package com.pure.service.service.impl;

import com.pure.service.service.ClassCategoryBaseService;
import com.pure.service.domain.ClassCategoryBase;
import com.pure.service.repository.ClassCategoryBaseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing ClassCategoryBase.
 */
@Service
@Transactional
public class ClassCategoryBaseServiceImpl implements ClassCategoryBaseService{

    private final Logger log = LoggerFactory.getLogger(ClassCategoryBaseServiceImpl.class);

    private final ClassCategoryBaseRepository classCategoryBaseRepository;

    public ClassCategoryBaseServiceImpl(ClassCategoryBaseRepository classCategoryBaseRepository) {
        this.classCategoryBaseRepository = classCategoryBaseRepository;
    }

    /**
     * Save a classCategoryBase.
     *
     * @param classCategoryBase the entity to save
     * @return the persisted entity
     */
    @Override
    public ClassCategoryBase save(ClassCategoryBase classCategoryBase) {
        log.debug("Request to save ClassCategoryBase : {}", classCategoryBase);
        return classCategoryBaseRepository.save(classCategoryBase);
    }

    /**
     *  Get all the classCategoryBases.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ClassCategoryBase> findAll(Pageable pageable) {
        log.debug("Request to get all ClassCategoryBases");
        return classCategoryBaseRepository.findAll(pageable);
    }

    /**
     *  Get one classCategoryBase by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ClassCategoryBase findOne(Long id) {
        log.debug("Request to get ClassCategoryBase : {}", id);
        return classCategoryBaseRepository.findOne(id);
    }

    /**
     *  Delete the  classCategoryBase by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ClassCategoryBase : {}", id);
        classCategoryBaseRepository.delete(id);
    }
}
