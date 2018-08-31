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
 * A CustomerCommunicationSchedule.
 */
@Entity
@Table(name = "customer_schedule")
public class CustomerCommunicationSchedule extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "scedule_date")
    private Instant sceduleDate;

    @Column(name = "comments")
    private String comments;

    @Column(name = "actuall_meet_date")
    private Instant actuallMeetDate;

    @Column(name = "source_type")
    private String sourceType;

    @ManyToOne
    private Customer customer;

    @ManyToOne
    private User follower;

    @ManyToOne
    private CustomerScheduleStatus scheduleStatus;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove


    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getSceduleDate() {
        return sceduleDate;
    }

    public CustomerCommunicationSchedule sceduleDate(Instant sceduleDate) {
        this.sceduleDate = sceduleDate;
        return this;
    }

    public void setSceduleDate(Instant sceduleDate) {
        this.sceduleDate = sceduleDate;
    }

    public String getComments() {
        return comments;
    }

    public CustomerCommunicationSchedule comments(String comments) {
        this.comments = comments;
        return this;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }


    public Instant getActuallMeetDate() {
        return actuallMeetDate;
    }

    public CustomerCommunicationSchedule actuallMeetDate(Instant actuallMeetDate) {
        this.actuallMeetDate = actuallMeetDate;
        return this;
    }

    public void setActuallMeetDate(Instant actuallMeetDate) {
        this.actuallMeetDate = actuallMeetDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public CustomerCommunicationSchedule customer(Customer customer) {
        this.customer = customer;
        return this;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public User getFollower() {
        return follower;
    }

    public CustomerCommunicationSchedule follower(User user) {
        this.follower = user;
        return this;
    }

    public void setFollower(User user) {
        this.follower = user;
    }

    public CustomerScheduleStatus getScheduleStatus() {
        return scheduleStatus;
    }

    public CustomerCommunicationSchedule scheduleStatus(CustomerScheduleStatus customerScheduleStatus) {
        this.scheduleStatus = customerScheduleStatus;
        return this;
    }

    public void setScheduleStatus(CustomerScheduleStatus customerScheduleStatus) {
        this.scheduleStatus = customerScheduleStatus;
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
        CustomerCommunicationSchedule customerCommunicationSchedule = (CustomerCommunicationSchedule) o;
        if (customerCommunicationSchedule.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), customerCommunicationSchedule.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CustomerCommunicationSchedule{" +
            "id=" + getId() +
            ", sceduleDate='" + getSceduleDate() + "'" +
            ", comments='" + getComments() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            ", actuallMeetDate='" + getActuallMeetDate() + "'" +
            "}";
    }
}
