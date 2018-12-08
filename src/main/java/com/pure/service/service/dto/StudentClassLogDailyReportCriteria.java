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
 * Criteria class for the StudentClassLogDailyReport entity. This class is used in StudentClassLogDailyReportResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /student-class-log-daily-reports?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class StudentClassLogDailyReportCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private IntegerFilter shouldTaken;

    private IntegerFilter leave;

    private IntegerFilter absence;

    private IntegerFilter added;

    private IntegerFilter actualTaken;

    private InstantFilter logDate;

    public StudentClassLogDailyReportCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IntegerFilter getShouldTaken() {
        return shouldTaken;
    }

    public void setShouldTaken(IntegerFilter shouldTaken) {
        this.shouldTaken = shouldTaken;
    }

    public IntegerFilter getLeave() {
        return leave;
    }

    public void setLeave(IntegerFilter leave) {
        this.leave = leave;
    }

    public IntegerFilter getAbsence() {
        return absence;
    }

    public void setAbsence(IntegerFilter absence) {
        this.absence = absence;
    }

    public IntegerFilter getAdded() {
        return added;
    }

    public void setAdded(IntegerFilter added) {
        this.added = added;
    }

    public IntegerFilter getActualTaken() {
        return actualTaken;
    }

    public void setActualTaken(IntegerFilter actualTaken) {
        this.actualTaken = actualTaken;
    }

    public InstantFilter getLogDate() {
        return logDate;
    }

    public void setLogDate(InstantFilter logDate) {
        this.logDate = logDate;
    }

    @Override
    public String toString() {
        return "StudentClassLogDailyReportCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (shouldTaken != null ? "shouldTaken=" + shouldTaken + ", " : "") +
                (leave != null ? "leave=" + leave + ", " : "") +
                (absence != null ? "absence=" + absence + ", " : "") +
                (added != null ? "added=" + added + ", " : "") +
                (actualTaken != null ? "actualTaken=" + actualTaken + ", " : "") +
                (logDate != null ? "logDate=" + logDate + ", " : "") +
            "}";
    }

}
