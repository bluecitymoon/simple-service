package com.pure.service.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A FreeClassRecord.
 */
@Entity
@Table(name = "free_class_record")
public class FreeClassRecord extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "person_name")
    private String personName;

    @Column(name = "contact_phone_number")
    private String contactPhoneNumber;

//    @Column(name = "created_by")
//    private String createdBy;
//
//    @Column(name = "created_date")
//    private ZonedDateTime createdDate;
//
//    @Column(name = "last_modified_by")
//    private String lastModifiedBy;
//
//    @Column(name = "last_modified_date")
//    private ZonedDateTime lastModifiedDate;
    @Column(name = "status")
    private String status;

    @ManyToOne
    private MarketChannelCategory marketChannelCategory;

    @ManyToMany
    @JoinTable(name = "free_class_record_class_category",
               joinColumns = @JoinColumn(name="free_class_records_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="class_categories_id", referencedColumnName="id"))
    private Set<ClassCategory> classCategories = new HashSet<>();

    @ManyToOne
    private User salesFollower;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPersonName() {
        return personName;
    }

    public FreeClassRecord personName(String personName) {
        this.personName = personName;
        return this;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getContactPhoneNumber() {
        return contactPhoneNumber;
    }

    public FreeClassRecord contactPhoneNumber(String contactPhoneNumber) {
        this.contactPhoneNumber = contactPhoneNumber;
        return this;
    }

    public void setContactPhoneNumber(String contactPhoneNumber) {
        this.contactPhoneNumber = contactPhoneNumber;
    }

//    public String getCreatedBy() {
//        return createdBy;
//    }
//
//    public FreeClassRecord createdBy(String createdBy) {
//        this.createdBy = createdBy;
//        return this;
//    }
//
//    public void setCreatedBy(String createdBy) {
//        this.createdBy = createdBy;
//    }
//
//    public ZonedDateTime getCreatedDate() {
//        return createdDate;
//    }
//
//    public FreeClassRecord createdDate(ZonedDateTime createdDate) {
//        this.createdDate = createdDate;
//        return this;
//    }
//
//    public void setCreatedDate(ZonedDateTime createdDate) {
//        this.createdDate = createdDate;
//    }
//
//    public String getLastModifiedBy() {
//        return lastModifiedBy;
//    }
//
//    public FreeClassRecord lastModifiedBy(String lastModifiedBy) {
//        this.lastModifiedBy = lastModifiedBy;
//        return this;
//    }
//
//    public void setLastModifiedBy(String lastModifiedBy) {
//        this.lastModifiedBy = lastModifiedBy;
//    }
//
//    public ZonedDateTime getLastModifiedDate() {
//        return lastModifiedDate;
//    }
//
//    public FreeClassRecord lastModifiedDate(ZonedDateTime lastModifiedDate) {
//        this.lastModifiedDate = lastModifiedDate;
//        return this;
//    }
//
//    public void setLastModifiedDate(ZonedDateTime lastModifiedDate) {
//        this.lastModifiedDate = lastModifiedDate;
//    }

    public String getStatus() {
        return status;
    }

    public FreeClassRecord status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public MarketChannelCategory getMarketChannelCategory() {
        return marketChannelCategory;
    }

    public FreeClassRecord marketChannelCategory(MarketChannelCategory marketChannelCategory) {
        this.marketChannelCategory = marketChannelCategory;
        return this;
    }

    public void setMarketChannelCategory(MarketChannelCategory marketChannelCategory) {
        this.marketChannelCategory = marketChannelCategory;
    }

    public Set<ClassCategory> getClassCategories() {
        return classCategories;
    }

    public FreeClassRecord classCategories(Set<ClassCategory> classCategories) {
        this.classCategories = classCategories;
        return this;
    }

    public FreeClassRecord addClassCategory(ClassCategory classCategory) {
        this.classCategories.add(classCategory);
        classCategory.getFreeClassRecords().add(this);
        return this;
    }

    public FreeClassRecord removeClassCategory(ClassCategory classCategory) {
        this.classCategories.remove(classCategory);
        classCategory.getFreeClassRecords().remove(this);
        return this;
    }

    public void setClassCategories(Set<ClassCategory> classCategories) {
        this.classCategories = classCategories;
    }

    public User getSalesFollower() {
        return salesFollower;
    }

    public FreeClassRecord salesFollower(User user) {
        this.salesFollower = user;
        return this;
    }

    public void setSalesFollower(User user) {
        this.salesFollower = user;
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
        FreeClassRecord freeClassRecord = (FreeClassRecord) o;
        if (freeClassRecord.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), freeClassRecord.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FreeClassRecord{" +
            "id=" + getId() +
            ", personName='" + getPersonName() + "'" +
            ", contactPhoneNumber='" + getContactPhoneNumber() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
