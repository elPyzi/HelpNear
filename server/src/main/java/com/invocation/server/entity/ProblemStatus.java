package com.invocation.server.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "problem_status")
@Data
public class ProblemStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "status", nullable = false, length = 50)
    private String status = "Minor";
}
