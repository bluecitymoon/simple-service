package com.pure.service.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

/**
 * A SystemVariable.
 */
@Entity
@Table(name = "system_variable")
public class SystemVariable extends AbstractRegionEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "attr_value")
    private String attrValue;

    @Column(name = "comments")
    private String comments;

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

    public SystemVariable name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAttrValue() {
        return attrValue;
    }

    public SystemVariable attrValue(String attrValue) {
        this.attrValue = attrValue;
        return this;
    }

    public void setAttrValue(String attrValue) {
        this.attrValue = attrValue;
    }

    public String getComments() {
        return comments;
    }

    public SystemVariable comments(String comments) {
        this.comments = comments;
        return this;
    }

    public void setComments(String comments) {
        this.comments = comments;
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
        SystemVariable systemVariable = (SystemVariable) o;
        if (systemVariable.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), systemVariable.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SystemVariable{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", attrValue='" + getAttrValue() + "'" +
            ", comments='" + getComments() + "'" +
            "}";
    }
}
