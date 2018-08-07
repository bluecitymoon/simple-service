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
 * Criteria class for the CustomerCollectionLog entity. This class is used in CustomerCollectionLogResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /customer-collection-logs?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CustomerCollectionLogCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private StringFilter serialNumber;

    private FloatFilter moneyShouldCollected;

    private FloatFilter moneyCollected;

    private FloatFilter balance;

    private StringFilter createdBy;

    private InstantFilter createdDate;

    private StringFilter lastModifiedBy;

    private InstantFilter lastModifiedDate;

    private LongFilter collectionId;

    private LongFilter customerId;

    public CustomerCollectionLogCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(StringFilter serialNumber) {
        this.serialNumber = serialNumber;
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

    public LongFilter getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(LongFilter collectionId) {
        this.collectionId = collectionId;
    }

    public LongFilter getCustomerId() {
        return customerId;
    }

    public void setCustomerId(LongFilter customerId) {
        this.customerId = customerId;
    }

    @Override
    public String toString() {
        return "CustomerCollectionLogCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (serialNumber != null ? "serialNumber=" + serialNumber + ", " : "") +
                (moneyShouldCollected != null ? "moneyShouldCollected=" + moneyShouldCollected + ", " : "") +
                (moneyCollected != null ? "moneyCollected=" + moneyCollected + ", " : "") +
                (balance != null ? "balance=" + balance + ", " : "") +
                (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
                (createdDate != null ? "createdDate=" + createdDate + ", " : "") +
                (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
                (lastModifiedDate != null ? "lastModifiedDate=" + lastModifiedDate + ", " : "") +
                (collectionId != null ? "collectionId=" + collectionId + ", " : "") +
                (customerId != null ? "customerId=" + customerId + ", " : "") +
            "}";
    }

}
