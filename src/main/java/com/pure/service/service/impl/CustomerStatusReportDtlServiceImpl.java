package com.pure.service.service.impl;

import com.pure.service.service.CustomerStatusReportDtlService;
import com.pure.service.domain.CustomerStatusReportDtl;
import com.pure.service.repository.CustomerStatusReportDtlRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing CustomerStatusReportDtl.
 */
@Service
@Transactional
public class CustomerStatusReportDtlServiceImpl implements CustomerStatusReportDtlService{

    private final Logger log = LoggerFactory.getLogger(CustomerStatusReportDtlServiceImpl.class);

    private final CustomerStatusReportDtlRepository customerStatusReportDtlRepository;

    public CustomerStatusReportDtlServiceImpl(CustomerStatusReportDtlRepository customerStatusReportDtlRepository) {
        this.customerStatusReportDtlRepository = customerStatusReportDtlRepository;
    }

    /**
     * Save a customerStatusReportDtl.
     *
     * @param customerStatusReportDtl the entity to save
     * @return the persisted entity
     */
    @Override
    public CustomerStatusReportDtl save(CustomerStatusReportDtl customerStatusReportDtl) {
        log.debug("Request to save CustomerStatusReportDtl : {}", customerStatusReportDtl);
        return customerStatusReportDtlRepository.save(customerStatusReportDtl);
    }

    /**
     *  Get all the customerStatusReportDtls.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CustomerStatusReportDtl> findAll(Pageable pageable) {
        log.debug("Request to get all CustomerStatusReportDtls");
        return customerStatusReportDtlRepository.findAll(pageable);
    }

    /**
     *  Get one customerStatusReportDtl by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public CustomerStatusReportDtl findOne(Long id) {
        log.debug("Request to get CustomerStatusReportDtl : {}", id);
        return customerStatusReportDtlRepository.findOne(id);
    }

    /**
     *  Delete the  customerStatusReportDtl by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CustomerStatusReportDtl : {}", id);
        customerStatusReportDtlRepository.delete(id);
    }
}
