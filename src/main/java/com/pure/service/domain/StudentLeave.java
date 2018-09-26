package com.pure.service.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A StudentLeave.
 */
@Entity
@Table(name = "student_leave")
public class StudentLeave implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "balance")
    private Integer balance;

    @Column(name = "calculate_start_date")
    private Instant calculateStartDate;

    @Column(name = "calculate_end_date")
    private Instant calculateEndDate;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @Column(name = "last_modified_date")
    private Instant lastModifiedDate;

    @ManyToOne
    private Student student;

    @ManyToOne
    private ClassArrangement classArrangement;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getBalance() {
        return balance;
    }

    public StudentLeave balance(Integer balance) {
        this.balance = balance;
        return this;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public Instant getCalculateStartDate() {
        return calculateStartDate;
    }

    public StudentLeave calculateStartDate(Instant calculateStartDate) {
        this.calculateStartDate = calculateStartDate;
        return this;
    }

    public void setCalculateStartDate(Instant calculateStartDate) {
        this.calculateStartDate = calculateStartDate;
    }

    public Instant getCalculateEndDate() {
        return calculateEndDate;
    }

    public StudentLeave calculateEndDate(Instant calculateEndDate) {
        this.calculateEndDate = calculateEndDate;
        return this;
    }

    public void setCalculateEndDate(Instant calculateEndDate) {
        this.calculateEndDate = calculateEndDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public StudentLeave createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public StudentLeave createdDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public StudentLeave lastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public StudentLeave lastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
        return this;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Student getStudent() {
        return student;
    }

    public StudentLeave student(Student student) {
        this.student = student;
        return this;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public ClassArrangement getClassArrangement() {
        return classArrangement;
    }

    public StudentLeave classArrangement(ClassArrangement classArrangement) {
        this.classArrangement = classArrangement;
        return this;
    }

    public void setClassArrangement(ClassArrangement classArrangement) {
        this.classArrangement = classArrangement;
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
        StudentLeave studentLeave = (StudentLeave) o;
        if (studentLeave.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), studentLeave.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "StudentLeave{" +
            "id=" + getId() +
            ", balance='" + getBalance() + "'" +
            ", calculateStartDate='" + getCalculateStartDate() + "'" +
            ", calculateEndDate='" + getCalculateEndDate() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}
