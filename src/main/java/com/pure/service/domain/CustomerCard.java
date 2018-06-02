package com.pure.service.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.Instant;
import java.util.Objects;

/**
 * A CustomerCard.
 */
@Entity
@Table(name = "customer_card")
public class CustomerCard extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "jhi_number")
    private String number;

    @Column(name = "serial_number")
    private String serialNumber;

    @Column(name = "sign_date")
    private Instant signDate;

    @Column(name = "start_date")
    private Instant startDate;

    @Column(name = "end_date")
    private Instant endDate;

    @Column(name = "money_collected")
    private Float moneyCollected;

    @Column(name = "balance")
    private Float balance;

    @ManyToOne
    private Customer customer;

    @ManyToOne
    private CustomerCardType customerCardType;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public CustomerCard number(String number) {
        this.number = number;
        return this;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public CustomerCard serialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
        return this;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Instant getSignDate() {
        return signDate;
    }

    public CustomerCard signDate(Instant signDate) {
        this.signDate = signDate;
        return this;
    }

    public void setSignDate(Instant signDate) {
        this.signDate = signDate;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public CustomerCard startDate(Instant startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public CustomerCard endDate(Instant endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public Float getMoneyCollected() {
        return moneyCollected;
    }

    public CustomerCard moneyCollected(Float moneyCollected) {
        this.moneyCollected = moneyCollected;
        return this;
    }

    public void setMoneyCollected(Float moneyCollected) {
        this.moneyCollected = moneyCollected;
    }

    public Float getBalance() {
        return balance;
    }

    public CustomerCard balance(Float balance) {
        this.balance = balance;
        return this;
    }

    public void setBalance(Float balance) {
        this.balance = balance;
    }


    public Customer getCustomer() {
        return customer;
    }

    public CustomerCard customer(Customer customer) {
        this.customer = customer;
        return this;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public CustomerCardType getCustomerCardType() {
        return customerCardType;
    }

    public CustomerCard customerCardType(CustomerCardType customerCardType) {
        this.customerCardType = customerCardType;
        return this;
    }

    public void setCustomerCardType(CustomerCardType customerCardType) {
        this.customerCardType = customerCardType;
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
        CustomerCard customerCard = (CustomerCard) o;
        if (customerCard.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), customerCard.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CustomerCard{" +
            "id=" + getId() +
            ", number='" + getNumber() + "'" +
            ", serialNumber='" + getSerialNumber() + "'" +
            ", signDate='" + getSignDate() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", moneyCollected='" + getMoneyCollected() + "'" +
            ", balance='" + getBalance() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}
