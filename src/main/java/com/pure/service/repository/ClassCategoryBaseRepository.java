package com.pure.service.repository;

import com.pure.service.domain.ClassCategoryBase;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ClassCategoryBase entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClassCategoryBaseRepository extends JpaRepository<ClassCategoryBase, Long>, JpaSpecificationExecutor<ClassCategoryBase> {

}
