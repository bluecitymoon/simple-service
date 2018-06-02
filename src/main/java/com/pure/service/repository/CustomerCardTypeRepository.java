package com.pure.service.repository;

import com.pure.service.domain.CustomerCardType;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the CustomerCardType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustomerCardTypeRepository extends JpaRepository<CustomerCardType, Long>, JpaSpecificationExecutor<CustomerCardType> {

}
