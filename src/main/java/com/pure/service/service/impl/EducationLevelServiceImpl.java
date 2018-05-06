package com.pure.service.service.impl;

import com.pure.service.service.EducationLevelService;
import com.pure.service.domain.EducationLevel;
import com.pure.service.repository.EducationLevelRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing EducationLevel.
 */
@Service
@Transactional
public class EducationLevelServiceImpl implements EducationLevelService{

    private final Logger log = LoggerFactory.getLogger(EducationLevelServiceImpl.class);

    private final EducationLevelRepository educationLevelRepository;

    public EducationLevelServiceImpl(EducationLevelRepository educationLevelRepository) {
        this.educationLevelRepository = educationLevelRepository;
    }

    /**
     * Save a educationLevel.
     *
     * @param educationLevel the entity to save
     * @return the persisted entity
     */
    @Override
    public EducationLevel save(EducationLevel educationLevel) {
        log.debug("Request to save EducationLevel : {}", educationLevel);
        return educationLevelRepository.save(educationLevel);
    }

    /**
     *  Get all the educationLevels.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EducationLevel> findAll(Pageable pageable) {
        log.debug("Request to get all EducationLevels");
        return educationLevelRepository.findAll(pageable);
    }

    /**
     *  Get one educationLevel by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public EducationLevel findOne(Long id) {
        log.debug("Request to get EducationLevel : {}", id);
        return educationLevelRepository.findOne(id);
    }

    /**
     *  Delete the  educationLevel by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete EducationLevel : {}", id);
        educationLevelRepository.delete(id);
    }
}
