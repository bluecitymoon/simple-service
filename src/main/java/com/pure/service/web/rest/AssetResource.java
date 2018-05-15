package com.pure.service.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pure.service.domain.Asset;
import com.pure.service.service.AssetQueryService;
import com.pure.service.service.AssetService;
import com.pure.service.service.dto.AssetCriteria;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * REST controller for managing Asset.
 */
@RestController
@RequestMapping("/api")
public class AssetResource {

    private final Logger log = LoggerFactory.getLogger(AssetResource.class);

    private static final String ENTITY_NAME = "asset";

    private final AssetService assetService;

    private final AssetQueryService assetQueryService;

    public AssetResource(AssetService assetService, AssetQueryService assetQueryService) {
        this.assetService = assetService;
        this.assetQueryService = assetQueryService;
    }

    /**
     * POST  /assets : Create a new asset.
     *
     * @param asset the asset to create
     * @return the ResponseEntity with status 201 (Created) and with body the new asset, or with status 400 (Bad Request) if the asset has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/assets")
    @Timed
    public ResponseEntity<Asset> createAsset(@RequestBody Asset asset) throws URISyntaxException {
        log.debug("REST request to save Asset : {}", asset);
        if (asset.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new asset cannot already have an ID")).body(null);
        }
        Asset result = assetService.save(asset);
        return ResponseEntity.created(new URI("/api/assets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * Upload a file, store the resource
     * @param file
     * @return
     * @throws URISyntaxException
     */
    @PostMapping("/assets/upload")
    @Timed
    public ResponseEntity<Asset> uploadAssert(@RequestParam("file") MultipartFile file) throws URISyntaxException, IOException {

        if (file.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        String resourceId =  UUID.randomUUID().toString();
        String serverFileName = "/images/" + resourceId + "-" + file.getOriginalFilename();
        Path path = Paths.get(serverFileName);
//
//        Files.createFile(path);

        byte[] bytes = file.getBytes();
        Files.write(path, bytes);

        Asset asset = new Asset();

        asset.setResourceId(resourceId);
        asset.setName(file.getOriginalFilename());
        asset.setType(file.getContentType());
        asset.setComments("File size is " + file.getSize());

        Asset result = assetService.save(asset);
        return ResponseEntity.created(new URI("/api/assets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /assets : Updates an existing asset.
     *
     * @param asset the asset to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated asset,
     * or with status 400 (Bad Request) if the asset is not valid,
     * or with status 500 (Internal Server Error) if the asset couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/assets")
    @Timed
    public ResponseEntity<Asset> updateAsset(@RequestBody Asset asset) throws URISyntaxException {
        log.debug("REST request to update Asset : {}", asset);
        if (asset.getId() == null) {
            return createAsset(asset);
        }
        Asset result = assetService.save(asset);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, asset.getId().toString()))
            .body(result);
    }

    /**
     * GET  /assets : get all the assets.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of assets in body
     */
    @GetMapping("/assets")
    @Timed
    public ResponseEntity<List<Asset>> getAllAssets(AssetCriteria criteria,@ApiParam Pageable pageable) {
        log.debug("REST request to get Assets by criteria: {}", criteria);
        Page<Asset> page = assetQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/assets");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /assets/:id : get the "id" asset.
     *
     * @param id the id of the asset to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the asset, or with status 404 (Not Found)
     */
    @GetMapping("/assets/{id}")
    @Timed
    public ResponseEntity<Asset> getAsset(@PathVariable Long id) {
        log.debug("REST request to get Asset : {}", id);
        Asset asset = assetService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(asset));
    }

    /**
     * DELETE  /assets/:id : delete the "id" asset.
     *
     * @param id the id of the asset to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/assets/{id}")
    @Timed
    public ResponseEntity<Void> deleteAsset(@PathVariable Long id) {
        log.debug("REST request to delete Asset : {}", id);
        assetService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
