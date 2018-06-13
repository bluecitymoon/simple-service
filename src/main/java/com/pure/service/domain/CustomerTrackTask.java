package com.pure.service.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A CustomerTrackTask.
 */
@Entity
@Table(name = "customer_track_task")
public class CustomerTrackTask implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Customer customer;

    @OneToOne
    @JoinColumn(unique = true)
    private Task task;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public CustomerTrackTask customer(Customer customer) {
        this.customer = customer;
        return this;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Task getTask() {
        return task;
    }

    public CustomerTrackTask task(Task task) {
        this.task = task;
        return this;
    }

    public void setTask(Task task) {
        this.task = task;
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
        CustomerTrackTask customerTrackTask = (CustomerTrackTask) o;
        if (customerTrackTask.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), customerTrackTask.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CustomerTrackTask{" +
            "id=" + getId() +
            "}";
    }
}
