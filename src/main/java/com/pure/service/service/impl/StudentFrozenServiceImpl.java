package com.pure.service.service.impl;

import com.pure.service.service.StudentFrozenService;
import com.pure.service.domain.StudentFrozen;
import com.pure.service.repository.StudentFrozenRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing StudentFrozen.
 */
@Service
@Transactional
public class StudentFrozenServiceImpl implements StudentFrozenService{

    private final Logger log = LoggerFactory.getLogger(StudentFrozenServiceImpl.class);

    private final StudentFrozenRepository studentFrozenRepository;

    public StudentFrozenServiceImpl(StudentFrozenRepository studentFrozenRepository) {
        this.studentFrozenRepository = studentFrozenRepository;
    }

    /**
     * Save a studentFrozen.
     *
     * @param studentFrozen the entity to save
     * @return the persisted entity
     */
    @Override
    public StudentFrozen save(StudentFrozen studentFrozen) {
        log.debug("Request to save StudentFrozen : {}", studentFrozen);
        return studentFrozenRepository.save(studentFrozen);
    }

    /**
     *  Get all the studentFrozens.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<StudentFrozen> findAll(Pageable pageable) {
        log.debug("Request to get all StudentFrozens");
        return studentFrozenRepository.findAll(pageable);
    }

    /**
     *  Get one studentFrozen by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public StudentFrozen findOne(Long id) {
        log.debug("Request to get StudentFrozen : {}", id);
        return studentFrozenRepository.findOne(id);
    }

    /**
     *  Delete the  studentFrozen by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete StudentFrozen : {}", id);
        studentFrozenRepository.delete(id);
    }
}
