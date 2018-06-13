package com.pure.service.repository;

import com.pure.service.domain.Payment;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the Payment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long>, JpaSpecificationExecutor<Payment> {

    @Query("select payment from Payment payment where payment.paidUser.login = ?#{principal.username}")
    List<Payment> findByPaidUserIsCurrentUser();

}
