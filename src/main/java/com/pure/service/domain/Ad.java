package com.pure.service.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Ad.
 */
@Entity
@Table(name = "ad")
public class Ad implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "jhi_type")
    private String type;

    @Column(name = "content")
    private String content;

    @Column(name = "rpx_width")
    private Integer rpxWidth;

    @Column(name = "rpx_height")
    private Integer rpxHeight;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "jhi_sequence")
    private Integer sequence;

    @Column(name = "comments")
    private String comments;

    @Column(name = "created_by")
    private String createdBy;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public Ad type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public Ad content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getRpxWidth() {
        return rpxWidth;
    }

    public Ad rpxWidth(Integer rpxWidth) {
        this.rpxWidth = rpxWidth;
        return this;
    }

    public void setRpxWidth(Integer rpxWidth) {
        this.rpxWidth = rpxWidth;
    }

    public Integer getRpxHeight() {
        return rpxHeight;
    }

    public Ad rpxHeight(Integer rpxHeight) {
        this.rpxHeight = rpxHeight;
        return this;
    }

    public void setRpxHeight(Integer rpxHeight) {
        this.rpxHeight = rpxHeight;
    }

    public Boolean isStatus() {
        return status;
    }

    public Ad status(Boolean status) {
        this.status = status;
        return this;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Integer getSequence() {
        return sequence;
    }

    public Ad sequence(Integer sequence) {
        this.sequence = sequence;
        return this;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public String getComments() {
        return comments;
    }

    public Ad comments(String comments) {
        this.comments = comments;
        return this;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public Ad createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
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
        Ad ad = (Ad) o;
        if (ad.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ad.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Ad{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", content='" + getContent() + "'" +
            ", rpxWidth='" + getRpxWidth() + "'" +
            ", rpxHeight='" + getRpxHeight() + "'" +
            ", status='" + isStatus() + "'" +
            ", sequence='" + getSequence() + "'" +
            ", comments='" + getComments() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            "}";
    }
}
