package com.invocation.server.repository;

import com.invocation.server.entity.Professional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProfessionalRepo extends JpaRepository<Professional, Integer> {
    List<Professional> findByCenterId(int centerId);
}
