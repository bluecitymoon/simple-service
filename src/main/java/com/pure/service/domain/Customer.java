package com.pure.service.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A Customer.
 */
@Entity
@Table(name = "customer")
public class Customer extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private Integer age;

    @Column(name = "contact_phone_number")
    private String contactPhoneNumber;

    @Column(name = "sex")
    private String sex;

    @Column(name = "birthday")
    private Instant birthday;

    @Column(name = "address")
    private String address;

    @Column(name = "hoby")
    private String hoby;

    @Column(name = "email")
    private String email;

    @Column(name = "class_level")
    private String classLevel;

    @Column(name = "parent_name")
    private String parentName;

    @Column(name = "parent_contract_number")
    private String parentContractNumber;

    @Column(name = "visit_date")
    private Instant visitDate;

    @Column(name = "track_status")
    private String trackStatus;

    @Column(name = "next_track_date")
    private Instant nextTrackDate;

    @OneToOne
    @JoinColumn(unique = true)
    private FreeClassRecord newOrder;

    @ManyToOne
    private CustomerStatus status;

    @ManyToOne
    private MarketChannelCategory channel;

    @ManyToOne
    private User salesFollower;

    @ManyToOne
    private User courseConsultant;

    @ManyToOne
    private NewOrderResourceLocation newOrderResourceLocation;

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

    public Customer name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public Customer age(Integer age) {
        this.age = age;
        return this;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getContactPhoneNumber() {
        return contactPhoneNumber;
    }

    public Customer contactPhoneNumber(String contactPhoneNumber) {
        this.contactPhoneNumber = contactPhoneNumber;
        return this;
    }

    public void setContactPhoneNumber(String contactPhoneNumber) {
        this.contactPhoneNumber = contactPhoneNumber;
    }


    public String getSex() {
        return sex;
    }

    public Customer sex(String sex) {
        this.sex = sex;
        return this;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Instant getBirthday() {
        return birthday;
    }

    public Customer birthday(Instant birthday) {
        this.birthday = birthday;
        return this;
    }

    public void setBirthday(Instant birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }

    public Customer address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHoby() {
        return hoby;
    }

    public Customer hoby(String hoby) {
        this.hoby = hoby;
        return this;
    }

    public void setHoby(String hoby) {
        this.hoby = hoby;
    }

    public String getEmail() {
        return email;
    }

    public Customer email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getClassLevel() {
        return classLevel;
    }

    public Customer classLevel(String classLevel) {
        this.classLevel = classLevel;
        return this;
    }

    public void setClassLevel(String classLevel) {
        this.classLevel = classLevel;
    }

    public String getParentName() {
        return parentName;
    }

    public Customer parentName(String parentName) {
        this.parentName = parentName;
        return this;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getParentContractNumber() {
        return parentContractNumber;
    }

    public Customer parentContractNumber(String parentContractNumber) {
        this.parentContractNumber = parentContractNumber;
        return this;
    }

    public void setParentContractNumber(String parentContractNumber) {
        this.parentContractNumber = parentContractNumber;
    }

    public Instant getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(Instant visitDate) {
        this.visitDate = visitDate;
    }

    public FreeClassRecord getNewOrder() {
        return newOrder;
    }

    public Customer newOrder(FreeClassRecord freeClassRecord) {
        this.newOrder = freeClassRecord;
        return this;
    }

    public void setNewOrder(FreeClassRecord freeClassRecord) {
        this.newOrder = freeClassRecord;
    }

    public CustomerStatus getStatus() {
        return status;
    }

    public Customer status(CustomerStatus customerStatus) {
        this.status = customerStatus;
        return this;
    }

    public void setStatus(CustomerStatus customerStatus) {
        this.status = customerStatus;
    }

    public MarketChannelCategory getChannel() {
        return channel;
    }

    public Customer channel(MarketChannelCategory marketChannelCategory) {
        this.channel = marketChannelCategory;
        return this;
    }

    public void setChannel(MarketChannelCategory marketChannelCategory) {
        this.channel = marketChannelCategory;
    }

    public User getSalesFollower() {
        return salesFollower;
    }

    public Customer setSalesFollower(User salesFollower) {
        this.salesFollower = salesFollower;

        return this;
    }

    public User getCourseConsultant() {
        return courseConsultant;
    }

    public void setCourseConsultant(User courseConsultant) {
        this.courseConsultant = courseConsultant;
    }

    public NewOrderResourceLocation getNewOrderResourceLocation() {
        return newOrderResourceLocation;
    }

    public void setNewOrderResourceLocation(NewOrderResourceLocation newOrderResourceLocation) {
        this.newOrderResourceLocation = newOrderResourceLocation;
    }

    public String getTrackStatus() {
        return trackStatus;
    }

    public void setTrackStatus(String trackStatus) {
        this.trackStatus = trackStatus;
    }

    public Instant getNextTrackDate() {
        return nextTrackDate;
    }

    public void setNextTrackDate(Instant nextTrackDate) {
        this.nextTrackDate = nextTrackDate;
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
        Customer customer = (Customer) o;
        if (customer.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), customer.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Customer{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", age='" + getAge() + "'" +
            ", contactPhoneNumber='" + getContactPhoneNumber() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            ", sex='" + getSex() + "'" +
            ", birthday='" + getBirthday() + "'" +
            ", address='" + getAddress() + "'" +
            ", hoby='" + getHoby() + "'" +
            ", email='" + getEmail() + "'" +
            ", classLevel='" + getClassLevel() + "'" +
            ", parentName='" + getParentName() + "'" +
            ", parentContractNumber='" + getParentContractNumber() + "'" +
            "}";
    }
}
