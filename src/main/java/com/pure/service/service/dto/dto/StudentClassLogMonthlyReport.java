package com.pure.service.service.dto.dto;

import com.pure.service.domain.StudentClassLogDailyReport;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class StudentClassLogMonthlyReport implements Serializable {

    private Integer shouldTaken;
    private Integer leave;
    private Integer absence;
    private Integer added;
    private Integer actualTaken;
    private Integer month;
    private Integer year;
    //key
    private String yearMonth;

    private List<StudentClassLogDailyReport> details = new ArrayList<>();

    public List<StudentClassLogDailyReport> getDetails() {
        return details;
    }

    public void setDetails(List<StudentClassLogDailyReport> details) {
        this.details = details;
    }

    public String getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(String yearMonth) {
        this.yearMonth = yearMonth;
    }

    public Integer getShouldTaken() {
        return shouldTaken;
    }

    public void setShouldTaken(Integer shouldTaken) {
        this.shouldTaken = shouldTaken;
    }

    public Integer getLeave() {
        return leave;
    }

    public void setLeave(Integer leave) {
        this.leave = leave;
    }

    public Integer getAbsence() {
        return absence;
    }

    public void setAbsence(Integer absence) {
        this.absence = absence;
    }

    public Integer getAdded() {
        return added;
    }

    public void setAdded(Integer added) {
        this.added = added;
    }

    public Integer getActualTaken() {
        return actualTaken;
    }

    public void setActualTaken(Integer actualTaken) {
        this.actualTaken = actualTaken;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}
