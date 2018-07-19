package com.pure.service.repository;

import com.pure.service.domain.CustomerStatus;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the CustomerStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustomerStatusRepository extends JpaRepository<CustomerStatus, Long>, JpaSpecificationExecutor<CustomerStatus> {

    CustomerStatus findByCode(String code);
}
