package com.pure.service.repository;

import com.pure.service.domain.ClassArrangementRule;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ClassArrangementRule entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClassArrangementRuleRepository extends JpaRepository<ClassArrangementRule, Long>, JpaSpecificationExecutor<ClassArrangementRule> {

}
