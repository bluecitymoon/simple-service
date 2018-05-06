package com.pure.service.service.impl;

import com.pure.service.service.ClassStatusService;
import com.pure.service.domain.ClassStatus;
import com.pure.service.repository.ClassStatusRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing ClassStatus.
 */
@Service
@Transactional
public class ClassStatusServiceImpl implements ClassStatusService{

    private final Logger log = LoggerFactory.getLogger(ClassStatusServiceImpl.class);

    private final ClassStatusRepository classStatusRepository;

    public ClassStatusServiceImpl(ClassStatusRepository classStatusRepository) {
        this.classStatusRepository = classStatusRepository;
    }

    /**
     * Save a classStatus.
     *
     * @param classStatus the entity to save
     * @return the persisted entity
     */
    @Override
    public ClassStatus save(ClassStatus classStatus) {
        log.debug("Request to save ClassStatus : {}", classStatus);
        return classStatusRepository.save(classStatus);
    }

    /**
     *  Get all the classStatuses.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ClassStatus> findAll(Pageable pageable) {
        log.debug("Request to get all ClassStatuses");
        return classStatusRepository.findAll(pageable);
    }

    /**
     *  Get one classStatus by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ClassStatus findOne(Long id) {
        log.debug("Request to get ClassStatus : {}", id);
        return classStatusRepository.findOne(id);
    }

    /**
     *  Delete the  classStatus by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ClassStatus : {}", id);
        classStatusRepository.delete(id);
    }
}
