package com.trey.authserver.entity;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

@Data
public class Authority implements GrantedAuthority {
    private String credentialsId;

    private String authoritiesId;

    @Override
    public String getAuthority() {
        return null;
    }
}
