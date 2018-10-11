package com.pure.service.service.impl;

import com.pure.service.service.ContractPackageTypeService;
import com.pure.service.domain.ContractPackageType;
import com.pure.service.repository.ContractPackageTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing ContractPackageType.
 */
@Service
@Transactional
public class ContractPackageTypeServiceImpl implements ContractPackageTypeService{

    private final Logger log = LoggerFactory.getLogger(ContractPackageTypeServiceImpl.class);

    private final ContractPackageTypeRepository contractPackageTypeRepository;

    public ContractPackageTypeServiceImpl(ContractPackageTypeRepository contractPackageTypeRepository) {
        this.contractPackageTypeRepository = contractPackageTypeRepository;
    }

    /**
     * Save a contractPackageType.
     *
     * @param contractPackageType the entity to save
     * @return the persisted entity
     */
    @Override
    public ContractPackageType save(ContractPackageType contractPackageType) {
        log.debug("Request to save ContractPackageType : {}", contractPackageType);
        return contractPackageTypeRepository.save(contractPackageType);
    }

    /**
     *  Get all the contractPackageTypes.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ContractPackageType> findAll(Pageable pageable) {
        log.debug("Request to get all ContractPackageTypes");
        return contractPackageTypeRepository.findAll(pageable);
    }

    /**
     *  Get one contractPackageType by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ContractPackageType findOne(Long id) {
        log.debug("Request to get ContractPackageType : {}", id);
        return contractPackageTypeRepository.findOne(id);
    }

    /**
     *  Delete the  contractPackageType by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ContractPackageType : {}", id);
        contractPackageTypeRepository.delete(id);
    }
}
