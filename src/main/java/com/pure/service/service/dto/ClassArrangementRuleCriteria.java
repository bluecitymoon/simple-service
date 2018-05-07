package com.pure.service.service.dto;

import java.io.Serializable;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

import io.github.jhipster.service.filter.InstantFilter;




/**
 * Criteria class for the ClassArrangementRule entity. This class is used in ClassArrangementRuleResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /class-arrangement-rules?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ClassArrangementRuleCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private InstantFilter estimateStartDate;

    private StringFilter estimateStartTime;

    private StringFilter estimateEndTime;

    private IntegerFilter maxLoopCount;

    private LongFilter targetClassId;

    private LongFilter loopWayId;

    public ClassArrangementRuleCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public InstantFilter getEstimateStartDate() {
        return estimateStartDate;
    }

    public void setEstimateStartDate(InstantFilter estimateStartDate) {
        this.estimateStartDate = estimateStartDate;
    }

    public StringFilter getEstimateStartTime() {
        return estimateStartTime;
    }

    public void setEstimateStartTime(StringFilter estimateStartTime) {
        this.estimateStartTime = estimateStartTime;
    }

    public StringFilter getEstimateEndTime() {
        return estimateEndTime;
    }

    public void setEstimateEndTime(StringFilter estimateEndTime) {
        this.estimateEndTime = estimateEndTime;
    }

    public IntegerFilter getMaxLoopCount() {
        return maxLoopCount;
    }

    public void setMaxLoopCount(IntegerFilter maxLoopCount) {
        this.maxLoopCount = maxLoopCount;
    }

    public LongFilter getTargetClassId() {
        return targetClassId;
    }

    public void setTargetClassId(LongFilter targetClassId) {
        this.targetClassId = targetClassId;
    }

    public LongFilter getLoopWayId() {
        return loopWayId;
    }

    public void setLoopWayId(LongFilter loopWayId) {
        this.loopWayId = loopWayId;
    }

    @Override
    public String toString() {
        return "ClassArrangementRuleCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (estimateStartDate != null ? "estimateStartDate=" + estimateStartDate + ", " : "") +
                (estimateStartTime != null ? "estimateStartTime=" + estimateStartTime + ", " : "") +
                (estimateEndTime != null ? "estimateEndTime=" + estimateEndTime + ", " : "") +
                (maxLoopCount != null ? "maxLoopCount=" + maxLoopCount + ", " : "") +
                (targetClassId != null ? "targetClassId=" + targetClassId + ", " : "") +
                (loopWayId != null ? "loopWayId=" + loopWayId + ", " : "") +
            "}";
    }

}
