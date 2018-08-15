package com.pure.service.repository;

import com.pure.service.domain.StudentClassLog;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the StudentClassLog entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StudentClassLogRepository extends JpaRepository<StudentClassLog, Long>, JpaSpecificationExecutor<StudentClassLog> {

}
