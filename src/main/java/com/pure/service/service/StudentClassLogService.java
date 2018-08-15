package com.pure.service.service;

import com.pure.service.domain.StudentClassLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing StudentClassLog.
 */
public interface StudentClassLogService {

    /**
     * Save a studentClassLog.
     *
     * @param studentClassLog the entity to save
     * @return the persisted entity
     */
    StudentClassLog save(StudentClassLog studentClassLog);

    /**
     *  Get all the studentClassLogs.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<StudentClassLog> findAll(Pageable pageable);

    /**
     *  Get the "id" studentClassLog.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    StudentClassLog findOne(Long id);

    /**
     *  Delete the "id" studentClassLog.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
