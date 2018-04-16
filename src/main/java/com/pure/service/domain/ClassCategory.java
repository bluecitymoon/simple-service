package com.pure.service.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A ClassCategory.
 */
@Entity
@Table(name = "class_category")
public class ClassCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private String code;

    @Column(name = "description")
    private String description;

    @ManyToMany(mappedBy = "classCategories")
    @JsonIgnore
    private Set<FreeClassRecord> freeClassRecords = new HashSet<>();

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

    public ClassCategory name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public ClassCategory code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public ClassCategory description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<FreeClassRecord> getFreeClassRecords() {
        return freeClassRecords;
    }

    public ClassCategory freeClassRecords(Set<FreeClassRecord> freeClassRecords) {
        this.freeClassRecords = freeClassRecords;
        return this;
    }

    public ClassCategory addFreeClassRecord(FreeClassRecord freeClassRecord) {
        this.freeClassRecords.add(freeClassRecord);
        freeClassRecord.getClassCategories().add(this);
        return this;
    }

    public ClassCategory removeFreeClassRecord(FreeClassRecord freeClassRecord) {
        this.freeClassRecords.remove(freeClassRecord);
        freeClassRecord.getClassCategories().remove(this);
        return this;
    }

    public void setFreeClassRecords(Set<FreeClassRecord> freeClassRecords) {
        this.freeClassRecords = freeClassRecords;
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
        ClassCategory classCategory = (ClassCategory) o;
        if (classCategory.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), classCategory.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ClassCategory{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", code='" + getCode() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
