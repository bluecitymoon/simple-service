package com.pure.service.repository;

import com.pure.service.domain.FreeClassPlan;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the FreeClassPlan entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FreeClassPlanRepository extends JpaRepository<FreeClassPlan, Long>, JpaSpecificationExecutor<FreeClassPlan> {

}
