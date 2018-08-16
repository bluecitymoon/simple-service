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
 * A ClassArrangement.
 */
@Entity
@Table(name = "class_arrangement")
public class ClassArrangement extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start_date")
    private Instant startDate;

    @Column(name = "end_date")
    private Instant endDate;

    @ManyToOne
    private Teacher planedTeacher;

    @ManyToOne
    private Teacher actualTeacher;

    @ManyToOne
    private ClassArrangementStatus status;

    @ManyToOne
    private Product clazz;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public ClassArrangement startDate(Instant startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public ClassArrangement endDate(Instant endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public Teacher getPlanedTeacher() {
        return planedTeacher;
    }

    public ClassArrangement planedTeacher(Teacher teacher) {
        this.planedTeacher = teacher;
        return this;
    }

    public void setPlanedTeacher(Teacher teacher) {
        this.planedTeacher = teacher;
    }

    public Teacher getActualTeacher() {
        return actualTeacher;
    }

    public ClassArrangement actualTeacher(Teacher teacher) {
        this.actualTeacher = teacher;
        return this;
    }

    public void setActualTeacher(Teacher teacher) {
        this.actualTeacher = teacher;
    }

    public ClassArrangementStatus getStatus() {
        return status;
    }

    public ClassArrangement status(ClassArrangementStatus classArrangementStatus) {
        this.status = classArrangementStatus;
        return this;
    }

    public void setStatus(ClassArrangementStatus classArrangementStatus) {
        this.status = classArrangementStatus;
    }

    public Product getClazz() {
        return clazz;
    }

    public ClassArrangement clazz(Product product) {
        this.clazz = product;
        return this;
    }

    public void setClazz(Product product) {
        this.clazz = product;
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
        ClassArrangement classArrangement = (ClassArrangement) o;
        if (classArrangement.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), classArrangement.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ClassArrangement{" +
            "id=" + getId() +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}
