package com.invocation.server.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "appointments")
@Data
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "text_info", nullable = false, columnDefinition = "TEXT")
    private String textInfo;

    @ManyToOne
    @JoinColumn(name = "fk_user", referencedColumnName = "id", nullable = false)
    private Users user;

    @ManyToOne
    @JoinColumn(name = "fk_professional", referencedColumnName = "id")
    private Professional professional;

    @Column(name = "title", nullable = false, length = 20)
    private String title;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;
}