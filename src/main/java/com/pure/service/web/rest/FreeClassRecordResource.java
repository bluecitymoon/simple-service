package com.pure.service.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pure.service.domain.FreeClassRecord;
import com.pure.service.service.FreeClassRecordQueryService;
import com.pure.service.service.FreeClassRecordService;
import com.pure.service.service.dto.FreeClassRecordCriteria;
import com.pure.service.web.rest.util.HeaderUtil;
import com.pure.service.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing FreeClassRecord.
 */
@RestController
@RequestMapping("/api")
public class FreeClassRecordResource {

    private final Logger log = LoggerFactory.getLogger(FreeClassRecordResource.class);

    private static final String ENTITY_NAME = "freeClassRecord";

    private final FreeClassRecordService freeClassRecordService;

    private final FreeClassRecordQueryService freeClassRecordQueryService;

    public FreeClassRecordResource(FreeClassRecordService freeClassRecordService, FreeClassRecordQueryService freeClassRecordQueryService) {
        this.freeClassRecordService = freeClassRecordService;
        this.freeClassRecordQueryService = freeClassRecordQueryService;
    }

    /**
     * POST  /free-class-records : Create a new freeClassRecord.
     *
     * @param freeClassRecord the freeClassRecord to create
     * @return the ResponseEntity with status 201 (Created) and with body the new freeClassRecord, or with status 400 (Bad Request) if the freeClassRecord has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/free-class-records")
    @Timed
    public ResponseEntity<FreeClassRecord> createFreeClassRecord(@RequestBody FreeClassRecord freeClassRecord) throws URISyntaxException {
        log.debug("REST request to save FreeClassRecord : {}", freeClassRecord);
        if (freeClassRecord.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new freeClassRecord cannot already have an ID")).body(null);
        }
        FreeClassRecord result = freeClassRecordService.save(freeClassRecord);
        return ResponseEntity.created(new URI("/api/free-class-records/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /free-class-records : Updates an existing freeClassRecord.
     *
     * @param freeClassRecord the freeClassRecord to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated freeClassRecord,
     * or with status 400 (Bad Request) if the freeClassRecord is not valid,
     * or with status 500 (Internal Server Error) if the freeClassRecord couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/free-class-records")
    @Timed
    public ResponseEntity<FreeClassRecord> updateFreeClassRecord(@RequestBody FreeClassRecord freeClassRecord) throws URISyntaxException {
        log.debug("REST request to update FreeClassRecord : {}", freeClassRecord);
        if (freeClassRecord.getId() == null) {
            return createFreeClassRecord(freeClassRecord);
        }
        FreeClassRecord result = freeClassRecordService.save(freeClassRecord);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, freeClassRecord.getId().toString()))
            .body(result);
    }

    /**
     * GET  /free-class-records : get all the freeClassRecords.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of freeClassRecords in body
     */
    @GetMapping("/free-class-records")
    @Timed
    public ResponseEntity<List<FreeClassRecord>> getAllFreeClassRecords(FreeClassRecordCriteria criteria,@ApiParam Pageable pageable) {
        log.debug("REST request to get FreeClassRecords by criteria: {}", criteria);
        Page<FreeClassRecord> page = freeClassRecordQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/free-class-records");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /free-class-records/:id : get the "id" freeClassRecord.
     *
     * @param id the id of the freeClassRecord to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the freeClassRecord, or with status 404 (Not Found)
     */
    @GetMapping("/free-class-records/{id}")
    @Timed
    public ResponseEntity<FreeClassRecord> getFreeClassRecord(@PathVariable Long id) {
        log.debug("REST request to get FreeClassRecord : {}", id);
        FreeClassRecord freeClassRecord = freeClassRecordService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(freeClassRecord));
    }

    /**
     * DELETE  /free-class-records/:id : delete the "id" freeClassRecord.
     *
     * @param id the id of the freeClassRecord to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/free-class-records/{id}")
    @Timed
    public ResponseEntity<Void> deleteFreeClassRecord(@PathVariable Long id) {
        log.debug("REST request to delete FreeClassRecord : {}", id);
        freeClassRecordService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
