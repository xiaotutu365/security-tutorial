package com.trey.authserver.entity;

import lombok.Data;

import java.util.List;

@Data
public class Credentials {
    private Integer id;

    private Integer enabled;

    private String name;

    private String password;

    private Integer version;

//    private List<Authority> grantedAuthorities;
}