package com.pure.service.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A CountNumber.
 */
@Entity
@Table(name = "count_number")
public class CountNumber implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "jhi_value")
    private Integer value;

    @ManyToOne
    private ClassArrangementRuleLoopWay loopWay;

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

    public CountNumber name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public CountNumber value(Integer value) {
        this.value = value;
        return this;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public ClassArrangementRuleLoopWay getLoopWay() {
        return loopWay;
    }

    public CountNumber loopWay(ClassArrangementRuleLoopWay classArrangementRuleLoopWay) {
        this.loopWay = classArrangementRuleLoopWay;
        return this;
    }

    public void setLoopWay(ClassArrangementRuleLoopWay classArrangementRuleLoopWay) {
        this.loopWay = classArrangementRuleLoopWay;
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
        CountNumber countNumber = (CountNumber) o;
        if (countNumber.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), countNumber.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CountNumber{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", value='" + getValue() + "'" +
            "}";
    }
}
