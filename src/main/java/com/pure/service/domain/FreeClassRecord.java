package com.pure.service.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A FreeClassRecord.
 */
@Entity
@Table(name = "free_class_record")
public class FreeClassRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "person_name")
    private String personName;

    @Column(name = "contact_phone_number")
    private String contactPhoneNumber;

    @ManyToOne
    private MarketChannelCategory marketChannelCategory;

    @ManyToMany
    @JoinTable(name = "free_class_record_class_category",
               joinColumns = @JoinColumn(name="free_class_records_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="class_categories_id", referencedColumnName="id"))
    private Set<ClassCategory> classCategories = new HashSet<>();

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
            "}";
    }
}
