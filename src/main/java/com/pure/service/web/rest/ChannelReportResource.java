package com.pure.service.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pure.service.domain.ChannelReport;
import com.pure.service.service.ChannelReportService;
import com.pure.service.web.rest.util.HeaderUtil;
import com.pure.service.web.rest.util.PaginationUtil;
import com.pure.service.service.dto.ChannelReportCriteria;
import com.pure.service.service.ChannelReportQueryService;
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
 * REST controller for managing ChannelReport.
 */
@RestController
@RequestMapping("/api")
public class ChannelReportResource {

    private final Logger log = LoggerFactory.getLogger(ChannelReportResource.class);

    private static final String ENTITY_NAME = "channelReport";

    private final ChannelReportService channelReportService;

    private final ChannelReportQueryService channelReportQueryService;

    public ChannelReportResource(ChannelReportService channelReportService, ChannelReportQueryService channelReportQueryService) {
        this.channelReportService = channelReportService;
        this.channelReportQueryService = channelReportQueryService;
    }

    /**
     * POST  /channel-reports : Create a new channelReport.
     *
     * @param channelReport the channelReport to create
     * @return the ResponseEntity with status 201 (Created) and with body the new channelReport, or with status 400 (Bad Request) if the channelReport has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/channel-reports")
    @Timed
    public ResponseEntity<ChannelReport> createChannelReport(@RequestBody ChannelReport channelReport) throws URISyntaxException {
        log.debug("REST request to save ChannelReport : {}", channelReport);
        if (channelReport.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new channelReport cannot already have an ID")).body(null);
        }
        ChannelReport result = channelReportService.save(channelReport);
        return ResponseEntity.created(new URI("/api/channel-reports/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /channel-reports : Updates an existing channelReport.
     *
     * @param channelReport the channelReport to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated channelReport,
     * or with status 400 (Bad Request) if the channelReport is not valid,
     * or with status 500 (Internal Server Error) if the channelReport couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/channel-reports")
    @Timed
    public ResponseEntity<ChannelReport> updateChannelReport(@RequestBody ChannelReport channelReport) throws URISyntaxException {
        log.debug("REST request to update ChannelReport : {}", channelReport);
        if (channelReport.getId() == null) {
            return createChannelReport(channelReport);
        }
        ChannelReport result = channelReportService.save(channelReport);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, channelReport.getId().toString()))
            .body(result);
    }

    /**
     * GET  /channel-reports : get all the channelReports.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of channelReports in body
     */
    @GetMapping("/channel-reports")
    @Timed
    public ResponseEntity<List<ChannelReport>> getAllChannelReports(ChannelReportCriteria criteria,@ApiParam Pageable pageable) {
        log.debug("REST request to get ChannelReports by criteria: {}", criteria);
        Page<ChannelReport> page = channelReportQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/channel-reports");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /channel-reports/:id : get the "id" channelReport.
     *
     * @param id the id of the channelReport to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the channelReport, or with status 404 (Not Found)
     */
    @GetMapping("/channel-reports/{id}")
    @Timed
    public ResponseEntity<ChannelReport> getChannelReport(@PathVariable Long id) {
        log.debug("REST request to get ChannelReport : {}", id);
        ChannelReport channelReport = channelReportService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(channelReport));
    }

    /**
     * DELETE  /channel-reports/:id : delete the "id" channelReport.
     *
     * @param id the id of the channelReport to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/channel-reports/{id}")
    @Timed
    public ResponseEntity<Void> deleteChannelReport(@PathVariable Long id) {
        log.debug("REST request to delete ChannelReport : {}", id);
        channelReportService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
