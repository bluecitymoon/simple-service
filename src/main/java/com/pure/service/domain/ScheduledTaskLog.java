package com.pure.service.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import java.util.Objects;

/**
 * A ScheduledTaskLog.
 */
@Entity
@Table(name = "scheduled_task_log")
public class ScheduledTaskLog extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "class_name")
    private String className;

    @Column(name = "name")
    private String name;

    @Lob
    @Column(name = "detail_info")
    private String detailInfo;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public ScheduledTaskLog className(String className) {
        this.className = className;
        return this;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getName() {
        return name;
    }

    public ScheduledTaskLog name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetailInfo() {
        return detailInfo;
    }

    public ScheduledTaskLog detailInfo(String detailInfo) {
        this.detailInfo = detailInfo;
        return this;
    }

    public void setDetailInfo(String detailInfo) {
        this.detailInfo = detailInfo;
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
        ScheduledTaskLog scheduledTaskLog = (ScheduledTaskLog) o;
        if (scheduledTaskLog.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), scheduledTaskLog.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ScheduledTaskLog{" +
            "id=" + getId() +
            ", className='" + getClassName() + "'" +
            ", name='" + getName() + "'" +
            ", detailInfo='" + getDetailInfo() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}
