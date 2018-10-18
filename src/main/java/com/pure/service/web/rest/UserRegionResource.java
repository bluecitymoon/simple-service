package com.pure.service.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pure.service.domain.User;
import com.pure.service.domain.UserRegion;
import com.pure.service.service.UserRegionService;
import com.pure.service.web.rest.util.HeaderUtil;
import com.pure.service.web.rest.util.PaginationUtil;
import com.pure.service.service.dto.UserRegionCriteria;
import com.pure.service.service.UserRegionQueryService;
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
 * REST controller for managing UserRegion.
 */
@RestController
@RequestMapping("/api")
public class UserRegionResource {

    private final Logger log = LoggerFactory.getLogger(UserRegionResource.class);

    private static final String ENTITY_NAME = "userRegion";

    private final UserRegionService userRegionService;

    private final UserRegionQueryService userRegionQueryService;

    public UserRegionResource(UserRegionService userRegionService, UserRegionQueryService userRegionQueryService) {
        this.userRegionService = userRegionService;
        this.userRegionQueryService = userRegionQueryService;
    }

    /**
     * POST  /user-regions : Create a new userRegion.
     *
     * @param userRegion the userRegion to create
     * @return the ResponseEntity with status 201 (Created) and with body the new userRegion, or with status 400 (Bad Request) if the userRegion has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/user-regions")
    @Timed
    public ResponseEntity<UserRegion> createUserRegion(@RequestBody UserRegion userRegion) throws URISyntaxException {
        log.debug("REST request to save UserRegion : {}", userRegion);
        if (userRegion.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new userRegion cannot already have an ID")).body(null);
        }
        UserRegion result = userRegionService.save(userRegion);
        return ResponseEntity.created(new URI("/api/user-regions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /user-regions : Updates an existing userRegion.
     *
     * @param userRegion the userRegion to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated userRegion,
     * or with status 400 (Bad Request) if the userRegion is not valid,
     * or with status 500 (Internal Server Error) if the userRegion couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/user-regions")
    @Timed
    public ResponseEntity<UserRegion> updateUserRegion(@RequestBody UserRegion userRegion) throws URISyntaxException {
        log.debug("REST request to update UserRegion : {}", userRegion);
        if (userRegion.getId() == null) {
            return createUserRegion(userRegion);
        }
        UserRegion result = userRegionService.save(userRegion);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, userRegion.getId().toString()))
            .body(result);
    }

    /**
     * GET  /user-regions : get all the userRegions.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of userRegions in body
     */
    @GetMapping("/user-regions")
    @Timed
    public ResponseEntity<List<UserRegion>> getAllUserRegions(UserRegionCriteria criteria,@ApiParam Pageable pageable) {
        log.debug("REST request to get UserRegions by criteria: {}", criteria);
        Page<UserRegion> page = userRegionQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/user-regions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/user-regions/users/{regionId}")
    @Timed
    public ResponseEntity<List<User>> getAllUsersInRegion(@PathVariable("regionId") Long regionId) {
        return new ResponseEntity<>(userRegionService.getAllUsersInRegion(regionId), null, HttpStatus.OK);
    }


    /**
     * GET  /user-regions/:id : get the "id" userRegion.
     *
     * @param id the id of the userRegion to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the userRegion, or with status 404 (Not Found)
     */
    @GetMapping("/user-regions/{id}")
    @Timed
    public ResponseEntity<UserRegion> getUserRegion(@PathVariable Long id) {
        log.debug("REST request to get UserRegion : {}", id);
        UserRegion userRegion = userRegionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(userRegion));
    }

    /**
     * DELETE  /user-regions/:id : delete the "id" userRegion.
     *
     * @param id the id of the userRegion to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/user-regions/{id}")
    @Timed
    public ResponseEntity<Void> deleteUserRegion(@PathVariable Long id) {
        log.debug("REST request to delete UserRegion : {}", id);
        userRegionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @DeleteMapping("/user-regions/user/{userId}/region/{regionId}")
    @Timed
    public ResponseEntity<Void> deleteUserRegion(@PathVariable Long userId, @PathVariable Long regionId) {

        Long deletedUserRegionId = userRegionService.removeUserRegion(userId, regionId);

        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, deletedUserRegionId.toString())).build();
    }
}
