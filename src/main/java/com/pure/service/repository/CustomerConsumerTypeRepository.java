package com.pure.service.repository;

import com.pure.service.domain.CustomerConsumerType;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the CustomerConsumerType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustomerConsumerTypeRepository extends JpaRepository<CustomerConsumerType, Long>, JpaSpecificationExecutor<CustomerConsumerType> {

}
