package com.pure.service.service.dto;

import com.pure.service.region.AbstractQueryCriteria;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.InstantFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

import java.time.Instant;


/**
 * Criteria class for the Contract entity. This class is used in ContractResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /contracts?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ContractCriteria extends AbstractQueryCriteria {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private StringFilter contractNumber;

    private StringFilter serialNumber;

    private InstantFilter signDate;

    private InstantFilter startDate;

    private InstantFilter endDate;

    private FloatFilter totalMoneyAmount;

    private FloatFilter moneyShouldCollected;

    private FloatFilter moneyCollected;

    private FloatFilter promotionAmount;

    private IntegerFilter totalHours;

    private IntegerFilter hoursTaken;

    private StringFilter comments;

    private StringFilter createdBy;

    private InstantFilter createdDate;

    private StringFilter lastModifiedBy;

    private InstantFilter lastModifiedDate;

    private LongFilter studentId;

    private LongFilter courseId;

    private LongFilter contractStatusId;

    private LongFilter productId;

    private LongFilter customerCardId;

    private LongFilter customerId;

    private String customerName;
    private String customerContactPhoneNumber;
    private Long followerId;

    private Instant signStartDate;
    private Instant signEndDate;
    private String studentName;

    public ContractCriteria() {
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Instant getSignStartDate() {
        return signStartDate;
    }

    public void setSignStartDate(Instant signStartDate) {
        this.signStartDate = signStartDate;
    }

    public Instant getSignEndDate() {
        return signEndDate;
    }

    public void setSignEndDate(Instant signEndDate) {
        this.signEndDate = signEndDate;
    }

    public Long getFollowerId() {
        return followerId;
    }

    public void setFollowerId(Long followerId) {
        this.followerId = followerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerContactPhoneNumber() {
        return customerContactPhoneNumber;
    }

    public void setCustomerContactPhoneNumber(String customerContactPhoneNumber) {
        this.customerContactPhoneNumber = customerContactPhoneNumber;
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(StringFilter contractNumber) {
        this.contractNumber = contractNumber;
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

    public FloatFilter getTotalMoneyAmount() {
        return totalMoneyAmount;
    }

    public void setTotalMoneyAmount(FloatFilter totalMoneyAmount) {
        this.totalMoneyAmount = totalMoneyAmount;
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

    public FloatFilter getPromotionAmount() {
        return promotionAmount;
    }

    public void setPromotionAmount(FloatFilter promotionAmount) {
        this.promotionAmount = promotionAmount;
    }

    public IntegerFilter getTotalHours() {
        return totalHours;
    }

    public void setTotalHours(IntegerFilter totalHours) {
        this.totalHours = totalHours;
    }

    public IntegerFilter getHoursTaken() {
        return hoursTaken;
    }

    public void setHoursTaken(IntegerFilter hoursTaken) {
        this.hoursTaken = hoursTaken;
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

    public LongFilter getStudentId() {
        return studentId;
    }

    public void setStudentId(LongFilter studentId) {
        this.studentId = studentId;
    }

    public LongFilter getCourseId() {
        return courseId;
    }

    public void setCourseId(LongFilter courseId) {
        this.courseId = courseId;
    }

    public LongFilter getContractStatusId() {
        return contractStatusId;
    }

    public void setContractStatusId(LongFilter contractStatusId) {
        this.contractStatusId = contractStatusId;
    }

    public LongFilter getProductId() {
        return productId;
    }

    public void setProductId(LongFilter productId) {
        this.productId = productId;
    }

    public LongFilter getCustomerCardId() {
        return customerCardId;
    }

    public void setCustomerCardId(LongFilter customerCardId) {
        this.customerCardId = customerCardId;
    }

    public LongFilter getCustomerId() {
        return customerId;
    }

    public void setCustomerId(LongFilter customerId) {
        this.customerId = customerId;
    }

    @Override
    public String toString() {
        return "ContractCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (contractNumber != null ? "contractNumber=" + contractNumber + ", " : "") +
                (serialNumber != null ? "serialNumber=" + serialNumber + ", " : "") +
                (signDate != null ? "signDate=" + signDate + ", " : "") +
                (startDate != null ? "startDate=" + startDate + ", " : "") +
                (endDate != null ? "endDate=" + endDate + ", " : "") +
                (totalMoneyAmount != null ? "totalMoneyAmount=" + totalMoneyAmount + ", " : "") +
                (moneyShouldCollected != null ? "moneyShouldCollected=" + moneyShouldCollected + ", " : "") +
                (moneyCollected != null ? "moneyCollected=" + moneyCollected + ", " : "") +
                (promotionAmount != null ? "promotionAmount=" + promotionAmount + ", " : "") +
                (totalHours != null ? "totalHours=" + totalHours + ", " : "") +
                (hoursTaken != null ? "hoursTaken=" + hoursTaken + ", " : "") +
                (comments != null ? "comments=" + comments + ", " : "") +
                (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
                (createdDate != null ? "createdDate=" + createdDate + ", " : "") +
                (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
                (lastModifiedDate != null ? "lastModifiedDate=" + lastModifiedDate + ", " : "") +
                (studentId != null ? "studentId=" + studentId + ", " : "") +
                (courseId != null ? "courseId=" + courseId + ", " : "") +
                (contractStatusId != null ? "contractStatusId=" + contractStatusId + ", " : "") +
                (productId != null ? "productId=" + productId + ", " : "") +
                (customerCardId != null ? "customerCardId=" + customerCardId + ", " : "") +
                (customerId != null ? "customerId=" + customerId + ", " : "") +
            "}";
    }

}
