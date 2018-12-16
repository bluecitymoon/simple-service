package com.pure.service.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pure.service.domain.ConsultantReport;
import com.pure.service.service.ConsultantReportService;
import com.pure.service.web.rest.util.HeaderUtil;
import com.pure.service.web.rest.util.PaginationUtil;
import com.pure.service.service.dto.ConsultantReportCriteria;
import com.pure.service.service.ConsultantReportQueryService;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ConsultantReport.
 */
@RestController
@RequestMapping("/api")
public class ConsultantReportResource {

    private final Logger log = LoggerFactory.getLogger(ConsultantReportResource.class);

    private static final String ENTITY_NAME = "consultantReport";

    private final ConsultantReportService consultantReportService;

    private final ConsultantReportQueryService consultantReportQueryService;

    public ConsultantReportResource(ConsultantReportService consultantReportService, ConsultantReportQueryService consultantReportQueryService) {
        this.consultantReportService = consultantReportService;
        this.consultantReportQueryService = consultantReportQueryService;
    }

    /**
     * POST  /consultant-reports : Create a new consultantReport.
     *
     * @param consultantReport the consultantReport to create
     * @return the ResponseEntity with status 201 (Created) and with body the new consultantReport, or with status 400 (Bad Request) if the consultantReport has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/consultant-reports")
    @Timed
    public ResponseEntity<ConsultantReport> createConsultantReport(@RequestBody ConsultantReport consultantReport) throws URISyntaxException {
        log.debug("REST request to save ConsultantReport : {}", consultantReport);
        if (consultantReport.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new consultantReport cannot already have an ID")).body(null);
        }
        ConsultantReport result = consultantReportService.save(consultantReport);
        return ResponseEntity.created(new URI("/api/consultant-reports/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /consultant-reports : Updates an existing consultantReport.
     *
     * @param consultantReport the consultantReport to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated consultantReport,
     * or with status 400 (Bad Request) if the consultantReport is not valid,
     * or with status 500 (Internal Server Error) if the consultantReport couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/consultant-reports")
    @Timed
    public ResponseEntity<ConsultantReport> updateConsultantReport(@RequestBody ConsultantReport consultantReport) throws URISyntaxException {
        log.debug("REST request to update ConsultantReport : {}", consultantReport);
        if (consultantReport.getId() == null) {
            return createConsultantReport(consultantReport);
        }
        ConsultantReport result = consultantReportService.save(consultantReport);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, consultantReport.getId().toString()))
            .body(result);
    }

    /**
     * GET  /consultant-reports : get all the consultantReports.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of consultantReports in body
     */
    @GetMapping("/consultant-reports")
    @Timed
    public ResponseEntity<List<ConsultantReport>> getAllConsultantReports(ConsultantReportCriteria criteria,@ApiParam Pageable pageable) {
        log.debug("REST request to get ConsultantReports by criteria: {}", criteria);
        Page<ConsultantReport> page = consultantReportQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/consultant-reports");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /consultant-reports/:id : get the "id" consultantReport.
     *
     * @param id the id of the consultantReport to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the consultantReport, or with status 404 (Not Found)
     */
    @GetMapping("/consultant-reports/{id}")
    @Timed
    public ResponseEntity<ConsultantReport> getConsultantReport(@PathVariable Long id) {
        log.debug("REST request to get ConsultantReport : {}", id);
        ConsultantReport consultantReport = consultantReportService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(consultantReport));
    }

    /**
     * DELETE  /consultant-reports/:id : delete the "id" consultantReport.
     *
     * @param id the id of the consultantReport to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/consultant-reports/{id}")
    @Timed
    public ResponseEntity<Void> deleteConsultantReport(@PathVariable Long id) {
        log.debug("REST request to delete ConsultantReport : {}", id);
        consultantReportService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
