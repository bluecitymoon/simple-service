package com.pure.service.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

/**
 * A UserGuideDocument.
 */
@Entity
@Table(name = "user_guide_document")
public class UserGuideDocument extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "full_url")
    private String fullUrl;

    @Column(name = "full_folder")
    private String fullFolder;

    @Column(name = "base_url")
    private String baseUrl;

    @Column(name = "comments")
    private String comments;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public UserGuideDocument title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFileName() {
        return fileName;
    }

    public UserGuideDocument fileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFullUrl() {
        return fullUrl;
    }

    public UserGuideDocument fullUrl(String fullUrl) {
        this.fullUrl = fullUrl;
        return this;
    }

    public void setFullUrl(String fullUrl) {
        this.fullUrl = fullUrl;
    }

    public String getFullFolder() {
        return fullFolder;
    }

    public UserGuideDocument fullFolder(String fullFolder) {
        this.fullFolder = fullFolder;
        return this;
    }

    public void setFullFolder(String fullFolder) {
        this.fullFolder = fullFolder;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public UserGuideDocument baseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        return this;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getComments() {
        return comments;
    }

    public UserGuideDocument comments(String comments) {
        this.comments = comments;
        return this;
    }

    public void setComments(String comments) {
        this.comments = comments;
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
        UserGuideDocument userGuideDocument = (UserGuideDocument) o;
        if (userGuideDocument.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userGuideDocument.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserGuideDocument{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", fileName='" + getFileName() + "'" +
            ", fullUrl='" + getFullUrl() + "'" +
            ", fullFolder='" + getFullFolder() + "'" +
            ", baseUrl='" + getBaseUrl() + "'" +
            ", comments='" + getComments() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}
