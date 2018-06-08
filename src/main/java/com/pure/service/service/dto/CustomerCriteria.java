package com.pure.service.service.dto;

import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.InstantFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

import java.io.Serializable;




/**
 * Criteria class for the Customer entity. This class is used in CustomerResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /customers?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CustomerCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private StringFilter name;

    private IntegerFilter age;

    private StringFilter contactPhoneNumber;

    private StringFilter createdBy;

    private InstantFilter createdDate;

    private StringFilter lastModifiedBy;

    private InstantFilter lastModifiedDate;

    private StringFilter sex;

    private InstantFilter birthday;

    private StringFilter address;

    private StringFilter hoby;

    private StringFilter email;

    private StringFilter classLevel;

    private StringFilter parentName;

    private StringFilter parentContractNumber;

    private LongFilter newOrderId;

    private LongFilter statusId;

    private LongFilter channelId;

    private LongFilter salesFollowerId;

    private LongFilter courseConsultantId;

    private String department;

    public CustomerCriteria() {
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

    public StringFilter getContactPhoneNumber() {
        return contactPhoneNumber;
    }

    public void setContactPhoneNumber(StringFilter contactPhoneNumber) {
        this.contactPhoneNumber = contactPhoneNumber;
    }

    public StringFilter getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(StringFilter createdBy) {
        this.createdBy = createdBy;
    }

    public InstantFilter getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(InstantFilter createdDate) {
        this.createdDate = createdDate;
    }

    public StringFilter getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(StringFilter lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public InstantFilter getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(InstantFilter lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public StringFilter getSex() {
        return sex;
    }

    public void setSex(StringFilter sex) {
        this.sex = sex;
    }

    public InstantFilter getBirthday() {
        return birthday;
    }

    public void setBirthday(InstantFilter birthday) {
        this.birthday = birthday;
    }

    public StringFilter getAddress() {
        return address;
    }

    public void setAddress(StringFilter address) {
        this.address = address;
    }

    public StringFilter getHoby() {
        return hoby;
    }

    public void setHoby(StringFilter hoby) {
        this.hoby = hoby;
    }

    public StringFilter getEmail() {
        return email;
    }

    public void setEmail(StringFilter email) {
        this.email = email;
    }

    public StringFilter getClassLevel() {
        return classLevel;
    }

    public void setClassLevel(StringFilter classLevel) {
        this.classLevel = classLevel;
    }

    public StringFilter getParentName() {
        return parentName;
    }

    public void setParentName(StringFilter parentName) {
        this.parentName = parentName;
    }

    public StringFilter getParentContractNumber() {
        return parentContractNumber;
    }

    public void setParentContractNumber(StringFilter parentContractNumber) {
        this.parentContractNumber = parentContractNumber;
    }

    public LongFilter getNewOrderId() {
        return newOrderId;
    }

    public void setNewOrderId(LongFilter newOrderId) {
        this.newOrderId = newOrderId;
    }

    public LongFilter getStatusId() {
        return statusId;
    }

    public void setStatusId(LongFilter statusId) {
        this.statusId = statusId;
    }

    public LongFilter getChannelId() {
        return channelId;
    }

    public void setChannelId(LongFilter channelId) {
        this.channelId = channelId;
    }

    public void setSalesFollowerId(LongFilter userIdFilter) { this.salesFollowerId = userIdFilter;}

    public LongFilter getSalesFollowerId() {
        return salesFollowerId;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public LongFilter getCourseConsultantId() {
        return courseConsultantId;
    }

    public void setCourseConsultantId(LongFilter courseConsultantId) {
        this.courseConsultantId = courseConsultantId;
    }

    @Override
    public String toString() {
        return "CustomerCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (age != null ? "age=" + age + ", " : "") +
                (contactPhoneNumber != null ? "contactPhoneNumber=" + contactPhoneNumber + ", " : "") +
                (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
                (createdDate != null ? "createdDate=" + createdDate + ", " : "") +
                (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
                (lastModifiedDate != null ? "lastModifiedDate=" + lastModifiedDate + ", " : "") +
                (sex != null ? "sex=" + sex + ", " : "") +
                (birthday != null ? "birthday=" + birthday + ", " : "") +
                (address != null ? "address=" + address + ", " : "") +
                (hoby != null ? "hoby=" + hoby + ", " : "") +
                (email != null ? "email=" + email + ", " : "") +
                (classLevel != null ? "classLevel=" + classLevel + ", " : "") +
                (parentName != null ? "parentName=" + parentName + ", " : "") +
                (parentContractNumber != null ? "parentContractNumber=" + parentContractNumber + ", " : "") +
                (newOrderId != null ? "newOrderId=" + newOrderId + ", " : "") +
                (statusId != null ? "statusId=" + statusId + ", " : "") +
                (channelId != null ? "channelId=" + channelId + ", " : "") +
            "}";
    }


}
