package com.pure.service.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.Instant;
import java.util.Objects;

/**
 * A Contract.
 */
@Entity
@Table(name = "contract")
public class Contract extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "contract_number")
    private String contractNumber;

    @Column(name = "serial_number")
    private String serialNumber;

    @Column(name = "sign_date")
    private Instant signDate;

    @Column(name = "start_date")
    private Instant startDate;

    @Column(name = "end_date")
    private Instant endDate;

    @Column(name = "total_money_amount")
    private Float totalMoneyAmount;

    @Column(name = "money_should_collected")
    private Float moneyShouldCollected;

    @Column(name = "money_collected")
    private Float moneyCollected;

    @Column(name = "promotion_amount")
    private Float promotionAmount;

    @Column(name = "total_hours")
    private Integer totalHours;

    //总购买课时数
    @Column(name = "total_class_count")
    private Integer totalClassCount;

    //已上课时数
    @Column(name = "hours_taken")
    private Integer hoursTaken;

    @Column(name = "comments")
    private String comments;

    private Boolean active;

    @ManyToOne
    private Student student;

    @ManyToOne
    private Course course;

    @ManyToOne
    private ContractStatus contractStatus;

    @ManyToOne
    private Product product;

    @ManyToOne
    private CustomerCard customerCard;

    @ManyToOne
    private Customer customer;

    @ManyToOne
    private ContractNature contractNature;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove


    public Integer getTotalClassCount() {
        return totalClassCount;
    }

    public void setTotalClassCount(Integer totalClassCount) {
        this.totalClassCount = totalClassCount;
    }

    public ContractNature getContractNature() {
        return contractNature;
    }

    public void setContractNature(ContractNature contractNature) {
        this.contractNature = contractNature;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public Contract contractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
        return this;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public Contract serialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
        return this;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Instant getSignDate() {
        return signDate;
    }

    public Contract signDate(Instant signDate) {
        this.signDate = signDate;
        return this;
    }

    public void setSignDate(Instant signDate) {
        this.signDate = signDate;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public Contract startDate(Instant startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public Contract endDate(Instant endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public Float getTotalMoneyAmount() {
        return totalMoneyAmount;
    }

    public Contract totalMoneyAmount(Float totalMoneyAmount) {
        this.totalMoneyAmount = totalMoneyAmount;
        return this;
    }

    public void setTotalMoneyAmount(Float totalMoneyAmount) {
        this.totalMoneyAmount = totalMoneyAmount;
    }

    public Float getMoneyShouldCollected() {
        return moneyShouldCollected;
    }

    public Contract moneyShouldCollected(Float moneyShouldCollected) {
        this.moneyShouldCollected = moneyShouldCollected;
        return this;
    }

    public void setMoneyShouldCollected(Float moneyShouldCollected) {
        this.moneyShouldCollected = moneyShouldCollected;
    }

    public Float getMoneyCollected() {
        return moneyCollected;
    }

    public Contract moneyCollected(Float moneyCollected) {
        this.moneyCollected = moneyCollected;
        return this;
    }

    public void setMoneyCollected(Float moneyCollected) {
        this.moneyCollected = moneyCollected;
    }

    public Float getPromotionAmount() {
        return promotionAmount;
    }

    public Contract promotionAmount(Float promotionAmount) {
        this.promotionAmount = promotionAmount;
        return this;
    }

    public void setPromotionAmount(Float promotionAmount) {
        this.promotionAmount = promotionAmount;
    }

    public Integer getTotalHours() {
        return totalHours;
    }

    public Contract totalHours(Integer totalHours) {
        this.totalHours = totalHours;
        return this;
    }

    public void setTotalHours(Integer totalHours) {
        this.totalHours = totalHours;
    }

    public Integer getHoursTaken() {
        return hoursTaken;
    }

    public Contract hoursTaken(Integer hoursTaken) {
        this.hoursTaken = hoursTaken;
        return this;
    }

    public void setHoursTaken(Integer hoursTaken) {
        this.hoursTaken = hoursTaken;
    }

    public String getComments() {
        return comments;
    }

    public Contract comments(String comments) {
        this.comments = comments;
        return this;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Student getStudent() {
        return student;
    }

    public Contract student(Student student) {
        this.student = student;
        return this;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public Contract course(Course course) {
        this.course = course;
        return this;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public ContractStatus getContractStatus() {
        return contractStatus;
    }

    public Contract contractStatus(ContractStatus contractStatus) {
        this.contractStatus = contractStatus;
        return this;
    }

    public void setContractStatus(ContractStatus contractStatus) {
        this.contractStatus = contractStatus;
    }

    public Product getProduct() {
        return product;
    }

    public Contract product(Product product) {
        this.product = product;
        return this;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public CustomerCard getCustomerCard() {
        return customerCard;
    }

    public Contract customerCard(CustomerCard customerCard) {
        this.customerCard = customerCard;
        return this;
    }

    public void setCustomerCard(CustomerCard customerCard) {
        this.customerCard = customerCard;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Contract customer(Customer customer) {
        this.customer = customer;
        return this;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
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
        Contract contract = (Contract) o;
        if (contract.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), contract.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Contract{" +
            "id=" + getId() +
            ", contractNumber='" + getContractNumber() + "'" +
            ", serialNumber='" + getSerialNumber() + "'" +
            ", signDate='" + getSignDate() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", totalMoneyAmount='" + getTotalMoneyAmount() + "'" +
            ", moneyShouldCollected='" + getMoneyShouldCollected() + "'" +
            ", moneyCollected='" + getMoneyCollected() + "'" +
            ", promotionAmount='" + getPromotionAmount() + "'" +
            ", totalHours='" + getTotalHours() + "'" +
            ", hoursTaken='" + getHoursTaken() + "'" +
            ", comments='" + getComments() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}
