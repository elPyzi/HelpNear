package com.invocation.server.repository;

import com.invocation.server.entity.ProblemProcessing;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProblemProcessingRepo extends JpaRepository<ProblemProcessing, Integer> {}
