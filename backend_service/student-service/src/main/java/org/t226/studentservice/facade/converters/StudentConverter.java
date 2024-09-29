package org.t226.studentservice.facade.converters;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.t226.studentservice.controller.model.ExternalStudent;
import org.t226.studentservice.persistance.model.Student;

@Component
@RequiredArgsConstructor
public class StudentConverter implements Converter<Student, ExternalStudent> {

    private final ModelMapper mapper;

    @Override
    public ExternalStudent toDto(Student model) {
        return mapper.map(model, ExternalStudent.class);
    }

    @Override
    public Student toModel(ExternalStudent dto) {
        return mapper.map(dto, Student.class);
    }
}
