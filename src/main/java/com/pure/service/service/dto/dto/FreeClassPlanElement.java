package com.pure.service.service.dto.dto;

import java.io.Serializable;
import java.time.Instant;

public class FreeClassPlanElement implements Serializable {

    private int id;
    private Instant scheduleDate;
    private String label;
    private boolean avaliable;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Instant getScheduleDate() {
        return scheduleDate;
    }

    public void setScheduleDate(Instant scheduleDate) {
        this.scheduleDate = scheduleDate;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public boolean isAvaliable() {
        return avaliable;
    }

    public void setAvaliable(boolean avaliable) {
        this.avaliable = avaliable;
    }
}
