package com.pure.service.service.dto;

import com.pure.service.region.AbstractQueryCriteria;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.InstantFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;




/**
 * Criteria class for the FreeClassPlan entity. This class is used in FreeClassPlanResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /free-class-plans?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class FreeClassPlanCriteria extends AbstractQueryCriteria {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private InstantFilter planDate;

    private IntegerFilter limitCount;

    private IntegerFilter actualCount;

    public FreeClassPlanCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public InstantFilter getPlanDate() {
        return planDate;
    }

    public void setPlanDate(InstantFilter planDate) {
        this.planDate = planDate;
    }

    public IntegerFilter getLimitCount() {
        return limitCount;
    }

    public void setLimitCount(IntegerFilter limitCount) {
        this.limitCount = limitCount;
    }

    public IntegerFilter getActualCount() {
        return actualCount;
    }

    public void setActualCount(IntegerFilter actualCount) {
        this.actualCount = actualCount;
    }

    @Override
    public String toString() {
        return "FreeClassPlanCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (planDate != null ? "planDate=" + planDate + ", " : "") +
                (limitCount != null ? "limitCount=" + limitCount + ", " : "") +
                (actualCount != null ? "actualCount=" + actualCount + ", " : "") +
            "}";
    }

}
