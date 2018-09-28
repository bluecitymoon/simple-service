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
 * Criteria class for the TimetableItem entity. This class is used in TimetableItemResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /timetable-items?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class TimetableItemCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private StringFilter weekdayName;

    private StringFilter classroom;

    private StringFilter courseName;

    private StringFilter teacherName;

    private StringFilter createdBy;

    private InstantFilter createdDate;

    private StringFilter lastModifiedBy;

    private InstantFilter lastModifiedDate;

    private LongFilter clazzId;

    private LongFilter timeSegmentId;

    public TimetableItemCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getWeekdayName() {
        return weekdayName;
    }

    public void setWeekdayName(StringFilter weekdayName) {
        this.weekdayName = weekdayName;
    }

    public StringFilter getClassroom() {
        return classroom;
    }

    public void setClassroom(StringFilter classroom) {
        this.classroom = classroom;
    }

    public StringFilter getCourseName() {
        return courseName;
    }

    public void setCourseName(StringFilter courseName) {
        this.courseName = courseName;
    }

    public StringFilter getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(StringFilter teacherName) {
        this.teacherName = teacherName;
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

    public LongFilter getClazzId() {
        return clazzId;
    }

    public void setClazzId(LongFilter clazzId) {
        this.clazzId = clazzId;
    }

    public LongFilter getTimeSegmentId() {
        return timeSegmentId;
    }

    public void setTimeSegmentId(LongFilter timeSegmentId) {
        this.timeSegmentId = timeSegmentId;
    }

    @Override
    public String toString() {
        return "TimetableItemCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (weekdayName != null ? "weekdayName=" + weekdayName + ", " : "") +
                (classroom != null ? "classroom=" + classroom + ", " : "") +
                (courseName != null ? "courseName=" + courseName + ", " : "") +
                (teacherName != null ? "teacherName=" + teacherName + ", " : "") +
                (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
                (createdDate != null ? "createdDate=" + createdDate + ", " : "") +
                (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
                (lastModifiedDate != null ? "lastModifiedDate=" + lastModifiedDate + ", " : "") +
                (clazzId != null ? "clazzId=" + clazzId + ", " : "") +
                (timeSegmentId != null ? "timeSegmentId=" + timeSegmentId + ", " : "") +
            "}";
    }

}
