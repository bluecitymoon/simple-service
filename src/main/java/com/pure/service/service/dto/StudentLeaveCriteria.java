package com.pure.service.service.dto;

import com.pure.service.region.AbstractQueryCriteria;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.InstantFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;




/**
 * Criteria class for the StudentLeave entity. This class is used in StudentLeaveResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /student-leaves?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class StudentLeaveCriteria extends AbstractQueryCriteria {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private IntegerFilter balance;

    private InstantFilter calculateStartDate;

    private InstantFilter calculateEndDate;

    private StringFilter createdBy;

    private InstantFilter createdDate;

    private StringFilter lastModifiedBy;

    private InstantFilter lastModifiedDate;

    private LongFilter studentId;

    private LongFilter classArrangementId;

    public StudentLeaveCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IntegerFilter getBalance() {
        return balance;
    }

    public void setBalance(IntegerFilter balance) {
        this.balance = balance;
    }

    public InstantFilter getCalculateStartDate() {
        return calculateStartDate;
    }

    public void setCalculateStartDate(InstantFilter calculateStartDate) {
        this.calculateStartDate = calculateStartDate;
    }

    public InstantFilter getCalculateEndDate() {
        return calculateEndDate;
    }

    public void setCalculateEndDate(InstantFilter calculateEndDate) {
        this.calculateEndDate = calculateEndDate;
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

    public LongFilter getClassArrangementId() {
        return classArrangementId;
    }

    public void setClassArrangementId(LongFilter classArrangementId) {
        this.classArrangementId = classArrangementId;
    }

    @Override
    public String toString() {
        return "StudentLeaveCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (balance != null ? "balance=" + balance + ", " : "") +
                (calculateStartDate != null ? "calculateStartDate=" + calculateStartDate + ", " : "") +
                (calculateEndDate != null ? "calculateEndDate=" + calculateEndDate + ", " : "") +
                (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
                (createdDate != null ? "createdDate=" + createdDate + ", " : "") +
                (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
                (lastModifiedDate != null ? "lastModifiedDate=" + lastModifiedDate + ", " : "") +
                (studentId != null ? "studentId=" + studentId + ", " : "") +
                (classArrangementId != null ? "classArrangementId=" + classArrangementId + ", " : "") +
            "}";
    }

}
