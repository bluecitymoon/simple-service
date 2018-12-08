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
 * A StudentAbsenceLog.
 */
@Entity
@Table(name = "student_absence_log")
public class StudentAbsenceLog extends AbstractAuditingRegionEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "comments")
    private String comments;

    @Column(name = "class_count")
    private Integer classCount;

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

    public String getComments() {
        return comments;
    }

    public StudentAbsenceLog comments(String comments) {
        this.comments = comments;
        return this;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }


    public Integer getClassCount() {
        return classCount;
    }

    public StudentAbsenceLog classCount(Integer classCount) {
        this.classCount = classCount;
        return this;
    }

    public void setClassCount(Integer classCount) {
        this.classCount = classCount;
    }

    public Student getStudent() {
        return student;
    }

    public StudentAbsenceLog student(Student student) {
        this.student = student;
        return this;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public ClassArrangement getClassArrangement() {
        return classArrangement;
    }

    public StudentAbsenceLog classArrangement(ClassArrangement classArrangement) {
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
        StudentAbsenceLog studentAbsenceLog = (StudentAbsenceLog) o;
        if (studentAbsenceLog.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), studentAbsenceLog.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "StudentAbsenceLog{" +
            "id=" + getId() +
            ", comments='" + getComments() + "'" +
            ", regionId='" + getRegionId() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            ", classCount='" + getClassCount() + "'" +
            "}";
    }
}
