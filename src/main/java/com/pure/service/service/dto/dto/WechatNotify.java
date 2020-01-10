package com.pure.service.service.dto.dto;

import java.io.Serializable;

public class WechatNotify implements Serializable {
    private String access_token;
    private String touser;
    private String template_id;
    private WechatFreeClassNotify data;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getTouser() {
        return touser;
    }

    public void setTouser(String touser) {
        this.touser = touser;
    }

    public String getTemplate_id() {
        return template_id;
    }

    public void setTemplate_id(String template_id) {
        this.template_id = template_id;
    }

    public WechatFreeClassNotify getData() {
        return data;
    }

    public void setData(WechatFreeClassNotify data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "WechatNotify{" +
            "access_token='" + access_token + '\'' +
            ", touser='" + touser + '\'' +
            ", template_id='" + template_id + '\'' +
            ", data=" + data +
            '}';
    }
}
