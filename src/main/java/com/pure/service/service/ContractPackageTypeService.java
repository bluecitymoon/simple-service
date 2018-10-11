package com.pure.service.service;

import com.pure.service.domain.ContractPackageType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing ContractPackageType.
 */
public interface ContractPackageTypeService {

    /**
     * Save a contractPackageType.
     *
     * @param contractPackageType the entity to save
     * @return the persisted entity
     */
    ContractPackageType save(ContractPackageType contractPackageType);

    /**
     *  Get all the contractPackageTypes.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ContractPackageType> findAll(Pageable pageable);

    /**
     *  Get the "id" contractPackageType.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ContractPackageType findOne(Long id);

    /**
     *  Delete the "id" contractPackageType.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
