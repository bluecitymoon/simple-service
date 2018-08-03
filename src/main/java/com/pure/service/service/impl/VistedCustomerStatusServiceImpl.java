package com.pure.service.service.impl;

import com.pure.service.service.VistedCustomerStatusService;
import com.pure.service.domain.VistedCustomerStatus;
import com.pure.service.repository.VistedCustomerStatusRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing VistedCustomerStatus.
 */
@Service
@Transactional
public class VistedCustomerStatusServiceImpl implements VistedCustomerStatusService{

    private final Logger log = LoggerFactory.getLogger(VistedCustomerStatusServiceImpl.class);

    private final VistedCustomerStatusRepository vistedCustomerStatusRepository;

    public VistedCustomerStatusServiceImpl(VistedCustomerStatusRepository vistedCustomerStatusRepository) {
        this.vistedCustomerStatusRepository = vistedCustomerStatusRepository;
    }

    /**
     * Save a vistedCustomerStatus.
     *
     * @param vistedCustomerStatus the entity to save
     * @return the persisted entity
     */
    @Override
    public VistedCustomerStatus save(VistedCustomerStatus vistedCustomerStatus) {
        log.debug("Request to save VistedCustomerStatus : {}", vistedCustomerStatus);
        return vistedCustomerStatusRepository.save(vistedCustomerStatus);
    }

    /**
     *  Get all the vistedCustomerStatuses.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<VistedCustomerStatus> findAll(Pageable pageable) {
        log.debug("Request to get all VistedCustomerStatuses");
        return vistedCustomerStatusRepository.findAll(pageable);
    }

    /**
     *  Get one vistedCustomerStatus by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public VistedCustomerStatus findOne(Long id) {
        log.debug("Request to get VistedCustomerStatus : {}", id);
        return vistedCustomerStatusRepository.findOne(id);
    }

    /**
     *  Delete the  vistedCustomerStatus by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete VistedCustomerStatus : {}", id);
        vistedCustomerStatusRepository.delete(id);
    }
}
