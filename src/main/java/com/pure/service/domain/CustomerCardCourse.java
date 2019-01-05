package com.pure.service.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A CustomerCardCourse.
 */
@Entity
@Table(name = "customer_card_course")
public class CustomerCardCourse implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private CustomerCard customerCard;

    @ManyToOne
    private Course course;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CustomerCard getCustomerCard() {
        return customerCard;
    }

    public CustomerCardCourse customerCard(CustomerCard customerCard) {
        this.customerCard = customerCard;
        return this;
    }

    public void setCustomerCard(CustomerCard customerCard) {
        this.customerCard = customerCard;
    }

    public Course getCourse() {
        return course;
    }

    public CustomerCardCourse course(Course course) {
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
        CustomerCardCourse customerCardCourse = (CustomerCardCourse) o;
        if (customerCardCourse.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), customerCardCourse.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CustomerCardCourse{" +
            "id=" + getId() +
            "}";
    }
}
