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
 * A StudentClassLog.
 */
@Entity
@Table(name = "student_class_log")
public class StudentClassLog extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "actual_taken_date")
    private Instant actualTakenDate;

    @Column(name = "comments")
    private String comments;

    @Column(name = "unique_number")
    private String uniqueNumber;

    @Column(name = "point")
    private Integer point;

    @ManyToOne
    private Student student;

    @ManyToOne
    private ClassArrangement arrangement;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUniqueNumber() {
        return uniqueNumber;
    }

    public void setUniqueNumber(String uniqueNumber) {
        this.uniqueNumber = uniqueNumber;
    }

    public Instant getActualTakenDate() {
        return actualTakenDate;
    }

    public StudentClassLog actualTakenDate(Instant actualTakenDate) {
        this.actualTakenDate = actualTakenDate;
        return this;
    }

    public void setActualTakenDate(Instant actualTakenDate) {
        this.actualTakenDate = actualTakenDate;
    }

    public String getComments() {
        return comments;
    }

    public StudentClassLog comments(String comments) {
        this.comments = comments;
        return this;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }


    public Integer getPoint() {
        return point;
    }

    public StudentClassLog point(Integer point) {
        this.point = point;
        return this;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public Student getStudent() {
        return student;
    }

    public StudentClassLog student(Student student) {
        this.student = student;
        return this;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public ClassArrangement getArrangement() {
        return arrangement;
    }

    public StudentClassLog arrangement(ClassArrangement classArrangement) {
        this.arrangement = classArrangement;
        return this;
    }

    public void setArrangement(ClassArrangement classArrangement) {
        this.arrangement = classArrangement;
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
        StudentClassLog studentClassLog = (StudentClassLog) o;
        if (studentClassLog.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), studentClassLog.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "StudentClassLog{" +
            "id=" + getId() +
            ", actualTakenDate='" + getActualTakenDate() + "'" +
            ", comments='" + getComments() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            ", point='" + getPoint() + "'" +
            "}";
    }
}
