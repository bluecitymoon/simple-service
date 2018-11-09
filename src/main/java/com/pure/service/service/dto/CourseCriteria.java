package com.pure.service.service.dto;

import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

import java.io.Serializable;






/**
 * Criteria class for the Course entity. This class is used in CourseResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /courses?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CourseCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private StringFilter name;

    private StringFilter code;

    private LongFilter classCategoryBaseId;

    public CourseCriteria() {
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

    public StringFilter getCode() {
        return code;
    }

    public void setCode(StringFilter code) {
        this.code = code;
    }

    public LongFilter getClassCategoryBaseId() {
        return classCategoryBaseId;
    }

    public void setClassCategoryBaseId(LongFilter classCategoryBaseId) {
        this.classCategoryBaseId = classCategoryBaseId;
    }

    @Override
    public String toString() {
        return "CourseCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (code != null ? "code=" + code + ", " : "") +
                (classCategoryBaseId != null ? "classCategoryBaseId=" + classCategoryBaseId + ", " : "") +
            "}";
    }

}
