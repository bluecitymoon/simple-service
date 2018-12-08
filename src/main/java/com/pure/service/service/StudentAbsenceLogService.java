package com.pure.service.service;

import com.pure.service.domain.StudentAbsenceLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing StudentAbsenceLog.
 */
public interface StudentAbsenceLogService {

    /**
     * Save a studentAbsenceLog.
     *
     * @param studentAbsenceLog the entity to save
     * @return the persisted entity
     */
    StudentAbsenceLog save(StudentAbsenceLog studentAbsenceLog);

    /**
     *  Get all the studentAbsenceLogs.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<StudentAbsenceLog> findAll(Pageable pageable);

    /**
     *  Get the "id" studentAbsenceLog.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    StudentAbsenceLog findOne(Long id);

    /**
     *  Delete the "id" studentAbsenceLog.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
