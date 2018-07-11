package com.pure.service.service.impl;

import com.pure.service.service.ContractNatureService;
import com.pure.service.domain.ContractNature;
import com.pure.service.repository.ContractNatureRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing ContractNature.
 */
@Service
@Transactional
public class ContractNatureServiceImpl implements ContractNatureService{

    private final Logger log = LoggerFactory.getLogger(ContractNatureServiceImpl.class);

    private final ContractNatureRepository contractNatureRepository;

    public ContractNatureServiceImpl(ContractNatureRepository contractNatureRepository) {
        this.contractNatureRepository = contractNatureRepository;
    }

    /**
     * Save a contractNature.
     *
     * @param contractNature the entity to save
     * @return the persisted entity
     */
    @Override
    public ContractNature save(ContractNature contractNature) {
        log.debug("Request to save ContractNature : {}", contractNature);
        return contractNatureRepository.save(contractNature);
    }

    /**
     *  Get all the contractNatures.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ContractNature> findAll(Pageable pageable) {
        log.debug("Request to get all ContractNatures");
        return contractNatureRepository.findAll(pageable);
    }

    /**
     *  Get one contractNature by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ContractNature findOne(Long id) {
        log.debug("Request to get ContractNature : {}", id);
        return contractNatureRepository.findOne(id);
    }

    /**
     *  Delete the  contractNature by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ContractNature : {}", id);
        contractNatureRepository.delete(id);
    }
}
