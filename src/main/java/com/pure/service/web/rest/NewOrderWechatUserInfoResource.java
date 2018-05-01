package com.pure.service.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pure.service.domain.NewOrderWechatUserInfo;
import com.pure.service.service.NewOrderWechatUserInfoQueryService;
import com.pure.service.service.NewOrderWechatUserInfoService;
import com.pure.service.service.OpenIdService;
import com.pure.service.service.dto.NewOrderWechatUserInfoCriteria;
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
 * REST controller for managing NewOrderWechatUserInfo.
 */
@RestController
@RequestMapping("/api")
public class NewOrderWechatUserInfoResource {

    private final Logger log = LoggerFactory.getLogger(NewOrderWechatUserInfoResource.class);

    private static final String ENTITY_NAME = "newOrderWechatUserInfo";

    private final NewOrderWechatUserInfoService newOrderWechatUserInfoService;

    private final NewOrderWechatUserInfoQueryService newOrderWechatUserInfoQueryService;

    private final OpenIdService openIdService;

    public NewOrderWechatUserInfoResource(NewOrderWechatUserInfoService newOrderWechatUserInfoService,
                                          NewOrderWechatUserInfoQueryService newOrderWechatUserInfoQueryService,
                                          OpenIdService openIdService) {
        this.newOrderWechatUserInfoService = newOrderWechatUserInfoService;
        this.newOrderWechatUserInfoQueryService = newOrderWechatUserInfoQueryService;
        this.openIdService = openIdService;
    }

    /**
     * POST  /new-order-wechat-user-infos : Create a new newOrderWechatUserInfo.
     *
     * @param newOrderWechatUserInfo the newOrderWechatUserInfo to create
     * @return the ResponseEntity with status 201 (Created) and with body the new newOrderWechatUserInfo, or with status 400 (Bad Request) if the newOrderWechatUserInfo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/new-order-wechat-user-infos")
    @Timed
    public ResponseEntity<NewOrderWechatUserInfo> createNewOrderWechatUserInfo(@RequestBody NewOrderWechatUserInfo newOrderWechatUserInfo) throws URISyntaxException {
        log.debug("REST request to save NewOrderWechatUserInfo : {}", newOrderWechatUserInfo);
        if (newOrderWechatUserInfo.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new newOrderWechatUserInfo cannot already have an ID")).body(null);
        }

        String openId = openIdService.getTencentOpenId(newOrderWechatUserInfo.getCode());
        newOrderWechatUserInfo.setOpenId(openId);

        NewOrderWechatUserInfo result = newOrderWechatUserInfoService.save(newOrderWechatUserInfo);
        return ResponseEntity.created(new URI("/api/new-order-wechat-user-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /new-order-wechat-user-infos : Updates an existing newOrderWechatUserInfo.
     *
     * @param newOrderWechatUserInfo the newOrderWechatUserInfo to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated newOrderWechatUserInfo,
     * or with status 400 (Bad Request) if the newOrderWechatUserInfo is not valid,
     * or with status 500 (Internal Server Error) if the newOrderWechatUserInfo couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/new-order-wechat-user-infos")
    @Timed
    public ResponseEntity<NewOrderWechatUserInfo> updateNewOrderWechatUserInfo(@RequestBody NewOrderWechatUserInfo newOrderWechatUserInfo) throws URISyntaxException {
        log.debug("REST request to update NewOrderWechatUserInfo : {}", newOrderWechatUserInfo);
        if (newOrderWechatUserInfo.getId() == null) {
            return createNewOrderWechatUserInfo(newOrderWechatUserInfo);
        }
        NewOrderWechatUserInfo result = newOrderWechatUserInfoService.save(newOrderWechatUserInfo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, newOrderWechatUserInfo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /new-order-wechat-user-infos : get all the newOrderWechatUserInfos.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of newOrderWechatUserInfos in body
     */
    @GetMapping("/new-order-wechat-user-infos")
    @Timed
    public ResponseEntity<List<NewOrderWechatUserInfo>> getAllNewOrderWechatUserInfos(NewOrderWechatUserInfoCriteria criteria,@ApiParam Pageable pageable) {
        log.debug("REST request to get NewOrderWechatUserInfos by criteria: {}", criteria);
        Page<NewOrderWechatUserInfo> page = newOrderWechatUserInfoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/new-order-wechat-user-infos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /new-order-wechat-user-infos/:id : get the "id" newOrderWechatUserInfo.
     *
     * @param id the id of the newOrderWechatUserInfo to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the newOrderWechatUserInfo, or with status 404 (Not Found)
     */
    @GetMapping("/new-order-wechat-user-infos/{id}")
    @Timed
    public ResponseEntity<NewOrderWechatUserInfo> getNewOrderWechatUserInfo(@PathVariable Long id) {
        log.debug("REST request to get NewOrderWechatUserInfo : {}", id);
        NewOrderWechatUserInfo newOrderWechatUserInfo = newOrderWechatUserInfoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(newOrderWechatUserInfo));
    }

//    /**
//     * DELETE  /new-order-wechat-user-infos/:id : delete the "id" newOrderWechatUserInfo.
//     *
//     * @param id the id of the newOrderWechatUserInfo to delete
//     * @return the ResponseEntity with status 200 (OK)
//     */
//    @DeleteMapping("/new-order-wechat-user-infos/{id}")
//    @Timed
//    public ResponseEntity<Void> deleteNewOrderWechatUserInfo(@PathVariable Long id) {
//        log.debug("REST request to delete NewOrderWechatUserInfo : {}", id);
//        newOrderWechatUserInfoService.delete(id);
//        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
//    }
}
