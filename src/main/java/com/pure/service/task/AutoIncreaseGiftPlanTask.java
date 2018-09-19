package com.pure.service.task;

import com.pure.service.domain.FreeClassPlan;
import com.pure.service.repository.FreeClassPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;

@Service
public class AutoIncreaseGiftPlanTask {

    @Autowired
    FreeClassPlanRepository freeClassPlanRepository;

    /**
     * 每天凌晨5点自动增加预约计划
     */
    @Scheduled(cron = "0 5 0 * * ?")
    private void autoIncreasePlan() {

        FreeClassPlan freeClassPlan = new FreeClassPlan();
        freeClassPlan.setLimitCount(6);

        Instant planDate = Instant.now().plus(Duration.ofDays(30));
        freeClassPlan.setPlanDate(planDate);

        freeClassPlanRepository.save(freeClassPlan);
    }
}
