package com.pure.service.service;

import com.pure.service.domain.CustomerStatusReportDtl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing CustomerStatusReportDtl.
 */
public interface CustomerStatusReportDtlService {

    /**
     * Save a customerStatusReportDtl.
     *
     * @param customerStatusReportDtl the entity to save
     * @return the persisted entity
     */
    CustomerStatusReportDtl save(CustomerStatusReportDtl customerStatusReportDtl);

    /**
     *  Get all the customerStatusReportDtls.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<CustomerStatusReportDtl> findAll(Pageable pageable);

    /**
     *  Get the "id" customerStatusReportDtl.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    CustomerStatusReportDtl findOne(Long id);

    /**
     *  Delete the "id" customerStatusReportDtl.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
