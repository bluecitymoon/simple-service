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
 * A Collection.
 */
@Entity
@Table(name = "collection")
public class Collection extends AbstractAuditingRegionEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "money_should_collected")
    private Float moneyShouldCollected = 0.0f;

    @Column(name = "money_collected")
    private Float moneyCollected = 0.0f;

    @Column(name = "balance")
    private Float balance = 0.0f;

    @Column(name = "sequence_number")
    private String sequenceNumber;

    @Column(name = "payer_name")
    private String payerName;

    @ManyToOne
    private FinanceCategory financeCategory;

    @ManyToOne
    private PaymentType paymentType;

    @ManyToOne
    private CollectionStatus status;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getMoneyShouldCollected() {
        return moneyShouldCollected;
    }

    public Collection moneyShouldCollected(Float moneyShouldCollected) {
        this.moneyShouldCollected = moneyShouldCollected;
        return this;
    }

    public void setMoneyShouldCollected(Float moneyShouldCollected) {
        this.moneyShouldCollected = moneyShouldCollected;
    }

    public Float getMoneyCollected() {
        return moneyCollected;
    }

    public Collection moneyCollected(Float moneyCollected) {
        this.moneyCollected = moneyCollected;
        return this;
    }

    public void setMoneyCollected(Float moneyCollected) {
        this.moneyCollected = moneyCollected;
    }

    public Float getBalance() {
        return balance;
    }

    public Collection balance(Float balance) {
        this.balance = balance;
        return this;
    }

    public void setBalance(Float balance) {
        this.balance = balance;
    }

    public String getSequenceNumber() {
        return sequenceNumber;
    }

    public Collection sequenceNumber(String sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
        return this;
    }

    public void setSequenceNumber(String sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public String getPayerName() {
        return payerName;
    }

    public Collection payerName(String payerName) {
        this.payerName = payerName;
        return this;
    }

    public void setPayerName(String payerName) {
        this.payerName = payerName;
    }


    public FinanceCategory getFinanceCategory() {
        return financeCategory;
    }

    public Collection financeCategory(FinanceCategory financeCategory) {
        this.financeCategory = financeCategory;
        return this;
    }

    public void setFinanceCategory(FinanceCategory financeCategory) {
        this.financeCategory = financeCategory;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public Collection paymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
        return this;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public CollectionStatus getStatus() {
        return status;
    }

    public Collection status(CollectionStatus collectionStatus) {
        this.status = collectionStatus;
        return this;
    }

    public void setStatus(CollectionStatus collectionStatus) {
        this.status = collectionStatus;
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
        Collection collection = (Collection) o;
        if (collection.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), collection.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Collection{" +
            "id=" + getId() +
            ", moneyShouldCollected='" + getMoneyShouldCollected() + "'" +
            ", moneyCollected='" + getMoneyCollected() + "'" +
            ", balance='" + getBalance() + "'" +
            ", sequenceNumber='" + getSequenceNumber() + "'" +
            ", payerName='" + getPayerName() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}
