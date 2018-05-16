package com.pure.service.service;

import com.pure.service.domain.MarketingQrcode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;

/**
 * Service Interface for managing MarketingQrcode.
 */
public interface MarketingQrcodeService {

    /**
     * Save a marketingQrcode.
     *
     * @param marketingQrcode the entity to save
     * @return the persisted entity
     */
    MarketingQrcode save(MarketingQrcode marketingQrcode);

    /**
     *  Get all the marketingQrcodes.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<MarketingQrcode> findAll(Pageable pageable);

    /**
     *  Get the "id" marketingQrcode.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    MarketingQrcode findOne(Long id);

    /**
     *  Delete the "id" marketingQrcode.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    MarketingQrcode generate(Long id) throws IOException;
}
