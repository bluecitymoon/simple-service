package com.pure.service.repository;

import com.pure.service.domain.ClassCategory;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ClassCategory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClassCategoryRepository extends JpaRepository<ClassCategory, Long> {

}
