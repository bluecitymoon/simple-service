package com.pure.service.service;

import com.pure.service.domain.StudentClassInOutLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing StudentClassInOutLog.
 */
public interface StudentClassInOutLogService {

    /**
     * Save a studentClassInOutLog.
     *
     * @param studentClassInOutLog the entity to save
     * @return the persisted entity
     */
    StudentClassInOutLog save(StudentClassInOutLog studentClassInOutLog);

    /**
     *  Get all the studentClassInOutLogs.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<StudentClassInOutLog> findAll(Pageable pageable);

    /**
     *  Get the "id" studentClassInOutLog.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    StudentClassInOutLog findOne(Long id);

    /**
     *  Delete the "id" studentClassInOutLog.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
