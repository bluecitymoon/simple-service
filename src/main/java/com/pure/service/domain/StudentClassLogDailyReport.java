package com.pure.service.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;
import java.util.Objects;

/**
 * A StudentClassLogDailyReport.
 */
@Entity
@Table(name = "student_class_log_daily_report")
public class StudentClassLogDailyReport extends AbstractAuditingRegionEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "should_taken")
    private Integer shouldTaken;

    @Column(name = "jhi_leave")
    private Integer leave;

    @Column(name = "absence")
    private Integer absence;

    @Column(name = "added")
    private Integer added;

    @Column(name = "actual_taken")
    private Integer actualTaken;

    @Column(name = "log_date")
    private Instant logDate;

    @Column(name = "frozen")
    private Integer frozen;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getFrozen() {
        return frozen;
    }

    public void setFrozen(Integer frozen) {
        this.frozen = frozen;
    }

    public Integer getShouldTaken() {
        return shouldTaken;
    }

    public StudentClassLogDailyReport shouldTaken(Integer shouldTaken) {
        this.shouldTaken = shouldTaken;
        return this;
    }

    public void setShouldTaken(Integer shouldTaken) {
        this.shouldTaken = shouldTaken;
    }

    public Integer getLeave() {
        return leave;
    }

    public StudentClassLogDailyReport leave(Integer leave) {
        this.leave = leave;
        return this;
    }

    public void setLeave(Integer leave) {
        this.leave = leave;
    }

    public Integer getAbsence() {
        return absence;
    }

    public StudentClassLogDailyReport absence(Integer absence) {
        this.absence = absence;
        return this;
    }

    public void setAbsence(Integer absence) {
        this.absence = absence;
    }

    public Integer getAdded() {
        return added;
    }

    public StudentClassLogDailyReport added(Integer added) {
        this.added = added;
        return this;
    }

    public void setAdded(Integer added) {
        this.added = added;
    }

    public Integer getActualTaken() {
        return actualTaken;
    }

    public StudentClassLogDailyReport actualTaken(Integer actualTaken) {
        this.actualTaken = actualTaken;
        return this;
    }

    public void setActualTaken(Integer actualTaken) {
        this.actualTaken = actualTaken;
    }

    public Instant getLogDate() {
        return logDate;
    }

    public StudentClassLogDailyReport logDate(Instant logDate) {
        this.logDate = logDate;
        return this;
    }

    public void setLogDate(Instant logDate) {
        this.logDate = logDate;
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
        StudentClassLogDailyReport studentClassLogDailyReport = (StudentClassLogDailyReport) o;
        if (studentClassLogDailyReport.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), studentClassLogDailyReport.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "StudentClassLogDailyReport{" +
            "id=" + getId() +
            ", shouldTaken='" + getShouldTaken() + "'" +
            ", leave='" + getLeave() + "'" +
            ", absence='" + getAbsence() + "'" +
            ", added='" + getAdded() + "'" +
            ", actualTaken='" + getActualTaken() + "'" +
            ", logDate='" + getLogDate() + "'" +
            "}";
    }
}
