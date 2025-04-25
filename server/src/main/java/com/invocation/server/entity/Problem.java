package com.invocation.server.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

@Entity
@Table(name = "problems")
@Data
public class Problem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title", nullable = false, length = 20)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "received_date", nullable = false)
    private LocalDate receivedDate;

    @ManyToOne
    @JoinColumn(name = "fk_center", referencedColumnName = "id", nullable = false)
    private SupportCenter supportCenter;

    @ManyToOne
    @JoinColumn(name = "fk_problem_status_id", referencedColumnName = "id", nullable = false)
    private ProblemStatus problemStatus;

    @ManyToOne
    @JoinColumn(name = "fk_problem_proccesing", referencedColumnName = "id", nullable = false)
    private ProblemProcessing problemProcessing;

    public void setReceivedDateFromString(String dateString) {
        try {
            String[] parts = dateString.split("-");
            if (parts.length == 3) {
                String year = parts[0];
                String month = parts[1].length() == 1 ? "0" + parts[1] : parts[1];
                String day = parts[2].length() == 1 ? "0" + parts[2] : parts[2];
                dateString = year + "-" + month + "-" + day;
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            this.receivedDate = LocalDate.parse(dateString, formatter);
        }
        catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Неверный формат даты. Ожидается yyyy-MM-dd или yyyy-M-d");
        }
    }

    public String getReceivedDateAsString() {
        if (this.receivedDate == null) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return this.receivedDate.format(formatter);
    }
}