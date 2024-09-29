package org.t226.studentservice.facade.model;

import lombok.Data;

@Data
public class UserDto {

    private String uuid;
    private String name;
    private String username;
    private String password;
    private String email;
    private Role role;
}
