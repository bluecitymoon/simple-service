package com.pure.service.repository;

import com.pure.service.domain.Task;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the Task entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TaskRepository extends JpaRepository<Task, Long>, JpaSpecificationExecutor<Task> {

    @Query("select task from Task task where task.assignee.login = ?#{principal.username}")
    List<Task> findByAssigneeIsCurrentUser();

    @Query("select task from Task task where task.reporter.login = ?#{principal.username}")
    List<Task> findByReporterIsCurrentUser();

}
