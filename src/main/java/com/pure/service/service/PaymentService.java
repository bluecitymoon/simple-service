package com.pure.service.service;

import com.pure.service.domain.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Payment.
 */
public interface PaymentService {

    /**
     * Save a payment.
     *
     * @param payment the entity to save
     * @return the persisted entity
     */
    Payment save(Payment payment);

    /**
     *  Get all the payments.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Payment> findAll(Pageable pageable);

    /**
     *  Get the "id" payment.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Payment findOne(Long id);

    /**
     *  Delete the "id" payment.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
