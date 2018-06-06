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
 * Criteria class for the CustomerCard entity. This class is used in CustomerCardResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /customer-cards?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CustomerCardCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private StringFilter number;

    private StringFilter serialNumber;

    private InstantFilter signDate;

    private InstantFilter startDate;

    private InstantFilter endDate;

    private FloatFilter moneyCollected;

    private FloatFilter balance;

    private StringFilter createdBy;

    private InstantFilter createdDate;

    private StringFilter lastModifiedBy;

    private InstantFilter lastModifiedDate;

    private FloatFilter totalMoneyAmount;

    private FloatFilter promotionAmount;

    private IntegerFilter classCount;

    private IntegerFilter totalMinutes;

    private FloatFilter specialPromotionAmount;

    private LongFilter customerId;

    private LongFilter customerCardTypeId;

    private LongFilter courseId;

    public CustomerCardCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getNumber() {
        return number;
    }

    public void setNumber(StringFilter number) {
        this.number = number;
    }

    public StringFilter getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(StringFilter serialNumber) {
        this.serialNumber = serialNumber;
    }

    public InstantFilter getSignDate() {
        return signDate;
    }

    public void setSignDate(InstantFilter signDate) {
        this.signDate = signDate;
    }

    public InstantFilter getStartDate() {
        return startDate;
    }

    public void setStartDate(InstantFilter startDate) {
        this.startDate = startDate;
    }

    public InstantFilter getEndDate() {
        return endDate;
    }

    public void setEndDate(InstantFilter endDate) {
        this.endDate = endDate;
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

    public FloatFilter getTotalMoneyAmount() {
        return totalMoneyAmount;
    }

    public void setTotalMoneyAmount(FloatFilter totalMoneyAmount) {
        this.totalMoneyAmount = totalMoneyAmount;
    }

    public FloatFilter getPromotionAmount() {
        return promotionAmount;
    }

    public void setPromotionAmount(FloatFilter promotionAmount) {
        this.promotionAmount = promotionAmount;
    }

    public IntegerFilter getClassCount() {
        return classCount;
    }

    public void setClassCount(IntegerFilter classCount) {
        this.classCount = classCount;
    }

    public IntegerFilter getTotalMinutes() {
        return totalMinutes;
    }

    public void setTotalMinutes(IntegerFilter totalMinutes) {
        this.totalMinutes = totalMinutes;
    }

    public FloatFilter getSpecialPromotionAmount() {
        return specialPromotionAmount;
    }

    public void setSpecialPromotionAmount(FloatFilter specialPromotionAmount) {
        this.specialPromotionAmount = specialPromotionAmount;
    }

    public LongFilter getCustomerId() {
        return customerId;
    }

    public void setCustomerId(LongFilter customerId) {
        this.customerId = customerId;
    }

    public LongFilter getCustomerCardTypeId() {
        return customerCardTypeId;
    }

    public void setCustomerCardTypeId(LongFilter customerCardTypeId) {
        this.customerCardTypeId = customerCardTypeId;
    }

    public LongFilter getCourseId() {
        return courseId;
    }

    public void setCourseId(LongFilter courseId) {
        this.courseId = courseId;
    }

    @Override
    public String toString() {
        return "CustomerCardCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (number != null ? "number=" + number + ", " : "") +
                (serialNumber != null ? "serialNumber=" + serialNumber + ", " : "") +
                (signDate != null ? "signDate=" + signDate + ", " : "") +
                (startDate != null ? "startDate=" + startDate + ", " : "") +
                (endDate != null ? "endDate=" + endDate + ", " : "") +
                (moneyCollected != null ? "moneyCollected=" + moneyCollected + ", " : "") +
                (balance != null ? "balance=" + balance + ", " : "") +
                (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
                (createdDate != null ? "createdDate=" + createdDate + ", " : "") +
                (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
                (lastModifiedDate != null ? "lastModifiedDate=" + lastModifiedDate + ", " : "") +
                (totalMoneyAmount != null ? "totalMoneyAmount=" + totalMoneyAmount + ", " : "") +
                (promotionAmount != null ? "promotionAmount=" + promotionAmount + ", " : "") +
                (classCount != null ? "classCount=" + classCount + ", " : "") +
                (totalMinutes != null ? "totalMinutes=" + totalMinutes + ", " : "") +
                (specialPromotionAmount != null ? "specialPromotionAmount=" + specialPromotionAmount + ", " : "") +
                (customerId != null ? "customerId=" + customerId + ", " : "") +
                (customerCardTypeId != null ? "customerCardTypeId=" + customerCardTypeId + ", " : "") +
                (courseId != null ? "courseId=" + courseId + ", " : "") +
            "}";
    }

}
