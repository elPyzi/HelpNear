package com.invocation.server.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "professionals")
@Data
public class Professional {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "info", nullable = false, columnDefinition = "TEXT")
    private String info;

    @Column(name = "average_rating")
    private double averageRating;

    @OneToOne
    @JoinColumn(name = "fk_user", referencedColumnName = "id", nullable = false, unique = true)
    private Users user;

    @ManyToOne
    @JoinColumn(name = "fk_center", referencedColumnName = "id")
    private SupportCenter center;
}