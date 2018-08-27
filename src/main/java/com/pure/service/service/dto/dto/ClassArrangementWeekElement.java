package com.pure.service.service.dto.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ClassArrangementWeekElement implements Serializable {

    private String weekdayName;
    private List<String> classrooms = new ArrayList<>();
    private List<String> courses = new ArrayList<>();
    private List<String> teachers = new ArrayList<>();
    private List<ClassSchedule> morningFirstHalf = new ArrayList<>();
    private List<ClassSchedule> morningSecondHalf = new ArrayList<>();
    private List<ClassSchedule> moonRest = new ArrayList<>();
    private List<ClassSchedule> afternoonFirstHalf = new ArrayList<>();
    private List<ClassSchedule> afternoonSecondHalf = new ArrayList<>();
    private List<ClassSchedule> afternoonThiredHalf = new ArrayList<>();
    private List<ClassSchedule> afternoonRest = new ArrayList<>();
    private List<ClassSchedule> evenning = new ArrayList<>();

    public String getWeekdayName() {
        return weekdayName;
    }

    public void setWeekdayName(String weekdayName) {
        this.weekdayName = weekdayName;
    }

    public List<String> getClassrooms() {
        return classrooms;
    }

    public void setClassrooms(List<String> classrooms) {
        this.classrooms = classrooms;
    }

    public List<String> getCourses() {
        return courses;
    }

    public void setCourses(List<String> courses) {
        this.courses = courses;
    }

    public List<String> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<String> teachers) {
        this.teachers = teachers;
    }

    public List<ClassSchedule> getMorningFirstHalf() {
        return morningFirstHalf;
    }

    public void setMorningFirstHalf(List<ClassSchedule> morningFirstHalf) {
        this.morningFirstHalf = morningFirstHalf;
    }

    public List<ClassSchedule> getMorningSecondHalf() {
        return morningSecondHalf;
    }

    public void setMorningSecondHalf(List<ClassSchedule> morningSecondHalf) {
        this.morningSecondHalf = morningSecondHalf;
    }

    public List<ClassSchedule> getMoonRest() {
        return moonRest;
    }

    public void setMoonRest(List<ClassSchedule> moonRest) {
        this.moonRest = moonRest;
    }

    public List<ClassSchedule> getAfternoonFirstHalf() {
        return afternoonFirstHalf;
    }

    public void setAfternoonFirstHalf(List<ClassSchedule> afternoonFirstHalf) {
        this.afternoonFirstHalf = afternoonFirstHalf;
    }

    public List<ClassSchedule> getAfternoonSecondHalf() {
        return afternoonSecondHalf;
    }

    public void setAfternoonSecondHalf(List<ClassSchedule> afternoonSecondHalf) {
        this.afternoonSecondHalf = afternoonSecondHalf;
    }

    public List<ClassSchedule> getAfternoonThiredHalf() {
        return afternoonThiredHalf;
    }

    public void setAfternoonThiredHalf(List<ClassSchedule> afternoonThiredHalf) {
        this.afternoonThiredHalf = afternoonThiredHalf;
    }

    public List<ClassSchedule> getAfternoonRest() {
        return afternoonRest;
    }

    public void setAfternoonRest(List<ClassSchedule> afternoonRest) {
        this.afternoonRest = afternoonRest;
    }

    public List<ClassSchedule> getEvenning() {
        return evenning;
    }

    public void setEvenning(List<ClassSchedule> evenning) {
        this.evenning = evenning;
    }
}
