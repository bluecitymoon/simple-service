package com.pure.service.service.dto.dto;

import java.time.Instant;

public class WeekElement {

    private Integer weekIndex;
    private Instant start;
    private Instant end;
    private Integer dayCount;

    public Integer getWeekIndex() {
        return weekIndex;
    }

    public void setWeekIndex(Integer weekIndex) {
        this.weekIndex = weekIndex;
    }

    public Instant getStart() {
        return start;
    }

    public void setStart(Instant start) {
        this.start = start;
    }

    public Instant getEnd() {
        return end;
    }

    public void setEnd(Instant end) {
        this.end = end;
    }

    public Integer getDayCount() {
        return dayCount;
    }

    public void setDayCount(Integer dayCount) {
        this.dayCount = dayCount;
    }

    @Override
    public String toString() {
        return "WeekElement{" +
            "weekIndex=" + weekIndex +
            ", start=" + start +
            ", end=" + end +
            ", dayCount=" + dayCount +
            '}';
    }
}
