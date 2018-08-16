package com.pure.service.repository;

import com.pure.service.domain.ClassArrangementStatus;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ClassArrangementStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClassArrangementStatusRepository extends JpaRepository<ClassArrangementStatus, Long>, JpaSpecificationExecutor<ClassArrangementStatus> {

    ClassArrangementStatus findByCode(String code);

}
