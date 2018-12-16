package com.pure.service.repository;

import com.pure.service.domain.CustomerConsumerLog;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the CustomerConsumerLog entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustomerConsumerLogRepository extends JpaRepository<CustomerConsumerLog, Long>, JpaSpecificationExecutor<CustomerConsumerLog> {

    CustomerConsumerLog findByUniqueNumber(String uniqueNumber);
}
