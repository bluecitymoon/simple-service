package com.pure.service.service.impl;

import com.pure.service.service.ContractTemplateService;
import com.pure.service.domain.ContractTemplate;
import com.pure.service.repository.ContractTemplateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing ContractTemplate.
 */
@Service
@Transactional
public class ContractTemplateServiceImpl implements ContractTemplateService{

    private final Logger log = LoggerFactory.getLogger(ContractTemplateServiceImpl.class);

    private final ContractTemplateRepository contractTemplateRepository;

    public ContractTemplateServiceImpl(ContractTemplateRepository contractTemplateRepository) {
        this.contractTemplateRepository = contractTemplateRepository;
    }

    /**
     * Save a contractTemplate.
     *
     * @param contractTemplate the entity to save
     * @return the persisted entity
     */
    @Override
    public ContractTemplate save(ContractTemplate contractTemplate) {
        log.debug("Request to save ContractTemplate : {}", contractTemplate);
        return contractTemplateRepository.save(contractTemplate);
    }

    /**
     *  Get all the contractTemplates.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ContractTemplate> findAll(Pageable pageable) {
        log.debug("Request to get all ContractTemplates");
        return contractTemplateRepository.findAll(pageable);
    }

    /**
     *  Get one contractTemplate by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ContractTemplate findOne(Long id) {
        log.debug("Request to get ContractTemplate : {}", id);
        return contractTemplateRepository.findOne(id);
    }

    /**
     *  Delete the  contractTemplate by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ContractTemplate : {}", id);
        contractTemplateRepository.delete(id);
    }
}
