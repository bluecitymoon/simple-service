package com.pure.service.service.dto.dto;

import java.io.Serializable;

public class CommonResponse implements Serializable {
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
