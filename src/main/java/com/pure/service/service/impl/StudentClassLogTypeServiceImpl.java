package com.pure.service.service.impl;

import com.pure.service.service.StudentClassLogTypeService;
import com.pure.service.domain.StudentClassLogType;
import com.pure.service.repository.StudentClassLogTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing StudentClassLogType.
 */
@Service
@Transactional
public class StudentClassLogTypeServiceImpl implements StudentClassLogTypeService{

    private final Logger log = LoggerFactory.getLogger(StudentClassLogTypeServiceImpl.class);

    private final StudentClassLogTypeRepository studentClassLogTypeRepository;

    public StudentClassLogTypeServiceImpl(StudentClassLogTypeRepository studentClassLogTypeRepository) {
        this.studentClassLogTypeRepository = studentClassLogTypeRepository;
    }

    /**
     * Save a studentClassLogType.
     *
     * @param studentClassLogType the entity to save
     * @return the persisted entity
     */
    @Override
    public StudentClassLogType save(StudentClassLogType studentClassLogType) {
        log.debug("Request to save StudentClassLogType : {}", studentClassLogType);
        return studentClassLogTypeRepository.save(studentClassLogType);
    }

    /**
     *  Get all the studentClassLogTypes.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<StudentClassLogType> findAll(Pageable pageable) {
        log.debug("Request to get all StudentClassLogTypes");
        return studentClassLogTypeRepository.findAll(pageable);
    }

    /**
     *  Get one studentClassLogType by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public StudentClassLogType findOne(Long id) {
        log.debug("Request to get StudentClassLogType : {}", id);
        return studentClassLogTypeRepository.findOne(id);
    }

    /**
     *  Delete the  studentClassLogType by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete StudentClassLogType : {}", id);
        studentClassLogTypeRepository.delete(id);
    }
}
