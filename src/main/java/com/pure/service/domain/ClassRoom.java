package com.pure.service.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ClassRoom.
 */
@Entity
@Table(name = "class_room")
public class ClassRoom implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private String code;

    @Column(name = "room_number")
    private String roomNumber;

    @Column(name = "acreage")
    private Float acreage;

    @Column(name = "max_student_capacity")
    private Integer maxStudentCapacity;

    @Column(name = "comments")
    private String comments;

    @Column(name = "avaliable")
    private Boolean avaliable;

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

    public ClassRoom name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public ClassRoom code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public ClassRoom roomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
        return this;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public Float getAcreage() {
        return acreage;
    }

    public ClassRoom acreage(Float acreage) {
        this.acreage = acreage;
        return this;
    }

    public void setAcreage(Float acreage) {
        this.acreage = acreage;
    }

    public Integer getMaxStudentCapacity() {
        return maxStudentCapacity;
    }

    public ClassRoom maxStudentCapacity(Integer maxStudentCapacity) {
        this.maxStudentCapacity = maxStudentCapacity;
        return this;
    }

    public void setMaxStudentCapacity(Integer maxStudentCapacity) {
        this.maxStudentCapacity = maxStudentCapacity;
    }

    public String getComments() {
        return comments;
    }

    public ClassRoom comments(String comments) {
        this.comments = comments;
        return this;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Boolean isAvaliable() {
        return avaliable;
    }

    public ClassRoom avaliable(Boolean avaliable) {
        this.avaliable = avaliable;
        return this;
    }

    public void setAvaliable(Boolean avaliable) {
        this.avaliable = avaliable;
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
        ClassRoom classRoom = (ClassRoom) o;
        if (classRoom.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), classRoom.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ClassRoom{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", code='" + getCode() + "'" +
            ", roomNumber='" + getRoomNumber() + "'" +
            ", acreage='" + getAcreage() + "'" +
            ", maxStudentCapacity='" + getMaxStudentCapacity() + "'" +
            ", comments='" + getComments() + "'" +
            ", avaliable='" + isAvaliable() + "'" +
            "}";
    }
}
