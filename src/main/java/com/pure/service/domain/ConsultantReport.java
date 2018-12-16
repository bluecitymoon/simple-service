package com.pure.service.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;
import java.util.Objects;

/**
 * A ConsultantReport.
 */
@Entity
@Table(name = "consultant_report")
public class ConsultantReport extends AbstractRegionEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "week_name")
    private Integer weekName;

    @Column(name = "week_from_date")
    private Instant weekFromDate;

    @Column(name = "week_end_date")
    private Instant weekEndDate;

    @Column(name = "visited_count")
    private Integer visitedCount;

    @Column(name = "dealed_money_amount")
    private Float dealedMoneyAmount;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getWeekName() {
        return weekName;
    }

    public ConsultantReport weekName(Integer weekName) {
        this.weekName = weekName;
        return this;
    }

    public void setWeekName(Integer weekName) {
        this.weekName = weekName;
    }

    public Instant getWeekFromDate() {
        return weekFromDate;
    }

    public ConsultantReport weekFromDate(Instant weekFromDate) {
        this.weekFromDate = weekFromDate;
        return this;
    }

    public void setWeekFromDate(Instant weekFromDate) {
        this.weekFromDate = weekFromDate;
    }

    public Instant getWeekEndDate() {
        return weekEndDate;
    }

    public ConsultantReport weekEndDate(Instant weekEndDate) {
        this.weekEndDate = weekEndDate;
        return this;
    }

    public void setWeekEndDate(Instant weekEndDate) {
        this.weekEndDate = weekEndDate;
    }

    public Integer getVisitedCount() {
        return visitedCount;
    }

    public ConsultantReport visitedCount(Integer visitedCount) {
        this.visitedCount = visitedCount;
        return this;
    }

    public void setVisitedCount(Integer visitedCount) {
        this.visitedCount = visitedCount;
    }

    public Float getDealedMoneyAmount() {
        return dealedMoneyAmount;
    }

    public ConsultantReport dealedMoneyAmount(Float dealedMoneyAmount) {
        this.dealedMoneyAmount = dealedMoneyAmount;
        return this;
    }

    public void setDealedMoneyAmount(Float dealedMoneyAmount) {
        this.dealedMoneyAmount = dealedMoneyAmount;
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
        ConsultantReport consultantReport = (ConsultantReport) o;
        if (consultantReport.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), consultantReport.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ConsultantReport{" +
            "id=" + getId() +
            ", weekName='" + getWeekName() + "'" +
            ", weekFromDate='" + getWeekFromDate() + "'" +
            ", weekEndDate='" + getWeekEndDate() + "'" +
            ", visitedCount='" + getVisitedCount() + "'" +
            ", dealedMoneyAmount='" + getDealedMoneyAmount() + "'" +
            "}";
    }
}
