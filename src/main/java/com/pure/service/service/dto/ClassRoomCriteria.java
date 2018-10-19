package com.pure.service.service.dto;

import com.pure.service.region.AbstractQueryCriteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;






/**
 * Criteria class for the ClassRoom entity. This class is used in ClassRoomResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /class-rooms?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ClassRoomCriteria extends AbstractQueryCriteria {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private StringFilter name;

    private StringFilter code;

    private StringFilter roomNumber;

    private FloatFilter acreage;

    private IntegerFilter maxStudentCapacity;

    private StringFilter comments;

    private BooleanFilter avaliable;

    public ClassRoomCriteria() {
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

    public StringFilter getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(StringFilter roomNumber) {
        this.roomNumber = roomNumber;
    }

    public FloatFilter getAcreage() {
        return acreage;
    }

    public void setAcreage(FloatFilter acreage) {
        this.acreage = acreage;
    }

    public IntegerFilter getMaxStudentCapacity() {
        return maxStudentCapacity;
    }

    public void setMaxStudentCapacity(IntegerFilter maxStudentCapacity) {
        this.maxStudentCapacity = maxStudentCapacity;
    }

    public StringFilter getComments() {
        return comments;
    }

    public void setComments(StringFilter comments) {
        this.comments = comments;
    }

    public BooleanFilter getAvaliable() {
        return avaliable;
    }

    public void setAvaliable(BooleanFilter avaliable) {
        this.avaliable = avaliable;
    }

    @Override
    public String toString() {
        return "ClassRoomCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (code != null ? "code=" + code + ", " : "") +
                (roomNumber != null ? "roomNumber=" + roomNumber + ", " : "") +
                (acreage != null ? "acreage=" + acreage + ", " : "") +
                (maxStudentCapacity != null ? "maxStudentCapacity=" + maxStudentCapacity + ", " : "") +
                (comments != null ? "comments=" + comments + ", " : "") +
                (avaliable != null ? "avaliable=" + avaliable + ", " : "") +
            "}";
    }

}
