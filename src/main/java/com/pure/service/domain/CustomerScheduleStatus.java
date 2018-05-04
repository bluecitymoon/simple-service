package com.pure.service.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A CustomerScheduleStatus.
 */
@Entity
@Table(name = "customer_schedule_status")
public class CustomerScheduleStatus implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private String code;

    @Column(name = "label_style")
    private String labelStyle;

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

    public CustomerScheduleStatus name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public CustomerScheduleStatus code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLabelStyle() {
        return labelStyle;
    }

    public CustomerScheduleStatus labelStyle(String labelStyle) {
        this.labelStyle = labelStyle;
        return this;
    }

    public void setLabelStyle(String labelStyle) {
        this.labelStyle = labelStyle;
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
        CustomerScheduleStatus customerScheduleStatus = (CustomerScheduleStatus) o;
        if (customerScheduleStatus.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), customerScheduleStatus.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CustomerScheduleStatus{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", code='" + getCode() + "'" +
            ", labelStyle='" + getLabelStyle() + "'" +
            "}";
    }
}
