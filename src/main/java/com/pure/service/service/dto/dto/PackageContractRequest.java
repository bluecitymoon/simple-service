package com.pure.service.service.dto.dto;

import java.io.Serializable;

public class PackageContractRequest implements Serializable {

    private Long customerId;
    private Long customerCardId;
    private Long packageId;

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getCustomerCardId() {
        return customerCardId;
    }

    public void setCustomerCardId(Long customerCardId) {
        this.customerCardId = customerCardId;
    }

    public Long getPackageId() {
        return packageId;
    }

    public void setPackageId(Long packageId) {
        this.packageId = packageId;
    }
}
