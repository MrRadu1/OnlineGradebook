package org.t226.authenticationservice.controller.model;

import lombok.Data;


@Data
public class SignUpRequest {

    private String username;

    private String password;

    private String firstName;

    private String lastName;

    private String email;

    private Address address;

    private String cnp;
}