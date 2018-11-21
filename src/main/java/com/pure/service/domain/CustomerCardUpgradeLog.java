package com.pure.service.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Objects;

/**
 * A CustomerCardUpgradeLog.
 */
@Entity
@Table(name = "customer_card_upgrade_log")
public class CustomerCardUpgradeLog extends AbstractAuditingRegionEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "serial_number")
    private String serialNumber;

    @ManyToOne
    private CustomerCardType originalCardType;

    @ManyToOne
    private CustomerCardType newCardType;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public CustomerCardUpgradeLog customerName(String customerName) {
        this.customerName = customerName;
        return this;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public CustomerCardUpgradeLog customerId(Long customerId) {
        this.customerId = customerId;
        return this;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public CustomerCardUpgradeLog serialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
        return this;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }


    public CustomerCardType getOriginalCardType() {
        return originalCardType;
    }

    public CustomerCardUpgradeLog originalCardType(CustomerCardType customerCardType) {
        this.originalCardType = customerCardType;
        return this;
    }

    public void setOriginalCardType(CustomerCardType customerCardType) {
        this.originalCardType = customerCardType;
    }

    public CustomerCardType getNewCardType() {
        return newCardType;
    }

    public CustomerCardUpgradeLog newCardType(CustomerCardType customerCardType) {
        this.newCardType = customerCardType;
        return this;
    }

    public void setNewCardType(CustomerCardType customerCardType) {
        this.newCardType = customerCardType;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CustomerCardUpgradeLog customerCardUpgradeLog = (CustomerCardUpgradeLog) o;
        if (customerCardUpgradeLog.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), customerCardUpgradeLog.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CustomerCardUpgradeLog{" +
            "id=" + getId() +
            ", customerName='" + getCustomerName() + "'" +
            ", customerId='" + getCustomerId() + "'" +
            ", serialNumber='" + getSerialNumber() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}
