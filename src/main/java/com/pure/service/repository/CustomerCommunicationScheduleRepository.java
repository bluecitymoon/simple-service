package com.pure.service.repository;

import com.pure.service.domain.CustomerCommunicationSchedule;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the CustomerCommunicationSchedule entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustomerCommunicationScheduleRepository extends JpaRepository<CustomerCommunicationSchedule, Long>, JpaSpecificationExecutor<CustomerCommunicationSchedule> {

    @Query("select customer_schedule from CustomerCommunicationSchedule customer_schedule where customer_schedule.follower.login = ?#{principal.username}")
    List<CustomerCommunicationSchedule> findByFollowerIsCurrentUser();

}
