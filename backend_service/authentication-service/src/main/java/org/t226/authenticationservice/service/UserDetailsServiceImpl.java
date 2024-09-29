package org.t226.authenticationservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.t226.authenticationservice.exception.AppException;
import org.t226.authenticationservice.exception.ExceptionType;
import org.t226.authenticationservice.repository.UserRepository;
import org.t226.authenticationservice.service.model.CustomUserDetails;
import org.t226.authenticationservice.user.User;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new AppException(String.format("Username %s not found", username), ExceptionType.USERNAME_NOT_FOUND));
        List<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(user.getRole().toString()));
        return mapUserToCustomUserDetails(user, authorities);
    }

    private CustomUserDetails mapUserToCustomUserDetails(User user, List<SimpleGrantedAuthority> authorities) {
        CustomUserDetails customUserDetails = new CustomUserDetails();
        customUserDetails.setId(user.getId());
        customUserDetails.setUsername(user.getUsername());
        customUserDetails.setPassword(user.getPassword());
        customUserDetails.setEmail(user.getEmail());
        customUserDetails.setUuid(user.getUuid());
        customUserDetails.setAuthorities(authorities);
        return customUserDetails;
    }
}
