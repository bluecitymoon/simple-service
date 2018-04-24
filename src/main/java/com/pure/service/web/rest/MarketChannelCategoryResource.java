package com.pure.service.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pure.service.domain.MarketChannelCategory;

import com.pure.service.repository.MarketChannelCategoryRepository;
import com.pure.service.security.AuthoritiesConstants;
import com.pure.service.web.rest.util.HeaderUtil;
import com.pure.service.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing MarketChannelCategory.
 */
@RestController
@RequestMapping("/api")
public class MarketChannelCategoryResource {

    private final Logger log = LoggerFactory.getLogger(MarketChannelCategoryResource.class);

    private static final String ENTITY_NAME = "marketChannelCategory";

    private final MarketChannelCategoryRepository marketChannelCategoryRepository;

    public MarketChannelCategoryResource(MarketChannelCategoryRepository marketChannelCategoryRepository) {
        this.marketChannelCategoryRepository = marketChannelCategoryRepository;
    }

    /**
     * POST  /market-channel-categories : Create a new marketChannelCategory.
     *
     * @param marketChannelCategory the marketChannelCategory to create
     * @return the ResponseEntity with status 201 (Created) and with body the new marketChannelCategory, or with status 400 (Bad Request) if the marketChannelCategory has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/market-channel-categories")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.HEADMASTER})
    public ResponseEntity<MarketChannelCategory> createMarketChannelCategory(@RequestBody MarketChannelCategory marketChannelCategory) throws URISyntaxException {
        log.debug("REST request to save MarketChannelCategory : {}", marketChannelCategory);
        if (marketChannelCategory.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new marketChannelCategory cannot already have an ID")).body(null);
        }
        MarketChannelCategory result = marketChannelCategoryRepository.save(marketChannelCategory);
        return ResponseEntity.created(new URI("/api/market-channel-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /market-channel-categories : Updates an existing marketChannelCategory.
     *
     * @param marketChannelCategory the marketChannelCategory to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated marketChannelCategory,
     * or with status 400 (Bad Request) if the marketChannelCategory is not valid,
     * or with status 500 (Internal Server Error) if the marketChannelCategory couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/market-channel-categories")
    @Timed
    public ResponseEntity<MarketChannelCategory> updateMarketChannelCategory(@RequestBody MarketChannelCategory marketChannelCategory) throws URISyntaxException {
        log.debug("REST request to update MarketChannelCategory : {}", marketChannelCategory);
        if (marketChannelCategory.getId() == null) {
            return createMarketChannelCategory(marketChannelCategory);
        }
        MarketChannelCategory result = marketChannelCategoryRepository.save(marketChannelCategory);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, marketChannelCategory.getId().toString()))
            .body(result);
    }

    /**
     * GET  /market-channel-categories : get all the marketChannelCategories.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of marketChannelCategories in body
     */
    @GetMapping("/market-channel-categories")
    @Timed
    public ResponseEntity<List<MarketChannelCategory>> getAllMarketChannelCategories(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of MarketChannelCategories");
        Page<MarketChannelCategory> page = marketChannelCategoryRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/market-channel-categories");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /market-channel-categories/:id : get the "id" marketChannelCategory.
     *
     * @param id the id of the marketChannelCategory to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the marketChannelCategory, or with status 404 (Not Found)
     */
    @GetMapping("/market-channel-categories/{id}")
    @Timed
    public ResponseEntity<MarketChannelCategory> getMarketChannelCategory(@PathVariable Long id) {
        log.debug("REST request to get MarketChannelCategory : {}", id);
        MarketChannelCategory marketChannelCategory = marketChannelCategoryRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(marketChannelCategory));
    }

    /**
     * DELETE  /market-channel-categories/:id : delete the "id" marketChannelCategory.
     *
     * @param id the id of the marketChannelCategory to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/market-channel-categories/{id}")
    @Timed
    public ResponseEntity<Void> deleteMarketChannelCategory(@PathVariable Long id) {
        log.debug("REST request to delete MarketChannelCategory : {}", id);
        marketChannelCategoryRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
