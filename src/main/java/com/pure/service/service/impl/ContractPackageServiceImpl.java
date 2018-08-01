package com.pure.service.service.impl;

import com.pure.service.service.ContractPackageService;
import com.pure.service.domain.ContractPackage;
import com.pure.service.repository.ContractPackageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing ContractPackage.
 */
@Service
@Transactional
public class ContractPackageServiceImpl implements ContractPackageService{

    private final Logger log = LoggerFactory.getLogger(ContractPackageServiceImpl.class);

    private final ContractPackageRepository contractPackageRepository;

    public ContractPackageServiceImpl(ContractPackageRepository contractPackageRepository) {
        this.contractPackageRepository = contractPackageRepository;
    }

    /**
     * Save a contractPackage.
     *
     * @param contractPackage the entity to save
     * @return the persisted entity
     */
    @Override
    public ContractPackage save(ContractPackage contractPackage) {
        log.debug("Request to save ContractPackage : {}", contractPackage);
        return contractPackageRepository.save(contractPackage);
    }

    /**
     *  Get all the contractPackages.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ContractPackage> findAll(Pageable pageable) {
        log.debug("Request to get all ContractPackages");
        return contractPackageRepository.findAll(pageable);
    }

    /**
     *  Get one contractPackage by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ContractPackage findOne(Long id) {
        log.debug("Request to get ContractPackage : {}", id);
        return contractPackageRepository.findOne(id);
    }

    /**
     *  Delete the  contractPackage by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ContractPackage : {}", id);
        contractPackageRepository.delete(id);
    }
}
