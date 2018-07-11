package com.pure.service.service;

import com.pure.service.domain.ContractNature;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing ContractNature.
 */
public interface ContractNatureService {

    /**
     * Save a contractNature.
     *
     * @param contractNature the entity to save
     * @return the persisted entity
     */
    ContractNature save(ContractNature contractNature);

    /**
     *  Get all the contractNatures.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ContractNature> findAll(Pageable pageable);

    /**
     *  Get the "id" contractNature.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ContractNature findOne(Long id);

    /**
     *  Delete the "id" contractNature.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
