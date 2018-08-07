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
 * A CustomerCollectionLog.
 */
@Entity
@Table(name = "customer_collection_log")
public class CustomerCollectionLog extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "serial_number")
    private String serialNumber;

    @Column(name = "money_should_collected")
    private Float moneyShouldCollected;

    @Column(name = "money_collected")
    private Float moneyCollected;

    @Column(name = "balance")
    private Float balance;

    @ManyToOne
    private Collection collection;

    @ManyToOne
    private Customer customer;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public CustomerCollectionLog serialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
        return this;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Float getMoneyShouldCollected() {
        return moneyShouldCollected;
    }

    public CustomerCollectionLog moneyShouldCollected(Float moneyShouldCollected) {
        this.moneyShouldCollected = moneyShouldCollected;
        return this;
    }

    public void setMoneyShouldCollected(Float moneyShouldCollected) {
        this.moneyShouldCollected = moneyShouldCollected;
    }

    public Float getMoneyCollected() {
        return moneyCollected;
    }

    public CustomerCollectionLog moneyCollected(Float moneyCollected) {
        this.moneyCollected = moneyCollected;
        return this;
    }

    public void setMoneyCollected(Float moneyCollected) {
        this.moneyCollected = moneyCollected;
    }

    public Float getBalance() {
        return balance;
    }

    public CustomerCollectionLog balance(Float balance) {
        this.balance = balance;
        return this;
    }

    public void setBalance(Float balance) {
        this.balance = balance;
    }

    public Collection getCollection() {
        return collection;
    }

    public CustomerCollectionLog collection(Collection collection) {
        this.collection = collection;
        return this;
    }

    public void setCollection(Collection collection) {
        this.collection = collection;
    }

    public Customer getCustomer() {
        return customer;
    }

    public CustomerCollectionLog customer(Customer customer) {
        this.customer = customer;
        return this;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
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
        CustomerCollectionLog customerCollectionLog = (CustomerCollectionLog) o;
        if (customerCollectionLog.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), customerCollectionLog.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CustomerCollectionLog{" +
            "id=" + getId() +
            ", serialNumber='" + getSerialNumber() + "'" +
            ", moneyShouldCollected='" + getMoneyShouldCollected() + "'" +
            ", moneyCollected='" + getMoneyCollected() + "'" +
            ", balance='" + getBalance() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}
