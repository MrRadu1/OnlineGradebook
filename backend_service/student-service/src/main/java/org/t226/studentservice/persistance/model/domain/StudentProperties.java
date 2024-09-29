package org.t226.studentservice.persistance.model.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class StudentProperties implements Serializable {

    private String firstname;
    private String lastname;
    private String cnp;
    private Address address;
    private List<Grade> grades;
}
