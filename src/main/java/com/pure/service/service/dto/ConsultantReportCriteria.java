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
 * Criteria class for the ConsultantReport entity. This class is used in ConsultantReportResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /consultant-reports?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ConsultantReportCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private IntegerFilter weekName;

    private InstantFilter weekFromDate;

    private InstantFilter weekEndDate;

    private IntegerFilter visitedCount;

    private FloatFilter dealedMoneyAmount;

    public ConsultantReportCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IntegerFilter getWeekName() {
        return weekName;
    }

    public void setWeekName(IntegerFilter weekName) {
        this.weekName = weekName;
    }

    public InstantFilter getWeekFromDate() {
        return weekFromDate;
    }

    public void setWeekFromDate(InstantFilter weekFromDate) {
        this.weekFromDate = weekFromDate;
    }

    public InstantFilter getWeekEndDate() {
        return weekEndDate;
    }

    public void setWeekEndDate(InstantFilter weekEndDate) {
        this.weekEndDate = weekEndDate;
    }

    public IntegerFilter getVisitedCount() {
        return visitedCount;
    }

    public void setVisitedCount(IntegerFilter visitedCount) {
        this.visitedCount = visitedCount;
    }

    public FloatFilter getDealedMoneyAmount() {
        return dealedMoneyAmount;
    }

    public void setDealedMoneyAmount(FloatFilter dealedMoneyAmount) {
        this.dealedMoneyAmount = dealedMoneyAmount;
    }

    @Override
    public String toString() {
        return "ConsultantReportCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (weekName != null ? "weekName=" + weekName + ", " : "") +
                (weekFromDate != null ? "weekFromDate=" + weekFromDate + ", " : "") +
                (weekEndDate != null ? "weekEndDate=" + weekEndDate + ", " : "") +
                (visitedCount != null ? "visitedCount=" + visitedCount + ", " : "") +
                (dealedMoneyAmount != null ? "dealedMoneyAmount=" + dealedMoneyAmount + ", " : "") +
            "}";
    }

}
