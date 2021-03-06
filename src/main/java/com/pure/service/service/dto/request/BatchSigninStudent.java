package com.pure.service.service.dto.request;

import com.pure.service.domain.Student;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BatchSigninStudent implements Serializable {

    private Long classId;
    private List<SingleArrangementRequest> arrangementIds;
    private List<Student> students = new ArrayList<>();
    private List<Student> absentStudents = new ArrayList<>();
    private List<Student> addedStudents = new ArrayList<>();

    public List<SingleArrangementRequest> getArrangementIds() {
        return arrangementIds;
    }

    public void setArrangementIds(List<SingleArrangementRequest> arrangementIds) {
        this.arrangementIds = arrangementIds;
    }

    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public List<Student> getAddedStudents() {
        return addedStudents;
    }

    public void setAddedStudents(List<Student> addedStudents) {
        this.addedStudents = addedStudents;
    }

    public List<Student> getAbsentStudents() {
        return absentStudents;
    }

    public void setAbsentStudents(List<Student> absentStudents) {
        this.absentStudents = absentStudents;
    }
}
