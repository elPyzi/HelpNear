package com.invocation.server.repository;

import com.invocation.server.entity.Professional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfessionalRepo extends JpaRepository<Professional, Integer> {}
