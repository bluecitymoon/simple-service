package com.pure.service.service.dto.dto;

import java.io.Serializable;

public class UpdateContractBalanceRequest implements Serializable {

    private Long contractId;
    private Integer balance;

    public Long getContractId() {
        return contractId;
    }

    public void setContractId(Long contractId) {
        this.contractId = contractId;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }
}
