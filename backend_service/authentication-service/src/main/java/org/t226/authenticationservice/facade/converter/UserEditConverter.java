package org.t226.authenticationservice.facade.converter;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.t226.authenticationservice.controller.model.UserEditDto;
import org.t226.authenticationservice.user.User;

@RequiredArgsConstructor
@Component
public class UserEditConverter implements Converter<User, UserEditDto> {

    private final ModelMapper modelMapper;

    @Override
    public User toModel(UserEditDto dto) {
        return modelMapper.map(dto, User.class);
    }

    @Override
    public UserEditDto toDto(User model) {
        return modelMapper.map(model, UserEditDto.class);
    }
}
