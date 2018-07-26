package com.pure.service.service;

import com.pure.service.domain.ContractTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing ContractTemplate.
 */
public interface ContractTemplateService {

    /**
     * Save a contractTemplate.
     *
     * @param contractTemplate the entity to save
     * @return the persisted entity
     */
    ContractTemplate save(ContractTemplate contractTemplate);

    /**
     *  Get all the contractTemplates.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ContractTemplate> findAll(Pageable pageable);

    /**
     *  Get the "id" contractTemplate.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ContractTemplate findOne(Long id);

    /**
     *  Delete the "id" contractTemplate.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
