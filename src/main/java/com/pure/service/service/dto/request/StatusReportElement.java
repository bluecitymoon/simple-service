package com.pure.service.service.dto.request;

import java.io.Serializable;

/**
 *
 */
public class StatusReportElement implements Serializable {

    private String location;
    private Long locationId;
    private Integer ageTooSmallCount = 0;
    private Integer errorInformation = 0;
    private Integer noWillingCount = 0;
    private Integer consideringCount = 0;
    private Integer scheduledCount = 0;
    private Integer dealedCount = 0; //已成交数量
    private Integer visitedCount = 0; //已成交数量
    private Integer newCreatedCount = 0;
    private Integer totalCount = 0;
    private String valuableRate = "0";

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getAgeTooSmallCount() {
        return ageTooSmallCount;
    }

    public void setAgeTooSmallCount(Integer ageTooSmallCount) {
        this.ageTooSmallCount = ageTooSmallCount;
    }

    public Integer getErrorInformation() {
        return errorInformation;
    }

    public void setErrorInformation(Integer errorInformation) {
        this.errorInformation = errorInformation;
    }

    public Integer getNoWillingCount() {
        return noWillingCount;
    }

    public void setNoWillingCount(Integer noWillingCount) {
        this.noWillingCount = noWillingCount;
    }

    public Integer getConsideringCount() {
        return consideringCount;
    }

    public void setConsideringCount(Integer consideringCount) {
        this.consideringCount = consideringCount;
    }

    public Integer getScheduledCount() {
        return scheduledCount;
    }

    public void setScheduledCount(Integer scheduledCount) {
        this.scheduledCount = scheduledCount;
    }

    public Integer getDealedCount() {
        return dealedCount;
    }

    public void setDealedCount(Integer dealedCount) {
        this.dealedCount = dealedCount;
    }

    public Integer getNewCreatedCount() {
        return newCreatedCount;
    }

    public void setNewCreatedCount(Integer newCreatedCount) {
        this.newCreatedCount = newCreatedCount;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public String getValuableRate() {
        return valuableRate;
    }

    public void setValuableRate(String valuableRate) {
        this.valuableRate = valuableRate;
    }

    public Integer getVisitedCount() {
        return visitedCount;
    }

    public void setVisitedCount(Integer visitedCount) {
        this.visitedCount = visitedCount;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }
}
