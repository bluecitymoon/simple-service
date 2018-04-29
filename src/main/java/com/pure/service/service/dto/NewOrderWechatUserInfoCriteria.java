package com.pure.service.service.dto;

import java.io.Serializable;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

import io.github.jhipster.service.filter.InstantFilter;




/**
 * Criteria class for the NewOrderWechatUserInfo entity. This class is used in NewOrderWechatUserInfoResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /new-order-wechat-user-infos?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class NewOrderWechatUserInfoCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private StringFilter code;

    private StringFilter encryptedData;

    private StringFilter iv;

    private StringFilter nickName;

    private IntegerFilter gender;

    private StringFilter language;

    private StringFilter city;

    private StringFilter province;

    private StringFilter country;

    private StringFilter avatarUrl;

    private StringFilter createdBy;

    private InstantFilter createdDate;

    private StringFilter lastModifiedBy;

    private InstantFilter lastModifiedDate;

    private StringFilter openId;

    private StringFilter comments;

    public NewOrderWechatUserInfoCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getCode() {
        return code;
    }

    public void setCode(StringFilter code) {
        this.code = code;
    }

    public StringFilter getEncryptedData() {
        return encryptedData;
    }

    public void setEncryptedData(StringFilter encryptedData) {
        this.encryptedData = encryptedData;
    }

    public StringFilter getIv() {
        return iv;
    }

    public void setIv(StringFilter iv) {
        this.iv = iv;
    }

    public StringFilter getNickName() {
        return nickName;
    }

    public void setNickName(StringFilter nickName) {
        this.nickName = nickName;
    }

    public IntegerFilter getGender() {
        return gender;
    }

    public void setGender(IntegerFilter gender) {
        this.gender = gender;
    }

    public StringFilter getLanguage() {
        return language;
    }

    public void setLanguage(StringFilter language) {
        this.language = language;
    }

    public StringFilter getCity() {
        return city;
    }

    public void setCity(StringFilter city) {
        this.city = city;
    }

    public StringFilter getProvince() {
        return province;
    }

    public void setProvince(StringFilter province) {
        this.province = province;
    }

    public StringFilter getCountry() {
        return country;
    }

    public void setCountry(StringFilter country) {
        this.country = country;
    }

    public StringFilter getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(StringFilter avatarUrl) {
        this.avatarUrl = avatarUrl;
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

    public StringFilter getOpenId() {
        return openId;
    }

    public void setOpenId(StringFilter openId) {
        this.openId = openId;
    }

    public StringFilter getComments() {
        return comments;
    }

    public void setComments(StringFilter comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "NewOrderWechatUserInfoCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (code != null ? "code=" + code + ", " : "") +
                (encryptedData != null ? "encryptedData=" + encryptedData + ", " : "") +
                (iv != null ? "iv=" + iv + ", " : "") +
                (nickName != null ? "nickName=" + nickName + ", " : "") +
                (gender != null ? "gender=" + gender + ", " : "") +
                (language != null ? "language=" + language + ", " : "") +
                (city != null ? "city=" + city + ", " : "") +
                (province != null ? "province=" + province + ", " : "") +
                (country != null ? "country=" + country + ", " : "") +
                (avatarUrl != null ? "avatarUrl=" + avatarUrl + ", " : "") +
                (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
                (createdDate != null ? "createdDate=" + createdDate + ", " : "") +
                (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
                (lastModifiedDate != null ? "lastModifiedDate=" + lastModifiedDate + ", " : "") +
                (openId != null ? "openId=" + openId + ", " : "") +
                (comments != null ? "comments=" + comments + ", " : "") +
            "}";
    }

}
