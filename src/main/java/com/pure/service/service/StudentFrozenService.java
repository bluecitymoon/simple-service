package com.pure.service.service;

import com.pure.service.domain.StudentFrozen;
import com.pure.service.service.dto.request.StudentFrozenRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing StudentFrozen.
 */
public interface StudentFrozenService {

    /**
     * Save a studentFrozen.
     *
     * @param studentFrozen the entity to save
     * @return the persisted entity
     */
    StudentFrozen save(StudentFrozen studentFrozen);

    /**
     *  Get all the studentFrozens.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<StudentFrozen> findAll(Pageable pageable);

    /**
     *  Get the "id" studentFrozen.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    StudentFrozen findOne(Long id);

    /**
     *  Delete the "id" studentFrozen.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    StudentFrozen generateStudentFrozen(StudentFrozenRequest studentFrozen);
}
