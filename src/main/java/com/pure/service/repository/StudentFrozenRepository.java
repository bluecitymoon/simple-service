package com.pure.service.repository;

import com.pure.service.domain.StudentFrozen;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the StudentFrozen entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StudentFrozenRepository extends JpaRepository<StudentFrozen, Long>, JpaSpecificationExecutor<StudentFrozen> {

}
