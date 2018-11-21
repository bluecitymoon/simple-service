package com.pure.service.service.dto.request;

import java.io.Serializable;
import java.time.Instant;

public class StudentClassArrangementsRequest implements Serializable {

    private Long studentId;
    private Instant startDate;
    private Instant endDate;

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
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
}
