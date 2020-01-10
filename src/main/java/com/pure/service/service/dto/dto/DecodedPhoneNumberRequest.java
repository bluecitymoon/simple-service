package com.pure.service.service.dto.dto;

import java.io.Serializable;

public class DecodedPhoneNumberRequest implements Serializable {
    private String sessionKey;
    private String iv;
    private String phoneNumber;

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    public String getIv() {
        return iv;
    }

    public void setIv(String iv) {
        this.iv = iv;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
