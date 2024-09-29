package org.t226.authenticationservice.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.t226.authenticationservice.controller.model.UserDto;
import org.t226.authenticationservice.controller.model.UserEditDto;
import org.t226.authenticationservice.controller.model.UserWithAddressDto;
import org.t226.authenticationservice.facade.converter.UserConverter;
import org.t226.authenticationservice.facade.converter.UserEditConverter;
import org.t226.authenticationservice.facade.model.ExternalStudent;
import org.t226.authenticationservice.service.UserService;
import org.t226.authenticationservice.user.User;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UserFacade {

    private final UserService userService;
    private final UserConverter converter;
    private final UserEditConverter editConverter;

    public List<UserDto> findAll() {
        List<User> all = userService.findAll();
        List<UserDto> userDtos = new ArrayList<>();
        for (User user : all) {
            userDtos.add(converter.toDto(user));
        }
        return userDtos;
    }

    public UserDto save(UserDto userDto) {
        return converter.toDto(userService.save(converter.toModel(userDto)));
    }

    public UserWithAddressDto find(String uuid) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<ExternalStudent> forEntity = restTemplate.getForEntity("http://localhost:8081/api/students/" + uuid, ExternalStudent.class);
        ExternalStudent body = forEntity.getBody();
        UserDto userDto = converter.toDto(userService.find(uuid));
        String name = body.getFirstname() + " " + body.getLastname();
        UserWithAddressDto userWithAddressDto = new UserWithAddressDto(userDto.getUuid(), name, userDto.getUsername(), userDto.getEmail(), body.getAddress(), userDto.getRole());
        return userWithAddressDto;
    }

    public void delete(String uuid) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete("http://localhost:8081/api/students/delete-from-user/" + uuid);
        userService.delete(uuid);
    }

    public void update(String uuid, UserEditDto userDto) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.put("http://localhost:8081/api/students/edit-from-user/" + uuid, userDto, UserEditDto.class);
        userService.update(uuid, editConverter.toModel(userDto));
    }
}
