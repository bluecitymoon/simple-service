package com.pure.service.repository;

import com.pure.service.domain.StudentAbsenceLog;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the StudentAbsenceLog entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StudentAbsenceLogRepository extends JpaRepository<StudentAbsenceLog, Long>, JpaSpecificationExecutor<StudentAbsenceLog> {

}
