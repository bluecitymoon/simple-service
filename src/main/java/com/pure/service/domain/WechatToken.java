package com.pure.service.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class WechatToken implements Serializable {

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("expires_in")
    private Integer expiresIn;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Integer getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Integer expiresIn) {
        this.expiresIn = expiresIn;
    }

    @Override
    public String toString() {
        return "WechatToken{" +
            "accessToken='" + accessToken + '\'' +
            ", expiresIn=" + expiresIn +
            '}';
    }
}
