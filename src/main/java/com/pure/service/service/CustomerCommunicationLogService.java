package com.pure.service.service;

import com.pure.service.domain.CustomerCommunicationLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing CustomerCommunicationLog.
 */
public interface CustomerCommunicationLogService {

    /**
     * Save a customerCommunicationLog.
     *
     * @param customerCommunicationLog the entity to save
     * @return the persisted entity
     */
    CustomerCommunicationLog save(CustomerCommunicationLog customerCommunicationLog);

    /**
     *  Get all the customerCommunicationLogs.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<CustomerCommunicationLog> findAll(Pageable pageable);

    /**
     *  Get the "id" customerCommunicationLog.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    CustomerCommunicationLog findOne(Long id);

    /**
     *  Delete the "id" customerCommunicationLog.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
