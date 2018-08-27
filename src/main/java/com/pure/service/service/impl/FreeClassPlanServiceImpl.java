package com.pure.service.service.impl;

import com.pure.service.service.FreeClassPlanService;
import com.pure.service.domain.FreeClassPlan;
import com.pure.service.repository.FreeClassPlanRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing FreeClassPlan.
 */
@Service
@Transactional
public class FreeClassPlanServiceImpl implements FreeClassPlanService{

    private final Logger log = LoggerFactory.getLogger(FreeClassPlanServiceImpl.class);

    private final FreeClassPlanRepository freeClassPlanRepository;

    public FreeClassPlanServiceImpl(FreeClassPlanRepository freeClassPlanRepository) {
        this.freeClassPlanRepository = freeClassPlanRepository;
    }

    /**
     * Save a freeClassPlan.
     *
     * @param freeClassPlan the entity to save
     * @return the persisted entity
     */
    @Override
    public FreeClassPlan save(FreeClassPlan freeClassPlan) {
        log.debug("Request to save FreeClassPlan : {}", freeClassPlan);
        return freeClassPlanRepository.save(freeClassPlan);
    }

    /**
     *  Get all the freeClassPlans.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<FreeClassPlan> findAll(Pageable pageable) {
        log.debug("Request to get all FreeClassPlans");
        return freeClassPlanRepository.findAll(pageable);
    }

    /**
     *  Get one freeClassPlan by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public FreeClassPlan findOne(Long id) {
        log.debug("Request to get FreeClassPlan : {}", id);
        return freeClassPlanRepository.findOne(id);
    }

    /**
     *  Delete the  freeClassPlan by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete FreeClassPlan : {}", id);
        freeClassPlanRepository.delete(id);
    }
}
