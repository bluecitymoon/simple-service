package com.pure.service.repository;

import com.pure.service.domain.CustomerCommunicationLog;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the CustomerCommunicationLog entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustomerCommunicationLogRepository extends JpaRepository<CustomerCommunicationLog, Long>, JpaSpecificationExecutor<CustomerCommunicationLog> {

    List<CustomerCommunicationLog> findByCustomer_Id(Long customerId);
}
