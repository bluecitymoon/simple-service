package com.pure.service.service.impl;

import com.pure.service.service.ClassArrangementService;
import com.pure.service.domain.ClassArrangement;
import com.pure.service.repository.ClassArrangementRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing ClassArrangement.
 */
@Service
@Transactional
public class ClassArrangementServiceImpl implements ClassArrangementService{

    private final Logger log = LoggerFactory.getLogger(ClassArrangementServiceImpl.class);

    private final ClassArrangementRepository classArrangementRepository;

    public ClassArrangementServiceImpl(ClassArrangementRepository classArrangementRepository) {
        this.classArrangementRepository = classArrangementRepository;
    }

    /**
     * Save a classArrangement.
     *
     * @param classArrangement the entity to save
     * @return the persisted entity
     */
    @Override
    public ClassArrangement save(ClassArrangement classArrangement) {
        log.debug("Request to save ClassArrangement : {}", classArrangement);
        return classArrangementRepository.save(classArrangement);
    }

    /**
     *  Get all the classArrangements.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ClassArrangement> findAll(Pageable pageable) {
        log.debug("Request to get all ClassArrangements");
        return classArrangementRepository.findAll(pageable);
    }

    /**
     *  Get one classArrangement by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ClassArrangement findOne(Long id) {
        log.debug("Request to get ClassArrangement : {}", id);
        return classArrangementRepository.findOne(id);
    }

    /**
     *  Delete the  classArrangement by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ClassArrangement : {}", id);
        classArrangementRepository.delete(id);
    }
}
