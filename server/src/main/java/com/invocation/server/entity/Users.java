package com.invocation.server.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Entity
@Table(name = "users")
@Data
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "login", nullable = false, length = 50)
    private String login;

    @Column(name = "full_name", nullable = false, length = 100)
    private String fullName;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Column(name = "user_email", nullable = false, length = 50)
    private String userEmail;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Column(name = "address", nullable = false, length = 100)
    private String address;

    @Column(name = "contact_number", nullable = false, length = 15)
    private String contactNumber;

    @Column(name = "isBaned", nullable = false)
    private Boolean isBaned = false;

    @ManyToOne
    @JoinColumn(name = "fk_user_role", referencedColumnName = "id", nullable = false)
    private Roles role;

    public void setBirthDateFromString(String dateString) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            this.birthDate = LocalDate.parse(dateString, formatter);
        }
        catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Неверный формат даты. Ожидается dd.MM.yyyy");
        }
    }

    public String getBirthDateAsString() {
        if (this.birthDate == null) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return this.birthDate.format(formatter);
    }

}