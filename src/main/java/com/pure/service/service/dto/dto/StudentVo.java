package com.pure.service.service.dto.dto;

import com.pure.service.domain.Customer;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class StudentVo implements Serializable {
    private Long id;
    private String name;
    private String phone;
    private String gender;
    private Instant birthday;
    private String address;
    private String school;
    private String qq;
    private String comments;
    private Customer customer;
    private List<StudentClassCount> studentClassCounts = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Instant getBirthday() {
        return birthday;
    }

    public void setBirthday(Instant birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<StudentClassCount> getStudentClassCounts() {
        return studentClassCounts;
    }

    public void setStudentClassCounts(List<StudentClassCount> studentClassCounts) {
        this.studentClassCounts = studentClassCounts;
    }
}
