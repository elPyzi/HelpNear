package com.invocation.server.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "clients")
@Data
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_id")
    private int id;

    @OneToOne
    @JoinColumn(name = "fk_user_id", referencedColumnName = "id", nullable = false, unique = true)
    private Users user;

    @ManyToOne
    @JoinColumn(name = "fk_professional", referencedColumnName = "id")
    private Professional professional;
}