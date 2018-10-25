package com.pure.service.service.dto.dto;

import java.io.Serializable;

public class ChannelReportElement implements Serializable {

    private Long channelId;
    private String channelName;
    private String channelCode;
    private Integer visitedCustomerCount;
    private Integer cardCount;
    private Integer contractCount;
    private Double moneyCollected;
    private Double contractMadeRate;
    private String contractMadeRateText;

    public ChannelReportElement(Long channelId, String channelName, Integer visitedCustomerCount) {
        this.channelId = channelId;
        this.channelName = channelName;
        this.visitedCustomerCount = visitedCustomerCount;
    }

    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    public Integer getVisitedCustomerCount() {
        return visitedCustomerCount;
    }

    public void setVisitedCustomerCount(Integer visitedCustomerCount) {
        this.visitedCustomerCount = visitedCustomerCount;
    }

    public Integer getCardCount() {
        return cardCount;
    }

    public void setCardCount(Integer cardCount) {
        this.cardCount = cardCount;
    }

    public Integer getContractCount() {
        return contractCount;
    }

    public void setContractCount(Integer contractCount) {
        this.contractCount = contractCount;
    }

    public Double getMoneyCollected() {
        return moneyCollected;
    }

    public void setMoneyCollected(Double moneyCollected) {
        this.moneyCollected = moneyCollected;
    }

    public Double getContractMadeRate() {
        return contractMadeRate;
    }

    public void setContractMadeRate(Double contractMadeRate) {
        this.contractMadeRate = contractMadeRate;
    }

    public String getContractMadeRateText() {
        return contractMadeRateText;
    }

    public void setContractMadeRateText(String contractMadeRateText) {
        this.contractMadeRateText = contractMadeRateText;
    }
}
