package com.pure.service.repository;

import com.pure.service.domain.SystemVariable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the SystemVariable entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SystemVariableRepository extends JpaRepository<SystemVariable, Long>, JpaSpecificationExecutor<SystemVariable> {

}
