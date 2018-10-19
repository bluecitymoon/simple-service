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
 * A CustomerScheduleFeedback.
 */
@Entity
@Table(name = "customer_schedule_feedback")
public class CustomerScheduleFeedback extends AbstractAuditingRegionEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "gift_code")
    private String giftCode;

    @Column(name = "gift_status")
    private String giftStatus;


    @ManyToOne
    private Customer customer;

    @ManyToOne
    private CustomerCommunicationSchedule schedule;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGiftCode() {
        return giftCode;
    }

    public CustomerScheduleFeedback giftCode(String giftCode) {
        this.giftCode = giftCode;
        return this;
    }

    public void setGiftCode(String giftCode) {
        this.giftCode = giftCode;
    }

    public String getGiftStatus() {
        return giftStatus;
    }

    public CustomerScheduleFeedback giftStatus(String giftStatus) {
        this.giftStatus = giftStatus;
        return this;
    }

    public void setGiftStatus(String giftStatus) {
        this.giftStatus = giftStatus;
    }



    public Customer getCustomer() {
        return customer;
    }

    public CustomerScheduleFeedback customer(Customer customer) {
        this.customer = customer;
        return this;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public CustomerCommunicationSchedule getSchedule() {
        return schedule;
    }

    public CustomerScheduleFeedback schedule(CustomerCommunicationSchedule customerCommunicationSchedule) {
        this.schedule = customerCommunicationSchedule;
        return this;
    }

    public void setSchedule(CustomerCommunicationSchedule customerCommunicationSchedule) {
        this.schedule = customerCommunicationSchedule;
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
        CustomerScheduleFeedback customerScheduleFeedback = (CustomerScheduleFeedback) o;
        if (customerScheduleFeedback.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), customerScheduleFeedback.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CustomerScheduleFeedback{" +
            "id=" + getId() +
            ", giftCode='" + getGiftCode() + "'" +
            ", giftStatus='" + getGiftStatus() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}
