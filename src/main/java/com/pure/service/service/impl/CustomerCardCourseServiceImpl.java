package com.pure.service.service.impl;

import com.pure.service.service.CustomerCardCourseService;
import com.pure.service.domain.CustomerCardCourse;
import com.pure.service.repository.CustomerCardCourseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing CustomerCardCourse.
 */
@Service
@Transactional
public class CustomerCardCourseServiceImpl implements CustomerCardCourseService{

    private final Logger log = LoggerFactory.getLogger(CustomerCardCourseServiceImpl.class);

    private final CustomerCardCourseRepository customerCardCourseRepository;

    public CustomerCardCourseServiceImpl(CustomerCardCourseRepository customerCardCourseRepository) {
        this.customerCardCourseRepository = customerCardCourseRepository;
    }

    /**
     * Save a customerCardCourse.
     *
     * @param customerCardCourse the entity to save
     * @return the persisted entity
     */
    @Override
    public CustomerCardCourse save(CustomerCardCourse customerCardCourse) {
        log.debug("Request to save CustomerCardCourse : {}", customerCardCourse);
        return customerCardCourseRepository.save(customerCardCourse);
    }

    /**
     *  Get all the customerCardCourses.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CustomerCardCourse> findAll(Pageable pageable) {
        log.debug("Request to get all CustomerCardCourses");
        return customerCardCourseRepository.findAll(pageable);
    }

    /**
     *  Get one customerCardCourse by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public CustomerCardCourse findOne(Long id) {
        log.debug("Request to get CustomerCardCourse : {}", id);
        return customerCardCourseRepository.findOne(id);
    }

    /**
     *  Delete the  customerCardCourse by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CustomerCardCourse : {}", id);
        customerCardCourseRepository.delete(id);
    }
}
