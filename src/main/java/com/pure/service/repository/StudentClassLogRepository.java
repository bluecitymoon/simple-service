package com.pure.service.repository;

import com.pure.service.domain.StudentClassLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data JPA repository for the StudentClassLog entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StudentClassLogRepository extends JpaRepository<StudentClassLog, Long>, JpaSpecificationExecutor<StudentClassLog> {

    List<StudentClassLog> findByStudent_Id(Long studentId);

    @Query(nativeQuery = true, value = "select count(0) as studentClassLogCount from student_class_log where arrangement_id = :1")
    Integer getStudentClassLogCount(Long arrangementId);
}
