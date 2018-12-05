package com.pure.service.service.dto;

import com.pure.service.region.AbstractQueryCriteria;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;






/**
 * Criteria class for the SystemVariable entity. This class is used in SystemVariableResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /system-variables?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class SystemVariableCriteria extends AbstractQueryCriteria {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private StringFilter name;

    private StringFilter attrValue;

    private StringFilter comments;

    public SystemVariableCriteria() {
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

    public StringFilter getAttrValue() {
        return attrValue;
    }

    public void setAttrValue(StringFilter attrValue) {
        this.attrValue = attrValue;
    }

    public StringFilter getComments() {
        return comments;
    }

    public void setComments(StringFilter comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "SystemVariableCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (attrValue != null ? "attrValue=" + attrValue + ", " : "") +
                (comments != null ? "comments=" + comments + ", " : "") +
            "}";
    }

}
