package com.pure.service.repository;

import com.pure.service.domain.TaskStatus;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the TaskStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TaskStatusRepository extends JpaRepository<TaskStatus, Long>, JpaSpecificationExecutor<TaskStatus> {

}
