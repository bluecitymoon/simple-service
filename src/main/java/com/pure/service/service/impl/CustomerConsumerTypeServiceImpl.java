package com.pure.service.service.impl;

import com.pure.service.service.CustomerConsumerTypeService;
import com.pure.service.domain.CustomerConsumerType;
import com.pure.service.repository.CustomerConsumerTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing CustomerConsumerType.
 */
@Service
@Transactional
public class CustomerConsumerTypeServiceImpl implements CustomerConsumerTypeService{

    private final Logger log = LoggerFactory.getLogger(CustomerConsumerTypeServiceImpl.class);

    private final CustomerConsumerTypeRepository customerConsumerTypeRepository;

    public CustomerConsumerTypeServiceImpl(CustomerConsumerTypeRepository customerConsumerTypeRepository) {
        this.customerConsumerTypeRepository = customerConsumerTypeRepository;
    }

    /**
     * Save a customerConsumerType.
     *
     * @param customerConsumerType the entity to save
     * @return the persisted entity
     */
    @Override
    public CustomerConsumerType save(CustomerConsumerType customerConsumerType) {
        log.debug("Request to save CustomerConsumerType : {}", customerConsumerType);
        return customerConsumerTypeRepository.save(customerConsumerType);
    }

    /**
     *  Get all the customerConsumerTypes.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CustomerConsumerType> findAll(Pageable pageable) {
        log.debug("Request to get all CustomerConsumerTypes");
        return customerConsumerTypeRepository.findAll(pageable);
    }

    /**
     *  Get one customerConsumerType by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public CustomerConsumerType findOne(Long id) {
        log.debug("Request to get CustomerConsumerType : {}", id);
        return customerConsumerTypeRepository.findOne(id);
    }

    /**
     *  Delete the  customerConsumerType by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CustomerConsumerType : {}", id);
        customerConsumerTypeRepository.delete(id);
    }
}
