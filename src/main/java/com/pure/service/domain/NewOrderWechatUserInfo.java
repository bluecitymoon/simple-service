package com.pure.service.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A NewOrderWechatUserInfo.
 */
@Entity
@Table(name = "new_order_wechat_user_info")
public class NewOrderWechatUserInfo extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "encrypted_data", length = 5000)
    private String encryptedData;

    @Column(name = "iv")
    private String iv;

    @Column(name = "nick_name")
    private String nickName;

    @Column(name = "gender")
    private Integer gender;

    @Column(name = "language")
    private String language;

    @Column(name = "city")
    private String city;

    @Column(name = "province")
    private String province;

    @Column(name = "country")
    private String country;

    @Column(name = "avatar_url")
    private String avatarUrl;
//
//    @Column(name = "created_by")
//    private String createdBy;
//
//    @Column(name = "created_date")
//    private Instant createdDate;
//
//    @Column(name = "last_modified_by")
//    private String lastModifiedBy;
//
//    @Column(name = "last_modified_date")
//    private Instant lastModifiedDate;

    @Column(name = "open_id")
    private String openId;

    @Column(name = "comments")
    private String comments;

    @OneToMany(mappedBy = "newOrderWechatUserInfo")
    @JsonIgnore
    private Set<FreeClassRecord> newOrders = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public NewOrderWechatUserInfo code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getEncryptedData() {
        return encryptedData;
    }

    public NewOrderWechatUserInfo encryptedData(String encryptedData) {
        this.encryptedData = encryptedData;
        return this;
    }

    public void setEncryptedData(String encryptedData) {
        this.encryptedData = encryptedData;
    }

    public String getIv() {
        return iv;
    }

    public NewOrderWechatUserInfo iv(String iv) {
        this.iv = iv;
        return this;
    }

    public void setIv(String iv) {
        this.iv = iv;
    }

    public String getNickName() {
        return nickName;
    }

    public NewOrderWechatUserInfo nickName(String nickName) {
        this.nickName = nickName;
        return this;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Integer getGender() {
        return gender;
    }

    public NewOrderWechatUserInfo gender(Integer gender) {
        this.gender = gender;
        return this;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getLanguage() {
        return language;
    }

    public NewOrderWechatUserInfo language(String language) {
        this.language = language;
        return this;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCity() {
        return city;
    }

    public NewOrderWechatUserInfo city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public NewOrderWechatUserInfo province(String province) {
        this.province = province;
        return this;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public NewOrderWechatUserInfo country(String country) {
        this.country = country;
        return this;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public NewOrderWechatUserInfo avatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
        return this;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

//    public String getCreatedBy() {
//        return createdBy;
//    }
//
//    public NewOrderWechatUserInfo createdBy(String createdBy) {
//        this.createdBy = createdBy;
//        return this;
//    }
//
//    public void setCreatedBy(String createdBy) {
//        this.createdBy = createdBy;
//    }
//
//    public Instant getCreatedDate() {
//        return createdDate;
//    }
//
//    public NewOrderWechatUserInfo createdDate(Instant createdDate) {
//        this.createdDate = createdDate;
//        return this;
//    }
//
//    public void setCreatedDate(Instant createdDate) {
//        this.createdDate = createdDate;
//    }
//
//    public String getLastModifiedBy() {
//        return lastModifiedBy;
//    }
//
//    public NewOrderWechatUserInfo lastModifiedBy(String lastModifiedBy) {
//        this.lastModifiedBy = lastModifiedBy;
//        return this;
//    }
//
//    public void setLastModifiedBy(String lastModifiedBy) {
//        this.lastModifiedBy = lastModifiedBy;
//    }
//
//    public Instant getLastModifiedDate() {
//        return lastModifiedDate;
//    }
//
//    public NewOrderWechatUserInfo lastModifiedDate(Instant lastModifiedDate) {
//        this.lastModifiedDate = lastModifiedDate;
//        return this;
//    }
//
//    public void setLastModifiedDate(Instant lastModifiedDate) {
//        this.lastModifiedDate = lastModifiedDate;
//    }

    public String getOpenId() {
        return openId;
    }

    public NewOrderWechatUserInfo openId(String openId) {
        this.openId = openId;
        return this;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getComments() {
        return comments;
    }

    public NewOrderWechatUserInfo comments(String comments) {
        this.comments = comments;
        return this;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Set<FreeClassRecord> getNewOrders() {
        return newOrders;
    }

    public NewOrderWechatUserInfo newOrders(Set<FreeClassRecord> freeClassRecords) {
        this.newOrders = freeClassRecords;
        return this;
    }

    public NewOrderWechatUserInfo addNewOrder(FreeClassRecord freeClassRecord) {
        this.newOrders.add(freeClassRecord);
        freeClassRecord.setNewOrderWechatUserInfo(this);
        return this;
    }

    public NewOrderWechatUserInfo removeNewOrder(FreeClassRecord freeClassRecord) {
        this.newOrders.remove(freeClassRecord);
        freeClassRecord.setNewOrderWechatUserInfo(null);
        return this;
    }

    public void setNewOrders(Set<FreeClassRecord> freeClassRecords) {
        this.newOrders = freeClassRecords;
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
        NewOrderWechatUserInfo newOrderWechatUserInfo = (NewOrderWechatUserInfo) o;
        if (newOrderWechatUserInfo.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), newOrderWechatUserInfo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "NewOrderWechatUserInfo{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", encryptedData='" + getEncryptedData() + "'" +
            ", iv='" + getIv() + "'" +
            ", nickName='" + getNickName() + "'" +
            ", gender='" + getGender() + "'" +
            ", language='" + getLanguage() + "'" +
            ", city='" + getCity() + "'" +
            ", province='" + getProvince() + "'" +
            ", country='" + getCountry() + "'" +
            ", avatarUrl='" + getAvatarUrl() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            ", openId='" + getOpenId() + "'" +
            ", comments='" + getComments() + "'" +
            "}";
    }
}
