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
 * A CustomerCousultantAssignHistory.
 */
@Entity
@Table(name = "cust_cc_assign_history")
public class CustomerCousultantAssignHistory extends AbstractAuditingRegionEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "older_follower_login")
    private String olderFollowerLogin;

    @Column(name = "older_follower_name")
    private String olderFollowerName;

    @Column(name = "new_follower_login")
    private String newFollowerLogin;

    @Column(name = "new_follower_name")
    private String newFollowerName;

    @Column(name = "comments")
    private String comments;

    @ManyToOne
    private Customer customer;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOlderFollowerLogin() {
        return olderFollowerLogin;
    }

    public CustomerCousultantAssignHistory olderFollowerLogin(String olderFollowerLogin) {
        this.olderFollowerLogin = olderFollowerLogin;
        return this;
    }

    public void setOlderFollowerLogin(String olderFollowerLogin) {
        this.olderFollowerLogin = olderFollowerLogin;
    }

    public String getOlderFollowerName() {
        return olderFollowerName;
    }

    public CustomerCousultantAssignHistory olderFollowerName(String olderFollowerName) {
        this.olderFollowerName = olderFollowerName;
        return this;
    }

    public void setOlderFollowerName(String olderFollowerName) {
        this.olderFollowerName = olderFollowerName;
    }

    public String getNewFollowerLogin() {
        return newFollowerLogin;
    }

    public CustomerCousultantAssignHistory newFollowerLogin(String newFollowerLogin) {
        this.newFollowerLogin = newFollowerLogin;
        return this;
    }

    public void setNewFollowerLogin(String newFollowerLogin) {
        this.newFollowerLogin = newFollowerLogin;
    }

    public String getNewFollowerName() {
        return newFollowerName;
    }

    public CustomerCousultantAssignHistory newFollowerName(String newFollowerName) {
        this.newFollowerName = newFollowerName;
        return this;
    }

    public void setNewFollowerName(String newFollowerName) {
        this.newFollowerName = newFollowerName;
    }

    public String getComments() {
        return comments;
    }

    public CustomerCousultantAssignHistory comments(String comments) {
        this.comments = comments;
        return this;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Customer getCustomer() {
        return customer;
    }

    public CustomerCousultantAssignHistory customer(Customer customer) {
        this.customer = customer;
        return this;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
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
        CustomerCousultantAssignHistory customerCousultantAssignHistory = (CustomerCousultantAssignHistory) o;
        if (customerCousultantAssignHistory.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), customerCousultantAssignHistory.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CustomerCousultantAssignHistory{" +
            "id=" + getId() +
            ", olderFollowerLogin='" + getOlderFollowerLogin() + "'" +
            ", olderFollowerName='" + getOlderFollowerName() + "'" +
            ", newFollowerLogin='" + getNewFollowerLogin() + "'" +
            ", newFollowerName='" + getNewFollowerName() + "'" +
            ", comments='" + getComments() + "'" +
            "}";
    }
}
