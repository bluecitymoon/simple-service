package com.pure.service.repository;

import com.pure.service.domain.CustomerTrackTask;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the CustomerTrackTask entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustomerTrackTaskRepository extends JpaRepository<CustomerTrackTask, Long>, JpaSpecificationExecutor<CustomerTrackTask> {

    List<CustomerTrackTask> findByCustomer_Id(Long customerId);

    @Query(value = "select count(0) as unfinished_task_count from customer_track_task tt left join task t on tt.task_id = t.id where task_status_id = 2 and tt.customer_id = ?", nativeQuery = true)
    Integer getCustomerFinishedTrackCount(Long customerId);

}
