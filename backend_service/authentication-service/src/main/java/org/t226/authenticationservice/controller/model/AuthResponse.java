package org.t226.authenticationservice.controller.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.t226.authenticationservice.user.Role;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {

    private String username;
    private String email;
    private String uuid;
    private String name;
    private Role role;
}