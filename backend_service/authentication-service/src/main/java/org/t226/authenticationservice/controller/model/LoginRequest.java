package org.t226.authenticationservice.controller.model;

import lombok.Data;


@Data
public class LoginRequest {

    private String username;

    private String password;
}
