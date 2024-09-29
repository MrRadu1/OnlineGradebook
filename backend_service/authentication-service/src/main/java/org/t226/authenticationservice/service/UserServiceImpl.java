package org.t226.authenticationservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.t226.authenticationservice.exception.AppException;
import org.t226.authenticationservice.exception.ExceptionType;
import org.t226.authenticationservice.repository.UserRepository;
import org.t226.authenticationservice.user.User;
import org.t226.authenticationservice.validator.UserValidator;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private static final String USER_NOT_PRESENT = "User is not present in the database";
    private static final String PASSWORD_INVALID = "password is invalid";
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    public User save(User user) {
        String encode = passwordEncoder.encode(user.getPassword());
        UserValidator.validateUser(user, userRepository);
        user.setPassword(encode);
        return userRepository.save(user);
    }

    @Override
    public User saveForUpdate(User user, User updatedUser) {
        if(!user.getUsername().equals(updatedUser.getUsername())){
            UserValidator.validateUsername(updatedUser.getUsername(), userRepository);
        }
        if(!user.getEmail().equals(updatedUser.getEmail())){
            UserValidator.validateEmail(updatedUser.getEmail(), userRepository);
        }
        user.setUsername(updatedUser.getUsername());
        user.setEmail(updatedUser.getEmail());
        user.setName(updatedUser.getName());
        return userRepository.save(user);
    }

    @Override
    public User find(String uuid) {
        return userRepository.findByUuid(uuid).orElseThrow(() -> new AppException(
                USER_NOT_PRESENT, ExceptionType.USERNAME_NOT_FOUND
        ));
    }

    @Override
    @Transactional
    public void update(String uuid, User updatedUser) {
        User user = find(uuid);
        saveForUpdate(user, updatedUser);
    }

    @Override
    public void delete(String uuid) {
        userRepository.delete(find(uuid));
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<User> validUsernameAndPassword(String username, String password) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (passwordEncoder.matches(password, userDetails.getPassword())) {
            return getUserByUsername(username);
        } else {
            throw new AppException(PASSWORD_INVALID, ExceptionType.INVALID_PASSWORD);
        }
    }


    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }
}
