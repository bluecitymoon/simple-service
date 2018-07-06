package com.pure.service.service.impl;

import com.pure.service.service.SchoolService;
import com.pure.service.domain.School;
import com.pure.service.repository.SchoolRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing School.
 */
@Service
@Transactional
public class SchoolServiceImpl implements SchoolService{

    private final Logger log = LoggerFactory.getLogger(SchoolServiceImpl.class);

    private final SchoolRepository schoolRepository;

    public SchoolServiceImpl(SchoolRepository schoolRepository) {
        this.schoolRepository = schoolRepository;
    }

    /**
     * Save a school.
     *
     * @param school the entity to save
     * @return the persisted entity
     */
    @Override
    public School save(School school) {
        log.debug("Request to save School : {}", school);
        return schoolRepository.save(school);
    }

    /**
     *  Get all the schools.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<School> findAll(Pageable pageable) {
        log.debug("Request to get all Schools");
        return schoolRepository.findAll(pageable);
    }

    /**
     *  Get one school by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public School findOne(Long id) {
        log.debug("Request to get School : {}", id);
        return schoolRepository.findOne(id);
    }

    /**
     *  Delete the  school by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete School : {}", id);
        schoolRepository.delete(id);
    }
}
