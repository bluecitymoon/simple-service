package com.pure.service.service.dto.dto;

import java.io.Serializable;

public class WechatNotifyValue implements Serializable {

    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "WechatNotifyValue{" +
            "value='" + value + '\'' +
            '}';
    }
}
