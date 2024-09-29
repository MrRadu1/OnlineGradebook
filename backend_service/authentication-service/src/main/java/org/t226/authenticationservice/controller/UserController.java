package org.t226.authenticationservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.t226.authenticationservice.controller.model.UserDto;
import org.t226.authenticationservice.controller.model.UserEditDto;
import org.t226.authenticationservice.controller.model.UserWithAddressDto;
import org.t226.authenticationservice.facade.UserFacade;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserFacade userFacade;

    @GetMapping
    public ResponseEntity<List<UserDto>> findAll() {
        return new ResponseEntity<>(userFacade.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<UserWithAddressDto> find(@PathVariable String uuid) {
        return new ResponseEntity<>(userFacade.find(uuid), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserDto> save(@RequestBody UserDto userDto) {
        return new ResponseEntity<>(userFacade.save(userDto), HttpStatus.OK);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity delete(@PathVariable String uuid) {
        userFacade.delete(uuid);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity update(@PathVariable String uuid, @RequestBody UserEditDto userDto) {
        userFacade.update(uuid, userDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
