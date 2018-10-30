package com.pure.service.region;

import io.github.jhipster.service.filter.LongFilter;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RegionQueryAspect {


    private final Logger log = LoggerFactory.getLogger(RegionQueryAspect.class);

    @Before("@annotation(RegionBasedQuery)")
    public void setRegionIdParameter(JoinPoint joinPoint) {

        log.debug("setRegionIdParameter for " + joinPoint.getSignature().getName() + " class name " + joinPoint.getTarget().getClass().toGenericString());

        Object firstArgument = joinPoint.getArgs()[0];
        if (firstArgument instanceof AbstractQueryCriteria) {

            log.debug("The criteria is region based, setting region id.");
            Long regionId;

            if (RegionIdStorage.getRegionIdContext() == null) {
                regionId = 1L;
            } else {
                regionId = Long.valueOf(RegionIdStorage.getRegionIdContext());
            }

            LongFilter regionIdFilter = new LongFilter();
            regionIdFilter.setEquals(regionId);

            ((AbstractQueryCriteria) firstArgument).setRegionId(regionIdFilter);
        }
    }
}
