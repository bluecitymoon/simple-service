package com.pure.service.service.dto.dto;

import java.io.Serializable;

public class ClassroomDto implements Serializable {

    private Long id;
    private String name;

    public ClassroomDto() {
    }

    public ClassroomDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
