package com.pure.service.service.dto;

import com.pure.service.region.AbstractQueryCriteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.InstantFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;




/**
 * Criteria class for the StudentFrozenArrangement entity. This class is used in StudentFrozenArrangementResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /student-frozen-arrangements?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class StudentFrozenArrangementCriteria extends AbstractQueryCriteria {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private StringFilter createdBy;

    private InstantFilter createdDate;

    private StringFilter lastModifiedBy;

    private InstantFilter lastModifiedDate;

    private BooleanFilter active;

    private LongFilter studentId;

    private LongFilter classArrangementId;

    private LongFilter studentFrozenId;

    public StudentFrozenArrangementCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
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

    public BooleanFilter getActive() {
        return active;
    }

    public void setActive(BooleanFilter active) {
        this.active = active;
    }

    public LongFilter getStudentId() {
        return studentId;
    }

    public void setStudentId(LongFilter studentId) {
        this.studentId = studentId;
    }

    public LongFilter getClassArrangementId() {
        return classArrangementId;
    }

    public void setClassArrangementId(LongFilter classArrangementId) {
        this.classArrangementId = classArrangementId;
    }

    public LongFilter getStudentFrozenId() {
        return studentFrozenId;
    }

    public void setStudentFrozenId(LongFilter studentFrozenId) {
        this.studentFrozenId = studentFrozenId;
    }

    @Override
    public String toString() {
        return "StudentFrozenArrangementCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
                (createdDate != null ? "createdDate=" + createdDate + ", " : "") +
                (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
                (lastModifiedDate != null ? "lastModifiedDate=" + lastModifiedDate + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (studentId != null ? "studentId=" + studentId + ", " : "") +
                (classArrangementId != null ? "classArrangementId=" + classArrangementId + ", " : "") +
                (studentFrozenId != null ? "studentFrozenId=" + studentFrozenId + ", " : "") +
            "}";
    }

}
