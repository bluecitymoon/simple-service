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
 * A NewOrderAssignHistory.
 */
@Entity
@Table(name = "new_order_assign_history")
public class NewOrderAssignHistory extends AbstractAuditingRegionEntity {

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
//
//    @Column(name = "created_by")
//    private String createdBy;
//
//    @Column(name = "created_date")
//    private Instant createdDate;
//
//    @Column(name = "last_modified_by")
//    private String lastModifiedBy;
//
//    @Column(name = "last_modified_date")
//    private Instant lastModifiedDate;
    @ManyToOne
    private FreeClassRecord newOrder;

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

    public NewOrderAssignHistory olderFollowerLogin(String olderFollowerLogin) {
        this.olderFollowerLogin = olderFollowerLogin;
        return this;
    }

    public void setOlderFollowerLogin(String olderFollowerLogin) {
        this.olderFollowerLogin = olderFollowerLogin;
    }

    public String getOlderFollowerName() {
        return olderFollowerName;
    }

    public NewOrderAssignHistory olderFollowerName(String olderFollowerName) {
        this.olderFollowerName = olderFollowerName;
        return this;
    }

    public void setOlderFollowerName(String olderFollowerName) {
        this.olderFollowerName = olderFollowerName;
    }

    public String getNewFollowerLogin() {
        return newFollowerLogin;
    }

    public NewOrderAssignHistory newFollowerLogin(String newFollowerLogin) {
        this.newFollowerLogin = newFollowerLogin;
        return this;
    }

    public void setNewFollowerLogin(String newFollowerLogin) {
        this.newFollowerLogin = newFollowerLogin;
    }

    public String getNewFollowerName() {
        return newFollowerName;
    }

    public NewOrderAssignHistory newFollowerName(String newFollowerName) {
        this.newFollowerName = newFollowerName;
        return this;
    }

    public void setNewFollowerName(String newFollowerName) {
        this.newFollowerName = newFollowerName;
    }

//    public String getCreatedBy() {
//        return createdBy;
//    }
//
//    public NewOrderAssignHistory createdBy(String createdBy) {
//        this.createdBy = createdBy;
//        return this;
//    }
//
//    public void setCreatedBy(String createdBy) {
//        this.createdBy = createdBy;
//    }
//
//    public Instant getCreatedDate() {
//        return createdDate;
//    }
//
//    public NewOrderAssignHistory createdDate(Instant createdDate) {
//        this.createdDate = createdDate;
//        return this;
//    }
//
//    public void setCreatedDate(Instant createdDate) {
//        this.createdDate = createdDate;
//    }
//
//    public String getLastModifiedBy() {
//        return lastModifiedBy;
//    }
//
//    public NewOrderAssignHistory lastModifiedBy(String lastModifiedBy) {
//        this.lastModifiedBy = lastModifiedBy;
//        return this;
//    }
//
//    public void setLastModifiedBy(String lastModifiedBy) {
//        this.lastModifiedBy = lastModifiedBy;
//    }
//
//    public Instant getLastModifiedDate() {
//        return lastModifiedDate;
//    }
//
//    public NewOrderAssignHistory lastModifiedDate(Instant lastModifiedDate) {
//        this.lastModifiedDate = lastModifiedDate;
//        return this;
//    }
//
//    public void setLastModifiedDate(Instant lastModifiedDate) {
//        this.lastModifiedDate = lastModifiedDate;
//    }

    public FreeClassRecord getNewOrder() {
        return newOrder;
    }

    public NewOrderAssignHistory newOrder(FreeClassRecord freeClassRecord) {
        this.newOrder = freeClassRecord;
        return this;
    }

    public void setNewOrder(FreeClassRecord freeClassRecord) {
        this.newOrder = freeClassRecord;
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
        NewOrderAssignHistory newOrderAssignHistory = (NewOrderAssignHistory) o;
        if (newOrderAssignHistory.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), newOrderAssignHistory.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "NewOrderAssignHistory{" +
            "id=" + getId() +
            ", olderFollowerLogin='" + getOlderFollowerLogin() + "'" +
            ", olderFollowerName='" + getOlderFollowerName() + "'" +
            ", newFollowerLogin='" + getNewFollowerLogin() + "'" +
            ", newFollowerName='" + getNewFollowerName() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}
