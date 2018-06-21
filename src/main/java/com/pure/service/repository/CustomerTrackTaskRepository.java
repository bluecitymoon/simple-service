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

}
