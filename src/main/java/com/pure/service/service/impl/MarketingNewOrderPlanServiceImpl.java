package com.pure.service.service.impl;

import com.pure.service.service.MarketingNewOrderPlanService;
import com.pure.service.domain.MarketingNewOrderPlan;
import com.pure.service.repository.MarketingNewOrderPlanRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing MarketingNewOrderPlan.
 */
@Service
@Transactional
public class MarketingNewOrderPlanServiceImpl implements MarketingNewOrderPlanService{

    private final Logger log = LoggerFactory.getLogger(MarketingNewOrderPlanServiceImpl.class);

    private final MarketingNewOrderPlanRepository marketingNewOrderPlanRepository;

    public MarketingNewOrderPlanServiceImpl(MarketingNewOrderPlanRepository marketingNewOrderPlanRepository) {
        this.marketingNewOrderPlanRepository = marketingNewOrderPlanRepository;
    }

    /**
     * Save a marketingNewOrderPlan.
     *
     * @param marketingNewOrderPlan the entity to save
     * @return the persisted entity
     */
    @Override
    public MarketingNewOrderPlan save(MarketingNewOrderPlan marketingNewOrderPlan) {
        log.debug("Request to save MarketingNewOrderPlan : {}", marketingNewOrderPlan);
        return marketingNewOrderPlanRepository.save(marketingNewOrderPlan);
    }

    /**
     *  Get all the marketingNewOrderPlans.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MarketingNewOrderPlan> findAll(Pageable pageable) {
        log.debug("Request to get all MarketingNewOrderPlans");
        return marketingNewOrderPlanRepository.findAll(pageable);
    }

    /**
     *  Get one marketingNewOrderPlan by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public MarketingNewOrderPlan findOne(Long id) {
        log.debug("Request to get MarketingNewOrderPlan : {}", id);
        return marketingNewOrderPlanRepository.findOne(id);
    }

    /**
     *  Delete the  marketingNewOrderPlan by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MarketingNewOrderPlan : {}", id);
        marketingNewOrderPlanRepository.delete(id);
    }
}
