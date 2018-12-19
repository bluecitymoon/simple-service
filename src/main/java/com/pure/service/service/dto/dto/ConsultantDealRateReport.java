package com.pure.service.service.dto.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ConsultantDealRateReport implements Serializable {


    private List<String> channelNames = new ArrayList<>();
    private List<ConsultantDealRate> channelCustomerCount = new ArrayList<>();

    public List<String> getChannelNames() {
        return channelNames;
    }

    public void setChannelNames(List<String> channelNames) {
        this.channelNames = channelNames;
    }

    public List<ConsultantDealRate> getChannelCustomerCount() {
        return channelCustomerCount;
    }

    public void setChannelCustomerCount(List<ConsultantDealRate> channelCustomerCount) {
        this.channelCustomerCount = channelCustomerCount;
    }
}
