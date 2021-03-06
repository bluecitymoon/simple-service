package com.pure.service.service.dto;

import com.pure.service.region.AbstractQueryCriteria;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.InstantFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;




/**
 * Criteria class for the CustomerScheduleFeedback entity. This class is used in CustomerScheduleFeedbackResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /customer-schedule-feedbacks?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CustomerScheduleFeedbackCriteria extends AbstractQueryCriteria {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private StringFilter giftCode;

    private StringFilter giftStatus;

    private StringFilter createdBy;

    private InstantFilter createdDate;

    private StringFilter lastModifiedBy;

    private InstantFilter lastModifiedDate;

    private LongFilter customerId;

    private LongFilter scheduleId;

    private String customerPhoneNumber;

    public CustomerScheduleFeedbackCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public String getCustomerPhoneNumber() {
        return customerPhoneNumber;
    }

    public void setCustomerPhoneNumber(String customerPhoneNumber) {
        this.customerPhoneNumber = customerPhoneNumber;
    }

    public StringFilter getGiftCode() {
        return giftCode;
    }

    public void setGiftCode(StringFilter giftCode) {
        this.giftCode = giftCode;
    }

    public StringFilter getGiftStatus() {
        return giftStatus;
    }

    public void setGiftStatus(StringFilter giftStatus) {
        this.giftStatus = giftStatus;
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

    public LongFilter getCustomerId() {
        return customerId;
    }

    public void setCustomerId(LongFilter customerId) {
        this.customerId = customerId;
    }

    public LongFilter getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(LongFilter scheduleId) {
        this.scheduleId = scheduleId;
    }

    @Override
    public String toString() {
        return "CustomerScheduleFeedbackCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (giftCode != null ? "giftCode=" + giftCode + ", " : "") +
                (giftStatus != null ? "giftStatus=" + giftStatus + ", " : "") +
                (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
                (createdDate != null ? "createdDate=" + createdDate + ", " : "") +
                (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
                (lastModifiedDate != null ? "lastModifiedDate=" + lastModifiedDate + ", " : "") +
                (customerId != null ? "customerId=" + customerId + ", " : "") +
                (scheduleId != null ? "scheduleId=" + scheduleId + ", " : "") +
            "}";
    }

}
