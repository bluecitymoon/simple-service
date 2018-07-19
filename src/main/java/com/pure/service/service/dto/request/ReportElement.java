package com.pure.service.service.dto.request;

import java.io.Serializable;

/**
 *
 */
public class ReportElement implements Serializable {

    private String key;
    private String value;
    private String label;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
