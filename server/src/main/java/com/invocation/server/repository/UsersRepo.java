package com.invocation.server.repository;

import com.invocation.server.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsersRepo extends JpaRepository<Users, Integer> {
    Optional<Users> findByLogin(String login);
    Optional<Users> findByEmail(String email);
    Optional<Users> findByProblemId(int problemId);
    List<Users> findByRoleId(int roleId);
}
