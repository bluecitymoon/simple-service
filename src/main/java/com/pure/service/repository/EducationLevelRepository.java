package com.pure.service.repository;

import com.pure.service.domain.EducationLevel;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the EducationLevel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EducationLevelRepository extends JpaRepository<EducationLevel, Long>, JpaSpecificationExecutor<EducationLevel> {

}
