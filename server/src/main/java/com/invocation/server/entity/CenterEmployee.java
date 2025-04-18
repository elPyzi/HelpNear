package com.invocation.server.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "center_employee")
@Data
public class CenterEmployee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @OneToOne
    @JoinColumn(name = "fk_user", referencedColumnName = "id", nullable = false, unique = true)
    private Users user;

    @ManyToOne
    @JoinColumn(name = "fk_center", referencedColumnName = "id")
    private SupportCenter center;
}