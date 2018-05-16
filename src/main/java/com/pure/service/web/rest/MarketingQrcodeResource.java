package com.pure.service.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pure.service.domain.MarketingQrcode;
import com.pure.service.service.MarketingQrcodeService;
import com.pure.service.web.rest.util.HeaderUtil;
import com.pure.service.web.rest.util.PaginationUtil;
import com.pure.service.service.dto.MarketingQrcodeCriteria;
import com.pure.service.service.MarketingQrcodeQueryService;
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

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing MarketingQrcode.
 */
@RestController
@RequestMapping("/api")
public class MarketingQrcodeResource {

    private final Logger log = LoggerFactory.getLogger(MarketingQrcodeResource.class);

    private static final String ENTITY_NAME = "marketingQrcode";

    private final MarketingQrcodeService marketingQrcodeService;

    private final MarketingQrcodeQueryService marketingQrcodeQueryService;

    public MarketingQrcodeResource(MarketingQrcodeService marketingQrcodeService, MarketingQrcodeQueryService marketingQrcodeQueryService) {
        this.marketingQrcodeService = marketingQrcodeService;
        this.marketingQrcodeQueryService = marketingQrcodeQueryService;
    }

    /**
     * POST  /marketing-qrcodes : Create a new marketingQrcode.
     *
     * @param marketingQrcode the marketingQrcode to create
     * @return the ResponseEntity with status 201 (Created) and with body the new marketingQrcode, or with status 400 (Bad Request) if the marketingQrcode has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/marketing-qrcodes")
    @Timed
    public ResponseEntity<MarketingQrcode> createMarketingQrcode(@RequestBody MarketingQrcode marketingQrcode) throws URISyntaxException {
        log.debug("REST request to save MarketingQrcode : {}", marketingQrcode);
        if (marketingQrcode.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new marketingQrcode cannot already have an ID")).body(null);
        }
        MarketingQrcode result = marketingQrcodeService.save(marketingQrcode);
        return ResponseEntity.created(new URI("/api/marketing-qrcodes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PostMapping("/marketing-qrcodes/generate/{id}")
    @Timed
    public ResponseEntity<MarketingQrcode> generate(@PathVariable Long id) throws URISyntaxException, IOException {
        log.debug("REST request to generate MarketingQrcode : {}", id);
        if (id != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "用户Id未指定")).body(null);
        }
        MarketingQrcode result = marketingQrcodeService.generate(id);

        return ResponseEntity.created(new URI("/api/marketing-qrcodes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /marketing-qrcodes : Updates an existing marketingQrcode.
     *
     * @param marketingQrcode the marketingQrcode to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated marketingQrcode,
     * or with status 400 (Bad Request) if the marketingQrcode is not valid,
     * or with status 500 (Internal Server Error) if the marketingQrcode couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/marketing-qrcodes")
    @Timed
    public ResponseEntity<MarketingQrcode> updateMarketingQrcode(@RequestBody MarketingQrcode marketingQrcode) throws URISyntaxException {
        log.debug("REST request to update MarketingQrcode : {}", marketingQrcode);
        if (marketingQrcode.getId() == null) {
            return createMarketingQrcode(marketingQrcode);
        }
        MarketingQrcode result = marketingQrcodeService.save(marketingQrcode);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, marketingQrcode.getId().toString()))
            .body(result);
    }

    /**
     * GET  /marketing-qrcodes : get all the marketingQrcodes.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of marketingQrcodes in body
     */
    @GetMapping("/marketing-qrcodes")
    @Timed
    public ResponseEntity<List<MarketingQrcode>> getAllMarketingQrcodes(MarketingQrcodeCriteria criteria,@ApiParam Pageable pageable) {
        log.debug("REST request to get MarketingQrcodes by criteria: {}", criteria);
        Page<MarketingQrcode> page = marketingQrcodeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/marketing-qrcodes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /marketing-qrcodes/:id : get the "id" marketingQrcode.
     *
     * @param id the id of the marketingQrcode to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the marketingQrcode, or with status 404 (Not Found)
     */
    @GetMapping("/marketing-qrcodes/{id}")
    @Timed
    public ResponseEntity<MarketingQrcode> getMarketingQrcode(@PathVariable Long id) {
        log.debug("REST request to get MarketingQrcode : {}", id);
        MarketingQrcode marketingQrcode = marketingQrcodeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(marketingQrcode));
    }

    /**
     * DELETE  /marketing-qrcodes/:id : delete the "id" marketingQrcode.
     *
     * @param id the id of the marketingQrcode to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/marketing-qrcodes/{id}")
    @Timed
    public ResponseEntity<Void> deleteMarketingQrcode(@PathVariable Long id) {
        log.debug("REST request to delete MarketingQrcode : {}", id);
        marketingQrcodeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
