package com.pure.service.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A CustomerCardType.
 */
@Entity
@Table(name = "customer_card_type")
public class CustomerCardType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private String code;

    @Column(name = "total_money_amount")
    private Float totalMoneyAmount;

    @Column(name = "class_count")
    private Integer classCount;

    @Column(name = "total_minutes")
    private Integer totalMinutes;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public CustomerCardType name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public CustomerCardType code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Float getTotalMoneyAmount() {
        return totalMoneyAmount;
    }

    public CustomerCardType totalMoneyAmount(Float totalMoneyAmount) {
        this.totalMoneyAmount = totalMoneyAmount;
        return this;
    }

    public void setTotalMoneyAmount(Float totalMoneyAmount) {
        this.totalMoneyAmount = totalMoneyAmount;
    }

    public Integer getClassCount() {
        return classCount;
    }

    public CustomerCardType classCount(Integer classCount) {
        this.classCount = classCount;
        return this;
    }

    public void setClassCount(Integer classCount) {
        this.classCount = classCount;
    }

    public Integer getTotalMinutes() {
        return totalMinutes;
    }

    public CustomerCardType totalMinutes(Integer totalMinutes) {
        this.totalMinutes = totalMinutes;
        return this;
    }

    public void setTotalMinutes(Integer totalMinutes) {
        this.totalMinutes = totalMinutes;
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
        CustomerCardType customerCardType = (CustomerCardType) o;
        if (customerCardType.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), customerCardType.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CustomerCardType{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", code='" + getCode() + "'" +
            ", totalMoneyAmount='" + getTotalMoneyAmount() + "'" +
            ", classCount='" + getClassCount() + "'" +
            ", totalMinutes='" + getTotalMinutes() + "'" +
            "}";
    }
}
