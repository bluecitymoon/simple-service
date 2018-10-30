package com.pure.service.service.dto;

import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.InstantFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

import java.io.Serializable;




/**
 * Criteria class for the AuthorityUserGuideDocument entity. This class is used in AuthorityUserGuideDocumentResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /authority-user-guide-documents?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AuthorityUserGuideDocumentCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private StringFilter createdBy;

    private InstantFilter createdDate;

    private StringFilter lastModifiedBy;

    private InstantFilter lastModifiedDate;

    private StringFilter comments;

    private LongFilter authorityId;

    private LongFilter userGuideDocumentId;

    public AuthorityUserGuideDocumentCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(StringFilter createdBy) {
        this.createdBy = createdBy;
    }

    public InstantFilter getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(InstantFilter createdDate) {
        this.createdDate = createdDate;
    }

    public StringFilter getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(StringFilter lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public InstantFilter getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(InstantFilter lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public StringFilter getComments() {
        return comments;
    }

    public void setComments(StringFilter comments) {
        this.comments = comments;
    }

    public LongFilter getAuthorityId() {
        return authorityId;
    }

    public void setAuthorityId(LongFilter authorityId) {
        this.authorityId = authorityId;
    }

    public LongFilter getUserGuideDocumentId() {
        return userGuideDocumentId;
    }

    public void setUserGuideDocumentId(LongFilter userGuideDocumentId) {
        this.userGuideDocumentId = userGuideDocumentId;
    }

    @Override
    public String toString() {
        return "AuthorityUserGuideDocumentCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
                (createdDate != null ? "createdDate=" + createdDate + ", " : "") +
                (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
                (lastModifiedDate != null ? "lastModifiedDate=" + lastModifiedDate + ", " : "") +
                (comments != null ? "comments=" + comments + ", " : "") +
                (authorityId != null ? "authorityId=" + authorityId + ", " : "") +
                (userGuideDocumentId != null ? "userGuideDocumentId=" + userGuideDocumentId + ", " : "") +
            "}";
    }

}
