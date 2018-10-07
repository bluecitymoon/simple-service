package com.pure.service.service;

import com.pure.service.domain.CustomerConsumerLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing CustomerConsumerLog.
 */
public interface CustomerConsumerLogService {

    /**
     * Save a customerConsumerLog.
     *
     * @param customerConsumerLog the entity to save
     * @return the persisted entity
     */
    CustomerConsumerLog save(CustomerConsumerLog customerConsumerLog);

    /**
     *  Get all the customerConsumerLogs.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<CustomerConsumerLog> findAll(Pageable pageable);

    /**
     *  Get the "id" customerConsumerLog.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    CustomerConsumerLog findOne(Long id);

    /**
     *  Delete the "id" customerConsumerLog.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
