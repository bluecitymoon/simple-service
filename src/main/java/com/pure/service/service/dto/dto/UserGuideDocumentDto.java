package com.pure.service.service.dto.dto;

import com.pure.service.domain.Authority;
import com.pure.service.domain.UserGuideDocument;

import java.util.List;

public class UserGuideDocumentDto extends UserGuideDocument {

    private List<Authority> authorities;

    public List<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<Authority> authorities) {
        this.authorities = authorities;
    }
}
