package org.t226.authenticationservice.facade.converter;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.t226.authenticationservice.controller.model.UserDto;
import org.t226.authenticationservice.user.User;

@Component
@RequiredArgsConstructor
public class UserConverter implements Converter<User, UserDto> {

    private final ModelMapper modelMapper;

    @Override
    public User toModel(UserDto dto) {
        return modelMapper.map(dto, User.class);
    }

    @Override
    public UserDto toDto(User model) {
        return modelMapper.map(model, UserDto.class);
    }
}
