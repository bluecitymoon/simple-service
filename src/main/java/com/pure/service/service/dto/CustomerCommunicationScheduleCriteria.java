package com.pure.service.service.dto;

import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.InstantFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

import java.io.Serializable;


/**
 * Criteria class for the CustomerCommunicationSchedule entity. This class is used in CustomerCommunicationScheduleResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /customer-communication-schedules?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CustomerCommunicationScheduleCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private InstantFilter sceduleDate;

    private StringFilter comments;

    private StringFilter createdBy;
    private String customerCreatedBy;

    private InstantFilter createdDate;

    private StringFilter lastModifiedBy;

    private InstantFilter lastModifiedDate;

    private InstantFilter actuallMeetDate;

    private LongFilter customerId;

    private LongFilter followerId;

    private LongFilter scheduleStatusId;

    private StringFilter customerName;
    private StringFilter contactPhoneNumber;

    private StringFilter sourceType;

    private Long channelId;
    private Long pwiId;
    private Long tmkId;

    private Long courseConsultantId;

    public CustomerCommunicationScheduleCriteria() {
    }

    public StringFilter getSourceType() {
        return sourceType;
    }

    public void setSourceType(StringFilter sourceType) {
        this.sourceType = sourceType;
    }

    public String getCustomerCreatedBy() {
        return customerCreatedBy;
    }

    public void setCustomerCreatedBy(String customerCreatedBy) {
        this.customerCreatedBy = customerCreatedBy;
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public InstantFilter getSceduleDate() {
        return sceduleDate;
    }

    public void setSceduleDate(InstantFilter sceduleDate) {
        this.sceduleDate = sceduleDate;
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

    public InstantFilter getActuallMeetDate() {
        return actuallMeetDate;
    }

    public void setActuallMeetDate(InstantFilter actuallMeetDate) {
        this.actuallMeetDate = actuallMeetDate;
    }

    public LongFilter getCustomerId() {
        return customerId;
    }

    public void setCustomerId(LongFilter customerId) {
        this.customerId = customerId;
    }

    public LongFilter getFollowerId() {
        return followerId;
    }

    public void setFollowerId(LongFilter followerId) {
        this.followerId = followerId;
    }

    public LongFilter getScheduleStatusId() {
        return scheduleStatusId;
    }

    public void setScheduleStatusId(LongFilter scheduleStatusId) {
        this.scheduleStatusId = scheduleStatusId;
    }

    public StringFilter getCustomerName() {
        return customerName;
    }

    public void setCustomerName(StringFilter customerName) {
        this.customerName = customerName;
    }

    public StringFilter getContactPhoneNumber() {
        return contactPhoneNumber;
    }

    public void setContactPhoneNumber(StringFilter contactPhoneNumber) {
        this.contactPhoneNumber = contactPhoneNumber;
    }

    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }

    public Long getPwiId() {
        return pwiId;
    }

    public void setPwiId(Long pwiId) {
        this.pwiId = pwiId;
    }

    public Long getTmkId() {
        return tmkId;
    }

    public void setTmkId(Long tmkId) {
        this.tmkId = tmkId;
    }

    public Long getCourseConsultantId() {
        return courseConsultantId;
    }

    public void setCourseConsultantId(Long courseConsultantId) {
        this.courseConsultantId = courseConsultantId;
    }

    public boolean isNull() {

        return id == null && sceduleDate == null && comments == null && createdBy == null
            && createdDate == null && lastModifiedBy == null && lastModifiedDate == null && actuallMeetDate == null
            && customerId == null && followerId == null && scheduleStatusId == null && customerName == null && contactPhoneNumber == null && channelId == null
            && pwiId == null && tmkId == null;
    }

    @Override
    public String toString() {
        return "CustomerCommunicationScheduleCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (sceduleDate != null ? "sceduleDate=" + sceduleDate + ", " : "") +
            (comments != null ? "comments=" + comments + ", " : "") +
            (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
            (createdDate != null ? "createdDate=" + createdDate + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (lastModifiedDate != null ? "lastModifiedDate=" + lastModifiedDate + ", " : "") +
            (actuallMeetDate != null ? "actuallMeetDate=" + actuallMeetDate + ", " : "") +
            (customerId != null ? "customerId=" + customerId + ", " : "") +
            (followerId != null ? "followerId=" + followerId + ", " : "") +
            (scheduleStatusId != null ? "scheduleStatusId=" + scheduleStatusId + ", " : "") +
            "}";
    }

}
