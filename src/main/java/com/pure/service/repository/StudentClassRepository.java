package com.pure.service.repository;

import com.pure.service.domain.StudentClass;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the StudentClass entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StudentClassRepository extends JpaRepository<StudentClass, Long>, JpaSpecificationExecutor<StudentClass> {

    StudentClass findByStudent_IdAndProduct_Id(Long studentId, Long classId);

    List<StudentClass> findByProduct_Id(Long classId);

}
