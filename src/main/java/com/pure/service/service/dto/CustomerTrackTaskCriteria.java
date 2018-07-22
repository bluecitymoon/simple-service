package com.pure.service.service.dto;

import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.LongFilter;

import java.io.Serializable;






/**
 * Criteria class for the CustomerTrackTask entity. This class is used in CustomerTrackTaskResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /customer-track-tasks?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CustomerTrackTaskCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private LongFilter customerId;

    private LongFilter taskId;

    private Long salesFollowerId;

    private boolean isToday;

    public CustomerTrackTaskCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getCustomerId() {
        return customerId;
    }

    public void setCustomerId(LongFilter customerId) {
        this.customerId = customerId;
    }

    public LongFilter getTaskId() {
        return taskId;
    }

    public void setTaskId(LongFilter taskId) {
        this.taskId = taskId;
    }

    public Long getSalesFollowerId() {
        return salesFollowerId;
    }

    public void setSalesFollowerId(Long salesFollowerId) {
        this.salesFollowerId = salesFollowerId;
    }

    public boolean isToday() {
        return isToday;
    }

    public void setToday(boolean today) {
        isToday = today;
    }

    @Override
    public String toString() {
        return "CustomerTrackTaskCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (customerId != null ? "customerId=" + customerId + ", " : "") +
                (taskId != null ? "taskId=" + taskId + ", " : "") +
            "}";
    }

}
