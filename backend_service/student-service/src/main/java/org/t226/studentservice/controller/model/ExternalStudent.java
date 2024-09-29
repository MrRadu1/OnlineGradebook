package org.t226.studentservice.controller.model;

import lombok.Data;

import java.util.List;

@Data
public class ExternalStudent {
    private String uuid;
    private String firstname;
    private String lastname;
    private String cnp;
    private Address address;
    private List<Grade> grades;
    private boolean valid;
}
