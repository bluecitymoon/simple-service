package com.pure.service.service.dto;

import java.io.Serializable;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;






/**
 * Criteria class for the Teacher entity. This class is used in TeacherResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /teachers?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class TeacherCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private StringFilter name;

    private IntegerFilter age;

    private StringFilter graduatedSchool;

    private LongFilter educationLevelId;

    public TeacherCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public IntegerFilter getAge() {
        return age;
    }

    public void setAge(IntegerFilter age) {
        this.age = age;
    }

    public StringFilter getGraduatedSchool() {
        return graduatedSchool;
    }

    public void setGraduatedSchool(StringFilter graduatedSchool) {
        this.graduatedSchool = graduatedSchool;
    }

    public LongFilter getEducationLevelId() {
        return educationLevelId;
    }

    public void setEducationLevelId(LongFilter educationLevelId) {
        this.educationLevelId = educationLevelId;
    }

    @Override
    public String toString() {
        return "TeacherCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (age != null ? "age=" + age + ", " : "") +
                (graduatedSchool != null ? "graduatedSchool=" + graduatedSchool + ", " : "") +
                (educationLevelId != null ? "educationLevelId=" + educationLevelId + ", " : "") +
            "}";
    }

}
