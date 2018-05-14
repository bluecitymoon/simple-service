package com.pure.service.service.dto;

import java.io.Serializable;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;






/**
 * Criteria class for the Ad entity. This class is used in AdResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /ads?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AdCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private StringFilter type;

    private StringFilter content;

    private IntegerFilter rpxWidth;

    private IntegerFilter rpxHeight;

    private BooleanFilter status;

    private IntegerFilter sequence;

    private StringFilter comments;

    private StringFilter createdBy;

    public AdCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getType() {
        return type;
    }

    public void setType(StringFilter type) {
        this.type = type;
    }

    public StringFilter getContent() {
        return content;
    }

    public void setContent(StringFilter content) {
        this.content = content;
    }

    public IntegerFilter getRpxWidth() {
        return rpxWidth;
    }

    public void setRpxWidth(IntegerFilter rpxWidth) {
        this.rpxWidth = rpxWidth;
    }

    public IntegerFilter getRpxHeight() {
        return rpxHeight;
    }

    public void setRpxHeight(IntegerFilter rpxHeight) {
        this.rpxHeight = rpxHeight;
    }

    public BooleanFilter getStatus() {
        return status;
    }

    public void setStatus(BooleanFilter status) {
        this.status = status;
    }

    public IntegerFilter getSequence() {
        return sequence;
    }

    public void setSequence(IntegerFilter sequence) {
        this.sequence = sequence;
    }

    public StringFilter getComments() {
        return comments;
    }

    public void setComments(StringFilter comments) {
        this.comments = comments;
    }

    public StringFilter getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(StringFilter createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public String toString() {
        return "AdCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (type != null ? "type=" + type + ", " : "") +
                (content != null ? "content=" + content + ", " : "") +
                (rpxWidth != null ? "rpxWidth=" + rpxWidth + ", " : "") +
                (rpxHeight != null ? "rpxHeight=" + rpxHeight + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (sequence != null ? "sequence=" + sequence + ", " : "") +
                (comments != null ? "comments=" + comments + ", " : "") +
                (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
            "}";
    }

}
