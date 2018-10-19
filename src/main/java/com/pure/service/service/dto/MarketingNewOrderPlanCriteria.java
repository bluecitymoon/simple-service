package com.pure.service.service.dto;

import com.pure.service.region.AbstractQueryCriteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.InstantFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;




/**
 * Criteria class for the MarketingNewOrderPlan entity. This class is used in MarketingNewOrderPlanResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /marketing-new-order-plans?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MarketingNewOrderPlanCriteria extends AbstractQueryCriteria {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private StringFilter year;

    private StringFilter month;

    private IntegerFilter targetNumber;

    private IntegerFilter currentNumber;

    private BooleanFilter finished;

    private StringFilter createdBy;

    private InstantFilter createdDate;

    private StringFilter lastModifiedBy;

    private InstantFilter lastModifiedDate;

    private FloatFilter percentage;

    private LongFilter userId;

    public MarketingNewOrderPlanCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getYear() {
        return year;
    }

    public void setYear(StringFilter year) {
        this.year = year;
    }

    public StringFilter getMonth() {
        return month;
    }

    public void setMonth(StringFilter month) {
        this.month = month;
    }

    public IntegerFilter getTargetNumber() {
        return targetNumber;
    }

    public void setTargetNumber(IntegerFilter targetNumber) {
        this.targetNumber = targetNumber;
    }

    public IntegerFilter getCurrentNumber() {
        return currentNumber;
    }

    public void setCurrentNumber(IntegerFilter currentNumber) {
        this.currentNumber = currentNumber;
    }

    public BooleanFilter getFinished() {
        return finished;
    }

    public void setFinished(BooleanFilter finished) {
        this.finished = finished;
    }

    public StringFilter getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(StringFilter createdBy) {
        this.createdBy = createdBy;
    }

    public InstantFilter getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(InstantFilter createdDate) {
        this.createdDate = createdDate;
    }

    public StringFilter getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(StringFilter lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public InstantFilter getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(InstantFilter lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public FloatFilter getPercentage() {
        return percentage;
    }

    public void setPercentage(FloatFilter percentage) {
        this.percentage = percentage;
    }

    public LongFilter getUserId() {
        return userId;
    }

    public void setUserId(LongFilter userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "MarketingNewOrderPlanCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (year != null ? "year=" + year + ", " : "") +
                (month != null ? "month=" + month + ", " : "") +
                (targetNumber != null ? "targetNumber=" + targetNumber + ", " : "") +
                (currentNumber != null ? "currentNumber=" + currentNumber + ", " : "") +
                (finished != null ? "finished=" + finished + ", " : "") +
                (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
                (createdDate != null ? "createdDate=" + createdDate + ", " : "") +
                (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
                (lastModifiedDate != null ? "lastModifiedDate=" + lastModifiedDate + ", " : "") +
                (percentage != null ? "percentage=" + percentage + ", " : "") +
                (userId != null ? "userId=" + userId + ", " : "") +
            "}";
    }

}
