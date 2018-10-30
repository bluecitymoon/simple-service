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
 * A AuthorityUserGuideDocument.
 */
@Entity
@Table(name = "authority_user_guide_document")
public class AuthorityUserGuideDocument extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "comments")
    private String comments;

    @ManyToOne
    private Authority authority;

    @ManyToOne
    private UserGuideDocument userGuideDocument;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComments() {
        return comments;
    }

    public AuthorityUserGuideDocument comments(String comments) {
        this.comments = comments;
        return this;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Authority getAuthority() {
        return authority;
    }

    public AuthorityUserGuideDocument authority(Authority authority) {
        this.authority = authority;
        return this;
    }

    public void setAuthority(Authority authority) {
        this.authority = authority;
    }

    public UserGuideDocument getUserGuideDocument() {
        return userGuideDocument;
    }

    public AuthorityUserGuideDocument userGuideDocument(UserGuideDocument userGuideDocument) {
        this.userGuideDocument = userGuideDocument;
        return this;
    }

    public void setUserGuideDocument(UserGuideDocument userGuideDocument) {
        this.userGuideDocument = userGuideDocument;
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
        AuthorityUserGuideDocument authorityUserGuideDocument = (AuthorityUserGuideDocument) o;
        if (authorityUserGuideDocument.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), authorityUserGuideDocument.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AuthorityUserGuideDocument{" +
            "id=" + getId() +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            ", comments='" + getComments() + "'" +
            "}";
    }
}
