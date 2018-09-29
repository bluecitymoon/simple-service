package com.pure.service.service.dto.request;

import java.io.Serializable;

public class SingleStudentClassRequest implements Serializable {

    private Long studentId;
    private Long classId;

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }
}
