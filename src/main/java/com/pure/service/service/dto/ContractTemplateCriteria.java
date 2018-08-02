package com.pure.service.service.dto;

import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.InstantFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

import java.io.Serializable;




/**
 * Criteria class for the ContractTemplate entity. This class is used in ContractTemplateResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /contract-templates?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ContractTemplateCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private FloatFilter totalMoneyAmount;

    private IntegerFilter classCount;

    private IntegerFilter totalMinutes;

    private IntegerFilter totalHours;

    private IntegerFilter years;

    private IntegerFilter promotionAmount;

    private StringFilter createdBy;

    private InstantFilter createdDate;

    private StringFilter lastModifiedBy;

    private InstantFilter lastModifiedDate;

    private StringFilter name;
//
//    private LongFilter customerCardTypeId;

    private LongFilter contractNatureId;

    private LongFilter contractPackageId;

    public ContractTemplateCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public FloatFilter getTotalMoneyAmount() {
        return totalMoneyAmount;
    }

    public void setTotalMoneyAmount(FloatFilter totalMoneyAmount) {
        this.totalMoneyAmount = totalMoneyAmount;
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

    public IntegerFilter getTotalHours() {
        return totalHours;
    }

    public void setTotalHours(IntegerFilter totalHours) {
        this.totalHours = totalHours;
    }

    public IntegerFilter getYears() {
        return years;
    }

    public void setYears(IntegerFilter years) {
        this.years = years;
    }

    public IntegerFilter getPromotionAmount() {
        return promotionAmount;
    }

    public void setPromotionAmount(IntegerFilter promotionAmount) {
        this.promotionAmount = promotionAmount;
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

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }
//
//    public LongFilter getCustomerCardTypeId() {
//        return customerCardTypeId;
//    }
//
//    public void setCustomerCardTypeId(LongFilter customerCardTypeId) {
//        this.customerCardTypeId = customerCardTypeId;
//    }

    public LongFilter getContractPackageId() {
        return contractPackageId;
    }

    public void setContractPackageId(LongFilter contractPackageId) {
        this.contractPackageId = contractPackageId;
    }

    public LongFilter getContractNatureId() {
        return contractNatureId;
    }

    public void setContractNatureId(LongFilter contractNatureId) {
        this.contractNatureId = contractNatureId;
    }

    @Override
    public String toString() {
        return "ContractTemplateCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (totalMoneyAmount != null ? "totalMoneyAmount=" + totalMoneyAmount + ", " : "") +
                (classCount != null ? "classCount=" + classCount + ", " : "") +
                (totalMinutes != null ? "totalMinutes=" + totalMinutes + ", " : "") +
                (totalHours != null ? "totalHours=" + totalHours + ", " : "") +
                (years != null ? "years=" + years + ", " : "") +
                (promotionAmount != null ? "promotionAmount=" + promotionAmount + ", " : "") +
                (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
                (createdDate != null ? "createdDate=" + createdDate + ", " : "") +
                (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
                (lastModifiedDate != null ? "lastModifiedDate=" + lastModifiedDate + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
//                (customerCardTypeId != null ? "customerCardTypeId=" + customerCardTypeId + ", " : "") +
                (contractNatureId != null ? "contractNatureId=" + contractNatureId + ", " : "") +
            "}";
    }

}
