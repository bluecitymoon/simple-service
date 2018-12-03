package com.pure.service.domain;


import com.pure.service.service.dto.dto.ChannelReportElement;
import com.pure.service.service.dto.dto.LocationStatusReportEntity;
import com.pure.service.service.dto.dto.Overview;
import com.pure.service.service.dto.dto.ReportEntity;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.OneToOne;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;
import javax.persistence.Table;
import java.time.Instant;
import java.util.Objects;

/**
 * A Customer.
 */
@NamedNativeQueries({
    @NamedNativeQuery(name = "Customer.searchCustomerStatusReport",
query = "select u.id as userId, u.first_name as userName, count(0) as count, cs.name as statusName, cs.code as statusCode\n" +
        "\t from customer t \n" +
        "\t     cross join customer_status cs on t.status_id = cs.id\n" +
        "\t     cross join jhi_user u on t.sales_follower_id  = u.id\n" +
        "\t where t.assign_date < :2 and t.assign_date > :1\n" +
        "\t and exists (select 1 from `jhi_user_authority` a where a.`user_id` = u.id and a.`authority_name` = 'ROLE_COURSE_CONSULTANT') \n" +
        "\t and t.region_id = :3 \n" +
        "\t     group by t.status_id, u.id;",
    resultSetMapping = "reportMapping"),
    @NamedNativeQuery(name = "Customer.searchCurrentUserOverview",
query = "select count(0) as untrackedCustomerCount from customer t \n" +
        "\tleft join `customer_status` cs on t.`status_id` = cs.id\n" +
        "\t where `sales_follower_id` = :1\n" +
        "\t \tand cs.`code` = 'new_created' \t\n" +
        "\t \tand t.assign_date < :2 \n" +
        "\t \tand t.assign_date > :3" +
        "\t \tand t.region_id = :4 \n" ,
    resultSetMapping = "overviewMapping"),

    @NamedNativeQuery(name="Customer.searchLocationCustomerStatusReport",

query = "select count(0) as count, l.name as location, l.id as locationId, cs.code as statusCode, cs.name statusName from customer t \n" +
        "\tcross join new_order_resource_location l on t.new_order_resource_location_id = l.`id`\n" +
        "\tcross join `customer_status` cs on t.`status_id` = cs.id\n" +
        "\twhere t.assign_date < :2 and t.assign_date > :1\n" +
        "\t and t.region_id = :3 \n" +
        " group by l.`id`, cs.id ",
    resultSetMapping = "locationStatusMapping"),

    @NamedNativeQuery(name = "Customer.searchChannelReport",
    query = "select  cs.id as channelId, cs.name as channelName, count(0) as visitedCustomerCount from customer c \n" +
        "\tleft join market_channel_category cs on c.channel_id = cs.id\n" +
        "\twhere c.visit_date < :2 and c.visit_date > :1\n" +
        "\t and c.region_id = :3 \n" +
        "\tgroup by cs.id\n",

    resultSetMapping = "channelMapping")

})

@SqlResultSetMappings({
    @SqlResultSetMapping(name = "overviewMapping",
        classes = @ConstructorResult(targetClass = Overview.class,
            columns = {
                @ColumnResult(name = "untrackedCustomerCount", type = Integer.class),
            })
    ),
    @SqlResultSetMapping(name = "reportMapping",
        classes = @ConstructorResult(targetClass = ReportEntity.class,
            columns = {
                @ColumnResult(name = "userId", type = Long.class),
                @ColumnResult(name = "userName", type = String.class),
                @ColumnResult(name = "count", type = Integer.class),
                @ColumnResult(name = "statusName", type = String.class),
                @ColumnResult(name = "statusCode", type = String.class)
            })),
    @SqlResultSetMapping(name = "locationStatusMapping",
        classes = @ConstructorResult(targetClass = LocationStatusReportEntity.class,
            columns = {
                @ColumnResult(name = "count", type = Integer.class),
                @ColumnResult(name = "location", type = String.class),
                @ColumnResult(name = "locationId", type = Long.class),
                @ColumnResult(name = "statusCode", type = String.class),
                @ColumnResult(name = "statusName", type = String.class)
            })),
    @SqlResultSetMapping(name = "channelMapping",
        classes = @ConstructorResult(targetClass = ChannelReportElement.class,
            columns = {
                @ColumnResult(name = "channelId", type = Long.class),
                @ColumnResult(name = "channelName", type = String.class),
                @ColumnResult(name = "visitedCustomerCount", type = Integer.class)
            }))
})


@Entity
@Table(name = "customer")
public class Customer extends AbstractAuditingRegionEntity {

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

    @Column(name = "last_track_date")
    private Instant lastTrackDate;

    @Column(name = "track_count")
    private Integer trackCount;

    @Column(name = "last_track_comments")
    private String lastTrackComments;

    @Column(name = "next_track_comments")
    private String nextTrackComments;

    @Column(name = "next_schedule_date")
    private Instant nextScheduleDate;

    @Column(name = "next_schedule_comments")
    private String nextScheduleComments;

    @Column(name = "school")
    private String school;

    @Column(name = "assign_date")
    private Instant assignDate;

    @Column(name = "course_consultant_assign_date")
    private Instant courseConsultantAssignDate;

    @OneToOne
    @JoinColumn(unique = true)
    private FreeClassRecord newOrder;

    @ManyToOne
    private CustomerStatus status;

    @ManyToOne
    private VistedCustomerStatus vistedCustomerStatus;

    @ManyToOne
    private MarketChannelCategory channel;

    @ManyToOne
    private User salesFollower;

    @ManyToOne
    private User courseConsultant;

    @ManyToOne
    private NewOrderResourceLocation newOrderResourceLocation;

    public VistedCustomerStatus getVistedCustomerStatus() {
        return vistedCustomerStatus;
    }

    public void setVistedCustomerStatus(VistedCustomerStatus vistedCustomerStatus) {
        this.vistedCustomerStatus = vistedCustomerStatus;
    }

    public Instant getCourseConsultantAssignDate() {
        return courseConsultantAssignDate;
    }

    public void setCourseConsultantAssignDate(Instant courseConsultantAssignDate) {
        this.courseConsultantAssignDate = courseConsultantAssignDate;
    }

    public String getNextTrackComments() {
        return nextTrackComments;
    }

    public void setNextTrackComments(String nextTrackComments) {
        this.nextTrackComments = nextTrackComments;
    }

    public Instant getAssignDate() {
        return assignDate;
    }

    public void setAssignDate(Instant assignDate) {
        this.assignDate = assignDate;
    }

    public Instant getLastTrackDate() {
        return lastTrackDate;
    }

    public void setLastTrackDate(Instant lastTrackDate) {
        this.lastTrackDate = lastTrackDate;
    }

    public Integer getTrackCount() {
        return trackCount;
    }

    public void setTrackCount(Integer trackCount) {
        this.trackCount = trackCount;
    }

    public String getLastTrackComments() {
        return lastTrackComments;
    }

    public void setLastTrackComments(String lastTrackComments) {
        this.lastTrackComments = lastTrackComments;
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

    public String getNextScheduleComments() {
        return nextScheduleComments;
    }

    public void setNextScheduleComments(String nextScheduleComments) {
        this.nextScheduleComments = nextScheduleComments;
    }

    public Instant getNextScheduleDate() {
        return nextScheduleDate;
    }

    public void setNextScheduleDate(Instant nextScheduleDate) {
        this.nextScheduleDate = nextScheduleDate;
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
