package com.invocation.server.repository;

import com.invocation.server.entity.CenterEmployee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CenterEmployeeRepo extends JpaRepository<CenterEmployee, Integer> {
    Optional<CenterEmployee> findByUserId(int userId);
}
