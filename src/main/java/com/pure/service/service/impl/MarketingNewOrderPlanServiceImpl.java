package com.pure.service.service.impl;

import com.pure.service.domain.FreeClassRecord;
import com.pure.service.domain.MarketingNewOrderPlan;
import com.pure.service.repository.MarketingNewOrderPlanRepository;
import com.pure.service.service.FreeClassRecordQueryService;
import com.pure.service.service.FreeClassRecordService;
import com.pure.service.service.MarketingNewOrderPlanService;
import com.pure.service.service.dto.FreeClassRecordCriteria;
import io.github.jhipster.service.filter.InstantFilter;
import io.github.jhipster.service.filter.LongFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.Instant;
import java.util.Collections;
import java.util.List;


/**
 * Service Implementation for managing MarketingNewOrderPlan.
 */
@Service
@Transactional
public class MarketingNewOrderPlanServiceImpl implements MarketingNewOrderPlanService{

    private final Logger log = LoggerFactory.getLogger(MarketingNewOrderPlanServiceImpl.class);

    private final MarketingNewOrderPlanRepository marketingNewOrderPlanRepository;
    private final FreeClassRecordService freeClassRecordService;
    private final FreeClassRecordQueryService freeClassRecordQueryService;

    public MarketingNewOrderPlanServiceImpl(MarketingNewOrderPlanRepository marketingNewOrderPlanRepository,
                                            FreeClassRecordService freeClassRecordService,
                                            FreeClassRecordQueryService freeClassRecordQueryService) {
        this.marketingNewOrderPlanRepository = marketingNewOrderPlanRepository;
        this.freeClassRecordService = freeClassRecordService;
        this.freeClassRecordQueryService = freeClassRecordQueryService;
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

    @Override
    public List<MarketingNewOrderPlan> generateReport(List<MarketingNewOrderPlan> marketingNewOrderPlans) {

        if (CollectionUtils.isEmpty(marketingNewOrderPlans)) {
            return Collections.EMPTY_LIST;
        }

        for (MarketingNewOrderPlan plan : marketingNewOrderPlans) {

            //2014-12-03T10:15:30.00Z
            String planMonth = plan.getMonth();
            if (planMonth.length() == 1 && !planMonth.startsWith("0")) {
                planMonth = "0" + planMonth;
            }
            String startInstantString = plan.getYear() + "-" + planMonth + "-01T00:00:01.00Z";
            Integer lastMonth = Integer.valueOf(plan.getMonth()) + 1;
            lastMonth = lastMonth == 13? 1 : lastMonth;

            String lastMonthString = "";
            if (lastMonth < 10) {
                lastMonthString =  "0" + lastMonth;
            } else {
                lastMonthString = "" + lastMonthString;
            }
            String endInstantString = plan.getYear() + "-" + lastMonthString + "-01T00:00:01.00Z";
            Instant start = Instant.parse(startInstantString);
            Instant end = Instant.parse(endInstantString);

            FreeClassRecordCriteria freeClassRecordCriteria = new FreeClassRecordCriteria();
            InstantFilter instantFilter = new InstantFilter();
            instantFilter.setGreaterThan(start);
            instantFilter.setLessThan(end);

            freeClassRecordCriteria.setCreatedDate(instantFilter);

            long agentId = plan.getUser().getId();
            LongFilter longFilter = new LongFilter();
            longFilter.setEquals(agentId);

            freeClassRecordCriteria.setAgentId(longFilter);

            log.debug("Refresh report: looking plans with start - end instant {} - {} with agent id {}", startInstantString, endInstantString, agentId );
            List<FreeClassRecord> freeClassRecords = freeClassRecordQueryService.findByCriteria(freeClassRecordCriteria);
            int currentNumber = freeClassRecords.size();
            plan.setCurrentNumber(currentNumber);
            log.debug("Refresh report: get {} results of new orders", currentNumber);

            boolean finished = currentNumber >= plan.getTargetNumber();
            plan.setFinished(finished);

            float percentage = (currentNumber / plan.getTargetNumber()) * 100;
            percentage = percentage > 100 ? 100: percentage;
            plan.setPercentage(percentage);

        }

        List<MarketingNewOrderPlan> updatedPlans = marketingNewOrderPlanRepository.save(marketingNewOrderPlans);

        return updatedPlans;
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
