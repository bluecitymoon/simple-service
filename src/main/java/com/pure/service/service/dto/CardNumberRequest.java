package com.pure.service.service.dto;

import java.io.Serializable;

public class CardNumberRequest implements Serializable {

    private Long customerId;
    private String cardCode;

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getCardCode() {
        return cardCode;
    }

    public void setCardCode(String cardCode) {
        this.cardCode = cardCode;
    }
}
