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
 * A StudentClassInOutLog.
 */
@Entity
@Table(name = "student_class_in_out_log")
public class StudentClassInOutLog extends AbstractAuditingRegionEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "comments")
    private String comments;

    @ManyToOne
    private Student student;

    @ManyToOne
    private Product oldClass;

    @ManyToOne
    private Product newClass;

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

    public StudentClassInOutLog comments(String comments) {
        this.comments = comments;
        return this;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }


    public Student getStudent() {
        return student;
    }

    public StudentClassInOutLog student(Student student) {
        this.student = student;
        return this;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Product getOldClass() {
        return oldClass;
    }

    public StudentClassInOutLog oldClass(Product product) {
        this.oldClass = product;
        return this;
    }

    public void setOldClass(Product product) {
        this.oldClass = product;
    }

    public Product getNewClass() {
        return newClass;
    }

    public StudentClassInOutLog newClass(Product product) {
        this.newClass = product;
        return this;
    }

    public void setNewClass(Product product) {
        this.newClass = product;
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
        StudentClassInOutLog studentClassInOutLog = (StudentClassInOutLog) o;
        if (studentClassInOutLog.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), studentClassInOutLog.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "StudentClassInOutLog{" +
            "id=" + getId() +
            ", comments='" + getComments() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}
