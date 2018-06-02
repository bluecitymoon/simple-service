package com.pure.service.service;

import com.pure.service.domain.CustomerCardType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing CustomerCardType.
 */
public interface CustomerCardTypeService {

    /**
     * Save a customerCardType.
     *
     * @param customerCardType the entity to save
     * @return the persisted entity
     */
    CustomerCardType save(CustomerCardType customerCardType);

    /**
     *  Get all the customerCardTypes.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<CustomerCardType> findAll(Pageable pageable);

    /**
     *  Get the "id" customerCardType.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    CustomerCardType findOne(Long id);

    /**
     *  Delete the "id" customerCardType.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
