package org.t226.authenticationservice.facade.model;

import lombok.Data;
import org.t226.authenticationservice.controller.model.Address;

import java.util.List;

@Data
public class ExternalStudent {
    private String uuid;
    private String firstname;
    private String lastname;
    private String cnp;
    private Address address;
}
