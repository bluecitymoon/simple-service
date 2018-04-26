package com.pure.service.service.impl;

import com.pure.service.service.CustomerService;
import com.pure.service.domain.Customer;
import com.pure.service.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Customer.
 */
@Service
@Transactional
public class CustomerServiceImpl implements CustomerService{

    private final Logger log = LoggerFactory.getLogger(CustomerServiceImpl.class);

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    /**
     * Save a customer.
     *
     * @param customer the entity to save
     * @return the persisted entity
     */
    @Override
    public Customer save(Customer customer) {
        log.debug("Request to save Customer : {}", customer);
        return customerRepository.save(customer);
    }

    /**
     *  Get all the customers.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Customer> findAll(Pageable pageable) {
        log.debug("Request to get all Customers");
        return customerRepository.findAll(pageable);
    }

    /**
     *  Get one customer by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Customer findOne(Long id) {
        log.debug("Request to get Customer : {}", id);
        return customerRepository.findOne(id);
    }

    /**
     *  Delete the  customer by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Customer : {}", id);
        customerRepository.delete(id);
    }
}
