package com.pure.service.repository;

import com.pure.service.domain.CustomerCardCourse;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the CustomerCardCourse entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustomerCardCourseRepository extends JpaRepository<CustomerCardCourse, Long>, JpaSpecificationExecutor<CustomerCardCourse> {

}
