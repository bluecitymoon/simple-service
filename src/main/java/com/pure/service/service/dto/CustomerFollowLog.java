package com.pure.service.service.dto;

import com.pure.service.domain.Customer;
import com.pure.service.domain.CustomerCommunicationLogType;

import java.time.Instant;

public class CustomerFollowLog extends Customer {

    private CustomerCommunicationLogType logType;
    private String lastFollowComments;
    private Instant lastFollowTime;
    private Integer followCount;

    public CustomerCommunicationLogType getLogType() {
        return logType;
    }

    public void setLogType(CustomerCommunicationLogType logType) {
        this.logType = logType;
    }

    public String getLastFollowComments() {
        return lastFollowComments;
    }

    public void setLastFollowComments(String lastFollowComments) {
        this.lastFollowComments = lastFollowComments;
    }

    public Instant getLastFollowTime() {
        return lastFollowTime;
    }

    public void setLastFollowTime(Instant lastFollowTime) {
        this.lastFollowTime = lastFollowTime;
    }

    public Integer getFollowCount() {
        return followCount;
    }

    public void setFollowCount(Integer followCount) {
        this.followCount = followCount;
    }
}
