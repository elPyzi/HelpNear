package com.invocation.server.repository;

import com.invocation.server.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClientRepo extends JpaRepository<Client, Integer> {
    List<Client> findByProfessionalId(int professionalId);
}
