package com.pure.service.service.dto.dto;

import com.pure.service.domain.Contract;

import java.io.Serializable;

public class TypedContractRequest implements Serializable {

    private String code;
    private Contract contract;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }
}
