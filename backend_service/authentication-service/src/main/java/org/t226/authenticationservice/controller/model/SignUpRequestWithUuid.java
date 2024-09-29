package org.t226.authenticationservice.controller.model;

import lombok.Data;

@Data
public class SignUpRequestWithUuid {

    private String uuid;

    private String username;

    private String password;

    private String firstname;

    private String lastname;

    private String email;

    private Address address;

    private String cnp;
}
