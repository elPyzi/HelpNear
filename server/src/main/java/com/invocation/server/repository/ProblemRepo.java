package com.invocation.server.repository;

import com.invocation.server.entity.Problem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProblemRepo extends JpaRepository<Problem, Integer> {}
