package com.pure.service.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A CustomerStatusReportDtl.
 */
@Entity
@Table(name = "customer_status_report_dtl")
public class CustomerStatusReportDtl implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "age_too_small_count")
    private Integer ageTooSmallCount;

    @Column(name = "error_information")
    private Integer errorInformation;

    @Column(name = "no_willing_count")
    private Integer noWillingCount;

    @Column(name = "considering_count")
    private Integer consideringCount;

    @Column(name = "scheduled_count")
    private Integer scheduledCount;

    @Column(name = "dealed_count")
    private Integer dealedCount;

    @Column(name = "new_created_count")
    private Integer newCreatedCount;

    @Column(name = "total_count")
    private Integer totalCount;

    @Column(name = "finish_rate")
    private Double finishRate;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public CustomerStatusReportDtl userId(Long userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public CustomerStatusReportDtl userName(String userName) {
        this.userName = userName;
        return this;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getAgeTooSmallCount() {
        return ageTooSmallCount;
    }

    public CustomerStatusReportDtl ageTooSmallCount(Integer ageTooSmallCount) {
        this.ageTooSmallCount = ageTooSmallCount;
        return this;
    }

    public void setAgeTooSmallCount(Integer ageTooSmallCount) {
        this.ageTooSmallCount = ageTooSmallCount;
    }

    public Integer getErrorInformation() {
        return errorInformation;
    }

    public CustomerStatusReportDtl errorInformation(Integer errorInformation) {
        this.errorInformation = errorInformation;
        return this;
    }

    public void setErrorInformation(Integer errorInformation) {
        this.errorInformation = errorInformation;
    }

    public Integer getNoWillingCount() {
        return noWillingCount;
    }

    public CustomerStatusReportDtl noWillingCount(Integer noWillingCount) {
        this.noWillingCount = noWillingCount;
        return this;
    }

    public void setNoWillingCount(Integer noWillingCount) {
        this.noWillingCount = noWillingCount;
    }

    public Integer getConsideringCount() {
        return consideringCount;
    }

    public CustomerStatusReportDtl consideringCount(Integer consideringCount) {
        this.consideringCount = consideringCount;
        return this;
    }

    public void setConsideringCount(Integer consideringCount) {
        this.consideringCount = consideringCount;
    }

    public Integer getScheduledCount() {
        return scheduledCount;
    }

    public CustomerStatusReportDtl scheduledCount(Integer scheduledCount) {
        this.scheduledCount = scheduledCount;
        return this;
    }

    public void setScheduledCount(Integer scheduledCount) {
        this.scheduledCount = scheduledCount;
    }

    public Integer getDealedCount() {
        return dealedCount;
    }

    public CustomerStatusReportDtl dealedCount(Integer dealedCount) {
        this.dealedCount = dealedCount;
        return this;
    }

    public void setDealedCount(Integer dealedCount) {
        this.dealedCount = dealedCount;
    }

    public Integer getNewCreatedCount() {
        return newCreatedCount;
    }

    public CustomerStatusReportDtl newCreatedCount(Integer newCreatedCount) {
        this.newCreatedCount = newCreatedCount;
        return this;
    }

    public void setNewCreatedCount(Integer newCreatedCount) {
        this.newCreatedCount = newCreatedCount;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public CustomerStatusReportDtl totalCount(Integer totalCount) {
        this.totalCount = totalCount;
        return this;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Double getFinishRate() {
        return finishRate;
    }

    public CustomerStatusReportDtl finishRate(Double finishRate) {
        this.finishRate = finishRate;
        return this;
    }

    public void setFinishRate(Double finishRate) {
        this.finishRate = finishRate;
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
        CustomerStatusReportDtl customerStatusReportDtl = (CustomerStatusReportDtl) o;
        if (customerStatusReportDtl.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), customerStatusReportDtl.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CustomerStatusReportDtl{" +
            "id=" + getId() +
            ", userId='" + getUserId() + "'" +
            ", userName='" + getUserName() + "'" +
            ", ageTooSmallCount='" + getAgeTooSmallCount() + "'" +
            ", errorInformation='" + getErrorInformation() + "'" +
            ", noWillingCount='" + getNoWillingCount() + "'" +
            ", consideringCount='" + getConsideringCount() + "'" +
            ", scheduledCount='" + getScheduledCount() + "'" +
            ", dealedCount='" + getDealedCount() + "'" +
            ", newCreatedCount='" + getNewCreatedCount() + "'" +
            ", totalCount='" + getTotalCount() + "'" +
            ", finishRate='" + getFinishRate() + "'" +
            "}";
    }
}
