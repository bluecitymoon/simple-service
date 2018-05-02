package com.pure.service.service.impl;

import com.pure.service.service.CustomerCommunicationScheduleService;
import com.pure.service.domain.CustomerCommunicationSchedule;
import com.pure.service.repository.CustomerCommunicationScheduleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing CustomerCommunicationSchedule.
 */
@Service
@Transactional
public class CustomerCommunicationScheduleServiceImpl implements CustomerCommunicationScheduleService{

    private final Logger log = LoggerFactory.getLogger(CustomerCommunicationScheduleServiceImpl.class);

    private final CustomerCommunicationScheduleRepository customerCommunicationScheduleRepository;

    public CustomerCommunicationScheduleServiceImpl(CustomerCommunicationScheduleRepository customerCommunicationScheduleRepository) {
        this.customerCommunicationScheduleRepository = customerCommunicationScheduleRepository;
    }

    /**
     * Save a customerCommunicationSchedule.
     *
     * @param customerCommunicationSchedule the entity to save
     * @return the persisted entity
     */
    @Override
    public CustomerCommunicationSchedule save(CustomerCommunicationSchedule customerCommunicationSchedule) {
        log.debug("Request to save CustomerCommunicationSchedule : {}", customerCommunicationSchedule);
        return customerCommunicationScheduleRepository.save(customerCommunicationSchedule);
    }

    /**
     *  Get all the customerCommunicationSchedules.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CustomerCommunicationSchedule> findAll(Pageable pageable) {
        log.debug("Request to get all CustomerCommunicationSchedules");
        return customerCommunicationScheduleRepository.findAll(pageable);
    }

    /**
     *  Get one customerCommunicationSchedule by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public CustomerCommunicationSchedule findOne(Long id) {
        log.debug("Request to get CustomerCommunicationSchedule : {}", id);
        return customerCommunicationScheduleRepository.findOne(id);
    }

    /**
     *  Delete the  customerCommunicationSchedule by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CustomerCommunicationSchedule : {}", id);
        customerCommunicationScheduleRepository.delete(id);
    }
}
