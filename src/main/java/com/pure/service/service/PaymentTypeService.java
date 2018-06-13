package com.pure.service.service;

import com.pure.service.domain.PaymentType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing PaymentType.
 */
public interface PaymentTypeService {

    /**
     * Save a paymentType.
     *
     * @param paymentType the entity to save
     * @return the persisted entity
     */
    PaymentType save(PaymentType paymentType);

    /**
     *  Get all the paymentTypes.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<PaymentType> findAll(Pageable pageable);

    /**
     *  Get the "id" paymentType.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PaymentType findOne(Long id);

    /**
     *  Delete the "id" paymentType.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
