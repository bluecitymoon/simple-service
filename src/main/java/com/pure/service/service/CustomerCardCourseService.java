package com.pure.service.service;

import com.pure.service.domain.CustomerCardCourse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing CustomerCardCourse.
 */
public interface CustomerCardCourseService {

    /**
     * Save a customerCardCourse.
     *
     * @param customerCardCourse the entity to save
     * @return the persisted entity
     */
    CustomerCardCourse save(CustomerCardCourse customerCardCourse);

    /**
     *  Get all the customerCardCourses.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<CustomerCardCourse> findAll(Pageable pageable);

    /**
     *  Get the "id" customerCardCourse.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    CustomerCardCourse findOne(Long id);

    /**
     *  Delete the "id" customerCardCourse.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
