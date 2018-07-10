package com.pure.service.service;

import com.pure.service.domain.ContractStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing ContractStatus.
 */
public interface ContractStatusService {

    /**
     * Save a contractStatus.
     *
     * @param contractStatus the entity to save
     * @return the persisted entity
     */
    ContractStatus save(ContractStatus contractStatus);

    /**
     *  Get all the contractStatuses.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ContractStatus> findAll(Pageable pageable);

    /**
     *  Get the "id" contractStatus.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ContractStatus findOne(Long id);

    /**
     *  Delete the "id" contractStatus.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
