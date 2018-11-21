package com.pure.service.service;

import com.pure.service.domain.ClassArrangement;
import com.pure.service.domain.StudentFrozenArrangement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing StudentFrozenArrangement.
 */
public interface StudentFrozenArrangementService {

    /**
     * Save a studentFrozenArrangement.
     *
     * @param studentFrozenArrangement the entity to save
     * @return the persisted entity
     */
    StudentFrozenArrangement save(StudentFrozenArrangement studentFrozenArrangement);

    /**
     *  Get all the studentFrozenArrangements.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<StudentFrozenArrangement> findAll(Pageable pageable);

    /**
     *  Get the "id" studentFrozenArrangement.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    StudentFrozenArrangement findOne(Long id);

    /**
     *  Delete the "id" studentFrozenArrangement.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    List<ClassArrangement> getStudentFrozenArrangements(Long frozenId);
}
