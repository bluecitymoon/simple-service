package com.pure.service.repository;

import com.pure.service.domain.VistedCustomerStatus;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the VistedCustomerStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VistedCustomerStatusRepository extends JpaRepository<VistedCustomerStatus, Long>, JpaSpecificationExecutor<VistedCustomerStatus> {

}
