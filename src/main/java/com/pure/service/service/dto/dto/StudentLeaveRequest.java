package com.pure.service.service.dto.dto;

import com.pure.service.domain.ClassArrangement;
import com.pure.service.domain.Student;

import java.io.Serializable;
import java.util.List;

public class StudentLeaveRequest implements Serializable {

    private List<ClassArrangement> arrangements;
    private List<Student> students;

    public List<ClassArrangement> getArrangements() {
        return arrangements;
    }

    public void setArrangements(List<ClassArrangement> arrangements) {
        this.arrangements = arrangements;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}
