package com.pure.service.repository;

import com.pure.service.domain.CustomerCommunicationLogType;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the CustomerCommunicationLogType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustomerCommunicationLogTypeRepository extends JpaRepository<CustomerCommunicationLogType, Long> {

}
