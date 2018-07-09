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
 * A Payment.
 */
@Entity
@Table(name = "payment")
public class Payment extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "project_name")
    private String projectName;

    @Column(name = "estimate_amount")
    private Float estimateAmount;

    @Column(name = "actual_amount")
    private Float actualAmount;

    @Column(name = "paied")
    private Float paied;

    @Column(name = "unpaid")
    private Float unpaid;

    @Column(name = "paid_date")
    private Instant paidDate;

    @Column(name = "comments")
    private String comments;


    @ManyToOne
    private User paidUser;

    @ManyToOne
    private PaymentType paymentType;

    @ManyToOne
    private FinanceCategory financeCategory;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public Payment projectName(String projectName) {
        this.projectName = projectName;
        return this;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Float getEstimateAmount() {
        return estimateAmount;
    }

    public Payment estimateAmount(Float estimateAmount) {
        this.estimateAmount = estimateAmount;
        return this;
    }

    public void setEstimateAmount(Float estimateAmount) {
        this.estimateAmount = estimateAmount;
    }

    public Float getActualAmount() {
        return actualAmount;
    }

    public Payment actualAmount(Float actualAmount) {
        this.actualAmount = actualAmount;
        return this;
    }

    public void setActualAmount(Float actualAmount) {
        this.actualAmount = actualAmount;
    }

    public Float getPaied() {
        return paied;
    }

    public Payment paied(Float paied) {
        this.paied = paied;
        return this;
    }

    public void setPaied(Float paied) {
        this.paied = paied;
    }

    public Float getUnpaid() {
        return unpaid;
    }

    public Payment unpaid(Float unpaid) {
        this.unpaid = unpaid;
        return this;
    }

    public void setUnpaid(Float unpaid) {
        this.unpaid = unpaid;
    }

    public Instant getPaidDate() {
        return paidDate;
    }

    public Payment paidDate(Instant paidDate) {
        this.paidDate = paidDate;
        return this;
    }

    public void setPaidDate(Instant paidDate) {
        this.paidDate = paidDate;
    }

    public String getComments() {
        return comments;
    }

    public Payment comments(String comments) {
        this.comments = comments;
        return this;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public User getPaidUser() {
        return paidUser;
    }

    public Payment paidUser(User user) {
        this.paidUser = user;
        return this;
    }

    public void setPaidUser(User user) {
        this.paidUser = user;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public Payment paymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
        return this;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public FinanceCategory getFinanceCategory() {
        return financeCategory;
    }

    public Payment financeCategory(FinanceCategory financeCategory) {
        this.financeCategory = financeCategory;
        return this;
    }

    public void setFinanceCategory(FinanceCategory financeCategory) {
        this.financeCategory = financeCategory;
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
        Payment payment = (Payment) o;
        if (payment.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), payment.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Payment{" +
            "id=" + getId() +
            ", projectName='" + getProjectName() + "'" +
            ", estimateAmount='" + getEstimateAmount() + "'" +
            ", actualAmount='" + getActualAmount() + "'" +
            ", paied='" + getPaied() + "'" +
            ", unpaid='" + getUnpaid() + "'" +
            ", paidDate='" + getPaidDate() + "'" +
            ", comments='" + getComments() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}
