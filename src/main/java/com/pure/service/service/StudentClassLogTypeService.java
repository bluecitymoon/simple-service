package com.pure.service.service;

import com.pure.service.domain.StudentClassLogType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing StudentClassLogType.
 */
public interface StudentClassLogTypeService {

    /**
     * Save a studentClassLogType.
     *
     * @param studentClassLogType the entity to save
     * @return the persisted entity
     */
    StudentClassLogType save(StudentClassLogType studentClassLogType);

    /**
     *  Get all the studentClassLogTypes.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<StudentClassLogType> findAll(Pageable pageable);

    /**
     *  Get the "id" studentClassLogType.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    StudentClassLogType findOne(Long id);

    /**
     *  Delete the "id" studentClassLogType.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
