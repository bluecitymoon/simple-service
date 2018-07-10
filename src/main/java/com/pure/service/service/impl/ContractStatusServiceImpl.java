package com.pure.service.service.impl;

import com.pure.service.service.ContractStatusService;
import com.pure.service.domain.ContractStatus;
import com.pure.service.repository.ContractStatusRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing ContractStatus.
 */
@Service
@Transactional
public class ContractStatusServiceImpl implements ContractStatusService{

    private final Logger log = LoggerFactory.getLogger(ContractStatusServiceImpl.class);

    private final ContractStatusRepository contractStatusRepository;

    public ContractStatusServiceImpl(ContractStatusRepository contractStatusRepository) {
        this.contractStatusRepository = contractStatusRepository;
    }

    /**
     * Save a contractStatus.
     *
     * @param contractStatus the entity to save
     * @return the persisted entity
     */
    @Override
    public ContractStatus save(ContractStatus contractStatus) {
        log.debug("Request to save ContractStatus : {}", contractStatus);
        return contractStatusRepository.save(contractStatus);
    }

    /**
     *  Get all the contractStatuses.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ContractStatus> findAll(Pageable pageable) {
        log.debug("Request to get all ContractStatuses");
        return contractStatusRepository.findAll(pageable);
    }

    /**
     *  Get one contractStatus by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ContractStatus findOne(Long id) {
        log.debug("Request to get ContractStatus : {}", id);
        return contractStatusRepository.findOne(id);
    }

    /**
     *  Delete the  contractStatus by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ContractStatus : {}", id);
        contractStatusRepository.delete(id);
    }
}
