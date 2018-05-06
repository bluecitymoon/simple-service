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
 * A Product.
 */
@Entity
@Table(name = "product")
public class Product extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private String code;

    @Column(name = "minutes_per_class")
    private Float minutesPerClass;

    @Column(name = "planed_start_date")
    private Instant planedStartDate;

    @Column(name = "comments")
    private String comments;

    @ManyToOne
    private ClassAgeLevel classAgeLevel;

    @ManyToOne
    private Teacher teacher;

    @ManyToOne
    private ClassRoom classRoom;

    @ManyToOne
    private Course course;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Product name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public Product code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Float getMinutesPerClass() {
        return minutesPerClass;
    }

    public Product minutesPerClass(Float minutesPerClass) {
        this.minutesPerClass = minutesPerClass;
        return this;
    }

    public void setMinutesPerClass(Float minutesPerClass) {
        this.minutesPerClass = minutesPerClass;
    }

    public Instant getPlanedStartDate() {
        return planedStartDate;
    }

    public Product planedStartDate(Instant planedStartDate) {
        this.planedStartDate = planedStartDate;
        return this;
    }

    public void setPlanedStartDate(Instant planedStartDate) {
        this.planedStartDate = planedStartDate;
    }

    public String getComments() {
        return comments;
    }

    public Product comments(String comments) {
        this.comments = comments;
        return this;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }



    public ClassAgeLevel getClassAgeLevel() {
        return classAgeLevel;
    }

    public Product classAgeLevel(ClassAgeLevel classAgeLevel) {
        this.classAgeLevel = classAgeLevel;
        return this;
    }

    public void setClassAgeLevel(ClassAgeLevel classAgeLevel) {
        this.classAgeLevel = classAgeLevel;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public Product teacher(Teacher teacher) {
        this.teacher = teacher;
        return this;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public ClassRoom getClassRoom() {
        return classRoom;
    }

    public Product classRoom(ClassRoom classRoom) {
        this.classRoom = classRoom;
        return this;
    }

    public void setClassRoom(ClassRoom classRoom) {
        this.classRoom = classRoom;
    }

    public Course getCourse() {
        return course;
    }

    public Product course(Course course) {
        this.course = course;
        return this;
    }

    public void setCourse(Course course) {
        this.course = course;
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
        Product product = (Product) o;
        if (product.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), product.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Product{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", code='" + getCode() + "'" +
            ", minutesPerClass='" + getMinutesPerClass() + "'" +
            ", planedStartDate='" + getPlanedStartDate() + "'" +
            ", comments='" + getComments() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}
