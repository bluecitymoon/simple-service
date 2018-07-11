package com.pure.service.service;

import com.pure.service.domain.ContractType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing ContractType.
 */
public interface ContractTypeService {

    /**
     * Save a contractType.
     *
     * @param contractType the entity to save
     * @return the persisted entity
     */
    ContractType save(ContractType contractType);

    /**
     *  Get all the contractTypes.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ContractType> findAll(Pageable pageable);

    /**
     *  Get the "id" contractType.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ContractType findOne(Long id);

    /**
     *  Delete the "id" contractType.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
