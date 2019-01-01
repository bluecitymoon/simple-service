package com.pure.service.repository;

import com.pure.service.domain.StudentLeave;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the StudentLeave entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StudentLeaveRepository extends JpaRepository<StudentLeave, Long>, JpaSpecificationExecutor<StudentLeave> {

    @Query(nativeQuery = true, value = "select count(0) as studentLeaveCount from student_leave where class_arrangement_id = :1")
    Integer getStudentLeaveCountByArrangement(Long arrangementId);

}
