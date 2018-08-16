package com.pure.service.service;

import com.pure.service.domain.StudentClass;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing StudentClass.
 */
public interface StudentClassService {

    /**
     * Save a studentClass.
     *
     * @param studentClass the entity to save
     * @return the persisted entity
     */
    StudentClass save(StudentClass studentClass);

    /**
     *  Get all the studentClasses.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<StudentClass> findAll(Pageable pageable);

    /**
     *  Get the "id" studentClass.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    StudentClass findOne(Long id);

    /**
     *  Delete the "id" studentClass.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
