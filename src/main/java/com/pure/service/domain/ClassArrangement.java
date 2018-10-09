package com.pure.service.domain;


import com.pure.service.service.dto.dto.ClassSchedule;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;
import javax.persistence.Table;
import java.time.Instant;
import java.util.Objects;

/**
 * A ClassArrangement.
 */
@NamedNativeQueries({
    @NamedNativeQuery(name = "ClassArrangement.getAllSchedules",
        query = "select  ca.id as arrangementId, p.id as classId, concat(DATE_FORMAT(start_date,'%H:%i'), '-', DATE_FORMAT(end_date,'%H:%i'),  ' ', p.name, ' ', t.name) as title, p.name as className, p.students_reach_max_numbers as fullTag, cs.name as statusTag, t.name as teacherName, ca.start_date as start, ca.end_date as end, cr.name as classroomName, cr.id as classroomId, c.name as courseName\n" +
            " from class_arrangement ca\n" +
            " cross join product p on ca.clazz_id = p.id\n" +
            " cross join teacher t on ca.planed_teacher_id = t.id\n" +
            " cross join class_room cr on p.class_room_id = cr.id\n" +
            " cross join course c on p.course_id = c.id\n" +
            " left join class_status cs on cs.id = p.status_id \n" +
            " order by ca.start_date asc",
        resultSetMapping = "scheduleMapping"),
    @NamedNativeQuery(name = "ClassArrangement.getAllSchedulesByRange",
        query = "select  ca.id as arrangementId, p.id as classId, concat(DATE_FORMAT(start_date,'%H:%i'), '-', DATE_FORMAT(end_date,'%H:%i'),  ' ', p.name, ' ', t.name) as title, p.name as className, p.students_reach_max_numbers as fullTag, cs.name as statusTag, t.name as teacherName, ca.start_date as start, ca.end_date as end, cr.name as classroomName, cr.id as classroomId, c.name as courseName\n" +
            " from class_arrangement ca\n" +
            " cross join product p on ca.clazz_id = p.id\n" +
            " cross join teacher t on ca.planed_teacher_id = t.id\n" +
            " cross join class_room cr on p.class_room_id = cr.id\n" +
            " cross join course c on p.course_id = c.id\n" +
            " left join class_status cs on cs.id = p.status_id \n" +
            " and ca.start_date > :1\n" +
            " and ca.end_date < :2\n" +
            " order by ca.start_date asc",
        resultSetMapping = "scheduleMapping")})

@SqlResultSetMappings(
    {@SqlResultSetMapping(name = "scheduleMapping",
        classes = @ConstructorResult(targetClass = ClassSchedule.class,
            columns = {
                @ColumnResult(name = "arrangementId", type = Long.class),
                @ColumnResult(name = "classId", type = Long.class),
                @ColumnResult(name = "title", type = String.class),
                @ColumnResult(name = "className", type = String.class),
                @ColumnResult(name = "fullTag", type = String.class),
                @ColumnResult(name = "statusTag", type = String.class),
                @ColumnResult(name = "teacherName", type = String.class),
                @ColumnResult(name = "start", type = Instant.class),
                @ColumnResult(name = "end", type = Instant.class),
                @ColumnResult(name = "classroomName", type = String.class),
                @ColumnResult(name = "classroomId", type = Long.class),
                @ColumnResult(name = "courseName", type = String.class)
            }))
})

@Entity
@Table(name = "class_arrangement")
public class ClassArrangement extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start_date")
    private Instant startDate;

    @Column(name = "end_date")
    private Instant endDate;

    @Column(name = "consume_class_count")
    private Integer consumeClassCount;

    @ManyToOne
    private Teacher planedTeacher;

    @ManyToOne
    private Teacher actualTeacher;

    @ManyToOne
    private ClassArrangementStatus status;

    @ManyToOne
    private Product clazz;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getConsumeClassCount() {
        return consumeClassCount;
    }

    public void setConsumeClassCount(Integer consumeClassCount) {
        this.consumeClassCount = consumeClassCount;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public ClassArrangement startDate(Instant startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public ClassArrangement endDate(Instant endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public Teacher getPlanedTeacher() {
        return planedTeacher;
    }

    public ClassArrangement planedTeacher(Teacher teacher) {
        this.planedTeacher = teacher;
        return this;
    }

    public void setPlanedTeacher(Teacher teacher) {
        this.planedTeacher = teacher;
    }

    public Teacher getActualTeacher() {
        return actualTeacher;
    }

    public ClassArrangement actualTeacher(Teacher teacher) {
        this.actualTeacher = teacher;
        return this;
    }

    public void setActualTeacher(Teacher teacher) {
        this.actualTeacher = teacher;
    }

    public ClassArrangementStatus getStatus() {
        return status;
    }

    public ClassArrangement status(ClassArrangementStatus classArrangementStatus) {
        this.status = classArrangementStatus;
        return this;
    }

    public void setStatus(ClassArrangementStatus classArrangementStatus) {
        this.status = classArrangementStatus;
    }

    public Product getClazz() {
        return clazz;
    }

    public ClassArrangement clazz(Product product) {
        this.clazz = product;
        return this;
    }

    public void setClazz(Product product) {
        this.clazz = product;
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
        ClassArrangement classArrangement = (ClassArrangement) o;
        if (classArrangement.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), classArrangement.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ClassArrangement{" +
            "id=" + getId() +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}
