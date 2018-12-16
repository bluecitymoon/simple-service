package com.pure.service.service;

import com.pure.service.domain.ConsultantReport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing ConsultantReport.
 */
public interface ConsultantReportService {

    /**
     * Save a consultantReport.
     *
     * @param consultantReport the entity to save
     * @return the persisted entity
     */
    ConsultantReport save(ConsultantReport consultantReport);

    /**
     *  Get all the consultantReports.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ConsultantReport> findAll(Pageable pageable);

    /**
     *  Get the "id" consultantReport.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ConsultantReport findOne(Long id);

    /**
     *  Delete the "id" consultantReport.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
