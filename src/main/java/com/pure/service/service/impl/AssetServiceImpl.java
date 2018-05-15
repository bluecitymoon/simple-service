package com.pure.service.service.impl;

import com.pure.service.service.AssetService;
import com.pure.service.domain.Asset;
import com.pure.service.repository.AssetRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;


/**
 * Service Implementation for managing Asset.
 */
@Service
@Transactional
public class AssetServiceImpl implements AssetService{

    private final Logger log = LoggerFactory.getLogger(AssetServiceImpl.class);

    private final AssetRepository assetRepository;

    public AssetServiceImpl(AssetRepository assetRepository) {
        this.assetRepository = assetRepository;
    }

    /**
     * Save a asset.
     *
     * @param asset the entity to save
     * @return the persisted entity
     */
    @Override
    public Asset save(Asset asset) {
        log.debug("Request to save Asset : {}", asset);
        return assetRepository.save(asset);
    }

    @Override
    public Asset saveAsset(MultipartFile file) throws IOException {

        String resourceId =  UUID.randomUUID().toString();
        String serverFileName = "/images/" + resourceId + "-" + file.getOriginalFilename();
        Path path = Paths.get(serverFileName);

        Files.write(path, file.getBytes());

        Asset asset = new Asset();

        asset.setResourceId(resourceId);
        asset.setName(file.getOriginalFilename());
        asset.setType(file.getContentType());
        asset.setComments("File size is " + file.getSize());
        asset.setFullPath("http://www.puzhenchina.com/images/" + resourceId + "-" + file.getOriginalFilename());

        return save(asset);
    }

    /**
     *  Get all the assets.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Asset> findAll(Pageable pageable) {
        log.debug("Request to get all Assets");
        return assetRepository.findAll(pageable);
    }

    /**
     *  Get one asset by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Asset findOne(Long id) {
        log.debug("Request to get Asset : {}", id);
        return assetRepository.findOne(id);
    }

    /**
     *  Delete the  asset by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Asset : {}", id);
        assetRepository.delete(id);
    }
}
