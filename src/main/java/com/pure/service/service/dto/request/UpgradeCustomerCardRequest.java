package com.pure.service.service.dto.request;

import com.pure.service.domain.ContractPackage;
import com.pure.service.domain.Course;
import com.pure.service.domain.Customer;
import com.pure.service.domain.CustomerCardType;

import java.io.Serializable;
import java.time.Instant;

public class UpgradeCustomerCardRequest implements Serializable {

    private String number;

    private String serialNumber;

    private Instant signDate;

    private Instant startDate;

    private Instant endDate;

    private Float moneyCollected;

    private Float moneyShouldCollected;

    private Float balance;

    private Float totalMoneyAmount;

    private Float promotionAmount;

    private Integer classCount;

    private Integer totalMinutes;

    private Float specialPromotionAmount;

    private Customer customer;

    private CustomerCardType customerCardType;
    private CustomerCardType newCustomerCardType;

    private ContractPackage upgradePackage;

    private Course course;

    public ContractPackage getUpgradePackage() {
        return upgradePackage;
    }

    public void setUpgradePackage(ContractPackage upgradePackage) {
        this.upgradePackage = upgradePackage;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Instant getSignDate() {
        return signDate;
    }

    public void setSignDate(Instant signDate) {
        this.signDate = signDate;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public Float getMoneyCollected() {
        return moneyCollected;
    }

    public void setMoneyCollected(Float moneyCollected) {
        this.moneyCollected = moneyCollected;
    }

    public Float getMoneyShouldCollected() {
        return moneyShouldCollected;
    }

    public void setMoneyShouldCollected(Float moneyShouldCollected) {
        this.moneyShouldCollected = moneyShouldCollected;
    }

    public Float getBalance() {
        return balance;
    }

    public void setBalance(Float balance) {
        this.balance = balance;
    }

    public Float getTotalMoneyAmount() {
        return totalMoneyAmount;
    }

    public void setTotalMoneyAmount(Float totalMoneyAmount) {
        this.totalMoneyAmount = totalMoneyAmount;
    }

    public Float getPromotionAmount() {
        return promotionAmount;
    }

    public void setPromotionAmount(Float promotionAmount) {
        this.promotionAmount = promotionAmount;
    }

    public Integer getClassCount() {
        return classCount;
    }

    public void setClassCount(Integer classCount) {
        this.classCount = classCount;
    }

    public Integer getTotalMinutes() {
        return totalMinutes;
    }

    public void setTotalMinutes(Integer totalMinutes) {
        this.totalMinutes = totalMinutes;
    }

    public Float getSpecialPromotionAmount() {
        return specialPromotionAmount;
    }

    public void setSpecialPromotionAmount(Float specialPromotionAmount) {
        this.specialPromotionAmount = specialPromotionAmount;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public CustomerCardType getCustomerCardType() {
        return customerCardType;
    }

    public void setCustomerCardType(CustomerCardType customerCardType) {
        this.customerCardType = customerCardType;
    }

    public CustomerCardType getNewCustomerCardType() {
        return newCustomerCardType;
    }

    public void setNewCustomerCardType(CustomerCardType newCustomerCardType) {
        this.newCustomerCardType = newCustomerCardType;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
