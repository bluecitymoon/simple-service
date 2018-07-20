package com.pure.service.service.dto;

import java.io.Serializable;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;






/**
 * Criteria class for the CustomerStatusReportDtl entity. This class is used in CustomerStatusReportDtlResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /customer-status-report-dtls?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CustomerStatusReportDtlCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private LongFilter userId;

    private StringFilter userName;

    private IntegerFilter ageTooSmallCount;

    private IntegerFilter errorInformation;

    private IntegerFilter noWillingCount;

    private IntegerFilter consideringCount;

    private IntegerFilter scheduledCount;

    private IntegerFilter dealedCount;

    private IntegerFilter newCreatedCount;

    private IntegerFilter totalCount;

    private DoubleFilter finishRate;

    public CustomerStatusReportDtlCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getUserId() {
        return userId;
    }

    public void setUserId(LongFilter userId) {
        this.userId = userId;
    }

    public StringFilter getUserName() {
        return userName;
    }

    public void setUserName(StringFilter userName) {
        this.userName = userName;
    }

    public IntegerFilter getAgeTooSmallCount() {
        return ageTooSmallCount;
    }

    public void setAgeTooSmallCount(IntegerFilter ageTooSmallCount) {
        this.ageTooSmallCount = ageTooSmallCount;
    }

    public IntegerFilter getErrorInformation() {
        return errorInformation;
    }

    public void setErrorInformation(IntegerFilter errorInformation) {
        this.errorInformation = errorInformation;
    }

    public IntegerFilter getNoWillingCount() {
        return noWillingCount;
    }

    public void setNoWillingCount(IntegerFilter noWillingCount) {
        this.noWillingCount = noWillingCount;
    }

    public IntegerFilter getConsideringCount() {
        return consideringCount;
    }

    public void setConsideringCount(IntegerFilter consideringCount) {
        this.consideringCount = consideringCount;
    }

    public IntegerFilter getScheduledCount() {
        return scheduledCount;
    }

    public void setScheduledCount(IntegerFilter scheduledCount) {
        this.scheduledCount = scheduledCount;
    }

    public IntegerFilter getDealedCount() {
        return dealedCount;
    }

    public void setDealedCount(IntegerFilter dealedCount) {
        this.dealedCount = dealedCount;
    }

    public IntegerFilter getNewCreatedCount() {
        return newCreatedCount;
    }

    public void setNewCreatedCount(IntegerFilter newCreatedCount) {
        this.newCreatedCount = newCreatedCount;
    }

    public IntegerFilter getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(IntegerFilter totalCount) {
        this.totalCount = totalCount;
    }

    public DoubleFilter getFinishRate() {
        return finishRate;
    }

    public void setFinishRate(DoubleFilter finishRate) {
        this.finishRate = finishRate;
    }

    @Override
    public String toString() {
        return "CustomerStatusReportDtlCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (userId != null ? "userId=" + userId + ", " : "") +
                (userName != null ? "userName=" + userName + ", " : "") +
                (ageTooSmallCount != null ? "ageTooSmallCount=" + ageTooSmallCount + ", " : "") +
                (errorInformation != null ? "errorInformation=" + errorInformation + ", " : "") +
                (noWillingCount != null ? "noWillingCount=" + noWillingCount + ", " : "") +
                (consideringCount != null ? "consideringCount=" + consideringCount + ", " : "") +
                (scheduledCount != null ? "scheduledCount=" + scheduledCount + ", " : "") +
                (dealedCount != null ? "dealedCount=" + dealedCount + ", " : "") +
                (newCreatedCount != null ? "newCreatedCount=" + newCreatedCount + ", " : "") +
                (totalCount != null ? "totalCount=" + totalCount + ", " : "") +
                (finishRate != null ? "finishRate=" + finishRate + ", " : "") +
            "}";
    }

}
