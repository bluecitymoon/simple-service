package com.pure.service.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A FreeClassPlan.
 */
@Entity
@Table(name = "free_class_plan")
public class FreeClassPlan implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "plan_date")
    private Instant planDate;

    @Column(name = "limit_count")
    private Integer limitCount = 0;

    @Column(name = "actual_count")
    private Integer actualCount = 0;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getPlanDate() {
        return planDate;
    }

    public FreeClassPlan planDate(Instant planDate) {
        this.planDate = planDate;
        return this;
    }

    public void setPlanDate(Instant planDate) {
        this.planDate = planDate;
    }

    public Integer getLimitCount() {
        return limitCount;
    }

    public FreeClassPlan limitCount(Integer limitCount) {
        this.limitCount = limitCount;
        return this;
    }

    public void setLimitCount(Integer limitCount) {
        this.limitCount = limitCount;
    }

    public Integer getActualCount() {
        return actualCount;
    }

    public FreeClassPlan actualCount(Integer actualCount) {
        this.actualCount = actualCount;
        return this;
    }

    public void setActualCount(Integer actualCount) {
        this.actualCount = actualCount;
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
        FreeClassPlan freeClassPlan = (FreeClassPlan) o;
        if (freeClassPlan.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), freeClassPlan.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FreeClassPlan{" +
            "id=" + getId() +
            ", planDate='" + getPlanDate() + "'" +
            ", limitCount='" + getLimitCount() + "'" +
            ", actualCount='" + getActualCount() + "'" +
            "}";
    }
}
