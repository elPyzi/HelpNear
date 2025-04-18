package com.invocation.server.repository;

import com.invocation.server.entity.Problem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProblemRepo extends JpaRepository<Problem, Integer> {
    List<Problem> findBySupportCenterId(Integer centerId);
}
