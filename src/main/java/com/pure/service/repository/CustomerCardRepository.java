package com.pure.service.repository;

import com.pure.service.domain.CustomerCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


/**
 * Spring Data JPA repository for the CustomerCard entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustomerCardRepository extends JpaRepository<CustomerCard, Long>, JpaSpecificationExecutor<CustomerCard> {

    @Query(value = "select count(0) as customercardcount from customer_card where customer_id = ?1", nativeQuery = true)
    Integer getCustomerCardCount(Integer customerId);
}
