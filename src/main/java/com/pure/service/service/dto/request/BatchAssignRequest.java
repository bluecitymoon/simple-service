package com.pure.service.service.dto.request;

import com.pure.service.domain.Customer;

import java.io.Serializable;
import java.util.List;

public class BatchAssignRequest implements Serializable {
    private List<Customer> customers;
    private Long userId;

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
