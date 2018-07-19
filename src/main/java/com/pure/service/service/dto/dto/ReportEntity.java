package com.pure.service.service.dto.dto;

import java.io.Serializable;

public class ReportEntity implements Serializable {

    public ReportEntity(Long userId, String userName, Integer count, String statusName, String statusCode) {
        this.userId = userId;
        this.userName = userName;
        this.count = count;
        this.statusName = statusName;
        this.statusCode = statusCode;
    }

    private Long userId;
    private String userName;
    private Integer count;
    private String statusName;
    private String statusCode;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }
}
