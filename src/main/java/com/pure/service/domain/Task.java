package com.pure.service.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.Instant;
import java.util.Objects;

/**
 * A Task.
 */
@Entity
@Table(name = "task")
public class Task extends AbstractAuditingRegionEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "estimate_execute_date")
    private Instant estimateExecuteDate;

    @ManyToOne
    private User assignee;

    @ManyToOne
    private TaskStatus taskStatus;

    @ManyToOne
    private User reporter;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public Task description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getEstimateExecuteDate() {
        return estimateExecuteDate;
    }

    public Task estimateExecuteDate(Instant estimateExecuteDate) {
        this.estimateExecuteDate = estimateExecuteDate;
        return this;
    }

    public void setEstimateExecuteDate(Instant estimateExecuteDate) {
        this.estimateExecuteDate = estimateExecuteDate;
    }

    public User getAssignee() {
        return assignee;
    }

    public Task assignee(User user) {
        this.assignee = user;
        return this;
    }

    public void setAssignee(User user) {
        this.assignee = user;
    }

    public TaskStatus getTaskStatus() {
        return taskStatus;
    }

    public Task taskStatus(TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
        return this;
    }

    public void setTaskStatus(TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
    }

    public User getReporter() {
        return reporter;
    }

    public Task reporter(User user) {
        this.reporter = user;
        return this;
    }

    public void setReporter(User user) {
        this.reporter = user;
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
        Task task = (Task) o;
        if (task.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), task.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Task{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", estimateExecuteDate='" + getEstimateExecuteDate() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}
