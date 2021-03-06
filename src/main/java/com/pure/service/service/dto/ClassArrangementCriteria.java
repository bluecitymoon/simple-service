package com.pure.service.service.dto;

import com.pure.service.region.AbstractQueryCriteria;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.InstantFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;




/**
 * Criteria class for the ClassArrangement entity. This class is used in ClassArrangementResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /class-arrangements?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ClassArrangementCriteria extends AbstractQueryCriteria {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private InstantFilter startDate;

    private InstantFilter endDate;

    private StringFilter createdBy;

    private InstantFilter createdDate;

    private StringFilter lastModifiedBy;

    private InstantFilter lastModifiedDate;

    private LongFilter planedTeacherId;

    private LongFilter actualTeacherId;

    private LongFilter statusId;

    private LongFilter clazzId;

    private String startTime;
    private String endTime;

    public ClassArrangementCriteria() {
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
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

    public LongFilter getPlanedTeacherId() {
        return planedTeacherId;
    }

    public void setPlanedTeacherId(LongFilter planedTeacherId) {
        this.planedTeacherId = planedTeacherId;
    }

    public LongFilter getActualTeacherId() {
        return actualTeacherId;
    }

    public void setActualTeacherId(LongFilter actualTeacherId) {
        this.actualTeacherId = actualTeacherId;
    }

    public LongFilter getStatusId() {
        return statusId;
    }

    public void setStatusId(LongFilter statusId) {
        this.statusId = statusId;
    }

    public LongFilter getClazzId() {
        return clazzId;
    }

    public void setClazzId(LongFilter clazzId) {
        this.clazzId = clazzId;
    }

    @Override
    public String toString() {
        return "ClassArrangementCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (startDate != null ? "startDate=" + startDate + ", " : "") +
                (endDate != null ? "endDate=" + endDate + ", " : "") +
                (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
                (createdDate != null ? "createdDate=" + createdDate + ", " : "") +
                (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
                (lastModifiedDate != null ? "lastModifiedDate=" + lastModifiedDate + ", " : "") +
                (planedTeacherId != null ? "planedTeacherId=" + planedTeacherId + ", " : "") +
                (actualTeacherId != null ? "actualTeacherId=" + actualTeacherId + ", " : "") +
                (statusId != null ? "statusId=" + statusId + ", " : "") +
                (clazzId != null ? "clazzId=" + clazzId + ", " : "") +
            "}";
    }

}
