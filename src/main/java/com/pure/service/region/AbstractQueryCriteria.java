package com.pure.service.region;

import io.github.jhipster.service.filter.LongFilter;

import java.io.Serializable;

public class AbstractQueryCriteria implements Serializable {

    private LongFilter regionId;

    public LongFilter getRegionId() {
        return regionId;
    }

    public void setRegionId(LongFilter regionId) {
        this.regionId = regionId;
    }
}
