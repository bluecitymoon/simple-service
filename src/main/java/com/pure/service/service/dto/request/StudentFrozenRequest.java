package com.pure.service.service.dto.request;

import com.pure.service.domain.ClassArrangement;
import com.pure.service.domain.Student;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

public class StudentFrozenRequest implements Serializable {

    private List<ClassArrangement> classArrangements;
    private Instant startDate;
    private Instant endDate;
    private Student student;

    public List<ClassArrangement> getClassArrangements() {
        return classArrangements;
    }

    public void setClassArrangements(List<ClassArrangement> classArrangements) {
        this.classArrangements = classArrangements;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}
