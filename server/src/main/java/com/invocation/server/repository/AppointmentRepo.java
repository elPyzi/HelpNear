package com.invocation.server.repository;

import com.invocation.server.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppointmentRepo extends JpaRepository<Appointment, Integer> {
    List<Appointment> findByUserId(int userId);
}
