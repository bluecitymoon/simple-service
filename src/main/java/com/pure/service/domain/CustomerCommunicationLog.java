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
 * A CustomerCommunicationLog.
 */
@Entity
@Table(name = "customer_communication_log")
public class CustomerCommunicationLog extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "comments")
    private String comments;

    @ManyToOne
    private CustomerCommunicationLogType logType;

    @ManyToOne
    private Customer customer;

    @ManyToOne
    private FreeClassRecord freeClassRecord;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComments() {
        return comments;
    }

    public CustomerCommunicationLog comments(String comments) {
        this.comments = comments;
        return this;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public CustomerCommunicationLogType getLogType() {
        return logType;
    }

    public CustomerCommunicationLog logType(CustomerCommunicationLogType customerCommunicationLogType) {
        this.logType = customerCommunicationLogType;
        return this;
    }

    public void setLogType(CustomerCommunicationLogType customerCommunicationLogType) {
        this.logType = customerCommunicationLogType;
    }

    public Customer getCustomer() {
        return customer;
    }

    public CustomerCommunicationLog customer(Customer customer) {
        this.customer = customer;
        return this;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public FreeClassRecord getFreeClassRecord() {
        return freeClassRecord;
    }

    public CustomerCommunicationLog freeClassRecord(FreeClassRecord freeClassRecord) {
        this.freeClassRecord = freeClassRecord;
        return this;
    }

    public void setFreeClassRecord(FreeClassRecord freeClassRecord) {
        this.freeClassRecord = freeClassRecord;
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
        CustomerCommunicationLog customerCommunicationLog = (CustomerCommunicationLog) o;
        if (customerCommunicationLog.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), customerCommunicationLog.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CustomerCommunicationLog{" +
            "id=" + getId() +
            ", comments='" + getComments() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}
