package com.pure.service.repository;

import com.pure.service.domain.CustomerCard;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the CustomerCard entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustomerCardRepository extends JpaRepository<CustomerCard, Long>, JpaSpecificationExecutor<CustomerCard> {

}
