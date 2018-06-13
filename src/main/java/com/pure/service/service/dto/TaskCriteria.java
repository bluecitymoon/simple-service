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
 * Criteria class for the Task entity. This class is used in TaskResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /tasks?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class TaskCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private StringFilter description;

    private InstantFilter estimateExecuteDate;

    private StringFilter createdBy;

    private InstantFilter createdDate;

    private StringFilter lastModifiedBy;

    private InstantFilter lastModifiedDate;

    private LongFilter assigneeId;

    private LongFilter taskStatusId;

    private LongFilter reporterId;

    public TaskCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public InstantFilter getEstimateExecuteDate() {
        return estimateExecuteDate;
    }

    public void setEstimateExecuteDate(InstantFilter estimateExecuteDate) {
        this.estimateExecuteDate = estimateExecuteDate;
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

    public LongFilter getAssigneeId() {
        return assigneeId;
    }

    public void setAssigneeId(LongFilter assigneeId) {
        this.assigneeId = assigneeId;
    }

    public LongFilter getTaskStatusId() {
        return taskStatusId;
    }

    public void setTaskStatusId(LongFilter taskStatusId) {
        this.taskStatusId = taskStatusId;
    }

    public LongFilter getReporterId() {
        return reporterId;
    }

    public void setReporterId(LongFilter reporterId) {
        this.reporterId = reporterId;
    }

    @Override
    public String toString() {
        return "TaskCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (estimateExecuteDate != null ? "estimateExecuteDate=" + estimateExecuteDate + ", " : "") +
                (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
                (createdDate != null ? "createdDate=" + createdDate + ", " : "") +
                (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
                (lastModifiedDate != null ? "lastModifiedDate=" + lastModifiedDate + ", " : "") +
                (assigneeId != null ? "assigneeId=" + assigneeId + ", " : "") +
                (taskStatusId != null ? "taskStatusId=" + taskStatusId + ", " : "") +
                (reporterId != null ? "reporterId=" + reporterId + ", " : "") +
            "}";
    }

}
