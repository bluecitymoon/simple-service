package com.pure.service.service.impl;

import com.pure.service.service.StudentClassInOutLogService;
import com.pure.service.domain.StudentClassInOutLog;
import com.pure.service.repository.StudentClassInOutLogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing StudentClassInOutLog.
 */
@Service
@Transactional
public class StudentClassInOutLogServiceImpl implements StudentClassInOutLogService{

    private final Logger log = LoggerFactory.getLogger(StudentClassInOutLogServiceImpl.class);

    private final StudentClassInOutLogRepository studentClassInOutLogRepository;

    public StudentClassInOutLogServiceImpl(StudentClassInOutLogRepository studentClassInOutLogRepository) {
        this.studentClassInOutLogRepository = studentClassInOutLogRepository;
    }

    /**
     * Save a studentClassInOutLog.
     *
     * @param studentClassInOutLog the entity to save
     * @return the persisted entity
     */
    @Override
    public StudentClassInOutLog save(StudentClassInOutLog studentClassInOutLog) {
        log.debug("Request to save StudentClassInOutLog : {}", studentClassInOutLog);
        return studentClassInOutLogRepository.save(studentClassInOutLog);
    }

    /**
     *  Get all the studentClassInOutLogs.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<StudentClassInOutLog> findAll(Pageable pageable) {
        log.debug("Request to get all StudentClassInOutLogs");
        return studentClassInOutLogRepository.findAll(pageable);
    }

    /**
     *  Get one studentClassInOutLog by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public StudentClassInOutLog findOne(Long id) {
        log.debug("Request to get StudentClassInOutLog : {}", id);
        return studentClassInOutLogRepository.findOne(id);
    }

    /**
     *  Delete the  studentClassInOutLog by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete StudentClassInOutLog : {}", id);
        studentClassInOutLogRepository.delete(id);
    }
}
