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
 * A CustomerConsumerLog.
 */
@Entity
@Table(name = "customer_consumer_log")
public class CustomerConsumerLog extends AbstractAuditingRegionEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "count")
    private Float count;

    @Column(name = "unit")
    private String unit;

    @Column(name = "unique_number")
    private String uniqueNumber;



    @Column(name = "consumer_name")
    private String consumerName;

    @ManyToOne
    private CustomerConsumerType customerConsumerType;

    @ManyToOne
    private Student student;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getCount() {
        return count;
    }

    public CustomerConsumerLog count(Float count) {
        this.count = count;
        return this;
    }

    public void setCount(Float count) {
        this.count = count;
    }

    public String getUnit() {
        return unit;
    }

    public CustomerConsumerLog unit(String unit) {
        this.unit = unit;
        return this;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getUniqueNumber() {
        return uniqueNumber;
    }

    public CustomerConsumerLog uniqueNumber(String uniqueNumber) {
        this.uniqueNumber = uniqueNumber;
        return this;
    }

    public void setUniqueNumber(String uniqueNumber) {
        this.uniqueNumber = uniqueNumber;
    }


    public String getConsumerName() {
        return consumerName;
    }

    public CustomerConsumerLog consumerName(String consumerName) {
        this.consumerName = consumerName;
        return this;
    }

    public void setConsumerName(String consumerName) {
        this.consumerName = consumerName;
    }

    public CustomerConsumerType getCustomerConsumerType() {
        return customerConsumerType;
    }

    public CustomerConsumerLog customerConsumerType(CustomerConsumerType customerConsumerType) {
        this.customerConsumerType = customerConsumerType;
        return this;
    }

    public void setCustomerConsumerType(CustomerConsumerType customerConsumerType) {
        this.customerConsumerType = customerConsumerType;
    }

    public Student getStudent() {
        return student;
    }

    public CustomerConsumerLog student(Student student) {
        this.student = student;
        return this;
    }

    public void setStudent(Student student) {
        this.student = student;
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
        CustomerConsumerLog customerConsumerLog = (CustomerConsumerLog) o;
        if (customerConsumerLog.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), customerConsumerLog.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CustomerConsumerLog{" +
            "id=" + getId() +
            ", count='" + getCount() + "'" +
            ", unit='" + getUnit() + "'" +
            ", uniqueNumber='" + getUniqueNumber() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            ", consumerName='" + getConsumerName() + "'" +
            "}";
    }
}
