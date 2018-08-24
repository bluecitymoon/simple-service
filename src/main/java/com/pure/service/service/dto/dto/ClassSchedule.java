package com.pure.service.service.dto.dto;

import java.io.Serializable;
import java.time.Instant;

public class ClassSchedule implements Serializable {

    private Long arrangementId;
    private Long classId;
    private String title;
    private String className;
    private String teacherName;
    private Instant start;
    private Instant end;
    private String classroomName;
    private String courseName;
    private Boolean allDay = true;

    public ClassSchedule(Long arrangementId, Long classId, String title, String className, String teacherName, Instant start, Instant end, String classroomName, String courseName) {
        this.arrangementId = arrangementId;
        this.classId = classId;
        this.title = title;
        this.className = className;
        this.teacherName = teacherName;
        this.start = start;
        this.end = end;
        this.classroomName = classroomName;
        this.courseName = courseName;
    }

    public ClassSchedule(Long arrangementId, Long classId, String title, String className, String teacherName, Instant start, Instant end) {
        this.arrangementId = arrangementId;
        this.classId = classId;
        this.title = title;
        this.className = className;
        this.teacherName = teacherName;
        this.start = start;
        this.end = end;
    }

    public ClassSchedule(Long classId, String title, String className, String teacherName, Instant start, Instant end) {
        this.classId = classId;
        this.title = title;
        this.className = className;
        this.teacherName = teacherName;
        this.start = start;
        this.end = end;
    }

    public ClassSchedule(String title, String className, String teacherName, Instant start, Instant end) {
        this.title = title;
        this.className = className;
        this.teacherName = teacherName;
        this.start = start;
        this.end = end;
    }

    public ClassSchedule(String title, String className, String teacherName, Instant start, Instant end, Boolean allDay) {
        this.title = title;
        this.className = className;
        this.teacherName = teacherName;
        this.start = start;
        this.end = end;
        this.allDay = allDay;
    }


    public String getClassroomName() {
        return classroomName;
    }

    public void setClassroomName(String classroomName) {
        this.classroomName = classroomName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Long getArrangementId() {
        return arrangementId;
    }

    public void setArrangementId(Long arrangementId) {
        this.arrangementId = arrangementId;
    }

    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public Instant getStart() {
        return start;
    }

    public void setStart(Instant start) {
        this.start = start;
    }

    public Instant getEnd() {
        return end;
    }

    public void setEnd(Instant end) {
        this.end = end;
    }

    public Boolean getAllDay() {
        return allDay;
    }

    public void setAllDay(Boolean allDay) {
        this.allDay = allDay;
    }
}
