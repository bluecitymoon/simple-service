package com.pure.service.service.dto.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CombinedConsultantReport implements Serializable {

    private List<ConsultantWork> consultantWorks = new ArrayList<>();
    private ConsultantDealRateReport consultantDealCount;
    private List<UserBasedConsultantReport> userBasedConsultantReports = new ArrayList<>();

    public List<UserBasedConsultantReport> getUserBasedConsultantReports() {
        return userBasedConsultantReports;
    }

    public void setUserBasedConsultantReports(List<UserBasedConsultantReport> userBasedConsultantReports) {
        this.userBasedConsultantReports = userBasedConsultantReports;
    }

    public List<ConsultantWork> getConsultantWorks() {
        return consultantWorks;
    }

    public void setConsultantWorks(List<ConsultantWork> consultantWorks) {
        this.consultantWorks = consultantWorks;
    }

    public ConsultantDealRateReport getConsultantDealCount() {
        return consultantDealCount;
    }

    public void setConsultantDealCount(ConsultantDealRateReport consultantDealCount) {
        this.consultantDealCount = consultantDealCount;
    }
}
