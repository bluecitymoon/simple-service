package com.pure.service.repository;

import com.pure.service.domain.ClassArrangementRuleLoopWay;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ClassArrangementRuleLoopWay entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClassArrangementRuleLoopWayRepository extends JpaRepository<ClassArrangementRuleLoopWay, Long>, JpaSpecificationExecutor<ClassArrangementRuleLoopWay> {

}
