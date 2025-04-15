package com.invocation.server.repository;

import com.invocation.server.entity.ProblemStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProblemStatusRepo extends JpaRepository<ProblemStatus, Integer> {}
