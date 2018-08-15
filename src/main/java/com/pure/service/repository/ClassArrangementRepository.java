package com.pure.service.repository;

import com.pure.service.domain.ClassArrangement;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ClassArrangement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClassArrangementRepository extends JpaRepository<ClassArrangement, Long>, JpaSpecificationExecutor<ClassArrangement> {

}
