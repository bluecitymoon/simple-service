package com.pure.service.service.dto.request;

import com.pure.service.domain.Teacher;

import java.io.Serializable;

public class SingleArrangementRequest implements Serializable {
    private Long arrangementId;
    private Teacher actualTeacher;

    public Teacher getActualTeacher() {
        return actualTeacher;
    }

    public void setActualTeacher(Teacher actualTeacher) {
        this.actualTeacher = actualTeacher;
    }

    public Long getArrangementId() {
        return arrangementId;
    }

    public void setArrangementId(Long arrangementId) {
        this.arrangementId = arrangementId;
    }
}
