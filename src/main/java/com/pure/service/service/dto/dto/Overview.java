package com.pure.service.service.dto.dto;

import java.io.Serializable;

public class Overview implements Serializable {

    private Integer untrackedCustomerCount;

    public Overview(Integer untrackedCustomerCount) {
        this.untrackedCustomerCount = untrackedCustomerCount;
    }

    public Integer getUntrackedCustomerCount() {
        return untrackedCustomerCount;
    }

    public void setUntrackedCustomerCount(Integer untrackedCustomerCount) {
        this.untrackedCustomerCount = untrackedCustomerCount;
    }
}
