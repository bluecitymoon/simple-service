package com.pure.service.service;

import com.pure.service.domain.StudentLeave;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing StudentLeave.
 */
public interface StudentLeaveService {

    /**
     * Save a studentLeave.
     *
     * @param studentLeave the entity to save
     * @return the persisted entity
     */
    StudentLeave save(StudentLeave studentLeave);

    /**
     *  Get all the studentLeaves.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<StudentLeave> findAll(Pageable pageable);

    /**
     *  Get the "id" studentLeave.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    StudentLeave findOne(Long id);

    /**
     *  Delete the "id" studentLeave.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
