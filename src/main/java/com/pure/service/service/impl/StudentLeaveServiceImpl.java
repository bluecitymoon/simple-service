package com.pure.service.service.impl;

import com.pure.service.service.StudentLeaveService;
import com.pure.service.domain.StudentLeave;
import com.pure.service.repository.StudentLeaveRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing StudentLeave.
 */
@Service
@Transactional
public class StudentLeaveServiceImpl implements StudentLeaveService{

    private final Logger log = LoggerFactory.getLogger(StudentLeaveServiceImpl.class);

    private final StudentLeaveRepository studentLeaveRepository;

    public StudentLeaveServiceImpl(StudentLeaveRepository studentLeaveRepository) {
        this.studentLeaveRepository = studentLeaveRepository;
    }

    /**
     * Save a studentLeave.
     *
     * @param studentLeave the entity to save
     * @return the persisted entity
     */
    @Override
    public StudentLeave save(StudentLeave studentLeave) {
        log.debug("Request to save StudentLeave : {}", studentLeave);
        return studentLeaveRepository.save(studentLeave);
    }

    /**
     *  Get all the studentLeaves.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<StudentLeave> findAll(Pageable pageable) {
        log.debug("Request to get all StudentLeaves");
        return studentLeaveRepository.findAll(pageable);
    }

    /**
     *  Get one studentLeave by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public StudentLeave findOne(Long id) {
        log.debug("Request to get StudentLeave : {}", id);
        return studentLeaveRepository.findOne(id);
    }

    /**
     *  Delete the  studentLeave by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete StudentLeave : {}", id);
        studentLeaveRepository.delete(id);
    }
}
