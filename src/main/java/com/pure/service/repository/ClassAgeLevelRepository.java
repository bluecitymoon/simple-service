package com.pure.service.repository;

import com.pure.service.domain.ClassAgeLevel;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ClassAgeLevel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClassAgeLevelRepository extends JpaRepository<ClassAgeLevel, Long>, JpaSpecificationExecutor<ClassAgeLevel> {

}
