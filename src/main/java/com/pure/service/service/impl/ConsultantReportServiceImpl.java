package com.pure.service.service.impl;

import com.pure.service.service.ConsultantReportService;
import com.pure.service.domain.ConsultantReport;
import com.pure.service.repository.ConsultantReportRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing ConsultantReport.
 */
@Service
@Transactional
public class ConsultantReportServiceImpl implements ConsultantReportService{

    private final Logger log = LoggerFactory.getLogger(ConsultantReportServiceImpl.class);

    private final ConsultantReportRepository consultantReportRepository;

    public ConsultantReportServiceImpl(ConsultantReportRepository consultantReportRepository) {
        this.consultantReportRepository = consultantReportRepository;
    }

    /**
     * Save a consultantReport.
     *
     * @param consultantReport the entity to save
     * @return the persisted entity
     */
    @Override
    public ConsultantReport save(ConsultantReport consultantReport) {
        log.debug("Request to save ConsultantReport : {}", consultantReport);
        return consultantReportRepository.save(consultantReport);
    }

    /**
     *  Get all the consultantReports.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ConsultantReport> findAll(Pageable pageable) {
        log.debug("Request to get all ConsultantReports");
        return consultantReportRepository.findAll(pageable);
    }

    /**
     *  Get one consultantReport by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ConsultantReport findOne(Long id) {
        log.debug("Request to get ConsultantReport : {}", id);
        return consultantReportRepository.findOne(id);
    }

    /**
     *  Delete the  consultantReport by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ConsultantReport : {}", id);
        consultantReportRepository.delete(id);
    }
}
