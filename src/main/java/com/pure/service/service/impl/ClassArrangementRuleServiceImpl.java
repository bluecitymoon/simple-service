package com.pure.service.service.impl;

import com.pure.service.service.ClassArrangementRuleService;
import com.pure.service.domain.ClassArrangementRule;
import com.pure.service.repository.ClassArrangementRuleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing ClassArrangementRule.
 */
@Service
@Transactional
public class ClassArrangementRuleServiceImpl implements ClassArrangementRuleService{

    private final Logger log = LoggerFactory.getLogger(ClassArrangementRuleServiceImpl.class);

    private final ClassArrangementRuleRepository classArrangementRuleRepository;

    public ClassArrangementRuleServiceImpl(ClassArrangementRuleRepository classArrangementRuleRepository) {
        this.classArrangementRuleRepository = classArrangementRuleRepository;
    }

    /**
     * Save a classArrangementRule.
     *
     * @param classArrangementRule the entity to save
     * @return the persisted entity
     */
    @Override
    public ClassArrangementRule save(ClassArrangementRule classArrangementRule) {
        log.debug("Request to save ClassArrangementRule : {}", classArrangementRule);
        return classArrangementRuleRepository.save(classArrangementRule);
    }

    /**
     *  Get all the classArrangementRules.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ClassArrangementRule> findAll(Pageable pageable) {
        log.debug("Request to get all ClassArrangementRules");
        return classArrangementRuleRepository.findAll(pageable);
    }

    /**
     *  Get one classArrangementRule by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ClassArrangementRule findOne(Long id) {
        log.debug("Request to get ClassArrangementRule : {}", id);
        return classArrangementRuleRepository.findOne(id);
    }

    /**
     *  Delete the  classArrangementRule by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ClassArrangementRule : {}", id);
        classArrangementRuleRepository.delete(id);
    }
}
