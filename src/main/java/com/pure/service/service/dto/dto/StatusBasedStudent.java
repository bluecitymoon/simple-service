package com.pure.service.service.dto.dto;

import com.pure.service.domain.Student;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class StatusBasedStudent implements Serializable {

    private List<Student> shouldTakenStudents = new ArrayList<>();
    private List<Student> absentStudents = new ArrayList<>();
    private List<Student> askedLeaveStudents = new ArrayList<>();
    private List<Student> addedStudents = new ArrayList<>();
    private List<Student> actualTakenStudents = new ArrayList<>();
    private Instant logDate;
    private String weekName;

    public List<Student> getAskedLeaveStudents() {
        return askedLeaveStudents;
    }

    public void setAskedLeaveStudents(List<Student> askedLeaveStudents) {
        this.askedLeaveStudents = askedLeaveStudents;
    }

    public List<Student> getShouldTakenStudents() {
        return shouldTakenStudents;
    }

    public void setShouldTakenStudents(List<Student> shouldTakenStudents) {
        this.shouldTakenStudents = shouldTakenStudents;
    }

    public List<Student> getAbsentStudents() {
        return absentStudents;
    }

    public void setAbsentStudents(List<Student> absentStudents) {
        this.absentStudents = absentStudents;
    }

    public List<Student> getAddedStudents() {
        return addedStudents;
    }

    public void setAddedStudents(List<Student> addedStudents) {
        this.addedStudents = addedStudents;
    }

    public List<Student> getActualTakenStudents() {
        return actualTakenStudents;
    }

    public void setActualTakenStudents(List<Student> actualTakenStudents) {
        this.actualTakenStudents = actualTakenStudents;
    }

    public Instant getLogDate() {
        return logDate;
    }

    public void setLogDate(Instant logDate) {
        this.logDate = logDate;
    }

    public String getWeekName() {
        return weekName;
    }

    public void setWeekName(String weekName) {
        this.weekName = weekName;
    }
}
