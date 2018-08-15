package com.pure.service.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pure.service.domain.CountNumber;
import com.pure.service.service.CountNumberService;
import com.pure.service.web.rest.util.HeaderUtil;
import com.pure.service.web.rest.util.PaginationUtil;
import com.pure.service.service.dto.CountNumberCriteria;
import com.pure.service.service.CountNumberQueryService;
import io.github.jhipster.service.filter.LongFilter;
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
 * REST controller for managing CountNumber.
 */
@RestController
@RequestMapping("/api")
public class CountNumberResource {

    private final Logger log = LoggerFactory.getLogger(CountNumberResource.class);

    private static final String ENTITY_NAME = "countNumber";

    private final CountNumberService countNumberService;

    private final CountNumberQueryService countNumberQueryService;

    public CountNumberResource(CountNumberService countNumberService, CountNumberQueryService countNumberQueryService) {
        this.countNumberService = countNumberService;
        this.countNumberQueryService = countNumberQueryService;
    }

    /**
     * POST  /count-numbers : Create a new countNumber.
     *
     * @param countNumber the countNumber to create
     * @return the ResponseEntity with status 201 (Created) and with body the new countNumber, or with status 400 (Bad Request) if the countNumber has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/count-numbers")
    @Timed
    public ResponseEntity<CountNumber> createCountNumber(@RequestBody CountNumber countNumber) throws URISyntaxException {
        log.debug("REST request to save CountNumber : {}", countNumber);
        if (countNumber.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new countNumber cannot already have an ID")).body(null);
        }
        CountNumber result = countNumberService.save(countNumber);
        return ResponseEntity.created(new URI("/api/count-numbers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /count-numbers : Updates an existing countNumber.
     *
     * @param countNumber the countNumber to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated countNumber,
     * or with status 400 (Bad Request) if the countNumber is not valid,
     * or with status 500 (Internal Server Error) if the countNumber couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/count-numbers")
    @Timed
    public ResponseEntity<CountNumber> updateCountNumber(@RequestBody CountNumber countNumber) throws URISyntaxException {
        log.debug("REST request to update CountNumber : {}", countNumber);
        if (countNumber.getId() == null) {
            return createCountNumber(countNumber);
        }
        CountNumber result = countNumberService.save(countNumber);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, countNumber.getId().toString()))
            .body(result);
    }

    /**
     * GET  /count-numbers : get all the countNumbers.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of countNumbers in body
     */
    @GetMapping("/count-numbers")
    @Timed
    public ResponseEntity<List<CountNumber>> getAllCountNumbers(CountNumberCriteria criteria,@ApiParam Pageable pageable) {
        log.debug("REST request to get CountNumbers by criteria: {}", criteria);
        Page<CountNumber> page = countNumberQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/count-numbers");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


    @GetMapping("/count-numbers/loop-way/{id}")
    @Timed
    public ResponseEntity<List<CountNumber>> getAllCountNumbersByLoopWay(@PathVariable("id") Long id) {

        CountNumberCriteria countNumberCriteria = new CountNumberCriteria();
        LongFilter longFilter = new LongFilter();
        longFilter.setEquals(id);

        countNumberCriteria.setLoopWayId(longFilter);

        log.debug("REST request to get CountNumbers by criteria: {}", countNumberCriteria);

        List<CountNumber> page = countNumberQueryService.findByCriteria(countNumberCriteria);

        return new ResponseEntity<>(page, null, HttpStatus.OK);
    }


    /**
     * GET  /count-numbers/:id : get the "id" countNumber.
     *
     * @param id the id of the countNumber to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the countNumber, or with status 404 (Not Found)
     */
    @GetMapping("/count-numbers/{id}")
    @Timed
    public ResponseEntity<CountNumber> getCountNumber(@PathVariable Long id) {
        log.debug("REST request to get CountNumber : {}", id);
        CountNumber countNumber = countNumberService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(countNumber));
    }

    /**
     * DELETE  /count-numbers/:id : delete the "id" countNumber.
     *
     * @param id the id of the countNumber to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/count-numbers/{id}")
    @Timed
    public ResponseEntity<Void> deleteCountNumber(@PathVariable Long id) {
        log.debug("REST request to delete CountNumber : {}", id);
        countNumberService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
