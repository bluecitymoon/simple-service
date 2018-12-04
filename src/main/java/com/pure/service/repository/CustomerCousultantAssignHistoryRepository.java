package com.pure.service.repository;

import com.pure.service.domain.CustomerCousultantAssignHistory;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the CustomerCousultantAssignHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustomerCousultantAssignHistoryRepository extends JpaRepository<CustomerCousultantAssignHistory, Long>, JpaSpecificationExecutor<CustomerCousultantAssignHistory> {

}
