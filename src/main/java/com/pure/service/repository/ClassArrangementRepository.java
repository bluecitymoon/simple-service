package com.pure.service.repository;

import com.pure.service.domain.ClassArrangement;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the ClassArrangement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClassArrangementRepository extends JpaRepository<ClassArrangement, Long>, JpaSpecificationExecutor<ClassArrangement> {

    List<ClassArrangement> findByClazz_Id(Long id);
}
