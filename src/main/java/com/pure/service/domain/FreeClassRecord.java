package com.pure.service.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A FreeClassRecord.
 */
@Entity
@Table(name = "free_class_record")
public class FreeClassRecord extends AbstractAuditingRegionEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "person_name")
    private String personName;

    @Column(name = "contact_phone_number", unique = true)
//    @Size(max = 11, min = 11)
    private String contactPhoneNumber;

    @Column(name = "status")
    private String status;

    @Column(name = "agent_id")
    private Long agentId;

    @Column(name = "birthday")
    private Instant birthday;

    @Column(name = "school")
    private String school;

    @Column(name = "comments")
    private String comments;

    @Column(name = "age")
    private Integer age;

    @Column(name = "class_level")
    private String classLevel;

    @Column(name = "source_type")
    private String sourceType;

    @Column(name = "schedule_date")
    private Instant scheduleDate;

    @Column(name = "gift_code")
    private String giftCode;

    @Column(name = "outer_referer")
    private String outerReferer;

    @ManyToOne
    private MarketChannelCategory marketChannelCategory;

    @ManyToOne
    private NewOrderResourceLocation newOrderResourceLocation;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "free_class_record_class_category",
               joinColumns = @JoinColumn(name="free_class_records_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="class_categories_id", referencedColumnName="id"))
    private Set<ClassCategory> classCategories = new HashSet<>();

    @ManyToOne
    private User salesFollower;

    @ManyToOne
    private User referer;

    @ManyToOne
    private NewOrderWechatUserInfo newOrderWechatUserInfo;

    public String getOuterReferer() {
        return outerReferer;
    }

    public void setOuterReferer(String outerReferer) {
        this.outerReferer = outerReferer;
    }

    public Instant getScheduleDate() {
        return scheduleDate;
    }

    public void setScheduleDate(Instant scheduleDate) {
        this.scheduleDate = scheduleDate;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAgentId() {
        return agentId;
    }

    public void setAgentId(Long agentId) {
        this.agentId = agentId;
    }

    public User getReferer() {
        return referer;
    }

    public String getGiftCode() {
        return giftCode;
    }

    public void setGiftCode(String giftCode) {
        this.giftCode = giftCode;
    }

    public FreeClassRecord setReferer(User referer) {
        this.referer = referer;

        return this;
    }

    public String getPersonName() {
        return personName;
    }

    public FreeClassRecord personName(String personName) {
        this.personName = personName;
        return this;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getContactPhoneNumber() {
        return contactPhoneNumber;
    }

    public FreeClassRecord contactPhoneNumber(String contactPhoneNumber) {
        this.contactPhoneNumber = contactPhoneNumber;
        return this;
    }

    public void setContactPhoneNumber(String contactPhoneNumber) {
        this.contactPhoneNumber = contactPhoneNumber;
    }

    public NewOrderWechatUserInfo getNewOrderWechatUserInfo() {
        return newOrderWechatUserInfo;
    }

    public FreeClassRecord setNewOrderWechatUserInfo(NewOrderWechatUserInfo newOrderWechatUserInfo) {
        this.newOrderWechatUserInfo = newOrderWechatUserInfo;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public FreeClassRecord status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Instant getBirthday() {
        return birthday;
    }

    public FreeClassRecord birthday(Instant birthday) {
        this.birthday = birthday;
        return this;
    }

    public void setBirthday(Instant birthday) {
        this.birthday = birthday;
    }

    public MarketChannelCategory getMarketChannelCategory() {
        return marketChannelCategory;
    }

    public FreeClassRecord marketChannelCategory(MarketChannelCategory marketChannelCategory) {
        this.marketChannelCategory = marketChannelCategory;
        return this;
    }

    public void setMarketChannelCategory(MarketChannelCategory marketChannelCategory) {
        this.marketChannelCategory = marketChannelCategory;
    }

    public Set<ClassCategory> getClassCategories() {
        return classCategories;
    }

    public FreeClassRecord classCategories(Set<ClassCategory> classCategories) {
        this.classCategories = classCategories;
        return this;
    }

    public FreeClassRecord addClassCategory(ClassCategory classCategory) {
        this.classCategories.add(classCategory);
        classCategory.getFreeClassRecords().add(this);
        return this;
    }

    public FreeClassRecord removeClassCategory(ClassCategory classCategory) {
        this.classCategories.remove(classCategory);
        classCategory.getFreeClassRecords().remove(this);
        return this;
    }

    public void setClassCategories(Set<ClassCategory> classCategories) {
        this.classCategories = classCategories;
    }

    public User getSalesFollower() {
        return salesFollower;
    }

    public FreeClassRecord salesFollower(User user) {
        this.salesFollower = user;
        return this;
    }

    public void setSalesFollower(User user) {
        this.salesFollower = user;
    }

    public NewOrderResourceLocation getNewOrderResourceLocation() {
        return newOrderResourceLocation;
    }

    public void setNewOrderResourceLocation(NewOrderResourceLocation newOrderResourceLocation) {
        this.newOrderResourceLocation = newOrderResourceLocation;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getClassLevel() {
        return classLevel;
    }

    public void setClassLevel(String classLevel) {
        this.classLevel = classLevel;
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
        FreeClassRecord freeClassRecord = (FreeClassRecord) o;
        if (freeClassRecord.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), freeClassRecord.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FreeClassRecord{" +
            "id=" + getId() +
            ", personName='" + getPersonName() + "'" +
            ", contactPhoneNumber='" + getContactPhoneNumber() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            ", status='" + getStatus() + "'" +
            ", birthday='" + getBirthday() + "'" +
            "}";
    }
}
