package com.pure.service.service.dto;

import com.pure.service.region.AbstractQueryCriteria;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.InstantFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;




/**
 * Criteria class for the CustomerConsumerLog entity. This class is used in CustomerConsumerLogResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /customer-consumer-logs?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CustomerConsumerLogCriteria extends AbstractQueryCriteria {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private FloatFilter count;

    private StringFilter unit;

    private StringFilter uniqueNumber;

    private StringFilter createdBy;

    private InstantFilter createdDate;

    private StringFilter lastModifiedBy;

    private InstantFilter lastModifiedDate;

    private StringFilter consumerName;

    private LongFilter customerConsumerTypeId;

    private LongFilter studentId;

    public CustomerConsumerLogCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public FloatFilter getCount() {
        return count;
    }

    public void setCount(FloatFilter count) {
        this.count = count;
    }

    public StringFilter getUnit() {
        return unit;
    }

    public void setUnit(StringFilter unit) {
        this.unit = unit;
    }

    public StringFilter getUniqueNumber() {
        return uniqueNumber;
    }

    public void setUniqueNumber(StringFilter uniqueNumber) {
        this.uniqueNumber = uniqueNumber;
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

    public StringFilter getConsumerName() {
        return consumerName;
    }

    public void setConsumerName(StringFilter consumerName) {
        this.consumerName = consumerName;
    }

    public LongFilter getCustomerConsumerTypeId() {
        return customerConsumerTypeId;
    }

    public void setCustomerConsumerTypeId(LongFilter customerConsumerTypeId) {
        this.customerConsumerTypeId = customerConsumerTypeId;
    }

    public LongFilter getStudentId() {
        return studentId;
    }

    public void setStudentId(LongFilter studentId) {
        this.studentId = studentId;
    }

    @Override
    public String toString() {
        return "CustomerConsumerLogCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (count != null ? "count=" + count + ", " : "") +
                (unit != null ? "unit=" + unit + ", " : "") +
                (uniqueNumber != null ? "uniqueNumber=" + uniqueNumber + ", " : "") +
                (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
                (createdDate != null ? "createdDate=" + createdDate + ", " : "") +
                (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
                (lastModifiedDate != null ? "lastModifiedDate=" + lastModifiedDate + ", " : "") +
                (consumerName != null ? "consumerName=" + consumerName + ", " : "") +
                (customerConsumerTypeId != null ? "customerConsumerTypeId=" + customerConsumerTypeId + ", " : "") +
                (studentId != null ? "studentId=" + studentId + ", " : "") +
            "}";
    }

}
