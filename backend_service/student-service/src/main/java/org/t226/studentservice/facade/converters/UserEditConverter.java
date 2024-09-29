package org.t226.studentservice.facade.converters;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.t226.studentservice.controller.model.ExternalStudent;
import org.t226.studentservice.controller.model.UserEditDto;

@RequiredArgsConstructor
@Component
public class UserEditConverter implements Converter<ExternalStudent, UserEditDto> {

    private final ModelMapper modelMapper;


    @Override
    public UserEditDto toDto(ExternalStudent model) {
        return modelMapper.map(model, UserEditDto.class);
    }

    @Override
    public ExternalStudent toModel(UserEditDto dto) {
        return modelMapper.map(dto, ExternalStudent.class);
    }
}
