package com.pure.service.service.dto.dto;

import java.io.Serializable;

public class OuterReferReportEntity implements Serializable {

    private Integer count;
    private String refer;
    private String statusCode;
    private String statusName;

    public OuterReferReportEntity(Integer count, String refer, String statusCode, String statusName) {
        this.count = count;
        this.refer = refer;
        this.statusCode = statusCode;
        this.statusName = statusName;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getRefer() {
        return refer;
    }

    public void setRefer(String refer) {
        this.refer = refer;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }
}
