package com.pure.service.service.impl;

import com.pure.service.service.StudentAbsenceLogService;
import com.pure.service.domain.StudentAbsenceLog;
import com.pure.service.repository.StudentAbsenceLogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing StudentAbsenceLog.
 */
@Service
@Transactional
public class StudentAbsenceLogServiceImpl implements StudentAbsenceLogService{

    private final Logger log = LoggerFactory.getLogger(StudentAbsenceLogServiceImpl.class);

    private final StudentAbsenceLogRepository studentAbsenceLogRepository;

    public StudentAbsenceLogServiceImpl(StudentAbsenceLogRepository studentAbsenceLogRepository) {
        this.studentAbsenceLogRepository = studentAbsenceLogRepository;
    }

    /**
     * Save a studentAbsenceLog.
     *
     * @param studentAbsenceLog the entity to save
     * @return the persisted entity
     */
    @Override
    public StudentAbsenceLog save(StudentAbsenceLog studentAbsenceLog) {
        log.debug("Request to save StudentAbsenceLog : {}", studentAbsenceLog);
        return studentAbsenceLogRepository.save(studentAbsenceLog);
    }

    /**
     *  Get all the studentAbsenceLogs.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<StudentAbsenceLog> findAll(Pageable pageable) {
        log.debug("Request to get all StudentAbsenceLogs");
        return studentAbsenceLogRepository.findAll(pageable);
    }

    /**
     *  Get one studentAbsenceLog by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public StudentAbsenceLog findOne(Long id) {
        log.debug("Request to get StudentAbsenceLog : {}", id);
        return studentAbsenceLogRepository.findOne(id);
    }

    /**
     *  Delete the  studentAbsenceLog by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete StudentAbsenceLog : {}", id);
        studentAbsenceLogRepository.delete(id);
    }
}
