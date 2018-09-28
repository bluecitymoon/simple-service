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
 * A TimetableItem.
 */
@Entity
@Table(name = "timetable_item")
public class TimetableItem extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "weekday_name")
    private String weekdayName;

    @Column(name = "classroom")
    private String classroom;

    @Column(name = "course_name")
    private String courseName;

    @Column(name = "teacher_name")
    private String teacherName;

    @ManyToOne
    private Product clazz;

    @ManyToOne
    private TimeSegment timeSegment;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWeekdayName() {
        return weekdayName;
    }

    public TimetableItem weekdayName(String weekdayName) {
        this.weekdayName = weekdayName;
        return this;
    }

    public void setWeekdayName(String weekdayName) {
        this.weekdayName = weekdayName;
    }

    public String getClassroom() {
        return classroom;
    }

    public TimetableItem classroom(String classroom) {
        this.classroom = classroom;
        return this;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }

    public String getCourseName() {
        return courseName;
    }

    public TimetableItem courseName(String courseName) {
        this.courseName = courseName;
        return this;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public TimetableItem teacherName(String teacherName) {
        this.teacherName = teacherName;
        return this;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public Product getClazz() {
        return clazz;
    }

    public TimetableItem clazz(Product product) {
        this.clazz = product;
        return this;
    }

    public void setClazz(Product product) {
        this.clazz = product;
    }

    public TimeSegment getTimeSegment() {
        return timeSegment;
    }

    public TimetableItem timeSegment(TimeSegment timeSegment) {
        this.timeSegment = timeSegment;
        return this;
    }

    public void setTimeSegment(TimeSegment timeSegment) {
        this.timeSegment = timeSegment;
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
        TimetableItem timetableItem = (TimetableItem) o;
        if (timetableItem.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), timetableItem.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TimetableItem{" +
            "id=" + getId() +
            ", weekdayName='" + getWeekdayName() + "'" +
            ", classroom='" + getClassroom() + "'" +
            ", courseName='" + getCourseName() + "'" +
            ", teacherName='" + getTeacherName() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}
