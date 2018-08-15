package com.pure.service.service.impl;

import com.pure.service.service.ClassArrangementStatusService;
import com.pure.service.domain.ClassArrangementStatus;
import com.pure.service.repository.ClassArrangementStatusRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing ClassArrangementStatus.
 */
@Service
@Transactional
public class ClassArrangementStatusServiceImpl implements ClassArrangementStatusService{

    private final Logger log = LoggerFactory.getLogger(ClassArrangementStatusServiceImpl.class);

    private final ClassArrangementStatusRepository classArrangementStatusRepository;

    public ClassArrangementStatusServiceImpl(ClassArrangementStatusRepository classArrangementStatusRepository) {
        this.classArrangementStatusRepository = classArrangementStatusRepository;
    }

    /**
     * Save a classArrangementStatus.
     *
     * @param classArrangementStatus the entity to save
     * @return the persisted entity
     */
    @Override
    public ClassArrangementStatus save(ClassArrangementStatus classArrangementStatus) {
        log.debug("Request to save ClassArrangementStatus : {}", classArrangementStatus);
        return classArrangementStatusRepository.save(classArrangementStatus);
    }

    /**
     *  Get all the classArrangementStatuses.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ClassArrangementStatus> findAll(Pageable pageable) {
        log.debug("Request to get all ClassArrangementStatuses");
        return classArrangementStatusRepository.findAll(pageable);
    }

    /**
     *  Get one classArrangementStatus by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ClassArrangementStatus findOne(Long id) {
        log.debug("Request to get ClassArrangementStatus : {}", id);
        return classArrangementStatusRepository.findOne(id);
    }

    /**
     *  Delete the  classArrangementStatus by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ClassArrangementStatus : {}", id);
        classArrangementStatusRepository.delete(id);
    }
}
