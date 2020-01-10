package com.pure.service.service.dto.dto;

import java.io.Serializable;

public class WechatFreeClassNotify implements Serializable {

    private WechatNotifyValue name1;
    private WechatNotifyValue phrase2;
    private WechatNotifyValue thing3;
    private WechatNotifyValue thing4;
    private WechatNotifyValue thing5;

    public WechatNotifyValue getName1() {
        return name1;
    }

    public void setName1(WechatNotifyValue name1) {
        this.name1 = name1;
    }

    public WechatNotifyValue getPhrase2() {
        return phrase2;
    }

    public void setPhrase2(WechatNotifyValue phrase2) {
        this.phrase2 = phrase2;
    }

    public WechatNotifyValue getThing3() {
        return thing3;
    }

    public void setThing3(WechatNotifyValue thing3) {
        this.thing3 = thing3;
    }

    public WechatNotifyValue getThing4() {
        return thing4;
    }

    public void setThing4(WechatNotifyValue thing4) {
        this.thing4 = thing4;
    }

    public WechatNotifyValue getThing5() {
        return thing5;
    }

    public void setThing5(WechatNotifyValue thing5) {
        this.thing5 = thing5;
    }

    @Override
    public String toString() {
        return "WechatFreeClassNotify{" +
            "name1=" + name1 +
            ", phrase2=" + phrase2 +
            ", thing3=" + thing3 +
            ", thing4=" + thing4 +
            ", thing5=" + thing5 +
            '}';
    }
}
