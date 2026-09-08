package com.example.selfobservationrecord.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import jakarta.validation.constraints.Size;

@Entity
@Table(
    name = "diaries",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "record_date"})
    }
)
public class Diary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "record_date", nullable = false)
    private LocalDate recordDate;

    @Size(max = 300)
    @Column(nullable = false, columnDefinition = "TEXT")
    private String event;

    @Size(max = 300)
    @Column(columnDefinition = "TEXT")
    private String reflection;

    @Column(nullable = false)
    private Integer status;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    // getter / setter は後で追加
}