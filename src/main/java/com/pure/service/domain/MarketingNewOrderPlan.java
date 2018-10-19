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
 * A MarketingNewOrderPlan.
 */
@Entity
@Table(name = "marketing_new_order_plan")
public class MarketingNewOrderPlan extends AbstractAuditingRegionEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "jhi_year")
    private String year;

    @Column(name = "month")
    private String month;

    @Column(name = "target_number")
    private Integer targetNumber;

    @Column(name = "current_number")
    private Integer currentNumber;

    @Column(name = "finished")
    private Boolean finished;

    @Column(name = "percentage")
    private Float percentage;

    @ManyToOne
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getYear() {
        return year;
    }

    public MarketingNewOrderPlan year(String year) {
        this.year = year;
        return this;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public MarketingNewOrderPlan month(String month) {
        this.month = month;
        return this;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Integer getTargetNumber() {
        return targetNumber;
    }

    public MarketingNewOrderPlan targetNumber(Integer targetNumber) {
        this.targetNumber = targetNumber;
        return this;
    }

    public void setTargetNumber(Integer targetNumber) {
        this.targetNumber = targetNumber;
    }

    public Integer getCurrentNumber() {
        return currentNumber;
    }

    public MarketingNewOrderPlan currentNumber(Integer currentNumber) {
        this.currentNumber = currentNumber;
        return this;
    }

    public void setCurrentNumber(Integer currentNumber) {
        this.currentNumber = currentNumber;
    }

    public Boolean isFinished() {
        return finished;
    }

    public MarketingNewOrderPlan finished(Boolean finished) {
        this.finished = finished;
        return this;
    }

    public void setFinished(Boolean finished) {
        this.finished = finished;
    }

    public Float getPercentage() {
        return percentage;
    }

    public MarketingNewOrderPlan percentage(Float percentage) {
        this.percentage = percentage;
        return this;
    }

    public void setPercentage(Float percentage) {
        this.percentage = percentage;
    }

    public User getUser() {
        return user;
    }

    public MarketingNewOrderPlan user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
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
        MarketingNewOrderPlan marketingNewOrderPlan = (MarketingNewOrderPlan) o;
        if (marketingNewOrderPlan.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), marketingNewOrderPlan.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MarketingNewOrderPlan{" +
            "id=" + getId() +
            ", year='" + getYear() + "'" +
            ", month='" + getMonth() + "'" +
            ", targetNumber='" + getTargetNumber() + "'" +
            ", currentNumber='" + getCurrentNumber() + "'" +
            ", finished='" + isFinished() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            ", percentage='" + getPercentage() + "'" +
            "}";
    }
}
