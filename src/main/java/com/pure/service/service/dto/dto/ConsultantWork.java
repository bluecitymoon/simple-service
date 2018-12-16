package com.pure.service.service.dto.dto;

import com.pure.service.domain.Contract;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class ConsultantWork implements Serializable {

    private Integer weekName;
    private Integer year;
    private Integer month;
    private Instant weekFromDate;
    private Instant weekEndDate;
    private Integer visitedCount;
    private Float dealedMoneyAmount;
    private List<Contract> contracts = new ArrayList<>();

    public List<Contract> getContracts() {
        return contracts;
    }

    public void setContracts(List<Contract> contracts) {
        this.contracts = contracts;
    }

    public Integer getWeekName() {
        return weekName;
    }

    public void setWeekName(Integer weekName) {
        this.weekName = weekName;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Instant getWeekFromDate() {
        return weekFromDate;
    }

    public void setWeekFromDate(Instant weekFromDate) {
        this.weekFromDate = weekFromDate;
    }

    public Instant getWeekEndDate() {
        return weekEndDate;
    }

    public void setWeekEndDate(Instant weekEndDate) {
        this.weekEndDate = weekEndDate;
    }

    public Integer getVisitedCount() {
        return visitedCount;
    }

    public void setVisitedCount(Integer visitedCount) {
        this.visitedCount = visitedCount;
    }

    public Float getDealedMoneyAmount() {
        return dealedMoneyAmount;
    }

    public void setDealedMoneyAmount(Float dealedMoneyAmount) {
        this.dealedMoneyAmount = dealedMoneyAmount;
    }
}
