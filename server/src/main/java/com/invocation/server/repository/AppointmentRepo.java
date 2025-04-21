package com.invocation.server.repository;

import com.invocation.server.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepo extends JpaRepository<Appointment, Integer> {
}
