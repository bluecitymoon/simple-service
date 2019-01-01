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

    @Query(nativeQuery = true, value = "select count(0) as absenceLogCount from student_absence_log where class_arrangement_id = :1")
    Integer getStudentAbsenceLogCount(Long arrangementId);

}
