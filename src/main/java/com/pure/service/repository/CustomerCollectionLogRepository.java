package com.pure.service.repository;

import com.pure.service.domain.CustomerCollectionLog;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the CustomerCollectionLog entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustomerCollectionLogRepository extends JpaRepository<CustomerCollectionLog, Long>, JpaSpecificationExecutor<CustomerCollectionLog> {

    List<CustomerCollectionLog> findByCustomer_Id(Long id);
}
