package com.pure.service.service.impl;

import com.pure.service.service.CustomerCardTypeService;
import com.pure.service.domain.CustomerCardType;
import com.pure.service.repository.CustomerCardTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing CustomerCardType.
 */
@Service
@Transactional
public class CustomerCardTypeServiceImpl implements CustomerCardTypeService{

    private final Logger log = LoggerFactory.getLogger(CustomerCardTypeServiceImpl.class);

    private final CustomerCardTypeRepository customerCardTypeRepository;

    public CustomerCardTypeServiceImpl(CustomerCardTypeRepository customerCardTypeRepository) {
        this.customerCardTypeRepository = customerCardTypeRepository;
    }

    /**
     * Save a customerCardType.
     *
     * @param customerCardType the entity to save
     * @return the persisted entity
     */
    @Override
    public CustomerCardType save(CustomerCardType customerCardType) {
        log.debug("Request to save CustomerCardType : {}", customerCardType);
        return customerCardTypeRepository.save(customerCardType);
    }

    /**
     *  Get all the customerCardTypes.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CustomerCardType> findAll(Pageable pageable) {
        log.debug("Request to get all CustomerCardTypes");
        return customerCardTypeRepository.findAll(pageable);
    }

    /**
     *  Get one customerCardType by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public CustomerCardType findOne(Long id) {
        log.debug("Request to get CustomerCardType : {}", id);
        return customerCardTypeRepository.findOne(id);
    }

    /**
     *  Delete the  customerCardType by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CustomerCardType : {}", id);
        customerCardTypeRepository.delete(id);
    }
}
