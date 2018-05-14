package com.pure.service.service.impl;

import com.pure.service.service.SystemVariableService;
import com.pure.service.domain.SystemVariable;
import com.pure.service.repository.SystemVariableRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing SystemVariable.
 */
@Service
@Transactional
public class SystemVariableServiceImpl implements SystemVariableService{

    private final Logger log = LoggerFactory.getLogger(SystemVariableServiceImpl.class);

    private final SystemVariableRepository systemVariableRepository;

    public SystemVariableServiceImpl(SystemVariableRepository systemVariableRepository) {
        this.systemVariableRepository = systemVariableRepository;
    }

    /**
     * Save a systemVariable.
     *
     * @param systemVariable the entity to save
     * @return the persisted entity
     */
    @Override
    public SystemVariable save(SystemVariable systemVariable) {
        log.debug("Request to save SystemVariable : {}", systemVariable);
        return systemVariableRepository.save(systemVariable);
    }

    /**
     *  Get all the systemVariables.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SystemVariable> findAll(Pageable pageable) {
        log.debug("Request to get all SystemVariables");
        return systemVariableRepository.findAll(pageable);
    }

    /**
     *  Get one systemVariable by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public SystemVariable findOne(Long id) {
        log.debug("Request to get SystemVariable : {}", id);
        return systemVariableRepository.findOne(id);
    }

    /**
     *  Delete the  systemVariable by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SystemVariable : {}", id);
        systemVariableRepository.delete(id);
    }
}
