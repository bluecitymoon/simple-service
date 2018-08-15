package com.pure.service.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A ClassArrangementRule.
 */
@Entity
@Table(name = "class_arrangement_rule")
public class ClassArrangementRule implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "estimate_start_date")
    private Instant estimateStartDate;

    @Column(name = "estimate_end_date")
    private Instant estimateDate;

    @Column(name = "count_number")
    private Integer countNumber;

    @Column(name = "estimate_start_time")
    private String estimateStartTime;

    @Column(name = "estimate_end_time")
    private String estimateEndTime;

    @Column(name = "max_loop_count")
    private Integer maxLoopCount;

    @ManyToOne
    private Product targetClass;

    @ManyToOne
    private ClassArrangementRuleLoopWay loopWay;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getEstimateStartDate() {
        return estimateStartDate;
    }

    public ClassArrangementRule estimateStartDate(Instant estimateStartDate) {
        this.estimateStartDate = estimateStartDate;
        return this;
    }

    public Instant getEstimateDate() {
        return estimateDate;
    }

    public void setEstimateDate(Instant estimateDate) {
        this.estimateDate = estimateDate;
    }

    public Integer getCountNumber() {
        return countNumber;
    }

    public void setCountNumber(Integer countNumber) {
        this.countNumber = countNumber;
    }

    public void setEstimateStartDate(Instant estimateStartDate) {
        this.estimateStartDate = estimateStartDate;
    }

    public String getEstimateStartTime() {
        return estimateStartTime;
    }

    public ClassArrangementRule estimateStartTime(String estimateStartTime) {
        this.estimateStartTime = estimateStartTime;
        return this;
    }

    public void setEstimateStartTime(String estimateStartTime) {
        this.estimateStartTime = estimateStartTime;
    }

    public String getEstimateEndTime() {
        return estimateEndTime;
    }

    public ClassArrangementRule estimateEndTime(String estimateEndTime) {
        this.estimateEndTime = estimateEndTime;
        return this;
    }

    public void setEstimateEndTime(String estimateEndTime) {
        this.estimateEndTime = estimateEndTime;
    }

    public Integer getMaxLoopCount() {
        return maxLoopCount;
    }

    public ClassArrangementRule maxLoopCount(Integer maxLoopCount) {
        this.maxLoopCount = maxLoopCount;
        return this;
    }

    public void setMaxLoopCount(Integer maxLoopCount) {
        this.maxLoopCount = maxLoopCount;
    }

    public Product getTargetClass() {
        return targetClass;
    }

    public ClassArrangementRule targetClass(Product product) {
        this.targetClass = product;
        return this;
    }

    public void setTargetClass(Product product) {
        this.targetClass = product;
    }

    public ClassArrangementRuleLoopWay getLoopWay() {
        return loopWay;
    }

    public ClassArrangementRule loopWay(ClassArrangementRuleLoopWay classArrangementRuleLoopWay) {
        this.loopWay = classArrangementRuleLoopWay;
        return this;
    }

    public void setLoopWay(ClassArrangementRuleLoopWay classArrangementRuleLoopWay) {
        this.loopWay = classArrangementRuleLoopWay;
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
        ClassArrangementRule classArrangementRule = (ClassArrangementRule) o;
        if (classArrangementRule.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), classArrangementRule.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ClassArrangementRule{" +
            "id=" + getId() +
            ", estimateStartDate='" + getEstimateStartDate() + "'" +
            ", estimateStartTime='" + getEstimateStartTime() + "'" +
            ", estimateEndTime='" + getEstimateEndTime() + "'" +
            ", maxLoopCount='" + getMaxLoopCount() + "'" +
            "}";
    }
}
