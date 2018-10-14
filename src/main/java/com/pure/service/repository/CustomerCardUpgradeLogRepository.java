package com.pure.service.repository;

import com.pure.service.domain.CustomerCardUpgradeLog;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the CustomerCardUpgradeLog entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustomerCardUpgradeLogRepository extends JpaRepository<CustomerCardUpgradeLog, Long>, JpaSpecificationExecutor<CustomerCardUpgradeLog> {

}
