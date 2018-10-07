package com.pure.service.service;

import com.pure.service.domain.CustomerConsumerType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing CustomerConsumerType.
 */
public interface CustomerConsumerTypeService {

    /**
     * Save a customerConsumerType.
     *
     * @param customerConsumerType the entity to save
     * @return the persisted entity
     */
    CustomerConsumerType save(CustomerConsumerType customerConsumerType);

    /**
     *  Get all the customerConsumerTypes.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<CustomerConsumerType> findAll(Pageable pageable);

    /**
     *  Get the "id" customerConsumerType.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    CustomerConsumerType findOne(Long id);

    /**
     *  Delete the "id" customerConsumerType.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
