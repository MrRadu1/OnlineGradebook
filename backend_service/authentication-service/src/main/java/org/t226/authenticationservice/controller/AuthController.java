package org.t226.authenticationservice.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.t226.authenticationservice.controller.model.*;
import org.t226.authenticationservice.facade.UserFacade;
import org.t226.authenticationservice.service.UserService;
import org.t226.authenticationservice.user.Role;
import org.t226.authenticationservice.user.User;

import java.util.Optional;


@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final UserFacade userFacade;
    private final ModelMapper modelMapper;

    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
        Optional<User> userOptional = userService.validUsernameAndPassword(loginRequest.getUsername(), loginRequest.getPassword());
        try {
            User user = userOptional.get();
            AuthResponse authResponse = new AuthResponse(user.getUsername(),
                    user.getEmail(),
                    user.getUuid(),
                    user.getName(),
                    user.getRole());
            return ResponseEntity.ok(authResponse);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signUp(@RequestBody SignUpRequest signUpRequest) {
        UserDto user = userFacade.save(createUser(signUpRequest));
        RestTemplate restTemplate = new RestTemplate();
        SignUpRequestWithUuid map = modelMapper.map(signUpRequest, SignUpRequestWithUuid.class);
        map.setUuid(user.getUuid());
        try {
            restTemplate.postForEntity("http://localhost:8081/api/students", map, SignUpRequestWithUuid.class);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        AuthResponse authResponse = modelMapper.map(user, AuthResponse.class);
        return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
    }

    private UserDto createUser(SignUpRequest signUpRequest) {
        UserDto user = new UserDto();
        user.setName(signUpRequest.getFirstName() + " " + signUpRequest.getLastName());
        user.setUsername(signUpRequest.getUsername());
        user.setPassword(signUpRequest.getPassword());
        user.setEmail(signUpRequest.getEmail());
        user.setRole(Role.STUDENT);
        return user;
    }
}