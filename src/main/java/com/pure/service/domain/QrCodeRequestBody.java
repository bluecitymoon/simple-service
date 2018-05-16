package com.pure.service.domain;

import java.io.Serializable;

public class QrCodeRequestBody implements Serializable {
    private String path;
    private Integer width;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }
}
