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
    public void setUser(User user) {
        this.user = user;
    }

    public void setRecordDate(LocalDate recordDate) {
        this.recordDate = recordDate;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public void setReflection(String reflection) {
        this.reflection = reflection;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}