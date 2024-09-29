package org.t226.authenticationservice.controller.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.t226.authenticationservice.user.Role;

@Data
@AllArgsConstructor
public class UserWithAddressDto {
    private String uuid;
    private String name;
    private String username;
    private String email;
    private Address address;
    private Role role;
}
