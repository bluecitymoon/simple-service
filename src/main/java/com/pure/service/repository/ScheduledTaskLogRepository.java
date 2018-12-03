package com.pure.service.repository;

import com.pure.service.domain.ScheduledTaskLog;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ScheduledTaskLog entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ScheduledTaskLogRepository extends JpaRepository<ScheduledTaskLog, Long>, JpaSpecificationExecutor<ScheduledTaskLog> {

}
