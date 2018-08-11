package com.pure.service.service.dto.request;

import com.pure.service.domain.Customer;

import java.io.Serializable;

public class MergeCustomer implements Serializable {

    private Long originalId;
    private Long targetId;
    private Customer customer;

    public Long getOriginalId() {
        return originalId;
    }

    public void setOriginalId(Long originalId) {
        this.originalId = originalId;
    }

    public Long getTargetId() {
        return targetId;
    }

    public void setTargetId(Long targetId) {
        this.targetId = targetId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
