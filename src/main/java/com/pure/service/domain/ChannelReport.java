package com.pure.service.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ChannelReport.
 */
@Entity
@Table(name = "channel_report")
public class ChannelReport implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "month")
    private String month;

    @Column(name = "jhi_year")
    private String year;

    @Column(name = "channel_id")
    private Long channelId;

    @Column(name = "channel_name")
    private String channelName;

    @Column(name = "visited_customer_count")
    private Integer visitedCustomerCount;

    @Column(name = "card_count")
    private Integer cardCount;

    @Column(name = "contract_count")
    private Integer contractCount;

    @Column(name = "money_collected")
    private Double moneyCollected;

    @Column(name = "contract_made_rate")
    private Double contractMadeRate;

    @Column(name = "region_id")
    private Long regionId;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMonth() {
        return month;
    }

    public ChannelReport month(String month) {
        this.month = month;
        return this;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public ChannelReport year(String year) {
        this.year = year;
        return this;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Long getChannelId() {
        return channelId;
    }

    public ChannelReport channelId(Long channelId) {
        this.channelId = channelId;
        return this;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }

    public String getChannelName() {
        return channelName;
    }

    public ChannelReport channelName(String channelName) {
        this.channelName = channelName;
        return this;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public Integer getVisitedCustomerCount() {
        return visitedCustomerCount;
    }

    public ChannelReport visitedCustomerCount(Integer visitedCustomerCount) {
        this.visitedCustomerCount = visitedCustomerCount;
        return this;
    }

    public void setVisitedCustomerCount(Integer visitedCustomerCount) {
        this.visitedCustomerCount = visitedCustomerCount;
    }

    public Integer getCardCount() {
        return cardCount;
    }

    public ChannelReport cardCount(Integer cardCount) {
        this.cardCount = cardCount;
        return this;
    }

    public void setCardCount(Integer cardCount) {
        this.cardCount = cardCount;
    }

    public Integer getContractCount() {
        return contractCount;
    }

    public ChannelReport contractCount(Integer contractCount) {
        this.contractCount = contractCount;
        return this;
    }

    public void setContractCount(Integer contractCount) {
        this.contractCount = contractCount;
    }

    public Double getMoneyCollected() {
        return moneyCollected;
    }

    public ChannelReport moneyCollected(Double moneyCollected) {
        this.moneyCollected = moneyCollected;
        return this;
    }

    public void setMoneyCollected(Double moneyCollected) {
        this.moneyCollected = moneyCollected;
    }

    public Double getContractMadeRate() {
        return contractMadeRate;
    }

    public ChannelReport contractMadeRate(Double contractMadeRate) {
        this.contractMadeRate = contractMadeRate;
        return this;
    }

    public void setContractMadeRate(Double contractMadeRate) {
        this.contractMadeRate = contractMadeRate;
    }

    public Long getRegionId() {
        return regionId;
    }

    public ChannelReport regionId(Long regionId) {
        this.regionId = regionId;
        return this;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
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
        ChannelReport channelReport = (ChannelReport) o;
        if (channelReport.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), channelReport.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ChannelReport{" +
            "id=" + getId() +
            ", month='" + getMonth() + "'" +
            ", year='" + getYear() + "'" +
            ", channelId='" + getChannelId() + "'" +
            ", channelName='" + getChannelName() + "'" +
            ", visitedCustomerCount='" + getVisitedCustomerCount() + "'" +
            ", cardCount='" + getCardCount() + "'" +
            ", contractCount='" + getContractCount() + "'" +
            ", moneyCollected='" + getMoneyCollected() + "'" +
            ", contractMadeRate='" + getContractMadeRate() + "'" +
            ", regionId='" + getRegionId() + "'" +
            "}";
    }
}
