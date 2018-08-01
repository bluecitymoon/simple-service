package com.pure.service.service;

import com.pure.service.domain.ContractPackage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing ContractPackage.
 */
public interface ContractPackageService {

    /**
     * Save a contractPackage.
     *
     * @param contractPackage the entity to save
     * @return the persisted entity
     */
    ContractPackage save(ContractPackage contractPackage);

    /**
     *  Get all the contractPackages.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ContractPackage> findAll(Pageable pageable);

    /**
     *  Get the "id" contractPackage.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ContractPackage findOne(Long id);

    /**
     *  Delete the "id" contractPackage.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
