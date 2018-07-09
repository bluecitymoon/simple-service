package com.pure.service.service.dto;

import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.InstantFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

import java.io.Serializable;




/**
 * Criteria class for the Payment entity. This class is used in PaymentResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /payments?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PaymentCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private StringFilter projectName;

    private FloatFilter estimateAmount;

    private FloatFilter actualAmount;

    private FloatFilter paied;

    private FloatFilter unpaid;

    private InstantFilter paidDate;

    private StringFilter comments;

    private StringFilter createdBy;

    private InstantFilter createdDate;

    private StringFilter lastModifiedBy;

    private InstantFilter lastModifiedDate;

    private LongFilter paidUserId;

    private LongFilter paymentTypeId;

    private LongFilter financeCategoryId;

    public PaymentCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getProjectName() {
        return projectName;
    }

    public void setProjectName(StringFilter projectName) {
        this.projectName = projectName;
    }

    public FloatFilter getEstimateAmount() {
        return estimateAmount;
    }

    public void setEstimateAmount(FloatFilter estimateAmount) {
        this.estimateAmount = estimateAmount;
    }

    public FloatFilter getActualAmount() {
        return actualAmount;
    }

    public void setActualAmount(FloatFilter actualAmount) {
        this.actualAmount = actualAmount;
    }

    public FloatFilter getPaied() {
        return paied;
    }

    public void setPaied(FloatFilter paied) {
        this.paied = paied;
    }

    public FloatFilter getUnpaid() {
        return unpaid;
    }

    public void setUnpaid(FloatFilter unpaid) {
        this.unpaid = unpaid;
    }

    public InstantFilter getPaidDate() {
        return paidDate;
    }

    public void setPaidDate(InstantFilter paidDate) {
        this.paidDate = paidDate;
    }

    public StringFilter getComments() {
        return comments;
    }

    public void setComments(StringFilter comments) {
        this.comments = comments;
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

    public LongFilter getPaidUserId() {
        return paidUserId;
    }

    public void setPaidUserId(LongFilter paidUserId) {
        this.paidUserId = paidUserId;
    }

    public LongFilter getPaymentTypeId() {
        return paymentTypeId;
    }

    public void setPaymentTypeId(LongFilter paymentTypeId) {
        this.paymentTypeId = paymentTypeId;
    }

    public LongFilter getFinanceCategoryId() {
        return financeCategoryId;
    }

    public void setFinanceCategoryId(LongFilter financeCategoryId) {
        this.financeCategoryId = financeCategoryId;
    }

    @Override
    public String toString() {
        return "PaymentCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (projectName != null ? "projectName=" + projectName + ", " : "") +
                (estimateAmount != null ? "estimateAmount=" + estimateAmount + ", " : "") +
                (actualAmount != null ? "actualAmount=" + actualAmount + ", " : "") +
                (paied != null ? "paied=" + paied + ", " : "") +
                (unpaid != null ? "unpaid=" + unpaid + ", " : "") +
                (paidDate != null ? "paidDate=" + paidDate + ", " : "") +
                (comments != null ? "comments=" + comments + ", " : "") +
                (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
                (createdDate != null ? "createdDate=" + createdDate + ", " : "") +
                (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
                (lastModifiedDate != null ? "lastModifiedDate=" + lastModifiedDate + ", " : "") +
                (paidUserId != null ? "paidUserId=" + paidUserId + ", " : "") +
                (paymentTypeId != null ? "paymentTypeId=" + paymentTypeId + ", " : "") +
                (financeCategoryId != null ? "financeCategoryId=" + financeCategoryId + ", " : "") +
            "}";
    }

}
