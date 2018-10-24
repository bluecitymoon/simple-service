package com.pure.service.region;

import com.pure.service.domain.AbstractAuditingRegionEntity;
import com.pure.service.domain.AbstractRegionEntity;
import io.github.jhipster.service.filter.LongFilter;

public class RegionUtils {

    public static void setRegionIdFilter(AbstractQueryCriteria criteria) {

        Long regionId = Long.valueOf(RegionIdStorage.getRegionIdContext());
        LongFilter regionIdFilter = new LongFilter();
        regionIdFilter.setEquals(regionId);

        criteria.setRegionId(regionIdFilter);
    }

    public static void setRegionAbstractAuditingRegionEntity(AbstractAuditingRegionEntity entity) {

        Long regionId = Long.valueOf(RegionIdStorage.getRegionIdContext());
        entity.setRegionId(regionId);
    }

    public static void setRegionAbstractRegionEntity(AbstractRegionEntity entity) {
        Long regionId = Long.valueOf(RegionIdStorage.getRegionIdContext());
        entity.setRegionId(regionId);
    }

    public static Long getRegionIdForCurrentUser() {

        String regionId = RegionIdStorage.getRegionIdContext();
        if (org.apache.commons.lang3.math.NumberUtils.isCreatable(regionId)) {
            return Long.valueOf(regionId);
        }

        return null;
    }
}
