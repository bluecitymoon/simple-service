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
 * Criteria class for the ChannelReport entity. This class is used in ChannelReportResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /channel-reports?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ChannelReportCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private StringFilter month;

    private StringFilter year;

    private LongFilter channelId;

    private StringFilter channelName;

    private IntegerFilter visitedCustomerCount;

    private IntegerFilter cardCount;

    private IntegerFilter contractCount;

    private DoubleFilter moneyCollected;

    private DoubleFilter contractMadeRate;

    private LongFilter regionId;

    public ChannelReportCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getMonth() {
        return month;
    }

    public void setMonth(StringFilter month) {
        this.month = month;
    }

    public StringFilter getYear() {
        return year;
    }

    public void setYear(StringFilter year) {
        this.year = year;
    }

    public LongFilter getChannelId() {
        return channelId;
    }

    public void setChannelId(LongFilter channelId) {
        this.channelId = channelId;
    }

    public StringFilter getChannelName() {
        return channelName;
    }

    public void setChannelName(StringFilter channelName) {
        this.channelName = channelName;
    }

    public IntegerFilter getVisitedCustomerCount() {
        return visitedCustomerCount;
    }

    public void setVisitedCustomerCount(IntegerFilter visitedCustomerCount) {
        this.visitedCustomerCount = visitedCustomerCount;
    }

    public IntegerFilter getCardCount() {
        return cardCount;
    }

    public void setCardCount(IntegerFilter cardCount) {
        this.cardCount = cardCount;
    }

    public IntegerFilter getContractCount() {
        return contractCount;
    }

    public void setContractCount(IntegerFilter contractCount) {
        this.contractCount = contractCount;
    }

    public DoubleFilter getMoneyCollected() {
        return moneyCollected;
    }

    public void setMoneyCollected(DoubleFilter moneyCollected) {
        this.moneyCollected = moneyCollected;
    }

    public DoubleFilter getContractMadeRate() {
        return contractMadeRate;
    }

    public void setContractMadeRate(DoubleFilter contractMadeRate) {
        this.contractMadeRate = contractMadeRate;
    }

    public LongFilter getRegionId() {
        return regionId;
    }

    public void setRegionId(LongFilter regionId) {
        this.regionId = regionId;
    }

    @Override
    public String toString() {
        return "ChannelReportCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (month != null ? "month=" + month + ", " : "") +
                (year != null ? "year=" + year + ", " : "") +
                (channelId != null ? "channelId=" + channelId + ", " : "") +
                (channelName != null ? "channelName=" + channelName + ", " : "") +
                (visitedCustomerCount != null ? "visitedCustomerCount=" + visitedCustomerCount + ", " : "") +
                (cardCount != null ? "cardCount=" + cardCount + ", " : "") +
                (contractCount != null ? "contractCount=" + contractCount + ", " : "") +
                (moneyCollected != null ? "moneyCollected=" + moneyCollected + ", " : "") +
                (contractMadeRate != null ? "contractMadeRate=" + contractMadeRate + ", " : "") +
                (regionId != null ? "regionId=" + regionId + ", " : "") +
            "}";
    }

}
