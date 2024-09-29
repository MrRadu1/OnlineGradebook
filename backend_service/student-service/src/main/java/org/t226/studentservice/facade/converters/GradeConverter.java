package org.t226.studentservice.facade.converters;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.t226.studentservice.persistance.model.domain.Grade;

@RequiredArgsConstructor
@Component
public class GradeConverter implements Converter<Grade, org.t226.studentservice.controller.model.Grade> {

    private final ModelMapper modelMapper;

    @Override
    public org.t226.studentservice.controller.model.Grade toDto(Grade model) {
        return modelMapper.map(model, org.t226.studentservice.controller.model.Grade.class);
    }

    @Override
    public Grade toModel(org.t226.studentservice.controller.model.Grade dto) {
        return modelMapper.map(dto, Grade.class);
    }
}
