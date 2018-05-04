package com.pure.service.repository;

import com.pure.service.domain.CustomerScheduleStatus;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the CustomerScheduleStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustomerScheduleStatusRepository extends JpaRepository<CustomerScheduleStatus, Long>, JpaSpecificationExecutor<CustomerScheduleStatus> {

}
