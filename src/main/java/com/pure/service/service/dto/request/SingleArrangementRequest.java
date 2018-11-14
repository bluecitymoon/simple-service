package com.pure.service.service.dto.request;

import java.io.Serializable;

public class SingleArrangementRequest implements Serializable {
    private Long arrangementId;

    public Long getArrangementId() {
        return arrangementId;
    }

    public void setArrangementId(Long arrangementId) {
        this.arrangementId = arrangementId;
    }
}
