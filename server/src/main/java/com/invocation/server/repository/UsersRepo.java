package com.invocation.server.repository;

import com.invocation.server.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepo extends JpaRepository<Users, Integer> {
    Optional<Users> findByLogin(String login);
    Optional<Users> findByUserEmail(String userEmail);
}
