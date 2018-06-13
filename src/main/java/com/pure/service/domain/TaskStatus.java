package com.pure.service.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A TaskStatus.
 */
@Entity
@Table(name = "task_status")
public class TaskStatus implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private String code;

    @Column(name = "label_css")
    private String labelCss;

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

    public TaskStatus name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public TaskStatus code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLabelCss() {
        return labelCss;
    }

    public TaskStatus labelCss(String labelCss) {
        this.labelCss = labelCss;
        return this;
    }

    public void setLabelCss(String labelCss) {
        this.labelCss = labelCss;
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
        TaskStatus taskStatus = (TaskStatus) o;
        if (taskStatus.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), taskStatus.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TaskStatus{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", code='" + getCode() + "'" +
            ", labelCss='" + getLabelCss() + "'" +
            "}";
    }
}
