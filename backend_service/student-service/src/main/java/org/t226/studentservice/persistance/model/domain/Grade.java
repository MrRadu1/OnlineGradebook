package org.t226.studentservice.persistance.model.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Data
public class Grade implements Serializable {

    private String discipline;
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate date;
    private Integer grade;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Grade grade1 = (Grade) o;
        return Objects.equals(discipline, grade1.discipline) && Objects.equals(date, grade1.date) && Objects.equals(grade, grade1.grade);
    }



}
