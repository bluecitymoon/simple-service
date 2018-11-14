package com.pure.service.repository;

import com.pure.service.domain.StudentFrozenArrangement;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the StudentFrozenArrangement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StudentFrozenArrangementRepository extends JpaRepository<StudentFrozenArrangement, Long>, JpaSpecificationExecutor<StudentFrozenArrangement> {

}
