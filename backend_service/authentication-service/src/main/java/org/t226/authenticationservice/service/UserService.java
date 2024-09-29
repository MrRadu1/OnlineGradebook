package org.t226.authenticationservice.service;

import org.springframework.stereotype.Service;
import org.t226.authenticationservice.user.User;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {
    User save(User user);

    User find(String uuid);

    void update(String uuid, User toBeUpdated);

    void delete(String uuid);

    Optional<User> getUserByUsername(String username);

    Optional<User> validUsernameAndPassword(String username, String password);

    List<User> findAll();

    User saveForUpdate(User user, User updatedUser);
}
