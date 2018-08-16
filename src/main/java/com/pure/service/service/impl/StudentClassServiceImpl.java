package com.pure.service.service.impl;

import com.pure.service.service.StudentClassService;
import com.pure.service.domain.StudentClass;
import com.pure.service.repository.StudentClassRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing StudentClass.
 */
@Service
@Transactional
public class StudentClassServiceImpl implements StudentClassService{

    private final Logger log = LoggerFactory.getLogger(StudentClassServiceImpl.class);

    private final StudentClassRepository studentClassRepository;

    public StudentClassServiceImpl(StudentClassRepository studentClassRepository) {
        this.studentClassRepository = studentClassRepository;
    }

    /**
     * Save a studentClass.
     *
     * @param studentClass the entity to save
     * @return the persisted entity
     */
    @Override
    public StudentClass save(StudentClass studentClass) {
        log.debug("Request to save StudentClass : {}", studentClass);
        return studentClassRepository.save(studentClass);
    }

    /**
     *  Get all the studentClasses.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<StudentClass> findAll(Pageable pageable) {
        log.debug("Request to get all StudentClasses");
        return studentClassRepository.findAll(pageable);
    }

    /**
     *  Get one studentClass by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public StudentClass findOne(Long id) {
        log.debug("Request to get StudentClass : {}", id);
        return studentClassRepository.findOne(id);
    }

    /**
     *  Delete the  studentClass by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete StudentClass : {}", id);
        studentClassRepository.delete(id);
    }
}
