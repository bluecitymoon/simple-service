package com.pure.service.service.dto.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ClassArrangementWeekElement implements Serializable {

    private String weekdayName;
    private List<String> classrooms = new ArrayList<>();
    private List<String> courses = new ArrayList<>();
    private List<String> teachers = new ArrayList<>();
    private List<ClassNameElement> morningFirstHalf = new ArrayList<>();
    private List<ClassNameElement> morningSecondHalf = new ArrayList<>();
    private List<ClassNameElement> moonRest = new ArrayList<>();
    private List<ClassNameElement> afternoonFirstHalf = new ArrayList<>();
    private List<ClassNameElement> afternoonSecondHalf = new ArrayList<>();
    private List<ClassNameElement> afternoonThiredHalf = new ArrayList<>();
    private List<ClassNameElement> afternoonRest = new ArrayList<>();
    private List<ClassNameElement> evenning = new ArrayList<>();

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

    public List<ClassNameElement> getMorningFirstHalf() {
        return morningFirstHalf;
    }

    public void setMorningFirstHalf(List<ClassNameElement> morningFirstHalf) {
        this.morningFirstHalf = morningFirstHalf;
    }

    public List<ClassNameElement> getMorningSecondHalf() {
        return morningSecondHalf;
    }

    public void setMorningSecondHalf(List<ClassNameElement> morningSecondHalf) {
        this.morningSecondHalf = morningSecondHalf;
    }

    public List<ClassNameElement> getMoonRest() {
        return moonRest;
    }

    public void setMoonRest(List<ClassNameElement> moonRest) {
        this.moonRest = moonRest;
    }

    public List<ClassNameElement> getAfternoonFirstHalf() {
        return afternoonFirstHalf;
    }

    public void setAfternoonFirstHalf(List<ClassNameElement> afternoonFirstHalf) {
        this.afternoonFirstHalf = afternoonFirstHalf;
    }

    public List<ClassNameElement> getAfternoonSecondHalf() {
        return afternoonSecondHalf;
    }

    public void setAfternoonSecondHalf(List<ClassNameElement> afternoonSecondHalf) {
        this.afternoonSecondHalf = afternoonSecondHalf;
    }

    public List<ClassNameElement> getAfternoonThiredHalf() {
        return afternoonThiredHalf;
    }

    public void setAfternoonThiredHalf(List<ClassNameElement> afternoonThiredHalf) {
        this.afternoonThiredHalf = afternoonThiredHalf;
    }

    public List<ClassNameElement> getAfternoonRest() {
        return afternoonRest;
    }

    public void setAfternoonRest(List<ClassNameElement> afternoonRest) {
        this.afternoonRest = afternoonRest;
    }

    public List<ClassNameElement> getEvenning() {
        return evenning;
    }

    public void setEvenning(List<ClassNameElement> evenning) {
        this.evenning = evenning;
    }
}
