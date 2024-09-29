package org.t226.authenticationservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.t226.authenticationservice.user.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUuid(String uuid);

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);
}
