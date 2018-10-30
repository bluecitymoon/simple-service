package com.pure.service.region;


import com.pure.service.domain.AbstractAuditingRegionEntity;
import com.pure.service.domain.AbstractRegionEntity;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RegionInsertAspect {

    private final Logger log = LoggerFactory.getLogger(RegionQueryAspect.class);

    @Before("@annotation(RegionBasedInsert)")
    public void setRegionIdParameter(JoinPoint joinPoint) {

        log.debug("Insert: set region id for " + joinPoint.getSignature().getName() + " class name " + joinPoint.getTarget().getClass().toGenericString());

        if (RegionIdStorage.getRegionIdContext() == null) {
            return;
        }
        Object firstArgument = joinPoint.getArgs()[0];
        if (firstArgument instanceof AbstractAuditingRegionEntity) {

            log.debug("The criteria is region based, setting region id.");


            Long regionId = Long.valueOf(RegionIdStorage.getRegionIdContext());
            ((AbstractAuditingRegionEntity) firstArgument).setRegionId(regionId);

        } else if (firstArgument instanceof AbstractRegionEntity) {

            Long regionId = Long.valueOf(RegionIdStorage.getRegionIdContext());
            ((AbstractRegionEntity) firstArgument).setRegionId(regionId);
        }
    }
}
