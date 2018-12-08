package com.pure.service.repository;

import com.pure.service.domain.StudentClassLogType;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the StudentClassLogType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StudentClassLogTypeRepository extends JpaRepository<StudentClassLogType, Long>, JpaSpecificationExecutor<StudentClassLogType> {

    StudentClassLogType findByCode(String code);
}
