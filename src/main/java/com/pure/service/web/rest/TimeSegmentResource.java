package com.pure.service.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pure.service.domain.TimeSegment;
import com.pure.service.service.TimeSegmentService;
import com.pure.service.web.rest.util.HeaderUtil;
import com.pure.service.web.rest.util.PaginationUtil;
import com.pure.service.service.dto.TimeSegmentCriteria;
import com.pure.service.service.TimeSegmentQueryService;
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
 * REST controller for managing TimeSegment.
 */
@RestController
@RequestMapping("/api")
public class TimeSegmentResource {

    private final Logger log = LoggerFactory.getLogger(TimeSegmentResource.class);

    private static final String ENTITY_NAME = "timeSegment";

    private final TimeSegmentService timeSegmentService;

    private final TimeSegmentQueryService timeSegmentQueryService;

    public TimeSegmentResource(TimeSegmentService timeSegmentService, TimeSegmentQueryService timeSegmentQueryService) {
        this.timeSegmentService = timeSegmentService;
        this.timeSegmentQueryService = timeSegmentQueryService;
    }

    /**
     * POST  /time-segments : Create a new timeSegment.
     *
     * @param timeSegment the timeSegment to create
     * @return the ResponseEntity with status 201 (Created) and with body the new timeSegment, or with status 400 (Bad Request) if the timeSegment has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/time-segments")
    @Timed
    public ResponseEntity<TimeSegment> createTimeSegment(@RequestBody TimeSegment timeSegment) throws URISyntaxException {
        log.debug("REST request to save TimeSegment : {}", timeSegment);
        if (timeSegment.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new timeSegment cannot already have an ID")).body(null);
        }
        TimeSegment result = timeSegmentService.save(timeSegment);
        return ResponseEntity.created(new URI("/api/time-segments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /time-segments : Updates an existing timeSegment.
     *
     * @param timeSegment the timeSegment to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated timeSegment,
     * or with status 400 (Bad Request) if the timeSegment is not valid,
     * or with status 500 (Internal Server Error) if the timeSegment couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/time-segments")
    @Timed
    public ResponseEntity<TimeSegment> updateTimeSegment(@RequestBody TimeSegment timeSegment) throws URISyntaxException {
        log.debug("REST request to update TimeSegment : {}", timeSegment);
        if (timeSegment.getId() == null) {
            return createTimeSegment(timeSegment);
        }
        TimeSegment result = timeSegmentService.save(timeSegment);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, timeSegment.getId().toString()))
            .body(result);
    }

    /**
     * GET  /time-segments : get all the timeSegments.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of timeSegments in body
     */
    @GetMapping("/time-segments")
    @Timed
    public ResponseEntity<List<TimeSegment>> getAllTimeSegments(TimeSegmentCriteria criteria,@ApiParam Pageable pageable) {
        log.debug("REST request to get TimeSegments by criteria: {}", criteria);
        Page<TimeSegment> page = timeSegmentQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/time-segments");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /time-segments/:id : get the "id" timeSegment.
     *
     * @param id the id of the timeSegment to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the timeSegment, or with status 404 (Not Found)
     */
    @GetMapping("/time-segments/{id}")
    @Timed
    public ResponseEntity<TimeSegment> getTimeSegment(@PathVariable Long id) {
        log.debug("REST request to get TimeSegment : {}", id);
        TimeSegment timeSegment = timeSegmentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(timeSegment));
    }

    /**
     * DELETE  /time-segments/:id : delete the "id" timeSegment.
     *
     * @param id the id of the timeSegment to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/time-segments/{id}")
    @Timed
    public ResponseEntity<Void> deleteTimeSegment(@PathVariable Long id) {
        log.debug("REST request to delete TimeSegment : {}", id);
        timeSegmentService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
