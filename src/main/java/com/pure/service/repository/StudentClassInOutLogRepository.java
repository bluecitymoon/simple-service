package com.pure.service.repository;

import com.pure.service.domain.StudentClassInOutLog;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the StudentClassInOutLog entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StudentClassInOutLogRepository extends JpaRepository<StudentClassInOutLog, Long>, JpaSpecificationExecutor<StudentClassInOutLog> {

}
