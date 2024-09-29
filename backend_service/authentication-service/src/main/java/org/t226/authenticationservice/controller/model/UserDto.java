package org.t226.authenticationservice.controller.model;

import lombok.Data;
import org.t226.authenticationservice.user.Role;

@Data
public class UserDto {

    private String uuid;
    private String name;
    private String username;
    private String password;
    private String email;
    private Role role;
}
