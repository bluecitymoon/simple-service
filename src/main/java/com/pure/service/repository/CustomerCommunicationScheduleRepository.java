package com.pure.service.repository;

import com.pure.service.domain.CustomerCommunicationSchedule;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.time.Instant;
import java.util.List;

/**
 * Spring Data JPA repository for the CustomerCommunicationSchedule entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustomerCommunicationScheduleRepository extends JpaRepository<CustomerCommunicationSchedule, Long>, JpaSpecificationExecutor<CustomerCommunicationSchedule> {

    @Query("select customer_schedule from CustomerCommunicationSchedule customer_schedule where customer_schedule.follower.login = ?#{principal.username}")
    List<CustomerCommunicationSchedule> findByFollowerIsCurrentUser();

    List<CustomerCommunicationSchedule> findByCustomer_Id(Long id);
    List<CustomerCommunicationSchedule> findByCustomer_IdAndSourceType(Long id, String sourceType);

    @Query(nativeQuery = true, value = "select count(0) as customerCount from customer_schedule where actuall_meet_date > :1 and actuall_meet_date < :2 and region_id = :3")
    Integer getCustomerVisitedCountBetween(Instant start, Instant end, Long regionId);

}
