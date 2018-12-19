package com.pure.service.service.dto.request;

import java.io.Serializable;
import java.time.Instant;

public class SingleDateRequest implements Serializable {

    private Instant logDate;

    public Instant getLogDate() {
        return logDate;
    }

    public void setLogDate(Instant logDate) {
        this.logDate = logDate;
    }


}
