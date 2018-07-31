package com.pure.service.service.impl;

import com.pure.service.service.ContractService;
import com.pure.service.domain.Contract;
import com.pure.service.repository.ContractRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing Contract.
 */
@Service
@Transactional
public class ContractServiceImpl implements ContractService{

    private final Logger log = LoggerFactory.getLogger(ContractServiceImpl.class);

    private final ContractRepository contractRepository;

    public ContractServiceImpl(ContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }

    /**
     * Save a contract.
     *
     * @param contract the entity to save
     * @return the persisted entity
     */
    @Override
    public Contract save(Contract contract) {
        log.debug("Request to save Contract : {}", contract);
        return contractRepository.save(contract);
    }

    /**
     *  Get all the contracts.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Contract> findAll(Pageable pageable) {
        log.debug("Request to get all Contracts");
        return contractRepository.findAll(pageable);
    }


    /**
     *  get all the contracts where Student is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<Contract> findAllWhereStudentIsNull() {
        log.debug("Request to get all contracts where Student is null");
        return StreamSupport
            .stream(contractRepository.findAll().spliterator(), false)
            .filter(contract -> contract.getStudent() == null)
            .collect(Collectors.toList());
    }

    /**
     *  Get one contract by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Contract findOne(Long id) {
        log.debug("Request to get Contract : {}", id);
        return contractRepository.findOne(id);
    }

    /**
     *  Delete the  contract by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Contract : {}", id);
        contractRepository.delete(id);
    }
}
