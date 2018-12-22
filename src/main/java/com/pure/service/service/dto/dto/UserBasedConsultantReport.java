package com.pure.service.service.dto.dto;

import com.pure.service.domain.Contract;
import com.pure.service.domain.CustomerCard;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserBasedConsultantReport implements Serializable {

    private String consultantName;
    private Integer showCount;
    private Integer cardCount;
    private Float cardRate;
    private Float totalMoneyAmount;
    private Float singleCardPrice;

    private List<Contract> contracts = new ArrayList<>();
    private Set<CustomerCard> cards = new HashSet<>();

    public Set<CustomerCard> getCards() {
        return cards;
    }

    public void setCards(Set<CustomerCard> cards) {
        this.cards = cards;
    }

    public String getConsultantName() {
        return consultantName;
    }

    public void setConsultantName(String consultantName) {
        this.consultantName = consultantName;
    }

    public Integer getShowCount() {
        return showCount;
    }

    public void setShowCount(Integer showCount) {
        this.showCount = showCount;
    }

    public Integer getCardCount() {
        return cardCount;
    }

    public void setCardCount(Integer cardCount) {
        this.cardCount = cardCount;
    }

    public Float getCardRate() {
        return cardRate;
    }

    public void setCardRate(Float cardRate) {
        this.cardRate = cardRate;
    }

    public Float getTotalMoneyAmount() {
        return totalMoneyAmount;
    }

    public void setTotalMoneyAmount(Float totalMoneyAmount) {
        this.totalMoneyAmount = totalMoneyAmount;
    }

    public Float getSingleCardPrice() {
        return singleCardPrice;
    }

    public void setSingleCardPrice(Float singleCardPrice) {
        this.singleCardPrice = singleCardPrice;
    }

    public List<Contract> getContracts() {
        return contracts;
    }

    public void setContracts(List<Contract> contracts) {
        this.contracts = contracts;
    }
}
