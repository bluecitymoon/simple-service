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
 * A StudentFrozenArrangement.
 */
@Entity
@Table(name = "student_frozen_arrangement")
public class StudentFrozenArrangement extends AbstractAuditingRegionEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "active")
    private Boolean active;

    @ManyToOne
    private Student student;

    @ManyToOne
    private ClassArrangement classArrangement;

    @ManyToOne
    private StudentFrozen studentFrozen;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Boolean isActive() {
        return active;
    }

    public StudentFrozenArrangement active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Student getStudent() {
        return student;
    }

    public StudentFrozenArrangement student(Student student) {
        this.student = student;
        return this;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public ClassArrangement getClassArrangement() {
        return classArrangement;
    }

    public StudentFrozenArrangement classArrangement(ClassArrangement classArrangement) {
        this.classArrangement = classArrangement;
        return this;
    }

    public void setClassArrangement(ClassArrangement classArrangement) {
        this.classArrangement = classArrangement;
    }

    public StudentFrozen getStudentFrozen() {
        return studentFrozen;
    }

    public StudentFrozenArrangement studentFrozen(StudentFrozen studentFrozen) {
        this.studentFrozen = studentFrozen;
        return this;
    }

    public void setStudentFrozen(StudentFrozen studentFrozen) {
        this.studentFrozen = studentFrozen;
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
        StudentFrozenArrangement studentFrozenArrangement = (StudentFrozenArrangement) o;
        if (studentFrozenArrangement.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), studentFrozenArrangement.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "StudentFrozenArrangement{" +
            "id=" + getId() +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}
