package com.pure.service.service.dto.dto;

import java.io.Serializable;
import java.util.List;

public class ClassNameElement implements Serializable {

    private String className;
    private Long classId;
    private Boolean signable = false;
    private String statusColor;
    private List<String> students;

    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }

    public Boolean getSignable() {
        return signable;
    }

    public void setSignable(Boolean signable) {
        this.signable = signable;
    }

    public String getStatusColor() {
        return statusColor;
    }

    public void setStatusColor(String statusColor) {
        this.statusColor = statusColor;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public List<String> getStudents() {
        return students;
    }

    public void setStudents(List<String> students) {
        this.students = students;
    }
}
