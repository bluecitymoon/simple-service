package com.pure.service.service.impl;

import com.pure.service.service.StudentClassLogService;
import com.pure.service.domain.StudentClassLog;
import com.pure.service.repository.StudentClassLogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing StudentClassLog.
 */
@Service
@Transactional
public class StudentClassLogServiceImpl implements StudentClassLogService{

    private final Logger log = LoggerFactory.getLogger(StudentClassLogServiceImpl.class);

    private final StudentClassLogRepository studentClassLogRepository;

    public StudentClassLogServiceImpl(StudentClassLogRepository studentClassLogRepository) {
        this.studentClassLogRepository = studentClassLogRepository;
    }

    /**
     * Save a studentClassLog.
     *
     * @param studentClassLog the entity to save
     * @return the persisted entity
     */
    @Override
    public StudentClassLog save(StudentClassLog studentClassLog) {
        log.debug("Request to save StudentClassLog : {}", studentClassLog);
        return studentClassLogRepository.save(studentClassLog);
    }

    /**
     *  Get all the studentClassLogs.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<StudentClassLog> findAll(Pageable pageable) {
        log.debug("Request to get all StudentClassLogs");
        return studentClassLogRepository.findAll(pageable);
    }

    /**
     *  Get one studentClassLog by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public StudentClassLog findOne(Long id) {
        log.debug("Request to get StudentClassLog : {}", id);
        return studentClassLogRepository.findOne(id);
    }

    /**
     *  Delete the  studentClassLog by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete StudentClassLog : {}", id);
        studentClassLogRepository.delete(id);
    }
}
