package com.pure.service.repository;

import com.pure.service.domain.Customer;
import com.pure.service.service.dto.dto.Overview;
import com.pure.service.service.dto.dto.ReportEntity;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.time.Instant;
import java.util.List;


/**
 * Spring Data JPA repository for the Customer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>, JpaSpecificationExecutor<Customer> {


    @Query(nativeQuery = true)
    List<ReportEntity> searchCustomerStatusReport(Instant startDate, Instant endDate);

    @Query(nativeQuery = true)
    Overview searchCurrentUserOverview(Long userId, Instant endDate, Instant startDate);
}
