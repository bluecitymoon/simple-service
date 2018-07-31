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
 * Criteria class for the Collection entity. This class is used in CollectionResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /collections?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CollectionCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private FloatFilter moneyShouldCollected;

    private FloatFilter moneyCollected;

    private FloatFilter balance;

    private StringFilter sequenceNumber;

    private StringFilter payerName;

    private StringFilter createdBy;

    private InstantFilter createdDate;

    private StringFilter lastModifiedBy;

    private InstantFilter lastModifiedDate;

    private LongFilter financeCategoryId;

    private LongFilter paymentTypeId;

    private LongFilter statusId;

    public CollectionCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public FloatFilter getMoneyShouldCollected() {
        return moneyShouldCollected;
    }

    public void setMoneyShouldCollected(FloatFilter moneyShouldCollected) {
        this.moneyShouldCollected = moneyShouldCollected;
    }

    public FloatFilter getMoneyCollected() {
        return moneyCollected;
    }

    public void setMoneyCollected(FloatFilter moneyCollected) {
        this.moneyCollected = moneyCollected;
    }

    public FloatFilter getBalance() {
        return balance;
    }

    public void setBalance(FloatFilter balance) {
        this.balance = balance;
    }

    public StringFilter getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(StringFilter sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public StringFilter getPayerName() {
        return payerName;
    }

    public void setPayerName(StringFilter payerName) {
        this.payerName = payerName;
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

    public LongFilter getFinanceCategoryId() {
        return financeCategoryId;
    }

    public void setFinanceCategoryId(LongFilter financeCategoryId) {
        this.financeCategoryId = financeCategoryId;
    }

    public LongFilter getPaymentTypeId() {
        return paymentTypeId;
    }

    public void setPaymentTypeId(LongFilter paymentTypeId) {
        this.paymentTypeId = paymentTypeId;
    }

    public LongFilter getStatusId() {
        return statusId;
    }

    public void setStatusId(LongFilter statusId) {
        this.statusId = statusId;
    }

    @Override
    public String toString() {
        return "CollectionCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (moneyShouldCollected != null ? "moneyShouldCollected=" + moneyShouldCollected + ", " : "") +
                (moneyCollected != null ? "moneyCollected=" + moneyCollected + ", " : "") +
                (balance != null ? "balance=" + balance + ", " : "") +
                (sequenceNumber != null ? "sequenceNumber=" + sequenceNumber + ", " : "") +
                (payerName != null ? "payerName=" + payerName + ", " : "") +
                (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
                (createdDate != null ? "createdDate=" + createdDate + ", " : "") +
                (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
                (lastModifiedDate != null ? "lastModifiedDate=" + lastModifiedDate + ", " : "") +
                (financeCategoryId != null ? "financeCategoryId=" + financeCategoryId + ", " : "") +
                (paymentTypeId != null ? "paymentTypeId=" + paymentTypeId + ", " : "") +
                (statusId != null ? "statusId=" + statusId + ", " : "") +
            "}";
    }

}
