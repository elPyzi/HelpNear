package com.invocation.server.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "problem_processing")
@Data
public class ProblemProcessing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "process", nullable = false, length = 50)
    private String process = "idle";
}
