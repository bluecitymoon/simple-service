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
 * A ContractTemplate.
 */
@Entity
@Table(name = "contract_template")
public class ContractTemplate extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "total_money_amount")
    private Float totalMoneyAmount;

    @Column(name = "class_count")
    private Integer classCount;

    @Column(name = "total_minutes")
    private Integer totalMinutes;

    @Column(name = "total_hours")
    private Integer totalHours;

    @Column(name = "years")
    private Integer years;

    @Column(name = "promotion_amount")
    private Integer promotionAmount;

    @Column(name = "name")
    private String name;

    @ManyToOne
    private CustomerCardType customerCardType;

    @ManyToOne
    private ContractNature contractNature;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getTotalMoneyAmount() {
        return totalMoneyAmount;
    }

    public ContractTemplate totalMoneyAmount(Float totalMoneyAmount) {
        this.totalMoneyAmount = totalMoneyAmount;
        return this;
    }

    public void setTotalMoneyAmount(Float totalMoneyAmount) {
        this.totalMoneyAmount = totalMoneyAmount;
    }

    public Integer getClassCount() {
        return classCount;
    }

    public ContractTemplate classCount(Integer classCount) {
        this.classCount = classCount;
        return this;
    }

    public void setClassCount(Integer classCount) {
        this.classCount = classCount;
    }

    public Integer getTotalMinutes() {
        return totalMinutes;
    }

    public ContractTemplate totalMinutes(Integer totalMinutes) {
        this.totalMinutes = totalMinutes;
        return this;
    }

    public void setTotalMinutes(Integer totalMinutes) {
        this.totalMinutes = totalMinutes;
    }

    public Integer getTotalHours() {
        return totalHours;
    }

    public ContractTemplate totalHours(Integer totalHours) {
        this.totalHours = totalHours;
        return this;
    }

    public void setTotalHours(Integer totalHours) {
        this.totalHours = totalHours;
    }

    public Integer getYears() {
        return years;
    }

    public ContractTemplate years(Integer years) {
        this.years = years;
        return this;
    }

    public void setYears(Integer years) {
        this.years = years;
    }

    public Integer getPromotionAmount() {
        return promotionAmount;
    }

    public ContractTemplate promotionAmount(Integer promotionAmount) {
        this.promotionAmount = promotionAmount;
        return this;
    }

    public void setPromotionAmount(Integer promotionAmount) {
        this.promotionAmount = promotionAmount;
    }


    public CustomerCardType getCustomerCardType() {
        return customerCardType;
    }

    public ContractTemplate customerCardType(CustomerCardType customerCardType) {
        this.customerCardType = customerCardType;
        return this;
    }

    public void setCustomerCardType(CustomerCardType customerCardType) {
        this.customerCardType = customerCardType;
    }

    public ContractNature getContractNature() {
        return contractNature;
    }

    public ContractTemplate contractNature(ContractNature contractNature) {
        this.contractNature = contractNature;
        return this;
    }

    public void setContractNature(ContractNature contractNature) {
        this.contractNature = contractNature;
    }
    public String getName() {
        return name;
    }

    public ContractTemplate name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
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
        ContractTemplate contractTemplate = (ContractTemplate) o;
        if (contractTemplate.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), contractTemplate.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ContractTemplate{" +
            "id=" + getId() +
            ", totalMoneyAmount='" + getTotalMoneyAmount() + "'" +
            ", classCount='" + getClassCount() + "'" +
            ", totalMinutes='" + getTotalMinutes() + "'" +
            ", totalHours='" + getTotalHours() + "'" +
            ", years='" + getYears() + "'" +
            ", promotionAmount='" + getPromotionAmount() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            ", name='" + getName() + "'" +
            "}";
    }
}
