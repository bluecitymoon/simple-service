package com.pure.service.service;

import com.pure.service.domain.CustomerCardUpgradeLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing CustomerCardUpgradeLog.
 */
public interface CustomerCardUpgradeLogService {

    /**
     * Save a customerCardUpgradeLog.
     *
     * @param customerCardUpgradeLog the entity to save
     * @return the persisted entity
     */
    CustomerCardUpgradeLog save(CustomerCardUpgradeLog customerCardUpgradeLog);

    /**
     *  Get all the customerCardUpgradeLogs.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<CustomerCardUpgradeLog> findAll(Pageable pageable);

    /**
     *  Get the "id" customerCardUpgradeLog.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    CustomerCardUpgradeLog findOne(Long id);

    /**
     *  Delete the "id" customerCardUpgradeLog.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
