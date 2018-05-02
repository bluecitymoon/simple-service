package com.pure.service.service;

import com.pure.service.domain.CustomerCommunicationSchedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing CustomerCommunicationSchedule.
 */
public interface CustomerCommunicationScheduleService {

    /**
     * Save a customerCommunicationSchedule.
     *
     * @param customerCommunicationSchedule the entity to save
     * @return the persisted entity
     */
    CustomerCommunicationSchedule save(CustomerCommunicationSchedule customerCommunicationSchedule);

    /**
     *  Get all the customerCommunicationSchedules.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<CustomerCommunicationSchedule> findAll(Pageable pageable);

    /**
     *  Get the "id" customerCommunicationSchedule.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    CustomerCommunicationSchedule findOne(Long id);

    /**
     *  Delete the "id" customerCommunicationSchedule.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
