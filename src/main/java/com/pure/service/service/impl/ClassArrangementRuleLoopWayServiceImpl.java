package com.pure.service.service.impl;

import com.pure.service.service.ClassArrangementRuleLoopWayService;
import com.pure.service.domain.ClassArrangementRuleLoopWay;
import com.pure.service.repository.ClassArrangementRuleLoopWayRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing ClassArrangementRuleLoopWay.
 */
@Service
@Transactional
public class ClassArrangementRuleLoopWayServiceImpl implements ClassArrangementRuleLoopWayService{

    private final Logger log = LoggerFactory.getLogger(ClassArrangementRuleLoopWayServiceImpl.class);

    private final ClassArrangementRuleLoopWayRepository classArrangementRuleLoopWayRepository;

    public ClassArrangementRuleLoopWayServiceImpl(ClassArrangementRuleLoopWayRepository classArrangementRuleLoopWayRepository) {
        this.classArrangementRuleLoopWayRepository = classArrangementRuleLoopWayRepository;
    }

    /**
     * Save a classArrangementRuleLoopWay.
     *
     * @param classArrangementRuleLoopWay the entity to save
     * @return the persisted entity
     */
    @Override
    public ClassArrangementRuleLoopWay save(ClassArrangementRuleLoopWay classArrangementRuleLoopWay) {
        log.debug("Request to save ClassArrangementRuleLoopWay : {}", classArrangementRuleLoopWay);
        return classArrangementRuleLoopWayRepository.save(classArrangementRuleLoopWay);
    }

    /**
     *  Get all the classArrangementRuleLoopWays.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ClassArrangementRuleLoopWay> findAll(Pageable pageable) {
        log.debug("Request to get all ClassArrangementRuleLoopWays");
        return classArrangementRuleLoopWayRepository.findAll(pageable);
    }

    /**
     *  Get one classArrangementRuleLoopWay by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ClassArrangementRuleLoopWay findOne(Long id) {
        log.debug("Request to get ClassArrangementRuleLoopWay : {}", id);
        return classArrangementRuleLoopWayRepository.findOne(id);
    }

    /**
     *  Delete the  classArrangementRuleLoopWay by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ClassArrangementRuleLoopWay : {}", id);
        classArrangementRuleLoopWayRepository.delete(id);
    }
}
