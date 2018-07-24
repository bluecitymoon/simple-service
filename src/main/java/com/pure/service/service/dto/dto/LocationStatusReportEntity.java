package com.pure.service.service.dto.dto;

import java.io.Serializable;

public class LocationStatusReportEntity implements Serializable {

    private Integer count;
    private String location;
    private Long locationId;
    private String statusCode;
    private String statusName;

    public LocationStatusReportEntity(Integer count, String location, Long locationId, String statusCode, String statusName) {
        this.count = count;
        this.location = location;
        this.locationId = locationId;
        this.statusCode = statusCode;
        this.statusName = statusName;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
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
